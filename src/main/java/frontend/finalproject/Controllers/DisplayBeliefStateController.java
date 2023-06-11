package frontend.finalproject.Controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class DisplayBeliefStateController {

    public void handleCloseBtn(ActionEvent event){
        Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
        stage.close();
    }

}
