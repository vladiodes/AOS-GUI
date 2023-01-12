package backend.finalproject;

import backend.finalproject.ProjectFiles.AM.AM;
import backend.finalproject.ProjectFiles.Project;
import backend.finalproject.ProjectFiles.SD.SD;
import frontend.finalproject.Model.AM.AMModel;
import frontend.finalproject.Model.Env.EnvModel;
import frontend.finalproject.Model.SD.SDModel;
import utils.Response;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;

import static backend.finalproject.Constants.*;

public class AOSFacade implements IAOSFacade {

    private static IAOSFacade instance = null;
    public static IAOSFacade getInstance(){
        if (instance == null)
            instance = new AOSFacade();
        return instance;
    }

    private Process AOS_API_Process;
    private Project currentProject;

    private AOSFacade(){
    }

    public Response<Boolean> activateAOServer(){
        if (pingTcp(AOS_SERVER_HOST, AOS_SERVER_PORT, DEFAULT_TIMEOUT)){
            return Response.OK(true);
        }
        ProcessBuilder pb = new ProcessBuilder("bash", "-c", AOS_API_ACTIVATION_COMMAND);
        pb.redirectErrorStream(true);

        try {
            AOS_API_Process = pb.start();
        } catch (IOException e) {
            return Response.FAIL(e);
        }

        return Response.OK(true);
    }

    public Response<Boolean> deactivateAOServer() {
        if ((!pingTcp(AOS_SERVER_HOST, AOS_SERVER_PORT, DEFAULT_TIMEOUT)) || AOS_API_Process == null){
            return Response.OK(true);
        }

        AOS_API_Process.destroy();
        AOS_API_Process = null;

        return Response.OK(true);
    }

    public Response<Boolean> showAOServerStatus() {
        return Response.OK(pingTcp(AOS_SERVER_HOST, AOS_SERVER_PORT, DEFAULT_TIMEOUT));
    }

    public Response<List<String>> getAllProjects() {
        return null;
    }

    public Response<EnvModel> loadProject(String name) {
        return null;
    }

    @Override
    public Response<SDModel> loadSkillSD(String skillName) {
        return null;
    }

    @Override
    public Response<AMModel> loadSkillAM(String skillName) {
        return null;
    }

    public Response<Project> createNewProject(EnvModel envModel) {
        try{
            Project project = new Project(envModel);
            project.saveEnv();
            return Response.OK(project);
        }
        catch (Exception e){
            return Response.FAIL(e);
        }
    }


    public Response<Boolean> addSkillToProject(SDModel sdModel, AMModel amModel) {
        if (currentProject == null){
            return Response.OK(false);
        }
        SD sd = new SD(sdModel);
        AM am = new AM(amModel);
        currentProject.addSkill(sd, am);

        return Response.OK(true);
    }

    public Response<Boolean> deleteSkillFromProject(String projectName, String skillName) {
        return null;
    }

    public Response<List<String>> showAllSkillsInProject(String projectName) {
        return null;
    }

    public Response<Boolean> checkDocumentationFile(String file, DocumentationFile fileType) {
        return null;
    }

    @Override
    public Response<String> previewEnvJSON(EnvModel env) {
        //@TODO: Change it so all json parsing will be done in backend
        return Response.OK(env.toString());
    }

    @Override
    public Response<String> previewAMJSON(AMModel AM) {
        return Response.OK(AM.toString());
    }

    @Override
    public Response<String> previewSDJSON(SDModel SD) {
        return Response.OK(SD.toString());
    }

    public Response<String> getRobotBeliefState() {
        return null;
    }

    public Response<List<String>> getRobotBeliefStateHistory() {
        return null;
    }

    public Response<Boolean> stopInnerSimulation() {
        return null;
    }

    public Response<Boolean> stopRobotExec() {
        return null;
    }

    public Response<Boolean> activateRobotRequest() {
        return null;
    }

    public Response<Boolean> generateCodeRequest() {
        return null;
    }

    public Response<Boolean> activateRobotRequestNoRebuild() {
        return null;
    }

    public Response<Boolean> activateInnerSimulation() {
        return null;
    }

    public Response<Boolean> documentationFileCheckByAOServer() {
        return null;
    }

    public Response<Boolean> openGeneratedFile(String fileName, DocumentationFile documentationFile) {
        return null;
    }

    public Response<List<String>> showErrorsInDecisionEngine() {
        return null;
    }

    public Response<Boolean> openFileInSpecificError(String errorInfo) {
        return null;
    }

    private static boolean pingTcp(String host, int port, int timeout) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, port), timeout);
            return true;
        } catch (IOException e) {
            return false; // Either timeout or unreachable or failed DNS lookup.
        }
    }
}
