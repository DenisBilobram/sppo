package web.lab4.app.model;

import java.util.List;

import lombok.Data;

@Data

public class AppUser {


    private Long id;
    private String username;
    private String password;

    private List<AppRequest> requests;

}