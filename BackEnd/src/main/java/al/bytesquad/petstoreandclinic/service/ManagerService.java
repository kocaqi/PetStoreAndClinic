package al.bytesquad.petstoreandclinic.service;

import al.bytesquad.petstoreandclinic.entity.Manager;
import al.bytesquad.petstoreandclinic.entity.Role;
import al.bytesquad.petstoreandclinic.entity.User;
import al.bytesquad.petstoreandclinic.payload.entityDTO.ManagerDTO;
import al.bytesquad.petstoreandclinic.payload.saveDTO.ManagerSaveDTO;
import al.bytesquad.petstoreandclinic.repository.ManagerRepository;
import al.bytesquad.petstoreandclinic.repository.RoleRepository;
import al.bytesquad.petstoreandclinic.repository.ShopRepository;
import al.bytesquad.petstoreandclinic.repository.UserRepository;
import al.bytesquad.petstoreandclinic.search.MySpecification;
import al.bytesquad.petstoreandclinic.search.SearchCriteria;
import al.bytesquad.petstoreandclinic.service.exception.ResourceNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
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
public class ManagerService {

    private final ManagerRepository managerRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;
    private final ShopRepository shopRepository;

    @Autowired
    public ManagerService(ManagerRepository managerRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder,
                          RoleRepository roleRepository, UserRepository userRepository, ObjectMapper objectMapper,
                          ShopRepository shopRepository) {
        this.managerRepository = managerRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;

        modelMapper.addMappings(new PropertyMap<Manager, ManagerDTO>() {
            @Override
            protected void configure() {
                map().setId(source.getId());
            }
        });
        this.shopRepository = shopRepository;
    }

    public ManagerDTO create(String jsonString) throws JsonProcessingException {
        ManagerSaveDTO managerSaveDTO = objectMapper.readValue(jsonString, ManagerSaveDTO.class);

        Manager manager = modelMapper.map(managerSaveDTO, Manager.class);
        manager.setPassword(passwordEncoder.encode(managerSaveDTO.getPassword()));
        Role managerRole = roleRepository.findRoleByName("ROLE_MANAGER");
        manager.setRole(managerRole);
        if (managerSaveDTO.getShopId() != null)
            manager.setShop(shopRepository.findById(managerSaveDTO.getShopId()).orElseThrow(() -> new ResourceNotFoundException("Shop", "id", managerSaveDTO.getShopId())));
        Manager newManager = managerRepository.save(manager);

        User user = new User();
        user.setFirstName(managerSaveDTO.getFirstName());
        user.setLastName(managerSaveDTO.getLastName());
        user.setEmail(managerSaveDTO.getEmail());
        user.setPassword(passwordEncoder.encode(managerSaveDTO.getPassword()));
        List<Role> roles = new ArrayList<>();
        roles.add(managerRole);
        user.setRoles(roles);
        user.setSecondId(manager.getId());
        userRepository.save(user);

        return modelMapper.map(newManager, ManagerDTO.class);
    }

    public List<ManagerDTO> getAll(String keyword, Principal principal) {
        if (keyword == null)
            return managerRepository.findAllByEnabled(true).stream().map(manager -> modelMapper.map(manager, ManagerDTO.class)).collect(Collectors.toList());

        List<String> keyValues = List.of(keyword.split(","));
        HashMap<String, String> pairs = new HashMap<>();
        for (String s : keyValues) {
            String[] strings = s.split(":");
            pairs.put(strings[0], strings[1]);
        }
        pairs.put("enabled", "1");

        List<Manager> managers = managerRepository.findAll((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            for (String key : pairs.keySet()) {
                Path<Object> fieldPath = root.get(key);
                predicates.add(criteriaBuilder.equal(fieldPath, pairs.get(key)));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });

        /*List<Manager> filteredManagers;

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
            filteredManagers = managers;
        else
            filteredManagers = null;

        if (filteredManagers == null)
            return null;*/
        return managers.stream().map(manager -> modelMapper.map(manager, ManagerDTO.class)).collect(Collectors.toList());
    }

    public ManagerDTO getById(long id) {
        Manager manager = managerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Manager", "id", id));
        return modelMapper.map(manager, ManagerDTO.class);
    }

    @Transactional
    public ManagerDTO update(String jsonString, long id) throws JsonProcessingException {
        ManagerSaveDTO managerSaveDTO = objectMapper.readValue(jsonString, ManagerSaveDTO.class);

        Manager manager = managerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Manager", "id", id));
        String email = manager.getEmail();
        String password = manager.getPassword();
        manager.setFirstName(managerSaveDTO.getFirstName());
        manager.setLastName(managerSaveDTO.getLastName());
        manager.setEmail(managerSaveDTO.getEmail());
        manager.setPassword(passwordEncoder.encode(password));
        Manager updated = managerRepository.save(manager);

        User user = userRepository.findByEmail(email);
        user.setFirstName(managerSaveDTO.getFirstName());
        user.setLastName(managerSaveDTO.getLastName());
        user.setEmail(managerSaveDTO.getEmail());
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);

        return modelMapper.map(updated, ManagerDTO.class);
    }

    @Transactional
    public String delete(long id) {
        Manager manager = managerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Manager", "id", id));
        manager.setEnabled(false);
        managerRepository.save(manager);
        User user = userRepository.findUserById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        user.setEnabled(false);
        userRepository.save(user);
        return "User deleted successfully!";
    }

    public List<ManagerDTO> searchBy(String keyword) {
        MySpecification<Manager> specifyByFirstName = new MySpecification<>(new SearchCriteria("firstName", ":", keyword));
        MySpecification<Manager> specifyByLastName = new MySpecification<>(new SearchCriteria("lastName", ":", keyword));
        MySpecification<Manager> specifyByEmail = new MySpecification<>(new SearchCriteria("email", ":", keyword));

        List<Manager> managers = managerRepository.findAll(Specification
                .where(specifyByFirstName).and(specifyByLastName).and(specifyByEmail));

        return managers.stream().map(manager -> modelMapper.map(manager, ManagerDTO.class)).collect(Collectors.toList());
    }
}
