package al.bytesquad.petstoreandclinic.service;

import al.bytesquad.petstoreandclinic.entity.Receptionist;
import al.bytesquad.petstoreandclinic.entity.Role;
import al.bytesquad.petstoreandclinic.entity.User;
import al.bytesquad.petstoreandclinic.payload.entityDTO.ReceptionistDTO;
import al.bytesquad.petstoreandclinic.payload.saveDTO.ReceptionistSaveDTO;
import al.bytesquad.petstoreandclinic.repository.*;
import al.bytesquad.petstoreandclinic.service.exception.ResourceNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReceptionistService {

    private final ReceptionistRepository receptionistRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final ManagerRepository managerRepository;
    private final RoleRepository roleRepository;
    private final ObjectMapper objectMapper;
    private final ShopRepository shopRepository;

    @Autowired
    public ReceptionistService(ReceptionistRepository receptionistRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, UserRepository userRepository,
                               ManagerRepository managerRepository,
                               RoleRepository roleRepository, ObjectMapper objectMapper,
                               ShopRepository shopRepository) {
        this.receptionistRepository = receptionistRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.userRepository = userRepository;
        this.managerRepository = managerRepository;
        this.roleRepository = roleRepository;
        this.objectMapper = objectMapper;

        modelMapper.addMappings(new PropertyMap<Receptionist, ReceptionistDTO>() {
            @Override
            protected void configure() {
                map().setId(source.getId());
            }
        });
        this.shopRepository = shopRepository;
    }

    public ReceptionistDTO create(String jsonString) throws JsonProcessingException {
        ReceptionistSaveDTO receptionistSaveDTO = objectMapper.readValue(jsonString, ReceptionistSaveDTO.class);

        Receptionist receptionist = modelMapper.map(receptionistSaveDTO, Receptionist.class);
        receptionist.setPassword(passwordEncoder.encode(receptionistSaveDTO.getPassword()));
        Role receptionistRole = roleRepository.findRoleByName("ROLE_RECEPTIONIST");
        receptionist.setRole(receptionistRole);

        User user = new User();
        user.setFirstName(receptionistSaveDTO.getFirstName());
        user.setLastName(receptionist.getLastName());
        user.setEmail(receptionist.getEmail());
        user.setPassword(passwordEncoder.encode(receptionistSaveDTO.getPassword()));
        List<Role> roles = new ArrayList<>();
        roles.add(receptionistRole);
        user.setRoles(roles);
        userRepository.save(user);

        return modelMapper.map(receptionistRepository.save(receptionist), ReceptionistDTO.class);
    }

    @Transactional
    public ReceptionistDTO update(String jsonString, long id) throws JsonProcessingException {
        ReceptionistSaveDTO receptionistSaveDTO = objectMapper.readValue(jsonString, ReceptionistSaveDTO.class);

        Receptionist receptionist = receptionistRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Receptionist", "id", id));
        receptionist.setFirstName(receptionistSaveDTO.getFirstName());
        receptionist.setLastName(receptionistSaveDTO.getLastName());
        receptionist.setEmail(receptionistSaveDTO.getEmail());
        receptionist.setPassword(passwordEncoder.encode(receptionistSaveDTO.getPassword()));
        receptionist.setRole(receptionistSaveDTO.getRole());
        receptionist.setShop(shopRepository.findById(receptionistSaveDTO.getShopId()).orElseThrow(() -> new ResourceNotFoundException("Shop", "id", id)));

        User user = userRepository.findUserById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        user.setFirstName(receptionistSaveDTO.getFirstName());
        user.setLastName(receptionistSaveDTO.getLastName());
        user.setEmail(receptionistSaveDTO.getEmail());
        user.setPassword(passwordEncoder.encode(receptionistSaveDTO.getPassword()));
        user.setSecondId(receptionist.getId());
        userRepository.save(user);

        return modelMapper.map(receptionistRepository.save(receptionist), ReceptionistDTO.class);
    }

    @Transactional
    public String delete(long id) {
        Receptionist receptionist = receptionistRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Receptionist", "id", id));
        receptionist.setEnabled(false);
        receptionistRepository.save(receptionist);
        User user = userRepository.findUserById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        user.setEnabled(false);
        userRepository.save(user);
        return "Receptionist deleted successfully!";
    }

    public List<ReceptionistDTO> get(String keyword, Principal principal) {
        if (keyword == null)
            return receptionistRepository.findAllByEnabled(true).stream().map(receptionist -> modelMapper.map(receptionist, ReceptionistDTO.class)).collect(Collectors.toList());

        List<String> keyValues = List.of(keyword.split(","));
        HashMap<String, String> pairs = new HashMap<>();
        for (String s : keyValues) {
            String[] strings = s.split(":");
            pairs.put(strings[0], strings[1]);
        }
        pairs.put("enabled", "1");

        List<Receptionist> receptionists = receptionistRepository.findAll((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            for (String key : pairs.keySet()) {
                Path<Object> fieldPath = root.get(key);
                predicates.add(criteriaBuilder.equal(fieldPath, pairs.get(key)));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });

        /*List<Receptionist> filtered;

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
            filtered = receptionists;
        else if (selectedRole.equals("manager"))
            filtered = receptionists.stream().filter(receptionist -> receptionist.getShop().equals(managerRepository.findByEmail(loggedInEmail).getShop())).collect(Collectors.toList());
        else filtered = null;

        if (filtered == null)
            return null;*/
        return receptionists.stream().map(receptionist -> modelMapper.map(receptionist, ReceptionistDTO.class)).collect(Collectors.toList());
    }
}
