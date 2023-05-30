package al.bytesquad.petstoreandclinic.controller;

import al.bytesquad.petstoreandclinic.payload.Response;
import al.bytesquad.petstoreandclinic.payload.entityDTO.DoctorDTO;
import al.bytesquad.petstoreandclinic.payload.entityDTO.ManagerDTO;
import al.bytesquad.petstoreandclinic.payload.saveDTO.DoctorSaveDTO;
import al.bytesquad.petstoreandclinic.payload.saveDTO.ManagerSaveDTO;
import al.bytesquad.petstoreandclinic.service.ManagerService;
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
    public ResponseEntity<ManagerDTO> create(@Valid @RequestBody ManagerSaveDTO managerSaveDTO){
        return new ResponseEntity<>(managerService.create(managerSaveDTO), HttpStatus.CREATED);
    }

    //get all managers
    @GetMapping
    public Response<ManagerDTO> getAll(@RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
                                      @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
                                      @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
                                      @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir){
        return managerService.getAll(pageNo, pageSize, sortBy, sortDir);
    }

    //search for managers
    @GetMapping("/search")
    public List<ManagerDTO> searchBy(@RequestParam String keyword){
        return managerService.searchBy(keyword);
    }

    //get manager by id
    @GetMapping("/{id}")
    public ResponseEntity<ManagerDTO> getById(@PathVariable(name = "id") long id){
        return new ResponseEntity<>(managerService.getById(id), HttpStatus.OK);
    }

    //update manager
    @PutMapping("/update/{id}")
    public ResponseEntity<ManagerDTO> update(@Valid @RequestBody ManagerSaveDTO managerSaveDTO, @PathVariable(name = "id") long id){
        return new ResponseEntity<>(managerService.update(managerSaveDTO, id), HttpStatus.OK);
    }

    //"delete" manager
    @PutMapping("/delete/{id}")
    public void delete(@PathVariable(name = "id") long id){
        managerService.delete(id);
    }

}
