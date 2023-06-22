package lab8.client.gui.validators;

import java.util.ArrayList;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lab8.app.labwork.Color;
import lab8.app.labwork.Difficulty;

public class FormValidator {
    public static ArrayList<Label> validateUser(String username, String password) {
        ArrayList<Label> errors = new ArrayList<>();
        if (username.length() < 4 || username.length() > 30) {
            Label usernameLenError = new Label("После username должно быть от 4 до 30 символов.");
            errors.add(usernameLenError);
        }
        if (password.length() < 4 || password.length() > 30) {
            Label passwordLenError = new Label("После password должно быть от 4 до 30 символов.");
            errors.add(passwordLenError);
        }
        return errors;
    }

    public static ArrayList<Label> validateProfile(String firstName, String height, Color color) {
        ArrayList<Label> errors = new ArrayList<>();
        if (firstName.equals("")) {
            errors.add(new Label("Введите значение для поля Name."));
        }
        Double heightNum;
        try {
            heightNum = Double.parseDouble(height);
            if (heightNum == null || heightNum <= 0) {
                errors.add(new Label("Поле height должно быть больше 0."));
            }
        } catch (Exception exp) {
            errors.add(new Label("Значение поля height должно быть числом."));
        }
        if (color == null) {
            errors.add(new Label("Выберите значение для поля Eye color"));
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
            errors.add(new Label("Введите поле Name."));
        }

        try {
            if (xCoord == null || xCoord.equals("")) {
                errors.add(new Label("Введите значение X Coordinate."));
            } else if (Long.parseLong(xCoord) > 689) {
                errors.add(new Label("Введите корректное значение X Coordinate, оно должно быть меньше 689."));
            }
        } catch (Exception exp) {
            errors.add(new Label("Неверный формат X Coordinate."));
        }
        
        try {
            if (yCoord == null || yCoord.equals("")) {
                errors.add(new Label("Введите значение Y Coordinate."));
            } else if (Long.parseLong(yCoord) > 689) {
                errors.add(new Label("Введите корректное значение Y Coordinate, оно должно быть меньше 475."));
            }
        } catch (Exception exp) {
            errors.add(new Label("Неверный формат Y Coordinate."));
        }
        
        try {
            if (minimalPoint != null && !minimalPoint.equals("") && Long.parseLong(minimalPoint) <= 0) {
                errors.add(new Label("Значение Minimal point длжно быть больше 0."));
            }
        } catch (Exception exp) {
            errors.add(new Label("Неверный формат Minimal point."));
        }

        try {
            if (tunedInWorks != null && !tunedInWorks.equals("") && (Long.parseLong(tunedInWorks) < Long.MIN_VALUE || Long.parseLong(tunedInWorks) > Long.MAX_VALUE)) {
                errors.add(new Label("Введите корректное значение Tuned in works."));
            }
        } catch (Exception exp) {
            errors.add(new Label("Неверный формат Tuned in works."));
        }

        if (difficulty == null) {
            errors.add(new Label("Введите значение Difficulty."));
        }

        return errors;
    }

    // public static ArrayList<Node> validateLabWork() {
        
    // }
}
