package web.lab4.app.component;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RegistrationAspect {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Before("execution(* web.lab4.app.controller.AuthController.registerUser(..))")
    public void logBefore(JoinPoint joinPoint) {
        log.info("Executing method: {}", joinPoint.getSignature());
    }

    @After("execution(* web.lab4.app.controller.AuthController.registerUser(..))")
    public void logAfter(JoinPoint joinPoint) {
        log.info("Method executed: {}", joinPoint.getSignature());
    }
}