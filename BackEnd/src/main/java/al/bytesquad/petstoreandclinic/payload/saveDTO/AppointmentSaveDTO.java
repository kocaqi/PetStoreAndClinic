package al.bytesquad.petstoreandclinic.payload.saveDTO;

import al.bytesquad.petstoreandclinic.entity.Doctor;
import al.bytesquad.petstoreandclinic.entity.Pet;
import lombok.Data;

import java.util.Date;

@Data
public class AppointmentSaveDTO {

    private Long petId;
    private Long doctorId;
    private Long clientId;
    private String startTime;
    private String finishTime;

}
