package al.bytesquad.petstoreandclinic.service;

import al.bytesquad.petstoreandclinic.entity.*;
import al.bytesquad.petstoreandclinic.payload.JWTAuthResponse;
import al.bytesquad.petstoreandclinic.payload.LoginDTO;
import al.bytesquad.petstoreandclinic.payload.StatusResponse;
import al.bytesquad.petstoreandclinic.repository.*;
import al.bytesquad.petstoreandclinic.secuity.JWTProvider;
import al.bytesquad.petstoreandclinic.service.exception.ResourceNotFoundException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JWTProvider provider;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final SessionRepository sessionRepository;
    private final ObjectMapper objectMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final AdminRepository adminRepository;
    private final ManagerRepository managerRepository;
    private final DoctorRepository doctorRepository;
    private final ReceptionistRepository receptionistRepository;
    private final ClientRepository clientRepository;

    public AuthService(AuthenticationManager authenticationManager, JWTProvider provider,
                       UserRepository userRepository, RoleRepository roleRepository,
                       SessionRepository sessionRepository, ObjectMapper objectMapper,
                       AdminRepository adminRepository,
                       ManagerRepository managerRepository,
                       DoctorRepository doctorRepository,
                       ReceptionistRepository receptionistRepository,
                       ClientRepository clientRepository) {
        this.authenticationManager = authenticationManager;
        this.provider = provider;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.sessionRepository = sessionRepository;
        this.objectMapper = objectMapper;
        this.passwordEncoder = new BCryptPasswordEncoder();
        this.adminRepository = adminRepository;
        this.managerRepository = managerRepository;
        this.doctorRepository = doctorRepository;
        this.receptionistRepository = receptionistRepository;
        this.clientRepository = clientRepository;
    }

    public ResponseEntity<StatusResponse> authenticate(String jsonString) throws Exception {
        LoginDTO loginDTO = objectMapper.readValue(jsonString, LoginDTO.class);

        Role adminRole = roleRepository.findRoleByName("ROLE_ADMIN");
        Role managerRole = roleRepository.findRoleByName("ROLE_MANAGER");
        Role doctorRole = roleRepository.findRoleByName("ROLE_DOCTOR");
        Role receptionistRole = roleRepository.findRoleByName("ROLE_RECEPTIONIST");
        Role clientRole = roleRepository.findRoleByName("ROLE_CLIENT");

        String email = loginDTO.getEmail();
        User user = userRepository.findByEmail(email);
        if (user == null)
            return ResponseEntity.ok(new StatusResponse(null, 404, null, "Email not found!", null, null));
        if(!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword()))
            return ResponseEntity.ok(new StatusResponse(null, 404, user.getSecondId(), "Incorrect password!", null, null));
        Long roleId = loginDTO.getRoleId();
        //Role role = roleRepository.findById(roleId).orElseThrow(() -> new ResourceNotFoundException("Role", "id", roleId));
        if (roleId == null) {
            //List<Role> roles = user.getRoles();
            List<Role> roles = new ArrayList<>();
            /*List<Admin> admins = adminRepository.findAll();
            for(Admin a : admins){
                if(a.getEmail().equals(loginDTO.getEmail())){
                    roles.add(adminRole);
                    break;
                }
            }
            List<Manager> managers = managerRepository.findAll();
            for(Manager a : managers){
                if(a.getEmail().equals(loginDTO.getEmail())){
                    roles.add(managerRole);
                    break;
                }
            }
            List<Doctor> doctors = doctorRepository.findAll();
            for(Doctor a : doctors){
                if(a.getEmail().equals(loginDTO.getEmail())){
                    roles.add(doctorRole);
                    break;
                }
            }
            List<Receptionist> receptionists = receptionistRepository.findAll();
            for(Receptionist a : receptionists){
                if(a.getEmail().equals(loginDTO.getEmail())){
                    roles.add(receptionistRole);
                    break;
                }
            }
            List<Client> clients = clientRepository.findAll();
            for(Client a : clients){
                if(a.getEmail().equals(loginDTO.getEmail())){
                    roles.add(clientRole);
                    break;
                }
            }*/
            Admin admin = adminRepository.findAdminByEmail(loginDTO.getEmail());
            if(admin!=null)
                roles.add(adminRole);
            System.out.println(roles);
            Manager manager = managerRepository.findByEmail(loginDTO.getEmail());
            if(manager!=null)
                roles.add(managerRole);
            Doctor doctor = doctorRepository.findByEmail(loginDTO.getEmail());
            if(doctor!=null)
                roles.add(doctorRole);
            Receptionist receptionist = receptionistRepository.findByEmail(loginDTO.getEmail());
            if(receptionist != null)
                roles.add(receptionistRole);
            Client client = clientRepository.findByEmail(loginDTO.getEmail());
            if(client != null)
                roles.add(clientRole);

            if (roles.size() > 1) {
                System.out.println("more than 1...");
                return ResponseEntity.ok(new StatusResponse(null, 300, user.getSecondId(), "More than 1 role!", roles, null));
            } else {
                Session session = new Session();
                //session.setId(UUID.randomUUID());
                session.setSessionId(UUID.randomUUID().toString());
                session.setRole(roles.get(0));
                session.setUser(user);
                sessionRepository.save(session);
                /*Authentication authentication;
                try {
                    authentication = authenticationManager.authenticate(
                            new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
                } catch (DisabledException e) {
                    throw new Exception("USER_DISABLED", e);
                } catch (BadCredentialsException e) {
                    throw new Exception("INVALID_CREDENTIALS", e);
                }
                String accessToken = provider.generateToken(authentication);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                System.out.println(accessToken);*/
                return ResponseEntity.ok(new StatusResponse(session.getSessionId(), 200, user.getSecondId(), "Single role!", null, session.getRole()));
            }
        } else {
            Session session = new Session();
            session.setSessionId(UUID.randomUUID().toString());
            session.setRole(roleRepository.findById(loginDTO.getRoleId()).orElseThrow(() -> new ResourceNotFoundException("Role", "id", loginDTO.getRoleId())));
            session.setUser(user);
            sessionRepository.save(session);
            /*Authentication authentication;
            try {
                authentication = authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
            } catch (DisabledException e) {
                throw new Exception("USER_DISABLED", e);
            } catch (BadCredentialsException e) {
                throw new Exception("INVALID_CREDENTIALS", e);
            }
            String accessToken = provider.generateToken(authentication);
            SecurityContextHolder.getContext().setAuthentication(authentication);*/
            return ResponseEntity.ok(new StatusResponse(session.getSessionId(), 200, user.getSecondId(), "Single role!", null, session.getRole()));
        }
        /*Authentication authentication;
        try {
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
        String accessToken = provider.generateToken(authentication);
        String refreshToken = provider.generateRefreshToken(authentication);
        return ResponseEntity.ok(new JWTAuthResponse(accessToken, refreshToken));*/
    }

    public ResponseEntity<JWTAuthResponse> refreshToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String accessToken = provider.generateToken(authentication);
        String refreshToken = provider.generateRefreshToken(authentication);
        return ResponseEntity.ok(new JWTAuthResponse(accessToken, refreshToken));
    }

    public ResponseEntity<StatusResponse> auth(String sessionId) {
        Session session = sessionRepository.findSessionBySessionId(sessionId);
        if(session == null)
            return ResponseEntity.ok(new StatusResponse(null, 404, null, "Logout!", null, null));
        return ResponseEntity.ok(new StatusResponse(session.getSessionId(), 200, session.getUser().getSecondId(), "Success!", null, session.getRole()));
    }
}
