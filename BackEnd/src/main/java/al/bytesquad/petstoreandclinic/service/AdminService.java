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
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import org.modelmapper.ModelMapper;
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

    @Autowired
    public AdminService(AdminRepository adminRepository, ModelMapper modelMapper, UserRepository userRepository,
                        RoleRepository roleRepository) {
        this.adminRepository = adminRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Transactional
    public AdminDTO create(AdminSaveDTO adminSaveDTO) {
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

    public AdminDTO update(AdminSaveDTO adminSaveDTO, long id) {
        Admin admin = adminRepository.findAdminById(id).orElseThrow(() -> new ResourceNotFoundException("Admin", "id", id));
        admin.setFirstName(adminSaveDTO.getFirstName());
        admin.setLastName(adminSaveDTO.getLastName());
        admin.setEmail(adminSaveDTO.getEmail());
        admin.setPassword(passwordEncoder.encode(adminSaveDTO.getPassword()));
        Admin updatedAdmin = adminRepository.save(admin);
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

    public void delete(long id) {
        Admin admin = adminRepository.findAdminById(id).orElseThrow(() -> new ResourceNotFoundException("Admin", "id", id));
        admin.setEnabled(false);
        adminRepository.save(admin);
    }

    public List<AdminDTO> getAll(String keyword) {
        List<String> keyValues = List.of(keyword.split(","));
        HashMap<String, String> pairs = new HashMap<>();
        for (String s : keyValues) {
            String[] strings = s.split(":");
            pairs.put(strings[0], strings[1]);
        }

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
