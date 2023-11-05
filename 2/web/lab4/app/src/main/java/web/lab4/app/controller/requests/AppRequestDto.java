package web.lab4.app.controller.requests;

import java.sql.Timestamp;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import web.lab4.app.model.AppRequest;


@Data
@NoArgsConstructor
public class AppRequestDto {
    
    @NotNull
    @Min(value = -3, message = "Значение X должно быть больше или равно -3.")
    @Max(value = 5, message = "Значение X должно быть меньше или равно 5.")
    private Double x;

    @NotNull
    @Min(value = -5, message = "Значение Y должно быть больше или равно -5.")
    @Max(value = 3, message = "Значение Y должно быть меньше или равно 3.")
    private Double y;
    
    @NotNull
    @Min(value = 1, message = "Значение R должно быть больше 0.")
    @Max(value = 5, message = "Значение R должно быть меньше или равно 5.")
    private Double r;
    private Boolean result;
    private Timestamp timestamp;
    private AppUserDto owner;


    public AppRequestDto(AppRequest appRequest) {
        this.x = appRequest.getX();
        this.y = appRequest.getY();
        this.r = appRequest.getR();
        this.timestamp = appRequest.getTime();
        this.result = appRequest.getResult();
        this.owner = new AppUserDto(appRequest.getOwner());
    }

}
