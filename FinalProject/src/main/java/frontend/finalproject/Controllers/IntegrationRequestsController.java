package frontend.finalproject.Controllers;

import DTO.HttpRequests.StopRobotRequestDTO;
import backend.finalproject.AOSFacade;
import backend.finalproject.IAOSFacade;
import frontend.finalproject.Utils.NotificationUtils;
import frontend.finalproject.Utils.UtilsFXML;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;
import utils.RequestsResponse.InitProjectRequestResponse;
import utils.RequestsResponse.RequestResponse;
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
        Response<RequestResponse> resp = facade.sendRequest(requestDTO);

        if (resp.hasErrorOccurred())
            UtilsFXML.showNotification(NotificationUtils.ERROR_SENDING_REQUEST_TITLE, null, resp);
        else {
            UtilsFXML.showNotification(NotificationUtils.STOP_ROBOT_REQUEST_SENT_TITLE, NotificationUtils.STOP_ROBOT_REQUEST_SENT_TXT, resp);
        }
    }

    public void handleGetSolverActionsRequestBTNClick(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        UtilsFXML.loadStage(UtilsFXML.GET_SOLVER_ACTIONS_PATH,stage);
    }

    public void handleExecutionOutcomeRequestBTNClick(ActionEvent actionEvent){
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        UtilsFXML.loadStage(UtilsFXML.GET_EXECUTION_OUTCOME_PATH,stage);
    }
}
