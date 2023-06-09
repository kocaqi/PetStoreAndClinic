package al.bytesquad.petstoreandclinic.payload.saveDTO;

import al.bytesquad.petstoreandclinic.entity.Client;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PetSaveDTO {

    private String name;
    private Long clientId;
    private String species;
    private String breed;
    private String gender;
    private LocalDate dateOfBirth;
    private String colour;

}
