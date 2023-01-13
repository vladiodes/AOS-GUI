package backend.finalproject.ProjectFiles;

import backend.finalproject.Constants;
import backend.finalproject.ProjectFiles.AM.AM;
import backend.finalproject.ProjectFiles.Common.PlpMain;
import backend.finalproject.ProjectFiles.Env.Environment;
import backend.finalproject.ProjectFiles.SD.SD;
import frontend.finalproject.Model.Env.EnvModel;

import java.io.File;
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

    // TODO: consider refactor to utils
    public void saveEnv() throws IOException {
        String jsonEnv = Environment.toJson();
        StringBuilder envSavePath = new StringBuilder(Constants.PROJECTS_FOLDER_PATH);
        envSavePath.append("/").append(getProjectName()).append("/").append(getProjectName()).append(Constants.ENVIRONMENT_FILE_SUFFIX);
        writeToFile(envSavePath.toString(), jsonEnv);
    }

    public String getProjectName() {
        return Environment.getProjectName();
    }

    private void writeToFile(String savePath, String content) throws IOException {
        File file = new File(savePath);
        file.getParentFile().mkdirs(); // create the directories if they don't exist
        file.createNewFile();
        FileWriter writer = new FileWriter(file);
        writer.write(content);
        writer.flush();
        writer.close();
    }

    public void addSkill(SD sd, AM am) throws Exception {
        PlpMain sdPlp = sd.getPlpMain();
        PlpMain amPlp = am.getPlpMain();
        if (!sdPlp.getProject().equals(amPlp.getProject()) || !sdPlp.getProject().equals(getProjectName())){
            throw new Exception("SD and AM files has to have matching Project field in PlpMain");
        }
        if (!sdPlp.getName().equals(amPlp.getName())){
            throw new Exception("SD and AM files has to have matching skill name");
        }
        if (!amPlp.getType().equals("Glue")){
            throw new Exception("AM Plp has to be type Glue");
        }
        if (!sdPlp.getType().equals("PLP")){
            throw new Exception("SD Plp has to be type PLP");
        }
        Skill skill = new Skill(sd, am);
        Skills.add(skill);
        saveSkill(skill);
    }

    public void saveSkill(Skill skill) throws IOException {
        String jsonSD = skill.getSd().toJson();
        StringBuilder sdSavePath = new StringBuilder(Constants.PROJECTS_FOLDER_PATH);
        sdSavePath.append("/").append(getProjectName()).append("/").append(getProjectName()).append(".").append(skill.getSkillName()).append(".json");

        String jsonAM = skill.getAm().toJson();
        StringBuilder amSavePath = new StringBuilder(Constants.PROJECTS_FOLDER_PATH);
        sdSavePath.append("/").append(getProjectName()).append("/").append(getProjectName()).append(".").append(skill.getSkillName()).append(" glue.json");

        writeToFile(sdSavePath.toString(), jsonSD);
        writeToFile(sdSavePath.toString(), jsonAM);

    }
}
