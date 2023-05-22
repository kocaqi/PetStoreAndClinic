package al.bytesquad.petstoreandclinic.service;

import al.bytesquad.petstoreandclinic.entity.Admin;
import al.bytesquad.petstoreandclinic.payload.entityDTO.AdminDTO;
import al.bytesquad.petstoreandclinic.payload.saveDTO.AdminSaveDTO;
import al.bytesquad.petstoreandclinic.repository.AdminRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
    private final AdminRepository adminRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AdminService(AdminRepository adminRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
    }

    public AdminDTO create(AdminSaveDTO adminSaveDTO) {
        Admin admin = modelMapper.map(adminSaveDTO, Admin.class);
        admin.setPassword(passwordEncoder.encode(adminSaveDTO.getPassword()));
        Admin newAdmin = adminRepository.save(admin);
        return modelMapper.map(newAdmin, AdminDTO.class);
    }
}
