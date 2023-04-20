package backend.finalproject;

import DTO.HttpRequests.HttpRequestDTO;
import backend.finalproject.IntegrationRequests.IntegrationRequestsHandler;
import backend.finalproject.ProjectFiles.AM.AM;
import backend.finalproject.ProjectFiles.Env.Environment;
import backend.finalproject.ProjectFiles.Project;
import backend.finalproject.ProjectFiles.SD.SD;
import backend.finalproject.ProjectFiles.Skill;
import frontend.finalproject.Model.AM.AMModel;
import frontend.finalproject.Model.Env.EnvModel;
import frontend.finalproject.Model.SD.SDModel;
import utils.RequestsResponse.InitProjectRequestResponse;
import utils.RequestsResponse.RequestResponse;
import utils.Response;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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
        try {
            File[] directories = new File(PROJECTS_FOLDER_PATH).listFiles(File::isDirectory);
            return Response.OK(Arrays.stream(directories).map(File::getName).collect(Collectors.toList()));
        }
        catch (Exception e){
            return Response.FAIL(e);
        }
    }

    public Response<EnvModel> getProjectEnv() {
        try{
            validateCurrentProjectExists();
            Environment environment = currentProject.getEnvironment();
            EnvModel envModel = new EnvModel(environment);
            return Response.OK(envModel);
        }
        catch (Exception e){
            return Response.FAIL(e);
        }
    }

    @Override
    public Response<SDModel> getProjectSkillSD(String skillName) {
        try{
            validateCurrentProjectExists();
            Skill skill = currentProject.getSkill(skillName);
            SDModel sdModel = new SDModel(skill.getSd());
            return Response.OK(sdModel);
        }
        catch (Exception e){
            return Response.FAIL(e);
        }
    }

    @Override
    public Response<AMModel> getProjectSkillAM(String skillName) {
        try{
            validateCurrentProjectExists();
            Skill skill = currentProject.getSkill(skillName);
            AMModel amModel = new AMModel(skill.getAm());
            return Response.OK(amModel);
        }
        catch (Exception e){
            return Response.FAIL(e);
        }
    }

    public Response<EnvModel> createNewProject(EnvModel envModel) {
        try{
            Project project = new Project(envModel);
            EnvModel createdEnvModel = new EnvModel(project.getEnvironment());
            project.saveEnv();
            return Response.OK(createdEnvModel);
        }
        catch (Exception e){
            return Response.FAIL(e);
        }
    }

    @Override
    public Response<Boolean> setCurrentWorkingProject(String projectName) {
        try{
            currentProject = new Project(projectName);
            return Response.OK(true);
        }
        catch (Exception e){
            return Response.FAIL(e);
        }

    }


    public Response<Boolean> addSkillToProject(SDModel sdModel, AMModel amModel) {
        try {
            validateCurrentProjectExists();
            currentProject.addSkill(new SD(sdModel), new AM(amModel));
            return Response.OK(true);
        }
        catch (Exception e){
            return Response.FAIL(e);
        }

    }

    public Response<Boolean> deleteSkillFromProject(String projectName, String skillName) {
        return null;
    }

    @Override
    public Response<List<String>> getSkillNames() {

        try {
            validateCurrentProjectExists();
            return Response.OK(currentProject.getSkillsNames());
        }
        catch (Exception e){
            return Response.FAIL(e);
        }
    }

    public Response<List<String>> getSkillNames(String projectName) {
        return Response.OK(new ArrayList<>(Arrays.stream(new String[]{"Skill1","Skill2","Navigate"}).toList()));
    }

    @Override
    public Response<Boolean> saveChangesToEnv(EnvModel newEnvModel) {
        try{
            validateCurrentProjectExists();
            Environment environment = new Environment(newEnvModel);
            currentProject.setEnvironment(environment);
            return Response.OK(true);
        }
        catch (Exception e){
            return Response.FAIL(e);
        }
    }

    @Override
    public Response<Boolean> saveChangesToSkill(String prevSkillName, SDModel newSDModel, AMModel newAMModel) {
        try{
            validateCurrentProjectExists();
            currentProject.editSkill(prevSkillName, new Skill(new SD(newSDModel), new AM(newAMModel)));
            return Response.OK(true);
        }
        catch (Exception e){
            return Response.FAIL(e);
        }
    }

    public Response<Boolean> checkDocumentationFile(String file, DocumentationFile fileType) {
        return null;
    }

    @Override
    public Response<String> previewEnvJSON(EnvModel envModel) {
        try {
            Environment environment = new Environment(envModel);
            return Response.OK(environment.toJson());
        }
        catch (Exception e){
            return Response.FAIL(e);
        }
    }

    @Override
    public Response<String> previewAMJSON(AMModel amModel) {
        try {
            AM am = new AM(amModel);
            return Response.OK(am.toJson());
        }
        catch (Exception e){
            return Response.FAIL(e);
        }
    }

    @Override
    public Response<String> previewSDJSON(SDModel sdModel) {
        try {
            SD sd = new SD(sdModel);
            return Response.OK(sd.toJson());
        }
        catch (Exception e){
            return Response.FAIL(e);
        }
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

    @Override
    public Response<RequestResponse> sendRequest(HttpRequestDTO request) {
        try {
            IntegrationRequestsHandler handler = new IntegrationRequestsHandler();
            RequestResponse response = handler.handle(request);
            return Response.OK(response);
        }
        catch (Exception e){
            return Response.FAIL(e);
        }
    }

    private static boolean pingTcp(String host, int port, int timeout) {
        try (Socket socket = new Socket()) {
            socket.connect(new InetSocketAddress(host, port), timeout);
            return true;
        } catch (IOException e) {
            return false; // Either timeout or unreachable or failed DNS lookup.
        }
    }

    private void validateCurrentProjectExists() throws Exception {
        if (currentProject == null){
            throw new Exception("Please first set current project");
        }
    }
}
