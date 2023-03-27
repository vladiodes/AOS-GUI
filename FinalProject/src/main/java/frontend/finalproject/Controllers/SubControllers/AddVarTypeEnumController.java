package frontend.finalproject.Controllers.SubControllers;

import frontend.finalproject.Controllers.UtilsFXML;
import frontend.finalproject.Controllers.UtilsFXML.Source;
import frontend.finalproject.Model.Env.GlobalVariableTypeEnumModel;
import frontend.finalproject.Model.Env.GlobalVariableTypeModel;
import frontend.finalproject.NotificationUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.*;

public class AddVarTypeEnumController implements VarTypeSubController {
    @FXML private Button SubmitBTN;
    @FXML private TreeView<String> enumTreeView;
    @FXML private TextField GlobalVarTypeNameTXT;
    @FXML private Label enumValueLBL;
    @FXML private TextField enumValueTXT;
    @FXML private Button nextValBTN;
    @FXML private Label titleLBL;
    private UtilsFXML.Source source = null;

    private GlobalVariableTypeEnumModel enumModel;
    private final List<GlobalVariableTypeModel> addedVars = new LinkedList<>();

    @FXML
    public void initialize(){
        enumModel = new GlobalVariableTypeEnumModel("","enum");
        enumTreeView.setRoot(new TreeItem<>("Enum values"));
    }

    public List<GlobalVariableTypeModel> getAddedVars() {
        return addedVars;
    }

    public void handleInsertNextEnumValClick(ActionEvent event) {
        String value = enumValueTXT.getText();
        if(!value.equals("")){

            enumModel.addEnumValue(value);
            enumTreeView.getRoot().getChildren().add(new TreeItem<>(value));
            enumValueTXT.setText("");
            UtilsFXML.showNotification(NotificationUtils.ADDED_ENUM_VALUE_TITLE,NotificationUtils.ADDED_ENUM_VALUE_TEXT,null);
        }
        else{
            UtilsFXML.showErrorNotification(NotificationUtils.ADDED_ENUM_VALUE_FAILED_TITLE,NotificationUtils.ADDED_ENUM_VALUE_FAILED_TEXT);
        }
    }

    public void handleAddVarTypeClick(ActionEvent actionEvent) {
        enumModel.setTypeName(GlobalVarTypeNameTXT.getText());

        if(!enumModel.getTypeName().equals("")) {
            if(source != Source.EDIT_ENV || addedVars.size() == 0) {
                addedVars.add(enumModel);
                this.initialize();
                UtilsFXML.showNotification(NotificationUtils.ADDED_GLOBAL_VAR_NEW_TYPE_TITLE, NotificationUtils.ADDED_GLOBAL_VAR_NEW_TYPE_TEXT, null);
            }
        }
        else{
            UtilsFXML.showErrorNotification(NotificationUtils.ADDED_GLOBAL_VAR_NEW_TYPE_FAILED_TITLE,NotificationUtils.ADDED_GLOBAL_VAR_TYPE_FAILED_TEXT);
        }

    }

    public void handleDeleteEnumValClick(ActionEvent actionEvent) {
        TreeItem<String> selected = enumTreeView.getSelectionModel().getSelectedItem();
        if(enumTreeView.getRoot().getChildren().contains(selected)){
            enumTreeView.getRoot().getChildren().remove(selected);
            enumModel.getEnumValues().remove(selected.getValue());
            UtilsFXML.showNotification(NotificationUtils.REMOVED_ENUM_VALUE_TITLE,NotificationUtils.REMOVED_ENUM_VALUE_TEXT,null);
        }
        else {
            UtilsFXML.showErrorNotification(NotificationUtils.REMOVED_ENUM_VALUE_FAILED_TITLE,NotificationUtils.REMOVED_ENUM_VALUE_FAILED_TEXT);
        }
    }

    public void setSource(UtilsFXML.Source source) {
        titleLBL.setText("Edit enum type");
        SubmitBTN.setText("Edit enum type");
        this.source = source;
    }

    public void setGlobalVarType(GlobalVariableTypeModel type) {
        if(type instanceof GlobalVariableTypeEnumModel enumType){
            GlobalVarTypeNameTXT.setText(enumType.getTypeName());
            enumModel = enumType;
            for(String value : enumType.getEnumValues()){
                enumTreeView.getRoot().getChildren().add(new TreeItem<>(value));
            }
        }
    }
}
