package is.lab1.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authorize -> authorize
                .requestMatchers("admin/**").authenticated()  // Требует аутентификации для всех запросов по адресу /admin/*
                .anyRequest().permitAll()                      // Все остальные запросы доступны без аутентификации
            )
            .formLogin(formLogin -> formLogin
                .permitAll()                                   // Разрешить всем доступ к форме логина
            )
            .logout(logout -> logout
                .permitAll()                                   // Разрешить всем доступ к функции логаута
            );

        return http.build();
    }
}