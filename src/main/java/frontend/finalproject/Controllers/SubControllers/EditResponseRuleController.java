package frontend.finalproject.Controllers.SubControllers;

import frontend.finalproject.Model.AM.ResponseRule;
import frontend.finalproject.Model.Model;
import frontend.finalproject.Utils.NotificationUtils;
import frontend.finalproject.Utils.UtilsFXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditResponseRuleController implements EditSubController {
    @FXML private TextField ResponseTXT;
    @FXML private TextArea ConditionCodeTXT;
    @FXML private Button editBTN;

    private ResponseRule model;
    private Runnable callback;

    public void setResponseRule(ResponseRule responseRule) {
        this.model = responseRule;
        ResponseTXT.setText(responseRule.getResponse());
        ConditionCodeTXT.setText(responseRule.getConditionCodeWithLocalVariables());
    }

    public void handleEditBTNClick(ActionEvent actionEvent) {
        model.setResponse(ResponseTXT.getText());
        model.setConditionCodeWithLocalVariables(ConditionCodeTXT.getText());

        Stage stage = (Stage) editBTN.getScene().getWindow();
        stage.close();
        callback.run();
        UtilsFXML.showNotification(NotificationUtils.EDIT_RESPONSE_RULE_SUCCESS_TITLE, NotificationUtils.EDIT_RESPONSE_RULE_SUCCESS_TEXT,null);
    }

    @Override
    public void setModel(Model model) {
        setResponseRule((ResponseRule) model);
    }

    public void setCallback(Runnable callback) {
        this.callback = callback;
    }
}
