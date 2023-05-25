package frontend.finalproject.Controllers;

import DTO.HttpRequests.GetLogsRequestDTO;
import DTO.HttpRequests.GetSimulatedStatesRequestDTO;
import DTO.HttpRequests.StopRobotRequestDTO;
import backend.finalproject.AOSFacade;
import backend.finalproject.IAOSFacade;
import frontend.finalproject.ServerResponseDisplayers.*;
import frontend.finalproject.Utils.NotificationUtils;
import frontend.finalproject.Utils.UtilsFXML;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;
import utils.Response;

public class IntegrationRequestsController {
    private IAOSFacade facade = AOSFacade.getInstance();
    public void handleInitializeProjectRequestBTNClick(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        UtilsFXML.loadStage(UtilsFXML.INITIALIZE_PROJECT_PATH,stage);
    }
 
    public void handleBackBTNClick(ActionEvent actionEvent) {
        UtilsFXML.navToHome(actionEvent);
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

    public void handleExecutionOutcomeRequestBTNClick(ActionEvent actionEvent){
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        UtilsFXML.loadStage(UtilsFXML.GET_EXECUTION_OUTCOME_PATH,stage);
    }

    public void handleLogsBTNClick(ActionEvent actionEvent) {
        Response<String> response = facade.sendRequest(new GetLogsRequestDTO());
        if(response.hasErrorOccurred()){
            UtilsFXML.showNotification(NotificationUtils.ERROR_SENDING_REQUEST_TITLE, null, response);
        }
        else {
            UtilsFXML.loadResponseStage(new LogsDisplayer(response.getValue()));
        }
    }
}
