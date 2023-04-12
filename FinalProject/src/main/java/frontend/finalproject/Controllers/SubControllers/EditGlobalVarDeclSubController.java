package frontend.finalproject.Controllers.SubControllers;

import frontend.finalproject.Utils.UtilsFXML;
import frontend.finalproject.Model.Env.GlobalVariablesDeclarationModel;
import frontend.finalproject.Model.Model;
import frontend.finalproject.Utils.NotificationUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditGlobalVarDeclSubController implements EditSubController {
    @FXML
    private Button editBTN;
    @FXML
    private TextField NameGlobalVarDecTXT;
    @FXML
    private TextField TypeGlobalVarDecTXT;
    @FXML
    private TextField IsActionParameterValueTXT;
    @FXML
    private TextArea DefaultCodeGlobVarDecTXT;

    private GlobalVariablesDeclarationModel model;
    private Runnable callback;

    public void handleEditBTNClick(ActionEvent actionEvent) {
        model.setName(NameGlobalVarDecTXT.getText());
        model.setType(TypeGlobalVarDecTXT.getText());
        model.setActionParameterValue(IsActionParameterValueTXT.getText().equals("true"));
        model.setDefaultCode(DefaultCodeGlobVarDecTXT.getText());

        Stage stage = (Stage) editBTN.getScene().getWindow();
        stage.close();
        callback.run();
        UtilsFXML.showNotification(NotificationUtils.EDIT_GLOBAL_VAR_DEC_SUCCESS, NotificationUtils.EDIT_GLOBAL_VAR_DEC_SUCCESS_MSG, null);
    }

    private void setModel(GlobalVariablesDeclarationModel model) {
        this.model = model;
        NameGlobalVarDecTXT.setText(model.getName());
        TypeGlobalVarDecTXT.setText(model.getType());
        IsActionParameterValueTXT.setText(model.isActionParameterValue() ? "true" : "false");
        DefaultCodeGlobVarDecTXT.setText(model.getDefaultCode());
    }

    @Override
    public void setModel(Model model) {
        setModel((GlobalVariablesDeclarationModel) model);
    }

    @Override
    public void setCallback(Runnable r) {
        this.callback = r;

    }

}

