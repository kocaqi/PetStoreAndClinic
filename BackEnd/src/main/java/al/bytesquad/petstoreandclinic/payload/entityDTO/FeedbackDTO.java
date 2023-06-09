package al.bytesquad.petstoreandclinic.payload.entityDTO;

import al.bytesquad.petstoreandclinic.entity.Client;
import al.bytesquad.petstoreandclinic.entity.Doctor;
import al.bytesquad.petstoreandclinic.entity.Shop;
import lombok.Data;

@Data
public class FeedbackDTO {

    private Long id;
    private Client client;
    private Shop shop;
    private Doctor doctor;
    private String title;
    private String message;

}
