package backend.finalproject;

import backend.finalproject.ProjectFiles.Project;
import frontend.finalproject.Model.AM.AMModel;
import frontend.finalproject.Model.Env.EnvModel;
import frontend.finalproject.Model.SD.SDModel;
import utils.Response;

import java.util.List;

/**
 * This is the interface of the facade (AKA API) that
 * the frontend developers will be using to call the backend services.
 * @TODO: Maybe think about creating more specific DTO object instead of returning/receiving JSON strings as params/outputs.
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

    /**
     * Loading a project from the disc into the system
     * @param name the name of the project to load.
     * There should be a project with such name in the system.
     * @return a JSON string that represents the project and all of its properties.
     * @TODO: Maybe this should return a DTO object representing the project itself
     * REQ 1.4
     */
    Response<String> loadProject(String name);

    // ============= Project and Skills REQ =============

    /**
     * Creating a new project
     * @param envModel the model as recieved from the FE
     * @return returns a response object wrapped with true - success or false - failure
     * REQ 2.1
     */
    Response<Project> createNewProject(EnvModel envModel);

    /**
     * Adding a new skill to a project
     * @param projectName the name of the project to add the skill to
     * @param sd the sd JSON string for the respective skill
     * @param am the am JSON string for the respective skill
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
     * @param projectName the name of the queried project
     * @return a response object wrapped with a list of the names of the skills
     * REQ 2.5
     */
    Response<List<String>> showAllSkillsInProject(String projectName);

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

    // ============= Robot Actions and States REQ =============

    /**
     * @return a response object wrapped with a JSON String
     * that gives information regarding the current belief state of a robot.
     * REQ 4.1
     */
    Response<String> getRobotBeliefState();

    /**
     * @return a response object wrapped with a list of JSON Strings
     * that each one of them represents the belief state history throughout the execution process.
     * REQ 4.3
     */
    Response<List<String>> getRobotBeliefStateHistory();

    /**
     * Should be invoked to stop an inner simulation.
     * @return a response object wrapped with true/false on success/failure
     * REQ 4.4
     */
    Response<Boolean> stopInnerSimulation();

    /**
     * Should be invoked to stop robot's execution
     * @return a response object wrapped with true/false on success/failure
     * REQ 4.5
     */
    Response<Boolean> stopRobotExec();

    // ============= Integration with the AOS Server REQ =============

    /**
     * Activating the robot integration request
     * Prior to activating the system should generate and build the code
     * @return a response object wrapped with true/false upon success/failure
     * REQ 5.1
     */
    Response<Boolean> activateRobotRequest();

    /**
     * Generating code WITHOUT BUILDING integration request
     * @return a response object wrapped with true/false upon success/failure
     * REQ 5.2
     */
    Response<Boolean> generateCodeRequest();

    /**
     * Activating the robot integration request
     * This is done without rebuilding the project's code
     * @return a response object wrapped with true/false upon success/failure
     * REQ 5.3.1
     */
    Response<Boolean> activateRobotRequestNoRebuild();

    /**
     * Starting an inner simulation integration request
     * @return a response object wrapped with true/false upon success/failure
     * REQ 5.3.2
     */
    Response<Boolean> activateInnerSimulation();

    /**
     * Project's documentation files correction check integration request
     * @return a response object wrapped with true/false upon success/failure
     * REQ 5.4
     */
    Response<Boolean> documentationFileCheckByAOServer();

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
     * An integration request to show compilation errors in the generated decision engine code
     * @return a response object wrapped with all the compilation errors
     * REQ 5.6.1
     */
    Response<List<String>> showErrorsInDecisionEngine();

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

    /**
     * An enum that represents the type of the documentation files.
     */
    enum DocumentationFile {
        AM,
        SD,
        ENVIRONMENT
    }
}

