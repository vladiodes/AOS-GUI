package frontend.finalproject.Controllers;

import DTO.HttpRequests.InitProjectRequestDTO;
import backend.finalproject.AOSFacade;
import backend.finalproject.IAOSFacade;
import frontend.finalproject.Utils.NotificationUtils;
import frontend.finalproject.Utils.UtilsFXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utils.RequestsResponse.InitProjectRequestResponse;
import utils.RequestsResponse.RequestResponse;
import utils.Response;

import java.util.Objects;

public class InitializeProjectRequestController {
    @FXML private VBox IntegrationResponseVBOX;
    @FXML private VBox RemarksVBOX;
    @FXML private VBox ErrorsVBOX;
    @FXML private TextField PLPDirPathTXT;
    @FXML private ChoiceBox<String> OnlyGenerateCodeCBX;
    @FXML private ChoiceBox<String> RunWithoutRebuildCBX;
    @FXML private TextField RosDistributionTXT;
    @FXML private TextField WorkspaceDirPathTXT;
    @FXML private TextField TargetLaunchFileTXT;
    @FXML private TextArea RosTargetProjectPackagesTXT;
    @FXML private TextField TargetProjectInitializationTimeInSecondsTXT;
    @FXML private TextField PolicyGraphDepthTXT;
    @FXML private ChoiceBox<String> LoadBeliefFromDBCBX;
    @FXML private ChoiceBox<String> DebugOnSolverConfigCBX;
    @FXML private TextField NumOfParticlesTXT;
    @FXML private TextField NumOfBeliefStateParticlesToSaveInDBTXT;
    @FXML private ChoiceBox<String> VerbosityCBX;
    @FXML private TextArea ActionsToSimulateTXT;
    @FXML private ChoiceBox<String> IsInternalSimulationCBX;
    @FXML private TextField PlanningTimePerMoveInSecondsTXT;
    @FXML private ChoiceBox<String> DebugOnMiddlewareConfigCBX;
    private final InitProjectRequestDTO.InitProjectRequestDTOBuilder initProjectRequestDTOBuilder = new InitProjectRequestDTO.InitProjectRequestDTOBuilder();
    private final IAOSFacade facade = AOSFacade.getInstance();

    public void handleBrowseTargetProjectLaunchFileClick(ActionEvent event) {
        UtilsFXML.extractFilePathToTextField(TargetLaunchFileTXT);
    }

    public void handleBrowseWorkspaceDirPathClick(ActionEvent event) {
        UtilsFXML.extractDirPathToTextField(WorkspaceDirPathTXT);
    }

    public void handleBackBTNClick(ActionEvent event) {
        UtilsFXML.loadStage(UtilsFXML.INTEGRATION_REQUESTS_PATH, (Stage) ((Node) event.getSource()).getScene().getWindow());
    }

    public void handleSendRequestBTN(ActionEvent event) {
        ErrorsVBOX.getChildren().clear();
        RemarksVBOX.getChildren().clear();

        InitProjectRequestDTO requestDTO = initProjectRequestDTOBuilder.setPLPsDirectoryPath(PLPDirPathTXT.getText())
                .setOnlyGenerateCode(Objects.equals(OnlyGenerateCodeCBX.getSelectionModel().selectedItemProperty().getValue(), "true"))
                .setRunWithoutRebuild(Objects.equals(RunWithoutRebuildCBX.getSelectionModel().selectedItemProperty().getValue(), "true"))
                .setRosDistribution(RosDistributionTXT.getText())
                .setWorkspaceDirectoryPath(WorkspaceDirPathTXT.getText())
                .setTargetProjectLaunchFile(TargetLaunchFileTXT.getText())
                .setRosTargetProjectPackages(RosTargetProjectPackagesTXT.getText().split("\n"))
                .setTargetProjectInitializationTimeInSeconds(TargetProjectInitializationTimeInSecondsTXT.getText().isEmpty() ? 1 : Integer.parseInt(TargetProjectInitializationTimeInSecondsTXT.getText()))
                .setPolicyGraphDepth(PolicyGraphDepthTXT.getText().isEmpty() ? 1 : Integer.parseInt(PolicyGraphDepthTXT.getText()))
                .setDebugOn(Objects.equals(DebugOnSolverConfigCBX.getSelectionModel().selectedItemProperty().getValue(), "true"))
                .setNumOfParticles(NumOfParticlesTXT.getText().isEmpty() ? 1 : Integer.parseInt(NumOfParticlesTXT.getText()))
                .setBeliefStateParticlesToSave(NumOfBeliefStateParticlesToSaveInDBTXT.getText().isEmpty() ? 1 : Integer.parseInt(NumOfBeliefStateParticlesToSaveInDBTXT.getText()))
                .setLoadBeliefFromDB(Objects.equals(LoadBeliefFromDBCBX.getSelectionModel().selectedItemProperty().getValue(), "true"))
                .setVerbosity(Objects.equals(VerbosityCBX.getSelectionModel().selectedItemProperty().getValue(), "true"))
                .setActionsToSimulate(ActionsToSimulateTXT.getText().split("\n"))
                .setIsInternalSimulation(Objects.equals(IsInternalSimulationCBX.getSelectionModel().selectedItemProperty().getValue(), "true"))
                .setPlanningTimePerMoveInSeconds(PlanningTimePerMoveInSecondsTXT.getText().isEmpty() ? 1 : Double.parseDouble(PlanningTimePerMoveInSecondsTXT.getText()))
                .setDebugOnMiddleware(Objects.equals(DebugOnMiddlewareConfigCBX.getSelectionModel().selectedItemProperty().getValue(), "true"))
                .build();

        Response<RequestResponse> resp = facade.sendRequest(requestDTO);

        if (resp.hasErrorOccurred())
            UtilsFXML.showNotification(NotificationUtils.ERROR_SENDING_REQUEST_TITLE, null, resp);
        else {
            showRemarksAndErrors((InitProjectRequestResponse) resp.getValue());
        }
    }

    private void showRemarksAndErrors(InitProjectRequestResponse response) {
        IntegrationResponseVBOX.visibleProperty().setValue(true);
        for (String s : response.getErrors()) {
            ErrorsVBOX.getChildren().add(new Label(s));
        }
        for (String s : response.getRemarks()) {
            RemarksVBOX.getChildren().add(new Label(s));
        }
    }

    public void handleBrowsePLPDirectoryBTNClick(ActionEvent event) {
        UtilsFXML.extractDirPathToTextField(PLPDirPathTXT);
    }
}
