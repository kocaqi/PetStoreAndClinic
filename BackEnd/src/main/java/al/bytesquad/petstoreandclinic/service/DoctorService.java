package al.bytesquad.petstoreandclinic.service;

import al.bytesquad.petstoreandclinic.entity.Doctor;
import al.bytesquad.petstoreandclinic.entity.Manager;
import al.bytesquad.petstoreandclinic.entity.Role;
import al.bytesquad.petstoreandclinic.entity.User;
import al.bytesquad.petstoreandclinic.payload.entityDTO.DoctorDTO;
import al.bytesquad.petstoreandclinic.payload.saveDTO.DoctorSaveDTO;
import al.bytesquad.petstoreandclinic.repository.*;
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
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ManagerRepository managerRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ShopRepository shopRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public DoctorService(DoctorRepository doctorRepository, ModelMapper modelMapper,
                         UserRepository userRepository,
                         RoleRepository roleRepository,
                         ManagerRepository managerRepository, PasswordEncoder passwordEncoder,
                         ShopRepository shopRepository, ObjectMapper objectMapper) {
        this.doctorRepository = doctorRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.managerRepository = managerRepository;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.shopRepository = shopRepository;
        this.objectMapper = objectMapper;

        modelMapper.addMappings(new PropertyMap<Doctor, DoctorDTO>() {
            @Override
            protected void configure() {
                map().setId(source.getId());
            }
        });
    }

    @Transactional
    public DoctorDTO create(String jsonString) throws JsonProcessingException {
        DoctorSaveDTO doctorSaveDTO = objectMapper.readValue(jsonString, DoctorSaveDTO.class);

        //convert DTO to entity
        Doctor doctor = modelMapper.map(doctorSaveDTO, Doctor.class);
        doctor.setPassword((passwordEncoder.encode(doctorSaveDTO.getPassword())));
        Role doctorRole = roleRepository.findRoleByName("ROLE_DOCTOR");
        doctor.setRole(doctorRole);
        Doctor newDoctor = doctorRepository.save(doctor);

        User user = new User();
        user.setFirstName(doctorSaveDTO.getFirstName());
        user.setLastName(doctorSaveDTO.getLastName());
        user.setEmail(doctorSaveDTO.getEmail());
        user.setPassword(passwordEncoder.encode(doctorSaveDTO.getPassword()));
        List<Role> roles = new ArrayList<>();
        roles.add(doctorRole);
        user.setRoles(roles);
        user.setSecondId(doctor.getId());
        userRepository.save(user);

        //convert entity to DTO
        return modelMapper.map(newDoctor, DoctorDTO.class);
    }

    public List<DoctorDTO> getAll(String keyword, Principal principal) {
        if (keyword == null)
            return doctorRepository.findAllByEnabled(true).stream().map(doctor -> modelMapper.map(doctor, DoctorDTO.class)).collect(Collectors.toList());

        List<String> keyValues = List.of(keyword.split(","));
        HashMap<String, String> pairs = new HashMap<>();
        for (String s : keyValues) {
            String[] strings = s.split(":");
            pairs.put(strings[0], strings[1]);
        }
        pairs.put("enabled", "1");

        List<Doctor> doctors = doctorRepository.findAll((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            for (String key : pairs.keySet()) {
                Path<Object> fieldPath = root.get(key);
                predicates.add(criteriaBuilder.equal(fieldPath, pairs.get(key)));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });

        /*List<Doctor> filteredDoctors;

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

        if (selectedRole.equals("manager"))
            filteredDoctors = doctors.stream()
                    .filter(doctor -> doctor.getShop().equals(managerRepository.findByEmail(loggedInEmail).getShop()))
                    .collect(Collectors.toList());
        else if (selectedRole.equals("admin") || selectedRole.equals("client"))
            filteredDoctors = doctors;
        else
            filteredDoctors = null;

        if (filteredDoctors == null)
            return null;*/
        return doctors.stream().map(doctor -> modelMapper.map(doctor, DoctorDTO.class)).collect(Collectors.toList());
    }

    public DoctorDTO getById(long id) {
        Doctor doctor = doctorRepository.findDoctorById(id).orElseThrow(() -> new ResourceNotFoundException("Doctor", "id", id));
        return modelMapper.map(doctor, DoctorDTO.class);
    }

    @Transactional
    public DoctorDTO update(String jsonString, long id) throws JsonProcessingException {
        DoctorSaveDTO doctorSaveDTO = objectMapper.readValue(jsonString, DoctorSaveDTO.class);

        Doctor doctor = doctorRepository.findDoctorById(id).orElseThrow(() -> new ResourceNotFoundException("Doctor", "id", id));
        String email = doctor.getEmail();
        String password = doctor.getPassword();
        doctor.setFirstName(doctorSaveDTO.getFirstName());
        doctor.setLastName(doctorSaveDTO.getLastName());
        doctor.setEmail(doctorSaveDTO.getEmail());
        doctor.setPassword(passwordEncoder.encode(password));
        Doctor updatedDoctor = doctorRepository.save(doctor);

        User user = userRepository.findByEmail(email);
        user.setFirstName(doctorSaveDTO.getFirstName());
        user.setLastName(doctorSaveDTO.getLastName());
        user.setEmail(doctorSaveDTO.getEmail());
        user.setPassword(passwordEncoder.encode(password));
        userRepository.save(user);

        return modelMapper.map(updatedDoctor, DoctorDTO.class);
    }

    public List<DoctorDTO> searchBy(String keyword, Principal principal) {
        MySpecification<Doctor> specifyByFirstName = new MySpecification<>(new SearchCriteria("firstName", ":", keyword));
        MySpecification<Doctor> specifyByLastName = new MySpecification<>(new SearchCriteria("lastName", ":", keyword));
        MySpecification<Doctor> specifyByEmail = new MySpecification<>(new SearchCriteria("email", ":", keyword));

        String loggedInEmail = principal.getName();
        User loggedInUser = userRepository.findByEmail(loggedInEmail);
        Role adminRole = roleRepository.findRoleByName("ROLE_ADMIN");
        List<Doctor> doctors;

        if (loggedInUser.getRoles().contains(adminRole))
            doctors = doctorRepository.findAll(Specification
                    .where(specifyByFirstName).and(specifyByLastName).and(specifyByEmail));
        else {
            Manager manager = managerRepository.findByEmail(loggedInEmail);
            long shopId = manager.getShop().getId();
            MySpecification<Doctor> specifyByShop = new MySpecification<>(new SearchCriteria("shop", ":", shopId));
            doctors = doctorRepository.findAll(Specification.where(specifyByFirstName).and(specifyByLastName).and(specifyByEmail).and(specifyByShop));
        }

        return doctors.stream().map(doctor -> modelMapper.map(doctor, DoctorDTO.class)).collect(Collectors.toList());
    }

    @Transactional
    public String delete(long id) {
        Doctor doctor = doctorRepository.findDoctorById(id).orElseThrow(() -> new ResourceNotFoundException("Doctor", "id", id));
        doctor.setEnabled(false);
        doctorRepository.save(doctor);
        User user = userRepository.findUserById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
        user.setEnabled(false);
        userRepository.save(user);
        return "User deleted successfully!";
    }
}
