package frontend.finalproject.Controllers.SubControllers;

import frontend.finalproject.Controllers.UtilsFXML;
import frontend.finalproject.Model.Common.AssignmentBlock;
import frontend.finalproject.Model.Model;
import frontend.finalproject.NotificationUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditAssCodeSubController implements EditSubController {


    @FXML private TextArea AssCodeTXT;
    @FXML private Button editBTN;
    @FXML private TextField AssNameTXT;
    private AssignmentBlock model;
    private Runnable callback;

    public void handleEditBTNClick(ActionEvent actionEvent) {
        AssignmentBlock temp = new AssignmentBlock(AssNameTXT.getText(), AssCodeTXT.getText());
        model.setAssignmentName(temp.getAssignmentName());
        model.setAssignmentCode(temp.getAssignmentCode());

        Stage stage = (Stage) editBTN.getScene().getWindow();
        stage.close();
        callback.run();
        UtilsFXML.showNotification(NotificationUtils.EDIT_ASS_CODE_TITLE,NotificationUtils.EDIT_ASS_CODE_TXT,null);
    }

    private void setModel(AssignmentBlock model) {
        this.model = model;
        AssNameTXT.setText(model.getAssignmentName());
        StringBuilder sb = new StringBuilder();
        for(String line : model.getAssignmentCode())
            sb.append(line).append("\n");

        AssCodeTXT.setText(sb.toString());
    }

    @Override
    public void setModel(Model model) {
        setModel((AssignmentBlock) model);
    }

    public void setCallback(Runnable callback) {
        this.callback = callback;
    }
}
