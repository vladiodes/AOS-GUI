package frontend.finalproject.Controllers;

import DTO.HttpRequests.GetSolverActionsRequestDTO;
import DTO.HttpRequests.ManualActionPutRequestDTO;
import backend.finalproject.AOSFacade;
import backend.finalproject.IAOSFacade;

import frontend.finalproject.ServerResponseDisplayers.ManualActionsSentVisualizer;
import frontend.finalproject.ServerResponseDisplayers.SolverActionsVisualizer;
import frontend.finalproject.Utils.NotificationUtils;
import frontend.finalproject.Utils.UtilsFXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;

import utils.Response;

public class ManualActionRequestController {
    @FXML private VBox AvailableActionsVBOX;
    @FXML private VBox ActionsSentVBOX;
    private final IAOSFacade facade = AOSFacade.getInstance();
    private SolverActionsVisualizer actionsVisualizer;
    private ManualActionsSentVisualizer actionsSentVisualizer;

    @FXML
    public void initialize(){
        Response<String> availableActionsResponse = facade.sendRequest(new GetSolverActionsRequestDTO());
        Response<String> sentActionsResponse = facade.sendRequest(new ManualActionPutRequestDTO.ManualActionGetRequestDTO());
        if(!availableActionsResponse.hasErrorOccurred()){
            this.actionsVisualizer = new SolverActionsVisualizer(availableActionsResponse.getValue(), this::handleSendActionRequest);
            AvailableActionsVBOX.getChildren().add(actionsVisualizer.displayJSON());
        }
        if(!sentActionsResponse.hasErrorOccurred()){
            this.actionsSentVisualizer = new ManualActionsSentVisualizer(sentActionsResponse.getValue(),AOSFacade.getInstance());
            ActionsSentVBOX.getChildren().addAll(actionsSentVisualizer.displayJSON());
        }
    }

    public void handleSendActionRequest(int actionID){
        ManualActionPutRequestDTO requestDTO = new ManualActionPutRequestDTO(actionID);
        Response<String> resp = facade.sendRequest(requestDTO);
        if(resp.hasErrorOccurred())
            UtilsFXML.showNotification(NotificationUtils.ERROR_SENDING_REQUEST_TITLE, null, resp);
        else
        {
            TreeView<String> treeView = (TreeView<String>) ActionsSentVBOX.getChildren().get(ActionsSentVBOX.getChildren().size()-1);
            treeView.getRoot().getChildren().add(actionsSentVisualizer.getActionDesc(actionID));
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

    public void setOnActionSentCallback(Runnable onActionSentCallback) {
        this.actionsVisualizer.setOnActionSentCallback(onActionSentCallback);
    }
}
