package lab8.client;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.ArrayList;
import java.util.concurrent.PriorityBlockingQueue;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.TextAlignment;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lab8.app.auth.User;
import lab8.app.auth.commands.LoginCommand;
import lab8.app.auth.commands.RegistrationCommand;
import lab8.app.commands.Command;
import lab8.app.labwork.Color;
import lab8.app.labwork.Coordinates;
import lab8.app.labwork.Difficulty;
import lab8.app.labwork.LabWork;
import lab8.app.labwork.Person;
import lab8.app.signals.ServerSignal;
import lab8.client.auth.Authenticator;
import lab8.client.gui.GuiFabric;
import lab8.client.gui.validators.FormValidator;
import lab8.client.network.ServerConnection;

public class ClientApp extends Application {

    private static User user;

    private static ServerConnection server;

    private static Pane mainPaneWithLabWorks;

    private static PriorityBlockingQueue<LabWork> currentCollectLabWorks = new PriorityBlockingQueue<>();

    public static void setServer(ServerConnection server) {
        ClientApp.server = server;
    }

    private static String host;
    public static String getHost() {
        return host;
    }

    private static int port;

    public static int getPort() {
        return port;
    }

    private static Stage primaryStage;
    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    private static Stage authStage;
    public static Stage getAuthStage() {
        return authStage;
    }

    private static Stage mainAppStage;
    public static Stage getMainAppStage() {
        return mainAppStage;
    }

    private static Stage errorStage;

    private static Stage commandStage;

    public static Stage getCommandStage() {
        return commandStage;
    }

    @Override
    public void start(Stage primaryStage) {
        ClientApp.primaryStage = primaryStage;

        ClientApp.setServer(new ServerConnection(host, port));

        createAuthStage();

        // createMainStage();


    }

