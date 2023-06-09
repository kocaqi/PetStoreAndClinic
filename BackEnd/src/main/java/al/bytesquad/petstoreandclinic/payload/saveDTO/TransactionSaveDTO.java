package al.bytesquad.petstoreandclinic.payload.saveDTO;

import al.bytesquad.petstoreandclinic.entity.Client;
import al.bytesquad.petstoreandclinic.entity.Pet;
import al.bytesquad.petstoreandclinic.entity.Product;
import lombok.Data;

@Data
public class TransactionSaveDTO {
    private Long clientId;
    private Long productId;
    private Long petId;
    private double quantity;
}
