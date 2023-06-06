package al.bytesquad.petstoreandclinic.controller;

import al.bytesquad.petstoreandclinic.payload.entityDTO.FeedbackDTO;
import al.bytesquad.petstoreandclinic.payload.saveDTO.FeedbackSaveDTO;
import al.bytesquad.petstoreandclinic.service.FeedbackService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @PostMapping("/leaveFeedback")
    public ResponseEntity<FeedbackDTO> leaveFeedback(@Valid @RequestBody FeedbackSaveDTO feedbackSaveDTO, Principal principal) {
        return new ResponseEntity<>(feedbackService.create(feedbackSaveDTO, principal), HttpStatus.CREATED);
    }

    @GetMapping("/")
    public List<FeedbackDTO> getAll(@RequestParam String keyword, Principal principal) {
        return feedbackService.getAll(keyword, principal);
    }

    /*@GetMapping("/{id}")
    public ResponseEntity<FeedbackDTO> getById(@PathVariable(name = "id") long id) {
        return new ResponseEntity<>(feedbackService.getById(id), HttpStatus.OK);
    }*/

    @PutMapping("/delete/{id}")
    public void delete(@PathVariable(name = "id") long id) {
        feedbackService.delete(id);
    }
}
