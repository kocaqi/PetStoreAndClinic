package al.bytesquad.petstoreandclinic.payload.saveDTO;

import lombok.Data;

@Data
public class ClientSaveDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String password;

}
