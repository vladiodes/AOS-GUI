package frontend.finalproject.Controllers;

import backend.finalproject.IAOSFacade;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

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
}
