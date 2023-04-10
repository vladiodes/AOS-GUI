package frontend.finalproject.Controllers;

import DTO.HttpRequests.InitProjectRequestDTO;
import backend.finalproject.AOSFacade;
import backend.finalproject.IAOSFacade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import utils.Response;

import java.io.File;
import java.util.Objects;

public class InitializeProjectRequestController {
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
        extractFilePathToTextField(TargetLaunchFileTXT);
    }

    private void extractDirPathToTextField(TextField textField) {
        final DirectoryChooser directoryChooser = new DirectoryChooser();
        File file = directoryChooser.showDialog(new Stage());
        if(file!=null){
            textField.setText(file.getAbsolutePath());
        }
    }

    private void extractFilePathToTextField(TextField textField){
        final FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(new Stage());
        if(file!=null){
            textField.setText(file.getAbsolutePath());
        }
    }

    public void handleBrowseWorkspaceDirPathClick(ActionEvent event) {
        extractDirPathToTextField(WorkspaceDirPathTXT);
    }

    public void handleBackBTNClick(ActionEvent event) {
        UtilsFXML.navToHome(event);
    }

    public void handleSendRequestBTN(ActionEvent event) {

        InitProjectRequestDTO requestDTO = initProjectRequestDTOBuilder.setPLPsDirectoryPath(PLPDirPathTXT.getText())
                .setOnlyGenerateCode(Objects.equals(OnlyGenerateCodeCBX.getSelectionModel().selectedItemProperty().getValue(), "true"))
                .setRunWithoutRebuild(Objects.equals(RunWithoutRebuildCBX.getSelectionModel().selectedItemProperty().getValue(), "true"))
                .setRosDistribution(RosDistributionTXT.getText())
                .setWorkspaceDirectortyPath(WorkspaceDirPathTXT.getText())
                .setTargetProjectLaunchFile(TargetLaunchFileTXT.getText())
                .setRosTargetProjectPackages(RosTargetProjectPackagesTXT.getText().split("\n"))
                .setTargetProjectInitializationTimeInSeconds(Integer.parseInt(TargetProjectInitializationTimeInSecondsTXT.getText()))
                .setPolicyGraphDepth(Integer.parseInt(PolicyGraphDepthTXT.getText()))
                .setDebugOn(Objects.equals(DebugOnSolverConfigCBX.getSelectionModel().selectedItemProperty().getValue(), "true"))
                .setNumOfParticles(Integer.parseInt(NumOfParticlesTXT.getText()))
                .setBeliefStateParticlesToSave(Integer.parseInt(NumOfBeliefStateParticlesToSaveInDBTXT.getText()))
                .setLoadBeliefFromDB(Objects.equals(LoadBeliefFromDBCBX.getSelectionModel().selectedItemProperty().getValue(), "true"))
                .setVerbosity(Objects.equals(VerbosityCBX.getSelectionModel().selectedItemProperty().getValue(), "true"))
                .setActionsToSimulate(ActionsToSimulateTXT.getText().split("\n"))
                .setIsInternalSimulation(Objects.equals(IsInternalSimulationCBX.getSelectionModel().selectedItemProperty().getValue(), "true"))
                .setPlanningTimePerMoveInSeconds(Double.parseDouble(PlanningTimePerMoveInSecondsTXT.getText()))
                .setDebugOnMiddleware(Objects.equals(DebugOnMiddlewareConfigCBX.getSelectionModel().selectedItemProperty().getValue(), "true"))
                .build();

        Response<String> resp = facade.sendRequest(requestDTO);

        System.out.println(resp == null ? "Resp" : resp.getValue());

    }

    public void handleBrowsePLPDirectoryBTNClick(ActionEvent event) {
        extractDirPathToTextField(PLPDirPathTXT);
    }
}
