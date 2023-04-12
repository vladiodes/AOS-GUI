package frontend.finalproject.Controllers.SubControllers;

import frontend.finalproject.Utils.UtilsFXML;
import frontend.finalproject.Model.AM.DataPublishedRobotFramework;
import frontend.finalproject.Model.Common.ImportCodeModel;
import frontend.finalproject.Model.Model;
import frontend.finalproject.Utils.NotificationUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import static frontend.finalproject.Utils.UtilsFXML.*;

public class AddRobotFrameworkLocalVarController implements AddSubController, EditSubController {
    @FXML private Label titleLBL;
    @FXML private TextField LocalVarNameRobotFrameWorkTXT;
    @FXML private TextField ROSTopicPathTXT;
    @FXML private TextField VarTypeRobotFrameworkTXT;
    @FXML private TextField InitialValueTXT;
    @FXML private TextField TopicMessageTypeTXT;
    @FXML private TextArea AssCodeRobotFrameworkTXT;
    @FXML private TextField RobotFrameworkFromTXT;
    @FXML private TextArea RobotFrameworkImportTXT;
    @FXML private TreeView<String> ImportCodeRobotFWTreeView;
    @FXML private Button insertBTN;

    private DataPublishedRobotFramework model = new DataPublishedRobotFramework();
    private Runnable callback;
    private UtilsFXML.Source source = UtilsFXML.Source.ADD;

    @Override
    public void setModel(Model model) {
        setModel((DataPublishedRobotFramework) model);
    }

    @FXML
    public void initialize(){
        ImportCodeRobotFWTreeView.setRoot(new TreeItem<>("Import code"));
    }
    private void setModel(DataPublishedRobotFramework model) {
        this.model = model;
        setSource(UtilsFXML.Source.EDIT);
        LocalVarNameRobotFrameWorkTXT.setText(model.getLocalVariableName());
        ROSTopicPathTXT.setText(model.getRosTopicPath());
        VarTypeRobotFrameworkTXT.setText(model.getVariableType());
        InitialValueTXT.setText(model.getInitialValue());
        TopicMessageTypeTXT.setText(model.getTopicMessageType());
        AssCodeRobotFrameworkTXT.setText(model.getAssignmentCode());

        TreeItem<String> importItems = createImportCodeTree(model.getImportCode());
        for(TreeItem<String> item : importItems.getChildren())
            ImportCodeRobotFWTreeView.getRoot().getChildren().add(item);

    }

    private void setSource(UtilsFXML.Source source) {
        this.source = source;
        titleLBL.setText("Edit data from robot framework");
        insertBTN.setText("Edit");
    }

    @Override
    public void setCallback(Runnable callback) {
        this.callback = callback;

    }

    @Override
    public Model getModel() {
        return model;
    }

    public void handleInsertImportCodeRobotFrameworkBTNClick(ActionEvent actionEvent) {
        String[] imports = RobotFrameworkImportTXT.getText().split("\n");
        ImportCodeModel importCodeModel = new ImportCodeModel();
        importCodeModel.setFrom(RobotFrameworkFromTXT.getText());
        for (String importCode : imports)
            importCodeModel.addImportValue(importCode);

        RobotFrameworkFromTXT.setText("");
        RobotFrameworkImportTXT.setText("");
        model.addImportCodeModel(importCodeModel);

        addImportCodeModelToTree(ImportCodeRobotFWTreeView.getRoot(),importCodeModel);
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
        UtilsFXML.showNotification(NotificationUtils.EDITED_LOCAL_VAR_INIT_PUBLIC_DATA_FROM_ROBOT_FW_TITLE, NotificationUtils.EDITED_LOCAL_VAR_INIT_PUBLIC_DATA_FROM_ROBOT_FW_TEXT,null);
    }

    private void updateModel() {
        model.setLocalVariableName(LocalVarNameRobotFrameWorkTXT.getText());
        model.setRosTopicPath(ROSTopicPathTXT.getText());
        model.setVariableType(VarTypeRobotFrameworkTXT.getText());
        model.setInitialValue(InitialValueTXT.getText());
        model.setTopicMessageType(TopicMessageTypeTXT.getText());
        model.setAssignmentCode(AssCodeRobotFrameworkTXT.getText());

        callback.run();
        Stage stage = (Stage) insertBTN.getScene().getWindow();
        stage.close();
    }

    private void handleAddModel() {
        updateModel();
        UtilsFXML.showNotification(NotificationUtils.ADDED_LOCAL_VAR_INIT_PUBLIC_DATA_FROM_ROBOT_FW_TITLE, NotificationUtils.ADDED_LOCAL_VAR_INIT_PUBLIC_DATA_FROM_ROBOT_FW_TEXT,null);
    }

    public void handleEditImportCodeBTNClick(ActionEvent actionEvent) {
        TreeItem<String> selectedItem = ImportCodeRobotFWTreeView.getSelectionModel().getSelectedItem();
        if(ImportCodeRobotFWTreeView.getRoot().getChildren().contains(selectedItem)){
            ImportCodeModel m = model.getImportCode().get
                    (ImportCodeRobotFWTreeView.getRoot().getChildren().indexOf(selectedItem));


            loadEditStage(UtilsFXML.EDIT_IMPORT_CODE_PATH, m,selectedItem,
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

    public void handleDeleteImportCodeBTNClick(ActionEvent actionEvent) {
        TreeItem<String> val = ImportCodeRobotFWTreeView.getSelectionModel().getSelectedItem();
        if(ImportCodeRobotFWTreeView.getRoot().getChildren().contains(val)){
            int selected = ImportCodeRobotFWTreeView.getRoot().getChildren().indexOf(val);
            model.getImportCode().remove(selected);
            ImportCodeRobotFWTreeView.getRoot().getChildren().remove(val);

            UtilsFXML.showNotification(NotificationUtils.DELETED_IMPORT_CODE_TITLE,NotificationUtils.DELETED_IMPORT_CODE_TEXT,null);
        }
        else {
            UtilsFXML.showErrorNotification(NotificationUtils.DELETE_IMPORT_CODE_FAIL_TITLE,NotificationUtils.DELETE_IMPORT_CODE_FAIL_TEXT);
        }
    }
}
