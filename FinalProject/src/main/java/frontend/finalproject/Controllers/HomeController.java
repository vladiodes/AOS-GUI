package frontend.finalproject.Controllers;

import backend.finalproject.AOSFacade;
import backend.finalproject.IAOSFacade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;


public class HomeController {

    @FXML private Button btnActivateAOS;
    @FXML
    private Button btnCreateProject;
    @FXML
    private Button btnProjects;
    @FXML
    private Button btnCodeCheck;
    @FXML
    private Button btnRobotExecution;
    private final IAOSFacade facade = AOSFacade.getInstance();
    public void handleButtonClicks(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        if (event.getSource() == btnProjects) {
            UtilsFXML.loadStage(UtilsFXML.PROJECTS_FXML_PATH, stage);
        }
        else if(event.getSource() == btnCreateProject){
            UtilsFXML.loadStage(UtilsFXML.CREATE_PROJECT_PATH,stage);
        }
        else if(event.getSource() == btnCodeCheck){
            UtilsFXML.loadStage("create-skill-view.fxml",stage);
        }
    }

    @FXML
    public void initialize(){
        if(facade.showAOServerStatus().getValue()){
            setRunningAOS();
        }
        else {
            setDownAOS();
        }
    }

    private void setDownAOS() {
        btnActivateAOS.setText(UtilsFXML.SHUTDOWN_AOS_BUTTON_TXT);
        btnActivateAOS.getStyleClass().setAll("DownAOS");
    }

    private void setRunningAOS() {
        btnActivateAOS.setText(UtilsFXML.RUNNING_AOS_BUTTON_TXT);
        btnActivateAOS.getStyleClass().setAll("RunningAOS");
    }

    public void handleActivationOfAOSRequest(ActionEvent event) {
        boolean isAOSRunning = facade.showAOServerStatus().getValue();
        if(isAOSRunning && facade.deactivateAOServer().getValue()){
            setDownAOS();
        }
        else if(!isAOSRunning && facade.activateAOServer().getValue()){
            setRunningAOS();
        }
        else{
            System.out.println("WTF");
        }
    }
}
