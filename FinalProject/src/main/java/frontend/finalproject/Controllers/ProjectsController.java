package frontend.finalproject.Controllers;

import backend.finalproject.AOSFacade;
import backend.finalproject.IAOSFacade;
import frontend.finalproject.Model.AM.AMModel;
import frontend.finalproject.Model.Env.EnvModel;
import frontend.finalproject.Model.SD.SDModel;
import frontend.finalproject.Utils.NotificationUtils;
import frontend.finalproject.Utils.UtilsFXML;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;
import utils.Response;

import java.io.IOException;
import java.util.List;

public class ProjectsController {
    @FXML
    private Button backButton;

    @FXML
    private ListView<String> projectList;

    @FXML private ListView<String> skillsList;

    private IAOSFacade facade = AOSFacade.getInstance();

    public void handleButtonClicks(ActionEvent event) {
        if (event.getSource() == backButton) {
            UtilsFXML.navToHome(event);
        }
    }

    @FXML
    public void initialize(){
        populateProjectsList();
        projectList.setOnMouseClicked(mouseEvent -> {
            String selectedProj = projectList.getSelectionModel().getSelectedItem();
            facade.setCurrentWorkingProject(selectedProj);
            populateSkillsList(selectedProj);
        });
    }

    private void populateSkillsList(String selectedProj) {
        List<String> skills = facade.getSkillNames().getValue(); // TODO: validate response
        this.skillsList.setItems(FXCollections.observableArrayList(skills));
    }

    private void populateProjectsList() {
        List<String> projects = facade.getAllProjects().getValue();
        this.projectList.setItems(FXCollections.observableArrayList(projects));
    }

    public void handleAddSkillAction(ActionEvent event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        try{
            FXMLLoader loader = new FXMLLoader(ProjectsController.class.getResource(UtilsFXML.ADD_SKILL_FXML_PATH));
            Parent root = loader.load();
            CreateSkillController controller = loader.getController();
            controller.setProjectName(projectList.getSelectionModel().getSelectedItem());
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void handleEditEnvBTNClick(ActionEvent event) {
        Response<EnvModel> response = facade.getProjectEnv();
        if(response.hasErrorOccurred()) {
            UtilsFXML.showNotification(NotificationUtils.GENERAL_ERROR_TITLE, response.getMessage(), response);
            return;
        }

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        try{
            FXMLLoader loader = new FXMLLoader(ProjectsController.class.getResource(UtilsFXML.CREATE_PROJECT_PATH));
            Parent root = loader.load();
            CreateEnvController controller = loader.getController();
            controller.setSource(UtilsFXML.Source.EDIT_ENV);
            controller.setEnv(response.getValue());
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void handleEditSkillBTNClick(ActionEvent event) {
        String selectedSkill = skillsList.getSelectionModel().getSelectedItem();
        Response<SDModel> response1 = facade.getProjectSkillSD(selectedSkill);
        Response<AMModel> response2 = facade.getProjectSkillAM(selectedSkill);

        if(response1.hasErrorOccurred()){
            UtilsFXML.showNotification(NotificationUtils.GENERAL_ERROR_TITLE,response1.getMessage(), response1);
            return;
        }

        if(response2.hasErrorOccurred()){
            UtilsFXML.showNotification(NotificationUtils.GENERAL_ERROR_TITLE,response2.getMessage(),response2);
            return;
        }

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        try{
            FXMLLoader loader = new FXMLLoader(ProjectsController.class.getResource(UtilsFXML.ADD_SKILL_FXML_PATH));
            Parent root = loader.load();
            CreateSkillController controller = loader.getController();
            controller.setSource(UtilsFXML.Source.EDIT_SKILL);
            controller.setSD(response1.getValue());
            controller.setAM(response2.getValue());
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }
}
