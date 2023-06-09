package al.bytesquad.petstoreandclinic.controller;

import al.bytesquad.petstoreandclinic.payload.entityDTO.AdminDTO;
import al.bytesquad.petstoreandclinic.service.AdminService;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/admins")
@CrossOrigin(origins = "http://localhost:3000")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    //add new admin
    @PostMapping("/create")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<AdminDTO> create(@Valid @RequestBody String adminSaveDTO/*, HttpServletRequest request*/) throws JsonProcessingException {
        /*RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("Access-Control-Allow-Origin", "http://localhost:3000");

        HttpEntity<AdminSaveDTO> requestEntity = new HttpEntity<>(adminSaveDTO, headers);

        ResponseEntity<AdminDTO> response = restTemplate.exchange(
                request.getRequestURL().toString(),
                HttpMethod.POST,
                requestEntity,
                AdminDTO.class
        );

        // Handle the response
        if (response.getStatusCode().is2xxSuccessful()) {
            AdminDTO responseBody = response.getBody();
            System.out.println("Response Body: " + responseBody);
            return new ResponseEntity<>(adminService.create(adminSaveDTO), HttpStatus.CREATED);
        }*/

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
    @PostMapping("/update/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<AdminDTO> update(@Valid @RequestBody String adminSaveDTO, @PathVariable(name = "id") long id) throws JsonProcessingException {
        return new ResponseEntity<>(adminService.update(adminSaveDTO, id), HttpStatus.OK);
    }

    /*//search admin
    @GetMapping("/search")
    public List<AdminDTO> searchBy(@RequestParam String keyword) {
        return adminService.searchBy(keyword);
    }*/

    //"delete" admin
    @GetMapping("/remove/{id}")
    @CrossOrigin(origins = "http://localhost:3000")
    public String delete(@PathVariable(name = "id") long id) {
        return adminService.delete(id);
    }
}
