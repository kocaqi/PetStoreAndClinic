package al.bytesquad.petstoreandclinic.controller;

import al.bytesquad.petstoreandclinic.payload.Response;
import al.bytesquad.petstoreandclinic.payload.entityDTO.ClientDTO;
import al.bytesquad.petstoreandclinic.payload.entityDTO.DoctorDTO;
import al.bytesquad.petstoreandclinic.payload.saveDTO.ClientSaveDTO;
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
    public ResponseEntity<DoctorDTO> create(@Valid @RequestBody DoctorSaveDTO doctorSaveDTO){
        return new ResponseEntity<>(doctorService.create(doctorSaveDTO), HttpStatus.CREATED);
    }

    //get all doctors
    @GetMapping
    public Response<DoctorDTO> getAll(@RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
                                      @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
                                      @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
                                      @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir,
                                      @RequestParam(value = "shopId", defaultValue = "-1", required = false) long shopId,
                                      Principal principal){
        return doctorService.getAll(pageNo, pageSize, sortBy, sortDir, shopId, principal);
    }

    @GetMapping("/search")
    public List<DoctorDTO> searchBy(@RequestParam String keyword){
        return doctorService.searchBy(keyword);
    }

    //get client by id
    @GetMapping("/{id}")
    public ResponseEntity<DoctorDTO> getById(@PathVariable(name = "id") long id){
        return new ResponseEntity<>(doctorService.getById(id), HttpStatus.OK);
    }

    //update client
    @PutMapping("/update/{id}")
    public ResponseEntity<DoctorDTO> update(@Valid @RequestBody DoctorSaveDTO doctorSaveDTO, @PathVariable(name = "id") long id){
        return new ResponseEntity<>(doctorService.update(doctorSaveDTO, id), HttpStatus.OK);
    }

    //"delete" doctor
    @PutMapping("/delete/{id}")
    public void delete(@PathVariable(name = "id") long id){
        doctorService.delete(id);
    }

}
