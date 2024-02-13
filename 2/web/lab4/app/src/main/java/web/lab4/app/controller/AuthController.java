package web.lab4.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import web.lab4.app.component.JwtUtil;
import web.lab4.app.controller.exception.RegistrationFailException;
import web.lab4.app.controller.requests.AuthenticationRequest;
import web.lab4.app.controller.requests.AuthenticationResponse;
import web.lab4.app.service.AppUserDetailsService;

@RestController
@RequestMapping("api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private AppUserDetailsService userDetailsService;

    @Autowired
    private JwtUtil jwtTokenUtil;

    @PostMapping("status")
    public ResponseEntity<?> status() {
        return ResponseEntity.ok().build();
    }

    @PostMapping("register")
    public ResponseEntity<?> registerUser(@RequestBody AuthenticationRequest registrationRequest) throws RegistrationFailException {

        UserDetails newUser = userDetailsService.registerUser(registrationRequest);
        final String jwt = jwtTokenUtil.generateToken(newUser);
        return ResponseEntity.ok(new AuthenticationResponse(jwt, newUser.getUsername()));

    }

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) throws BadCredentialsException {

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(), authenticationRequest.getPassword()));
        
        final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
        
        final String jwt = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(new AuthenticationResponse(jwt, userDetails.getUsername()));
    }

}
