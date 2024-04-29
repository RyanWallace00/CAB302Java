/**
 * LifestyleCalendar, a JavaFX application for promoting and managing a better work/life balance whilst taking into consideration health safety aspects such as eye-strain warnings
 * It includes features like user authentication, profile management, and event scheduling.
 */
package com.example.cab302javaproject; // Declares the package name for the Java class

import javafx.application.Application; // Imports the Application class from the JavaFX library
import javafx.geometry.Insets; // Imports the Insets class from the JavaFX library for creating padding around UI elements
import javafx.geometry.Pos; // Imports the Pos class from the JavaFX library for positioning UI elements
import javafx.scene.Scene; // Imports the Scene class from the JavaFX library for creating the main window
import javafx.scene.control.*; // Imports all classes related to UI controls from the JavaFX library
import javafx.scene.image.ImageView; // Imports the ImageView class from the JavaFX library for displaying images
import javafx.scene.layout.*; // Imports all classes related to UI layout from the JavaFX library
import javafx.scene.text.Font; // Imports the Font class from the JavaFX library for setting text styles
import javafx.scene.text.TextAlignment; // Imports the TextAlignment class from the JavaFX library for setting text alignment
import javafx.stage.Stage; // Imports the Stage class from the JavaFX library for creating the main window
import javafx.scene.image.Image; // Imports the Image class from the JavaFX library for loading images

import java.util.*;
import java.util.concurrent.atomic.AtomicReference; // Imports the AtomicReference class from the Java concurrent package for thread-safe reference handling
import java.io.*; // Imports all classes related to input/output from the Java I/O package
import java.io.Serializable; // Imports the Serializable interface from the Java I/O package for serializing objects
import java.io.FileInputStream; // Imports the FileInputStream class from the Java I/O package for reading from files
import java.io.FileOutputStream; // Imports the FileOutputStream class from the Java I/O package for writing to files
import java.io.ObjectInputStream; // Imports the ObjectInputStream class from the Java I/O package for deserializing objects
import java.io.ObjectOutputStream; // Imports the ObjectOutputStream class from the Java I/O package for serializing objects
import java.time.ZonedDateTime; // Imports the ZonedDateTime class from the Java time package for representing dates and times

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.ComboBox;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.LocalDateTime;
import java.util.UUID;
import java.text.DateFormat;


/**
 * The LifestyleCalendar class extends the Application class and serves as the main entry point for the application.
 */
public class LifestyleCalendar extends Application { // Defines the LifestyleCalendar class which extends the Application class from JavaFX
    private Stage primaryStage; // Declares a private instance variable to hold the primary stage (main window)
    private StackPane rootPane; // Declares a private instance variable to hold the root pane (main container)
    private HashMap<Optional<UUID>, UserDetails> userDetailsMap; // Declares a private instance variable to hold a map of user details keyed by UUID
    private HashMap<Optional<UUID>, CalendarDetails> calendarDetailsMap; // Declares a private instance variable to hold a map of calendar details keyed by UUID
    private UserDetails loggedInUser; // Declares a private instance variable to hold the currently logged-in user's details
    private Image image; // Declares a private instance variable to hold the application logo image

    /**
     * The start method initializes the primary stage and displays the home page.
     * @param stage The primary stage for the JavaFX application.
     */
    @Override // Overrides the start method from the Application class
    public void start(Stage stage) { // Defines the start method which takes a Stage object as a parameter
        primaryStage = stage; // Assigns the passed Stage object to the primaryStage instance variable
        rootPane = new StackPane(); // Creates a new instance of StackPane and assigns it to the rootPane instance variable
        userDetailsMap = new HashMap<Optional<UUID>, UserDetails>(); // Creates a new instance of HashMap and assigns it to the userDetailsMap instance variable
        calendarDetailsMap = new HashMap<Optional<UUID>, CalendarDetails>(); // Creates a new instance of HashMap and assigns it to the calendarDetailsMap instance variable
        Scene scene = new Scene(rootPane, 600, 400); // Creates a new Scene object with the rootPane as the root node and dimensions of 600x400
        stage.setTitle("Lifestyle Calendar!"); // Sets the title of the primary stage
        stage.setScene(scene); // Sets the scene of the primary stage
        stage.show(); // Displays the primary stage
        image = new Image("LifestyleCalendarLogo.png"); // Creates a new Image object by loading the "LifestyleCalendarLogo.png" file
        stage.getIcons().add(image); // Adds the loaded image as an icon to the primary stage
        loadUserData(); // Calls the loadUserData method to load user data from a file
        showHomePage(); // Calls the showHomePage method to display the home page
    }

    /**
     * Displays the home page with login and signup options.
     */
    private void showHomePage() { // Defines a private method to display the home page
        BorderPane homePane = new BorderPane(); // Creates a new instance of BorderPane and assigns it to the homePane variable
        ImageView imageView = new ImageView(); // Creates a new instance of ImageView and assigns it to the imageView variable
        imageView.setFitWidth(200); // Sets the fitted width of the ImageView to 200
        imageView.setFitHeight(150); // Sets the fitted height of the ImageView to 150
        imageView.setPreserveRatio(true); // Sets the ImageView to preserve the aspect ratio of the image
        imageView.setSmooth(true); // Enables smooth scaling for the ImageView
        imageView.setCache(true); // Enables caching for the ImageView
        imageView.setImage(image); // Sets the image of the ImageView to the loaded application logo
        BorderPane.setAlignment(imageView, Pos.TOP_CENTER); // Aligns the ImageView to the top center of the BorderPane
        VBox buttonBox = new VBox(10); // Creates a new instance of VBox with a spacing of 10 and assigns it to the buttonBox variable
        buttonBox.setPadding(new Insets(10)); // Sets the padding of the VBox to 10
        buttonBox.setAlignment(Pos.CENTER); // Aligns the contents of the VBox to the center
        Button loginButton = new Button("LOGIN"); // Creates a new instance of Button with the text "LOGIN" and assigns it to the loginButton variable
        loginButton.setOnAction(event -> showLoginScreen()); // Sets an event handler for the loginButton to call the showLoginScreen method
        Button signUpButton = new Button("SIGN UP"); // Creates a new instance of Button with the text "SIGN UP" and assigns it to the signUpButton variable
        signUpButton.setOnAction(event -> showSignUpScreen()); // Sets an event handler for the signUpButton to call the showSignUpScreen method
        buttonBox.getChildren().addAll(imageView,loginButton, signUpButton); // Adds the ImageView, loginButton, and signUpButton to the buttonBox
        homePane.setCenter(buttonBox); // Sets the center of the homePane to the buttonBox
        rootPane.getChildren().setAll(homePane); // Sets the contents of the rootPane to the homePane
    }

