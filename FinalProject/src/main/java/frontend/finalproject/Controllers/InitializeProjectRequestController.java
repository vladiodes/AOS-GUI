package frontend.finalproject.Controllers;

import DTO.HttpRequests.InitProjectRequestDTO;
import backend.finalproject.AOSFacade;
import backend.finalproject.IAOSFacade;
import frontend.finalproject.ServerResponseDisplayers.JsonTableViewVisualizer;
import frontend.finalproject.ServerResponseDisplayers.JsonTreeViewVisualizer;
import frontend.finalproject.Utils.NotificationUtils;
import frontend.finalproject.Utils.UtilsFXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import utils.Response;

import java.util.Objects;

public class InitializeProjectRequestController {
    public static final String PACKAGES_DEFAULT = "Insert all packages separated by a newline";
    public static final String ACTIONS_DEFAULT = "Insert all actions numbers separated by a newline";
    public static final String TRUE = "true";
    public static final String FALSE = "false";
    @FXML private TextField VerbosityTXT;
    @FXML private ChoiceBox<String> ManualControlCBX;
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
    @FXML private TextArea ActionsToSimulateTXT;
    @FXML private ChoiceBox<String> IsInternalSimulationCBX;
    @FXML private TextField PlanningTimePerMoveInSecondsTXT;
    @FXML private ChoiceBox<String> DebugOnMiddlewareConfigCBX;

    public static String lastPLPDirPath = "";
    public static boolean lastOnlyGenerateCode = false;
    public static boolean lastRunWithoutRebuild = false;
    public static String lastRosDistribution = "";
    public static String lastWorkspaceDirPath = "";
    public static String lastTargetLaunchFile = "";
    public static String lastRosTargetProjectPackages = "";
    public static String lastTargetProjectInitializationTimeInSeconds = "";
    public static String lastPolicyGraphDepth = "";
    public static boolean lastLoadBeliefFromDB = false;
    public static boolean lastDebugOnSolverConfig = false;
    public static String lastNumOfParticles = "";
    public static String lastNumOfBeliefStateParticlesToSaveInDB = "";
    public static String lastActionsToSimulate = "";
    public static boolean lastIsInternalSimulation = false;
    public static String lastPlanningTimePerMoveInSeconds = "";
    public static boolean lastDebugOnMiddlewareConfig = false;
    public static String lastVerbosity = "";
    public static boolean lastManualControl = false;

    private final InitProjectRequestDTO.InitProjectRequestDTOBuilder initProjectRequestDTOBuilder = new InitProjectRequestDTO.InitProjectRequestDTOBuilder();
    private final IAOSFacade facade = AOSFacade.getInstance();

    @FXML
    public void initialize(){
        PLPDirPathTXT.setText(lastPLPDirPath);
        OnlyGenerateCodeCBX.getSelectionModel().select(lastOnlyGenerateCode ? TRUE : FALSE);
        RunWithoutRebuildCBX.getSelectionModel().select(lastRunWithoutRebuild ? TRUE : FALSE);
        RosDistributionTXT.setText(lastRosDistribution);
        WorkspaceDirPathTXT.setText(lastWorkspaceDirPath);
        TargetLaunchFileTXT.setText(lastTargetLaunchFile);
        RosTargetProjectPackagesTXT.setText(lastRosTargetProjectPackages);
        TargetProjectInitializationTimeInSecondsTXT.setText(lastTargetProjectInitializationTimeInSeconds);
        PolicyGraphDepthTXT.setText(lastPolicyGraphDepth);
        LoadBeliefFromDBCBX.getSelectionModel().select(lastLoadBeliefFromDB ? TRUE : FALSE);
        DebugOnSolverConfigCBX.getSelectionModel().select(lastDebugOnSolverConfig ? TRUE : FALSE);
        NumOfParticlesTXT.setText(lastNumOfParticles);
        NumOfBeliefStateParticlesToSaveInDBTXT.setText(lastNumOfBeliefStateParticlesToSaveInDB);
        ActionsToSimulateTXT.setText(lastActionsToSimulate);
        IsInternalSimulationCBX.getSelectionModel().select(lastIsInternalSimulation ? TRUE : FALSE);
        PlanningTimePerMoveInSecondsTXT.setText(lastPlanningTimePerMoveInSeconds);
        DebugOnMiddlewareConfigCBX.getSelectionModel().select(lastDebugOnMiddlewareConfig ? TRUE : FALSE);
        VerbosityTXT.setText(lastVerbosity);
        ManualControlCBX.getSelectionModel().select(lastManualControl ? TRUE : FALSE);
    }
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

