package lab8.client;

import java.io.File;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.ArrayList;
import java.util.concurrent.PriorityBlockingQueue;

import javafx.animation.FadeTransition;
import javafx.application.Application;

import javafx.util.Duration;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
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
import lab8.app.commands.CommandAdd;
import lab8.app.commands.CommandClear;
import lab8.app.commands.CommandCountLessAuthor;
import lab8.app.commands.CommandExecute;
import lab8.app.commands.CommandHead;
import lab8.app.commands.CommandInfo;
import lab8.app.commands.CommandLook;
import lab8.app.commands.CommandMaxByName;
import lab8.app.commands.CommandPrintTunedInWorks;
import lab8.app.commands.CommandRemoveById;
import lab8.app.commands.CommandRemoveHead;
import lab8.app.commands.CommandRemoveLower;
import lab8.app.commands.CommandUpdate;
import lab8.app.labwork.Color;
import lab8.app.labwork.Coordinates;
import lab8.app.labwork.Difficulty;
import lab8.app.labwork.LabWork;
import lab8.app.labwork.Language;
import lab8.app.labwork.Person;
import lab8.app.signals.ServerSignal;
import lab8.client.auth.Authenticator;
import lab8.client.gui.GuiFabric;
import lab8.client.gui.LabWorksController;
import lab8.client.gui.table.ButtonTableCell;
import lab8.client.gui.validators.FormValidator;
import lab8.client.network.ServerConnection;

public class ClientApp extends Application {

    private static User user;

	private static ServerConnection server;

    private static Pane mainPaneWithLabWorks;

    private static TableView<LabWork> labWorksTable;

	private static PriorityBlockingQueue<LabWork> currentCollectLabWorks = new PriorityBlockingQueue<>();

    private static String host;
    
    private static int port;

    private static Stage errorStage;

    private static Stage commandStage;

    private static Locale locale = new Locale("ru");

    private static Language language = Language.RUSSIAN;

    private static ResourceBundle bundle = ResourceBundle.getBundle("locale/all", locale);

    private static Double[] xCoordCurrent = {Double.valueOf(0)};
    private static Double[] yCoordCurrent = {Double.valueOf(0)};

    public static Locale getLocale() {
		return locale;
	}

    public static ResourceBundle getBundle() {
		return bundle;
	}

	private static LabWorksController collChecker;

    @Override
    public void start(Stage primaryStage) {
        ClientApp.primaryStage = primaryStage;

        createAuthStage();

    }


