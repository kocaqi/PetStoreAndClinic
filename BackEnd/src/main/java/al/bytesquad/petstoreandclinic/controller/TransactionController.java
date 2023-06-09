package al.bytesquad.petstoreandclinic.controller;

import al.bytesquad.petstoreandclinic.payload.entityDTO.TransactionDTO;
import al.bytesquad.petstoreandclinic.service.TransactionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping("/create")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<TransactionDTO> create(@Valid @RequestBody String transactionSaveDTO, Principal principal) throws JsonProcessingException {
        return new ResponseEntity<>(transactionService.create(transactionSaveDTO, principal), HttpStatus.CREATED);
    }

    @GetMapping
    @CrossOrigin(origins = "http://localhost:3000")
    public List<TransactionDTO> getAll(@RequestParam(required = false) String keyword, Principal principal) {
        return transactionService.getAll(keyword, principal);
    }

    @GetMapping("/remove/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public String delete(@PathVariable(name = "id") long id) {
        return transactionService.delete(id);
    }
}
