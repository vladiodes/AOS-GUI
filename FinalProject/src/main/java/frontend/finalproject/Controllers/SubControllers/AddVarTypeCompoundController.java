package frontend.finalproject.Controllers.SubControllers;

import frontend.finalproject.Controllers.UtilsFXML;
import frontend.finalproject.Model.Env.CompoundVariable;
import frontend.finalproject.Model.Env.GlobalVariableTypeCompoundModel;
import frontend.finalproject.Model.Env.GlobalVariableTypeModel;
import frontend.finalproject.NotificationUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.LinkedList;
import java.util.List;

public class AddVarTypeCompoundController implements VarTypeSubController {
    @FXML private TreeView<String> compoundVarsTreeView;
    @FXML private Button SubmitBTN;
    @FXML private Label titleLBL;
    @FXML private TextField GlobalVarTypeNameTXT;
    @FXML private TextField CompoundNameTXT;
    @FXML private TextField CompoundTypeTXT;
    @FXML private TextField CompoundDefaultTXT;
    private UtilsFXML.Source source = null;

    private GlobalVariableTypeCompoundModel compoundModel;
    private final List<GlobalVariableTypeModel> addedVars = new LinkedList<>();
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
        compoundModel.setTypeName(GlobalVarTypeNameTXT.getText());

        if(!compoundModel.getTypeName().equals("")) {
            if(source != UtilsFXML.Source.EDIT_ENV || addedVars.size() == 0) {
                addedVars.add(compoundModel);
                this.initialize();
                CompoundNameTXT.setText("");
                CompoundTypeTXT.setText("");
                CompoundDefaultTXT.setText("");
                GlobalVarTypeNameTXT.setText("");
                UtilsFXML.showNotification(NotificationUtils.ADD_VAR_TYPE_COMPOUND_TITLE, NotificationUtils.ADD_VAR_TYPE_COMPOUND_TEXT, null);
            }
        }
        else{
            UtilsFXML.showErrorNotification(NotificationUtils.ADD_VAR_TYPE_COMPOUND_FAILED_TITLE, NotificationUtils.ADD_VAR_TYPE_COMPOUND_FAILED_TEXT);
        }
    }

    public void setSource(UtilsFXML.Source source) {
        this.source = source;
        titleLBL.setText("Edit Compound Variable type");
        SubmitBTN.setText("Edit");
    }

    public void setGlobalVarType(GlobalVariableTypeModel type) {
        compoundModel = (GlobalVariableTypeCompoundModel) type;
        GlobalVarTypeNameTXT.setText(type.getTypeName());
        for(CompoundVariable cv : compoundModel.getVariables()){
            compoundVarsTreeView.getRoot().getChildren().add(new TreeItem<>(cv.toString()));
        }

    }

    public List<GlobalVariableTypeModel> getAddedVars() {
        return addedVars;
    }

    @Override
    public void setCallback(Runnable callback) {
        this.callback = callback;
    }

    public void handleDeleteCompoundVarClick(ActionEvent actionEvent) {
        TreeItem<String> selected = compoundVarsTreeView.getSelectionModel().getSelectedItem();
        if(compoundVarsTreeView.getRoot().getChildren().contains(selected)){
            compoundModel.getVariables().remove(compoundVarsTreeView.getRoot().getChildren().indexOf(selected));
            compoundVarsTreeView.getRoot().getChildren().remove(selected);
        }
    }
}
