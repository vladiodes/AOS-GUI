package frontend.finalproject.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;

import java.io.File;

public class InitializeProjectRequestController {
    @FXML private ChoiceBox<String> ProjectNameCBX;
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

    public void handleBrowseTargetProjectLaunchFileClick(ActionEvent event) {
        extractPathToTextField(TargetLaunchFileTXT);
    }

    private void extractPathToTextField(TextField textField) {
        final DirectoryChooser directoryChooser = new DirectoryChooser();
        File file = directoryChooser.showDialog(new Stage());
        if(file!=null){
            textField.setText(file.getAbsolutePath());
        }
    }

    public void handleBrowseWorkspaceDirPathClick(ActionEvent event) {
        extractPathToTextField(WorkspaceDirPathTXT);
    }

    public void handleBackBTNClick(ActionEvent event) {
        UtilsFXML.navToHome(event);
    }

    public void handleSendRequestBTN(ActionEvent event) {
    }
}
