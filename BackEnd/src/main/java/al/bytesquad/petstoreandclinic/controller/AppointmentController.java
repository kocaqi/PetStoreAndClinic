package al.bytesquad.petstoreandclinic.controller;

import al.bytesquad.petstoreandclinic.payload.entityDTO.AppointmentDTO;
import al.bytesquad.petstoreandclinic.service.AppointmentService;
import com.fasterxml.jackson.core.JsonProcessingException;
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
    public ResponseEntity<AppointmentDTO> book(@Valid @RequestBody String appointmentSaveDTO, Principal principal) throws JsonProcessingException {
        return new ResponseEntity<>(appointmentService.book(appointmentSaveDTO, principal), HttpStatus.CREATED);
    }

    //delete appointment
    @GetMapping("/remove/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public String delete(@PathVariable(name = "id") long id, Principal principal) {
        return appointmentService.delete(id, principal);
    }
}
