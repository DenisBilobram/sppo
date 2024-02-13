package web.lab4.app.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import web.lab4.app.controller.exception.RegistrationFailException;
import web.lab4.app.controller.requests.AuthenticationRequest;
import web.lab4.app.model.AppUser;
import web.lab4.app.repository.UserRepository;

@Service
public class AppUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser appUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new User(appUser.getUsername(), appUser.getPassword(), new ArrayList<>());
    }

    public UserDetails registerUser(AuthenticationRequest registrationRequest) throws RegistrationFailException {

        if (userRepository.findByUsername(registrationRequest.getUsername()).isPresent()) {
            throw new RegistrationFailException("User already exists");
        }

        AppUser newUser = new AppUser();
        newUser.setUsername(registrationRequest.getUsername());
        newUser.setPassword(passwordEncoder.encode(registrationRequest.getPassword())); 

        userRepository.save(newUser);

        return new User(newUser.getUsername(), newUser.getPassword(), new ArrayList<>());
    }
}
