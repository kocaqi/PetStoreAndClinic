package al.bytesquad.petstoreandclinic.payload.saveDTO;

import al.bytesquad.petstoreandclinic.entity.Doctor;
import al.bytesquad.petstoreandclinic.entity.Pet;
import lombok.Data;

import java.util.Date;

@Data
public class AppointmentSaveDTO {

    private Pet pet;
    private Doctor doctor;
    private Date startTime;
    private Date finishTime;

}
