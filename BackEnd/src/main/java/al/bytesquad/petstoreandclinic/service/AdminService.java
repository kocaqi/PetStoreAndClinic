package al.bytesquad.petstoreandclinic.service;

import al.bytesquad.petstoreandclinic.entity.Admin;
import al.bytesquad.petstoreandclinic.entity.Role;
import al.bytesquad.petstoreandclinic.entity.User;
import al.bytesquad.petstoreandclinic.payload.entityDTO.AdminDTO;
import al.bytesquad.petstoreandclinic.payload.saveDTO.AdminSaveDTO;
import al.bytesquad.petstoreandclinic.repository.AdminRepository;
import al.bytesquad.petstoreandclinic.repository.RoleRepository;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminService {
    private final AdminRepository adminRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public AdminService(AdminRepository adminRepository, ModelMapper modelMapper, UserRepository userRepository,
                        RoleRepository roleRepository, ObjectMapper objectMapper) {
        this.adminRepository = adminRepository;
        this.modelMapper = modelMapper;
        this.objectMapper = objectMapper;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;

        // Configure mapping for Admin to AdminDTO
        modelMapper.addMappings(new PropertyMap<Admin, AdminDTO>() {
            @Override
            protected void configure() {
                map().setId(source.getId());
            }
        });
    }

    @Transactional
    public AdminDTO create(String jsonString) throws JsonProcessingException {
        AdminSaveDTO adminSaveDTO = objectMapper.readValue(jsonString, AdminSaveDTO.class);

        Admin admin = modelMapper.map(adminSaveDTO, Admin.class);
        admin.setPassword(passwordEncoder.encode(adminSaveDTO.getPassword()));
        Role adminRole = roleRepository.findRoleByName("ROLE_ADMIN");
        admin.setRole(adminRole);
        Admin newAdmin = adminRepository.save(admin);

        User user = new User();
        user.setFirstName(adminSaveDTO.getFirstName());
        user.setLastName(adminSaveDTO.getLastName());
        user.setEmail(adminSaveDTO.getEmail());
        user.setPassword(passwordEncoder.encode(adminSaveDTO.getPassword()));
        List<Role> roles = new ArrayList<>();
        roles.add(adminRole);
        user.setRoles(roles);
        userRepository.save(user);

        return modelMapper.map(newAdmin, AdminDTO.class);
    }

    public AdminDTO getById(long id) {
        Admin admin = adminRepository.findAdminById(id).orElseThrow(() -> new ResourceNotFoundException("Admin", "id", id));
        return modelMapper.map(admin, AdminDTO.class);
    }

    @Transactional
    public AdminDTO update(String jsonString, long id) throws JsonProcessingException {
        AdminSaveDTO adminSaveDTO = objectMapper.readValue(jsonString, AdminSaveDTO.class);

        Admin admin = adminRepository.findAdminById(id).orElseThrow(() -> new ResourceNotFoundException("Admin", "id", id));
        String email = admin.getEmail();
        String password = admin.getPassword();
        admin.setFirstName(adminSaveDTO.getFirstName());
        admin.setLastName(adminSaveDTO.getLastName());
        admin.setEmail(adminSaveDTO.getEmail());
        admin.setPassword(passwordEncoder.encode(password));
        Admin updatedAdmin = adminRepository.save(admin);

        User user = userRepository.findByEmail(email);
        user.setFirstName(adminSaveDTO.getFirstName());
        user.setLastName(adminSaveDTO.getLastName());
        user.setEmail(adminSaveDTO.getEmail());
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);

        return modelMapper.map(updatedAdmin, AdminDTO.class);
    }

    public List<AdminDTO> searchBy(String keyword) {
        MySpecification<Admin> specifyByFirstName = new MySpecification<>(new SearchCriteria("firstName", ":", keyword));
        MySpecification<Admin> specifyByLastName = new MySpecification<>(new SearchCriteria("lastName", ":", keyword));
        MySpecification<Admin> specifyByEmail = new MySpecification<>(new SearchCriteria("email", ":", keyword));

        List<Admin> admins = adminRepository.findAll(Specification
                .where(specifyByFirstName).and(specifyByLastName).and(specifyByEmail));

        return admins.stream().map(admin -> modelMapper.map(admin, AdminDTO.class)).collect(Collectors.toList());

    }

    @Transactional
    public String delete(long id) {
        Admin admin = adminRepository.findAdminById(id).orElseThrow(() -> new ResourceNotFoundException("Admin", "id", id));
        admin.setEnabled(false);
        adminRepository.save(admin);
        User user = userRepository.findUserById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        user.setEnabled(false);
        userRepository.save(user);
        return "User deleted successfully!";
    }

    public List<AdminDTO> getAll(String keyword) {
        if(keyword == null)
            return adminRepository.findEnabledAdmins().stream().map(admin -> modelMapper.map(admin, AdminDTO.class)).collect(Collectors.toList());

        List<String> keyValues = List.of(keyword.split(","));
        HashMap<String, String> pairs = new HashMap<>();
        for (String s : keyValues) {
            String[] strings = s.split(":");
            pairs.put(strings[0], strings[1]);
        }
        pairs.put("enabled", "1");

        // Retrieve admins based on the specified criteria
        List<Admin> admins = adminRepository.findAll((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            for (String key : pairs.keySet()) {
                Path<Object> fieldPath = root.get(key);
                predicates.add(criteriaBuilder.equal(fieldPath, pairs.get(key)));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });

        // Convert Admin entities to AdminDTO
        return admins.stream().map(admin -> modelMapper.map(admin, AdminDTO.class)).collect(Collectors.toList());
    }
}
