package al.bytesquad.petstoreandclinic.controller;

import al.bytesquad.petstoreandclinic.payload.entityDTO.ReceptionistDTO;
import al.bytesquad.petstoreandclinic.payload.saveDTO.ReceptionistSaveDTO;
import al.bytesquad.petstoreandclinic.service.ReceptionistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/receptionists")
public class ReceptionistController {

    private final ReceptionistService receptionistService;

    @Autowired
    public ReceptionistController(ReceptionistService receptionistService) {
        this.receptionistService = receptionistService;
    }

    @PostMapping("/create")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<ReceptionistDTO> addNew(@Valid @RequestBody ReceptionistSaveDTO receptionistSaveDTO) {
        return new ResponseEntity<>(receptionistService.create(receptionistSaveDTO), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<ReceptionistDTO> update(@Valid @RequestBody ReceptionistSaveDTO receptionistSaveDTO, @PathVariable(name = "id") long id) {
        return new ResponseEntity<>(receptionistService.update(receptionistSaveDTO, id), HttpStatus.OK);
    }

    @PutMapping("/remove/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public void delete(@PathVariable(name = "id") long id) {
        receptionistService.delete(id);
    }

    @GetMapping
    @CrossOrigin(origins = "http://localhost:3000")
    public List<ReceptionistDTO> get(@RequestParam(required = false) String keyword, Principal principal) {
        return receptionistService.get(keyword, principal);
    }
}
