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
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        if (event.getSource() == btnProjects) {
            UtilsFXML.loadStage(UtilsFXML.PROJECTS_FXML_PATH, stage);
        }
        else if(event.getSource() == btnCreateProject){
            UtilsFXML.loadStage(UtilsFXML.CREATE_PROJECT_PATH,stage );
        }
    }

}
