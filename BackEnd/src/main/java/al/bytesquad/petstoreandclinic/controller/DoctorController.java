package al.bytesquad.petstoreandclinic.controller;

import al.bytesquad.petstoreandclinic.payload.entityDTO.DoctorDTO;
import al.bytesquad.petstoreandclinic.payload.saveDTO.DoctorSaveDTO;
import al.bytesquad.petstoreandclinic.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    private final DoctorService doctorService;

    @Autowired
    public DoctorController(DoctorService doctorService) {
        this.doctorService = doctorService;
    }


    //create doctor
    @PostMapping("/create")
    public ResponseEntity<DoctorDTO> create(@Valid @RequestBody DoctorSaveDTO doctorSaveDTO) {
        return new ResponseEntity<>(doctorService.create(doctorSaveDTO), HttpStatus.CREATED);
    }

    //get all doctors
    @GetMapping
    public List<DoctorDTO> getAll(@RequestParam String keyword, Principal principal) {
        return doctorService.getAll(keyword, principal);
    }

    /*@GetMapping("/search")
    public List<DoctorDTO> searchBy(@RequestParam String keyword, Principal principal) {
        return doctorService.searchBy(keyword, principal);
    }*/

    //get doctor by id
    /*@GetMapping("/{id}")
    public ResponseEntity<DoctorDTO> getById(@PathVariable(name = "id") long id) {
        return new ResponseEntity<>(doctorService.getById(id), HttpStatus.OK);
    }*/

    //update doctor
    @PutMapping("/update/{id}")
    public ResponseEntity<DoctorDTO> update(@Valid @RequestBody DoctorSaveDTO doctorSaveDTO, @PathVariable(name = "id") long id) {
        return new ResponseEntity<>(doctorService.update(doctorSaveDTO, id), HttpStatus.OK);
    }

    //"delete" doctor
    @PutMapping("/delete/{id}")
    public void delete(@PathVariable(name = "id") long id) {
        doctorService.delete(id);
    }

}
