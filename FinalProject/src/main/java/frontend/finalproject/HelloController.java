package frontend.finalproject;

import backend.finalproject.AOSFacade;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class HelloController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
        AOSFacade facade = new AOSFacade();
        facade.activateAOServer();
    }
}