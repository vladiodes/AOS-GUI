package frontend.finalproject.Controllers.SubControllers;

import frontend.finalproject.Controllers.CreateSkillController;
import frontend.finalproject.Controllers.UtilsFXML;
import frontend.finalproject.Model.AM.SDParametersModel;
import frontend.finalproject.Model.AM.SkillCodeReturnValueModel;
import frontend.finalproject.Model.Common.ImportCodeModel;
import frontend.finalproject.Model.Model;
import frontend.finalproject.NotificationUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class AddSkillCodeRetValueController implements AddSubController, EditSubController {

    @FXML private TextArea SkillCodeImportTXT;
    @FXML private TextField SkillCodeFromTXT;
    @FXML private Label titleLBL;
    @FXML private TextField LocalVarNameSkillCodeTXT;
    @FXML private TextField VarTypeSkillCodeTXT;
    @FXML private ChoiceBox<String> FromROSCBX;
    @FXML private TextArea AssCodeSkillCodeRetValueTXT;
    @FXML private TreeView<String> ImportCodeSkillCodeTreeView;
    @FXML private Button insertBTN;

    private SkillCodeReturnValueModel model = new SkillCodeReturnValueModel();
    private Runnable callback;

    private UtilsFXML.Source source = UtilsFXML.Source.ADD;

    @Override
    public void setModel(Model model) {
        setModel((SkillCodeReturnValueModel) model);
    }

    @Override
    public void setCallback(Runnable callback) {
        this.callback =callback;

    }

    @Override
    public Model getModel() {
        return model;
    }

    public void handleInsertImportCodeSkillCodeBTNClick(ActionEvent actionEvent) {
        String[] imports = SkillCodeImportTXT.getText().split("\n");
        ImportCodeModel importCodeModel = new ImportCodeModel();
        importCodeModel.setFrom(SkillCodeFromTXT.getText());
        for (String importCode : imports)
            importCodeModel.addImportValue(importCode);

        SkillCodeFromTXT.setText("");
        SkillCodeImportTXT.setText("");
        model.addImportCodeModel(importCodeModel);

        CreateSkillController.addImportCodeModelToTree(ImportCodeSkillCodeTreeView.getRoot(),importCodeModel);
        UtilsFXML.showNotification(NotificationUtils.ADDED_IMPORT_CODE_TITLE, NotificationUtils.ADDED_IMPORT_CODE_TEXT, null);

    }

    public void InsertClick(ActionEvent actionEvent) {
        if (source == UtilsFXML.Source.ADD) {
            handleAddModel();
        } else {
            handleEditModel();
        }
    }

    private void handleEditModel() {
        updateModel();
        UtilsFXML.showNotification(NotificationUtils.EDIT_LOCAL_VAR_INIT_SKILL_CODE_RET_VALUE_TITLE, NotificationUtils.EDIT_LOCAL_VAR_INIT_SKILL_CODE_RET_VALUE_TEXT,null);
    }

    private void updateModel() {
        model.setLocalVariableName(LocalVarNameSkillCodeTXT.getText());
        model.setVariableType(VarTypeSkillCodeTXT.getText());
        model.setFromROSServiceResponse(Boolean.parseBoolean(FromROSCBX.getValue()));
        model.setAssignmentCode(AssCodeSkillCodeRetValueTXT.getText());

        callback.run();
        Stage stage = (Stage) insertBTN.getScene().getWindow();
        stage.close();
    }

    private void handleAddModel() {
        updateModel();
        UtilsFXML.showNotification(NotificationUtils.ADDED_LOCAL_VAR_INIT_SKILL_CODE_RET_VALUE_TITLE, NotificationUtils.ADDED_LOCAL_VAR_INIT_SKILL_CODE_RET_VALUE_TEXT,null);
    }

    private void setModel(SkillCodeReturnValueModel model){
        this.model = model;
        setSource(UtilsFXML.Source.EDIT);
        
        LocalVarNameSkillCodeTXT.setText(model.getLocalVariableName());
        VarTypeSkillCodeTXT.setText(model.getVariableType());
        FromROSCBX.setValue(model.isFromROSServiceResponse() ? "true" : "false");
        AssCodeSkillCodeRetValueTXT.setText(model.getAssignmentCode());
        TreeItem<String> importItems = CreateSkillController.createImportCodeTree(model.getImportCode());
        for(TreeItem<String> item : importItems.getChildren())
            ImportCodeSkillCodeTreeView.getRoot().getChildren().add(item);

    }
    
    @FXML
    public void initialize(){
        ImportCodeSkillCodeTreeView.setRoot(new TreeItem<>("Import code"));
    }

    private void setSource(UtilsFXML.Source source) {
        this.source = source;
        titleLBL.setText("Edit Skill Code Return Value");
        insertBTN.setText("Edit");
    }


    public void handleEditImportCodeSkillCodeBTNClick(ActionEvent actionEvent) {
        TreeItem<String> selectedItem = ImportCodeSkillCodeTreeView.getSelectionModel().getSelectedItem();
        if(ImportCodeSkillCodeTreeView.getRoot().getChildren().contains(selectedItem)){
            ImportCodeModel m = model.getImportCode().get
                    (ImportCodeSkillCodeTreeView.getRoot().getChildren().indexOf(selectedItem));


            CreateSkillController.loadEditStage(UtilsFXML.EDIT_IMPORT_CODE_PATH, m,selectedItem,
                    () -> {
                        assert model != null;
                        selectedItem.setValue("From: " + m.getFrom());
                        selectedItem.getChildren().setAll(m.getImport().stream().map(TreeItem::new).toList());
                    }
            );
        }
        else{
            UtilsFXML.showErrorNotification(NotificationUtils.EDIT_IMPORT_CODE_FAIL_TITLE,NotificationUtils.EDIT_IMPORT_CODE_FAIL_TEXT);
        }
    }

    public void handleDeleteImportCodeSkillCodeBTNClick(ActionEvent actionEvent) {
        TreeItem<String> val = ImportCodeSkillCodeTreeView.getSelectionModel().getSelectedItem();
        if(ImportCodeSkillCodeTreeView.getRoot().getChildren().contains(val)){
            int selected = ImportCodeSkillCodeTreeView.getRoot().getChildren().indexOf(val);
            model.getImportCode().remove(selected);
            ImportCodeSkillCodeTreeView.getRoot().getChildren().remove(val);

            UtilsFXML.showNotification(NotificationUtils.DELETED_IMPORT_CODE_TITLE,NotificationUtils.DELETED_IMPORT_CODE_TEXT,null);
        }
        else {
            UtilsFXML.showErrorNotification(NotificationUtils.DELETE_IMPORT_CODE_FAIL_TITLE,NotificationUtils.DELETE_IMPORT_CODE_FAIL_TEXT);
        }
    }
}
