package al.bytesquad.petstoreandclinic.payload.saveDTO;

import al.bytesquad.petstoreandclinic.entity.Manager;
import lombok.Data;

@Data
public class ShopSaveDTO {
    private String name;
    private String location;
    private Manager manager;
}
