package al.bytesquad.petstoreandclinic.service;

import al.bytesquad.petstoreandclinic.entity.*;
import al.bytesquad.petstoreandclinic.payload.Response;
import al.bytesquad.petstoreandclinic.payload.entityDTO.ClientDTO;
import al.bytesquad.petstoreandclinic.payload.entityDTO.DoctorDTO;
import al.bytesquad.petstoreandclinic.payload.entityDTO.FeedbackDTO;
import al.bytesquad.petstoreandclinic.payload.entityDTO.ManagerDTO;
import al.bytesquad.petstoreandclinic.payload.saveDTO.ManagerSaveDTO;
import al.bytesquad.petstoreandclinic.repository.ManagerRepository;
import al.bytesquad.petstoreandclinic.repository.RoleRepository;
import al.bytesquad.petstoreandclinic.repository.UserRepository;
import al.bytesquad.petstoreandclinic.search.MySpecification;
import al.bytesquad.petstoreandclinic.search.SearchCriteria;
import al.bytesquad.petstoreandclinic.service.exception.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ManagerService {

    private final ManagerRepository managerRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Autowired
    public ManagerService(ManagerRepository managerRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder,
                          RoleRepository roleRepository, UserRepository userRepository) {
        this.managerRepository = managerRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    public ManagerDTO create(ManagerSaveDTO managerSaveDTO) {
        Manager manager = modelMapper.map(managerSaveDTO, Manager.class);
        manager.setPassword(passwordEncoder.encode(managerSaveDTO.getPassword()));
        Role managerRole = roleRepository.findRoleByName("ROLE_MANAGER");
        manager.setRole(managerRole);
        Manager newManager = managerRepository.save(manager);

        User user = new User();
        user.setFirstName(managerSaveDTO.getFirstName());
        user.setLastName(managerSaveDTO.getLastName());
        user.setEmail(managerSaveDTO.getEmail());
        user.setPassword(passwordEncoder.encode(managerSaveDTO.getPassword()));
        List<Role> roles = new ArrayList<>();
        roles.add(managerRole);
        user.setRoles(roles);
        userRepository.save(user);

        return modelMapper.map(newManager, ManagerDTO.class);
    }

    public Response<ManagerDTO> getAll(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Manager> managers = managerRepository.findAll(pageable);

        List<Manager> managerList = managers.getContent();

        List<ManagerDTO> content = managerList.stream().map(manager -> modelMapper.map(manager, ManagerDTO.class)).collect(Collectors.toList());

        Response<ManagerDTO> response = new Response<>();
        response.setContent(content);
        response.setPageNo(managers.getNumber());
        response.setPageSize(managers.getSize());
        response.setTotalElements(managers.getTotalElements());
        response.setTotalPages(managers.getTotalPages());
        response.setLast(managers.isLast());

        return response;
    }

    public ManagerDTO getById(long id) {
        Manager manager = managerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Manager", "id", id));
        return modelMapper.map(manager, ManagerDTO.class);
    }

    public ManagerDTO update(ManagerSaveDTO managerSaveDTO, long id) {
        Manager manager = managerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Manager", "id", id));
        manager.setFirstName(managerSaveDTO.getFirstName());
        manager.setLastName(managerSaveDTO.getLastName());
        manager.setEmail(managerSaveDTO.getEmail());
        manager.setPassword(passwordEncoder.encode(managerSaveDTO.getPassword()));
        Manager updated = managerRepository.save(manager);
        return modelMapper.map(updated, ManagerDTO.class);
    }

    public void delete(long id) {
        Manager manager = managerRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Manager", "id", id));
        manager.setEnabled(false);
        managerRepository.save(manager);
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
