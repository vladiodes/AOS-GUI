package frontend.finalproject.Controllers;

import DTO.HttpRequests.StopRobotRequestDTO;
import backend.finalproject.AOSFacade;
import backend.finalproject.IAOSFacade;
import frontend.finalproject.Utils.NotificationUtils;
import frontend.finalproject.Utils.UtilsFXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import utils.Response;


public class HomeController {

    @FXML
    private Button btnIntegrationRequests;
    @FXML
    private Button btnActivateAOS;
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
        } else if (event.getSource() == btnCreateProject) {
            UtilsFXML.loadStage(UtilsFXML.CREATE_PROJECT_PATH, stage);
        } else if (event.getSource() == btnCodeCheck) {
            UtilsFXML.loadStage(UtilsFXML.DEBUG_PROJECT_PATH, stage);
        } else if (event.getSource() == btnIntegrationRequests) {
            UtilsFXML.loadStage(UtilsFXML.INTEGRATION_REQUESTS_PATH, stage);
        }
        else if(event.getSource() == btnRobotExecution){
            handleStopRobotRequestBTNClick(event);
        }
    }

    public void handleStopRobotRequestBTNClick(ActionEvent actionEvent) {
        StopRobotRequestDTO requestDTO = new StopRobotRequestDTO();
        Response<String> resp = facade.sendRequest(requestDTO);
        if (resp.hasErrorOccurred())
            UtilsFXML.showNotification(NotificationUtils.ERROR_SENDING_REQUEST_TITLE, null, resp);
        else {
            UtilsFXML.showNotification(NotificationUtils.STOP_ROBOT_REQUEST_SENT_TITLE, NotificationUtils.STOP_ROBOT_REQUEST_SENT_TXT, resp);
        }
    }

    @FXML
    public void initialize() {
        if (facade.showAOServerStatus().getValue()) {
            setRunningAOS();
        } else {
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

    public void handleBtnActivationOfAOSRequest(ActionEvent event) {
        boolean isAOSRunning = facade.showAOServerStatus().getValue();

        if (isAOSRunning) {
            Response<Boolean> response = facade.deactivateAOServer();
            if (response.hasErrorOccurred()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to de-activate AOS server.");
                alert.show();
            } else {
                setDownAOS();
            }
        } else {
            Response<Boolean> response = facade.activateAOServer();
            if (response.hasErrorOccurred()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Failed to activate the AOS server. Make sure the directory is cloned and in the right hierarchy");
                alert.show();
            } else {
                setRunningAOS();
            }
        }
    }

}
