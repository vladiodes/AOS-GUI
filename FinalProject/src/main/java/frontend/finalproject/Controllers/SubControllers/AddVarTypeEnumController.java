package frontend.finalproject.Controllers.SubControllers;

import frontend.finalproject.Utils.UtilsFXML;
import frontend.finalproject.Utils.UtilsFXML.Source;
import frontend.finalproject.Model.Env.GlobalVariableTypeEnumModel;
import frontend.finalproject.Model.Env.GlobalVariableTypeModel;
import frontend.finalproject.Model.Model;
import frontend.finalproject.Utils.NotificationUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class AddVarTypeEnumController implements EditSubController,AddSubController {
    @FXML private Button SubmitBTN;
    @FXML private TreeView<String> enumTreeView;
    @FXML private TextField GlobalVarTypeNameTXT;
    @FXML private Label enumValueLBL;
    @FXML private TextField enumValueTXT;
    @FXML private Button nextValBTN;
    @FXML private Label titleLBL;
    private UtilsFXML.Source source = Source.ADD;

    private GlobalVariableTypeEnumModel enumModel;
    private Runnable callback;

    @FXML
    public void initialize(){
        enumModel = new GlobalVariableTypeEnumModel("","enum");
        enumTreeView.setRoot(new TreeItem<>("Enum values"));
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
        return enumModel;
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
        if(source == Source.ADD){
            handleAddModel();
        }
        else{
            handleEditModel();
        }
    }

    private void handleEditModel() {
        updateModule();
        UtilsFXML.showNotification(NotificationUtils.EDIT_GLOBAL_VAR_NEW_TYPE_TITLE, NotificationUtils.EDIT_GLOBAL_VAR_NEW_TYPE_TEXT, null);
    }

    private void updateModule() {
        enumModel.setTypeName(GlobalVarTypeNameTXT.getText());
        callback.run();
        Stage stage = (Stage) SubmitBTN.getScene().getWindow();
        stage.close();
    }

    private void handleAddModel() {
        updateModule();
        UtilsFXML.showNotification(NotificationUtils.ADDED_GLOBAL_VAR_NEW_TYPE_TITLE, NotificationUtils.ADDED_GLOBAL_VAR_NEW_TYPE_TEXT, null);
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
            setSource(Source.EDIT);
            GlobalVarTypeNameTXT.setText(enumType.getTypeName());
            enumModel = enumType;
            for(String value : enumType.getEnumValues()){
                enumTreeView.getRoot().getChildren().add(new TreeItem<>(value));
            }
        }
    }
}
