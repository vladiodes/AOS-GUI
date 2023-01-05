package backend.finalproject.ProjectFiles;

import backend.finalproject.Constants;
import com.google.gson.Gson;
import frontend.finalproject.Model.Env.EnvModel;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Project {
    Environment Environment;
    List<Skill> Skills;

    public Project(EnvModel envModel) {
        Environment = new Environment(envModel);
        Skills = new ArrayList<>();
    }

    public Environment getEnvironment() {
        return Environment;
    }

    public List<Skill> getSkills() {
        return Skills;
    }

    public void saveAsJson() {
        Gson gson = new Gson();
        String jsonEnv = gson.toJson(Environment);
        String envSavePath = Constants.PROJECTS_FOLDER_PATH + "/" + Environment.getProjectName() + ".environment.json";
        // TODO: save skills too
        try (FileWriter file = new FileWriter(envSavePath)) {
            file.write(jsonEnv);
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
