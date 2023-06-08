package al.bytesquad.petstoreandclinic.service;

import al.bytesquad.petstoreandclinic.entity.*;
import al.bytesquad.petstoreandclinic.payload.entityDTO.FeedbackDTO;
import al.bytesquad.petstoreandclinic.payload.saveDTO.FeedbackSaveDTO;
import al.bytesquad.petstoreandclinic.repository.*;
import al.bytesquad.petstoreandclinic.service.exception.ResourceNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FeedbackService {

    private final FeedbackRepository feedbackRepository;
    private final ModelMapper modelMapper;
    private final ClientRepository clientRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ManagerRepository managerRepository;
    private final DoctorRepository doctorRepository;
    private final ObjectMapper objectMapper;

    public FeedbackService(FeedbackRepository feedbackRepository, ModelMapper modelMapper,
                           ClientRepository clientRepository, UserRepository userRepository, RoleRepository roleRepository,
                           ManagerRepository managerRepository,
                           DoctorRepository doctorRepository, ObjectMapper objectMapper) {
        this.feedbackRepository = feedbackRepository;
        this.modelMapper = modelMapper;
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.managerRepository = managerRepository;
        this.doctorRepository = doctorRepository;
        this.objectMapper = objectMapper;

        modelMapper.addMappings(new PropertyMap<Feedback, FeedbackDTO>() {
            @Override
            protected void configure() {
                map().setId(source.getId());
            }
        });
    }

    public FeedbackDTO create(String jsonString, Principal principal) throws JsonProcessingException {
        FeedbackSaveDTO feedbackSaveDTO = objectMapper.readValue(jsonString, FeedbackSaveDTO.class);

        Feedback feedback = modelMapper.map(feedbackSaveDTO, Feedback.class);
        String loggedInEmail = principal.getName();
        Client client = clientRepository.findByEmail(loggedInEmail);
        feedback.setClient(client);
        return modelMapper.map(feedbackRepository.save(feedback), FeedbackDTO.class);
    }

    public String delete(long id) {
        Feedback feedback = feedbackRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Feedback", "id", id));
        feedbackRepository.delete(feedback);
        return "Feedback deleted successfully!";
    }

    public FeedbackDTO getById(long id) {
        Feedback feedback = feedbackRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Feedback", "id", id));
        return modelMapper.map(feedback, FeedbackDTO.class);
    }

    public List<FeedbackDTO> getAll(String keyword, Principal principal) {
        if(keyword == null)
            return feedbackRepository.findAll().stream().map(feedback -> modelMapper.map(feedback, FeedbackDTO.class)).collect(Collectors.toList());

        List<String> keyValues = List.of(keyword.split(","));
        HashMap<String, String> pairs = new HashMap<>();
        for (String s : keyValues) {
            String[] strings = s.split(":");
            pairs.put(strings[0], strings[1]);
        }

        List<Feedback> feedbacks = feedbackRepository.findAll((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            for (String key : pairs.keySet()) {
                Path<Object> fieldPath = root.get(key);
                predicates.add(criteriaBuilder.equal(fieldPath, pairs.get(key)));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });

        List<Feedback> filteredFeedbacks;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInEmail = principal.getName();
        User user = userRepository.findByEmail(loggedInEmail);
        List<String> userRoles = user.getRoles().stream()
                .map(Role::getName).toList();

        String selectedRole = null;

        if (authentication != null && userRoles.contains(authentication.getAuthorities().iterator().next().getAuthority())) {
            selectedRole = authentication.getAuthorities().iterator().next().getAuthority();
            // Assuming the authority is in the format "ROLE_{ROLE_NAME}"
            selectedRole = selectedRole.substring("ROLE_".length()).toLowerCase();
        }

        if (selectedRole.equals("admin"))
            filteredFeedbacks = feedbacks;
        else if (selectedRole.equals("manager"))
            filteredFeedbacks = feedbacks.stream()
                    .filter(feedback -> feedback.getShop().equals(managerRepository.findByEmail(loggedInEmail).getShop()))
                    .collect(Collectors.toList());
        else if (selectedRole.equals("doctor"))
            filteredFeedbacks = feedbacks.stream()
                    .filter(feedback -> feedback.getDoctor().equals(doctorRepository.findByEmail(loggedInEmail)))
                    .collect(Collectors.toList());
        else if (selectedRole.equals("client"))
            filteredFeedbacks = feedbacks.stream()
                    .filter(feedback -> feedback.getClient().equals(clientRepository.findByEmail(loggedInEmail)))
                    .collect(Collectors.toList());
        else filteredFeedbacks = null;

        if (filteredFeedbacks == null)
            return null;
        return filteredFeedbacks.stream().map(feedback -> modelMapper.map(feedback, FeedbackDTO.class)).collect(Collectors.toList());
    }
}