        InitProjectRequestDTO requestDTO = initProjectRequestDTOBuilder.setPLPsDirectoryPath(PLPDirPathTXT.getText())
                .setOnlyGenerateCode(Objects.equals(OnlyGenerateCodeCBX.getSelectionModel().selectedItemProperty().getValue(), TRUE))
                .setRunWithoutRebuild(Objects.equals(RunWithoutRebuildCBX.getSelectionModel().selectedItemProperty().getValue(), TRUE))
                .setRosDistribution(RosDistributionTXT.getText())
                .setWorkspaceDirectoryPath(WorkspaceDirPathTXT.getText())
                .setTargetProjectLaunchFile(TargetLaunchFileTXT.getText())
                .setRosTargetProjectPackages(RosTargetProjectPackagesTXT.getText().equals(PACKAGES_DEFAULT) || RosTargetProjectPackagesTXT.getText().isBlank() ? new String[]{} : RosTargetProjectPackagesTXT.getText().split("\n"))
                .setTargetProjectInitializationTimeInSeconds(TargetProjectInitializationTimeInSecondsTXT.getText().isEmpty() ? 1 : Integer.parseInt(TargetProjectInitializationTimeInSecondsTXT.getText()))
                .setPolicyGraphDepth(PolicyGraphDepthTXT.getText().isEmpty() ? 1 : Integer.parseInt(PolicyGraphDepthTXT.getText()))
                .setDebugOn(Objects.equals(DebugOnSolverConfigCBX.getSelectionModel().selectedItemProperty().getValue(), TRUE))
                .setNumOfParticles(NumOfParticlesTXT.getText().isEmpty() ? 1 : Integer.parseInt(NumOfParticlesTXT.getText()))
                .setBeliefStateParticlesToSave(NumOfBeliefStateParticlesToSaveInDBTXT.getText().isEmpty() ? 1 : Integer.parseInt(NumOfBeliefStateParticlesToSaveInDBTXT.getText()))
                .setLoadBeliefFromDB(Objects.equals(LoadBeliefFromDBCBX.getSelectionModel().selectedItemProperty().getValue(), TRUE))
                .setManualControl(Objects.equals(ManualControlCBX.getSelectionModel().selectedItemProperty().getValue(), TRUE))
                .setVerbosity(VerbosityTXT.getText().isEmpty() ? 1 : Integer.parseInt(VerbosityTXT.getText()))
                .setActionsToSimulate(ActionsToSimulateTXT.getText().equals(ACTIONS_DEFAULT) || ActionsToSimulateTXT.getText().isBlank() ? new String[]{} : ActionsToSimulateTXT.getText().split("\n"))
                .setIsInternalSimulation(Objects.equals(IsInternalSimulationCBX.getSelectionModel().selectedItemProperty().getValue(), TRUE))
                .setPlanningTimePerMoveInSeconds(PlanningTimePerMoveInSecondsTXT.getText().isEmpty() ? 1 : Double.parseDouble(PlanningTimePerMoveInSecondsTXT.getText()))
                .setDebugOnMiddleware(Objects.equals(DebugOnMiddlewareConfigCBX.getSelectionModel().selectedItemProperty().getValue(), TRUE))
                .build();

        saveLastValues();

        UtilsFXML.IS_MANUAL_CONTROL = Objects.equals(ManualControlCBX.getSelectionModel().selectedItemProperty().getValue(), TRUE);

        Response<String> resp = facade.sendRequest(requestDTO);

        if (resp.hasErrorOccurred())
            UtilsFXML.showNotification(NotificationUtils.ERROR_SENDING_REQUEST_TITLE, null, resp);
        else {
            UtilsFXML.loadResponseStage(new JsonTreeViewVisualizer(resp.getValue()));
        }
    }

    private void saveLastValues() {
        lastPLPDirPath = PLPDirPathTXT.getText();
        lastOnlyGenerateCode = Objects.equals(OnlyGenerateCodeCBX.getSelectionModel().selectedItemProperty().getValue(), TRUE);
        lastRunWithoutRebuild = Objects.equals(RunWithoutRebuildCBX.getSelectionModel().selectedItemProperty().getValue(), TRUE);
        lastRosDistribution = RosDistributionTXT.getText();
        lastWorkspaceDirPath = WorkspaceDirPathTXT.getText();
        lastTargetLaunchFile = TargetLaunchFileTXT.getText();
        lastRosTargetProjectPackages = RosTargetProjectPackagesTXT.getText();
        lastTargetProjectInitializationTimeInSeconds = TargetProjectInitializationTimeInSecondsTXT.getText();
        lastPolicyGraphDepth = PolicyGraphDepthTXT.getText();
        lastLoadBeliefFromDB = Objects.equals(LoadBeliefFromDBCBX.getSelectionModel().selectedItemProperty().getValue(), TRUE);
        lastDebugOnSolverConfig = Objects.equals(DebugOnSolverConfigCBX.getSelectionModel().selectedItemProperty().getValue(), TRUE);
        lastNumOfParticles = NumOfParticlesTXT.getText();
        lastNumOfBeliefStateParticlesToSaveInDB = NumOfBeliefStateParticlesToSaveInDBTXT.getText();
        lastActionsToSimulate = ActionsToSimulateTXT.getText();
        lastIsInternalSimulation = Objects.equals(IsInternalSimulationCBX.getSelectionModel().selectedItemProperty().getValue(), TRUE);
        lastPlanningTimePerMoveInSeconds = PlanningTimePerMoveInSecondsTXT.getText();
        lastDebugOnMiddlewareConfig = Objects.equals(DebugOnMiddlewareConfigCBX.getSelectionModel().selectedItemProperty().getValue(), TRUE);
        lastVerbosity = VerbosityTXT.getText();
        lastManualControl = Objects.equals(ManualControlCBX.getSelectionModel().selectedItemProperty().getValue(), TRUE);
    }

    public void handleBrowsePLPDirectoryBTNClick(ActionEvent event) {
        UtilsFXML.extractDirPathToTextField(PLPDirPathTXT);
    }
}
