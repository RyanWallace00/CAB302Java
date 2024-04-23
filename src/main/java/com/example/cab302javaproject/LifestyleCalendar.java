/**
 * LifestyleCalendar, a JavaFX application for promoting and maanging a better work/life balance whilst taking into consideration health aspects such as eye-strain
 * It includes features like user authentication, profile management, and event scheduling.
 */
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

import java.util.HashMap;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

import java.io.*;
import java.util.HashMap;
import java.io.Serializable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.time.ZonedDateTime;
import java.util.List;

/**
 * The LifestyleCalendar class extends the Application class and serves as the main entry point for the application.
 */
public class LifestyleCalendar extends Application {
    private Stage primaryStage;
    private StackPane rootPane;
    private HashMap<UUID, UserDetails> userDetailsMap;
    private UserDetails loggedInUser;
    private Image image;

    /**
     * The start method initializes the primary stage and displays the home page.
     * @param stage The primary stage for the JavaFX application.
     */
    @Override
    public void start(Stage stage) {
        primaryStage = stage;
        rootPane = new StackPane();
        userDetailsMap = new HashMap<>();

        Scene scene = new Scene(rootPane, 600, 400);
        //stage.getIcons().add(new Image("https://genuinecoder.com/wp-content/uploads/2022/06/genuine_coder-3.png"));

        stage.setTitle("Lifestyle Calendar!");
        stage.setScene(scene);
        stage.show();

        // Load application logo
        image = new Image("C:\\Users\\ryanwallace\\IdeaProjects\\CAB302Java\\src\\main\\java\\com\\example\\cab302javaproject\\LifestyleCalendarLogo.png"); //new Image("jetbrains://idea/navigate/reference?project=CAB302Java&path=com/example/cab302javaproject/LifestyleCalendarLogo.png");

        // Load user data when the application starts
        loadUserData();

        // Display home page
        showHomePage();
    }

    /**
     * Displays the home page with login and signup options.
     */
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

        //homePane.setTop(imageView);
        BorderPane.setAlignment(imageView, Pos.TOP_CENTER);

        VBox buttonBox = new VBox(10);
        buttonBox.setPadding(new Insets(10));
        buttonBox.setAlignment(Pos.CENTER);

        Button loginButton = new Button("LOGIN");
        loginButton.setOnAction(event -> showLoginScreen());
        Button signUpButton = new Button("SIGN UP");
        signUpButton.setOnAction(event -> showSignUpScreen());

        buttonBox.getChildren().addAll(imageView,loginButton, signUpButton);
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

        //loginPane.setTop(imageView);
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

        centerBox.getChildren().addAll(imageView, informationLabel, formBox, buttonsBox);
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
        signUpBox.setPadding(new Insets(0,10,30,10));
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
        Label emailLabel = new Label("Email");
        TextField emailField = new TextField();
        Label passwordLabel = new Label("Password");
        TextField passwordField = new TextField();

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

