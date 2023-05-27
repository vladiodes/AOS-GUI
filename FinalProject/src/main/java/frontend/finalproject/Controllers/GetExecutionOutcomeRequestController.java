package frontend.finalproject.Controllers;

import DTO.HttpRequests.GetExecutionOutcomeRequestDTO;
import backend.finalproject.AOSFacade;
import backend.finalproject.IAOSFacade;
import frontend.finalproject.ServerResponseDisplayers.ExecutionOutcomeVisualizer;
import frontend.finalproject.ServerResponseDisplayers.JsonTableViewVisualizer;
import frontend.finalproject.ServerResponseDisplayers.JsonTreeViewVisualizer;
import frontend.finalproject.Utils.NotificationUtils;
import frontend.finalproject.Utils.UtilsFXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import utils.Response;

import java.io.File;

public class GetExecutionOutcomeRequestController {

    private static final String ERROR_BELIEF_SIZE_INPUT = "Belief size input can only receive numeral positive values";
    @FXML private TextField beliefSizeVal;
    @FXML private TextField scriptFile;

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
                ExecutionOutcomeVisualizer executionOutcomeVisualizer = new ExecutionOutcomeVisualizer(resp.getValue(), beliefSize);
                UtilsFXML.loadResponseStage(executionOutcomeVisualizer,
                        executionOutcomeVisualizer::terminateRefresh);
                if (!scriptFile.getText().isEmpty())
                    facade.setScriptPath(scriptFile.getText());
            }


        }catch(NumberFormatException e){
            UtilsFXML.showErrorNotification("Input Error",ERROR_BELIEF_SIZE_INPUT);
        }
    }

    private void extractFilePathToTextField(TextField textField) {
        final FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(new Stage());
        if(file!=null){
            textField.setText(file.getAbsolutePath());
        }
    }
    public void handleBrowseFileBTNClick(ActionEvent event) {
        extractFilePathToTextField(scriptFile);
    }
}
