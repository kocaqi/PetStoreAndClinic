package al.bytesquad.petstoreandclinic.service;

import al.bytesquad.petstoreandclinic.repository.ReceptionistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReceptionistService {

    private final ReceptionistRepository receptionistRepository;

    @Autowired
    public ReceptionistService(ReceptionistRepository receptionistRepository) {
        this.receptionistRepository = receptionistRepository;
    }
}
