package com.example.cab302javaproject;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.UUID;


public class LifeStyleCalendar extends Application {
    private Stage primaryStage;
    private StackPane rootPane;
    private HashMap<UUID, UserDetails> userDetailsMap;
    private UserDetails loggedInUser;

    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        rootPane = new StackPane();

        Scene scene = new Scene(rootPane, 600, 400);
        stage.setTitle("LifeStyle Calendar!");
        stage.setScene(scene);
        stage.show();

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

        // Add the image here
        // Image image = new Image("path/../../image.jpg");
        // imageView.setImage(image);

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

        // Add the image here
        // Image image = new Image("path/../../image.jpg");
        // imageView.setImage(image);

        VBox loginBox = new VBox(10);
        loginBox.setPadding(new Insets(10));
        loginBox.setAlignment(Pos.CENTER);

        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        TextField passwordField = new TextField();
        passwordField.setPromptText("Password");
        Button loginButton = new Button("LOGIN");
        loginButton.setOnAction(event -> {
            String email = emailField.getText();
            String password = passwordField.getText();

            if (authenticateUser(email, password)) {
                showProfileEditScreen();
            } else {
                showAlert("Invalid email or password.");
            }
        });

        loginBox.getChildren().addAll(emailField, passwordField, loginButton);
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

        // Add the image here
        // Image image = new Image("path/to/your/image.jpg");
        // imageView.setImage(image);

        signUpPane.setTop(imageView);
        BorderPane.setAlignment(imageView, Pos.TOP_CENTER);

        VBox signUpBox = new VBox(10);
        signUpBox.setPadding(new Insets(10));
        signUpBox.setAlignment(Pos.CENTER);

        Label questionLabel = new Label("Please answer the following questions:");
        HBox accountTypeBox = new HBox(10);
        Button personalButton = new Button("Personal");
        Button managerButton = new Button("Manager");
        Button employeeButton = new Button("Employee");
        accountTypeBox.getChildren().addAll(personalButton, managerButton, employeeButton);
        TextField nameField = new TextField();
        nameField.setPromptText("Name");
        TextField emailField = new TextField();
        emailField.setPromptText("Email");
        TextField passwordField = new TextField();
        passwordField.setPromptText("Password");
        Button signUpButton = new Button("SIGN UP");
        signUpButton.setOnAction(event -> {
            String name = nameField.getText();
            String email = emailField.getText();
            String password = passwordField.getText();

            if (userDetailsMap.containsKey(email)) {
                showAlert("Email already exists.");
            } else {
                UUID uuid = UUID.randomUUID();
                UserDetails userDetails = new UserDetails(uuid, name, email, password);
                userDetailsMap.put(uuid, userDetails);
                showAlert("Sign up successful.");
                showLoginScreen();
            }
        });

        signUpBox.getChildren().addAll(questionLabel, accountTypeBox, nameField, emailField, passwordField, signUpButton);
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

        // Add the image here
        // Image image = new Image("path/to/your/image.jpg");
        // imageView.setImage(image);

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
        buttonsBox.getChildren().addAll(signUpButton, cancelButton);

        updatePane.setCenter(updateBox); // -- old
        rootPane.getChildren().setAll(updatePane);// -- old
        //updateBox.getChildren ??? - incomplete
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

    public static void main(String[] args) {
        launch();
    }
}