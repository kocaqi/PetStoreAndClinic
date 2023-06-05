package al.bytesquad.petstoreandclinic.controller;

import al.bytesquad.petstoreandclinic.payload.Response;
import al.bytesquad.petstoreandclinic.payload.entityDTO.ManagerDTO;
import al.bytesquad.petstoreandclinic.payload.entityDTO.PetDTO;
import al.bytesquad.petstoreandclinic.payload.saveDTO.ManagerSaveDTO;
import al.bytesquad.petstoreandclinic.payload.saveDTO.PetSaveDTO;
import al.bytesquad.petstoreandclinic.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/pets")
public class PetController {

    private final PetService petService;

    @Autowired
    public PetController(PetService petService) {
        this.petService = petService;
    }

    //create pet
    @PostMapping("/create")
    public ResponseEntity<PetDTO> create(@Valid @RequestBody PetSaveDTO petSaveDTO) {
        return new ResponseEntity<>(petService.create(petSaveDTO), HttpStatus.CREATED);
    }

    //get all pets
    @GetMapping
    public Response<PetDTO> getAll(@RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
                                       @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
                                       @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
                                       @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
        return petService.getAll(pageNo, pageSize, sortBy, sortDir);
    }

    //search for pets
    @GetMapping("/search")
    public List<PetDTO> searchBy(@RequestParam String keyword) {
        return petService.searchBy(keyword);
    }

    //get pet by id
    @GetMapping("/{id}")
    public ResponseEntity<PetDTO> getById(@PathVariable(name = "id") long id) {
        return new ResponseEntity<>(petService.getById(id), HttpStatus.OK);
    }

    //update pet
    @PutMapping("/update/{id}")
    public ResponseEntity<PetDTO> update(@Valid @RequestBody PetSaveDTO petSaveDTO, @PathVariable(name = "id") long id) {
        return new ResponseEntity<>(petService.update(petSaveDTO, id), HttpStatus.OK);
    }

    //"delete" pet
    @PutMapping("/delete/{id}")
    public void delete(@PathVariable(name = "id") long id) {
        petService.delete(id);
    }
}
