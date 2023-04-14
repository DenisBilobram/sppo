package app.signals;

import java.io.Serializable;

public class Signal implements Serializable {
    
    protected String message;

    protected boolean succes;

    public Signal() {
    }

    public Signal(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSucces() {
        return succes;
    }

    public void setSucces(boolean succes) {
        this.succes = succes;
    }

    

}
