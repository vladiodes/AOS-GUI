package frontend.finalproject.Controllers;

import backend.finalproject.AOSFacade;
import backend.finalproject.IAOSFacade;
import frontend.finalproject.Utils.LogsNotificationsFetcher;
import frontend.finalproject.Utils.UtilsFXML;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        IAOSFacade facade = AOSFacade.getInstance();
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(UtilsFXML.HOME_FXML_PATH));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("AOS");
        stage.setScene(scene);
        Thread t = new Thread(() -> {
            LogsNotificationsFetcher.getInstance().run();
        });
        stage.setOnCloseRequest(event -> {
            if(facade.showAOServerStatus().getValue()){
                System.out.println("Shutting down AOS...");
                facade.deactivateAOServer();
            }
            facade.cleanDirectoryFromImages();
            LogsNotificationsFetcher.getInstance().terminate();
            System.out.println("Terminating logs fetcher...");
        });
        stage.show();
        t.start();
    }

    public static void main(String[] args){
        launch();
    }
}
