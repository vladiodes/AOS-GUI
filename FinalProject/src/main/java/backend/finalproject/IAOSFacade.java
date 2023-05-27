package backend.finalproject;

import DTO.HttpRequests.HttpRequestDTO;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import frontend.finalproject.Model.AM.AMModel;
import frontend.finalproject.Model.Env.EnvModel;
import frontend.finalproject.Model.SD.SDModel;
import utils.Response;
import utils.ScriptResponse;

import java.util.List;

//TODO: Maybe think about creating more specific DTO object instead of returning/receiving JSON strings as params/outputs.
/**
 * This is the interface of the facade (AKA API) that
 * the frontend developers will be using to call the backend services.
 */
public interface IAOSFacade {

    // ============= GENERAL REQ =============

    /**
     * Used for activating the AOS server.
     * Precondition: AOS server is down.
     * After this function is invoked, the AOS server
     * should be up and running.
     * @return a response object wrapped with true/false if the activation was successful/not.
     * REQ 1.1
     */
    Response<Boolean> activateAOServer();

    /**
     * Used for deactivating the AOS server.
     * Precondition: AOS server is up.
     * After this function is invoked, the AOS server
     * shouldn't be running.
     * @return a response object wrapped with true/false if the deactivation was successful/not
     * REQ 1.1
     */
    Response<Boolean> deactivateAOServer();

    /**
     * Used for querying the AOS server whether it's up/down.
     * @return a response object wrapped with a boolean.
     * True stands for a running status, False for a shutdown status.
     * REQ 1.2
     */
    Response<Boolean> showAOServerStatus();

    /**
     * Used for querying the FS for which projects are available to run.
     * @return a response object wrapped with a list of all available project names in the file system.
     * REQ 1.3
     */
    Response<List<String>> getAllProjects();
    //TODO: Maybe this should return a DTO object representing the project itself
    /**
     * Loading a project from the disc into the system
     * @return a JSON string that represents the project and all of its properties.
     * REQ 1.4
     */
    Response<EnvModel> getProjectEnv();

    Response<SDModel> getProjectSkillSD(String skillName);

    Response<AMModel> getProjectSkillAM(String skillName);

    // ============= Project and Skills REQ =============

    /**
     * Creating a new project
     * @param envModel the model as received from the FE
     * @return returns a response object wrapped with the env model
     * REQ 2.1
     */
    Response<EnvModel> createNewProject(EnvModel envModel);

    Response<Boolean> setCurrentWorkingProject(String projectName);

    /**
     * Adding a new skill to a project
     * @return a response object wrapped with true - success or false - failure.
     * REQ 2.4
     */
    Response<Boolean> addSkillToProject(SDModel sdModel, AMModel amModel);

    /**
     * Deleting a skill from a project
     * @param projectName the name of the project from which the skill should be deleted
     * @param skillName the name/unique id of the skill to delete. The project should have such skill.
     * @return a response object wrapped with true - success or false - failure.
     * REQ 2.4
     */
    Response<Boolean> deleteSkillFromProject(String projectName, String skillName);

    /**
     * Returns all existing skills in a specific project
     * @return a response object wrapped with a list of the names of the skills
     * REQ 2.5
     */
    Response<List<String>> getSkillNames();

    Response<Boolean> saveChangesToEnv(EnvModel newEnvModel);

    Response<Boolean> saveChangesToSkill(String prevSkillName, SDModel newSDModel, AMModel newAMModel);

    // ============= Documentation Files REQ =============

    /**
     * Checks the correctness of a documentation file
     * @param file JSON String that represents the file
     * @param fileType the type of the documentation file
     * @return a response object wrapped with a boolean indicating if the
     * documentation file is correct/incorrect.
     * In addition, an appropriate, informative message should be provided
     * regarding the error in the documentation file.
     * For syntax errors - the message should also contain the exact place in which the error occurred.
     * REQ 3.2
     */
    Response<Boolean> checkDocumentationFile(String file, DocumentationFile fileType);

    /**
     * Returns a representation of the current env model in json format
     * @param env current env
     * @return as stated above
     */
    Response<String> previewEnvJSON(EnvModel env);

    /**
     * Same as previewEnv
     * @param AM - am file
     * @return am json to preview
     */
    Response<String> previewAMJSON(AMModel AM);

    /**
     * Same as previewEnv
     * @param SD - sd file
     * @return json to preview
     */
    Response<String> previewSDJSON(SDModel SD);

    // ============= Robot Actions and States REQ =============

    /**
     * An integration request to open the file that was generated by
     * the AOS server for debugging purposes.
     * This should open the generated file in a text editor/IDE with the cursor
     * pointing to the relevant line in the code.
     * @param fileName the name of the documentation file
     * @param documentationFile the type of the file that was generated
     * @return a response object wrapped with true/false upon success/failure
     * REQ 5.5
     */
    Response<Boolean> openGeneratedFile(String fileName, DocumentationFile documentationFile);

    /**
     * This function should open a file in the specific place in which the error occurred
     * @param errorInfo - JSON String of the error.
     *                  Should provide info such as:
     *                  - Filename
     *                  - Line in which error occurred
     *                  - etc
     * @return a response object wrapped with true/false upon success/failure
     * REQ 5.6.2
     */
    Response<Boolean> openFileInSpecificError(String errorInfo);

    Response<String> sendRequest(HttpRequestDTO request);

    Response<ScriptResponse> visualizeBeliefState(JsonObject beliefState, String filename);

    Response<Boolean> visualizeBeliefStates(JsonArray beliefStates);

    void setScriptPath(String text);

    /**
     * An enum that represents the type of the documentation files.
     */
    enum DocumentationFile {
        AM,
        SD,
        ENVIRONMENT
    }
}

