package al.bytesquad.petstoreandclinic.controller;

import al.bytesquad.petstoreandclinic.payload.entityDTO.RoleDTO;
import al.bytesquad.petstoreandclinic.service.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/roles")
public class RoleController {

    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("/create")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<RoleDTO> addNew(@RequestBody String roleName) {
        return new ResponseEntity<>(roleService.create(roleName), HttpStatus.CREATED);
    }
}
