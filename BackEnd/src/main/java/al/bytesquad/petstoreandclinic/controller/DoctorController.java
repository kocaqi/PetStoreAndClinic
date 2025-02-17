package al.bytesquad.petstoreandclinic.controller;

import al.bytesquad.petstoreandclinic.payload.entityDTO.DoctorDTO;
import al.bytesquad.petstoreandclinic.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping("/doctors")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class DoctorController {
    private final DoctorService doctorService;

    @PostMapping("/create")
    public ResponseEntity<DoctorDTO> create(@Valid @RequestBody String doctorSaveDTO) {
        return ResponseEntity.status(CREATED)
                .body(doctorService.create(doctorSaveDTO));
    }

    @GetMapping
    public List<DoctorDTO> getAll(@RequestParam(required = false) String keyword, 
                                 Principal principal) {
        return doctorService.getAll(keyword, principal);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<DoctorDTO> update(@Valid @RequestBody String doctorSaveDTO, 
                                          @PathVariable long id) {
        return ResponseEntity.ok(doctorService.update(doctorSaveDTO, id));
    }

    @GetMapping("/remove/{id}")
    public String delete(@PathVariable long id) {
        return doctorService.delete(id);
    }
}