    private void createAuthStage() {

        ClientApp.authStage = new Stage();
        ClientApp.authStage.setResizable(false);
        authStage.setTitle("LabWork App");

        Button loginButton = new Button("Авторизоваться");
        loginButton.getStyleClass().clear();
        loginButton.getStyleClass().add("auth-button");
        loginButton.setPrefSize(350, 30);
        

        Button registerButton = new Button("Зарегестрироваться");
        registerButton.getStyleClass().clear();
        registerButton.getStyleClass().add("auth-button");
        registerButton.setPrefSize(350, 30);
        VBox.setMargin(registerButton, new Insets(25, 0, 0, 0));
        

        VBox authVBoxCommon = new VBox(GuiFabric.generateAuthHBox(), loginButton, registerButton);
        authVBoxCommon.setMaxSize(500, 820);
        authVBoxCommon.setMinHeight(820);
        authVBoxCommon.setAlignment(Pos.TOP_CENTER);

        StackPane rootCoomon = new StackPane(authVBoxCommon);
        rootCoomon.setAlignment(Pos.CENTER);
        rootCoomon.getStyleClass().add("auth-container");

        Scene authSceneCommon = new Scene(rootCoomon, 820, 820);
        authSceneCommon.getStylesheets().add("/css/auth.css");

        Button submitButton1 = GuiFabric.genereateSubmitButton();
        TextField userNameField1 = GuiFabric.generateUsernameField();
        
        PasswordField passwordField1 = GuiFabric.generatePasswordField();
        
        Button backButton = GuiFabric.generateBackButton();
        backButton.setOnAction(event -> {
            authStage.setScene(authSceneCommon);
        });

        VBox authVBoxLoginErrors = new VBox();
        authVBoxLoginErrors.getStyleClass().add("errors-box");
        authVBoxLoginErrors.setAlignment(Pos.TOP_CENTER);

        VBox authVBoxLogin = new VBox(GuiFabric.generateAuthHBox(), userNameField1, passwordField1, submitButton1, backButton, authVBoxLoginErrors);
        authVBoxLogin.setMaxSize(500, 820);
        authVBoxLogin.setAlignment(Pos.TOP_CENTER);

        StackPane rootLogin = new StackPane(authVBoxLogin);
        rootLogin.setAlignment(Pos.CENTER);
        rootLogin.getStyleClass().add("auth-container");

        Scene authSceneLogin = new Scene(rootLogin, 820, 820);
        authSceneLogin.getStylesheets().add("/css/auth.css");

        submitButton1.setOnAction(event -> {
            authStage.setScene(authSceneLogin);
        });

        loginButton.setOnAction(event -> {

            submitButton1.setText("Войти");

            submitButton1.setOnAction(eventInner -> {

                ArrayList<Label> errors = FormValidator.validateUser(userNameField1.getText(), passwordField1.getText());
                if (errors.size() == 0) {
                    Authenticator authenticator = new Authenticator(getConnection());
                    LoginCommand loginCommand = new LoginCommand();
                    loginCommand.setUser(new User(userNameField1.getText(), passwordField1.getText()));
                    User user = authenticator.authentication(loginCommand);
                    if (user != null) {
                        authStage.close();
                        ClientApp.user = user;
                        createMainStage();
                    }
                } else {
                    authVBoxLoginErrors.getChildren().clear();
                    authVBoxLoginErrors.getChildren().addAll(errors);
                }
            });

            authStage.setScene(authSceneLogin);
        });

        Button submitButton2 = GuiFabric.genereateSubmitButton();
        
        backButton = GuiFabric.generateBackButton();
        backButton.setOnAction(event -> {
            authStage.setScene(authSceneCommon);
        });


        TextField userNameField2 = GuiFabric.generateUsernameField();
        
        PasswordField passwordField2 = GuiFabric.generatePasswordField();
        TextField nameField = new TextField();
        nameField.setPromptText("Name");
        nameField.getStyleClass().add("auth-field");
        VBox.setMargin(nameField, new Insets(0, 0, 25, 0));
        TextField heightField = new TextField();
        heightField.setPromptText("height");
        heightField.getStyleClass().add("auth-field");
        FormValidator.addNumberValidator(heightField);
        VBox.setMargin(heightField, new Insets(0, 0, 25, 0));
        ComboBox<Color> eyeColorBox = new ComboBox<>();
        eyeColorBox.getItems().addAll(Color.values());
        eyeColorBox.setPromptText("Eye color");
        eyeColorBox.getStyleClass().add("auth-field");
        VBox.setMargin(eyeColorBox, new Insets(0, 0, 25, 0));


        VBox authVBoxRegisterErrors = new VBox();
        authVBoxRegisterErrors.getStyleClass().add("errors-box");

        authVBoxRegisterErrors.setAlignment(Pos.TOP_CENTER);

        VBox authVBoxRegister = new VBox(GuiFabric.generateAuthHBox(), userNameField2, passwordField2, nameField, heightField, eyeColorBox, submitButton2, backButton, authVBoxRegisterErrors);
        authVBoxRegister.setMaxSize(500, 820);
        authVBoxRegister.setAlignment(Pos.TOP_CENTER);

        StackPane rootRegister = new StackPane(authVBoxRegister);
        rootRegister.setAlignment(Pos.CENTER);
        rootRegister.getStyleClass().add("auth-container");
        

        Scene authSceneRegister = new Scene(rootRegister, 820, 820);
        authSceneRegister.getStylesheets().add("/css/auth.css");

        registerButton.setOnAction(event -> {
            submitButton2.setText("Подтвердить");
            submitButton2.setOnAction(eventInner -> {

                ArrayList<Label> errosUser = FormValidator.validateUser(userNameField2.getText(), passwordField2.getText());
                ArrayList<Label> errosProfile = FormValidator.validateProfile(nameField.getText(), heightField.getText(), eyeColorBox.getValue());
                if (errosUser.size() == 0 && errosProfile.size() == 0) {
                    Authenticator authenticator = new Authenticator(getConnection());
                    RegistrationCommand loginCommand = new RegistrationCommand();
                    loginCommand.setUser(new User(userNameField2.getText(), passwordField2.getText(), new Person(nameField.getText(), Long.parseLong(heightField.getText()), eyeColorBox.getValue())));
                    User user = authenticator.authentication(loginCommand);
                    if (user != null) {
                        authStage.close();
                        ClientApp.user = user;
                        createMainStage();
                        return;
                    }
                } else {
                    authVBoxRegisterErrors.getChildren().clear();
                    authVBoxRegisterErrors.getChildren().addAll(errosUser);
                    authVBoxRegisterErrors.getChildren().addAll(errosProfile);
                }
            });
            authStage.setScene(authSceneRegister);
        });

        rootLogin.requestFocus();
        rootRegister.requestFocus();
        authStage.setScene(authSceneCommon);

        authStage.show();
    }

