package web.lab4.app.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = {"/main", "/auth", "/auth/logout"})
public class AngularController {
    
    @GetMapping()
    public String toMain() {
        return "forward:/";
    }

}
