package backend.finalproject;

import DTO.HttpRequests.HttpRequestDTO;
import backend.finalproject.IntegrationRequests.IntegrationRequestsHandler;
import backend.finalproject.ProjectFiles.AM.AM;
import backend.finalproject.ProjectFiles.Env.Environment;
import backend.finalproject.ProjectFiles.Project;
import backend.finalproject.ProjectFiles.SD.SD;
import backend.finalproject.ProjectFiles.Skill;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import frontend.finalproject.Model.AM.AMModel;
import frontend.finalproject.Model.Env.EnvModel;
import frontend.finalproject.Model.SD.SDModel;
import utils.Response;
import utils.ScriptResponse;

import java.io.*;
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
        ProcessBuilder pb = new ProcessBuilder();
        String homeDir = System.getProperty("user.home");
        File dir = new File(homeDir + AOS_API_ACTIVATION_DIR);

        if(!dir.exists()){
            return Response.FAIL(new Exception("AOS directory does not exist in the Home directory, or not in the right hierarchy"));
        }
        pb.directory(dir);
        pb.redirectErrorStream(true);
        pb.command(AOS_API_ACTIVATION_COMMAND);

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
            if(directories == null)
                return Response.OK(new ArrayList<>());
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


    public Response<Boolean> openGeneratedFile(String fileName, DocumentationFile documentationFile) {
        return openGeneratedFile(fileName, documentationFile, 0);
    }

    public Response<Boolean> openGeneratedFile(String fileName, DocumentationFile documentationFile, int line) {
        try{
            ProcessBuilder pb = new ProcessBuilder("code", fileName, ":"+line);
            pb.start();
            return Response.OK(true);
        } catch (IOException e) {
            return Response.FAIL(e);
        }
    }

    public Response<Boolean> openFileInSpecificError(String errorInfo) {
        return null;
    }

    @Override
    public Response<String> sendRequest(HttpRequestDTO request) {
        try {
            IntegrationRequestsHandler handler = new IntegrationRequestsHandler();
            String response = handler.handle(request);
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

    public Response<ScriptResponse> visualizeBeliefState(JsonObject beliefState) {
        try (FileInputStream file = new FileInputStream(new File(scriptPath))) {
            String userFunction = new String(file.readAllBytes());
            String userCode = String.format("""
                    import json
                    %s
                    belief_state = json.loads('%s')
                    display(belief_state, filename = '%s')
                    """,
                    userFunction,
                    beliefState.toString(),
                    Constants.SINGLE_STATE_IMAGE_FNAME);

            return runUserPythonCode(userCode, SINGLE_STATE_IMAGE_FNAME);
        } catch (IOException e) {
            return Response.FAIL(e);
        }
    }

    private boolean hasSavedImage(String fname) {
        File currentDirectory = new File(CWD);
        File[] images = currentDirectory.listFiles(file->file.getName().matches(fname));
        return images != null && images.length > 0;
    }

    private void deletePrevImageFiles(String filename) {
        File currentDirectory = new File(CWD);
        File[] images = currentDirectory.listFiles(file->file.getName().matches(filename));

        if(images == null)
            return;

        for(File image : images){
            image.delete();
        }
    }

    @Override
    public Response<ScriptResponse> visualizeBeliefStates(JsonArray beliefStates) {
        try (FileInputStream userCode = new FileInputStream(new File(scriptPath));
             FileInputStream epilogue = new FileInputStream(new File(Constants.IMAGE_MERGER_PYTHON_SCRIPT_PATH))) {

            String pythonCode = String.format("""
                            import json
                            %s
                            belief_states = json.loads('%s')
                            %s
                            merge_images(belief_states)
                            """,
                    new String(userCode.readAllBytes()),
                    beliefStates.toString(),
                    new String(epilogue.readAllBytes()));

            return runUserPythonCode(pythonCode, Constants.MERGED_STATE_IMAGE);
        } catch (IOException e) {
            return Response.FAIL(e);
        }
    }

    private Response<ScriptResponse> runUserPythonCode(String pythonCode, String filenameToSave){
        deletePrevImageFiles(filenameToSave);
        try (FileInputStream file = new FileInputStream(new File(scriptPath))) {
            ScriptResponse response = new ScriptResponse();
            // opening a new process with the belief state initialized
            ProcessBuilder pb = new ProcessBuilder();

            pb.command("python3", "-c", pythonCode);
            pb.redirectErrorStream(true);
            Process p = pb.start();

            // reading the output of the process
            StringBuilder sb = new StringBuilder();
            InputStreamReader reader = new InputStreamReader(p.getInputStream());
            BufferedReader buffer = new BufferedReader(reader);
            String line;
            while ((line = buffer.readLine()) != null) {
                sb.append(line);
                sb.append("\n");
            }

            response.setOutput(sb.toString());
            response.setSaveFig(hasSavedImage(filenameToSave));

            return Response.OK(response);
        } catch (IOException e) {
            return Response.FAIL(e);
        }
    }

    String scriptPath;
    public void setScriptPath(String path){
        scriptPath = path;
    }

    @Override
    public Response<Boolean> cleanDirectoryFromImages() {
        deletePrevImageFiles(Constants.MERGED_STATE_IMAGE);
        deletePrevImageFiles(Constants.SINGLE_STATE_IMAGE_FNAME);
        return Response.OK(true);
    }
}
