/**
 * LifestyleCalendar, a JavaFX application for promoting and managing a better work/life balance whilst taking into consideration health safety aspects such as eye-strain warnings
 * It includes features like user authentication, profile management, and event scheduling.
 */
package com.example.cab302javaproject; // Declares the package name for the Java class

import javafx.animation.Timeline;
import javafx.animation.KeyFrame;
import javafx.application.Application; // Imports the Application class from the JavaFX library
import javafx.application.Platform;
import javafx.geometry.Insets; // Imports the Insets class from the JavaFX library for creating padding around UI elements
import javafx.geometry.Pos; // Imports the Pos class from the JavaFX library for positioning UI elements
import javafx.scene.Node;
import javafx.scene.Scene; // Imports the Scene class from the JavaFX library for creating the main window
import javafx.scene.control.*; // Imports all classes related to UI controls from the JavaFX library
import javafx.scene.image.ImageView; // Imports the ImageView class from the JavaFX library for displaying images
import javafx.scene.layout.*; // Imports all classes related to UI layout from the JavaFX library
import javafx.scene.shape.Circle;
import javafx.scene.text.Font; // Imports the Font class from the JavaFX library for setting text styles
import javafx.scene.text.TextAlignment; // Imports the TextAlignment class from the JavaFX library for setting text alignment
import javafx.stage.Stage; // Imports the Stage class from the JavaFX library for creating the main window
import javafx.scene.image.Image; // Imports the Image class from the JavaFX library for loading images
import java.util.*;
import java.util.concurrent.atomic.AtomicReference; // Imports the AtomicReference class from the Java concurrent package for thread-safe reference handling
import javafx.scene.control.Button; // Imports the Button class from the JavaFX library for creating button UI elements
import javafx.scene.control.Label; // Imports the Label class from the JavaFX library for creating text labels in the UI
import javafx.scene.control.TextField; // Imports the TextField class from the JavaFX library for creating text input fields in the UI
import javafx.scene.paint.Color; // Imports the Color class from the JavaFX library for defining colors for UI elements
import javafx.scene.shape.Rectangle; // Imports the Rectangle class from the JavaFX library for creating rectangular shapes in the UI
import javafx.scene.control.TableColumn; // Imports the TableColumn class from the JavaFX library for creating columns in a table view
import javafx.scene.control.TableView; // Imports the TableView class from the JavaFX library for creating tables to display data
import javafx.scene.layout.BorderPane; // Imports the BorderPane class from the JavaFX library for creating a border pane layout
import javafx.scene.layout.HBox; // Imports the HBox class from the JavaFX library for creating horizontal box layouts
import javafx.scene.layout.VBox; // Imports Importing the VBox class from the JavaFX library for creating vertical box layouts
import javafx.beans.property.SimpleStringProperty; // Imports the SimpleStringProperty class from the JavaFX library for creating observable string properties
import javafx.scene.control.ComboBox; // Imports the ComboBox class from the JavaFX library for creating dropdown boxes
import java.time.LocalDate; // Imports the LocalDate class from the java.time package for working with dates
import java.time.LocalTime; // Imports the LocalTime class from the java.time package for working with times
import java.util.UUID; // Imports the UUID class from the java.util package for generating unique identifiers
import java.time.format.DateTimeFormatter; // Imports the DateTimeFormatter class from the java.time.format package for formatting date and time objects
import java.time.temporal.TemporalAdjusters; // Imports the TemporalAdjusters class from the java.time.temporal package for adjusting temporal objects
import java.time.temporal.ChronoUnit; // Imports the ChronoUnit class from the java.time.temporal package for working with units of time
import javafx.util.Duration; // Imports the Duration class from the javafx.util package for defining durations
import static com.example.cab302javaproject.CalendarData.*; // Imports all static members from the CalendarData class in the com.example.cab302javaproject package

/**
 * The LifestyleCalendar class extends the Application class and serves as the main entry point for the application.
 */
public class LifestyleCalendar extends Application { // Defines the LifestyleCalendar class which extends the Application class from JavaFX
    private Stage primaryStage; // Declares a private instance variable to hold the primary stage (main window)
    private StackPane rootPane; // Declares a private instance variable to hold the root pane (main container)
    public static HashMap<UUID, UserData.UserDetails> userDetailsMap; // Declares a private instance variable to hold a map of user details keyed by UUID
    public static HashMap<UUID, CalendarDetails> calendarDetailsMap; // Declares a private instance variable to hold a map of calendar details keyed by UUID
    public static UserData.UserDetails loggedInUser; // Declares a private instance variable to hold the currently logged-in user's details
    private Image image; // Declares a private instance variable to hold the logo image
    private static Image imageAppLogo; // Declares a private instance variable to hold the application logo image
    private LocalDate currentDate = LocalDate.now(); // Declares current date as variable
    private TableView<String[]> calendarGrid; // Declare the calendarGrid variable at the class level
    private BorderPane calendarPane; // Declare calendarPane as an instance variable