    private void showLoginScreen() { // Defines a private method to display the login screen
        BorderPane loginPane = new BorderPane(); // Creates a new instance of BorderPane and assigns it to the loginPane variable
        ImageView imageView = new ImageView(); // Creates a new instance of ImageView and assigns it to the imageView variable
        imageView.setFitWidth(200); // Sets the fitted width of the ImageView to 200
        imageView.setFitHeight(150); // Sets the fitted height of the ImageView to 150
        imageView.setPreserveRatio(true); // Sets the ImageView to preserve the aspect ratio of the image
        imageView.setSmooth(true); // Enables smooth scaling for the ImageView
        imageView.setCache(true); // Enables caching for the ImageView
        imageView.setImage(image); // Sets the image of the ImageView to the loaded application logo
        BorderPane.setAlignment(imageView, Pos.TOP_CENTER); // Aligns the ImageView to the top center of the BorderPane
        VBox centerBox = new VBox(10); // Creates a new instance of VBox with a spacing of 10 and assigns it to the centerBox variable
        centerBox.setAlignment(Pos.CENTER); // Aligns the contents of the VBox to the center
        centerBox.setPadding(new Insets(10)); // Sets the padding of the VBox to 10
        Label informationLabel = new Label("Please enter your account details below:"); // Creates a new instance of Label with the text "Please enter your account details below:" and assigns it to the informationLabel variable
        informationLabel.setFont(new Font(15)); // Sets the font size of the Label to 15
        informationLabel.setAlignment(Pos.CENTER); // Aligns the text of the Label to the center
        VBox formBox = new VBox(5); // Creates a new instance of VBox with a spacing of 5 and assigns it to the formBox variable
        Label emailLabel = new Label("Email"); // Creates a new instance of Label with the text "Email" and assigns it to the emailLabel variable
        TextField emailField = new TextField(); // Creates a new instance of TextField and assigns it to the emailField variable
        Label passwordLabel = new Label("Password"); // Creates a new instance of Label with the text "Password" and assigns it to the passwordLabel variable
        TextField passwordField = new TextField(); // Creates a new instance of TextField and assigns it to the passwordField variable
        formBox.getChildren().addAll(emailLabel, emailField, passwordLabel, passwordField); // Adds the emailLabel, emailField, passwordLabel, and passwordField to the formBox
        formBox.setAlignment(Pos.CENTER_LEFT); // Aligns the contents of the formBox to the center-left
        HBox buttonsBox = new HBox(10); // Creates a new instance of HBox with a spacing of 10 and assigns it to the buttonsBox variable
        Button loginButton = new Button("LOGIN"); // Creates a new instance of Button with the text "LOGIN" and assigns it to the loginButton variable
        Button cancelButton = new Button("CANCEL"); // Creates a new instance of Button with the text "CANCEL" and assigns it to the cancelButton variable
        buttonsBox.getChildren().addAll(loginButton, cancelButton); // Adds the loginButton and cancelButton to the buttonsBox
        buttonsBox.setAlignment(Pos.CENTER); // Aligns the contents of the buttonsBox to the center
        cancelButton.setOnAction(event -> showHomePage()); // Sets an event handler for the cancelButton to call the showHomePage method
        loginButton.setOnAction(event -> { // Sets an event handler for the loginButton
            String email = emailField.getText(); // Gets the text from the emailField and assigns it to the email variable
            String password = passwordField.getText(); // Gets the text from the passwordField and assigns it to the password variable
            if (authenticateUser(email, password)) { // Calls the authenticateUser method with the email and password, and checks if the user is authenticated
                loadCalendarData(); // Calls the loadCalendarData method to load calendar data from a file
                showCalendarScreen(); // Calls the showCalendarScreen method to display the calendar screen
            } else {
                showAlert("Invalid email or password."); // Displays an alert with the message "Invalid email or password."
            }
        });
        centerBox.getChildren().addAll(imageView, informationLabel, formBox, buttonsBox); // Adds the imageView, informationLabel, formBox, and buttonsBox to the centerBox
        loginPane.setCenter(centerBox); // Sets the center of the loginPane to the centerBox
        rootPane.getChildren().setAll(loginPane); // Sets the contents of the rootPane to the loginPane
    }

