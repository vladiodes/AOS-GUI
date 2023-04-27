package frontend.finalproject.Controllers;

import DTO.HttpRequests.GetExecutionOutcomeRequestDTO;
import backend.finalproject.AOSFacade;
import backend.finalproject.IAOSFacade;
import frontend.finalproject.Utils.NotificationUtils;
import frontend.finalproject.Utils.UtilsFXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utils.Response;

public class GetExecutionOutcomeRequestController {

    private static final String ERROR_BELIEF_SIZE_INPUT = "Belief size input can only receive numeral positive values";
    @FXML private TextField beliefSizeVal;

    private final IAOSFacade facade = AOSFacade.getInstance();
    public void handleBackBTNClick(ActionEvent actionEvent) {
        UtilsFXML.loadStage(UtilsFXML.INTEGRATION_REQUESTS_PATH, (Stage) ((Node) actionEvent.getSource()).getScene().getWindow());
    }

    public void handleSendRequestBTN(ActionEvent actionEvent){
        try {
            int beliefSize = Integer.parseInt(beliefSizeVal.getText());
            GetExecutionOutcomeRequestDTO requestDTO = new GetExecutionOutcomeRequestDTO(beliefSize);
            Response<String> resp = facade.sendRequest(requestDTO);

            if (resp.hasErrorOccurred())
                UtilsFXML.showNotification(NotificationUtils.ERROR_SENDING_REQUEST_TITLE, null, resp);
            else {
                UtilsFXML.loadResponseStage(resp.getValue());
            }


        }catch(NumberFormatException e){
            UtilsFXML.showErrorNotification("Input Error",ERROR_BELIEF_SIZE_INPUT);
        }
    }
}
