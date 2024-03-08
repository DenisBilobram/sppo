package web.lab4.app.component;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Aspect
@Component
@Slf4j
public class RegistrationAspect {

    @Before("execution(* web.lab4.app.controller.AuthController.registerUser(..))")
    public void logBefore(JoinPoint joinPoint) {
        log.info("Executing method: {}", joinPoint.getSignature());
    }

    @After("execution(* web.lab4.app.controller.AuthController.registerUser(..))")
    public void logAfter(JoinPoint joinPoint) {
        log.info("Method executed: {}", joinPoint.getSignature());
    }
}