package al.bytesquad.petstoreandclinic.controller;

import al.bytesquad.petstoreandclinic.payload.Response;
import al.bytesquad.petstoreandclinic.payload.entityDTO.AppointmentDTO;
import al.bytesquad.petstoreandclinic.payload.saveDTO.AppointmentSaveDTO;
import al.bytesquad.petstoreandclinic.service.AppointmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {

    private final AppointmentService appointmentService;

    public AppointmentController(AppointmentService appointmentService) {
        this.appointmentService = appointmentService;
    }

    //get all my appointments
    @GetMapping("/")
    public Response<AppointmentDTO> getAll(@RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
                                           @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
                                           @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
                                           @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir) {
        return appointmentService.getAll(pageNo, pageSize, sortBy, sortDir);
    }

    //book appointment
    @PostMapping("/bookAppointment")
    public ResponseEntity<AppointmentDTO> book(@Valid @RequestBody AppointmentSaveDTO appointmentSaveDTO, Principal principal) {
        return new ResponseEntity<>(appointmentService.book(appointmentSaveDTO, principal), HttpStatus.CREATED);
    }

    //delete appointment
    @DeleteMapping("/deleteAppointment/{id}")
    public void delete(@PathVariable(name = "id") long id, Principal principal) {
        appointmentService.delete(id, principal);
    }
}
