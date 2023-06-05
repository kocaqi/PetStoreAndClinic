package al.bytesquad.petstoreandclinic.service;

import al.bytesquad.petstoreandclinic.entity.*;
import al.bytesquad.petstoreandclinic.payload.Response;
import al.bytesquad.petstoreandclinic.payload.entityDTO.DoctorDTO;
import al.bytesquad.petstoreandclinic.payload.saveDTO.DoctorSaveDTO;
import al.bytesquad.petstoreandclinic.repository.*;
import al.bytesquad.petstoreandclinic.search.MySpecification;
import al.bytesquad.petstoreandclinic.search.SearchCriteria;
import al.bytesquad.petstoreandclinic.service.exception.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.print.Doc;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ManagerRepository managerRepository;
    private final PasswordEncoder passwordEncoder;
    private final ShopRepository shopRepository;

    @Autowired
    public DoctorService(DoctorRepository doctorRepository, ModelMapper modelMapper,
                         UserRepository userRepository,
                         RoleRepository roleRepository,
                         ManagerRepository managerRepository, PasswordEncoder passwordEncoder,
                         ShopRepository shopRepository) {
        this.doctorRepository = doctorRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.managerRepository = managerRepository;
        this.passwordEncoder = passwordEncoder;
        this.shopRepository = shopRepository;
    }

    @Transactional
    public DoctorDTO create(DoctorSaveDTO doctorSaveDTO) {
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
        userRepository.save(user);

        //convert entity to DTO
        return modelMapper.map(newDoctor, DoctorDTO.class);
    }

    public Response<DoctorDTO> getAll(int pageNo, int pageSize, String sortBy, String sortDir, long shopId, Principal principal) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Doctor> doctors;
        String loggedInEmail = principal.getName();
        User loggedInUser = userRepository.findByEmail(loggedInEmail);
        List<Role> roles = loggedInUser.getRoles();
        Role adminRole = roleRepository.findRoleByName("ROLE_ADMIN");

        if (roles.contains(adminRole)) {
            if (shopId == -1) {
                doctors = doctorRepository.findAll(pageable);
            } else {
                long finalShopId = shopId;
                Shop shop = shopRepository.findById(shopId).orElseThrow(() -> new ResourceNotFoundException("Shop", "id", finalShopId));
                List<Doctor> doctorList = doctorRepository.findAllByShop(shop);
                doctors = new PageImpl<>(doctorList, pageable, doctorList.size());
            }
        } else {
            Manager manager = managerRepository.findByEmail(loggedInEmail);
            shopId = manager.getShop().getId();
            long finalShopId = shopId;
            Shop shop = shopRepository.findById(shopId).orElseThrow(() -> new ResourceNotFoundException("Shop", "id", finalShopId));
            List<Doctor> doctorList = doctorRepository.findAllByShop(shop);
            doctors = new PageImpl<>(doctorList, pageable, doctorList.size());
        }

        List<Doctor> doctorList = doctors.getContent();

        List<DoctorDTO> content = doctorList.stream().map(doctor -> modelMapper.map(doctor, DoctorDTO.class)).collect(Collectors.toList());

        Response<DoctorDTO> response = new Response<>();
        response.setContent(content);
        response.setPageNo(doctors.getNumber());
        response.setPageSize(doctors.getSize());
        response.setTotalElements(doctors.getTotalElements());
        response.setTotalPages(doctors.getTotalPages());
        response.setLast(doctors.isLast());

        return response;
    }

    public DoctorDTO getById(long id) {
        Doctor doctor = doctorRepository.findDoctorById(id).orElseThrow(() -> new ResourceNotFoundException("Doctor", "id", id));
        return modelMapper.map(doctor, DoctorDTO.class);
    }

    public DoctorDTO update(DoctorSaveDTO doctorSaveDTO, long id) {
        Doctor doctor = doctorRepository.findDoctorById(id).orElseThrow(() -> new ResourceNotFoundException("Doctor", "id", id));
        doctor.setFirstName(doctorSaveDTO.getFirstName());
        doctor.setLastName(doctorSaveDTO.getLastName());
        doctor.setEmail(doctorSaveDTO.getEmail());
        doctor.setPassword(passwordEncoder.encode(doctorSaveDTO.getPassword()));
        Doctor updatedDoctor = doctorRepository.save(doctor);
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

    public void delete(long id) {
        Doctor doctor = doctorRepository.findDoctorById(id).orElseThrow(() -> new ResourceNotFoundException("Doctor", "id", id));
        doctor.setEnabled(false);
        doctorRepository.save(doctor);
    }
}
