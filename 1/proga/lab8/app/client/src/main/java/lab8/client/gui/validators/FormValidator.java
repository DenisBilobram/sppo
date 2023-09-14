package lab8.client.gui.validators;

import java.util.ArrayList;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lab8.app.labwork.Color;
import lab8.app.labwork.Difficulty;
import lab8.client.ClientApp;

public class FormValidator {
    public static ArrayList<Label> validateUser(String username, String password) {
        ArrayList<Label> errors = new ArrayList<>();
        if (username.length() < 4 || username.length() > 30) {
            Label usernameLenError = new Label(ClientApp.getBundle().getString("usernameerr"));
            errors.add(usernameLenError);
        }
        if (password.length() < 4 || password.length() > 30) {
            Label passwordLenError = new Label(ClientApp.getBundle().getString("passwerr"));
            errors.add(passwordLenError);
        }
        return errors;
    }

    public static ArrayList<Label> validateProfile(String firstName, String height, Color color) {
        ArrayList<Label> errors = new ArrayList<>();
        if (firstName.equals("")) {
            errors.add(new Label(ClientApp.getBundle().getString("nameerr")));
        }
        Double heightNum;
        try {
            heightNum = Double.parseDouble(height);
            if (heightNum == null || heightNum <= 0) {
                errors.add(new Label(ClientApp.getBundle().getString("heighterr1")));
            }
        } catch (Exception exp) {
            errors.add(new Label(ClientApp.getBundle().getString("heighterr2")));
        }
        if (color == null) {
            errors.add(new Label(ClientApp.getBundle().getString("colorerr")));
        }
        return errors;
    }

    public static void addNumberValidator(TextField field) {
        field.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                field.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });
    }

    public static ArrayList<Label> validateLabWork(String name, String xCoord, String yCoord, String minimalPoint, String tunedInWorks, Difficulty difficulty) {
        ArrayList<Label> errors = new ArrayList<>();

        if (name == null || name.equals("")) {
            errors.add(new Label(ClientApp.getBundle().getString("nameeerror")));
        }

        try {
            if (xCoord == null || xCoord.equals("")) {
                errors.add(new Label(ClientApp.getBundle().getString("xcorderr")));
            } else if (Long.parseLong(xCoord) > 689) {
                errors.add(new Label(ClientApp.getBundle().getString("xcorderr1")));
            }
        } catch (Exception exp) {
            errors.add(new Label(ClientApp.getBundle().getString("xcorderr2")));
        }
        
        try {
            if (yCoord == null || yCoord.equals("")) {
                errors.add(new Label(ClientApp.getBundle().getString("ycorderr")));
            } else if (Long.parseLong(yCoord) > 475) {
                errors.add(new Label(ClientApp.getBundle().getString("ycorderr1")));
            }
        } catch (Exception exp) {
            errors.add(new Label(ClientApp.getBundle().getString("ycorderr2")));
        }
        
        try {
            if (minimalPoint != null && !minimalPoint.equals("") && Long.parseLong(minimalPoint) <= 0) {
                errors.add(new Label(ClientApp.getBundle().getString("minerr")));
            }
        } catch (Exception exp) {
            errors.add(new Label(ClientApp.getBundle().getString("minerr1")));
        }

        try {
            if (tunedInWorks != null && !tunedInWorks.equals("") && (Long.parseLong(tunedInWorks) < Long.MIN_VALUE || Long.parseLong(tunedInWorks) > Long.MAX_VALUE)) {
                errors.add(new Label(ClientApp.getBundle().getString("tunederr")));
            }
        } catch (Exception exp) {
            errors.add(new Label(ClientApp.getBundle().getString("tunederr1")));
        }

        if (difficulty == null) {
            errors.add(new Label(ClientApp.getBundle().getString("diferr")));
        }

        return errors;
    }

    // public static ArrayList<Node> validateLabWork() {
        
    // }
}
