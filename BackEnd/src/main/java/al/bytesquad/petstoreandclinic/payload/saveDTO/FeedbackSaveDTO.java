package al.bytesquad.petstoreandclinic.payload.saveDTO;

import al.bytesquad.petstoreandclinic.entity.Doctor;
import al.bytesquad.petstoreandclinic.entity.Shop;
import lombok.Data;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotNull;

@Data
public class FeedbackSaveDTO {
    @Nullable
    private Shop shop;
    @Nullable
    private Doctor doctor;
    @NotNull
    private String message;
}
