package frontend.finalproject.Controllers;

import DTO.HttpRequests.GetSolverActionsRequestDTO;
import backend.finalproject.AOSFacade;
import backend.finalproject.IAOSFacade;
import frontend.finalproject.Utils.NotificationUtils;
import frontend.finalproject.Utils.UtilsFXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utils.RequestsResponse.InitProjectRequestResponse;
import utils.RequestsResponse.RequestResponse;
import utils.Response;

public class GetSolverActionsRequestController {

    @FXML private VBox SolverActionsVBOX;
    @FXML private VBox IntegrationResponseVBOX;
    private IAOSFacade facade = AOSFacade.getInstance();
    public void handleBackBTNClick(ActionEvent actionEvent) {
        UtilsFXML.loadStage(UtilsFXML.INTEGRATION_REQUESTS_PATH, (Stage) ((Node) actionEvent.getSource()).getScene().getWindow());
    }

    public void handleSendRequestBTN(ActionEvent actionEvent) {
        GetSolverActionsRequestDTO requestDTO = new GetSolverActionsRequestDTO();

        Response<String> resp = facade.sendRequest(requestDTO);

        if (resp.hasErrorOccurred())
            UtilsFXML.showNotification(NotificationUtils.ERROR_SENDING_REQUEST_TITLE, null, resp);
        else {
            showActions(resp.getValue());
        }
    }

    private void showActions(String response) {
        SolverActionsVBOX.getChildren().clear();
        IntegrationResponseVBOX.setVisible(true);
        SolverActionsVBOX.getChildren().add(new Label(response));
    }
}
