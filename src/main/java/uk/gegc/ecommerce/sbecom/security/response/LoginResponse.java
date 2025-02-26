package uk.gegc.ecommerce.sbecom.security.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class LoginResponse {
//    public Long id;
    private String username;
    private List<String> roles;
    private String jwtToken;

}
