package backend.finalproject;

import backend.finalproject.ProjectFiles.Project;
import frontend.finalproject.Model.Env.EnvModel;
import utils.Response;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.List;

import static backend.finalproject.Constants.*;

public class AOSFacade implements IAOSFacade {

    private Process AOS_API_Process;
    private Project currentProject;

    public AOSFacade(){
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

    public Response<String> loadProject(String name) {
        return null;
    }

    public Response<Boolean> createNewProject(EnvModel envModel) {
        try{
            Project project = new Project(envModel);
            saveProject(project);
        }
        catch (Exception e){
            return Response.FAIL(e);
        }
        return Response.OK(true);
    }

    private void saveProject(Project environment) {

    }

    public Response<Boolean> addSkillToProject(String projectName, String sd, String am) {
        return null;
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
