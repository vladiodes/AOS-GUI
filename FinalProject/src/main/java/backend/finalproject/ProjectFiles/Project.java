package backend.finalproject.ProjectFiles;

import backend.finalproject.Constants;
import backend.finalproject.ProjectFiles.AM.AM;
import backend.finalproject.ProjectFiles.Env.Environment;
import backend.finalproject.ProjectFiles.Env.GlobalVariableTypeCompound;
import backend.finalproject.ProjectFiles.Env.PlpMain;
import backend.finalproject.ProjectFiles.SD.SD;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import frontend.finalproject.Model.Env.EnvModel;
import utils.Json.CustomSerializers.GlobalVariableTypeCompoundJsonSerializer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Project {
    backend.finalproject.ProjectFiles.Env.Environment Environment;
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

    // TODO: consider refactor to utils
    public void saveEnv() {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .disableHtmlEscaping()
                .registerTypeAdapter(GlobalVariableTypeCompound.class, new GlobalVariableTypeCompoundJsonSerializer())
                .create();
        String jsonEnv = gson.toJson(Environment);
        StringBuilder envSavePath = new StringBuilder(Constants.PROJECTS_FOLDER_PATH);
        envSavePath.append("/").append(Environment.getProjectName()).append("/").append(Environment.getProjectName()).append(".environment.json");
        // TODO: save skills too
        try {
            File file = new File(envSavePath.toString());
            // create the directories if they don't exist
            file.getParentFile().mkdirs();
            file.createNewFile();
            FileWriter writer = new FileWriter(file);
            writer.write(jsonEnv);
            writer.flush();
            writer.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addSkill(SD sd, AM am) {
        Skill skill = new Skill(sd, am);
        Skills.add(skill);
        saveSkill(skill);
    }

    private void saveSkill(Skill skill) {
       // TODO
    }
}
