package at.technikum.tourplanner;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    private Button resetButton;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Hello, World! Welcome to JavaFX Application!");
        resetButton.setVisible(true);
    }

    @FXML
    public void resetWelcomeLabel() {
        welcomeText.setText("");
        resetButton.setVisible(false);
    }
}