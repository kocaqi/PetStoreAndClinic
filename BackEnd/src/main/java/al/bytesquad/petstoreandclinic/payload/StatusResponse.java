package al.bytesquad.petstoreandclinic.payload;

import al.bytesquad.petstoreandclinic.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
public class StatusResponse {
    private String sessionId;
    private int status;
    private Long userId;
    private String message;
    private List<Role> roles;
    private Role role;
}
