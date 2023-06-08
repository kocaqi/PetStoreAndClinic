package al.bytesquad.petstoreandclinic.service;

import al.bytesquad.petstoreandclinic.payload.JWTAuthResponse;
import al.bytesquad.petstoreandclinic.payload.LoginDTO;
import al.bytesquad.petstoreandclinic.secuity.JWTProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JWTProvider provider;

    public AuthService(AuthenticationManager authenticationManager, JWTProvider provider) {
        this.authenticationManager = authenticationManager;
        this.provider = provider;
    }

    public ResponseEntity<JWTAuthResponse> authenticate(LoginDTO loginDTO) throws Exception {
        Authentication authentication;
        try{
            authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
        String accessToken = provider.generateToken(authentication);
        String refreshToken = provider.generateRefreshToken(authentication);
        return ResponseEntity.ok(new JWTAuthResponse(accessToken, refreshToken));
    }

    public ResponseEntity<JWTAuthResponse> refreshToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String accessToken = provider.generateToken(authentication);
        String refreshToken = provider.generateRefreshToken(authentication);
        return ResponseEntity.ok(new JWTAuthResponse(accessToken, refreshToken));
    }
}
