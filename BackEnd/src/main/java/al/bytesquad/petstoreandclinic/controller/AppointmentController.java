package al.bytesquad.petstoreandclinic.controller;

import al.bytesquad.petstoreandclinic.payload.entityDTO.AppointmentDTO;
import al.bytesquad.petstoreandclinic.payload.saveDTO.AppointmentSaveDTO;
import al.bytesquad.petstoreandclinic.service.AppointmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    //get all appointments
    @GetMapping
    @CrossOrigin(origins = "http://localhost:3000")
    public List<AppointmentDTO> getAll(@RequestParam(required = false) String keyword, Principal principal) {
        return appointmentService.getAll(keyword, principal);
    }

    //book appointment
    @PostMapping("/create")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<AppointmentDTO> book(@Valid @RequestBody AppointmentSaveDTO appointmentSaveDTO, Principal principal) {
        return new ResponseEntity<>(appointmentService.book(appointmentSaveDTO, principal), HttpStatus.CREATED);
    }

    //delete appointment
    @DeleteMapping("/remove/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public void delete(@PathVariable(name = "id") long id, Principal principal) {
        appointmentService.delete(id, principal);
    }
}
