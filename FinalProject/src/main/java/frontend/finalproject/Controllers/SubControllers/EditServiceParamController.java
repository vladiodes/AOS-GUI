package frontend.finalproject.Controllers.SubControllers;

import frontend.finalproject.Model.AM.ServiceParameter;
import frontend.finalproject.Model.Model;
import frontend.finalproject.Utils.NotificationUtils;
import frontend.finalproject.Utils.UtilsFXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EditServiceParamController implements EditSubController {
    @FXML private TextField ServiceFieldNameTXT;
    @FXML private TextArea AssignServiceFieldCodeTXT;
    @FXML private Button editBTN;

    private ServiceParameter model;
    private Runnable callback;

    @Override
    public void setModel(Model model) {
        setModel((ServiceParameter) model);

    }

    @Override
    public void setCallback(Runnable r) {
        this.callback = r;

    }

    private void setModel(ServiceParameter model) {
        this.model = model;
        ServiceFieldNameTXT.setText(model.getServiceFieldName());
        AssignServiceFieldCodeTXT.setText(model.getAssignServiceFieldCode());
    }

    public void handleEditBTNClick(ActionEvent actionEvent) {
        model.setServiceFieldName(ServiceFieldNameTXT.getText());
        model.setAssignServiceFieldCode(AssignServiceFieldCodeTXT.getText());

        Stage stage = (Stage) editBTN.getScene().getWindow();
        stage.close();
        callback.run();

        UtilsFXML.showNotification(NotificationUtils.EDIT_SERVICE_PARAM_TITLE, NotificationUtils.EDIT_SERVICE_PARAM_TEXT,null);
    }
}
