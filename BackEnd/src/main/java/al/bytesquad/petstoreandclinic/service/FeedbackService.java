package al.bytesquad.petstoreandclinic.service;

import al.bytesquad.petstoreandclinic.entity.*;
import al.bytesquad.petstoreandclinic.payload.Response;
import al.bytesquad.petstoreandclinic.payload.entityDTO.FeedbackDTO;
import al.bytesquad.petstoreandclinic.payload.saveDTO.FeedbackSaveDTO;
import al.bytesquad.petstoreandclinic.repository.*;
import al.bytesquad.petstoreandclinic.service.exception.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.security.Principal;
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

    public FeedbackService(FeedbackRepository feedbackRepository, ModelMapper modelMapper,
                           ClientRepository clientRepository, UserRepository userRepository, RoleRepository roleRepository,
                           ManagerRepository managerRepository) {
        this.feedbackRepository = feedbackRepository;
        this.modelMapper = modelMapper;
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.managerRepository = managerRepository;
    }

    public FeedbackDTO create(FeedbackSaveDTO feedbackSaveDTO, Principal principal) {
        Feedback feedback = modelMapper.map(feedbackSaveDTO, Feedback.class);
        String loggedInEmail = principal.getName();
        Client client = clientRepository.findByEmail(loggedInEmail);
        feedback.setClient(client);
        return modelMapper.map(feedbackRepository.save(feedback), FeedbackDTO.class);
    }

    public void delete(long id) {
        Feedback feedback = feedbackRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Feedback", "id", id));
        feedbackRepository.delete(feedback);
    }

    public FeedbackDTO getById(long id) {
        Feedback feedback = feedbackRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Feedback", "id", id));
        return modelMapper.map(feedback, FeedbackDTO.class);
    }

    public Response<FeedbackDTO> getAll(int pageNo, int pageSize, String sortBy, String sortDir, long shopId, Principal principal) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Feedback> feedbacks;
        String loggedInEmail = principal.getName();
        User loggedInUser = userRepository.findByEmail(loggedInEmail);
        List<Role> roles = loggedInUser.getRoles();
        Role adminRole = roleRepository.findRoleByName("ROLE_ADMIN");

        if(roles.contains(adminRole)){
            if(shopId==-1)
                feedbacks = feedbackRepository.findAll(pageable);
            else
                feedbacks = feedbackRepository.findAllByShop(pageable, shopId);
        } else {
            Manager manager = managerRepository.findByEmail(loggedInEmail);
            shopId = manager.getShop().getId();
            feedbacks = feedbackRepository.findAllByShop(pageable, shopId);
        }
        List<Feedback> feedbackList = feedbacks.getContent();
        List<FeedbackDTO> content = feedbackList.stream().map(feedback -> modelMapper.map(feedback, FeedbackDTO.class)).toList();

        Response<FeedbackDTO> response = new Response<>();
        response.setContent(content);
        response.setPageNo(feedbacks.getNumber());
        response.setPageSize(feedbacks.getSize());
        response.setTotalElements(feedbacks.getTotalElements());
        response.setTotalPages(feedbacks.getTotalPages());
        response.setLast(feedbacks.isLast());

        return response;
    }
}