    private static void createAuthStage() {

        ClientApp.authStage = new Stage();
        ClientApp.authStage.setResizable(false);
        authStage.setTitle("LabWork App");


        Button loginButton = new Button(bundle.getString("authlogbut"));
        loginButton.getStyleClass().clear();
        loginButton.getStyleClass().add("auth-button");
        loginButton.setPrefSize(350, 30);
        

        Button registerButton = new Button(bundle.getString("authregbut"));
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

            submitButton1.setText(bundle.getString("loginbut"));

            submitButton1.setOnAction(eventInner -> {

                ArrayList<Label> errors = FormValidator.validateUser(userNameField1.getText(), passwordField1.getText());
                if (errors.size() == 0) {
                    Authenticator authenticator = new Authenticator(getConnection());
                    LoginCommand loginCommand = new LoginCommand();
                    loginCommand.setUser(new User(userNameField1.getText(), passwordField1.getText()));
                    User newUser = authenticator.authentication(loginCommand);
                    if (newUser != null) {
    
                        authStage.close();
                        ClientApp.user = newUser;
                        ClientApp.user.setLanguage(language);
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
            submitButton2.setText(bundle.getString("submitbut"));
            submitButton2.setOnAction(eventInner -> {

                ArrayList<Label> errosUser = FormValidator.validateUser(userNameField2.getText(), passwordField2.getText());
                ArrayList<Label> errosProfile = FormValidator.validateProfile(nameField.getText(), heightField.getText(), eyeColorBox.getValue());
                if (errosUser.size() == 0 && errosProfile.size() == 0) {
                    Authenticator authenticator = new Authenticator(getConnection());
                    RegistrationCommand loginCommand = new RegistrationCommand();
                    loginCommand.setUser(new User(userNameField2.getText(), passwordField2.getText(), new Person(nameField.getText(), Long.parseLong(heightField.getText()), eyeColorBox.getValue())));
                    User userNew = authenticator.authentication(loginCommand);
                    if (userNew != null) {
                        authStage.close();
                        ClientApp.user = userNew;
                        ClientApp.user.setLanguage(language);
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

        ClientApp.setServer(new ServerConnection(host, port));

    }

    public static void createErrorStage(String errorText, String buttunText, EventHandler<ActionEvent> vHandler, boolean crit) {

        ClientApp.errorStage = new Stage();
        errorStage.initModality(Modality.APPLICATION_MODAL);
        errorStage.setTitle(bundle.getString("errotitle"));

        errorStage.setOnCloseRequest(event -> {
            if (crit) {
                if (authStage != null) {
                    authStage.close();
                }
                if (mainAppStage != null) {
                    mainAppStage.close();
                }
                if (commandStage != null) {
                    commandStage.close();
                }
                primaryStage.close();
            } else {
                errorStage.close();
                ClientApp.errorStage = null;
            }
        });

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


        // Настройка сцена визуализации


        ClientApp.mainPaneWithLabWorks = new Pane();
        mainPaneWithLabWorks.setTranslateX(0);
        mainPaneWithLabWorks.setTranslateY(0);

        currentCollectLabWorks.clear(); 
        
        updateMainPainWithLabWorks();

        ClientApp.collChecker = new LabWorksController();
        collChecker.setDaemon(true);
        collChecker.start();

        Double[] xOffset = {Double.valueOf(0)};
        Double[] yOffset = {Double.valueOf(0)};

        Label xCoord = new Label("x: " + xCoordCurrent[0].intValue());
        HBox.setMargin(xCoord, new Insets(0, 20, 0, 0));
        Label yCoord = new Label("y: " + yCoordCurrent[0].intValue());
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
            xCoord.setText("x: " + xCoordCurrent[0].intValue());
            yCoord.setText("y: " + yCoordCurrent[0].intValue());
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


        // Настройка сцены коллекции

        ClientApp.labWorksTable = new TableView<>();
        TableColumn<LabWork, String> idField = new TableColumn<>("Id");
        TableColumn<LabWork, String> nameField = new TableColumn<>("Name");
        TableColumn<LabWork, String> xField = new TableColumn<>("X");
        TableColumn<LabWork, String> yField = new TableColumn<>("Y");
        TableColumn<LabWork, String> dateField = new TableColumn<>("Creation date");
        TableColumn<LabWork, String> minimalField = new TableColumn<>("Minimal point");
        TableColumn<LabWork, String> tunedField = new TableColumn<>("Tuned in works");
        TableColumn<LabWork, String> diffField = new TableColumn<>("Difficulty");
        TableColumn<LabWork, String> ownerField = new TableColumn<>("Owner");
        TableColumn<LabWork, Void> editField = new TableColumn<>("Command Update");
        TableColumn<LabWork, Void> deleteField = new TableColumn<>("Command Remove");

        idField.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameField.setCellValueFactory(new PropertyValueFactory<>("name"));
        xField.setCellValueFactory(param -> {
            return new SimpleStringProperty(Long.valueOf((param.getValue().getCoordinates().getX())).toString());
        });
        yField.setCellValueFactory(param -> {
            return new SimpleStringProperty(Long.valueOf((param.getValue().getCoordinates().getX())).toString());
        });
        dateField.setCellValueFactory(param -> {
            return new SimpleStringProperty(param.getValue().getCreationDate().toString());
        });
        minimalField.setCellValueFactory(new PropertyValueFactory<>("minimalPoint"));
        tunedField.setCellValueFactory(new PropertyValueFactory<>("tunedInWorks"));
        diffField.setCellValueFactory(param -> {
            return new SimpleStringProperty(param.getValue().getDifficulty().toString());
        });
        diffField.setPrefWidth(100);
        ownerField.setCellValueFactory(param -> {
            return new SimpleStringProperty(param.getValue().getOwner().getUsername());
        });;
        editField.setCellFactory(param -> new ButtonTableCell(bundle.getString("menuedit"), new CommandUpdate()));
        deleteField.setCellFactory(param -> new ButtonTableCell(bundle.getString("menudelete"), new CommandRemoveById()));

        labWorksTable.getColumns().add(idField);
        labWorksTable.getColumns().add(nameField);
        labWorksTable.getColumns().add(xField);
        labWorksTable.getColumns().add(yField);
        labWorksTable.getColumns().add(minimalField);
        labWorksTable.getColumns().add(tunedField);
        labWorksTable.getColumns().add(diffField);
        labWorksTable.getColumns().add(dateField);
        labWorksTable.getColumns().add(ownerField);
        labWorksTable.getColumns().add(editField);
        labWorksTable.getColumns().add(deleteField);

        updateLabWorkTable();


        TextField idFilterField = new TextField();
        idFilterField.setPromptText("Id");
        idFilterField.setPrefWidth(idField.getWidth());
        idField.widthProperty().addListener((obs, oldWidth, newWidth) -> {
            idFilterField.setPrefWidth(newWidth.doubleValue());
        });
        FormValidator.addNumberValidator(idFilterField);
        

        TextField nameFilterField = new TextField();
        nameFilterField.setPromptText("Name");
        nameFilterField.setPrefWidth(nameField.getWidth());
        nameField.widthProperty().addListener((obs, oldWidth, newWidth) -> {
            nameFilterField.setPrefWidth(newWidth.doubleValue());
        });


        TextField xFilterField = new TextField();
        xFilterField.setPromptText("X");
        xFilterField.setPrefWidth(xField.getWidth());
        xField.widthProperty().addListener((obs, oldWidth, newWidth) -> {
            xFilterField.setPrefWidth(newWidth.doubleValue());
        });
        FormValidator.addNumberValidator(xFilterField);
    

        TextField yFilterField = new TextField();
        yFilterField.setPromptText("Y");
        yFilterField.setPrefWidth(yField.getWidth());
        yField.widthProperty().addListener((obs, oldWidth, newWidth) -> {
            yFilterField.setPrefWidth(newWidth.doubleValue());
        });
        FormValidator.addNumberValidator(yFilterField);

        TextField minmalFilterField = new TextField();
        minmalFilterField.setPromptText("Minimal point");
        minmalFilterField.setPrefWidth(minimalField.getWidth());
        minimalField.widthProperty().addListener((obs, oldWidth, newWidth) -> {
            minmalFilterField.setPrefWidth(newWidth.doubleValue());
        });
        FormValidator.addNumberValidator(minmalFilterField);

        TextField tunedFilterField = new TextField();
        tunedFilterField.setPromptText("Tuned in works");
        tunedFilterField.setPrefWidth(tunedField.getWidth());
        tunedField.widthProperty().addListener((obs, oldWidth, newWidth) -> {
            tunedFilterField.setPrefWidth(newWidth.doubleValue());
        });
        FormValidator.addNumberValidator(tunedFilterField);

        ObservableList<Difficulty> options = FXCollections.observableArrayList();
        options.add(null);
        options.addAll(Difficulty.values());
        ComboBox<Difficulty> diffFilterField = new ComboBox<>();
        diffFilterField.setItems(options);
        diffFilterField.getSelectionModel().selectFirst();
        diffFilterField.setPromptText("Difficulty");
        diffFilterField.setPrefWidth(diffField.getWidth());
        diffField.widthProperty().addListener((obs, oldWidth, newWidth) -> {
            diffFilterField.setPrefWidth(newWidth.doubleValue());
        });

        DatePicker datePicker = new DatePicker();
        datePicker.setPromptText("Creation date");
        datePicker.setPrefWidth(dateField.getWidth());
        dateField.widthProperty().addListener((obs, oldWidth, newWidth) -> {
            datePicker.setPrefWidth(newWidth.doubleValue());
        });

        TextField ownerFilterField = new TextField();
        ownerFilterField.setPromptText("Owner");
        ownerFilterField.setPrefWidth(ownerField.getWidth());
        ownerField.widthProperty().addListener((obs, oldWidth, newWidth) -> {
            ownerFilterField.setPrefWidth(newWidth.doubleValue());
        });

        idFilterField.textProperty().addListener((obs, old, newVal) -> {
            filterTable(idFilterField.getText(), nameFilterField.getText(), xFilterField.getText(), yFilterField.getText(), minmalFilterField.getText(), tunedFilterField.getText(), diffFilterField.getValue(), datePicker.getValue(), ownerFilterField.getText());
        });

        nameFilterField.textProperty().addListener((obs, old, newVal) -> {
            filterTable(idFilterField.getText(), nameFilterField.getText(), xFilterField.getText(), yFilterField.getText(), minmalFilterField.getText(), tunedFilterField.getText(), diffFilterField.getValue(), datePicker.getValue(), ownerFilterField.getText());
        });

        xFilterField.textProperty().addListener((obs, old, newVal) -> {
            filterTable(idFilterField.getText(), nameFilterField.getText(), xFilterField.getText(), yFilterField.getText(), minmalFilterField.getText(), tunedFilterField.getText(), diffFilterField.getValue(), datePicker.getValue(), ownerFilterField.getText());
        });

        yFilterField.textProperty().addListener((obs, old, newVal) -> {
            filterTable(idFilterField.getText(), nameFilterField.getText(), xFilterField.getText(), yFilterField.getText(), minmalFilterField.getText(), tunedFilterField.getText(), diffFilterField.getValue(), datePicker.getValue(), ownerFilterField.getText());
        });

        minmalFilterField.textProperty().addListener((obs, old, newVal) -> {
            filterTable(idFilterField.getText(), nameFilterField.getText(), xFilterField.getText(), yFilterField.getText(), minmalFilterField.getText(), tunedFilterField.getText(), diffFilterField.getValue(), datePicker.getValue(), ownerFilterField.getText());
        });
        
        tunedFilterField.textProperty().addListener((obs, old, newVal) -> {
            filterTable(idFilterField.getText(), nameFilterField.getText(), xFilterField.getText(), yFilterField.getText(), minmalFilterField.getText(), tunedFilterField.getText(), diffFilterField.getValue(), datePicker.getValue(), ownerFilterField.getText());
        });

        diffFilterField.valueProperty().addListener((obs, old, newVal) -> {
            filterTable(idFilterField.getText(), nameFilterField.getText(), xFilterField.getText(), yFilterField.getText(), minmalFilterField.getText(), tunedFilterField.getText(), diffFilterField.getValue(), datePicker.getValue(), ownerFilterField.getText());
        });

        datePicker.valueProperty().addListener((obs, old, newVal) -> {
            filterTable(idFilterField.getText(), nameFilterField.getText(), xFilterField.getText(), yFilterField.getText(), minmalFilterField.getText(), tunedFilterField.getText(), diffFilterField.getValue(), datePicker.getValue(), ownerFilterField.getText());
        });

        ownerFilterField.textProperty().addListener((obs, old, newVal) -> {
            filterTable(idFilterField.getText(), nameFilterField.getText(), xFilterField.getText(), yFilterField.getText(), minmalFilterField.getText(), tunedFilterField.getText(), diffFilterField.getValue(), datePicker.getValue(), ownerFilterField.getText());
        });


        HBox tableFilters = new HBox(idFilterField, nameFilterField, xFilterField, yFilterField, minmalFilterField, tunedFilterField, diffFilterField, datePicker, ownerFilterField);

        VBox menuAndTable = new VBox(GuiFabric.generateTopMenu(mainAppVizualScene, mainAppCollectScene, mainAppCommandsScene, mainAppProfileScene), labWorksTable, tableFilters);
        rootCollect.getChildren().add(menuAndTable);

        


        // Найстройка сцены профиля


        Button profileHeader = new Button(bundle.getString("profilebut"));
        profileHeader.getStyleClass().add("profile-header");
        profileHeader.setTextAlignment(TextAlignment.CENTER);
        VBox.setMargin(profileHeader, new Insets(0, 0, 30, 0));
        

        Button userNameLabel = new Button(user.getUsername());
        userNameLabel.getStyleClass().add("profile-field");
        userNameLabel.setTextAlignment(TextAlignment.CENTER);
        VBox.setMargin(userNameLabel ,new Insets(0, 0, 20, 0));

        Button nameLabel = new Button(user.getProfile().getName());
        nameLabel.getStyleClass().add("profile-field");
        VBox.setMargin(nameLabel ,new Insets(0, 0, 20, 0));

        Button heightLabel = new Button(user.getProfile().getHeigth().toString());
        heightLabel.getStyleClass().add("profile-field");
        VBox.setMargin(heightLabel ,new Insets(0, 0, 20, 0));

        Button eyeColorLabel = new Button(user.getProfile().getEyeColor().toString());
        eyeColorLabel.getStyleClass().add("profile-field");
        VBox.setMargin(eyeColorLabel ,new Insets(0, 0, 20, 0));

        Button exitButton = new Button(bundle.getString("exitbut"));
        exitButton.getStyleClass().add("command-button");
        VBox.setMargin(exitButton ,new Insets(0, 0, 20, 0));

        Button langButton = new Button(bundle.getString("langbut"));
        langButton.getStyleClass().add("command-button");
        langButton.setOnAction(event -> {
            Stage changeLangStage = new Stage();
            changeLangStage.setTitle(bundle.getString("langbut"));

            Label langLabel = new Label("Выберите язык");
            langLabel.getStyleClass().add("command-header-label");
            
            ComboBox<Language> langField = new ComboBox<>();
            langField.getItems().addAll(Language.values());
            langField.setValue(language);
            VBox.setMargin(langField, new Insets(25));
            langField.setMinWidth(150);

            Button submitLang = new Button(bundle.getString("submitbut"));
            submitLang.getStyleClass().add("menu-button");
            submitLang.setOnAction(eventIn -> {
                if (langField.getValue().equals(Language.RUSSIAN)) {
                    locale = new Locale("ru");
                    language = Language.RUSSIAN;
	                bundle = ResourceBundle.getBundle("locale/all", locale);
                } else if (langField.getValue().equals(Language.ENGLISH)) {
                    locale = new Locale("en");
                    language = Language.ENGLISH;
	                bundle = ResourceBundle.getBundle("locale/all", locale);
                } else if (langField.getValue().equals(Language.ALBANIAN)) {
                    locale = new Locale("alb");
                    language = Language.ALBANIAN;
	                bundle = ResourceBundle.getBundle("locale/all", locale);
                } else if (langField.getValue().equals(Language.SERBIAN)) {
                    locale = new Locale("sr");
                    language = Language.SERBIAN;
	                bundle = ResourceBundle.getBundle("locale/all", locale);
                }
                ClientApp.getUser().setLanguage(language);
                changeLangStage.close();
                mainAppStage.close();
                createMainStage();
            });

            VBox langVBox = new VBox(langLabel, langField, submitLang);
            langVBox.setAlignment(Pos.TOP_CENTER);
            StackPane rootLang = new StackPane(langVBox);
            rootLang.setAlignment(Pos.CENTER);
            Scene langScene = new Scene(rootLang, 360, 180);

            langScene.getStylesheets().add("/css/app.css");
            changeLangStage.setScene(langScene);

            changeLangStage.show();

        });

        exitButton.setOnAction(event -> {
            ClientApp.mainAppStage.close();
            ClientApp.user = null;
            collChecker.interrupt();
            ClientApp.createAuthStage();
        });


        VBox profileInfo = new VBox(profileHeader, userNameLabel, nameLabel, heightLabel, eyeColorLabel);
        VBox.setMargin(profileInfo, new Insets(30));
        profileInfo.getStyleClass().add("profile-info-container");
        profileInfo.setAlignment(Pos.TOP_CENTER);
        

        VBox headerAndProfile = new VBox(GuiFabric.generateTopMenu(mainAppVizualScene, mainAppCollectScene, mainAppCommandsScene, mainAppProfileScene), profileInfo, exitButton, langButton);
        rootProfile.getChildren().add(headerAndProfile);
        headerAndProfile.setAlignment(Pos.TOP_CENTER);

        
        mainAppVizualScene.getStylesheets().add("/css/app.css");
        mainAppCollectScene.getStylesheets().add("/css/app.css");
        mainAppCommandsScene.getStylesheets().add("/css/app.css");
        mainAppProfileScene.getStylesheets().add("/css/app.css");

        ClientApp.mainAppStage.setScene(mainAppVizualScene);

        

        ClientApp.mainAppStage.show();
        mainPaneWithLabWorks.setClip(new Rectangle(mainPaneWithLabWorks.getWidth(), mainPaneWithLabWorks.getHeight()));


    }


    public static synchronized void updateMainPainWithLabWorks() {

        PriorityBlockingQueue<LabWork> labWorksNew = getConnection().getLabWorkCollection();

        List<LabWork> labWorksToAdd = labWorksNew.stream().filter(labWork -> !(currentCollectLabWorks.contains(labWork))).toList();
        
        List<LabWork> labWorksToDelete = currentCollectLabWorks.stream().filter(labWork -> !(labWorksNew.contains(labWork))).toList();

        ObservableList<Node> labWorkIcons = mainPaneWithLabWorks.getChildren();
        Iterator<Node> iterator = labWorkIcons.iterator();
        while (iterator.hasNext()) {
            Node icon = iterator.next();
            for (LabWork labWork : labWorksToDelete) {
                if (Long.valueOf(icon.getId()).equals(labWork.getId())) {
                    iterator.remove();
                }
            }
        }

        ArrayList<Canvas> labWorksCanvases = GuiFabric.generateLabWorksIcons(labWorksToAdd);

        for (Canvas labWork : labWorksCanvases) {
            labWork.setLayoutX(labWork.getLayoutX()-xCoordCurrent[0]);
            labWork.setLayoutY(labWork.getLayoutY()-yCoordCurrent[0]);
            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2),labWork);
            fadeTransition.setFromValue(0);
            fadeTransition.setToValue(1);
            fadeTransition.setDelay(Duration.seconds(0.75));
            labWork.setOpacity(0);
            mainPaneWithLabWorks.getChildren().add(labWork);
            fadeTransition.play();
        }
        ClientApp.currentCollectLabWorks = labWorksNew;
    }

    public static void createCommandStage(Command command, boolean quickMode) {

        ClientApp.commandStage = new Stage();
        ClientApp.commandStage.setTitle("Collection Command");



        command.setUser(ClientApp.user);

        StackPane rootCommandStage = new StackPane();
        rootCommandStage.setAlignment(Pos.TOP_CENTER);

        Label descrHeader = new Label(bundle.getString("comdescr"));

        descrHeader.getStyleClass().add("command-header-label");
        VBox.setMargin(descrHeader, new Insets(15, 15, 15, 15));

        Label descrText = new Label(getCommandDescription(command));
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
        Label scriptInfo = new Label();

        if (command.isRequireId() && !quickMode) {
            FormValidator.addNumberValidator(idField);
            idField.setPromptText(bundle.getString("fielddescrid"));
            commandInfoAndSet.getChildren().add(idField);
            commandInfoAndSet.getStyleClass().add("command-field");
        }
        if (command.isRequireFile()) {
            Button chooseFileButton = new Button(bundle.getString("fielddescrscript"));
            chooseFileButton.getStyleClass().add("menu-button");
            chooseFileButton.setOnAction(event -> {
                FileChooser fileChooser = new FileChooser();
                scriptFile[0] = fileChooser.showOpenDialog(ClientApp.commandStage);
                if (scriptFile[0] != null) {
                    command.setFile(scriptFile[0]);
                    scriptInfo.setText(((CommandExecute)command).pull(bundle).getMessage());
                    selectedFilePath.setText(scriptFile[0].getPath());
                }
            });
            commandInfoAndSet.getChildren().add(chooseFileButton);
            commandInfoAndSet.getChildren().add(selectedFilePath);
            commandInfoAndSet.getChildren().add(scriptInfo);
        }
        if (command.isRequireLabWork()) {
            Label newLabWorkLabel = new Label(bundle.getString("newvalueslab"));
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

            if (quickMode) {
                LabWork labWorkNew = command.getLabWorkNew();
                nameField.setText(labWorkNew.getName());
                xCoordField.setText(Long.valueOf(labWorkNew.getCoordinates().getX()).toString());
                yCoordField.setText(Long.valueOf(labWorkNew.getCoordinates().getY()).toString());
                minimalPointField.setText(Long.valueOf(labWorkNew.getMinimalPoint()).toString());
                tunedInWorksField.setText(Long.valueOf(labWorkNew.getTunedInWorks()).toString());
                difficultyCheckBox.setValue(labWorkNew.getDifficulty());
            }

            commandInfoAndSet.getChildren().addAll(newLabWorkLabel, nameField, coordContainer, minimalPointField, tunedInWorksField, difficultyCheckBox);
        }

        VBox errorsBox = new VBox();
        errorsBox.getStyleClass().add("errors-box");
        errorsBox.setAlignment(Pos.TOP_CENTER);

        Button submiButton = new Button(bundle.getString("submitbut"));
        submiButton.getStyleClass().add("command-button");
        VBox.setMargin(submiButton, new Insets(25, 0, 15, 0));

        Button backButton = new Button(bundle.getString("backbut"));
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
        

        Button closeCommandResult = new Button(bundle.getString("closebut"));
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


            if (command.isRequireId() && !quickMode) {
                if (idField.getText() == "") {
                    errors.add(new Label(bundle.getString("errorid")));
                } else {
                    command.setId(idField.getText());
                }
            }
            if (command.isRequireFile()) {
                if (scriptFile[0] == null) {
                    errors.add(new Label(bundle.getString("errorscr")));
                } else {
                    command.setFile(scriptFile[0]);
                }
            }
            if (command.isRequireLabWork()) {
                ArrayList<Label> labWorkErrors = FormValidator.validateLabWork(nameField.getText(), xCoordField.getText(), yCoordField.getText(), minimalPointField.getText(), tunedInWorksField.getText(), difficultyCheckBox.getValue());
                errors.addAll(labWorkErrors);
                if (errors.size() == 0) {
                    LabWork labWorkUpdate = new LabWork(nameField.getText(), new Coordinates(Long.parseLong(xCoordField.getText()), Long.parseLong(yCoordField.getText())), new Date(), !minimalPointField.getText().equals("") ? Long.parseLong(minimalPointField.getText()) : null, !tunedInWorksField.getText().equals("") ? Long.parseLong(tunedInWorksField.getText()) : null, difficultyCheckBox.getValue(), ClientApp.user.getProfile());
                    labWorkUpdate.setOwner(ClientApp.user);
                    command.setLabWorkNew(labWorkUpdate);
                    command.setUser(ClientApp.user);
                }
            }

            if (errors.size() == 0) {
                ServerSignal ServerSignal = ClientApp.getConnection().executeCommandOnServer(command, true);
                if (ServerSignal == null) {
                    return;
                }
                if (!ServerSignal.isSucces()) {
                    createErrorStage(ServerSignal.getMessage(), bundle.getString("closebut"), innerEvent -> {
                        ClientApp.getErrorStage().close();
                    }, false);
                } else {
                    resultHeader.setText(bundle.getString("commsucc"));
                    resultText.setText(ServerSignal.getMessage());
                    ClientApp.commandStage.setScene(commandResultScene);
                    updateMainPainWithLabWorks();
                    updateLabWorkTable();
                }

            } else {
                errorsBox.getChildren().addAll(errors);
            }
        });

        ClientApp.commandStage.show();

    }


    public static void filterTable(String idFilterField, String nameFilterField, String xFilterField, String yFilterField, String minmalFilterField, String tunedFilterField, Difficulty diffFilterField, LocalDate datePicker, String ownerFilterField) {
        List<LabWork> resultCollection = new ArrayList<>(currentCollectLabWorks);

        if (idFilterField != "") {
            resultCollection = resultCollection.stream().filter(x -> x.getId().equals(Long.valueOf(idFilterField))).toList();
        }
        if (nameFilterField != "") {
            resultCollection = resultCollection.stream().filter(x -> x.getName().contains(nameFilterField)).toList();
        }
        if (xFilterField != "") {
            resultCollection = resultCollection.stream().filter(x -> x.getCoordinates().getX() == Long.parseLong(xFilterField)).toList();
        }
        if (yFilterField != "") {
            resultCollection = resultCollection.stream().filter(x -> x.getCoordinates().getY() == Long.parseLong(yFilterField)).toList();
        }
        if (minmalFilterField != "") {
            resultCollection = resultCollection.stream().filter(x -> x.getMinimalPoint() == Long.parseLong(minmalFilterField)).toList();
        }
        if (tunedFilterField != "") {
            resultCollection = resultCollection.stream().filter(x -> x.getTunedInWorks() == Long.parseLong(tunedFilterField)).toList();
        }
        if (diffFilterField != null) {
            resultCollection = resultCollection.stream().filter(x -> x.getDifficulty().equals(diffFilterField)).toList();
        }
        if (datePicker != null) {
            resultCollection = resultCollection.stream().filter(x -> x.getCreationDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().equals(datePicker)).toList();
        }
        if (ownerFilterField != "") {
            resultCollection = resultCollection.stream().filter(x -> x.getOwner().getUsername().contains(ownerFilterField)).toList();
        }

        labWorksTable.getItems().clear();
        labWorksTable.getItems().addAll(resultCollection);

    }

    public static String getCommandDescription(Command command) {
        if (command instanceof CommandAdd) {
            return bundle.getString("commandadd");
        } if (command instanceof CommandClear) {
            return bundle.getString("commandclear");
        } if (command instanceof CommandCountLessAuthor) {
            return bundle.getString("commandlessauthor");
        } if (command instanceof CommandExecute) {
            return bundle.getString("commandexecute");
        } if (command instanceof CommandHead) {
            return bundle.getString("commandhead");
        } if (command instanceof CommandInfo) {
            return bundle.getString("commandinfo");
        } if (command instanceof CommandLook) {
            return bundle.getString("commandlook");
        } if (command instanceof CommandMaxByName) {
            return bundle.getString("commandmaxbyname");
        } if (command instanceof CommandPrintTunedInWorks) {
            return bundle.getString("commandprinttuned");
        } if (command instanceof CommandRemoveById) {
            return bundle.getString("commandremoveid");
        } if (command instanceof CommandRemoveHead) {
            return bundle.getString("commandremovehead");
        } if (command instanceof CommandRemoveLower) {
            return bundle.getString("commandremovelower");
        } if (command instanceof CommandUpdate) {
            return bundle.getString("commandupdate");
        }
        return "";
    }


    public static synchronized void updateLabWorkTable() {
        ObservableList<LabWork> listOfLabworks = labWorksTable.getItems();
        listOfLabworks.clear();
        
        for (LabWork labWork : currentCollectLabWorks) {
            listOfLabworks.add(labWork);
        }
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

    public static void setServer(ServerConnection server) {
        ClientApp.server = server;
    }


    public static String getHost() {
        return host;
    }

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
    public static User getUser() {
		return user;
	}

    public static Stage getCommandStage() {
        return commandStage;
    }

    public static PriorityBlockingQueue<LabWork> getCurrentCollectLabWorks() {
        return currentCollectLabWorks;
    }

    public static void setCurrentCollectLabWorks(PriorityBlockingQueue<LabWork> queue) {
        currentCollectLabWorks = queue;
    }

    public static TableView<LabWork> getLabWorksTable() {
		return labWorksTable;
	}
    
}
