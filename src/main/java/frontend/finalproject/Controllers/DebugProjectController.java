package frontend.finalproject.Controllers;

import backend.finalproject.AOSFacade;
import backend.finalproject.IAOSFacade;
import frontend.finalproject.Utils.UtilsFXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import utils.Response;

import java.io.File;

public class DebugProjectController {

    @FXML
    private Button openVScodeBtn;

    @FXML
    private TextField ProjectNameTXT;
    private IAOSFacade facade = AOSFacade.getInstance();

    public void handleButtonClick(ActionEvent event){
        String path = ProjectNameTXT.getText();
        Response<Boolean> response = facade.openGeneratedFile(path, null);
        if(response.getValue()){
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e){}
            handleReturnBtn(event);
        }

    }

    private void extractFilePathToTextField(TextField textField) {
        final DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Choose Project's Directory");
        File file = directoryChooser.showDialog(new Stage());
        if(file!=null){
            textField.setText(file.getAbsolutePath());
        }
    }

    public void handleBrowseFileBTNClick(ActionEvent event) {
        extractFilePathToTextField(ProjectNameTXT);
    }

    public void handleReturnBtn(ActionEvent event){
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        UtilsFXML.loadStage(UtilsFXML.HOME_FXML_PATH,stage);
    }
}
