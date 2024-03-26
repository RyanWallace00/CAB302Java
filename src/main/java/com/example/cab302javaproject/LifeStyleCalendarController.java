package com.example.cab302javaproject;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class LifeStyleCalendarController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to the Life Style Calendar JavaFX Application!!!");
    }
}