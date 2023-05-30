package al.bytesquad.petstoreandclinic.payload.entityDTO;

import al.bytesquad.petstoreandclinic.entity.Client;
import al.bytesquad.petstoreandclinic.entity.Doctor;
import al.bytesquad.petstoreandclinic.entity.Pet;
import lombok.Data;

import java.util.Date;

@Data
public class AppointmentDTO {

    private long id;
    private Client client;
    private Pet pet;
    private Doctor doctor;
    private Date startTime;
    private Date finishTime;

}
