package al.bytesquad.petstoreandclinic.service;

import al.bytesquad.petstoreandclinic.entity.Admin;
import al.bytesquad.petstoreandclinic.entity.Role;
import al.bytesquad.petstoreandclinic.payload.entityDTO.AdminDTO;
import al.bytesquad.petstoreandclinic.payload.entityDTO.RoleDTO;
import al.bytesquad.petstoreandclinic.repository.RoleRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {

    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public RoleService(RoleRepository roleRepository, ModelMapper modelMapper) {
        this.roleRepository = roleRepository;
        this.modelMapper = modelMapper;

        modelMapper.addMappings(new PropertyMap<Role, RoleDTO>() {
            @Override
            protected void configure() {
                map().setId(source.getId());
            }
        });
    }

    public RoleDTO create(String roleName) {
        Role role = new Role();
        roleName = "ROLE_"+roleName.toUpperCase();
        role.setName(roleName);
        return modelMapper.map(role, RoleDTO.class);
    }
}
