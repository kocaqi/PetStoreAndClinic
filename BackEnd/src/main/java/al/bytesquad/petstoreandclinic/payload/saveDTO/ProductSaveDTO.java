package al.bytesquad.petstoreandclinic.payload.saveDTO;

import al.bytesquad.petstoreandclinic.entity.productAttributes.Type;
import lombok.Data;

@Data
public class ProductSaveDTO {
    private String name;
    private double pricePerUnit;
    private double stock;
    private Type type;
}
