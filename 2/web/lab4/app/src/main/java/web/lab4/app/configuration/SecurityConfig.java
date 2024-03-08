package web.lab4.app.configuration;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;

import org.springframework.web.cors.CorsConfigurationSource;

import web.lab4.app.component.CustomAuthenticationEntryPoint;



@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    public CorsConfigurationSource configurationSource;
    
    @Autowired
    public CustomAuthenticationEntryPoint entryPoint;

    @Bean
    public JwtDecoder jwtDecoder() {

        String jwkSetUri = "http://localhost:29886/realms/lab4-app/protocol/openid-connect/certs";

        return NimbusJwtDecoder.withJwkSetUri(jwkSetUri).build();
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, @Autowired JwtDecoder jwtDecoder) throws Exception {
        http.cors(cors -> cors.configurationSource(configurationSource))
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authorizeHttpRequests -> authorizeHttpRequests.requestMatchers("/api/main/**", "/api/auth/status").authenticated().anyRequest().permitAll())
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .exceptionHandling(exception -> exception.authenticationEntryPoint(entryPoint))
            .oauth2Login(oauth -> oauth.defaultSuccessUrl("/main"))
            .oauth2ResourceServer(oauth2 -> oauth2.jwt(jwt -> jwt.decoder(jwtDecoder)));
        return http.build();
    }

   

}