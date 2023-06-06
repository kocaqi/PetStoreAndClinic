package al.bytesquad.petstoreandclinic.payload.entityDTO;

import al.bytesquad.petstoreandclinic.entity.productAttributes.Type;
import lombok.Data;

@Data
public class ProductDTO {
    private Long id;
    private String name;
    private double pricePerUnit;
    private double stock;
    private Type type;
}
