package al.bytesquad.petstoreandclinic;

import al.bytesquad.petstoreandclinic.entity.Admin;
import al.bytesquad.petstoreandclinic.entity.Role;
import al.bytesquad.petstoreandclinic.entity.User;
import al.bytesquad.petstoreandclinic.repository.AdminRepository;
import al.bytesquad.petstoreandclinic.repository.ReceptionistRepository;
import al.bytesquad.petstoreandclinic.repository.RoleRepository;
import al.bytesquad.petstoreandclinic.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class PetStoreAndClinicApplication implements CommandLineRunner {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    public static void main(String[] args) {
        SpringApplication.run(PetStoreAndClinicApplication.class, args);
    }

    private final PasswordEncoder passwordEncoder;
    private final AdminRepository adminRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public PetStoreAndClinicApplication(PasswordEncoder passwordEncoder, AdminRepository adminRepository,
                                        UserRepository userRepository,
                                        RoleRepository roleRepository,
                                        ReceptionistRepository receptionistRepository) {
        this.passwordEncoder = passwordEncoder;
        this.adminRepository = adminRepository;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        Role adminRole = new Role("ROLE_ADMIN");
        roleRepository.save(adminRole);

        Role managerRole = new Role("ROLE_MANAGER");
        roleRepository.save(managerRole);

        Role doctorRole = new Role("ROLE_DOCTOR");
        roleRepository.save(doctorRole);

        Role receptionistRole = new Role("ROLE_RECEPTIONIST");
        roleRepository.save(receptionistRole);

        Role clientRole = new Role("ROLE_CLIENT");
        roleRepository.save(clientRole);

        User adminUser = new User();
        adminUser.setFirstName("Admin");
        adminUser.setLastName("Admin");
        adminUser.setEmail("admin@gmail.com");
        adminUser.setPassword(passwordEncoder.encode("admin"));
        List<Role> roles = new ArrayList<>();
        roles.add(adminRole);
        adminUser.setRoles(roles);
        userRepository.save(adminUser);

        Admin admin = new Admin();
        admin.setFirstName("Admin");
        admin.setLastName("Admin");
        admin.setEmail("admin@gmail.com");
        admin.setPassword(passwordEncoder.encode("admin"));
        admin.setRole(adminRole);
        adminRepository.save(admin);
    }
}
