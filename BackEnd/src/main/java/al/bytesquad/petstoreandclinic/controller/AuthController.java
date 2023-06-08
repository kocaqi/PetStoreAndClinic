package al.bytesquad.petstoreandclinic.controller;

import al.bytesquad.petstoreandclinic.payload.JWTAuthResponse;
import al.bytesquad.petstoreandclinic.payload.LoginDTO;
import al.bytesquad.petstoreandclinic.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<JWTAuthResponse> authenticateUser(@RequestBody LoginDTO loginDTO) throws Exception{
        return authService.authenticate(loginDTO);
    }

    @GetMapping("/refreshToken")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<JWTAuthResponse> refreshToken() {
        return authService.refreshToken();
    }

}
