package al.bytesquad.petstoreandclinic.controller;

import al.bytesquad.petstoreandclinic.payload.entityDTO.ClientDTO;
import al.bytesquad.petstoreandclinic.payload.saveDTO.ClientSaveDTO;
import al.bytesquad.petstoreandclinic.service.ClientService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {

    private final ClientService clientService;

    @Autowired
    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    //create client
    @PostMapping("/create")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<ClientDTO> create(@Valid @RequestBody String clientSaveDTO) throws JsonProcessingException {
        return new ResponseEntity<>(clientService.create(clientSaveDTO), HttpStatus.CREATED);
    }

    //get all clients
    @GetMapping
    @CrossOrigin(origins = "http://localhost:3000")
    public List<ClientDTO> getAll(@RequestParam(required = false) String keyword) {
        return clientService.getAll(keyword);
    }

    /*@GetMapping("/search")
    public List<ClientDTO> searchBy(@RequestParam String keyword) {
        return clientService.searchBy(keyword);
    }*/

    //get client by id
    /*@GetMapping("/{id}")
    public ResponseEntity<ClientDTO> getById(@PathVariable(name = "id") long id) {
        return new ResponseEntity<>(clientService.getById(id), HttpStatus.OK);
    }*/

    //update client
    @PutMapping("/update/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<ClientDTO> update(@Valid @RequestBody String clientSaveDTO, @PathVariable(name = "id") long id) throws JsonProcessingException {
        return new ResponseEntity<>(clientService.update(clientSaveDTO, id), HttpStatus.OK);
    }

}
