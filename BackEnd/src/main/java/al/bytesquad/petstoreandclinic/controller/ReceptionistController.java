package al.bytesquad.petstoreandclinic.controller;

import al.bytesquad.petstoreandclinic.service.ReceptionistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/receptionists")
public class ReceptionistController {

    private final ReceptionistService receptionistService;

    @Autowired
    public ReceptionistController(ReceptionistService receptionistService) {
        this.receptionistService = receptionistService;
    }
}
