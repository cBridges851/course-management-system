package com.example.cms;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class CMSController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}