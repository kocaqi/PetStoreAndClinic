package al.bytesquad.petstoreandclinic.controller;

import al.bytesquad.petstoreandclinic.payload.entityDTO.ManagerDTO;
import al.bytesquad.petstoreandclinic.payload.saveDTO.ManagerSaveDTO;
import al.bytesquad.petstoreandclinic.service.ManagerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/managers")
public class ManagerController {

    private final ManagerService managerService;

    @Autowired
    public ManagerController(ManagerService managerService) {
        this.managerService = managerService;
    }

    //create manager
    @PostMapping("/create")
    public ResponseEntity<ManagerDTO> create(@Valid @RequestBody String managerSaveDTO) throws JsonProcessingException {
        return new ResponseEntity<>(managerService.create(managerSaveDTO), HttpStatus.CREATED);
    }

    //get all managers
    @GetMapping
    @CrossOrigin(origins = "http://localhost:3000")
    public List<ManagerDTO> getAll(@RequestParam(required = false) String keyword, Principal principal) {
        return managerService.getAll(keyword, principal);
    }

    //search for managers
    /*@GetMapping("/search")
    public List<ManagerDTO> searchBy(@RequestParam String keyword) {
        return managerService.searchBy(keyword);
    }*/

    //get manager by id
    /*@GetMapping("/{id}")
    public ResponseEntity<ManagerDTO> getById(@PathVariable(name = "id") long id) {
        return new ResponseEntity<>(managerService.getById(id), HttpStatus.OK);
    }*/

    //update manager
    @PostMapping("/update/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<ManagerDTO> update(@Valid @RequestBody String managerSaveDTO, @PathVariable(name = "id") long id) throws JsonProcessingException {
        return new ResponseEntity<>(managerService.update(managerSaveDTO, id), HttpStatus.OK);
    }

    //"delete" manager
    @GetMapping("/remove/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public String delete(@PathVariable(name = "id") long id) {
        return managerService.delete(id);
    }

}