            if (isEmailRegistered(email)) {
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
                        saveUserData(); // Save user data after sign up
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
                                boolean isValidLinkingCode = isValidLinkingCode(linkingCodeString);
                                if (!isValidLinkingCode) {
                                    showAlert("Invalid linking code.");
                                    return; // Exit the method if the code is invalid
                                }
                            } catch (IllegalArgumentException e) {
                                showAlert("Invalid linking code.");
                                return;
                            }
                            linkingCodeStage.close();
                            UserDetails userDetails = new UserDetails(userId, name, email, password, selectedAccountType, Optional.ofNullable(managerLinkingCode));
                            userDetailsMap.put(userId, userDetails);
                            showAlert("Sign up successful.");
                            popupStage.close();
                            showLoginScreen();
                            saveUserData(); // Save user data after sign up
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
                        saveUserData(); // Save user data after sign up
                    });

                    popupVBox.getChildren().addAll(popupLabel, yesButton, noButton);

                    Scene popupScene = new Scene(popupVBox);
                    popupStage.setScene(popupScene);
                    popupStage.showAndWait();
                } else { // xexplicitly say personal or owuldn't matter??
                    UserDetails userDetails = new UserDetails(userId, name, email, password, selectedAccountType, linkingCode);
                    userDetailsMap.put(userId, userDetails);
                    showAlert("Sign up successful.");
                    showLoginScreen();
                    saveUserData(); // Save user data after sign up
                }
            }
        });

        signUpBox.getChildren().addAll(questionLabel, accountTypeLabel, accountTypeBox, formBox, buttonsBox);

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
        updateBox.setPadding(new Insets(0, 10, 50, 10));
        updateBox.setAlignment(Pos.CENTER);

        Label accountSettingsLabel = new Label("Account Settings");
        accountSettingsLabel.setFont(new Font(30));//15));
        Label companyCodeDescriptionLabel = new Label();
        if (loggedInUser.getLinkingCode() == null || loggedInUser.getLinkingCode().isEmpty()) {//companyCodeDescriptionLabel == null) {
            companyCodeDescriptionLabel.setText("Add company code below:");
        } else if (Objects.equals(loggedInUser.getAccountType(), "Manager")) {
            companyCodeDescriptionLabel.setText("Company code below:");
        }
        else {
            companyCodeDescriptionLabel.setText("Modify company code below:");
        }
        accountSettingsLabel.setFont(new Font(15));
        Label companyCodeLabel = new Label("Company Code:");
        TextField companyCodeField = new TextField();
        Optional<UUID> linkingCode = loggedInUser.getLinkingCode();
        if (loggedInUser.getLinkingCode() == null || loggedInUser.getLinkingCode().isEmpty()) {
            companyCodeField.setText("");
        }else{
            companyCodeField.setText(linkingCode.isPresent() ? linkingCode.get().toString() : "");
        }
        if (Objects.equals(loggedInUser.getAccountType(), "Employee")){
            companyCodeField.setEditable(true);
        }
        else {
            companyCodeField.setEditable(false);
        }

        HBox companyBox = new HBox(10);
        companyBox.getChildren().addAll(companyCodeLabel, companyCodeField);
        companyBox.setAlignment(Pos.CENTER);

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
            boolean isValidUUID = isValidUUID(companyCodeField.getText());
            if (!companyCodeField.getText().isEmpty() && !isValidUUID){
                showAlert("Not valid linking code");
                return;
            }   else if (isEmailRegistered(email) && !Objects.equals(email, loggedInUser.getEmail())) {
                showAlert("Email already exists.");
                return;
            }

            UserDetails updatedUserDetails;
            if (Objects.equals(loggedInUser.getAccountType(), "Employee")){
                String companyCode = companyCodeField.getText();// Validate linking code against manager profiles
                boolean isValidLinkingCode = isValidLinkingCode(companyCode);
                if (!isValidLinkingCode) {
                    showAlert("Invalid linking code.");
                    return; // Exit the method if the code is invalid
                }

                Optional<UUID> linkingCodeOptional;
                if (companyCodeField.getText().isEmpty()) {
                    linkingCodeOptional = Optional.empty();
                } else {
                    try {
                        UUID linkingCodeUUID = UUID.fromString(companyCodeField.getText());
                        linkingCodeOptional = Optional.of(linkingCodeUUID);
                    } catch (IllegalArgumentException e) {
                        // Invalid UUID format
                        linkingCodeOptional = loggedInUser.getLinkingCode();
                    }
                }

                updatedUserDetails = new UserDetails(loggedInUser.getUuid(), name, email, password, loggedInUser.getAccountType(), linkingCodeOptional);
            }else{
                updatedUserDetails = new UserDetails(loggedInUser.getUuid(), name, email, password, loggedInUser.getAccountType(), loggedInUser.getLinkingCode());
            }

            userDetailsMap.put(loggedInUser.getUuid(), updatedUserDetails);
            loggedInUser = updatedUserDetails;
            showAlert("Details updated successfully.");
            saveUserData(); // Save user data after updating details
        });

        if (Objects.equals(loggedInUser.getAccountType(), "Personal")){
            updateBox.getChildren().addAll(accountSettingsLabel, updateDetailsLabel, formBox, buttonsBox);
        }else{
            updateBox.getChildren().addAll(accountSettingsLabel, companyCodeDescriptionLabel, companyBox, updateDetailsLabel, formBox, buttonsBox);
        }
        updatePane.setCenter(updateBox);

        rootPane.getChildren().setAll(updatePane);
    }

    private boolean isValidLinkingCode(String linkingCode) {
        // Iterate over userDetailsMap to find manager profiles
        for (UserDetails userDetails : userDetailsMap.values()) {
            if (Objects.equals(userDetails.getAccountType(), "Manager")) {
                Optional<UUID> managerLinkingCode = userDetails.getLinkingCode();
                if (managerLinkingCode.isPresent() && managerLinkingCode.get().toString().equals(linkingCode)) {
                    return true; // Valid linking code found
                }
            }
        }
        return false; // No matching linking code found
    }

    private boolean isEmailRegistered(String email) {
        // Iterate over userDetailsMap to check if email is already registered
        for (UserDetails userDetails : userDetailsMap.values()) {
            if (userDetails.getEmail().equals(email)) {
                return true; // Email already registered
            }
        }
        return false; // Email not registered
    }

    // Method to load user data from file
    private void loadUserData() {
        File file = new File("C:\\Users\\ryanwallace\\IdeaProjects\\CAB302Java\\src\\main\\java\\com\\example\\cab302javaproject\\userData.dat");

        if (file.exists() && file.length() > 0) {
            try {
                FileInputStream fileIn = new FileInputStream(file);
                ObjectInputStream objectIn = new ObjectInputStream(fileIn);
                userDetailsMap = (HashMap<UUID, UserDetails>) objectIn.readObject();
                objectIn.close();
                fileIn.close();

            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("userData.dat file is empty or does not exist.");
            userDetailsMap = new HashMap<>();
        }
    }

    private void saveUserData() {
        try {
            FileOutputStream fileOut = new FileOutputStream("C:\\Users\\ryanwallace\\IdeaProjects\\CAB302Java\\src\\main\\java\\com\\example\\cab302javaproject\\userData.dat");
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);

            objectOut.writeObject(userDetailsMap);
            objectOut.close();
            fileOut.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static boolean isValidUUID(String str) {
        try {
            UUID uuid = UUID.fromString(str);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
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

    private static class UserDetails implements Serializable {
        private final UUID uuid;
        private final String name;
        private final String email;
        private final String password;
        private final String accountType;
        private transient Optional<UUID> linkingCode;

        private static final long serialVersionUID = 1L;


        public UserDetails (UUID uuid, String name, String email, String password, String accountType, Optional<UUID> linkingCode) {
            this.uuid = uuid;
            this.name = name;
            this.email = email;
            this.password = password;
            this.accountType = accountType;
            this.linkingCode = linkingCode;
        }

        private void writeObject(ObjectOutputStream out) throws IOException {
            out.defaultWriteObject();
            out.writeBoolean(linkingCode.isPresent()); // Write a boolean indicating if linkingCode is present
            linkingCode.ifPresent(uuid -> {
                try {
                    out.writeObject(uuid); // Write the UUID if present
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

        private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
            in.defaultReadObject();
            boolean isPresent = in.readBoolean(); // Read the boolean indicating if linkingCode is present
            if (isPresent) {
                linkingCode = Optional.of((UUID) in.readObject()); // Read the UUID if present
            } else {
                linkingCode = Optional.empty(); // Set linkingCode to empty if not present
            }
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

    private static class CalendarDetails {
        private final UUID uuid;
        private final String eventName;
        private final String eventDescription;
        private final ZonedDateTime eventFrom;
        private final ZonedDateTime eventTo;
        private final List<UUID> linkingUsers;

        public CalendarDetails(UUID uuid, String eventName, String eventDescription, ZonedDateTime eventFrom, ZonedDateTime eventTo, List<UUID> linkingUsers) {
            this.uuid = uuid;
            this.eventName = eventName;
            this.eventDescription = eventDescription;
            this.eventFrom = eventFrom;
            this.eventTo = eventTo;
            this.linkingUsers = linkingUsers;
        }

        public UUID getUuid() {
            return uuid;
        }

        public String getEventName() {
            return eventName;
        }

        public String getEventDescrption() {
            return eventDescription;
        }

        public ZonedDateTime getEventFrom() {
            return eventFrom;
        }

        public ZonedDateTime getEventTo() {
            return eventTo;
        }

        public List<UUID> getLinkingUsers() {
            return linkingUsers;
        }
    }

    public static void main(String[] args) {
        launch();
    }
}