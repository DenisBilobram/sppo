package lab8.client.gui;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import lab8.app.commands.CommandAdd;
import lab8.app.commands.CommandClear;
import lab8.app.commands.CommandCountLessAuthor;
import lab8.app.commands.CommandExecute;
import lab8.app.commands.CommandHead;
import lab8.app.commands.CommandInfo;
import lab8.app.commands.CommandMaxByName;
import lab8.app.commands.CommandPrintTunedInWorks;
import lab8.app.commands.CommandRemoveById;
import lab8.app.commands.CommandRemoveHead;
import lab8.app.commands.CommandRemoveLower;
import lab8.app.commands.CommandUpdate;
import lab8.app.labwork.LabWork;
import lab8.client.ClientApp;

public class GuiFabric {

    public static HBox generateAuthHBox() {
        Label itmoLabel = new Label("ИТМО/");
        itmoLabel.setFont(Font.font("Arial", FontWeight.BOLD, 44));
        Label vtLabel = new Label("ВТ");
        vtLabel.setFont(Font.font("Arial", FontWeight.BOLD, 44));
        vtLabel.setTextFill(Color.rgb(236, 11, 67));
        ImageView imageView = new ImageView("image/duck.png");
        imageView.setFitWidth(50);
        imageView.setFitHeight(50);

        HBox authHBox = new HBox(itmoLabel, vtLabel, imageView);
        authHBox.setMaxSize(350, 200);
        authHBox.setAlignment(Pos.CENTER);
        authHBox.setPadding(new Insets(50, 0, 100, 0));

        return authHBox;
    }

    public static Button generateBackButton() {
        Button backButton = new Button("Назад");
        backButton.getStyleClass().clear();
        backButton.getStyleClass().add("auth-button");
        backButton.setPrefSize(350, 30);
        return backButton;
    }

    public static TextField generateUsernameField() {
        TextField usernamTextField = new TextField();
        usernamTextField.setPromptText("Username");
        usernamTextField.getStyleClass().add("auth-field");
        VBox.setMargin(usernamTextField, new Insets(0, 0, 25, 0));
        return usernamTextField;
    }

    public static PasswordField generatePasswordField() {
        PasswordField passwordField = new PasswordField();
        passwordField.getStyleClass().add("auth-field");
        passwordField.setPromptText("Password");
        VBox.setMargin(passwordField, new Insets(0, 0, 25, 0));
        return passwordField;
    }

    public static Button genereateSubmitButton() {
        Button submiButton = new Button();
        submiButton.getStyleClass().clear();
        submiButton.getStyleClass().add("auth-button");
        VBox.setMargin(submiButton, new Insets(0, 0, 25, 0));
        return submiButton;
    }

    public static HBox generateTopMenu(Scene vizual, Scene collect, Scene commands, Scene profile) {

        Button vizualButton = new Button("Визуализация");
        HBox.setMargin(vizualButton, new Insets(20, 20, 20, 0));
        vizualButton.getStyleClass().add("menu-button");
        Button collectioButton = new Button("Коллекция");
        HBox.setMargin(collectioButton, new Insets(20, 20, 20, 0));
        collectioButton.getStyleClass().add("menu-button");
        Button commandsButton = new Button("Команды");
        HBox.setMargin(commandsButton, new Insets(20, 20, 20, 0));
        commandsButton.getStyleClass().add("menu-button");
        Button profileButton = new Button("Профиль");
        HBox.setMargin(profileButton, new Insets(20, 0, 20, 0));
        profileButton.getStyleClass().add("menu-button");
        

        vizualButton.setOnAction(event -> {
            ClientApp.getMainAppStage().setScene(vizual);
        });

        collectioButton.setOnAction(event -> {
            ClientApp.getMainAppStage().setScene(collect);
        });

        commandsButton.setOnAction(event -> {
            ClientApp.getMainAppStage().setScene(commands);
        });

        profileButton.setOnAction(event -> {
            ClientApp.getMainAppStage().setScene(profile);
        });



        HBox topMenu = new HBox(vizualButton, collectioButton, commandsButton, profileButton);

        topMenu.getStyleClass().add("menu-container");
        topMenu.setAlignment(Pos.CENTER);

        

        return topMenu;
    }

    public static Canvas generateLabWrokIcon(LabWork labWork) {

        Canvas labWorkIcon = new Canvas(64, 64);
        labWorkIcon.setId(labWork.getId().toString());
        labWorkIcon.setLayoutX(labWork.getCoordinates().getX());
        labWorkIcon.setLayoutY(labWork.getCoordinates().getY());
        GraphicsContext gc = labWorkIcon.getGraphicsContext2D();

        gc.setStroke(Color.BLACK);
        gc.setFill(Color.WHITE);

        gc.fillRect(0, 0, 64, 64);
        gc.strokeRect(0, 0, 64, 64);

        gc.setFont(Font.font("Arial", 11));

        gc.setFill(Color.BLACK);
        gc.fillText("LabWork", 8, 16);

        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1.0);

