package al.bytesquad.petstoreandclinic.payload.saveDTO;

import lombok.Data;

@Data
public class ProductSaveDTO {
    private String name;
    private double pricePerUnit;
    private double stock;
    private String type;
}
