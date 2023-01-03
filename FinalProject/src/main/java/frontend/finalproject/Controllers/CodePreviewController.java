package frontend.finalproject.Controllers;

import backend.finalproject.IAOSFacade;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class CodePreviewController {
    @FXML
    private TextArea codeTXT;

    public void setCode(String code){
        codeTXT.setText(code);
    }

}
