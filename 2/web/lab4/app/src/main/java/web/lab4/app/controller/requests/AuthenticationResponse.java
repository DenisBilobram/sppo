package web.lab4.app.controller.requests;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthenticationResponse {
  private final String jwt;
  private final String username;
}

