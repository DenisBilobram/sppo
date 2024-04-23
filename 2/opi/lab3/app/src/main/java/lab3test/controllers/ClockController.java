package lab3test.controllers;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Named;

@Named
@ViewScoped
public class ClockController implements Serializable {
    
    private String currentDateTime;
    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public ClockController() {
    }

    @PostConstruct
    public void init() {
        updateTime();
    }

    public String getCurrentDateTime() {
        return currentDateTime;
    }

    public void updateTime() {
        Date now = new Date();
        this.currentDateTime = dateFormat.format(now);
    }

}