    private void showSignUpScreen() { // Defines a private method to display the sign-up screen
        BorderPane signUpPane = new BorderPane(); // Creates a new instance of BorderPane and assigns it to the signUpPane variable
        ImageView imageView = new ImageView(); // Creates a new instance of ImageView and assigns it to the imageView variable
        imageView.setFitWidth(200); // Sets the fitted width of the ImageView to 200
        imageView.setFitHeight(150); // Sets the fitted height of the ImageView to 150
        imageView.setPreserveRatio(true); // Sets the ImageView to preserve the aspect ratio of the image
        imageView.setSmooth(true); // Enables smooth scaling for the ImageView
        imageView.setCache(true); // Enables caching for the ImageView
        imageView.setImage(image); // Sets the image of the ImageView to the loaded application logo
        signUpPane.setTop(imageView); // Sets the ImageView as the top node of the signUpPane
        BorderPane.setAlignment(imageView, Pos.TOP_CENTER); // Aligns the ImageView to the top center of the BorderPane
        VBox signUpBox = new VBox(10); // Creates a new instance of VBox with a spacing of 10 and assigns it to the signUpBox variable
        signUpBox.setPadding(new Insets(0,10,30,10)); // Sets the padding of the VBox to 0 (top), 10 (right), 30 (bottom), 10 (left)
        signUpBox.setAlignment(Pos.CENTER); // Aligns the contents of the VBox to the center
        Label questionLabel = new Label("Please answer the following questions:"); // Creates a new instance of Label with the text "Please answer the following questions:" and assigns it to the questionLabel variable
        questionLabel.setFont(new Font(15)); // Sets the font size of the Label to 15
        Label accountTypeLabel = new Label("Select your account type below:"); // Creates a new instance of Label with the text "Select your account type below:" and assigns it to the accountTypeLabel variable
        ToggleGroup accountTypeGroup = new ToggleGroup(); // Creates a new instance of ToggleGroup and assigns it to the accountTypeGroup variable
        HBox accountTypeBox = new HBox(10); // Creates a new instance of HBox with a spacing of 10 and assigns it to the accountTypeBox variable
        accountTypeBox.setAlignment(Pos.CENTER); // Aligns the contents of the HBox to the center
        ToggleButton personalButton = new ToggleButton("Personal"); // Creates a new instance of ToggleButton with the text "Personal" and assigns it to the personalButton variable
        personalButton.setToggleGroup(accountTypeGroup); // Associates the personalButton with the accountTypeGroup
        ToggleButton managerButton = new ToggleButton("Manager"); // Creates a new instance of ToggleButton with the text "Manager" and assigns it to the managerButton variable
        managerButton.setToggleGroup(accountTypeGroup); // Associates the managerButton with the accountTypeGroup
        ToggleButton employeeButton = new ToggleButton("Employee"); // Creates a new instance of ToggleButton with the text "Employee" and assigns it to the employeeButton variable
        employeeButton.setToggleGroup(accountTypeGroup); // Associates the employeeButton with the accountTypeGroup
        accountTypeBox.getChildren().addAll(personalButton, managerButton, employeeButton); // Adds the personalButton, managerButton, and employeeButton to the accountTypeBox
        VBox formBox = new VBox(5); // Creates a new instance of VBox with a spacing of 5 and assigns it to the formBox variable
        Label nameLabel = new Label("Name"); // Creates a new instance of Label with the text "Name" and assigns it to the nameLabel variable
        TextField nameField = new TextField(); // Creates a new instance of TextField and assigns it to the nameField variable
        Label emailLabel = new Label("Email"); // Creates a new instance of Label with the text "Email" and assigns it to the emailLabel variable
        TextField emailField = new TextField(); // Creates a new instance of TextField and assigns it to the emailField variable
        Label passwordLabel = new Label("Password"); // Creates a new instance of Label with the text "Password" and assigns it to the passwordLabel variable
        TextField passwordField = new TextField(); // Creates a new instance of TextField and assigns it to the passwordField variable
        formBox.getChildren().addAll(nameLabel, nameField, emailLabel, emailField, passwordLabel, passwordField); // Adds the nameLabel, nameField, emailLabel, emailField, passwordLabel, and passwordField to the formBox
        formBox.setAlignment(Pos.CENTER_LEFT); // Aligns the contents of the formBox to the center-left
        HBox buttonsBox = new HBox(10); // Creates a new instance of HBox with a spacing of 10 and assigns it to the buttonsBox variable
        buttonsBox.setAlignment(Pos.CENTER); // Aligns the contents of the HBox to the center
        Button signUpButton = new Button("SIGN UP"); // Creates a new instance of Button with the text "SIGN UP" and assigns it to the signUpButton variable
        Button cancelButton = new Button("CANCEL"); // Creates a new instance of Button with the text "CANCEL" and assigns it to the cancelButton variable
        cancelButton.setOnAction(event -> showHomePage()); // Sets an event handler for the cancelButton to call the showHomePage method
        buttonsBox.getChildren().addAll(signUpButton, cancelButton); // Adds the signUpButton and cancelButton to the buttonsBox
        signUpButton.setOnAction(event -> { // Sets an event handler for the signUpButton
            final String name = nameField.getText(); // Gets the text from the nameField and assigns it to the name variable
            final String email = emailField.getText(); // Gets the text from the emailField and assigns it to the email variable
            final String password = passwordField.getText(); // Gets the text from the passwordField and assigns it to the password variable
            AtomicReference<String> atomicSelectedAccountType = new AtomicReference<>(); // Creates a new instance of AtomicReference<String> and assigns it to the atomicSelectedAccountType variable
            Toggle selectedToggle = accountTypeGroup.getSelectedToggle(); // Gets the currently selected Toggle from the accountTypeGroup and assigns it to the selectedToggle variable
            if (selectedToggle != null) { // Checks if a Toggle is selected
                atomicSelectedAccountType.set(((ToggleButton) selectedToggle).getText()); // Gets the text of the selected ToggleButton and sets it to the atomicSelectedAccountType
            }
            accountTypeGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> { // Adds a listener to the selectedToggleProperty of the accountTypeGroup
                if (newValue != null) { // Checks if a new Toggle is selected
                    atomicSelectedAccountType.set(((ToggleButton) newValue).getText()); // Gets the text of the newly selected ToggleButton and sets it to the atomicSelectedAccountType
                }
            });
            final String selectedAccountType = atomicSelectedAccountType.get(); // Gets the value of the atomicSelectedAccountType and assigns it to the selectedAccountType variable
            if (isEmailRegistered(email)) { // Calls the isEmailRegistered method with the email, and checks if the email is already registered
                showAlert("Email already exists."); // Displays an alert with the message "Email already exists."
            } else if (selectedAccountType == null) { // Checks if no account type is selected
                showAlert("Please select an account type."); // Displays an alert with the message "Please select an account type."
            } else {
                final Optional<UUID> userId = Optional.of(UUID.randomUUID()); // Generates a new random UUID and assigns it to the userId variable
                Optional<UUID> linkingCode = Optional.empty(); // Creates an empty Optional<UUID> and assigns it to the linkingCode variable
                if (selectedAccountType.equals("Manager")) { // Checks if the selected account type is "Manager"
                    // Create a popup for Manager account type
                    Stage popupStage = new Stage(); // Creates a new instance of Stage and assigns it to the popupStage variable
                    VBox popupVBox = new VBox(); // Creates a new instance of VBox and assigns it to the popupVBox variable
                    popupVBox.setAlignment(Pos.CENTER); // Aligns the contents of the popupVBox to the center
                    popupVBox.setSpacing(10); // Sets the spacing between children of the popupVBox to 10
                    Label popupLabel = new Label("Below is your linking code for your employees to connect to you,\nand allow you to add to or view their calendars"); // Creates a new instance of Label with the given text and assigns it to the popupLabel variable
                    popupLabel.setTextAlignment(TextAlignment.CENTER); // Sets the text alignment of the popupLabel to center
                    popupLabel.setWrapText(true); // Enables text wrapping for the popupLabel
                    final UUID managerLinkingCode = UUID.randomUUID(); // Generates a new random UUID and assigns it to the managerLinkingCode variable
                    Label linkingCodeLabel = new Label(managerLinkingCode.toString()); // Creates a new instance of Label with the string representation of managerLinkingCode and assigns it to the linkingCodeLabel variable
                    Button okButton = new Button("OK"); // Creates a new instance of Button with the text "OK" and assigns it to the okButton variable
                    okButton.setOnAction(e -> { // Sets an event handler for the okButton
                        popupStage.close(); // Closes the popupStage
                        //linkingCode = Optional.ofNullable(managerLinkingCode);
                        UserDetails userDetails = new UserDetails(userId, name, email, password, selectedAccountType, Optional.ofNullable(managerLinkingCode)); //linkingCode);
                        userDetailsMap.put(userId, userDetails); // Adds the newly created UserDetails object to the userDetailsMap with the userId as the key
                        showAlert("Sign up successful."); // Displays an alert with the message "Sign up successful."
                        showLoginScreen(); // Calls the showLoginScreen method to display the login screen
                        saveUserData(); // Calls the saveUserData method to save user data to a file
                    });
                    popupVBox.getChildren().addAll(popupLabel, linkingCodeLabel, okButton); // Adds the popupLabel, linkingCodeLabel, and okButton to the popupVBox
                    Scene popupScene = new Scene(popupVBox); // Creates a new instance of Scene with the popupVBox as the root node and assigns it to the popupScene variable
                    popupStage.setScene(popupScene); // Sets the scene of the popupStage to the popupScene
                    popupStage.showAndWait(); // Displays the popupStage and waits for it to be closed
                } else if (selectedAccountType.equals("Employee")) { // Checks if the selected account type is "Employee"
                    // Create a popup for Employee account type
                    Stage popupStage = new Stage(); // Creates a new instance of Stage and assigns it to the popupStage variable
                    VBox popupVBox = new VBox(); // Creates a new instance of VBox and assigns it to the popupVBox variable
                    popupVBox.setAlignment(Pos.CENTER); // Aligns the contents of the popupVBox to the center
                    popupVBox.setSpacing(10); // Sets the spacing between children of the popupVBox to 10
                    Label popupLabel = new Label("Do you have a linking code to a manager?"); // Creates a new instance of Label with the text "Do you have a linking code to a manager?" and assigns it to the popupLabel variable
                    Button yesButton = new Button("Yes"); // Creates a new instance of Button with the text "Yes" and assigns it to the yesButton variable
                    Button noButton = new Button("No"); // Creates a new instance of Button with the text "No" and assigns it to the noButton variable
                    yesButton.setOnAction(event2 -> { // Sets an event handler for the yesButton
                        // Create a new popup for entering the linking code
                        Stage linkingCodeStage = new Stage(); // Creates a new instance of Stage and assigns it to the linkingCodeStage variable
                        VBox linkingCodeVBox = new VBox(); // Creates a new instance of VBox and assigns it to the linkingCodeVBox variable
                        linkingCodeVBox.setAlignment(Pos.CENTER); // Aligns the contents of the linkingCodeVBox to the center
                        linkingCodeVBox.setSpacing(10); // Sets the spacing between children of the linkingCodeVBox to 10
                        Label linkingCodeLabel = new Label("Enter the manager's linking code:"); // Creates a new instance of Label with the text "Enter the manager's linking code:" and assigns it to the linkingCodeLabel variable
                        TextField linkingCodeField = new TextField(); // Creates a new instance of TextField and assigns it to the linkingCodeField variable
                        Button submitButton = new Button("Submit"); // Creates a new instance of Button with the text "Submit" and assigns it to the submitButton variable
                        Button cancelPopUpButton = new Button("Cancel"); // Creates a new instance of Button with the text "Cancel" and assigns it to the cancelPopUpButton variable
                        submitButton.setOnAction(event3 -> { // Sets an event handler for the submitButton
                            String linkingCodeString = linkingCodeField.getText(); // Gets the text from the linkingCodeField and assigns it to the linkingCodeString variable
                            UUID managerLinkingCode = null; // Declares a variable managerLinkingCode and initializes it with null
                            try {
                                managerLinkingCode = UUID.fromString(linkingCodeString); // Attempts to create a UUID from the linkingCodeString and assigns it to the managerLinkingCode variable
                                boolean isValidLinkingCode = isValidLinkingCode(linkingCodeString); // Calls the isValidLinkingCode method with the linkingCodeString and assigns the result to the isValidLinkingCode variable
                                if (!isValidLinkingCode) { // Checks if the linking code is invalid
                                    showAlert("Invalid linking code."); // Displays an alert with the message "Invalid linking code."
                                    return; // Exit the method if the code is invalid
                                }
                            } catch (IllegalArgumentException e) {
                                showAlert("Invalid linking code."); // Displays an alert with the message "Invalid linking code."
                                return;
                            }
                            linkingCodeStage.close(); // Closes the linkingCodeStage
                            UserDetails userDetails = new UserDetails(userId, name, email, password, selectedAccountType, Optional.ofNullable(managerLinkingCode));
                            userDetailsMap.put(userId, userDetails); // Adds the newly created UserDetails object to the userDetailsMap with the userId as the key
                            saveUserData(); // Calls the saveUserData method to save user data to a file
                            popupStage.close(); // Closes the popupStage
                            showAlert("Sign up successful."); // Displays an alert with the message "Sign up successful."
                            showLoginScreen(); // Calls the showLoginScreen method to display the login screen
                        });
                        cancelPopUpButton.setOnAction(event3 -> { // Sets an event handler for the cancelPopUpButton
                            linkingCodeStage.close(); // Closes the linkingCodeStage
                        });
                        linkingCodeVBox.getChildren().addAll(linkingCodeLabel, linkingCodeField, submitButton, cancelPopUpButton); // Adds the linkingCodeLabel, linkingCodeField, submitButton, and cancelPopUpButton to the linkingCodeVBox
                        Scene linkingCodeScene = new Scene(linkingCodeVBox); // Creates a new instance of Scene with the linkingCodeVBox as the root node and assigns it to the linkingCodeScene variable
                        linkingCodeStage.setScene(linkingCodeScene); // Sets the scene of the linkingCodeStage to the linkingCodeScene
                        linkingCodeStage.showAndWait(); // Displays the linkingCodeStage and waits for it to be closed
                    });
                    noButton.setOnAction(event2 -> { // Sets an event handler for the noButton
                        UserDetails userDetails = new UserDetails(userId, name, email, password, selectedAccountType, linkingCode);
                        userDetailsMap.put(userId, userDetails); // Adds the newly created UserDetails object to the userDetailsMap with the userId as the key
                        showAlert("Sign up successful."); // Displays an alert with the message "Sign up successful."
                        popupStage.close(); // Closes the popupStage
                        showLoginScreen(); // Calls the showLoginScreen method to display the login screen
                        saveUserData(); // Calls the saveUserData method to save user data to a file
                    });
                    popupVBox.getChildren().addAll(popupLabel, yesButton, noButton); // Adds the popupLabel, yesButton, and noButton to the popupVBox
                    Scene popupScene = new Scene(popupVBox); // Creates a new instance of Scene with the popupVBox as the root node and assigns it to the popupScene variable
                    popupStage.setScene(popupScene); // Sets the scene of the popupStage to the popupScene
                    popupStage.showAndWait(); // Displays the popupStage and waits for it to be closed
                } else { // If the selected account type is neither "Manager" nor "Employee" (implicitly "Personal")
                    UserDetails userDetails = new UserDetails(userId, name, email, password, selectedAccountType, linkingCode);
                    userDetailsMap.put(userId, userDetails); // Adds the newly created UserDetails object to the userDetailsMap with the userId as the key
                    showAlert("Sign up successful."); // Displays an alert with the message "Sign up successful."
                    showLoginScreen(); // Calls the showLoginScreen method to display the login screen
                    saveUserData(); // Calls the saveUserData method to save user data to a file
                }
            }
        });
        signUpBox.getChildren().addAll(questionLabel, accountTypeLabel, accountTypeBox, formBox, buttonsBox); // Adds the questionLabel, accountTypeLabel, accountTypeBox, formBox, and buttonsBox to the signUpBox
        signUpPane.setCenter(signUpBox); // Sets the center of the signUpPane to the signUpBox
        rootPane.getChildren().setAll(signUpPane); // Sets the contents of the rootPane to the signUpPane
    }

    private void showProfileEditScreen() { // This method shows the profile edit screen
        BorderPane updatePane = new BorderPane(); // Create a new BorderPane to hold the UI elements
        ImageView imageView = new ImageView(); // Create an ImageView for displaying the user's profile picture
        imageView.setFitWidth(200); // Set the width of the image to 200 pixels
        imageView.setFitHeight(150); // Set the height of the image to 150 pixels
        imageView.setPreserveRatio(true); // Preserve the aspect ratio of the image
        imageView.setSmooth(true); // Enable smooth rendering of the image
        imageView.setCache(true); // Cache the image for better performance
        imageView.setImage(image); // Set the image to display in the ImageView
        updatePane.setTop(imageView); // Set the ImageView at the top of the BorderPane
        BorderPane.setAlignment(imageView, Pos.TOP_CENTER); // Center the ImageView at the top
        VBox updateBox = new VBox(10); // Create a VBox to hold the form elements
        updateBox.setPadding(new Insets(0, 10, 50, 10)); // Set padding for the VBox
        updateBox.setAlignment(Pos.CENTER); // Center the contents of the VBox
        Label accountSettingsLabel = new Label("Account Settings"); // Create a label for account settings
        accountSettingsLabel.setFont(new Font(30)); // Set the font size for the label
        Label companyCodeDescriptionLabel = new Label(); // Create a label to display the company code description
        // Set the description label text based on the user's account type and linking code
        if (loggedInUser.getLinkingCode() == null || loggedInUser.getLinkingCode().isEmpty()) {
            companyCodeDescriptionLabel.setText("Add company code below:");
        } else if (Objects.equals(loggedInUser.getAccountType(), "Manager")) {
            companyCodeDescriptionLabel.setText("Company code below:");
        } else {
            companyCodeDescriptionLabel.setText("Modify company code below:");
        }
        accountSettingsLabel.setFont(new Font(15)); // Set the font size for the description label
        Label companyCodeLabel = new Label("Company Code:"); // Create a label for the company code
        TextField companyCodeField = new TextField(); // Create a text field for the company code
        Optional<UUID> linkingCode = loggedInUser.getLinkingCode(); // Get the linking code of the logged-in user
        // Set the text in the company code field based on the user's linking code
        if (loggedInUser.getLinkingCode() == null || loggedInUser.getLinkingCode().isEmpty()) {
            companyCodeField.setText("");
        } else {
            companyCodeField.setText(linkingCode.isPresent() ? linkingCode.get().toString() : "");
        }
        // Set the editable property of the company code field based on the user's account type
        if (Objects.equals(loggedInUser.getAccountType(), "Employee")) {
            companyCodeField.setEditable(true);
        } else {
            companyCodeField.setEditable(false);
        }
        HBox companyBox = new HBox(10); // Create an HBox to hold the company code label and text field
        companyBox.getChildren().addAll(companyCodeLabel, companyCodeField); // Add the label and text field to the HBox
        companyBox.setAlignment(Pos.CENTER); // Center the contents of the HBox
        Label updateDetailsLabel = new Label("Update your details below:"); // Create a label for updating user details
        updateDetailsLabel.setFont(new Font(15)); // Set the font size for the label
        VBox formBox = new VBox(5); // Create a VBox to hold the form fields
        Label nameLabel = new Label("Name"); // Create a label for the name field
        TextField nameField = new TextField(); // Create a text field for the name
        nameField.setText(loggedInUser.getName()); // Set the text in the name field with the logged-in user's name
        Label emailLabel = new Label("Email"); // Create a label for the email field
        TextField emailField = new TextField(); // Create a text field for the email
        emailField.setText(loggedInUser.getEmail()); // Set the text in the email field with the logged-in user's email
        Label passwordLabel = new Label("Password"); // Create a label for the password field
        TextField passwordField = new TextField(); // Create a text field for the password
        passwordField.setText(loggedInUser.getPassword()); // Set the text in the password field with the logged-in user's password
        // Add the form fields to the formBox
        formBox.getChildren().addAll(nameLabel, nameField, emailLabel, emailField, passwordLabel, passwordField);
        formBox.setAlignment(Pos.CENTER_LEFT); // Align the form fields to the center-left
        
        HBox buttonsBox = new HBox(10); // Create an HBox to hold the update and cancel buttons
        buttonsBox.setAlignment(Pos.CENTER); // Center the contents of the buttonsBox
        Button updateButton = new Button("UPDATE"); // Create an update button
        Button cancelButton = new Button("CANCEL"); // Create a cancel button
        cancelButton.setOnAction(event -> showHomePage()); // Set the action for the cancel button to show the home page
        buttonsBox.getChildren().addAll(updateButton, cancelButton); // Add the buttons to the buttonsBox
        updateButton.setOnAction(event -> {
            String name = nameField.getText(); // Get the name from the name field
            String email = emailField.getText(); // Get the email from the email field
            String password = passwordField.getText(); // Get the password from the password field
            boolean isValidUUID = isValidUUID(companyCodeField.getText()); // Check if the company code is a valid UUID
            if (!companyCodeField.getText().isEmpty() && !isValidUUID) {
                showAlert("Not valid linking code"); // Show an alert if the linking code is not a valid UUID
                return;
            } else if (isEmailRegistered(email) && !Objects.equals(email, loggedInUser.getEmail())) {
                showAlert("Email already exists."); // Show an alert if the email is already registered and not the same as the logged-in user's email
                return;
            }
            UserDetails updatedUserDetails;
            if (Objects.equals(loggedInUser.getAccountType(), "Employee")) {
                String companyCode = companyCodeField.getText(); // Get the company code from the text field
                boolean isValidLinkingCode = isValidLinkingCode(companyCode); // Validate the linking code against manager profiles
                if (!isValidLinkingCode) {
                    showAlert("Invalid linking code."); // Show an alert if the linking code is invalid
                    return; // Exit the method if the code is invalid
                }
                Optional<UUID> linkingCodeOptional;
                if (companyCodeField.getText().isEmpty()) {
                    linkingCodeOptional = Optional.empty(); // If the company code field is empty, create an empty Optional
                } else {
                    try {
                        UUID linkingCodeUUID = UUID.fromString(companyCodeField.getText()); // Convert the company code string to a UUID
                        linkingCodeOptional = Optional.of(linkingCodeUUID); // Create an Optional with the UUID
                    } catch (IllegalArgumentException e) {
                        // Invalid UUID format
                        linkingCodeOptional = loggedInUser.getLinkingCode(); // Use the existing linking code if the format is invalid
                    }
                }
                // Create a new UserDetails object with the updated information
                updatedUserDetails = new UserDetails(loggedInUser.getUuid(), name, email, password, loggedInUser.getAccountType(), linkingCodeOptional);
            } else {
                // For non-employee accounts, create a new UserDetails object without changing the linking code
                updatedUserDetails = new UserDetails(loggedInUser.getUuid(), name, email, password, loggedInUser.getAccountType(), loggedInUser.getLinkingCode());
            }
            userDetailsMap.put(loggedInUser.getUuid(), updatedUserDetails); // Update the user details in the map
            loggedInUser = updatedUserDetails; // Update the logged-in user with the new user details
            showAlert("Details updated successfully."); // Show an alert indicating that the details were updated successfully
            saveUserData(); // Save the updated user data
        });
        if (Objects.equals(loggedInUser.getAccountType(), "Personal")) {
            // If the user is a personal account, only show the account settings, update details, form, and buttons
            updateBox.getChildren().addAll(accountSettingsLabel, updateDetailsLabel, formBox, buttonsBox);
        } else {
            // For other account types, also show the company code description and company code fields
            updateBox.getChildren().addAll(accountSettingsLabel, companyCodeDescriptionLabel, companyBox, updateDetailsLabel, formBox, buttonsBox);
        }
        updatePane.setCenter(updateBox); // Set the updateBox in the center of the BorderPane
        rootPane.getChildren().setAll(updatePane); // Set the updatePane as the content of the rootPane
    }

    private void showCalendarScreen() {
        BorderPane calendarPane = new BorderPane(); //initialise the calendar pane

        // Create a VBox for the button
        VBox buttonBox = new VBox(10);
        buttonBox.setAlignment(Pos.CENTER);

        /**
        This is code for the 'menu bar' at the top of the screen.
        */
        // Create a Rectangle for the top partition
        Rectangle topPartition = new Rectangle();
        // Set the fill color of the Rectangle to a very light grey using a hex color code
        topPartition.setFill(Color.web("#e9e9e9"));
        // Set the stroke color of the Rectangle to grey using a hex color code
        topPartition.setStroke(Color.web("#b3b3b3"));
        // Set the width of the Rectangle to the width of the BorderPane
        topPartition.widthProperty().bind(calendarPane.widthProperty());
        // Set the height of the Rectangle to 5% of the height of the BorderPane
        topPartition.heightProperty().bind(calendarPane.heightProperty().multiply(0.05));
        // Add the Rectangle to the top of the BorderPane
        calendarPane.setTop(topPartition);

        // Create the "Back" button
        Button backButton = new Button("Back");
        backButton.setOnAction(event -> showHomePage());
        // Create the "Profile button
        Button profileButton = new Button("Profile");
        profileButton.setOnAction(event -> showProfileEditScreen());
        // Create the "Settings" button
        Button settingsButton = new Button("Settings");
        // Create the "Add Event" button
        Button addEventButton = new Button("Add Event");
        addEventButton.setOnAction(event -> showAddEvent());

        // Create a StackPane to hold the rectangle and the buttons
        StackPane headerStackPane = new StackPane();
        headerStackPane.getChildren().addAll(topPartition, backButton, new Pane(), addEventButton, new Pane(), settingsButton, new Pane(), profileButton);

        // Set the alignment of the buttons within the StackPane
        StackPane.setAlignment(backButton, Pos.CENTER_LEFT);
        StackPane.setAlignment(settingsButton, Pos.CENTER_RIGHT);
        StackPane.setAlignment(profileButton, Pos.CENTER_RIGHT);
        StackPane.setAlignment(addEventButton, Pos.CENTER);
        // Create an HBox to hold the StackPane
        HBox header = new HBox();
        header.getChildren().addAll(new Pane(), headerStackPane);
        HBox.setHgrow(header.getChildren().get(1), Priority.ALWAYS); // Make the Pane grow to push the StackPane to the right
        // Add the HBox to the top of the BorderPane
        calendarPane.setTop(header);

        /**
         * This is code for the left column
         */
        //Create rectangle for sidebar
        Rectangle sideBar = new Rectangle();
        // Set the fill color of the Rectangle to a very light grey using a hex color code
        sideBar.setFill(Color.web("#e9e9e9"));
        // Set the stroke color of the Rectangle to grey using a hex color code
        sideBar.setStroke(Color.web("#b3b3b3"));
        // Set the width of the Rectangle to the 20% of the width of the BorderPane
        sideBar.widthProperty().bind(calendarPane.widthProperty().multiply(0.20));
        // Set the height of the Rectangle to 95% of the height of the BorderPane, to account for the header bar
        sideBar.heightProperty().bind(calendarPane.heightProperty().multiply(0.95));
        //Add the rectangle to the left of the BorderPane
        calendarPane.setLeft(sideBar);

        /**
         * This is code for the calendar segment.
         */
        // Calendar Grid
        TableView<String[]> calendarGrid = new TableView<>();
        calendarGrid.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); // Ensure columns fill the available width

        // Add the "Time" column
        TableColumn<String[], String> timeColumn = new TableColumn<>("Time");
        timeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue()[0])));
        timeColumn.setPrefWidth(70); // Adjust the width as needed
        calendarGrid.getColumns().add(timeColumn);

        // Add columns for each day of the week
        String[] daysOfWeek = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        TableColumn<String[], String>[] columns = new TableColumn[daysOfWeek.length]; // Array to hold columns
        for (int i = 0; i < daysOfWeek.length; i++) {
            TableColumn<String[], String> column = new TableColumn<>(daysOfWeek[i]);
            final int index = i + 1; // Increment by 1 to skip the "Time" column
            column.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[index]));
            column.setPrefWidth(TableView.USE_COMPUTED_SIZE); // Let the column size adjust to fit content
            columns[i] = column; // Assign column to array
        }
        calendarGrid.getColumns().addAll(columns); // Add columns to TableView

        // Add rows for each hour of the day
        for (int hour = 0; hour < 24; hour++) {
            String[] row = new String[8]; // 8 columns (including the "Time" column)
            // Adjust time to display 00:00 - 23:00
            row[0] = String.format("%02d:00", hour);
            for (int day = 1; day < 8; day++) {
                row[day] = ""; // Placeholder for event information (to be implemented later)
            }
            calendarGrid.getItems().add(row);
        }

        // Limit the number of rows to 25
        calendarGrid.setFixedCellSize(25); // Set the height of each row
        calendarGrid.prefHeightProperty().bind(calendarGrid.fixedCellSizeProperty().multiply(25)); // Set the TableView's height

        // Add calendar grid to center of the BorderPane
        calendarPane.setCenter(calendarGrid);

        /**
         * This is code for the creation of the window
         */
        // Create a scene with the BorderPane
        Scene scene = new Scene(calendarPane, 1280, 720);
        // Set the scene on the stage
        primaryStage.setScene(scene);
        // Show the stage
        primaryStage.show();
    }

    private void showAddEvent() {
        // Create a new stage for the pop-up window
        Stage addEventStage = new Stage();
        addEventStage.setTitle("Add Event");

        // Create the layout for the pop-up window
        GridPane layout = new GridPane();
        layout.setAlignment(Pos.CENTER);
        layout.setHgap(10);
        layout.setVgap(10);

        // Labels for the fields
        Label titleLabel = new Label("Title:");
        Label typeLabel = new Label("Type:");
        Label dateLabel = new Label("Date:");
        Label timeFromLabel = new Label("Time From:");
        Label timeToLabel = new Label("Time To:");
        Label descriptionLabel = new Label("Description:");

        // TextFields and ComboBox for user input
        TextField titleField = new TextField();
        ComboBox<String> typeComboBox = new ComboBox<>();
        typeComboBox.getItems().addAll("Meeting", "Reminder");
        DatePicker datePicker = new DatePicker();
        TextArea descriptionArea = new TextArea();
        ComboBox<String> timeFromComboBox = new ComboBox<>();
        ComboBox<String> timeToComboBox = new ComboBox<>();

        // Populate time ComboBoxes
        for (int hour = 0; hour < 24; hour++) {
            String hourString = String.format("%02d:00", hour);
            timeFromComboBox.getItems().add(hourString);
            timeToComboBox.getItems().add(hourString);
        }

        // Convert selected time from ComboBox to LocalTime
        String selectedFromTime = timeFromComboBox.getValue();
        String selectedToTime = timeToComboBox.getValue();

        LocalDateTime timeFrom = LocalDateTime.parse(selectedFromTime);
        LocalDateTime timeTo = LocalDateTime.parse(selectedToTime);


        // Add components to the layout
        layout.add(titleLabel, 0, 0);
        layout.add(titleField, 1, 0);
        layout.add(typeLabel, 0, 1);
        layout.add(typeComboBox, 1, 1);
        layout.add(dateLabel, 0, 2);
        layout.add(datePicker, 1, 2);
        layout.add(timeFromLabel, 0, 3);
        layout.add(timeFromComboBox, 1, 3);
        layout.add(timeToLabel, 0, 4);
        layout.add(timeToComboBox, 1, 4);
        layout.add(descriptionLabel, 0, 5);
        layout.add(descriptionArea, 1, 5);

        Button addButton = new Button("Add");
        addButton.setOnAction(event -> {
            final UUID eventId = UUID.randomUUID(); // Generates a new random UUID and assigns it to the eventId variable
            if (Objects.equals(loggedInUser.accountType, "Personal")) {
                CalendarDetails calendarDetails = new CalendarDetails(eventId, titleField.toString(), typeComboBox.toString(), descriptionArea.toString(), datePicker, timeFrom, timeTo, loggedInUser.uuid);
                calendarDetailsMap.put(loggedInUser.uuid, calendarDetails); // Adds the newly created calendarDetails object to the calendarDetailsMap with the // userId as the key
            }   else {
                CalendarDetails calendarDetails = new CalendarDetails(eventId, titleField.toString(), typeComboBox.toString(), descriptionArea.toString(), datePicker, timeFrom, timeTo, loggedInUser.linkingCode);
                calendarDetailsMap.put(loggedInUser.linkingCode, calendarDetails); // Adds the newly created calendarDetails object to the calendarDetailsMap with the // userId as the key
            }
            showAlert("Calendar event created."); // Displays an alert with the message "Calendar event created."
            addEventStage.close();
            saveCalendarData(); // Calls the saveCalendarData method to save calendar data to a file
            });
      //      }
      //  }

        // Add the button to the layout
        layout.add(addButton, 1, 6);

        // Create a scene with the layout
        Scene scene = new Scene(layout, 600, 500);

        // Set the scene on the stage and show the stage
        addEventStage.setScene(scene);
        addEventStage.show();
    }

    private boolean isValidLinkingCode(String linkingCode) { // Defines a private method to check if a linking code is valid
        // Iterate over userDetailsMap to find manager profiles
        for (UserDetails userDetails : userDetailsMap.values()) { // Iterates over the values in the userDetailsMap
            if (Objects.equals(userDetails.getAccountType(), "Manager")) { // Checks if the current UserDetails object is of type "Manager"
                Optional<UUID> managerLinkingCode = userDetails.getLinkingCode(); // Gets the linking code of the manager
                if (managerLinkingCode.isPresent() && managerLinkingCode.get().toString().equals(linkingCode)) { // Checks if the manager's linking code matches the provided linking code
                    return true; // Valid linking code found
                }
            }
        }
        return false; // No matching linking code found
    }

    private boolean isEmailRegistered(String email) { // Defines a private method to check if an email is already registered
        // Iterate over userDetailsMap to check if email is already registered
        for (UserDetails userDetails : userDetailsMap.values()) { // Iterates over the values in the userDetailsMap
            if (userDetails.getEmail().equals(email)) { // Checks if the email of the current UserDetails object matches the provided email
                return true; // Email already registered
            }
        }
        return false; // Email not registered
    }

    private void loadUserData() { // This method loads user data from a file named "userData.dat" located in the "src/main/resources" directory
        File file = new File("src/main/resources/userData.dat"); // Create a File object for the "userData.dat" file
        if (file.exists() && file.length() > 0) { // Check if the file exists and has a non-zero length
            try {
                FileInputStream fileIn = new FileInputStream(file); // Create a FileInputStream to read from the file
                ObjectInputStream objectIn = new ObjectInputStream(fileIn); // Create an ObjectInputStream to read objects from the FileInputStream
                userDetailsMap = (HashMap<Optional<UUID>, UserDetails>) objectIn.readObject(); // Read the userDetailsMap object from the file and cast it to a HashMap<UUID, UserDetails>
                objectIn.close(); // Close the input streams
                fileIn.close(); // Close the input streams
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace(); // If an exception occurs during reading, print the stack trace
            }
        } else {
            userDetailsMap = new HashMap<Optional<UUID>, UserDetails>(); // If the file does not exist or is empty, create a new empty HashMap for userDetailsMap
        }
    }

    private void saveUserData() { // This method saves the userDetailsMap to the "userData.dat" file
        try {
            FileOutputStream fileOut = new FileOutputStream("src/main/resources/userData.dat"); // Create a FileOutputStream to write to the "userData.dat" file
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut); // Create an ObjectOutputStream to write objects to the FileOutputStream
            objectOut.writeObject(userDetailsMap); // Write the userDetailsMap object to the file
            objectOut.close(); // Close the output streams
            fileOut.close(); // Close the output streams
        } catch (Exception e) {
            e.printStackTrace(); // If an exception occurs during writing, print the stack trace
        }
    }

    private static boolean isValidUUID(String str) { // This method checks if a given string is a valid UUID (Universally Unique Identifier)
        try {
            UUID uuid = UUID.fromString(str); // Attempt to create a UUID object from the input string using the correct method
            return true; // If no exception is thrown, the string is a valid UUID
        } catch (IllegalArgumentException e) {
            return false; // If an exception is thrown, the string is not a valid UUID
        }
    }

    private boolean authenticateUser(String email, String password) { // Defines a private method to authenticate a user
        for (UserDetails userDetails : userDetailsMap.values()) { // Iterates over the values in the userDetailsMap
            if (userDetails.getEmail().equalsIgnoreCase(email) && userDetails.getPassword().equals(password)) { // Checks if the email and password match the current UserDetails object, ignoring case sensitivity on email field
                loggedInUser = userDetails; // Updates the loggedInUser instance variable with the authenticated user's details
                return true; // User authenticated successfully
            }
        }
        return false; // User authentication failed
    }

    private void showAlert(String message) { // Defines a private method to display an alert
        Alert alert = new Alert(Alert.AlertType.INFORMATION); // Creates a new instance of Alert with the type INFORMATION
        alert.setTitle("Information"); // Sets the title of the alert to "Information"
        alert.setHeaderText(null); // Sets the header text of the alert to null (no header text)
        alert.setContentText(message); // Sets the content text of the alert to the provided message
        alert.showAndWait(); // Displays the alert and waits for it to be closed
    }

    private static class UserDetails implements Serializable { // Defines a private static nested class UserDetails that implements the Serializable interface
        private final Optional<UUID> uuid; // Declares a final instance variable uuid of type UUID
        private final String name; // Declares a final instance variable name of type String
        private final String email; // Declares a final instance variable email of type String
        private final String password; // Declares a final instance variable password of type String
        private final String accountType; // Declares a final instance variable accountType of type String
        private transient Optional<UUID> linkingCode; // Declares a transient instance variable linkingCode of type Optional<UUID>
        private static final long serialVersionUID = 1L; // Declares a static final serialVersionUID field required for Serializable classes

        public UserDetails (Optional<UUID> uuid, String name, String email, String password, String accountType, Optional<UUID> linkingCode) { // Defines a constructor that takes parameters for all instance variables
            this.uuid = uuid; // Initializes the uuid instance variable
            this.name = name; // Initializes the name instance variable
            this.email = email; // Initializes the email instance variable
            this.password = password; // Initializes the password instance variable
            this.accountType = accountType; // Initializes the accountType instance variable
            this.linkingCode = linkingCode; // Initializes the linkingCode instance variable
        }

        private void writeObject(ObjectOutputStream out) throws IOException { // Defines a private method for custom serialization of the linkingCode field
            out.defaultWriteObject(); // Performs the default serialization for non-transient instance variables
            out.writeBoolean(linkingCode.isPresent()); // Writes a boolean indicating if linkingCode is present
            linkingCode.ifPresent(uuid -> { // Executes the lambda expression if linkingCode is present
                try {
                    out.writeObject(uuid); // Writes the UUID object if present
                } catch (IOException e) {
                    e.printStackTrace(); // Prints the stack trace in case of an IOException
                }
            });
        }

        private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException { // Defines a private method for custom deserialization of the linkingCode field
            in.defaultReadObject(); // Performs the default deserialization for non-transient instance variables
            boolean isPresent = in.readBoolean(); // Reads a boolean indicating if linkingCode is present
            if (isPresent) {
                linkingCode = Optional.of((UUID) in.readObject()); // Sets linkingCode to the deserialized UUID object if present
            } else {
                linkingCode = Optional.empty(); // Sets linkingCode to an empty Optional if not present
            }
        }

        public Optional<UUID> getUuid() { // Defines a public method to get the uuid
            return uuid; // Returns the uuid instance variable
        }

        public String getName() { // Defines a public method to get the name
            return name; // Returns the name instance variable
        }

        public String getEmail() { // Defines a public method to get the email
            return email; // Returns the email instance variable
        }

        public String getPassword() { // Defines a public method to get the password
            return password; // Returns the password instance variable
        }

        public String getAccountType() { // Defines a public method to get the accountType
            return accountType; // Returns the accountType instance variable
        }

        public Optional<UUID> getLinkingCode() { // Defines a public method to get the linkingCode
            return linkingCode; // Returns the linkingCode instance variable
        }
    }

    // Method to load user data from file
    private void loadCalendarData() { // Defines a private method to load calendar data from a file
        File file = new File("calendarData.dat"); // Creates a new instance of File with the filename "calendarData.dat"

        if (file.exists() && file.length() > 0) { // Checks if the file exists and has a non-zero length
            try {
                FileInputStream fileIn = new FileInputStream(file); // Creates a new instance of FileInputStream with the file
                ObjectInputStream objectIn = new ObjectInputStream(fileIn); // Creates a new instance of ObjectInputStream with the FileInputStream
                calendarDetailsMap = (HashMap<Optional<UUID>, CalendarDetails>) objectIn.readObject(); // Reads the calendarDetailsMap object from the ObjectInputStream
                objectIn.close(); // Closes the ObjectInputStream
                fileIn.close(); // Closes the FileInputStream

            } catch (IOException | ClassNotFoundException e) { // Catches IOException and ClassNotFoundException
                e.printStackTrace(); // Prints the stack trace in case of an exception
            }
        } else {
            //System.out.println("calendarData.dat file is empty or does not exist.");
            calendarDetailsMap = new HashMap<Optional<UUID>, CalendarDetails>(); // Creates a new instance of HashMap and assigns it to the calendarDetailsMap
        }
    }

    private void saveCalendarData() { // Defines a private method to save calendar data to a file
        try {
            FileOutputStream fileOut = new FileOutputStream("calendarData.dat"); // Creates a new instance of FileOutputStream with the filename "calendarData.dat"
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut); // Creates a new instance of ObjectOutputStream with the FileOutputStream

            objectOut.writeObject(calendarDetailsMap); // Writes the calendarDetailsMap object to the ObjectOutputStream
            objectOut.close(); // Closes the ObjectOutputStream
            fileOut.close(); // Closes the FileOutputStream
        } catch (Exception e) { // Catches any Exception
            e.printStackTrace(); // Prints the stack trace in case of an exception
        }
    }

    private static class CalendarDetails implements Serializable { // Defines a private static nested class CalendarDetails that implements the Serializable interface
        private final UUID uuid; // Declares a final instance variable uuid of type UUID
        private final String eventName; // Declares a final instance variable eventName of type String
        private final String eventDescription; // Declares a final instance variable eventDescription of type String
        private final String eventType; // Declares a final instance variable eventType of type String
        private final LocalDateTime eventTimeFrom; // Declares a final instance variable eventTimeFrom of type DateFormat
        private final LocalDateTime eventTimeTo; // Declares a final instance variable eventTimeTo of type DateFormat
        private final DatePicker eventDate; // Declares a final instance variable eventTo of type ZonedDateTime
        private transient Optional<UUID> linkingCode; // Declares a transient instance variable linkingCode of type Optional<UUID>
        private static final long serialVersionUID = 1L; // Declares a static final serialVersionUID field required for Serializable classes

        public CalendarDetails(UUID uuid, String eventName, String eventType, String eventDescription, DatePicker eventDate, LocalDateTime eventTimeFrom, LocalDateTime eventTimeTo, Optional<UUID> linkingCode) { // Defines a constructor that takes parameters for all instance variables
            this.uuid = uuid; // Initializes the uuid instance variable
            this.eventName = eventName; // Initializes the eventName instance variable
            this.eventType = eventType; // Initializes the eventType instance variable
            this.eventDescription = eventDescription; // Initializes the eventDescription instance variable
            this.eventDate = eventDate;
            this.eventTimeFrom = eventTimeFrom; // Initializes the eventFrom instance variable
            this.eventTimeTo = eventTimeTo; // Initializes the eventTo instance variable
            this.linkingCode = linkingCode; // Initializes the linkingUsers instance variable
        }

        public UUID getUuid() { // Defines a public method to get the uuid
            return uuid; // Returns the uuid instance variable
        }

        public String getEventName() { // Defines a public method to get the eventName
            return eventName; // Returns the eventName instance variable
        }

        public String getEventDescrption() { // Defines a public method to get the eventDescription
            return eventDescription; // Returns the eventDescription instance variable
        }

        public LocalDateTime getEventFrom() { // Defines a public method to get the eventFrom
            return eventTimeFrom; // Returns the eventTimeFrom instance variable
        }

        public LocalDateTime getEventTo() { // Defines a public method to get the eventTo
            return eventTimeTo; // Returns the eventTimeTo instance variable
        }

        public Optional<UUID> getLinkingCode() { // Defines a public method to get the linkingCode
            return linkingCode; // Returns the linkingCode instance variable
        }
    }

    public static void main(String[] args) {
        launch(); // calls for the JavaFx application to launch
    }
}
