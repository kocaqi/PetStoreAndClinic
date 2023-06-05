package al.bytesquad.petstoreandclinic.payload.entityDTO;

import al.bytesquad.petstoreandclinic.entity.Client;
import al.bytesquad.petstoreandclinic.entity.petAttributes.Breed;
import al.bytesquad.petstoreandclinic.entity.petAttributes.Gender;
import al.bytesquad.petstoreandclinic.entity.petAttributes.Species;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PetDTO {

    private Long id;
    private String name;
    private Client owner;
    private Species species;
    private Breed breed;
    private Gender gender;
    private LocalDate dateOfBirth;
    private String colour;

}
