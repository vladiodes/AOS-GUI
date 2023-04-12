package frontend.finalproject.Controllers.SubControllers;

import frontend.finalproject.Utils.UtilsFXML;
import frontend.finalproject.Model.Model;
import frontend.finalproject.Model.SD.GlobalVariableModuleParametersModel;
import frontend.finalproject.Utils.NotificationUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditGlobalVarModuleParamsController implements EditSubController{
    @FXML private TextField GlobalVarModuleParamNameTXT;
    @FXML private TextField GlobalVarModuleParamTypeTXT;
    @FXML private Button editBTN;

    private GlobalVariableModuleParametersModel model;
    private Runnable callback;

    public void handleEditBTNClick(ActionEvent actionEvent) {
        model.setName(GlobalVarModuleParamNameTXT.getText());
        model.setType(GlobalVarModuleParamTypeTXT.getText());

        Stage stage = (Stage) editBTN.getScene().getWindow();
        stage.close();
        callback.run();
        UtilsFXML.showNotification(NotificationUtils.EDIT_GLOBAL_VAR_MODULE_PARAMS_SUCCESS_TITLE, NotificationUtils.EDIT_GLOBAL_VAR_MODULE_PARAMS_SUCCESS_TEXT,null);
    }

    @Override
    public void setModel(Model model) {
        setModel((GlobalVariableModuleParametersModel) model);
    }

    public void setModel(GlobalVariableModuleParametersModel model) {
        this.model = model;
        GlobalVarModuleParamNameTXT.setText(model.getName());
        GlobalVarModuleParamTypeTXT.setText(model.getType());
    }

    @Override
    public void setCallback(Runnable r) {
        this.callback = r;
    }
}
