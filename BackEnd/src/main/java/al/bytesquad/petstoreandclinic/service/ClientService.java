package al.bytesquad.petstoreandclinic.service;

import al.bytesquad.petstoreandclinic.entity.Client;
import al.bytesquad.petstoreandclinic.entity.Role;
import al.bytesquad.petstoreandclinic.entity.User;
import al.bytesquad.petstoreandclinic.payload.entityDTO.ClientDTO;
import al.bytesquad.petstoreandclinic.payload.saveDTO.ClientSaveDTO;
import al.bytesquad.petstoreandclinic.repository.ClientRepository;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Autowired
    public ClientService(ClientRepository clientRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder,
                         RoleRepository roleRepository,
                         UserRepository userRepository) {
        this.clientRepository = clientRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public ClientDTO create(ClientSaveDTO clientSaveDTO) {
        //convert DTO to entity
        Client client = modelMapper.map(clientSaveDTO, Client.class);
        client.setPassword(passwordEncoder.encode(clientSaveDTO.getPassword()));
        Role clientRole = roleRepository.findRoleByName("ROLE_CLIENT");
        client.setRole(clientRole);
        Client newClient = clientRepository.save(client);

        User user = new User();
        user.setFirstName(clientSaveDTO.getFirstName());
        user.setLastName(clientSaveDTO.getLastName());
        user.setEmail((clientSaveDTO.getEmail()));
        user.setPassword(passwordEncoder.encode(clientSaveDTO.getPassword()));
        List<Role> roles = new ArrayList<>();
        roles.add(clientRole);
        user.setRoles(roles);
        userRepository.save(user);

        //convert entity to DTO
        return modelMapper.map(newClient, ClientDTO.class);
    }

    public List<ClientDTO> getAll(String keyword) {
        List<String> keyValues = List.of(keyword.split(","));
        HashMap<String, String> pairs = new HashMap<>();
        for (String s : keyValues) {
            String[] strings = s.split(":");
            pairs.put(strings[0], strings[1]);
        }

        List<Client> clients = clientRepository.findAll((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            for (String key : pairs.keySet()) {
                Path<Object> fieldPath = root.get(key);
                predicates.add(criteriaBuilder.equal(fieldPath, pairs.get(key)));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });

        return clients.stream().map(client -> modelMapper.map(client, ClientDTO.class)).collect(Collectors.toList());
    }

    public ClientDTO getById(long id) {
        Client client = clientRepository.findClientById(id).orElseThrow(() -> new ResourceNotFoundException("Client", "id", id));
        return modelMapper.map(client, ClientDTO.class);
    }

    public ClientDTO update(ClientSaveDTO clientSaveDTO, long id) {
        Client client = clientRepository.findClientById(id).orElseThrow(() -> new ResourceNotFoundException("Client", "id", id));
        client.setFirstName(clientSaveDTO.getFirstName());
        client.setLastName(clientSaveDTO.getLastName());
        client.setEmail(clientSaveDTO.getEmail());
        client.setPassword(passwordEncoder.encode(clientSaveDTO.getPassword()));
        Client updatedClient = clientRepository.save(client);
        return modelMapper.map(updatedClient, ClientDTO.class);
    }

    public List<ClientDTO> searchBy(String keyword) {
        MySpecification<Client> specifyByFirstName = new MySpecification<>(new SearchCriteria("firstName", ":", keyword));
        MySpecification<Client> specifyByLastName = new MySpecification<>(new SearchCriteria("lastName", ":", keyword));
        MySpecification<Client> specifyByEmail = new MySpecification<>(new SearchCriteria("email", ":", keyword));

        List<Client> clients = clientRepository.findAll(Specification
                .where(specifyByFirstName).and(specifyByLastName).and(specifyByEmail));

        return clients.stream().map(client -> modelMapper.map(client, ClientDTO.class)).collect(Collectors.toList());
    }
}
