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

        Role adminRole = roleRepository.findRoleByName("ROLE_ADMIN");
        if (adminRole == null)
            roleRepository.save(new Role("ROLE_ADMIN"));

        Role managerRole = roleRepository.findRoleByName("ROLE_MANAGER");
        if (managerRole == null)
            roleRepository.save(new Role("ROLE_MANAGER"));

        Role doctorRole = roleRepository.findRoleByName("ROLE_DOCTOR");
        if (doctorRole == null)
            roleRepository.save(new Role("ROLE_DOCTOR"));

        Role receptionistRole = roleRepository.findRoleByName("ROLE_RECEPTIONIST");
        if (receptionistRole == null)
            roleRepository.save(new Role("ROLE_RECEPTIONIST"));

        Role clientRole = roleRepository.findRoleByName("ROLE_CLIENT");
        if (clientRole == null)
            roleRepository.save(new Role("ROLE_CLIENT"));


        User adminUser = userRepository.findByEmail("admin@gmail.com");
        if (adminUser == null) {
            adminUser = new User();
            adminUser.setFirstName("Admin");
            adminUser.setLastName("Admin");
            adminUser.setEmail("admin@gmail.com");
            adminUser.setPassword(passwordEncoder.encode("admin"));
            List<Role> roles = new ArrayList<>();
            roles.add(adminRole);
            adminUser.setRoles(roles);
            userRepository.save(adminUser);
        }

        Admin admin = adminRepository.findAdminByEmail("admin@gmail.com");
        if (admin == null) {
            admin = new Admin();
            admin.setFirstName("Admin");
            admin.setLastName("Admin");
            admin.setEmail("admin@gmail.com");
            admin.setPassword(passwordEncoder.encode("admin"));
            admin.setRole(adminRole);
            adminRepository.save(admin);
        }
    }
}
