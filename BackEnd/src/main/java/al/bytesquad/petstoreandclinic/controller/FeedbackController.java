package al.bytesquad.petstoreandclinic.controller;

import al.bytesquad.petstoreandclinic.payload.Response;
import al.bytesquad.petstoreandclinic.payload.entityDTO.DoctorDTO;
import al.bytesquad.petstoreandclinic.payload.entityDTO.FeedbackDTO;
import al.bytesquad.petstoreandclinic.payload.saveDTO.FeedbackSaveDTO;
import al.bytesquad.petstoreandclinic.service.FeedbackService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    private final FeedbackService feedbackService;

    public FeedbackController(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @PostMapping("/leaveFeedback")
    public ResponseEntity<FeedbackDTO> leaveFeedback(@Valid @RequestBody FeedbackSaveDTO feedbackSaveDTO, Principal principal){
        return new ResponseEntity<>(feedbackService.create(feedbackSaveDTO, principal), HttpStatus.CREATED);
    }

    @GetMapping("/")
    public Response<FeedbackDTO> getAll(@RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
                                      @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
                                      @RequestParam(value = "sortBy", defaultValue = "id", required = false) String sortBy,
                                      @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir,
                                      @RequestParam(value = "shopId", defaultValue = "-1", required = false) long shopId,
                                      Principal principal){
        return feedbackService.getAll(pageNo, pageSize, sortBy, sortDir, shopId, principal);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FeedbackDTO> getById(@PathVariable(name = "id") long id){
        return new ResponseEntity<>(feedbackService.getById(id), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable(name = "id") long id){
        feedbackService.delete(id);
    }
}
