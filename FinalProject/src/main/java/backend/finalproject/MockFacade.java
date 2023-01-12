package backend.finalproject;

import backend.finalproject.ProjectFiles.Project;
import frontend.finalproject.Model.AM.AMModel;
import frontend.finalproject.Model.Env.EnvModel;
import frontend.finalproject.Model.SD.SDModel;
import utils.Response;

import java.util.ArrayList;
import java.util.List;

public class MockFacade implements IAOSFacade {
    private static IAOSFacade instance = null;
    public static IAOSFacade getInstance(){
        if (instance==null){
            instance = new MockFacade();
        }
        return instance;
    }

    private MockFacade(){

    }
    @Override
    public Response<Boolean> activateAOServer() {
        return null;
    }

    @Override
    public Response<Boolean> deactivateAOServer() {
        return null;
    }

    @Override
    public Response<Boolean> showAOServerStatus() {
        return null;
    }

    @Override
    public Response<List<String>> getAllProjects() {
        List<String> lst = new ArrayList<>();
        for(int i=1;i<=100;i++){
            lst.add("Project "+i);
        }
        return Response.OK(lst);
    }

    @Override
    public Response<String> loadProject(String name) {
        return null;
    }

    @Override
    public Response<Project> createNewProject(EnvModel envModel) {
        return null;
    }

    @Override
    public Response<Boolean> addSkillToProject(SDModel sdModel, AMModel amModel) {
        return null;
    }

    @Override
    public Response<Boolean> deleteSkillFromProject(String projectName, String skillName) {
        return null;
    }

    @Override
    public Response<List<String>> showAllSkillsInProject(String projectName) {
        List<String> lst = new ArrayList<>();
        lst.add("Skill of " + projectName);
        return Response.OK(lst);
    }

    @Override
    public Response<Boolean> checkDocumentationFile(String file, DocumentationFile fileType) {
        return null;
    }

    @Override
    public Response<String> getRobotBeliefState() {
        return null;
    }

    @Override
    public Response<List<String>> getRobotBeliefStateHistory() {
        return null;
    }

    @Override
    public Response<Boolean> stopInnerSimulation() {
        return null;
    }

    @Override
    public Response<Boolean> stopRobotExec() {
        return null;
    }

    @Override
    public Response<Boolean> activateRobotRequest() {
        return null;
    }

    @Override
    public Response<Boolean> generateCodeRequest() {
        return null;
    }

    @Override
    public Response<Boolean> activateRobotRequestNoRebuild() {
        return null;
    }

    @Override
    public Response<Boolean> activateInnerSimulation() {
        return null;
    }

    @Override
    public Response<Boolean> documentationFileCheckByAOServer() {
        return null;
    }

    @Override
    public Response<Boolean> openGeneratedFile(String fileName, DocumentationFile documentationFile) {
        return null;
    }

    @Override
    public Response<List<String>> showErrorsInDecisionEngine() {
        return null;
    }

    @Override
    public Response<Boolean> openFileInSpecificError(String errorInfo) {
        return null;
    }
}