    public static void createErrorStage(String errorText, String buttunText, EventHandler<ActionEvent> vHandler) {
        ClientApp.errorStage = new Stage();
        errorStage.setResizable(true);
        errorStage.setTitle("Ошибка");
        errorStage.initModality(Modality.APPLICATION_MODAL);

        Label errorMessage = new Label(errorText);
        VBox.setMargin(errorMessage, new Insets(0, 0, 30, 0));
        Button errorButton = new Button(buttunText);
        errorButton.setOnAction(vHandler);

        errorMessage.setStyle("-fx-font-size: 14px; -fx-text-fill: red;");
        errorButton.setStyle("-fx-font-size: 18px; -fx-max-width: 120px");
        FlowPane flowPane = new FlowPane(errorMessage);
        flowPane.setAlignment(Pos.CENTER);
        flowPane.setPadding(new Insets(15));

        VBox container = new VBox(flowPane, errorButton);
        container.setStyle("width: min-content;");
        container.setAlignment(Pos.CENTER);
        container.setSpacing(10);

        StackPane rooStackPane = new StackPane(container);

        Scene scene = new Scene(rooStackPane, 450, 200);
        errorStage.setScene(scene);

        errorStage.show();
    }

    public static void createMainStage() {

        ClientApp.mainAppStage = new Stage();
        ClientApp.mainAppStage.setTitle("LabWork App");

        ClientApp.mainAppStage.setOnCloseRequest(event -> {
            mainAppStage.close();
            if (commandStage != null) {
                commandStage.close();
            }
        });

        StackPane rootVizual = new StackPane();
        rootVizual.setAlignment(Pos.TOP_CENTER);
        Scene mainAppVizualScene = new Scene(rootVizual, 1280, 820);

        StackPane rootCollect = new StackPane();
        rootCollect.setAlignment(Pos.TOP_CENTER);
        Scene mainAppCollectScene = new Scene(rootCollect, 1280, 820);

        StackPane rootCommands = new StackPane();
        rootCommands.setAlignment(Pos.TOP_CENTER);
        Scene mainAppCommandsScene = new Scene(rootCommands, 1280, 820);

        StackPane rootProfile = new StackPane();
        rootProfile.setAlignment(Pos.TOP_CENTER);
        Scene mainAppProfileScene = new Scene(rootProfile, 1280, 820);

        ClientApp.mainPaneWithLabWorks = new Pane();
        
        updateMainPainWithLabWorks();

        Double[] xOffset = {Double.valueOf(0)};
        Double[] yOffset = {Double.valueOf(0)};

        Double[] xCoordCurrent = {Double.valueOf(0)};
        Double[] yCoordCurrent = {Double.valueOf(0)};

        Label xCoord = new Label("x: " + xCoordCurrent[0].toString() + "     ");
        Label yCoord = new Label("y: " + yCoordCurrent[0].toString());
        HBox coordinates = new HBox(xCoord, yCoord);

        mainPaneWithLabWorks.setOnMousePressed(event -> {
            xOffset[0] = event.getSceneX();
            yOffset[0] = event.getSceneY();
        });

        mainPaneWithLabWorks.setOnMouseDragged(event -> {
            double deltaX = event.getSceneX() - xOffset[0];
            double deltaY = event.getSceneY() - yOffset[0];
            xCoordCurrent[0] -= deltaX;
            yCoordCurrent[0] -= deltaY;
            for (Node node : mainPaneWithLabWorks.getChildren()) {
                node.setLayoutX(node.getLayoutX() + deltaX);
                node.setLayoutY(node.getLayoutY() + deltaY);
            }
            xOffset[0] = event.getSceneX();
            yOffset[0] = event.getSceneY();
            xCoord.setText("x: " + xCoordCurrent[0] + "     ");
            yCoord.setText("y: " + yCoordCurrent[0]);
        });


        
        HBox topMenuMain = GuiFabric.generateTopMenu(mainAppVizualScene, mainAppCollectScene, mainAppCommandsScene, mainAppProfileScene);
        VBox menuAndMain = new VBox(topMenuMain, mainPaneWithLabWorks, coordinates);
        coordinates.toFront();


        VBox.setVgrow(mainPaneWithLabWorks, Priority.ALWAYS);
        menuAndMain.setAlignment(Pos.TOP_CENTER);
        
        rootVizual.getChildren().add(menuAndMain);

        FlowPane commandsFlowPane = new FlowPane();
        commandsFlowPane.setMaxWidth(650);
        commandsFlowPane.setAlignment(Pos.CENTER);
        ArrayList<Button> commandButtons = GuiFabric.generateCommandButtons();
        commandsFlowPane.getChildren().addAll(commandButtons);

        StackPane commandsContainerStackPane = new StackPane(commandsFlowPane);
        commandsContainerStackPane.setAlignment(Pos.CENTER);
        commandsContainerStackPane.setPadding(new Insets(0, 0, 200, 0));

        VBox menuAndCommands = new VBox(GuiFabric.generateTopMenu(mainAppVizualScene, mainAppCollectScene, mainAppCommandsScene, mainAppProfileScene), commandsContainerStackPane);
        rootCommands.getChildren().add(menuAndCommands);
        menuAndCommands.setAlignment(Pos.TOP_CENTER);
        VBox.setVgrow(commandsContainerStackPane, Priority.ALWAYS);

        
        mainAppVizualScene.getStylesheets().add("/css/app.css");
        mainAppCollectScene.getStylesheets().add("/css/app.css");
        mainAppCommandsScene.getStylesheets().add("/css/app.css");
        mainAppProfileScene.getStylesheets().add("/css/app.css");

        ClientApp.mainAppStage.setScene(mainAppVizualScene);

        

        ClientApp.mainAppStage.show();
        mainPaneWithLabWorks.setClip(new Rectangle(mainPaneWithLabWorks.getWidth(), mainPaneWithLabWorks.getHeight()));


    }


