package al.bytesquad.petstoreandclinic.controller;

import al.bytesquad.petstoreandclinic.payload.entityDTO.AdminDTO;
import al.bytesquad.petstoreandclinic.payload.saveDTO.AdminSaveDTO;
import al.bytesquad.petstoreandclinic.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/admins")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    //add new admin
    @PostMapping("/create")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<AdminDTO> create(@Valid @RequestBody AdminSaveDTO adminSaveDTO) {
        return new ResponseEntity<>(adminService.create(adminSaveDTO), HttpStatus.CREATED);
    }

    //get admins
    @GetMapping
    @CrossOrigin(origins = "http://localhost:3000")
    public List<AdminDTO> getAll(@RequestParam(required = false) String keyword) {
        return adminService.getAll(keyword);
    }

    /*//get admin by id
    @GetMapping("/{id}")
    public ResponseEntity<AdminDTO> getById(@PathVariable(name = "id") long id) {
        return new ResponseEntity<>(adminService.getById(id), HttpStatus.OK);
    }*/

    //update admin
    @PutMapping("/update/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<AdminDTO> update(@Valid @RequestBody AdminSaveDTO adminSaveDTO, @PathVariable(name = "id") long id) {
        return new ResponseEntity<>(adminService.update(adminSaveDTO, id), HttpStatus.OK);
    }

    /*//search admin
    @GetMapping("/search")
    public List<AdminDTO> searchBy(@RequestParam String keyword) {
        return adminService.searchBy(keyword);
    }*/

    //"delete" admin
    @PutMapping("/remove/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public void delete(@PathVariable(name = "id") long id) {
        adminService.delete(id);
    }
}
