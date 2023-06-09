package al.bytesquad.petstoreandclinic.controller;

import al.bytesquad.petstoreandclinic.payload.entityDTO.BillDTO;
import al.bytesquad.petstoreandclinic.service.BillService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/bills")
public class BillController {

    private final BillService billService;

    @Autowired
    public BillController(BillService billService) {
        this.billService = billService;
    }

    @GetMapping("/create")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<BillDTO> generate(@RequestParam long clientId, Principal principal) throws JsonProcessingException {
        return new ResponseEntity<>(billService.generate(clientId, principal), HttpStatus.CREATED);
    }
}
