package frontend.finalproject.Utils;

import com.google.gson.Gson;
import frontend.finalproject.Controllers.CodePreviewController;
import frontend.finalproject.Controllers.CreateEnvController;
import frontend.finalproject.Controllers.HomeController;
import frontend.finalproject.Controllers.ResponseRequestController;
import frontend.finalproject.Controllers.SubControllers.EditSubController;
import frontend.finalproject.Model.Common.ImportCodeModel;
import frontend.finalproject.Model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import utils.Response;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class UtilsFXML {
    public final static String HOME_FXML_PATH = "entry-page-view.fxml";
    public final static String PROJECTS_FXML_PATH = "projects-view.fxml";
    public static final String CREATE_PROJECT_PATH = "create-env-view.fxml";
    public static final String PREVIEW_CODE_PATH = "preview-doc-view.fxml";
    public static final String ADD_SKILL_FXML_PATH = "create-skill-view.fxml";
    public static final String RUNNING_AOS_BUTTON_TXT = "AOS: UP\nTap to shut down AOS";
    public static final String SHUTDOWN_AOS_BUTTON_TXT = "AOS: DOWN\nTap to activate AOS";
    public static final String ADD_VAR_TYPE_PATH = "add-var-type-enum-view.fxml";
    public static final String ADD_VAR_TYPE_COMPOUND_PATH = "add-var-type-compound-view.fxml";
    public static final String EDIT_GLOBAL_VAR_DEC_PATH = "edit-global-var-decl-view.fxml";
    public static final String EDIT_ASS_CODE_PATH  ="edit-ass-code-view.fxml" ;
    public static final String EDIT_STATE_PATH = "edit-special-state-view.fxml";
    public static final String EDIT_RESPONSE_RULE_PATH = "edit-response-rule-view.fxml";
    public static final String EDIT_GLOBAL_VAR_MODULE_PARAMS_PATH = "edit-global-var-module-param-view.fxml";
    public static final String EDIT_IMPORT_CODE_PATH = "edit-import-code-view.fxml";
    public static final String EDIT_SERVICE_PARAMS_PATH = "edit-service-param-view.fxml";
    public static final String ADD_SD_FILE_SKILL_PARAMS_PATH = "add-sd-file-skill-param-view.fxml";
    public static final String ADD_SKILL_CODE_RET_VALUE_PATH = "add-skill-code-ret-value-view.fxml";
    public static final String ADD_ROBOT_FRAMEWORK_LOCAL_VAR_INIT_PATH = "add-robot-framework-local-var-view.fxml";
    public static final String INITIALIZE_PROJECT_PATH = "init-project-request-view.fxml";
    public static final String INTEGRATION_REQUESTS_PATH = "integration-request-view.fxml";
    public static final String GET_SOLVER_ACTIONS_PATH = "get-solver-actions-request-view.fxml";
    public static final String GET_EXECUTION_OUTCOME_PATH = "get-execution-outcome-request-view.fxml";
    public static final String DEBUG_PROJECT_PATH = "debug-project-view.fxml";
    private static final String RESPONSE_VIEW_PATH = "response-request-view.fxml";


    public static void loadStage(String fxml, Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader();
            Parent root = loader.load(Objects.requireNonNull(HomeController.class.getResource(fxml)));
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void handlePreviewBTNClick(ActionEvent event, String json) {
        Stage stage = new Stage();
        try{
            FXMLLoader loader = new FXMLLoader(CreateEnvController.class.getResource(UtilsFXML.PREVIEW_CODE_PATH));
            Parent root = loader.load();
            CodePreviewController controller = loader.getController();
            controller.setCode(json);
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void navToHome(ActionEvent event){
        loadStage(UtilsFXML.HOME_FXML_PATH, (Stage) ((Node) event.getSource()).getScene().getWindow());
    }

    public static<T> void showNotification(String title, String text, Response<T> response) {
        if (response == null || !response.hasErrorOccurred())
            showNotification(title, text);
        else
            showErrorNotification(title,
                    response.getMessage() == null ? text : response.getMessage());
    }

    public static void showErrorNotification(String title, String text) {
        Notifications notificationBuilder = Notifications.create()
                .title(title)
                .text(text)
                .graphic(null)
                .hideAfter(Duration.seconds(5))
                .position(Pos.CENTER);

        notificationBuilder.showError();
    }

    private static void showNotification(String title, String text) {
        Notifications notificationBuilder = Notifications.create()
                .title(title)
                .text(text)
                .graphic(null)
                .hideAfter(Duration.seconds(5))
                .position(Pos.TOP_RIGHT);

        notificationBuilder.showConfirm();
    }

    public static TreeItem<String> createImportCodeTree(List<ImportCodeModel> imports) {
        TreeItem<String> importItems = new TreeItem<>("Import code");
        for (ImportCodeModel imp : imports) {
            addImportCodeModelToTree(importItems, imp);
        }
        return importItems;
    }

    public static void addImportCodeModelToTree(TreeItem<String> importItems, ImportCodeModel imp) {
        TreeItem<String> impItem = new TreeItem<>("From: " + imp.getFrom());
        for (String code : imp.getImport())
            impItem.getChildren().add(new TreeItem<>(code));
        importItems.getChildren().add(impItem);
    }

    public static void loadEditStage(String fxml, Model model, TreeItem<String> selectedItem, Runnable callback){
        Stage stage = new Stage();
        try{
            FXMLLoader loader = new FXMLLoader(EditSubController.class.getResource(fxml));
            Parent root = loader.load();
            EditSubController controller = loader.getController();
            controller.setModel(model);
            controller.setCallback(callback);

            stage.setScene(new Scene(root));
            stage.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }

    public static void extractDirPathToTextField(TextField textField) {
        final DirectoryChooser directoryChooser = new DirectoryChooser();
        File file = directoryChooser.showDialog(new Stage());
        if(file!=null){
            textField.setText(file.getAbsolutePath());
        }
    }

    public static void extractFilePathToTextField(TextField textField){
        final FileChooser fileChooser = new FileChooser();
        File file = fileChooser.showOpenDialog(new Stage());
        if(file!=null){
            textField.setText(file.getAbsolutePath());
        }
    }

    private static Node displayJSON(String json) {
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(10, 10, 10, 10));

        Map<String, Object> response = jsonToMap(json);

        VBox vbox = new VBox();
        vbox.setSpacing(20);
        vbox.setPadding(new Insets(10, 10, 10, 10));

        for (Map.Entry<String, Object> entry : response.entrySet()) {
            extractJsonEntry(vbox, entry);
        }
        borderPane.setCenter(vbox);
        return borderPane;
    }

    private static void extractJsonEntry(VBox vbox, Map.Entry<String, Object> entry) {
        VBox keyValuePair = new VBox();
        keyValuePair.setAlignment(Pos.CENTER_LEFT);
        keyValuePair.setSpacing(5);

        Label titleLabel = new Label(entry.getKey());
        titleLabel.setFont(Font.font("Segoe UI", 20));
        titleLabel.setTextFill(Color.web("#0066cc"));
        keyValuePair.getChildren().add(titleLabel);

        Object value = entry.getValue();
        if (value instanceof Map) {
            keyValuePair.getChildren().add(displayJSON(new Gson().toJson(entry.getValue())));
        } else if (value instanceof List<?>) {
            extractJsonList(keyValuePair, (List<?>) value);
        }

        vbox.getChildren().add(keyValuePair);
    }

    private static void extractJsonList(VBox keyValuePair, List<?> value) {
        for (Object str : value) {
            if (str instanceof Map || str instanceof List<?>) {
                keyValuePair.getChildren().add(displayJSON(new Gson().toJson(str)));
            } else {
                Label valueLabel = new Label(str.toString());
                valueLabel.setFont(Font.font("Segoe UI", 12));
                valueLabel.setTextFill(Color.web("#333333"));
                keyValuePair.getChildren().add(valueLabel);
            }
        }
    }

    private static Map<String, Object> jsonToMap(String json){
        Gson gson = new Gson();
        Map<String, Object> map = gson.fromJson(json, Map.class);
        return map;
    }

    public static void loadResponseStage(String jsonResponse) {
        Node node = displayJSON(jsonResponse);
        Stage stage = new Stage();
        try{
            FXMLLoader loader = new FXMLLoader(CreateEnvController.class.getResource(UtilsFXML.RESPONSE_VIEW_PATH));
            Parent root = loader.load();
            ResponseRequestController controller = loader.getController();
            controller.setRoot(node);
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }

    public enum Source {
        EDIT_ENV, EDIT_SKILL,EDIT_VAR_TYPE,ADD,EDIT
    }
}


