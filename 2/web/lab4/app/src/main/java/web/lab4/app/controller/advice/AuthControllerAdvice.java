package web.lab4.app.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import web.lab4.app.controller.AuthController;
import web.lab4.app.controller.exception.RegistrationFailException;

@ControllerAdvice(assignableTypes = {AuthController.class})
public class AuthControllerAdvice {
    
    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handleBadCredentialsException(BadCredentialsException exp) {
        return "Пользователя с такими (username, password) не существует.";
    }

    @ExceptionHandler(RegistrationFailException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handleRegistrationFailException(RegistrationFailException exp) {
        return "Не удалось создать пользователя.";
    }

}
