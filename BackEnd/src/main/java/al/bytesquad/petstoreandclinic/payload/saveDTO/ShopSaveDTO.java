package al.bytesquad.petstoreandclinic.payload.saveDTO;

import lombok.Data;
import org.springframework.lang.Nullable;

@Data
public class ShopSaveDTO {
    private String name;
    private String address;
    private String city;
    private String country;
    @Nullable
    private Long managerId;
}
