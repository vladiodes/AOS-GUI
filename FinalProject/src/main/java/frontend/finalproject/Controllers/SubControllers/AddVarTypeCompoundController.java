package frontend.finalproject.Controllers.SubControllers;

import frontend.finalproject.Model.Env.CompoundVariable;
import frontend.finalproject.Model.Env.GlobalVariableTypeCompoundModel;
import frontend.finalproject.Model.Env.GlobalVariableTypeModel;
import frontend.finalproject.Model.Model;
import frontend.finalproject.Utils.NotificationUtils;
import frontend.finalproject.Utils.UtilsFXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class AddVarTypeCompoundController implements AddSubController,EditSubController {
    @FXML private TreeView<String> compoundVarsTreeView;
    @FXML private Button SubmitBTN;
    @FXML private Label titleLBL;
    @FXML private TextField GlobalVarTypeNameTXT;
    @FXML private TextField CompoundNameTXT;
    @FXML private TextField CompoundTypeTXT;
    @FXML private TextField CompoundDefaultTXT;
    private UtilsFXML.Source source = UtilsFXML.Source.ADD;

    private GlobalVariableTypeCompoundModel compoundModel;
    private Runnable callback;

    @FXML
    public void initialize(){
        compoundModel = new GlobalVariableTypeCompoundModel("","compound");
        compoundVarsTreeView.setRoot(new TreeItem<>("Compound variables"));
    }

    public void handleInsertNextCompoundVarClick(ActionEvent actionEvent) {
        String name = CompoundNameTXT.getText();
        String type = CompoundTypeTXT.getText();
        String def = CompoundDefaultTXT.getText();

        if(!name.equals("") && !type.equals("") && !def.equals("")){
            CompoundVariable cv = new CompoundVariable(name,type,def);
            compoundModel.insertVariable(cv);
            compoundVarsTreeView.getRoot().getChildren().add(new TreeItem<>(cv.toString()));
            CompoundNameTXT.setText("");
            CompoundTypeTXT.setText("");
            CompoundDefaultTXT.setText("");
            UtilsFXML.showNotification(NotificationUtils.ADD_VAR_TYPE_COMPOUND_TITLE, NotificationUtils.ADD_VAR_TYPE_COMPOUND_TEXT, null);
        }
        else{
            UtilsFXML.showErrorNotification(NotificationUtils.ADD_VAR_TYPE_COMPOUND_FAILED_TITLE, NotificationUtils.ADD_VAR_TYPE_COMPOUND_FAILED_TEXT);
        }
    }

    public void handleAddVarTypeClick(ActionEvent actionEvent) {
        if(source == UtilsFXML.Source.ADD){
            handleAddModel();
        }
        else{
            handleEditModel();
        }
    }

    private void handleEditModel() {
        updateModule();
        UtilsFXML.showNotification(NotificationUtils.EDIT_VAR_TYPE_COMPOUND_TITLE, NotificationUtils.EDIT_VAR_TYPE_COMPOUND_TEXT, null);
    }

    private void updateModule() {
        compoundModel.setTypeName(GlobalVarTypeNameTXT.getText());
        callback.run();
        Stage stage = (Stage) SubmitBTN.getScene().getWindow();
        stage.close();
    }

    private void handleAddModel() {
        updateModule();
        UtilsFXML.showNotification(NotificationUtils.ADD_VAR_TYPE_COMPOUND_TITLE, NotificationUtils.ADD_VAR_TYPE_COMPOUND_TEXT, null);
    }

    public void setSource(UtilsFXML.Source source) {
        this.source = source;
        titleLBL.setText("Edit Compound Variable type");
        SubmitBTN.setText("Edit");
    }

    public void setGlobalVarType(GlobalVariableTypeModel type) {
        setSource(UtilsFXML.Source.EDIT);
        compoundModel = (GlobalVariableTypeCompoundModel) type;
        GlobalVarTypeNameTXT.setText(type.getTypeName());
        for(CompoundVariable cv : compoundModel.getVariables()){
            compoundVarsTreeView.getRoot().getChildren().add(new TreeItem<>(cv.toString()));
        }
    }


    @Override
    public void setModel(Model model) {
        setGlobalVarType((GlobalVariableTypeModel) model);
    }

    @Override
    public void setCallback(Runnable callback) {
        this.callback = callback;
    }

    @Override
    public Model getModel() {
        return compoundModel;
    }

    public void handleDeleteCompoundVarClick(ActionEvent actionEvent) {
        TreeItem<String> selected = compoundVarsTreeView.getSelectionModel().getSelectedItem();
        if(compoundVarsTreeView.getRoot().getChildren().contains(selected)){
            compoundModel.getVariables().remove(compoundVarsTreeView.getRoot().getChildren().indexOf(selected));
            compoundVarsTreeView.getRoot().getChildren().remove(selected);
        }
    }
}
