package lab2.beans;

import java.io.Serializable;
import java.util.Date;

public class RecordBean implements Serializable{

    private Double x;
    private Double y;
    private Double r;
    private Date time;
    private Boolean result;

    public RecordBean(Double x, Double y, Double r, Date time, Boolean result) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.time = time;
        this.result = result;
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
