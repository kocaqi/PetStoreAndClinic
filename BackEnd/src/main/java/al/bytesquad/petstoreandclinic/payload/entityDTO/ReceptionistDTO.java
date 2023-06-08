package al.bytesquad.petstoreandclinic.payload.entityDTO;

import al.bytesquad.petstoreandclinic.entity.Role;
import al.bytesquad.petstoreandclinic.entity.Shop;
import lombok.Data;

@Data
public class ReceptionistDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;
    private Shop shop;
}
