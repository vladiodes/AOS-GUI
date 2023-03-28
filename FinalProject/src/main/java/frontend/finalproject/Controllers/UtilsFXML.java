package frontend.finalproject.Controllers;

import frontend.finalproject.Controllers.SubControllers.AddVarTypeEnumController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import utils.Response;

import java.io.IOException;
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

    public enum Source {
        EDIT_ENV, EDIT_SKILL,EDIT_VAR_TYPE
    }
}


