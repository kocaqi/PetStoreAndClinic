package al.bytesquad.petstoreandclinic.payload.saveDTO;

import al.bytesquad.petstoreandclinic.entity.Role;
import al.bytesquad.petstoreandclinic.entity.Shop;
import lombok.Data;

@Data
public class ReceptionistSaveDTO {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private Role role;
    private Long shopId;
    private String address;
    private String city;
    private String country;
    private String phone;
    private String about;
}
