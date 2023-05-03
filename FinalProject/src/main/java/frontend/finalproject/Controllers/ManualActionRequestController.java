package frontend.finalproject.Controllers;

import DTO.HttpRequests.ManualActionPutRequestDTO;
import backend.finalproject.AOSFacade;
import backend.finalproject.IAOSFacade;
import frontend.finalproject.ServerResponseDisplayers.JsonTreeViewVisualizer;
import frontend.finalproject.Utils.NotificationUtils;
import frontend.finalproject.Utils.UtilsFXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.Response;

public class ManualActionRequestController {
    @FXML private TextField ActionIDTxt;
    private final IAOSFacade facade = AOSFacade.getInstance();

    public void handleBackBTNClick(ActionEvent actionEvent) {
        UtilsFXML.loadStage(UtilsFXML.INTEGRATION_REQUESTS_PATH, (Stage) ((Node) actionEvent.getSource()).getScene().getWindow());
    }

    public void handleSendRequestBTN(ActionEvent actionEvent) {
        ManualActionPutRequestDTO requestDTO = new ManualActionPutRequestDTO(Integer.parseInt(ActionIDTxt.getText()));
        Response<String> resp = facade.sendRequest(requestDTO);
        if(resp.hasErrorOccurred())
            UtilsFXML.showNotification(NotificationUtils.ERROR_SENDING_REQUEST_TITLE, null, resp);
        else
            UtilsFXML.loadResponseStage(new JsonTreeViewVisualizer(resp.getValue()));
    }
}
