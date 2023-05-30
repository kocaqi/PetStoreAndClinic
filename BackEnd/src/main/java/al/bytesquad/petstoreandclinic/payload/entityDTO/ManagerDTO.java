package al.bytesquad.petstoreandclinic.payload.entityDTO;

import al.bytesquad.petstoreandclinic.entity.Shop;
import lombok.Data;

@Data
public class ManagerDTO {

    private long id;
    private String firstName;
    private String lastName;
    private String email;
    private Shop shop;

}
