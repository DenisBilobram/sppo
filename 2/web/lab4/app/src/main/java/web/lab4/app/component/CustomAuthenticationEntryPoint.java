package web.lab4.app.component;

import java.io.IOException;
import java.io.PrintWriter;


import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import org.springframework.security.core.AuthenticationException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); 
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter writer = response.getWriter();
        writer.print("{\"redirectUrl\": \"http://localhost:29886/realms/lab4-app/protocol/openid-connect/auth?client_id=lab4&response_type=code&scope=openid&redirect_uri=http://localhost:29885/auth/token\"}");
        writer.flush();
        writer.close();
        System.out.println("Added redirectUrl for unlogined req");
    }
}





