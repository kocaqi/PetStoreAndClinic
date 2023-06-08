package al.bytesquad.petstoreandclinic.payload;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JWTAuthResponse {

    private String accessToken;
    private String refreshToken;
    private String tokenType;

    public JWTAuthResponse(String accessToken, String refreshToken) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.tokenType = "Bearer";
    }
}

