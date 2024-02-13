package web.lab4.app.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import jakarta.validation.ValidationException;
import web.lab4.app.controller.MainController;

@ControllerAdvice(assignableTypes = {MainController.class})
public class MainControllerAdvice {
    
    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public String handleValidationException(ValidationException exp) {
        return exp.getMessage();
    }


}
