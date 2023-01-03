package frontend.finalproject.Controllers;

import backend.finalproject.AOSFacade;
import backend.finalproject.IAOSFacade;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        IAOSFacade facade = AOSFacade.getInstance();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("entry-page-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("AOS");
        stage.setScene(scene);
        stage.setOnCloseRequest(event -> {
            if(facade.showAOServerStatus().getValue()){
                System.out.println("Shutting down AOS...");
                facade.deactivateAOServer();
            }
        });
        stage.show();
    }

    public static void main(String[] args){
        launch();
    }
}