    /**
     * The start method initializes the primary stage and displays the home page.
     * @param stage The primary stage for the JavaFX application.
     */
    @Override // Overrides the start method from the Application class
    public void start(Stage stage) { // Defines the start method which takes a Stage object as a parameter
        primaryStage = stage; // Assigns the passed Stage object to the primaryStage instance variable
        rootPane = new StackPane(); // Creates a new instance of StackPane and assigns it to the rootPane instance variable
        userDetailsMap = new HashMap<UUID, UserData.UserDetails>(); // Creates a new instance of HashMap and assigns it to the userDetailsMap instance variable
        calendarDetailsMap = new HashMap<UUID, CalendarDetails>(); // Creates a new instance of HashMap and assigns it to the calendarDetailsMap instance variable
        Scene scene = new Scene(rootPane, 600, 400); // Creates a new Scene object with the rootPane as the root node and dimensions of 600x400
        stage.setTitle("Lifestyle Calendar!"); // Sets the title of the primary stage
        stage.setScene(scene); // Sets the scene of the primary stage
        stage.show(); // Displays the primary stage
        image = new Image("LifestyleCalendarLogo.png"); // Creates a new Image object by loading the "LifestyleCalendarLogo.png" file
        imageAppLogo= new Image("LifestyleCalendarLogoCalendar.png"); // Creates a new Image object by loading the "LifestyleCalendarLogo.png" file
        stage.getIcons().add(imageAppLogo); // Adds the loaded image as an icon to the primary stage
        UserData.loadUserData(); // Calls the loadUserData method to load user data from a file
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
        PasswordField passwordField = new PasswordField(); // Creates a new instance of TextField and assigns it to the passwordField variable
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
            if (UserData.authenticateUser(email, password)) { // Calls the authenticateUser method with the email and password, and checks if the user is authenticated
                CalendarData.loadCalendarData(); // Calls the loadCalendarData method to load calendar data from a file
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
        PasswordField passwordField = new PasswordField(); // Creates a new instance of TextField and assigns it to the passwordField variable
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
            if (UserData.isEmailRegistered(email)) { // Calls the isEmailRegistered method with the email, and checks if the email is already registered
                showAlert("Email already exists."); // Displays an alert with the message "Email already exists."
            } else if (selectedAccountType == null) { // Checks if no account type is selected
                showAlert("Please select an account type."); // Displays an alert with the message "Please select an account type."
            } else {
                final UUID userId = UUID.randomUUID(); // Generates a new random UUID and assigns it to the userId variable
                Optional<UUID> linkingCode = Optional.empty(); // Creates an empty Optional<UUID> and assigns it to the linkingCode variable
                final boolean notificationsPreference = true;
                final boolean eyeStrainPreference = true;
                final String notificationsSnoozeDuration = "10 minutes";
                final String notificationsReminderTime = "15 minutes before";
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
                        UserData.UserDetails userDetails = new UserData.UserDetails(userId, name, email, password, selectedAccountType, Optional.ofNullable(managerLinkingCode), notificationsPreference, notificationsSnoozeDuration, notificationsReminderTime, eyeStrainPreference); //linkingCode);
                        userDetailsMap.put(userId, userDetails); // Adds the newly created UserDetails object to the userDetailsMap with the userId as the key
                        showAlert("Sign up successful."); // Displays an alert with the message "Sign up successful."
                        showLoginScreen(); // Calls the showLoginScreen method to display the login screen
                        UserData.saveUserData(); // Calls the saveUserData method to save user data to a file
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
                                boolean isValidLinkingCode = UserData.isValidLinkingCode(linkingCodeString); // Calls the isValidLinkingCode method with the linkingCodeString and assigns the result to the isValidLinkingCode variable
                                if (!isValidLinkingCode) { // Checks if the linking code is invalid
                                    showAlert("Invalid linking code."); // Displays an alert with the message "Invalid linking code."
                                    return; // Exit the method if the code is invalid
                                }
                            } catch (IllegalArgumentException e) {
                                showAlert("Invalid linking code."); // Displays an alert with the message "Invalid linking code."
                                return;
                            }
                            linkingCodeStage.close(); // Closes the linkingCodeStage
                            UserData.UserDetails userDetails = new UserData.UserDetails(userId, name, email, password, selectedAccountType, Optional.ofNullable(managerLinkingCode), notificationsPreference, notificationsSnoozeDuration, notificationsReminderTime, eyeStrainPreference);
                            userDetailsMap.put(userId, userDetails); // Adds the newly created UserDetails object to the userDetailsMap with the userId as the key
                            UserData.saveUserData(); // Calls the saveUserData method to save user data to a file
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
                        UserData.UserDetails userDetails = new UserData.UserDetails(userId, name, email, password, selectedAccountType, linkingCode, notificationsPreference, notificationsSnoozeDuration, notificationsReminderTime, eyeStrainPreference);
                        userDetailsMap.put(userId, userDetails); // Adds the newly created UserDetails object to the userDetailsMap with the userId as the key
                        showAlert("Sign up successful."); // Displays an alert with the message "Sign up successful."
                        popupStage.close(); // Closes the popupStage
                        showLoginScreen(); // Calls the showLoginScreen method to display the login screen
                        UserData.saveUserData(); // Calls the saveUserData method to save user data to a file
                    });
                    popupVBox.getChildren().addAll(popupLabel, yesButton, noButton); // Adds the popupLabel, yesButton, and noButton to the popupVBox
                    Scene popupScene = new Scene(popupVBox); // Creates a new instance of Scene with the popupVBox as the root node and assigns it to the popupScene variable
                    popupStage.setScene(popupScene); // Sets the scene of the popupStage to the popupScene
                    popupStage.showAndWait(); // Displays the popupStage and waits for it to be closed
                } else { // If the selected account type is neither "Manager" nor "Employee" (implicitly "Personal")
                    UserData.UserDetails userDetails = new UserData.UserDetails(userId, name, email, password, selectedAccountType, linkingCode, notificationsPreference, notificationsSnoozeDuration, notificationsReminderTime, eyeStrainPreference);
                    userDetailsMap.put(userId, userDetails); // Adds the newly created UserDetails object to the userDetailsMap with the userId as the key
                    showAlert("Sign up successful."); // Displays an alert with the message "Sign up successful."
                    showLoginScreen(); // Calls the showLoginScreen method to display the login screen
                    UserData.saveUserData(); // Calls the saveUserData method to save user data to a file
                }
            }
        });
        signUpBox.getChildren().addAll(questionLabel, accountTypeLabel, accountTypeBox, formBox, buttonsBox); // Adds the questionLabel, accountTypeLabel, accountTypeBox, formBox, and buttonsBox to the signUpBox
        signUpPane.setCenter(signUpBox); // Sets the center of the signUpPane to the signUpBox
        rootPane.getChildren().setAll(signUpPane); // Sets the contents of the rootPane to the signUpPane
    }

    private void showProfileEditScreen() { // This method shows the profile edit screen
        // Create a new stage for the profile edit screen
        Stage profileEditStage = new Stage();
        profileEditStage.setTitle("Profile Edit");
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
        PasswordField passwordField = new PasswordField(); // Creates a new instance of TextField and assigns it to the passwordField variable
        passwordField.setText(loggedInUser.getPassword()); // Set the text in the password field with the logged-in user's password
        // Add the form fields to the formBox
        formBox.getChildren().addAll(nameLabel, nameField, emailLabel, emailField, passwordLabel, passwordField);
        formBox.setAlignment(Pos.CENTER_LEFT); // Align the form fields to the center-left
        
        HBox buttonsBox = new HBox(10); // Create an HBox to hold the update and cancel buttons
        buttonsBox.setAlignment(Pos.CENTER); // Center the contents of the buttonsBox
        Button updateButton = new Button("UPDATE"); // Create an update button
        Button cancelButton = new Button("CANCEL"); // Create a cancel button
        cancelButton.setOnAction(event -> profileEditStage.close()); // Set the action for the cancel button to show the home page
        buttonsBox.getChildren().addAll(updateButton, cancelButton); // Add the buttons to the buttonsBox
        updateButton.setOnAction(event -> {
            String name = nameField.getText(); // Get the name from the name field
            String email = emailField.getText(); // Get the email from the email field
            String password = passwordField.getText(); // Get the password from the password field
            boolean isValidUUID = UserData.isValidUUID(companyCodeField.getText()); // Check if the company code is a valid UUID
            if (!companyCodeField.getText().isEmpty() && !isValidUUID) {
                showAlert("Invalid linking code"); // Show an alert if the linking code is not a valid UUID
                return;
            } else if (UserData.isEmailRegistered(email) && !Objects.equals(email, loggedInUser.getEmail())) {
                showAlert("Email already exists."); // Show an alert if the email is already registered and not the same as the logged-in user's email
                return;
            }
            UserData.UserDetails updatedUserDetails;
            if (Objects.equals(loggedInUser.getAccountType(), "Employee")) {
                String companyCode = companyCodeField.getText(); // Get the company code from the text field
                boolean isValidLinkingCode = UserData.isValidLinkingCode(companyCode); // Validate the linking code against manager profiles
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
                updatedUserDetails = new UserData.UserDetails(loggedInUser.getUuid(), name, email, password, loggedInUser.getAccountType(), linkingCodeOptional, loggedInUser.getNotificationsPreference(), loggedInUser.getNotificationsSnoozeDuration(), loggedInUser.getNotificationsReminderTime(), loggedInUser.getEyeStrainPreference());
            } else {
                // For non-employee accounts, create a new UserDetails object without changing the linking code
                updatedUserDetails = new UserData.UserDetails(loggedInUser.getUuid(), name, email, password, loggedInUser.getAccountType(), loggedInUser.getLinkingCode(), loggedInUser.getNotificationsPreference(), loggedInUser.getNotificationsSnoozeDuration(), loggedInUser.getNotificationsReminderTime(), loggedInUser.getEyeStrainPreference());
            }
            userDetailsMap.put(loggedInUser.getUuid(), updatedUserDetails); // Update the user details in the map
            loggedInUser = updatedUserDetails; // Update the logged-in user with the new user details
            showAlert("Details updated successfully."); // Show an alert indicating that the details were updated successfully
            UserData.saveUserData(); // Save the updated user data
            profileEditStage.close(); // Close the profile edit stage
        });
        if (Objects.equals(loggedInUser.getAccountType(), "Personal")) {
            // If the user is a personal account, only show the account settings, update details, form, and buttons
            updateBox.getChildren().addAll(accountSettingsLabel, updateDetailsLabel, formBox, buttonsBox);
        } else {
            // For other account types, also show the company code description and company code fields
            updateBox.getChildren().addAll(accountSettingsLabel, companyCodeDescriptionLabel, companyBox, updateDetailsLabel, formBox, buttonsBox);
        }
        updatePane.setCenter(updateBox); // Set the updateBox in the center of the BorderPane
        Scene scene = new Scene(updatePane, 400, 500); // Create a scene with the updatePane
        // Set the scene on the profile edit stage and show the stage
        profileEditStage.setScene(scene);
        profileEditStage.show();
    }

    private void showNotificationSettingsPopup() { // Method to display the notification settings popup
        // Create a confirmation alert dialog
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Notification Settings");
        alert.setHeaderText(null);

        // Create the layout for the popup
        GridPane grid = new GridPane();
        grid.setHgap(10); // Set horizontal gap between grid cells
        grid.setVgap(10); // Set vertical gap between grid cells
        grid.setPadding(new Insets(20, 150, 10, 10)); // Set padding around the grid

        // Create the enable notifications toggle button
        Label enableNotificationsLabel = new Label("Enable Notifications:");
        ToggleButton enableNotificationsToggle = new ToggleButton(); // Create the enable notifications toggle button
        enableNotificationsToggle.setSelected(loggedInUser.getNotificationsPreference()); // Set the value from userDetailsMap

        // Create a rectangle to represent the toggle button background
        Rectangle toggleBackground = new Rectangle(50, 20);
        toggleBackground.setArcWidth(20);  // Set the corner radius width of the background
        toggleBackground.setArcHeight(20); // Set the corner radius height of the background
        toggleBackground.setFill(Color.LIGHTGRAY); // Set the initial background color of the toggle button

        // Create a circle to represent the toggle button thumb
        Circle toggleThumb = new Circle(10);
        toggleThumb.setFill(Color.WHITE); // Set the fill color of the thumb
        toggleThumb.setStroke(Color.LIGHTGRAY); // Set the stroke color of the thumb
        toggleThumb.setStrokeWidth(1); // Set the stroke width of the thumb
        toggleThumb.setTranslateX(loggedInUser.getNotificationsPreference() ? 15 : -15); // Set the initial position based on the value from userDetailsMap

        // Create a stack pane to hold the toggle button components
        StackPane togglePane = new StackPane(toggleBackground, toggleThumb);

        toggleBackground.setFill(loggedInUser.getNotificationsPreference() ? Color.LIMEGREEN : Color.LIGHTGRAY); // Set the initial color based on the value from userDetailsMap

        // Update the toggle button appearance when its state changes
        enableNotificationsToggle.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) { // If the new value is true, set the background to green and move the thumb to the right
                toggleBackground.setFill(Color.LIMEGREEN);
                toggleThumb.setTranslateX(15);
            } else { // Otherwise, set the background to gray and move the thumb to the left
                toggleBackground.setFill(Color.LIGHTGRAY);
                toggleThumb.setTranslateX(-15);
            }
        });

        // Set the toggle button graphic to the custom toggle pane
        enableNotificationsToggle.setGraphic(togglePane);

        // Create the snooze duration dropdown
        Label snoozeDurationLabel = new Label("Snooze Duration:"); // Create a label for the snooze duration dropdown
        ComboBox<String> snoozeDurationComboBox = new ComboBox<>(); // Create a dropdown for selecting snooze duration
        snoozeDurationComboBox.getItems().addAll( // Add options to the snooze duration dropdown
                "5 minutes",
                "10 minutes",
                "15 minutes",
                "30 minutes",
                "1 hour"
        );
        snoozeDurationComboBox.setValue(loggedInUser.getNotificationsSnoozeDuration()); // Set the value from userDetailsMap

        // Create the reminder time dropdown
        Label reminderTimeLabel = new Label("Reminder Time:"); // Create a label for the reminder time dropdown
        ComboBox<String> reminderTimeComboBox = new ComboBox<>(); // Create a dropdown for selecting reminder time
        reminderTimeComboBox.getItems().addAll( // Add options to the reminder time dropdown
                "5 minutes before",
                "10 minutes before",
                "15 minutes before",
                "30 minutes before",
                "1 hour before"
        );
        reminderTimeComboBox.setValue(loggedInUser.getNotificationsReminderTime()); // Set the value from userDetailsMap

        // Create the enable notifications toggle button
        Label enableEyeStrainLabel = new Label("Enable Eye Strain Notifications:"); // Create a label for Eye Strains
        ToggleButton enableEyeStrainToggle = new ToggleButton();
        enableEyeStrainToggle.setSelected(loggedInUser.getEyeStrainPreference()); // Set the value from userDetailsMap

        // Create a rectangle to represent the toggle button background
        Rectangle toggleRectangle = new Rectangle(50, 20);
        toggleRectangle.setArcWidth(20); // Set the corner radius width of the background
        toggleRectangle.setArcHeight(20); // Set the corner radius height of the background
        toggleRectangle.setFill(Color.LIGHTGRAY); // Set the initial background color of the toggle button

        // Create a circle to represent the toggle button thumb
        Circle toggleSwitch = new Circle(10);
        toggleSwitch.setFill(Color.WHITE); // Set the fill colour of the thumb white
        toggleSwitch.setStroke(Color.LIGHTGRAY); // Set the stroke colour of the thumb lightgray
        toggleSwitch.setStrokeWidth(1); // Set the stroke width of the thumb
        toggleSwitch.setTranslateX(loggedInUser.getEyeStrainPreference() ? 15 : -15); // Set the initial position based on the value from userDetailsMap

        // Create a stack pane to hold the toggle button components
        StackPane togglePaneGroup = new StackPane(toggleRectangle, toggleSwitch);

        toggleRectangle.setFill(loggedInUser.getEyeStrainPreference() ? Color.LIMEGREEN : Color.LIGHTGRAY); // Set the initial color based on the value from userDetailsMap

        // Update the toggle button appearance when its state changes
        enableEyeStrainToggle.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue) { // If the new value is true, set the background to green and move the thumb to the right
                toggleRectangle.setFill(Color.LIMEGREEN);
                toggleSwitch.setTranslateX(15);
            } else { // Otherwise, set the background to gray and move the thumb to the left
                toggleRectangle.setFill(Color.LIGHTGRAY);
                toggleSwitch.setTranslateX(-15);
            }
        });

        // Set the toggle button graphic to the custom toggle pane
        enableEyeStrainToggle.setGraphic(togglePaneGroup);

        // Add the labels, toggle button, and dropdown boxes to the grid
        grid.add(enableNotificationsLabel, 0, 0); // Add the enable notifications label and toggle button to the grid
        grid.add(enableNotificationsToggle, 1, 0); // Add the enable notifications toggle button to the grid
        grid.add(snoozeDurationLabel, 0, 1); // Add the snooze duration label to the grid
        grid.add(snoozeDurationComboBox, 1, 1); // Add the snooze duration dropdown to the grid
        grid.add(reminderTimeLabel, 0, 2); // Add the reminder time label to the grid
        grid.add(reminderTimeComboBox, 1, 2); // Add the reminder time dropdown to the grid
        grid.add(enableEyeStrainLabel, 0, 3); // Add the enable eye strain label to the grid
        grid.add(enableEyeStrainToggle, 1, 3); // Add the enable eye strain toggle button to the grid

        // Set the content of the alert to the grid
        alert.getDialogPane().setContent(grid);

        // Create "Cancel" buttons
        Button cancelButton = new Button("Cancel");

        // Set the icon for the alert popup window
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(imageAppLogo); // Add the application logo to the window

        // Show the alert and wait for user response
        Optional<ButtonType> result = alert.showAndWait();
        ButtonType button = result.orElse(ButtonType.CANCEL);

        // Set button actions
        if (button == ButtonType.OK) {
            boolean enableNotifications = enableNotificationsToggle.isSelected(); // Get the state of the enable notifications toggle button
            String selectedSnoozeDuration = snoozeDurationComboBox.getValue(); // Get the selected snooze duration from the dropdown
            String selectedReminderTime = reminderTimeComboBox.getValue(); // Get the selected reminder time from the dropdown
            boolean enableEyeStrain = enableEyeStrainToggle.isSelected(); // Get the state of the enable eye strain toggle button
            // Update the userDetailsMap with the selected values
            UserData.UserDetails updatedUserDetails = new UserData.UserDetails(
                    loggedInUser.getUuid(),
                    loggedInUser.getName(),
                    loggedInUser.getEmail(),
                    loggedInUser.getPassword(),
                    loggedInUser.getAccountType(),
                    loggedInUser.getLinkingCode(),
                    enableNotifications,
                    selectedSnoozeDuration,
                    selectedReminderTime,
                    enableEyeStrain
            );
            userDetailsMap.put(loggedInUser.getUuid(), updatedUserDetails); // Update the user details in the map with the new object
            loggedInUser = updatedUserDetails;
            UserData.saveUserData(); // Save the updated user data to file

            showAlert("Details updated successfully.");
            alert.close();

        } else {
            alert.close();
        }
    }

    private void showCalendarScreen() {
        calendarPane = new BorderPane(); // Create a new BorderPane for the calendar layout

        // Create the hamburger menu
        MenuButton menuButton = new MenuButton(); // Create a new MenuButton
        menuButton.setGraphic(createHamburgerIcon()); // Set hamburger icon as a graphic
        MenuItem accountSettingsMenuItem = new MenuItem("Account Settings"); // Create a menu item for Account Settings
        accountSettingsMenuItem.setOnAction(event -> showProfileEditScreen()); // Set action for menu item Account Settings
        MenuItem notificationSettingsMenuItem = new MenuItem("Notification Settings"); // Create a menu item for Notification Settings
        notificationSettingsMenuItem.setOnAction(event -> showNotificationSettingsPopup()); // Set action for menu item Notification Settings

        menuButton.getItems().addAll(accountSettingsMenuItem, notificationSettingsMenuItem); // Add menu items to the menu button

        // Create the "+" button for adding events
        Button addEventButton = new Button("+"); // Button created for "+" label
        addEventButton.setOnAction(event -> showAddEvent(null)); // Set action for the Add Event button

        // Create a logout button to log out of your account
        Button logOutButton = new Button("Log Out"); // Button created for "Log Out" label
        logOutButton.setOnAction(event -> {
            showAlert("Signed Out"); // Show a signed out alert
            primaryStage.close(); //reset the application

            Platform.runLater(() -> {
                try {
                    new LifestyleCalendar().start(new Stage());
                } catch (Exception e) {
                    primaryStage.close();
                }
            });
        });

        // Create an HBox to hold the addEventButton, menuButton and logOutButton
        HBox topRightBox = new HBox(10); // Create a new HBox with spacing
        topRightBox.setAlignment(Pos.CENTER_RIGHT); // Align contents to centre right
        topRightBox.getChildren().addAll(addEventButton, menuButton, logOutButton); // Add buttons to the Hbox
        calendarPane.setTop(topRightBox); // Set the HBox to the top of the layout

        // Create a VBox for the left grey section
        VBox leftSection = new VBox(10); // Create a new VBox with spacing
        leftSection.setAlignment(Pos.TOP_CENTER); // Align contents to the top center
        leftSection.setPadding(new Insets(10)); // Set padding around the VBox

        // Create an HBox for the day of the week labels
        HBox dayOfWeekLabels = new HBox(10); // Create a new HBox for the day of the week labels
        dayOfWeekLabels.setAlignment(Pos.CENTER); // Align contents to the centre
        String[] daysOfWeek = {"S", "M", "T", "W", "T", "F", "S"}; // Array of days name
        for (String day : daysOfWeek) {
            Label dayLabel = new Label(day); // Create label for each day
            dayOfWeekLabels.getChildren().add(dayLabel); // Add labels to the HBox
        }
        leftSection.getChildren().add(dayOfWeekLabels); // Add the HBox to the VBox

        // Create a mini calendar
        GridPane miniCalendar = createMiniCalendar(); // Create a mini calendar using a helper method
        leftSection.getChildren().add(miniCalendar); // Add the mini calendar to the VBox

        // Create navigation buttons
        Button prevMonthButton = new Button("<"); // Button created to navigate to the previous month
        Button nextMonthButton = new Button(">"); // Button created to navigate to the next month
        prevMonthButton.setOnAction(event -> {
            currentDate = currentDate.minusMonths(1); // Move to the previous month
            updateCalendar(); // Update the calendar display
        });
        nextMonthButton.setOnAction(event -> {
            currentDate = currentDate.plusMonths(1); // Move to the next month
            updateCalendar(); // Update the calendar display
        });

        // Create an HBox to hold the navigation buttons
        HBox navigationBox = new HBox(10); // Create a new HBox with spacing
        navigationBox.setAlignment(Pos.CENTER); // Align contents to the center
        navigationBox.getChildren().addAll(prevMonthButton, nextMonthButton); // Add buttons to the HBox
        leftSection.getChildren().add(navigationBox); // Add the HBox to the VBox

        calendarPane.setLeft(leftSection); // Set the VBox to the left of the layout

        // Create a GridPane to hold the date labels
        GridPane dateLabelsPane = new GridPane(); // Create a newGridPane for date labels
        dateLabelsPane.setHgap(100); // Set a horizontal gap between labels
        dateLabelsPane.setAlignment(Pos.CENTER); // Align contents to the centre

        // Calendar Grid
        calendarGrid = new TableView<>(); // Create a new TableView for the calendar
        calendarGrid.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY); // Set column resize policy
        calendarGrid.setFixedCellSize(30); // Adjust the cell size as needed
        VBox.setVgrow(calendarGrid, Priority.ALWAYS); // Allow vertical growth

        // Add the "Time" column
        TableColumn<String[], String> timeColumn = new TableColumn<>("Time"); // Create a column for time
        timeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(String.valueOf(cellData.getValue()[0]))); // Set cell value factory
        timeColumn.setPrefWidth(60); // Set preferred width
        calendarGrid.getColumns().add(timeColumn); // Add column to the grid


        // Create an array to hold the date labels
        Label[] dateLabels = new Label[7];

        // Add date labels for each day of the week
        LocalDate startOfWeek = currentDate.with(TemporalAdjusters.previousOrSame(java.time.DayOfWeek.SUNDAY)); // Get the start of the week
        for (int i = 0; i < 7; i++) {
            LocalDate date = startOfWeek.plusDays(i); // Get each day of the week
            dateLabels[i] = new Label(date.format(DateTimeFormatter.ofPattern("MMM d"))); // Add the label to the array
        }

        // Add columns for each day of the week
        String[] dayOfWeek = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"}; // Array of day names
        TableColumn<String[], String>[] columns = new TableColumn[7]; // Array for columns
        for (int i = 0; i < 7; i++) {
            final int columnIndex = i;
            String columnHeader = dateLabels[i].getText() + "\n" + dayOfWeek[i];
            TableColumn<String[], String> column = new TableColumn<>(columnHeader); // Create a column for each day
            final int index = i + 1;
            column.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue()[index])); // Set cell value factory
            column.setCellFactory(cell -> new TableCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty); // Call superclass
                    if (empty) {
                        setText(null); // Clear cell text
                    } else {
                        TableRow<String[]> tableRow = getTableRow(); // Get table row
                        if (tableRow != null && tableRow.getItem() != null) {
                            String time = tableRow.getItem()[0]; // Get time from row
                            // Convert the time string to LocalTime
                            LocalTime eventTime = LocalTime.parse(time);
                            // Calculate the date for this day of the week
                            LocalDate date = currentDate.with(TemporalAdjusters.previousOrSame(java.time.DayOfWeek.SUNDAY)).plusDays(columnIndex);
                            // Check if there's an event for this day and time
                            String eventDetails = checkForEvent(date, eventTime);
                            setText(eventDetails != null ? eventDetails : ""); // Set cell text to event details if not null
                        } else {
                            setText(""); // Set cell text to empty
                        }
                    }
                }
            });

            // Set double-click event handler for editing events
            column.setCellFactory(column1 -> {
                TableCell<String[], String> cell = new TableCell<>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty); // Call superclass method
                        if (empty) {
                            setText(null); // Clear cell text
                        } else {
                            setText(item); // Set call text
                        }
                    }
                };
                cell.setOnMouseClicked(event -> {
                    if (event.getClickCount() == 2 && !cell.isEmpty()) { // Check for double-click
                        String eventName = cell.getItem(); // Get event name from cell
                        if (eventName != null && !eventName.isEmpty()) {
                            // Find the corresponding CalendarDetails object
                            for (CalendarDetails calendarDetails : calendarDetailsMap.values()) {
                                if (calendarDetails.getEventName().equals(eventName)) { // Check if event name matches
                                    showAddEvent(calendarDetails); // Show add event screen
                                    break;
                                }
                            }
                        }
                    }
                });
                return cell;
            });
            column.setStyle("-fx-alignment: center;"); // Set alignment of column header

            columns[i] = column; // Store the column in the array
        }
        calendarGrid.getColumns().addAll(columns); // Add columns to the calendar grid

        // Create a VBox to hold the date labels and calendar grid
        VBox centerPane = new VBox(); // Create a new VBox
        VBox.setVgrow(centerPane, Priority.ALWAYS); // Allow vertical growth
        centerPane.getChildren().addAll(dateLabelsPane, calendarGrid); // Add date labels and calendars

        calendarPane.setCenter(centerPane); // Set VBox to the centre

        // Create a scene with the BorderPane
        Scene scene = new Scene(calendarPane, 1280, 720); // Create a new scene with the BorderPane
        primaryStage.setScene(scene); // Set the scene on the stage
        primaryStage.show(); // Show the stage

        updateCalendar(); // Call the method to update the calendar

        Timeline timeline = new Timeline(new KeyFrame(Duration.minutes(1), event -> {
            NotificationEnquiry.showNotifications(); // Show notifications
        }));
        timeline.setCycleCount(1);  // Ensures the timeline only runs once
        timeline.play(); // Start timeline

        Timeline eyetime = new Timeline(new KeyFrame(Duration.hours(2), event -> {
            showNotification("Eye Strain Break", " Rest your eyes for 15 minutes!");
        }));
        eyetime.setCycleCount(1);  // Ensures the timeline only runs once
        eyetime.play(); // Start timeline
    }

    private GridPane createMiniCalendar() {
        GridPane miniCalendar = new GridPane();
        miniCalendar.setHgap(5);
        miniCalendar.setVgap(5);
        miniCalendar.setAlignment(Pos.CENTER);

        // Add labels for days of the week
        String[] daysOfWeek = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
        for (int i = 0; i < daysOfWeek.length; i++) {
            Label dayLabel = new Label(daysOfWeek[i]);
            miniCalendar.add(dayLabel, i, 0);
        }

        // Add date cells
        LocalDate startDate = currentDate.withDayOfMonth(1);
        int dayOfWeek = startDate.getDayOfWeek().getValue() % 7;
        int row = 1;
        int col = dayOfWeek;
        while (startDate.getMonthValue() == LocalDate.now().getMonthValue()) {
            final LocalDate date = startDate; // Declare date as a final variable
            Label dateLabel = new Label(String.valueOf(startDate.getDayOfMonth()));
            miniCalendar.add(dateLabel, col, row);

            // Highlight the current week
            if (startDate.isEqual(currentDate) || (startDate.isAfter(currentDate) && startDate.isBefore(currentDate.plusWeeks(1)))) {
                dateLabel.setStyle("-fx-background-color: #a9a9a9;");
            }

            // Set action to update the selected week in the main calendar
            dateLabel.setOnMouseClicked(event -> {
                currentDate = date;
                updateCalendar();
            });

            startDate = startDate.plusDays(1);
            col++;
            if (col > 6) {
                col = 0;
                row++;
            }
        }

        return miniCalendar;
    }

    private void updateCalendar() {

        // Update the date labels
        VBox centerPane = (VBox) calendarPane.getCenter();
        GridPane dateLabelsPane = (GridPane) centerPane.getChildren().get(0);
        LocalDate startOfWeek = currentDate.with(TemporalAdjusters.previousOrSame(java.time.DayOfWeek.SUNDAY));

        // Check if dateLabelsPane has children before updating the date labels
        if (dateLabelsPane.getChildren().size() > 0) {
            for (int i = 0; i < 7; i++) {
                LocalDate date = startOfWeek.plusDays(i);
                Label dateLabel = (Label) dateLabelsPane.getChildren().get(i);
                dateLabel.setText(date.format(DateTimeFormatter.ofPattern("MMM d")));
            }
        }

        // Update the month and year label
        VBox leftSection = (VBox) calendarPane.getLeft();
        Label monthYearLabel = new Label(currentDate.format(DateTimeFormatter.ofPattern("MMMM yyyy")));
        monthYearLabel.setAlignment(Pos.CENTER);
        leftSection.getChildren().set(0, monthYearLabel);

        // Update the mini calendar
        GridPane miniCalendar = (GridPane) leftSection.getChildren().get(1);
        miniCalendar.getChildren().clear();

        // Add the day of the week labels to the mini calendar
        String[] daysOfWeek = {"S", "M", "T", "W", "T", "F", "S"};
        for (int i = 0; i < 7; i++) {
            Label dayLabel = new Label(daysOfWeek[i]);
            miniCalendar.add(dayLabel, i, 0);
        }

        // Calculate dayOfWeek and daysInMonth based on currentDate
        LocalDate firstDayOfMonth = currentDate.withDayOfMonth(1);
        int dayOfWeek = firstDayOfMonth.getDayOfWeek().getValue();
        int daysInMonth = currentDate.lengthOfMonth();

        // Add the updated mini calendar dates
        int row = 1;
        int col = dayOfWeek;
        for (int day = 1; day <= daysInMonth; day++) {
            LocalDate date = currentDate.withDayOfMonth(day);
            Label dateLabel = new Label(String.valueOf(day));
            miniCalendar.add(dateLabel, col, row);

            // Highlight the current week
            if (date.isEqual(currentDate) || (date.isAfter(currentDate) && date.isBefore(currentDate.plusWeeks(1)))) {
                dateLabel.setStyle("-fx-background-color: #a9a9a9;");
            }

            // Set action to update the selected week in the main calendar
            dateLabel.setOnMouseClicked(event -> {
                currentDate = date;
                updateCalendar();
            });

            col++;
            if (col > 6) {
                col = 0;
                row++;
            }
        }

        // Clear the existing calendar grid
        calendarGrid.getItems().clear();

        // Populate the calendar grid with events
        populateCalendarGrid();
    }

    private Node createHamburgerIcon() {
        VBox hamburgerIcon = new VBox();
        hamburgerIcon.setSpacing(3);
        hamburgerIcon.setAlignment(Pos.CENTER);
        hamburgerIcon.setPadding(new Insets(5));

        for (int i = 0; i < 3; i++) {
            Rectangle line = new Rectangle(20, 2);
            line.setFill(Color.BLACK);
            hamburgerIcon.getChildren().add(line);
        }

        return hamburgerIcon;
    }

    private void showAddEvent(CalendarDetails calendarDetail) {
        // Create a new stage for the "Add Event" screen
        Stage addEventStage = new Stage();
        addEventStage.setTitle(calendarDetail != null ? "Update Event" : "Add Event");

        addEventStage.getIcons().add(imageAppLogo); // Set the app icon in the top left of the stage

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
            for (int minute = 0; minute < 60; minute += 30) {
                String timeString = String.format("%02d:%02d", hour, minute);
                timeFromComboBox.getItems().add(timeString);
                timeToComboBox.getItems().add(timeString);
            }
        }

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

        // Populate the form fields if editing an existing event
        if (calendarDetail != null) {
            titleField.setText(calendarDetail.getEventName());
            typeComboBox.setValue(calendarDetail.getEventType());
            descriptionArea.setText(calendarDetail.getEventDescription());
            datePicker.setValue(calendarDetail.getEventDate());
            timeFromComboBox.setValue(calendarDetail.getEventFrom().toString());
            timeToComboBox.setValue(calendarDetail.getEventTo().toString());
        }

        // Set the action for the "Add" or "Update" button
        Button actionButton = new Button(calendarDetail != null ? "Update" : "Add");
        actionButton.setOnAction(event -> {
            // Validate form fields
            if (titleField.getText().isEmpty() || typeComboBox.getValue() == null || datePicker.getValue() == null ||
                    timeFromComboBox.getValue() == null || timeToComboBox.getValue() == null || descriptionArea.getText().isEmpty()) {
                showAlert("Please fill in all fields.");
                return;
            }
            String selectedFromTime = timeFromComboBox.getValue();
            String selectedToTime = timeToComboBox.getValue();
            LocalTime timeFrom = LocalTime.parse(selectedFromTime, DateTimeFormatter.ofPattern("HH:mm"));
            LocalTime timeTo = LocalTime.parse(selectedToTime, DateTimeFormatter.ofPattern("HH:mm"));

            // Validate time order and swap if necessary
            if (timeTo.isBefore(timeFrom)) {
                LocalTime temp = timeFrom;
                timeFrom = timeTo;
                timeTo = temp;
            }   else if (timeTo == timeFrom){
                showAlert("Times are the same.");
                return;
            }

            final UUID eventId = UUID.randomUUID();
            CalendarDetails calendarDetails;
            if (calendarDetail != null) {
                CalendarDetails updatedCalendarDetails = new CalendarDetails(calendarDetail.getUuid(), titleField.getText(), typeComboBox.getValue(), descriptionArea.getText(), datePicker.getValue(), timeFrom, timeTo, calendarDetail.getLinkingCode());
                calendarDetailsMap.put(calendarDetail.getUuid(), updatedCalendarDetails);
                showAlert("Calendar event updated.");
            } else {
                if (Objects.equals(loggedInUser.getAccountType(), "Personal")) {
                    calendarDetails = new CalendarDetails(eventId, titleField.getText(), typeComboBox.getValue(), descriptionArea.getText(), datePicker.getValue(), timeFrom, timeTo, Optional.of(loggedInUser.getUuid()));
                } else if (Objects.equals(loggedInUser.getAccountType(), "Manager")) {
                    calendarDetails = new CalendarDetails(eventId, titleField.getText(), typeComboBox.getValue(), descriptionArea.getText(), datePicker.getValue(), timeFrom, timeTo, loggedInUser.getLinkingCode());
                } else {
                    // For employees
                    if (loggedInUser.getLinkingCode() != null && loggedInUser.getLinkingCode().isPresent()) {
                        calendarDetails = new CalendarDetails(eventId, titleField.getText(), typeComboBox.getValue(), descriptionArea.getText(), datePicker.getValue(), timeFrom, timeTo, loggedInUser.getLinkingCode());
                    } else {
                        // If the employee doesn't have a linking code, set the event's linking code to their UUID
                        calendarDetails = new CalendarDetails(eventId, titleField.getText(), typeComboBox.getValue(), descriptionArea.getText(), datePicker.getValue(), timeFrom, timeTo, Optional.of(loggedInUser.getUuid()));
                    }
                }

                calendarDetailsMap.put(eventId, calendarDetails);
                showAlert("Calendar event created.");
            }
            addEventStage.close();
            CalendarData.saveCalendarData();
            showCalendarScreen();
            addEventStage.close(); // ??
        });
        // Add a "Cancel" button
        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(event -> addEventStage.close());
        // Add "Delete" button if editing an existing event
        Button deleteButton = null;
        if (calendarDetail != null) {
            deleteButton = new Button("Delete");
            deleteButton.setOnAction(event -> {
                calendarDetailsMap.remove(calendarDetail.getUuid()); // Gets current Calendar data and removes it
                CalendarData.saveCalendarData();
                showAlert("Event deleted.");
                showCalendarScreen();
                addEventStage.close();
            });
        }
        HBox buttonsBox = new HBox(10);
        buttonsBox.setAlignment(Pos.CENTER);
        buttonsBox.getChildren().addAll(actionButton, cancelButton);
        // Add delete button if not null
        if (deleteButton != null) {
            buttonsBox.getChildren().add(deleteButton);
        }



        ///buttonsBox.getChildren().set(0, actionButton);

        layout.add(buttonsBox, 1, 6);
        // Create a scene with the layout
        Scene scene = new Scene(layout, 600, 500);

        // Set the scene on the stage and show the stage
        addEventStage.setScene(scene);
        addEventStage.show();
    }

    private void populateCalendarGrid() {
        LocalDate startOfWeek = currentDate.with(TemporalAdjusters.previousOrSame(java.time.DayOfWeek.SUNDAY));

        // Clear the existing calendar grid
        calendarGrid.getItems().clear();

        // Populate rows for each half-hour of the day, including events
        for (int hour = 0; hour < 24; hour++) {
            for (int halfHour = 0; halfHour < 2; halfHour++) {
                String[] row = new String[8]; // +1 for the "Time" column
                LocalTime time = LocalTime.of(hour, halfHour * 30);
                row[0] = time.format(DateTimeFormatter.ofPattern("HH:mm"));

                // Iterate over the events in the calendarDetailsMap
                for (Map.Entry<UUID, CalendarDetails> entry : calendarDetailsMap.entrySet()) {
                    CalendarDetails calendarDetails = entry.getValue();
                    // Check if the event spans the current half-hour and belongs to the logged-in user
                    if (calendarDetails.getEventFrom().isBefore(time) &&
                            calendarDetails.getEventTo().isAfter(time) &&
                            CalendarData.isEventForLoggedInUser(calendarDetails)) {
                        // Find the day column index for this event
                        LocalDate eventDate = calendarDetails.getEventDate();
                        int columnIndex = (int) ChronoUnit.DAYS.between(startOfWeek, eventDate) + 1; // Adjust for array index

                        // Check if the column index is within the valid range
                        if (columnIndex >= 1 && columnIndex < 8) {
                            // Add the event title to the relevant cell in the row
                            row[columnIndex] = calendarDetails.getEventName();
                        }
                    }
                }

                // Add the row to the calendarGrid
                calendarGrid.getItems().add(row);
            }
        }
    }

    private String checkForEvent(LocalDate date, LocalTime time) {
        // Iterate through your calendarDetailsMap to find events that match the given date and time
        for (CalendarDetails event : calendarDetailsMap.values()) {
            if (event.getEventDate().equals(date) &&
                    event.getEventFrom().equals(time)) {
                // Check if the event belongs to the logged-in user
                if (loggedInUser.getAccountType().equals("Personal") &&
                        event.getLinkingCode() != null &&
                        event.getLinkingCode().isPresent() &&
                        event.getLinkingCode().get().equals(loggedInUser.getUuid())) {
                    return event.getEventName() + " (" + event.getEventType() + ")";
                } else if (loggedInUser.getAccountType().equals("Manager") &&
                        event.getLinkingCode() != null &&
                        event.getLinkingCode().isPresent() &&
                        event.getLinkingCode().get().equals(loggedInUser.getLinkingCode().get())) {
                    return event.getEventName() + " (" + event.getEventType() + ")";
                } else if (loggedInUser.getAccountType().equals("Employee")) {
                    if (loggedInUser.getLinkingCode() != null &&
                            loggedInUser.getLinkingCode().isPresent() &&
                            event.getLinkingCode() != null &&
                            event.getLinkingCode().isPresent() &&
                            event.getLinkingCode().get().equals(loggedInUser.getLinkingCode().get())) {
                        return event.getEventName() + " (" + event.getEventType() + ")";
                    } else if ((loggedInUser.getLinkingCode() == null ||
                            !loggedInUser.getLinkingCode().isPresent()) &&
                            event.getLinkingCode() != null &&
                            event.getLinkingCode().isPresent() &&
                            event.getLinkingCode().get().equals(loggedInUser.getUuid())) {
                        // If the employee doesn't have a linking code and the event's linking code matches their UUID
                        return event.getEventName() + " (" + event.getEventType() + ")";
                    }
                }
            }
        }
        // Return null if no event is found
        return null;
    }

    public static void showNotification(String eventName, String eventDescription) {
        Platform.runLater(() -> {
            // Create a BorderPane to hold all components
            BorderPane borderPane = new BorderPane();

            if (!Objects.equals(eventName, "Eye Strain Break")) {
                // Create the Snooze button
                Button snoozeButton = new Button("Snooze");
                snoozeButton.setOnAction(e -> {
                    // Schedule the notification to show again after x minutes
                    Timeline snoozeTimeline = new Timeline(new KeyFrame(Duration.minutes(1), event -> showNotification(eventName, eventDescription)));
                    snoozeTimeline.setCycleCount(1); // Only run once
                    snoozeTimeline.play();
                });
                // Create an HBox for the snooze button
                HBox snoozeHBox = new HBox();
                snoozeHBox.getChildren().add(snoozeButton);
                snoozeHBox.setAlignment(Pos.BOTTOM_RIGHT);
                snoozeHBox.setPadding(new Insets(0, 10, 10, 0));
                borderPane.setBottom(snoozeHBox);
            }

            // Create the notification image
            ImageView imageView = new ImageView(imageAppLogo); // Update the path to your actual image file
            imageView.setFitHeight(100); // Adjust the height of the image as needed
            imageView.setPreserveRatio(true);
            imageView.setSmooth(true);

            // Create a Label for the event name
            Label eventNameLabel = new Label(eventName);
            eventNameLabel.setAlignment(Pos.TOP_RIGHT);

            // Create a Label for the event description
            Label eventDescriptionLabel = new Label(eventDescription);
            eventDescriptionLabel.setAlignment(Pos.TOP_RIGHT);

            // Create an HBox for the event info (name and description)
            VBox eventInfoVBox = new VBox(eventNameLabel, eventDescriptionLabel);
            eventInfoVBox.setAlignment(Pos.TOP_RIGHT);
            eventInfoVBox.setSpacing(5);

            // Modify the BorderPane to hold additional components
            borderPane.setLeft(imageView);
            borderPane.setRight(eventInfoVBox);

            // Create the notification and show it
            org.controlsfx.control.Notifications notification = org.controlsfx.control.Notifications.create()
                    .hideAfter(Duration.seconds(10)) // Auto-hide after 5 seconds
                    .position(Pos.BOTTOM_RIGHT)
                    .graphic(borderPane);

            notification.show();
        });
    }

    private void showAlert(String message) { // Defines a private method to display an alert
        Alert alert = new Alert(Alert.AlertType.INFORMATION); // Creates a new instance of Alert with the type INFORMATION
        alert.setTitle("Information"); // Sets the title of the alert to "Information"
        alert.setHeaderText(null); // Sets the header text of the alert to null (no header text)
        // Set the icon for the alert popup window
        Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
        stage.getIcons().add(imageAppLogo);
        alert.setContentText(message); // Sets the content text of the alert to the provided message
        alert.showAndWait(); // Displays the alert and waits for it to be closed
    }

    public static void main(String[] args) {
        launch(); // calls for the JavaFx application to launch
    }
}
