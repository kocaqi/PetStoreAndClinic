package al.bytesquad.petstoreandclinic.payload;

import lombok.Data;

@Data
public class LoginDTO {
    private String email;
    private String password;
    private Long roleId;
}
