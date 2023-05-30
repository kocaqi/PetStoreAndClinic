package al.bytesquad.petstoreandclinic.payload.entityDTO;

import al.bytesquad.petstoreandclinic.entity.Role;
import lombok.Data;

@Data
public class AdminDTO {

    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private Role role;

}
