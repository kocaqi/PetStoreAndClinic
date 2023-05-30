package al.bytesquad.petstoreandclinic.payload.saveDTO;

import al.bytesquad.petstoreandclinic.entity.Appointment;
import al.bytesquad.petstoreandclinic.entity.Shop;
import lombok.Data;

import java.util.List;

@Data
public class DoctorSaveDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Shop shop;
    private List<Appointment> appointments;

}
