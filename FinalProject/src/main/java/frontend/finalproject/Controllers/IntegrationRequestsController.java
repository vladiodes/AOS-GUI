package frontend.finalproject.Controllers;

import frontend.finalproject.Utils.UtilsFXML;
import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class IntegrationRequestsController {
    public void handleInitializeProjectRequestBTNClick(ActionEvent actionEvent) {
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        UtilsFXML.loadStage(UtilsFXML.INITIALIZE_PROJECT_PATH,stage);
    }

    public void handleBackBTNClick(ActionEvent actionEvent) {
        UtilsFXML.navToHome(actionEvent);
    }
}
