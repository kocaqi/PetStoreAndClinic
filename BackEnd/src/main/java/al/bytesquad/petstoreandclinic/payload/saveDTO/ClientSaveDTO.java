package al.bytesquad.petstoreandclinic.payload.saveDTO;

import lombok.Data;

@Data
public class ClientSaveDTO {

    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String occupation;
    private String address;
    private String city;
    private String country;
    private String phone;
    private String about;

}
