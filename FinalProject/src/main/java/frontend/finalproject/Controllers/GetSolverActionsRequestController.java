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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utils.IntegrationRequestResponse;
import utils.Response;

public class GetSolverActionsRequestController {

    @FXML private TextField WorkspaceDirPathTXT;
    @FXML private TextField PLPDirPathTXT;
    @FXML private TextField TargetProjectLaunchFileTXT;
    @FXML private TextArea TargetProjectPackagesTXT;
    @FXML private TextField TargetProjectInitTimeTXT;
    @FXML private VBox SolverActionsVBOX;
    @FXML private VBox IntegrationResponseVBOX;
    private IAOSFacade facade = AOSFacade.getInstance();
    private final GetSolverActionsRequestDTO.GetSolverActionsRequestDTOBuilder builder = new GetSolverActionsRequestDTO.GetSolverActionsRequestDTOBuilder();

    public void handleBrowsePLPDirectoryBTNClick(ActionEvent actionEvent) {
        UtilsFXML.extractDirPathToTextField(PLPDirPathTXT);
    }

    public void handleBrowseWorkspaceDirectoryBTNClick(ActionEvent actionEvent) {
        UtilsFXML.extractFilePathToTextField(WorkspaceDirPathTXT);
    }

    public void handleBrowseTargetProjectLaunchFilePathBTNClick(ActionEvent actionEvent) {
        UtilsFXML.extractFilePathToTextField(TargetProjectLaunchFileTXT);
    }

    public void handleBackBTNClick(ActionEvent actionEvent) {
        UtilsFXML.loadStage(UtilsFXML.INTEGRATION_REQUESTS_PATH, (Stage) ((Node) actionEvent.getSource()).getScene().getWindow());
    }

    public void handleSendRequestBTN(ActionEvent actionEvent) {
        GetSolverActionsRequestDTO requestDTO = builder.setPLPsDirectoryPath(PLPDirPathTXT.getText())
                .setWorkspaceDirectoryPath(WorkspaceDirPathTXT.getText())
                .setTargetProjectLaunchFile(TargetProjectLaunchFileTXT.getText())
                .setRosTargetProjectPackages(TargetProjectPackagesTXT.getText().split("\n"))
                .setTargetProjectInitializationTimeInSeconds(TargetProjectInitTimeTXT.getText().isEmpty() ? 1 : Integer.parseInt(TargetProjectInitTimeTXT.getText()))
                .build();

        Response<IntegrationRequestResponse> resp = facade.sendRequest(requestDTO);

        if (resp.hasErrorOccurred())
            UtilsFXML.showNotification(NotificationUtils.ERROR_SENDING_REQUEST_TITLE, null, resp);
        else {
            showActions(resp.getValue());
        }
    }

    private void showActions(IntegrationRequestResponse response) {
        SolverActionsVBOX.getChildren().clear();
        IntegrationResponseVBOX.setVisible(true);
        response.getRemarks().forEach(remark -> {
            SolverActionsVBOX.getChildren().add(new Label(remark));
        });
    }
}
