package frontend.finalproject.Controllers.SubControllers;

import frontend.finalproject.Model.AM.SDParametersModel;
import frontend.finalproject.Model.Model;
import frontend.finalproject.Utils.NotificationUtils;
import frontend.finalproject.Utils.UtilsFXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;



public class AddSDFileSkillParamController implements EditSubController,AddSubController {
    @FXML
    private Label titleLBL;
    @FXML private TextField InputLocalVarTXT;
    @FXML private TextField FromGlobalVarTXT;
    @FXML private Button insertBTN;

    private Runnable callback;

    private UtilsFXML.Source source = UtilsFXML.Source.ADD;

    private SDParametersModel model;


    public void InsertClick(ActionEvent actionEvent) {
        if(source == UtilsFXML.Source.ADD) {
            handleAddModel();
        }else{
            handleEditModel();
        }

    }

    private void handleEditModel() {
        model.setInputLocalVariable(InputLocalVarTXT.getText());
        model.setFromGlobalVariable(FromGlobalVarTXT.getText());
        callback.run();

        Stage stage = (Stage) insertBTN.getScene().getWindow();
        stage.close();
        UtilsFXML.showNotification(NotificationUtils.EDIT_SD_FILE_SKILL_PARAM_TITLE, NotificationUtils.EDIT_SD_FILE_SKILL_PARAM_TEXT,null);
    }

    private void handleAddModel() {
        model = new SDParametersModel(InputLocalVarTXT.getText(),FromGlobalVarTXT.getText());
        callback.run();

        Stage stage = (Stage) insertBTN.getScene().getWindow();
        stage.close();
        UtilsFXML.showNotification(NotificationUtils.ADD_SD_FILE_SKILL_PARAM_TITLE, NotificationUtils.ADD_SD_FILE_SKILL_PARAM_TEXT,null);
    }

    @Override
    public void setModel(Model model) {
        setModel((SDParametersModel) model);
    }

    private void setModel(SDParametersModel model){
        this.model = model;
        setSource(UtilsFXML.Source.EDIT);
        InputLocalVarTXT.setText(model.getInputLocalVariable());
        FromGlobalVarTXT.setText(model.getFromGlobalVariable());
    }

    @Override
    public void setCallback(Runnable r) {
        this.callback = r;

    }

    @Override
    public Model getModel() {
        return model;
    }

    private void setSource(UtilsFXML.Source source){
        this.source = source;
        this.titleLBL.setText("Edit Skill Parameter");
        this.insertBTN.setText("Edit");
    }
}
