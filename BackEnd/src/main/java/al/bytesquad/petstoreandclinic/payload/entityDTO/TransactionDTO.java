package al.bytesquad.petstoreandclinic.payload.entityDTO;

import al.bytesquad.petstoreandclinic.entity.Bill;
import al.bytesquad.petstoreandclinic.entity.Client;
import al.bytesquad.petstoreandclinic.entity.Pet;
import al.bytesquad.petstoreandclinic.entity.User;
import lombok.Data;

@Data
public class TransactionDTO {
    private Long id;
    private Bill bill;
    private Client client;
    private User user;
    private Long productId;
    private String productName;
    private double pricePerUnit;
    private double quantity;
    private double total;
    private Pet pet;
    private boolean isPaid;
}
