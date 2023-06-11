package frontend.finalproject.Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;

public class ResponseRequestController {
    @FXML
    private VBox rootVBOX;

    private Runnable callback;

    public void setRoot(Node root) {
        this.rootVBOX.getChildren().add(root);
    }

    public void setCallback(Runnable callback) {
        this.callback = callback;
    }

    public void handleCloseBTN(ActionEvent actionEvent) {
        Scene scene = rootVBOX.getScene();
        if (scene != null) {
            Window window = scene.getWindow();
            if (window instanceof Stage stage) {
                stage.close();
                if (callback != null)
                    callback.run();
            }
        }
    }
}
