package backend.finalproject.ProjectFiles;

import backend.finalproject.Constants;
import backend.finalproject.ProjectFiles.AM.AM;
import backend.finalproject.ProjectFiles.AM.LocalVariablesInit.LocalVariablesInitialization;
import backend.finalproject.ProjectFiles.Common.IAssignmentBlock;
import backend.finalproject.ProjectFiles.Common.PlpMain;
import backend.finalproject.ProjectFiles.Env.Environment;
import backend.finalproject.ProjectFiles.Env.GlobalVariableType;
import backend.finalproject.ProjectFiles.Env.GlobalVariableTypeCompound;
import backend.finalproject.ProjectFiles.Env.GlobalVariableTypeEnum;
import backend.finalproject.ProjectFiles.SD.SD;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import frontend.finalproject.Model.Env.EnvModel;
import utils.Json.CustomDeserializers.AssignmentBlockDeserializer;
import utils.Json.CustomDeserializers.LocalVariablesInitializationDeserializer;
import utils.Json.CustomSerializers.GlobalVariableTypeCompoundJsonSerializer;
import utils.Json.CustomSerializers.GlobalVariableTypeEnumJsonSerializer;
import utils.Json.PolymorphDeserializer.PolymorphDeserializer;
import utils.Pair;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class Project {
    Environment Environment;
    List<Skill> Skills;

    Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .disableHtmlEscaping()
            .registerTypeAdapter(GlobalVariableTypeCompound .class, new GlobalVariableTypeCompoundJsonSerializer())
            .registerTypeAdapter(GlobalVariableTypeEnum .class, new GlobalVariableTypeEnumJsonSerializer())
            .registerTypeAdapter(IAssignmentBlock.class, new AssignmentBlockDeserializer())
            .registerTypeAdapter(LocalVariablesInitialization.class, new LocalVariablesInitializationDeserializer())
            .registerTypeAdapter(GlobalVariableType.class, new PolymorphDeserializer<GlobalVariableType>())
            .create();

    public Project(EnvModel envModel) {
        Environment = new Environment(envModel);
        Skills = new ArrayList<>();
    }

    public Project(String projectName) throws IOException {
        String projectPath = Constants.PROJECTS_FOLDER_PATH + "/" + projectName;
        File[] projectFiles = new File(projectPath).listFiles();
        Skills = new ArrayList<>();
        HashMap<String, Pair<SD, AM>> skills = new HashMap<>();

        assert projectFiles != null;
        for (File file : projectFiles){
            String fileName = file.getName();
            if (fileName.contains("environment")){
                loadEnvFile(file);
            }
            else if (fileName.contains("glue")){
                AM am = loadAMFile(file);
                String skillName = am.getPlpMain().getName();
                if (skills.containsKey(skillName)){
                    Pair<SD, AM> pair = skills.get(skillName);
                    pair.second = am;
                }
                else {
                    Pair<SD, AM> pair = new Pair<>();
                    pair.second = am;
                    skills.put(skillName, pair);
                }
            }
            else if (fileName.contains(projectName)){ // TODO: better recognition of sd file
                SD sd = loadSDFile(file);
                String skillName = sd.getPlpMain().getName();
                if (skills.containsKey(skillName)){
                    Pair<SD, AM> pair = skills.get(skillName);
                    pair.first = sd;
                }
                else {
                    Pair<SD, AM> pair = new Pair<>();
                    pair.first = sd;
                    skills.put(skillName, pair);
                }
            }
        }

        for (Pair<SD, AM> pair : skills.values()){
            Skills.add(new Skill(pair.first, pair.second));
        }

    }


    private SD loadSDFile(File file) throws IOException {
        String content = new String(Files.readAllBytes(file.toPath()));
        return gson.fromJson(content, SD.class);
    }

    private AM loadAMFile(File file) throws IOException {
        String content = new String(Files.readAllBytes(file.toPath()));
        return gson.fromJson(content, AM.class);
    }

    private void loadEnvFile(File file) throws IOException {
        String content = new String(Files.readAllBytes(file.toPath()));
        Environment = gson.fromJson(content, Environment.class);
    }

    public Environment getEnvironment() {
        return Environment;
    }

    public List<Skill> getSkills() {
        return Skills;
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
        String jsonSD = gson.toJson(skill.getSd());
        StringBuilder sdSavePath = new StringBuilder(Constants.PROJECTS_FOLDER_PATH);
        sdSavePath.append("/").append(getProjectName()).append("/").append(getProjectName()).append(".").append(skill.getSkillName()).append(".json");

        String jsonAM = gson.toJson(skill.getAm());
        StringBuilder amSavePath = new StringBuilder(Constants.PROJECTS_FOLDER_PATH);
        sdSavePath.append("/").append(getProjectName()).append("/").append(getProjectName()).append(".").append(skill.getSkillName()).append(" glue.json");

        writeToFile(sdSavePath.toString(), jsonSD);
        writeToFile(sdSavePath.toString(), jsonAM);

    }

    public void saveEnv() throws IOException {
        String jsonEnv = gson.toJson(Environment);
        StringBuilder envSavePath = new StringBuilder(Constants.PROJECTS_FOLDER_PATH);
        envSavePath.append("/").append(getProjectName()).append("/").append(getProjectName()).append(Constants.ENVIRONMENT_FILE_SUFFIX);
        writeToFile(envSavePath.toString(), jsonEnv);
    }

    public Skill getSkill(String skillName) throws Exception {
        for (Skill skill : Skills){
            if (skill.getSkillName().equals(skillName)){
                return skill;
            }
        }
        throw new Exception("Skill with name " + skillName + " has not found.");
    }
}
