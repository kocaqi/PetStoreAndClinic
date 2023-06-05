package al.bytesquad.petstoreandclinic.service;

import al.bytesquad.petstoreandclinic.payload.Response;
import al.bytesquad.petstoreandclinic.payload.entityDTO.PetDTO;
import al.bytesquad.petstoreandclinic.payload.saveDTO.PetSaveDTO;
import al.bytesquad.petstoreandclinic.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {

    private final PetRepository petRepository;

    @Autowired
    public PetService(PetRepository petRepository) {
        this.petRepository = petRepository;
    }

    public PetDTO create(PetSaveDTO petSaveDTO) {
        return null;
    }

    public Response<PetDTO> getAll(int pageNo, int pageSize, String sortBy, String sortDir) {
        return null;
    }

    public PetDTO getById(long id) {
        return null;
    }

    public PetDTO update(PetSaveDTO petSaveDTO, long id) {
        return null;
    }

    public List<PetDTO> searchBy(String keyword) {
        return null;
    }

    public void delete(long id) {
    }
}
