package com.example.cab302javaproject;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

public class LifestyleCalendar extends Application {
    private Stage primaryStage;
    private StackPane rootPane;
    private HashMap<UUID, UserDetails> userDetailsMap;
    private UserDetails loggedInUser;
    private Image image;

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        rootPane = new StackPane();
        userDetailsMap = new HashMap<>();
        Scene scene = new Scene(rootPane, 600, 400);
        stage.setTitle("Lifestyle Calendar!");
        stage.setScene(scene);
        stage.show();

        image = new Image(".../LifestyleCalendarLogo.png");

        showHomePage();
    }

    private void showHomePage() {
        BorderPane homePane = new BorderPane();

        // Image Properties
        ImageView imageView = new ImageView();
        imageView.setFitWidth(200);
        imageView.setFitHeight(150);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setCache(true);

        // Image Setting
         // image = new Image(".../LifestyleCalendarLogo.png");
         imageView.setImage(image);

        homePane.setTop(imageView);
        BorderPane.setAlignment(imageView, Pos.TOP_CENTER);

        VBox buttonBox = new VBox(10);
        buttonBox.setPadding(new Insets(10));
        buttonBox.setAlignment(Pos.CENTER);

        Button loginButton = new Button("LOGIN");
        loginButton.setOnAction(event -> showLoginScreen());
        Button signUpButton = new Button("SIGN UP");
        signUpButton.setOnAction(event -> showSignUpScreen());

        buttonBox.getChildren().addAll(loginButton, signUpButton);
        homePane.setCenter(buttonBox);

        rootPane.getChildren().setAll(homePane);
    }

    private void showLoginScreen() {
        BorderPane loginPane = new BorderPane();

        // Image Properties
        ImageView imageView = new ImageView();
        imageView.setFitWidth(200);
        imageView.setFitHeight(150);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setCache(true);

        // Image Setting
        // image = new Image(".../LifestyleCalendarLogo.png");
        imageView.setImage(image);

        VBox loginBox = new VBox(10);
        loginBox.setPadding(new Insets(10));
        loginBox.setAlignment(Pos.CENTER);

        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        TextField passwordField = new TextField();
        passwordField.setPromptText("Password");

       // Button loginButton = new Button("LOGIN");

        HBox buttonsBox = new HBox(10);
        Button loginButton = new Button("LOGIN");
        Button cancelButton = new Button("CANCEL");
        cancelButton.setOnAction(event -> showHomePage());
        buttonsBox.getChildren().addAll(loginButton, cancelButton);
        loginButton.setOnAction(event -> {
            String email = emailField.getText();
            String password = passwordField.getText();

            if (authenticateUser(email, password)) {
                showProfileEditScreen();
            } else {
                showAlert("Invalid email or password.");
            }
        });

        loginBox.getChildren().addAll(emailField, passwordField, buttonsBox);//loginButton);
        loginPane.setCenter(loginBox);

        rootPane.getChildren().setAll(loginPane);
    }

    private void showSignUpScreen() {
        BorderPane signUpPane = new BorderPane();

        // Image Properties
        ImageView imageView = new ImageView();
        imageView.setFitWidth(200);
        imageView.setFitHeight(150);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setCache(true);

        // Image Setting
        // image = new Image(".../LifestyleCalendarLogo.png");
        imageView.setImage(image);

        signUpPane.setTop(imageView);
        BorderPane.setAlignment(imageView, Pos.TOP_CENTER);

        VBox signUpBox = new VBox(10);
        signUpBox.setPadding(new Insets(10));
        signUpBox.setAlignment(Pos.CENTER);

        Label questionLabel = new Label("Please answer the following questions:");
        ToggleGroup accountTypeGroup = new ToggleGroup(); // Create a ToggleGroup
        HBox accountTypeBox = new HBox(10);
        accountTypeBox.setAlignment(Pos.CENTER); // Center the HBox
        ToggleButton personalButton = new ToggleButton("Personal");
        personalButton.setToggleGroup(accountTypeGroup); // Associate the button with the ToggleGroup
        ToggleButton managerButton = new ToggleButton("Manager");
        managerButton.setToggleGroup(accountTypeGroup);
        ToggleButton employeeButton = new ToggleButton("Employee");
        employeeButton.setToggleGroup(accountTypeGroup);
        accountTypeBox.getChildren().addAll(personalButton, managerButton, employeeButton);
        TextField nameField = new TextField();
        nameField.setPromptText("Name");
        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        TextField passwordField = new TextField();
        passwordField.setPromptText("Password");


        HBox buttonsBox = new HBox(10);
        Button signUpButton = new Button("SIGN UP");
        Button cancelButton = new Button("CANCEL");
        cancelButton.setOnAction(event -> showHomePage());
        buttonsBox.getChildren().addAll(signUpButton, cancelButton);


        //Button signUpButton = new Button("SIGN UP");
        signUpButton.setOnAction(event -> {
            String name = nameField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();

            AtomicReference<String> atomicSelectedAccountType = new AtomicReference<>();
            accountTypeGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    atomicSelectedAccountType.set(((ToggleButton) newValue).getText());
                }
            });
            String selectedAccountType = atomicSelectedAccountType.get();

            if (userDetailsMap.containsKey(email)) {
                showAlert("Email already exists.");
            } else if (selectedAccountType == null) {
                showAlert("Please select an account type.");
            } else {
//                UUID uuid = UUID.randomUUID();
//                UserDetails userDetails = new UserDetails(uuid, name, email, password, selectedAccountType,Optional.empty());

                UUID uuid = UUID.randomUUID();
                UserDetails userDetails;

                if (selectedAccountType.equals("Manager")) {
                    // Create a popup for Manager account type
                    Stage popupStage = new Stage();
                    VBox popupVBox = new VBox();
                    popupVBox.setAlignment(Pos.CENTER);
                    popupVBox.setSpacing(10);

                    Label popupLabel = new Label("Below is your linking code for your employees to connect to you,\nand allow you to add to or view their calendars");
                    popupLabel.setTextAlignment(TextAlignment.CENTER);
                    popupLabel.setWrapText(true);

                    UUID linkingCode = UUID.randomUUID();
                    Label linkingCodeLabel = new Label(linkingCode.toString());

                    Button okButton = new Button("OK");
                    okButton.setOnAction(e -> popupStage.close());

                    popupVBox.getChildren().addAll(popupLabel, linkingCodeLabel, okButton);

                    Scene popupScene = new Scene(popupVBox);
                    popupStage.setScene(popupScene);
                    popupStage.showAndWait();

                    //userDetails = new UserDetails(uuid, name, email, password, selectedAccountType, linkingCode);
                    userDetails = new UserDetails(uuid, name, email, password, selectedAccountType, Optional.ofNullable(linkingCode));
                } else if (selectedAccountType.equals("Employee")) {
                    // Create a popup for Employee account type
                    Stage popupStage = new Stage();
                    VBox popupVBox = new VBox();
                    popupVBox.setAlignment(Pos.CENTER);
                    popupVBox.setSpacing(10);

                    Label popupLabel = new Label("Do you have a linking code to a manager?");

                    Button yesButton = new Button("Yes");
                    Button noButton = new Button("No");

                    popupVBox.getChildren().addAll(popupLabel, yesButton, noButton);

                    Scene popupScene = new Scene(popupVBox);
                    popupStage.setScene(popupScene);
                    popupStage.showAndWait();

                    if (yesButton.isArmed()) {
                        // Create a new popup for entering the linking code
                        Stage linkingCodeStage = new Stage();
                        VBox linkingCodeVBox = new VBox();
                        linkingCodeVBox.setAlignment(Pos.CENTER);
                        linkingCodeVBox.setSpacing(10);

                        Label linkingCodeLabel = new Label("Enter the manager's linking code:");
                        TextField linkingCodeField = new TextField();

                        Button submitButton = new Button("Submit");
                        Button cancelPopUpButton = new Button("Cancel");

                        linkingCodeVBox.getChildren().addAll(linkingCodeLabel, linkingCodeField, submitButton, cancelPopUpButton);

                        Scene linkingCodeScene = new Scene(linkingCodeVBox);
                        linkingCodeStage.setScene(linkingCodeScene);
                        linkingCodeStage.showAndWait();

                        if (submitButton.isArmed()) {
                            String linkingCodeString = linkingCodeField.getText();
                            UUID linkingCode = UUID.fromString(linkingCodeString);
                            userDetails = new UserDetails(uuid, name, email, password, selectedAccountType, Optional.ofNullable(linkingCode));
//                            if (linkingCode != null) {
//                                userDetails = new UserDetails(uuid, name, email, password, selectedAccountType, linkingCode);
//                            }else{
//                                userDetails = new UserDetails(uuid, name, email, password, selectedAccountType, Optional.empty());
//                            }
                        } else {
                            userDetails = new UserDetails(uuid, name, email, password, selectedAccountType, Optional.empty());
                        }
                    } else {
                        userDetails = new UserDetails(uuid, name, email, password, selectedAccountType, Optional.empty());
                    }
                } else {
                    userDetails = new UserDetails(uuid, name, email, password, selectedAccountType, Optional.empty());
                }

                userDetailsMap.put(uuid, userDetails);
                showAlert("Sign up successful.");
                showLoginScreen();
            }
        });

        signUpBox.getChildren().addAll(questionLabel, accountTypeBox, nameField, emailField, passwordField, buttonsBox); //signUpButton);
        signUpPane.setCenter(signUpBox);

        rootPane.getChildren().setAll(signUpPane);
    }

    private void showProfileEditScreen() {
        BorderPane updatePane = new BorderPane();

        // Image Properties
        ImageView imageView = new ImageView();
        imageView.setFitWidth(200);
        imageView.setFitHeight(150);
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setCache(true);

        // Image Setting
        //image = new Image(".../LifestyleCalendarLogo.png");
        imageView.setImage(image);

        updatePane.setTop(imageView);
        BorderPane.setAlignment(imageView, Pos.TOP_CENTER);

        VBox updateBox = new VBox(10);
        updateBox.setPadding(new Insets(10));
        updateBox.setAlignment(Pos.CENTER);

        Label companyCodeLabel = new Label("Company Code: Lorem ipsum dolor sit");
        Label updateDetailsLabel = new Label("Update your details below:");
        TextField nameField = new TextField();
        nameField.setText(loggedInUser.getName());
        TextField emailField = new TextField();
        emailField.setText(loggedInUser.getEmail());
        TextField passwordField = new TextField();
        passwordField.setText(loggedInUser.getPassword());
        HBox buttonsBox = new HBox(10);
        Button signUpButton = new Button("UPDATE");
        Button cancelButton = new Button("CANCEL");
        cancelButton.setOnAction(event -> showHomePage());
        //signup button doing nothing??
        buttonsBox.getChildren().addAll(signUpButton, cancelButton);

        updateBox.getChildren().addAll(companyCodeLabel, updateDetailsLabel, nameField, emailField, passwordField, buttonsBox);
        updatePane.setCenter(updateBox);

        rootPane.getChildren().setAll(updatePane);
    }

    private boolean authenticateUser(String email, String password) {
        for (UserDetails userDetails : userDetailsMap.values()) {
            if (userDetails.getEmail().equals(email) && userDetails.getPassword().equals(password)) {
                loggedInUser = userDetails;
                return true;
            }
        }
        return false;
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private static class UserDetails {
        private final UUID uuid;
        private final String name;
        private final String email;
        private final String password;
        private final String accountType;
        private final Optional<UUID> linkingCode;

        public UserDetails(UUID uuid, String name, String email, String password, String accountType, Optional<UUID> linkingCode) {
            this.uuid = uuid;
            this.name = name;
            this.email = email;
            this.password = password;
            this.accountType = accountType;
            this.linkingCode = linkingCode;
        }

        public UUID getUuid() {
            return uuid;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }
    }

    public static void main(String[] args) {
        launch();
    }
}