        double startY = 26;
        double lineHeight = 4;
        for (int i = 0; i < 6; i++) {
            double y = startY + i * lineHeight;
            gc.strokeLine(8, y, 56, y);
        }

        ContextMenu contextMenu = new ContextMenu();

        MenuItem interactMenuLook = new MenuItem("Посмотреть");
        interactMenuLook.setOnAction(event -> {
            System.out.println("Вы выбрали 'Взаимодействовать с LabWork'");
        });

        MenuItem interactMenuEdit = new MenuItem("Редактировать");
        interactMenuEdit.setOnAction(event -> {
            System.out.println("Вы выбрали 'Редактировать LabWork'");
        });

        MenuItem interactMenuDelete = new MenuItem("Удалить");
        interactMenuDelete.setOnAction(event -> {
            System.out.println("Вы выбрали 'Удалить LabWork'");
        });

        contextMenu.getItems().addAll(interactMenuLook, interactMenuEdit, interactMenuDelete);

        labWorkIcon.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                contextMenu.show(labWorkIcon, event.getScreenX(), event.getScreenY());
            }
        });

        return labWorkIcon;

    }

    public static ArrayList<Canvas> generateLabWorksIcons(List<LabWork> priorityBlockingQueue) {
        ArrayList<Canvas> labWorksCanvases = new ArrayList<>();
        for (LabWork labWork : priorityBlockingQueue) {
            labWorksCanvases.add(generateLabWrokIcon(labWork));
        }
        return labWorksCanvases;
    }


    public static ArrayList<Button> generateCommandButtons() {
        ArrayList<Button> buttons = new ArrayList<>();

        Button addButton = new Button("Add");
        addButton.getStyleClass().add("command-button");

        addButton.setOnAction(event -> {
            ClientApp.createCommandStage(new CommandAdd());
        });

        Button updateButton = new Button("Update");
        updateButton.getStyleClass().add("command-button");

        updateButton.setOnAction(event -> {
            ClientApp.createCommandStage(new CommandUpdate());
        });

        Button clearButton = new Button("Clear");
        clearButton.getStyleClass().add("command-button");

        clearButton.setOnAction(event -> {
            ClientApp.createCommandStage(new CommandClear());
        });

        Button countLessAuthorButton = new Button("Count less auhtor");
        countLessAuthorButton.getStyleClass().add("command-button");

        countLessAuthorButton.setOnAction(event -> {
            ClientApp.createCommandStage(new CommandCountLessAuthor());
        });

        Button executeScriptButton = new Button("Execute script");
        executeScriptButton.getStyleClass().add("command-button");

        executeScriptButton.setOnAction(event -> {
            ClientApp.createCommandStage(new CommandExecute());
        });

        Button headButton = new Button("Head");
        headButton.getStyleClass().add("command-button");

        headButton.setOnAction(event -> {
            ClientApp.createCommandStage(new CommandHead());
        });

        Button infoButton = new Button("Info");
        infoButton.getStyleClass().add("command-button");

        infoButton.setOnAction(event -> {
            ClientApp.createCommandStage(new CommandInfo());
        });

        Button maxByNameButton = new Button("Max by name");
        maxByNameButton.getStyleClass().add("command-button");

        maxByNameButton.setOnAction(event -> {
            ClientApp.createCommandStage(new CommandMaxByName());
        });

        Button tunedInWorksButton = new Button("Show TunedInWorks");
        tunedInWorksButton.getStyleClass().add("command-button");

        tunedInWorksButton.setOnAction(event -> {
            ClientApp.createCommandStage(new CommandPrintTunedInWorks());
        });

        Button removeById = new Button("Remove by ID");
        removeById.getStyleClass().add("command-button");

        removeById.setOnAction(event -> {
            ClientApp.createCommandStage(new CommandRemoveById());
        });

        Button removeHead = new Button("Remove head");
        removeHead.getStyleClass().add("command-button");

        removeHead.setOnAction(event -> {
            ClientApp.createCommandStage(new CommandRemoveHead());
        });

        Button removeLower = new Button("Remove lower");
        removeLower.getStyleClass().add("command-button");

        removeLower.setOnAction(event -> {
            ClientApp.createCommandStage(new CommandRemoveLower());
        });

        buttons.add(addButton);
        buttons.add(updateButton);
        buttons.add(clearButton);
        buttons.add(countLessAuthorButton);
        buttons.add(executeScriptButton);
        buttons.add(headButton);
        buttons.add(infoButton);
        buttons.add(maxByNameButton);
        buttons.add(tunedInWorksButton);
        buttons.add(removeById);
        buttons.add(removeHead);
        buttons.add(removeLower);

        for (Button button : buttons) {
            FlowPane.setMargin(button, new Insets(20));
        }

        return buttons;
    }
}
