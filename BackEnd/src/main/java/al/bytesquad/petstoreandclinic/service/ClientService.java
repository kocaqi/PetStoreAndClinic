package al.bytesquad.petstoreandclinic.service;

import al.bytesquad.petstoreandclinic.entity.Client;
import al.bytesquad.petstoreandclinic.payload.Response;
import al.bytesquad.petstoreandclinic.payload.entityDTO.ClientDTO;
import al.bytesquad.petstoreandclinic.payload.saveDTO.ClientSaveDTO;
import al.bytesquad.petstoreandclinic.repository.ClientRepository;
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
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClientService {

    private final ClientRepository clientRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ClientService(ClientRepository clientRepository, ModelMapper modelMapper) {
        this.clientRepository = clientRepository;
        this.modelMapper = modelMapper;
    }

    public ClientDTO create(ClientSaveDTO clientSaveDTO) {
        //convert DTO to entity
        Client client = modelMapper.map(clientSaveDTO, Client.class);
        Client newClient = clientRepository.save(client);

        //convert entity to DTO
        return modelMapper.map(newClient, ClientDTO.class);
    }

    public Response<ClientDTO> getAll(int pageNo, int pageSize, String sortBy, String sortDir) {

        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Client> clients = clientRepository.findAll(pageable);

        List<Client> clientList = clients.getContent();

        List<ClientDTO> content = clientList.stream().map(client -> modelMapper.map(client, ClientDTO.class)).collect(Collectors.toList());

        Response<ClientDTO> postResponse = new Response<>();
        postResponse.setContent(content);
        postResponse.setPageNo(clients.getNumber());
        postResponse.setPageSize(clients.getSize());
        postResponse.setTotalElements(clients.getTotalElements());
        postResponse.setTotalPages(clients.getTotalPages());
        postResponse.setLast(clients.isLast());

        return postResponse;
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
