package frontend.finalproject.Controllers;

import DTO.HttpRequests.GetSolverActionsRequestDTO;
import DTO.HttpRequests.ManualActionPutRequestDTO;
import backend.finalproject.AOSFacade;
import backend.finalproject.IAOSFacade;
import frontend.finalproject.ServerResponseDisplayers.IJsonVisualizer;
import frontend.finalproject.ServerResponseDisplayers.JsonTreeViewVisualizer;
import frontend.finalproject.ServerResponseDisplayers.ManualActionsSentVisualizer;
import frontend.finalproject.ServerResponseDisplayers.SolverActionsVisualizer;
import frontend.finalproject.Utils.NotificationUtils;
import frontend.finalproject.Utils.UtilsFXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utils.Response;

public class ManualActionRequestController {
    @FXML private VBox AvailableActionsVBOX;
    @FXML private VBox ActionsSentVBOX;
    @FXML private TextField ActionIDTxt;
    private final IAOSFacade facade = AOSFacade.getInstance();
    private SolverActionsVisualizer actionsVisualizer;
    private ManualActionsSentVisualizer actionsSentVisualizer;

    @FXML
    public void initialize(){
        Response<String> availableActionsResponse = facade.sendRequest(new GetSolverActionsRequestDTO());
        Response<String> sentActionsResponse = facade.sendRequest(new ManualActionPutRequestDTO.ManualActionGetRequestDTO());
        if(!availableActionsResponse.hasErrorOccurred()){
            this.actionsVisualizer = new SolverActionsVisualizer(availableActionsResponse.getValue());
            AvailableActionsVBOX.getChildren().add(actionsVisualizer.displayJSON());
        }
        if(!sentActionsResponse.hasErrorOccurred()){
            this.actionsSentVisualizer = new ManualActionsSentVisualizer(sentActionsResponse.getValue(),AOSFacade.getInstance());
            ActionsSentVBOX.getChildren().addAll(actionsSentVisualizer.displayJSON());
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
        {
            TreeView<String> treeView = (TreeView<String>) ActionsSentVBOX.getChildren().get(ActionsSentVBOX.getChildren().size()-1);
            treeView.getRoot().getChildren().add(actionsSentVisualizer.getActionDesc(Integer.parseInt(ActionIDTxt.getText())));
            UtilsFXML.showNotification(NotificationUtils.SENT_ACTION_SUCCESSFULLY_TITLE,NotificationUtils.SENT_ACTION_SUCCESSFULLY_TEXT,null);
        }
    }

    public void handleDeleteActionsBTN(ActionEvent actionEvent) {
        ManualActionPutRequestDTO.ManualActionDeleteRequestDTO requestDTO = new ManualActionPutRequestDTO.ManualActionDeleteRequestDTO();
        Response<String> resp = facade.sendRequest(requestDTO);
        if(resp.hasErrorOccurred()){
            UtilsFXML.showNotification(NotificationUtils.ERROR_SENDING_REQUEST_TITLE, null, resp);
        }
        else{
            TreeView<String> treeView = (TreeView<String>) ActionsSentVBOX.getChildren().get(ActionsSentVBOX.getChildren().size()-1);
            treeView.getRoot().getChildren().clear();
        }
    }
}
