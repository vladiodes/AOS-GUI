package frontend.finalproject.Controllers;

import backend.finalproject.AOSFacade;
import backend.finalproject.IAOSFacade;
import backend.finalproject.MockFacade;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class ProjectsController {
    @FXML
    private Button backButton;

    @FXML
    private ListView<String> projectList;

    @FXML private ListView<String> skillsList;

    private IAOSFacade facade = MockFacade.getInstance();

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
            populateSkillsList(selectedProj);
        });
    }

    private void populateSkillsList(String selectedProj) {
        List<String> skills = facade.showAllSkillsInProject(selectedProj).getValue();
        this.skillsList.setItems(FXCollections.observableArrayList(skills));
    }

    private void populateProjectsList() {
        List<String> projects = facade.getAllProjects().getValue();
        this.projectList.setItems(FXCollections.observableArrayList(projects));
    }

    public void setFacade(IAOSFacade facade) {
        this.facade = facade;
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
}
