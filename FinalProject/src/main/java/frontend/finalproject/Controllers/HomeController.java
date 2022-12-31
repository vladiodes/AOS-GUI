package frontend.finalproject.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;


public class HomeController {

    @FXML
    private Button btnCreateProject;
    @FXML
    private Button btnProjects;
    @FXML
    private Button btnCodeCheck;
    @FXML
    private Button btnRobotExecution;
    public void handleButtonClicks(ActionEvent event) {
        if (event.getSource() == btnProjects) {
            UtilsFXML.loadStage(UtilsFXML.PROJECTS_FXML_PATH, (Stage) ((Node) event.getSource()).getScene().getWindow());
        }
    }

}
