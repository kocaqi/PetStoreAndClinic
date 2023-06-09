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
public class ClientService {

    private final ClientRepository clientRepository;
    private final ModelMapper modelMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public ClientService(ClientRepository clientRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder,
                         RoleRepository roleRepository,
                         UserRepository userRepository, ObjectMapper objectMapper) {
        this.clientRepository = clientRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;

        modelMapper.addMappings(new PropertyMap<Client, ClientDTO>() {
            @Override
            protected void configure() {
                map().setId(source.getId());
            }
        });
    }

    @Transactional
    public ClientDTO create(String jsonString) throws JsonProcessingException {
        ClientSaveDTO clientSaveDTO = objectMapper.readValue(jsonString, ClientSaveDTO.class);

        //convert DTO to entity
        Client client = modelMapper.map(clientSaveDTO, Client.class);
        client.setPassword(passwordEncoder.encode(clientSaveDTO.getPassword()));
        Role clientRole = roleRepository.findRoleByName("ROLE_CLIENT");
        client.setRole(clientRole);
        client.setEnabled(true);
        Client newClient = clientRepository.save(client);

        User user = new User();
        user.setFirstName(clientSaveDTO.getFirstName());
        user.setLastName(clientSaveDTO.getLastName());
        user.setEmail((clientSaveDTO.getEmail()));
        user.setPassword(passwordEncoder.encode(clientSaveDTO.getPassword()));
        user.setEnabled(true);
        List<Role> roles = new ArrayList<>();
        roles.add(clientRole);
        user.setRoles(roles);
        userRepository.save(user);

        //convert entity to DTO
        return modelMapper.map(newClient, ClientDTO.class);
    }

    public List<ClientDTO> getAll(String keyword) {
        if (keyword == null)
            return clientRepository.findAllByEnabled(true).stream().map(client -> modelMapper.map(client, ClientDTO.class)).collect(Collectors.toList());

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
        System.out.println(passwordEncoder.encode("albi"));
        return clients.stream().map(client -> modelMapper.map(client, ClientDTO.class)).collect(Collectors.toList());
    }

    public ClientDTO getById(long id) {
        Client client = clientRepository.findClientById(id).orElseThrow(() -> new ResourceNotFoundException("Client", "id", id));
        return modelMapper.map(client, ClientDTO.class);
    }

    @Transactional
    public ClientDTO update(String jsonString, long id) throws JsonProcessingException {
        ClientSaveDTO clientSaveDTO = objectMapper.readValue(jsonString, ClientSaveDTO.class);

        Client client = clientRepository.findClientById(id).orElseThrow(() -> new ResourceNotFoundException("Client", "id", id));

        String email = client.getEmail();
        String password = client.getPassword();
        client.setFirstName(clientSaveDTO.getFirstName());
        client.setLastName(clientSaveDTO.getLastName());
        client.setEmail(clientSaveDTO.getEmail());
        client.setPassword(passwordEncoder.encode(password));
        client.setEnabled(true);
        client.setOccupation(clientSaveDTO.getOccupation());
        client.setAddress(clientSaveDTO.getAddress());
        client.setCity(clientSaveDTO.getCity());
        client.setCountry(clientSaveDTO.getCountry());
        client.setPhone(clientSaveDTO.getPhone());
        client.setAbout(clientSaveDTO.getAbout());
        Client updatedClient = clientRepository.save(client);

        User user = userRepository.findByEmail(email);
        user.setFirstName(clientSaveDTO.getFirstName());
        user.setLastName(clientSaveDTO.getLastName());
        user.setEmail(clientSaveDTO.getEmail());
        user.setPassword(passwordEncoder.encode(password));
        user.setEnabled(true);
        user.setAddress(clientSaveDTO.getAddress());
        user.setCity(clientSaveDTO.getCity());
        user.setCountry(clientSaveDTO.getCountry());
        user.setPhone(clientSaveDTO.getPhone());
        user.setAbout(clientSaveDTO.getAbout());
        user.setSecondId(client.getId());
        userRepository.save(user);

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

    public String delete(long id) {
        Client client = clientRepository.findClientById(id).orElseThrow(() -> new ResourceNotFoundException("Client", "id", id));
        client.setEnabled(false);
        clientRepository.save(client);
        return "Client deleted successfully!";
    }
}
