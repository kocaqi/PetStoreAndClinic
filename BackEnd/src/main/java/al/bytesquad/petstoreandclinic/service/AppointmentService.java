package al.bytesquad.petstoreandclinic.service;

import al.bytesquad.petstoreandclinic.entity.Appointment;
import al.bytesquad.petstoreandclinic.entity.Client;
import al.bytesquad.petstoreandclinic.payload.Response;
import al.bytesquad.petstoreandclinic.payload.entityDTO.AppointmentDTO;
import al.bytesquad.petstoreandclinic.payload.saveDTO.AppointmentSaveDTO;
import al.bytesquad.petstoreandclinic.repository.AppointmentRepository;
import al.bytesquad.petstoreandclinic.repository.ClientRepository;
import al.bytesquad.petstoreandclinic.service.exception.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final ModelMapper modelMapper;
    private final ClientRepository clientRepository;

    public AppointmentService(AppointmentRepository appointmentRepository, ModelMapper modelMapper,
                              ClientRepository clientRepository) {
        this.appointmentRepository = appointmentRepository;
        this.modelMapper = modelMapper;
        this.clientRepository = clientRepository;
    }

    public AppointmentDTO book(AppointmentSaveDTO appointmentSaveDTO, Principal principal) {
        Appointment appointment = modelMapper.map(appointmentSaveDTO, Appointment.class);
        String email = principal.getName();
        Client client = clientRepository.findByEmail(email);
        appointment.setClient(client);
        Appointment newAppointment = appointmentRepository.save(appointment);
        return modelMapper.map(newAppointment, AppointmentDTO.class);
    }

    public void delete(long id, Principal principal) {
        String clientEmail = principal.getName();
        Appointment appointment = appointmentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Appointment", "id", id));
        if(clientEmail.equals(appointment.getClient().getEmail())){
            appointmentRepository.delete(appointment);
        }
    }

    public Response<AppointmentDTO> getAll(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);
        Page<Appointment> appointments = appointmentRepository.findAll(pageable);

        List<Appointment> appointmentList = appointments.getContent();

        List<AppointmentDTO> content = appointmentList.stream().map(appointment -> modelMapper.map(appointment, AppointmentDTO.class)).toList();

        Response<AppointmentDTO> response = new Response<>();
        response.setContent(content);
        response.setPageNo(appointments.getNumber());
        response.setPageSize(appointments.getSize());
        response.setTotalElements(appointments.getTotalElements());
        response.setTotalPages(appointments.getTotalPages());
        response.setLast(appointments.isLast());

        return response;
    }
}
