package web.lab4.app.controller.requests;

import lombok.Data;
import lombok.NoArgsConstructor;
import web.lab4.app.model.AppUser;


@Data
@NoArgsConstructor
public class AppUserDto {
    private Long id;
    private String username;


    public AppUserDto(AppUser appUser) {
        this.id = appUser.getId();
        this.username = appUser.getUsername();
    }

}
