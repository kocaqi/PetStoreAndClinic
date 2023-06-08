package al.bytesquad.petstoreandclinic.service;

import al.bytesquad.petstoreandclinic.entity.Appointment;
import al.bytesquad.petstoreandclinic.entity.Client;
import al.bytesquad.petstoreandclinic.entity.Role;
import al.bytesquad.petstoreandclinic.entity.User;
import al.bytesquad.petstoreandclinic.payload.entityDTO.AppointmentDTO;
import al.bytesquad.petstoreandclinic.payload.saveDTO.AppointmentSaveDTO;
import al.bytesquad.petstoreandclinic.repository.*;
import al.bytesquad.petstoreandclinic.service.exception.ResourceNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final ModelMapper modelMapper;
    private final ClientRepository clientRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final DoctorRepository doctorRepository;
    private final ManagerRepository managerRepository;
    private final ObjectMapper objectMapper;

    public AppointmentService(AppointmentRepository appointmentRepository, ModelMapper modelMapper,
                              ClientRepository clientRepository,
                              UserRepository userRepository,
                              RoleRepository roleRepository,
                              DoctorRepository doctorRepository,
                              ManagerRepository managerRepository, ObjectMapper objectMapper) {
        this.appointmentRepository = appointmentRepository;
        this.modelMapper = modelMapper;
        this.clientRepository = clientRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.doctorRepository = doctorRepository;
        this.managerRepository = managerRepository;
        this.objectMapper = objectMapper;

        modelMapper.addMappings(new PropertyMap<Appointment, AppointmentDTO>() {
            @Override
            protected void configure() {
                map().setId(source.getId());
            }
        });
    }

    public AppointmentDTO book(String jsonString, Principal principal) throws JsonProcessingException {
        AppointmentSaveDTO appointmentSaveDTO = objectMapper.readValue(jsonString, AppointmentSaveDTO.class);

        Appointment appointment = modelMapper.map(appointmentSaveDTO, Appointment.class);
        String email = principal.getName();
        Client client = clientRepository.findByEmail(email);
        appointment.setClient(client);
        Appointment newAppointment = appointmentRepository.save(appointment);
        return modelMapper.map(newAppointment, AppointmentDTO.class);
    }

    public String delete(long id, Principal principal) {
        String clientEmail = principal.getName();
        Appointment appointment = appointmentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Appointment", "id", id));
        if (clientEmail.equals(appointment.getClient().getEmail())) {
            appointmentRepository.delete(appointment);
            return "Appointment deleted successfully!";
        }
        return "Cannot delete appointment!";
    }

    public List<AppointmentDTO> getAll(String keyword, Principal principal) {
        if (keyword == null)
            return appointmentRepository.findAll().stream().map(appointment -> modelMapper.map(appointment, AppointmentDTO.class)).collect(Collectors.toList());

        List<String> keyValues = List.of(keyword.split(","));
        HashMap<String, String> pairs = new HashMap<>();
        for (String s : keyValues) {
            String[] strings = s.split(":");
            pairs.put(strings[0], strings[1]);
        }

        List<Appointment> appointments = appointmentRepository.findAll((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            for (String key : pairs.keySet()) {
                Path<Object> fieldPath = root.get(key);
                predicates.add(criteriaBuilder.equal(fieldPath, pairs.get(key)));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        });

        List<Appointment> filteredList;

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String loggedInEmail = principal.getName();
        User user = userRepository.findByEmail(loggedInEmail);
        List<String> userRoles = user.getRoles().stream()
                .map(Role::getName).toList();

        String selectedRole = null;

        if (authentication != null && userRoles.contains(authentication.getAuthorities().iterator().next().getAuthority())) {
            selectedRole = authentication.getAuthorities().iterator().next().getAuthority();
            // Assuming the authority is in the format "ROLE_{ROLE_NAME}"
            selectedRole = selectedRole.substring("ROLE_".length()).toLowerCase();
        }

        if ("client".equals(selectedRole)) {
            filteredList = appointments.stream()
                    .filter(appointment -> appointment.getClient().equals(clientRepository.findByEmail(loggedInEmail)))
                    .collect(Collectors.toList());
        } else if ("receptionist".equals(selectedRole)) {
            filteredList = null;
        } else if ("doctor".equals(selectedRole)) {
            filteredList = appointments.stream()
                    .filter(appointment -> appointment.getDoctor().equals(doctorRepository.findByEmail(loggedInEmail)))
                    .collect(Collectors.toList());
        } else if ("manager".equals(selectedRole)) {
            filteredList = appointments.stream()
                    .filter(appointment -> appointment.getDoctor().getShop().equals(managerRepository.findByEmail(loggedInEmail).getShop()))
                    .collect(Collectors.toList());
        } else {
            filteredList = appointments;
        }

        if (filteredList == null)
            return null;
        return filteredList.stream().map(appointment -> modelMapper.map(appointment, AppointmentDTO.class)).collect(Collectors.toList());
    }
}
