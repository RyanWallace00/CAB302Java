package com.example.cab302javaproject;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.io.File;
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

        image = new Image("C:\\Users\\ryanwallace\\IdeaProjects\\CAB302Java\\src\\main\\java\\com\\example\\cab302javaproject\\LifestyleCalendarLogo.png"); //new Image("jetbrains://idea/navigate/reference?project=CAB302Java&path=com/example/cab302javaproject/LifestyleCalendarLogo.png");

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
        imageView.setImage(image);

        loginPane.setTop(imageView);
        BorderPane.setAlignment(imageView, Pos.TOP_CENTER);

        VBox centerBox = new VBox(10);
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setPadding(new Insets(10));

        Label informationLabel = new Label("Please enter your account details below:");
        informationLabel.setFont(new Font(15));
        informationLabel.setAlignment(Pos.CENTER);

        VBox formBox = new VBox(5);
        Label emailLabel = new Label("Email");
        TextField emailField = new TextField();
        Label passwordLabel = new Label("Password");
        TextField passwordField = new TextField();
        formBox.getChildren().addAll(emailLabel, emailField, passwordLabel, passwordField);
        formBox.setAlignment(Pos.CENTER_LEFT);

        HBox buttonsBox = new HBox(10);
        Button loginButton = new Button("LOGIN");
        Button cancelButton = new Button("CANCEL");
        buttonsBox.getChildren().addAll(loginButton, cancelButton);
        buttonsBox.setAlignment(Pos.CENTER);

        cancelButton.setOnAction(event -> showHomePage()); // Set action for cancel button

        loginButton.setOnAction(event -> {
            String email = emailField.getText();
            String password = passwordField.getText();

            if (authenticateUser(email, password)) {
                showProfileEditScreen();
            } else {
                showAlert("Invalid email or password.");
            }
        });

        centerBox.getChildren().addAll(informationLabel, formBox, buttonsBox);
        loginPane.setCenter(centerBox);

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
        imageView.setImage(image);

        signUpPane.setTop(imageView);
        BorderPane.setAlignment(imageView, Pos.TOP_CENTER);

        VBox signUpBox = new VBox(10);
        signUpBox.setPadding(new Insets(10));
        signUpBox.setAlignment(Pos.CENTER);

        Label questionLabel = new Label("Please answer the following questions:");
        questionLabel.setFont(new Font(15));
        Label accountTypeLabel = new Label("Select your account type below:");
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

        VBox formBox = new VBox(5);

        Label nameLabel = new Label("Name");
        TextField nameField = new TextField();
        //nameField.setPromptText("Name");
        Label emailLabel = new Label("Email");
        TextField emailField = new TextField();
        //emailField.setPromptText("Email");
        Label passwordLabel = new Label("Password");
        TextField passwordField = new TextField();
       // passwordField.setPromptText("Password");

        formBox.getChildren().addAll(nameLabel, nameField, emailLabel, emailField, passwordLabel, passwordField);
        formBox.setAlignment(Pos.CENTER_LEFT);

        HBox buttonsBox = new HBox(10);
        buttonsBox.setAlignment(Pos.CENTER);
        Button signUpButton = new Button("SIGN UP");
        Button cancelButton = new Button("CANCEL");
        cancelButton.setOnAction(event -> showHomePage());
        buttonsBox.getChildren().addAll(signUpButton, cancelButton);

        signUpButton.setOnAction(event -> {
            final String name = nameField.getText();
            final String email = emailField.getText();
            final String password = passwordField.getText();

            AtomicReference<String> atomicSelectedAccountType = new AtomicReference<>();
            Toggle selectedToggle = accountTypeGroup.getSelectedToggle();
            if (selectedToggle != null) {
                atomicSelectedAccountType.set(((ToggleButton) selectedToggle).getText());
            }

            accountTypeGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue != null) {
                    atomicSelectedAccountType.set(((ToggleButton) newValue).getText());
                }
            });

            final String selectedAccountType = atomicSelectedAccountType.get();

            if (userDetailsMap.containsKey(email)) {
                showAlert("Email already exists.");
            } else if (selectedAccountType == null) {
                showAlert("Please select an account type.");
            } else {
                final UUID userId = UUID.randomUUID();
                Optional<UUID> linkingCode = Optional.empty();

                if (selectedAccountType.equals("Manager")) {
                    // Create a popup for Manager account type
                    Stage popupStage = new Stage();
                    VBox popupVBox = new VBox();
                    popupVBox.setAlignment(Pos.CENTER);
                    popupVBox.setSpacing(10);

                    Label popupLabel = new Label("Below is your linking code for your employees to connect to you,\nand allow you to add to or view their calendars");
                    popupLabel.setTextAlignment(TextAlignment.CENTER);
                    popupLabel.setWrapText(true);

                    final UUID managerLinkingCode = UUID.randomUUID();
                    Label linkingCodeLabel = new Label(managerLinkingCode.toString());

                    Button okButton = new Button("OK");
                    okButton.setOnAction(e -> {
                        popupStage.close();
                        //linkingCode = Optional.ofNullable(managerLinkingCode);
                        UserDetails userDetails = new UserDetails(userId, name, email, password, selectedAccountType, Optional.ofNullable(managerLinkingCode)); //linkingCode);
                        userDetailsMap.put(userId, userDetails);
                        showAlert("Sign up successful.");
                        showLoginScreen();
                    });

                    popupVBox.getChildren().addAll(popupLabel, linkingCodeLabel, okButton);

                    Scene popupScene = new Scene(popupVBox);
                    popupStage.setScene(popupScene);
                    popupStage.showAndWait();
                } else if (selectedAccountType.equals("Employee")) {
                    // Create a popup for Employee account type
                    Stage popupStage = new Stage();
                    VBox popupVBox = new VBox();
                    popupVBox.setAlignment(Pos.CENTER);
                    popupVBox.setSpacing(10);

                    Label popupLabel = new Label("Do you have a linking code to a manager?");

                    Button yesButton = new Button("Yes");
                    Button noButton = new Button("No");

                    yesButton.setOnAction(event2 -> {
                        // Create a new popup for entering the linking code
                        Stage linkingCodeStage = new Stage();
                        VBox linkingCodeVBox = new VBox();
                        linkingCodeVBox.setAlignment(Pos.CENTER);
                        linkingCodeVBox.setSpacing(10);

                        Label linkingCodeLabel = new Label("Enter the manager's linking code:");
                        TextField linkingCodeField = new TextField();

                        Button submitButton = new Button("Submit");
                        Button cancelPopUpButton = new Button("Cancel");

                        submitButton.setOnAction(event3 -> {
                            String linkingCodeString = linkingCodeField.getText();
                            UUID managerLinkingCode = null;
                            try {
                                managerLinkingCode = UUID.fromString(linkingCodeString);
                            } catch (IllegalArgumentException e) {
                                showAlert("Invalid linking code format.");
                                return;
                            }
                            linkingCodeStage.close();
                            UserDetails userDetails = new UserDetails(userId, name, email, password, selectedAccountType, Optional.ofNullable(managerLinkingCode));
                            userDetailsMap.put(userId, userDetails);
                            showAlert("Sign up successful.");
                            popupStage.close();
                            showLoginScreen();
                        });

                        cancelPopUpButton.setOnAction(event3 -> {
                            linkingCodeStage.close();
                        });

                        linkingCodeVBox.getChildren().addAll(linkingCodeLabel, linkingCodeField, submitButton, cancelPopUpButton);

                        Scene linkingCodeScene = new Scene(linkingCodeVBox);
                        linkingCodeStage.setScene(linkingCodeScene);
                        linkingCodeStage.showAndWait();
                    });

                    noButton.setOnAction(event2 -> {
                        UserDetails userDetails = new UserDetails(userId, name, email, password, selectedAccountType, linkingCode);
                        userDetailsMap.put(userId, userDetails);
                        showAlert("Sign up successful.");
                        popupStage.close();
                        showLoginScreen();
                    });

                    popupVBox.getChildren().addAll(popupLabel, yesButton, noButton);

                    Scene popupScene = new Scene(popupVBox);
                    popupStage.setScene(popupScene);
                    popupStage.showAndWait();
                } else {
                    UserDetails userDetails = new UserDetails(userId, name, email, password, selectedAccountType, linkingCode);
                    userDetailsMap.put(userId, userDetails);
                    showAlert("Sign up successful.");
                    showLoginScreen();
                }
            }
        });

        signUpBox.getChildren().addAll(questionLabel, accountTypeLabel, accountTypeBox, formBox, buttonsBox);

        //signUpBox.getChildren().addAll(questionLabel, accountTypeLabel, accountTypeBox, nameField, emailField, passwordField, buttonsBox);

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
        imageView.setImage(image);

        updatePane.setTop(imageView);
        BorderPane.setAlignment(imageView, Pos.TOP_CENTER);

        VBox updateBox = new VBox(10);
        updateBox.setPadding(new Insets(10));
        updateBox.setAlignment(Pos.CENTER);

        Label accountSettingsLabel = new Label("Account Settings");
        accountSettingsLabel.setFont(new Font(30));//15));
        //Label companyCodeLabel = new Label("Company Code: Lorem ipsum dolor sit");
        Label companyCodeLabel = new Label("Company Code:");
        TextField companyCodeField = new TextField();

        HBox companyBox = new HBox(10);
        companyBox.getChildren().addAll(companyCodeLabel, companyCodeField);
        companyBox.setAlignment(Pos.CENTER);

        // Add a gap between the labels
        // Region gap = new Region();
        // gap.setPrefHeight(60); // Height of gap
        new Label();

        Label updateDetailsLabel = new Label("Update your details below:");
        updateDetailsLabel.setFont(new Font(15));


        VBox formBox = new VBox(5);
        Label nameLabel = new Label("Name");

        TextField nameField = new TextField();
        nameField.setText(loggedInUser.getName());
        Label emailLabel = new Label("Email");
        TextField emailField = new TextField();
        emailField.setText(loggedInUser.getEmail());
        Label passwordLabel = new Label("Password");
        TextField passwordField = new TextField();
        passwordField.setText(loggedInUser.getPassword());

        formBox.getChildren().addAll(nameLabel, nameField, emailLabel, emailField, passwordLabel, passwordField);
        formBox.setAlignment(Pos.CENTER_LEFT);

        HBox buttonsBox = new HBox(10);
        buttonsBox.setAlignment(Pos.CENTER);
        Button updateButton = new Button("UPDATE");
        Button cancelButton = new Button("CANCEL");
        cancelButton.setOnAction(event -> showHomePage());
        buttonsBox.getChildren().addAll(updateButton, cancelButton);

        updateButton.setOnAction(event -> {
            String name = nameField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();

            UserDetails updatedUserDetails = new UserDetails(loggedInUser.getUuid(), name, email, password, loggedInUser.getAccountType(), loggedInUser.getLinkingCode());
            userDetailsMap.put(loggedInUser.getUuid(), updatedUserDetails);
            loggedInUser = updatedUserDetails;
            showAlert("Details updated successfully.");
        });

        updateBox.getChildren().addAll(accountSettingsLabel, companyBox, updateDetailsLabel, formBox, buttonsBox); //nameField, emailField, passwordField, buttonsBox);
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

        public String getAccountType() {
            return accountType;
        }

        public Optional<UUID> getLinkingCode() {
            return linkingCode;
        }
    }


    public static void main(String[] args) {
        launch();
    }
}