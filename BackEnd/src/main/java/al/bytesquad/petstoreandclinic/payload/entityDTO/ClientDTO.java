package al.bytesquad.petstoreandclinic.payload.entityDTO;

import al.bytesquad.petstoreandclinic.entity.Appointment;
import al.bytesquad.petstoreandclinic.entity.Bill;
import al.bytesquad.petstoreandclinic.entity.Feedback;
import al.bytesquad.petstoreandclinic.entity.Pet;
import lombok.Data;

import java.util.List;

@Data
public class ClientDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private List<Pet> pets;
    private List<Bill> bills;
    private List<Appointment> appointments;
    private List<Feedback> feedbacks;

}
