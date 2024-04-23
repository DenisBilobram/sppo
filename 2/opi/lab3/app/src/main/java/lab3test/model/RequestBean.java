package lab3test.model;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class RequestBean implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double x;
    private Double y;
    private Double r;
    
    private Timestamp time;
    
    private Boolean result;

    public RequestBean(Double x, Double y, Double r, Timestamp time, Boolean result) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.time = time;
        this.result = result;
    }

    public RequestBean(RequestBean requestBean) {
        this.x = requestBean.getX();
        this.y = requestBean.getY();
        this.r = requestBean.getR();
    }

    public RequestBean() {
        this.x = Double.valueOf(0);
        this.y = Double.valueOf(0);
        this.r = Double.valueOf(1);
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

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public Boolean getResult() {
        return result;
    }

    public void setResult(Boolean result) {
        this.result = result;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