    public static void updateMainPainWithLabWorks() {

        PriorityBlockingQueue<LabWork> labWorksNew = getConnection().getLabWorkCollection();

        List<LabWork> labWorksToDelete =  ClientApp.currentCollectLabWorks.stream().filter(labWrok -> !(labWorksNew.contains(labWrok))).toList();

        List<LabWork> labWorksToAdd = labWorksNew.stream().filter(labWork -> !(ClientApp.currentCollectLabWorks.contains(labWork))).toList();

        for (LabWork labWorktToDelete : labWorksToDelete) {
            Node labWorkCanvasDelete = mainPaneWithLabWorks.getChildren().stream().filter(labWorkCanvas -> labWorkCanvas.getId().equals(labWorktToDelete.getId().toString())).findFirst().get();
            mainPaneWithLabWorks.getChildren().remove(labWorkCanvasDelete);

        }

        ArrayList<Canvas> labWorksCanvases = GuiFabric.generateLabWorksIcons(labWorksToAdd);

        for (Canvas labWork : labWorksCanvases) {
            mainPaneWithLabWorks.getChildren().add(labWork);
        }
        ClientApp.currentCollectLabWorks = labWorksNew;
    }

    public static void createCommandStage(Command command) {
        ClientApp.commandStage = new Stage();
        ClientApp.commandStage.setTitle("Collection Command");


        StackPane rootCommandStage = new StackPane();
        rootCommandStage.setAlignment(Pos.TOP_CENTER);

        Label descrHeader = new Label("Описание комманды");
        descrHeader.getStyleClass().add("command-header-label");
        VBox.setMargin(descrHeader, new Insets(15, 15, 15, 15));


        Label descrText = new Label(command.getDescription());
        descrText.getStyleClass().add("command-descr-label");
        descrText.setWrapText(true);
        descrText.setTextAlignment(TextAlignment.CENTER);
        VBox.setMargin(descrText, new Insets(0, 0, 30, 0));


        VBox commandInfoAndSet = new VBox(descrHeader, descrText);
        commandInfoAndSet.setAlignment(Pos.TOP_CENTER);
        commandInfoAndSet.setPadding(new Insets(30, 0, 0, 0));
        commandInfoAndSet.setMaxWidth(400);

        final TextField idField = new TextField();

        File[] scriptFile = new File[1];

        final TextField nameField = new TextField();
        final TextField xCoordField = new TextField();
        final TextField yCoordField = new TextField();
        final TextField minimalPointField = new TextField();
        final TextField tunedInWorksField = new TextField();
        final ComboBox<Difficulty> difficultyCheckBox = new ComboBox<>();

        Label selectedFilePath = new Label();

        if (command.isRequireId()) {
            FormValidator.addNumberValidator(idField);
            idField.setPromptText("Введите ID элемента");
            commandInfoAndSet.getChildren().add(idField);
            commandInfoAndSet.getStyleClass().add("command-field");
        }
        if (command.isRequireFile()) {
            Button chooseFileButton = new Button("Выбрать скрипт");
            chooseFileButton.getStyleClass().add("menu-button");
            chooseFileButton.setOnAction(event -> {
                FileChooser fileChooser = new FileChooser();
                scriptFile[0] = fileChooser.showOpenDialog(ClientApp.commandStage);
                if (scriptFile[0] != null) {
                    selectedFilePath.setText(scriptFile[0].getPath());
                }
            });
            commandInfoAndSet.getChildren().add(chooseFileButton);
            commandInfoAndSet.getChildren().add(selectedFilePath);
        }
        if (command.isRequireLabWork()) {
            Label newLabWorkLabel = new Label("Новые значения LabWork");
            newLabWorkLabel.getStyleClass().add("command-header-label");
            VBox.setMargin(newLabWorkLabel, new Insets(15, 15, 15, 15));

            nameField.setPromptText("Name");
            nameField.getStyleClass().add("command-field");
            xCoordField.setPromptText("X coordinate");
            FormValidator.addNumberValidator(xCoordField);
            xCoordField.getStyleClass().add("command-field");
            yCoordField.setPromptText("Y coordinate");
            FormValidator.addNumberValidator(yCoordField);
            yCoordField.getStyleClass().add("command-field");
            HBox coordContainer = new HBox(xCoordField, yCoordField);
            minimalPointField.setPromptText("Minimal point");
            minimalPointField.getStyleClass().add("command-field");
            FormValidator.addNumberValidator(minimalPointField);
            tunedInWorksField.setPromptText("Tuned in works");
            tunedInWorksField.getStyleClass().add("command-field");
            FormValidator.addNumberValidator(tunedInWorksField);
            difficultyCheckBox.getItems().addAll(Difficulty.values());
            difficultyCheckBox.getStyleClass().add("command-field");
            difficultyCheckBox.setPromptText("Difficulty");
            difficultyCheckBox.setMinWidth(400);
            difficultyCheckBox.setMaxHeight(40);

            commandInfoAndSet.getChildren().addAll(newLabWorkLabel, nameField, coordContainer, minimalPointField, tunedInWorksField, difficultyCheckBox);
        }

        VBox errorsBox = new VBox();
        errorsBox.getStyleClass().add("errors-box");
        errorsBox.setAlignment(Pos.TOP_CENTER);

        Button submiButton = new Button("Подтвердить");
        submiButton.getStyleClass().add("command-button");
        VBox.setMargin(submiButton, new Insets(25, 0, 15, 0));

        Button backButton = new Button("Назад");
        backButton.getStyleClass().add("command-button");
        backButton.setOnAction(event -> {
            ClientApp.commandStage.close();
        });
        VBox.setMargin(backButton, new Insets(0, 0, 20, 0));

        commandInfoAndSet.getChildren().addAll(submiButton, backButton, errorsBox);

        Scene commandStageScene = new Scene(rootCommandStage, 720, 720);

        rootCommandStage.requestFocus();
        commandStageScene.getStylesheets().add("/css/app.css");

        rootCommandStage.getChildren().add(commandInfoAndSet);

        ClientApp.commandStage.setScene(commandStageScene);


        Label resultHeader = new Label();
        resultHeader.getStyleClass().add("command-header-label");
        VBox.setMargin(resultHeader, new Insets(30));

        Label resultText = new Label();
        resultText.getStyleClass().add("command-descr-label");
        ScrollPane resultTextContainer = new ScrollPane(resultText);
        resultTextContainer.setMaxWidth(500);
        resultTextContainer.setMaxHeight(500);
        

        Button closeCommandResult = new Button("Закрыть");
        closeCommandResult.getStyleClass().add("command-button");
        VBox.setMargin(closeCommandResult, new Insets(30));
        closeCommandResult.setOnAction(event -> {
            ClientApp.commandStage.close();
        });

        VBox commandResultVBox = new VBox(resultHeader, resultTextContainer, closeCommandResult);
        commandResultVBox.setAlignment(Pos.TOP_CENTER);

        StackPane commandRedultRoot = new StackPane(commandResultVBox);
        Scene commandResultScene = new Scene(commandRedultRoot, 720, 720);
        commandResultScene.getStylesheets().add("/css/app.css");

        submiButton.setOnAction(event -> {

            errorsBox.getChildren().clear();
            ArrayList<Label> errors = new ArrayList<>();


            if (command.isRequireId()) {
                if (idField.getText() == "") {
                    errors.add(new Label("Введите поле ID."));
                } else {
                    command.setId(idField.getText());
                }
            }
            if (command.isRequireFile()) {
                if (scriptFile[0] == null) {
                    errors.add(new Label("Необходимо загрузить скрипт."));
                } else {
                    command.setFile(scriptFile[0]);
                }
            }
            if (command.isRequireLabWork()) {
                ArrayList<Label> labWorkErrors = FormValidator.validateLabWork(nameField.getText(), xCoordField.getText(), yCoordField.getText(), minimalPointField.getText(), tunedInWorksField.getText(), difficultyCheckBox.getValue());
                errors.addAll(labWorkErrors);
                if (errors.size() == 0) {
                    LabWork labWorkUpdate = new LabWork(nameField.getText(), new Coordinates(Long.parseLong(xCoordField.getText()), Long.parseLong(yCoordField.getText())), new Date(), !minimalPointField.getText().equals("") ? Long.parseLong(minimalPointField.getText()) : null, !tunedInWorksField.getText().equals("") ? Long.parseLong(minimalPointField.getText()) : null, difficultyCheckBox.getValue(), ClientApp.user.getProfile());
                    labWorkUpdate.setOwner(ClientApp.user);
                    command.setLabWorkNew(labWorkUpdate);
                    command.setUser(ClientApp.user);
                }
            }

            if (errors.size() == 0) {
                ServerSignal ServerSignal = ClientApp.getConnection().executeCommandOnServer(command);
                if (!ServerSignal.isSucces()) {
                    createErrorStage(ServerSignal.getMessage(), "Закрыть", innerEvent -> {
                        ClientApp.getErrorStage().close();
                    });
                } else {
                    resultHeader.setText("Команда выполнена успешно");
                    resultText.setText(ServerSignal.getMessage());
                    ClientApp.commandStage.setScene(commandResultScene);
                    updateMainPainWithLabWorks();
                }

            } else {
                errorsBox.getChildren().addAll(errors);
            }
        });

        ClientApp.commandStage.show();

    }

    public static void main(String[] args) {
        if (args.length != 2 || !isPort(args[1])) {
            System.out.println("Неверный формат аргументов: {host} {port}");
            System.exit(0);
        }
        
        ClientApp.host = args[0];
        ClientApp.port = Integer.parseInt(args[1]);

        launch(args);
    }

    public static ServerConnection getConnection() {
        return ClientApp.server;
    }

        public static boolean isPort(String port) {
        try {
            int portNum = Integer.parseInt(port);
            if (portNum > 0 && portNum < 65536) {
                return true;
            }
        } catch (NumberFormatException exp) {
            
        }
        return false;
    }

    public static Stage getErrorStage() {
        return errorStage;
    }
    
}
