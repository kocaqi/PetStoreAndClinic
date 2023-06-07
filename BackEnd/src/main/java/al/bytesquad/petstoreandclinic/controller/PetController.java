package al.bytesquad.petstoreandclinic.controller;

import al.bytesquad.petstoreandclinic.payload.entityDTO.PetDTO;
import al.bytesquad.petstoreandclinic.payload.saveDTO.PetSaveDTO;
import al.bytesquad.petstoreandclinic.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
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
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<PetDTO> create(@Valid @RequestBody PetSaveDTO petSaveDTO) {
        return new ResponseEntity<>(petService.create(petSaveDTO), HttpStatus.CREATED);
    }

    //get all pets
    @GetMapping
    @CrossOrigin(origins = "http://localhost:3000")
    public List<PetDTO> getAll(@RequestParam(required = false) String keyword, Principal principal) {
        return petService.getAll(keyword, principal);
    }

    //search for pets
    /*@GetMapping("/search")
    public List<PetDTO> searchBy(@RequestParam String keyword) {
        return petService.searchBy(keyword);
    }*/

    //get pet by id
    /*@GetMapping("/{id}")
    public ResponseEntity<PetDTO> getById(@PathVariable(name = "id") long id) {
        return new ResponseEntity<>(petService.getById(id), HttpStatus.OK);
    }*/

    //update pet
    @PutMapping("/update/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<PetDTO> update(@Valid @RequestBody PetSaveDTO petSaveDTO, @PathVariable(name = "id") long id) {
        return new ResponseEntity<>(petService.update(petSaveDTO, id), HttpStatus.OK);
    }

    //"delete" pet
    @PutMapping("/remove/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public void delete(@PathVariable(name = "id") long id) {
        petService.delete(id);
    }
}
