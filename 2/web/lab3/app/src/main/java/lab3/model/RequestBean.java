package lab3.model;

import java.io.Serializable;
import java.util.Date;

import javax.inject.Named;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Named
@Entity
public class RequestBean implements Serializable {

    @Id
    private Long id;

    private Double x;
    private Double y;
    private Double r;
    private Date time;
    private Boolean result;

    public RequestBean(Double x, Double y, Double r, Date time, Boolean result) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.time = time;
        this.result = result;
    }

    public RequestBean() {
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }


    public void setY(Double y) {
        this.y = y;
    }

    public Double getR() {
        return r;
    }

    public void setR(Double r) {
        this.r = r;
    }

    public String getTime() {
        return this.time.toString();
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }
    
}
