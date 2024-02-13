package web.lab4.app.controller.requests;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenticationRequest {
  
  private String username;
  private String password;

}

