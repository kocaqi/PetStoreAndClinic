package al.bytesquad.petstoreandclinic.payload.entityDTO;

import al.bytesquad.petstoreandclinic.entity.Manager;
import lombok.Data;

@Data
public class ShopDTO {
    private Long id;
    private String name;
    private String location;
    private Manager manager;
}
