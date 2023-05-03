package frontend.finalproject.Controllers;

import DTO.HttpRequests.GetSolverActionsRequestDTO;
import DTO.HttpRequests.ManualActionPutRequestDTO;
import backend.finalproject.AOSFacade;
import backend.finalproject.IAOSFacade;
import frontend.finalproject.ServerResponseDisplayers.IJsonVisualizer;
import frontend.finalproject.ServerResponseDisplayers.JsonTreeViewVisualizer;
import frontend.finalproject.ServerResponseDisplayers.ManualActionsSentVisualizer;
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

public class ManualActionRequestController {
    @FXML private VBox AvailableActionsVBOX;
    @FXML private VBox ActionsSentVBOX;
    @FXML private TextField ActionIDTxt;
    private final IAOSFacade facade = AOSFacade.getInstance();

    @FXML
    public void initialize(){
        Response<String> availableActionsResponse = facade.sendRequest(new GetSolverActionsRequestDTO());
        Response<String> sentActionsResponse = facade.sendRequest(new ManualActionPutRequestDTO.ManualActionGetRequestDTO());
        if(!availableActionsResponse.hasErrorOccurred()){
            JsonTreeViewVisualizer jsonTreeViewVisualizer = new JsonTreeViewVisualizer(availableActionsResponse.getValue());
            AvailableActionsVBOX.getChildren().add(jsonTreeViewVisualizer.displayJSON());
        }
        if(!sentActionsResponse.hasErrorOccurred()){
            IJsonVisualizer jsonVisualizer = new ManualActionsSentVisualizer(sentActionsResponse.getValue());
            ActionsSentVBOX.getChildren().addAll(new Label("Sent actions"),jsonVisualizer.displayJSON());
        }
    }

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
