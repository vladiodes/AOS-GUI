package frontend.finalproject.Model.AM;

import backend.finalproject.ProjectFiles.AM.AM;
import backend.finalproject.ProjectFiles.AM.LocalVariablesInit.DataPublishedRobotFramework;
import backend.finalproject.ProjectFiles.AM.LocalVariablesInit.LocalVariablesInitialization;
import backend.finalproject.ProjectFiles.AM.LocalVariablesInit.SDParameters;
import backend.finalproject.ProjectFiles.AM.LocalVariablesInit.SkillCodeReturnValue;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import frontend.finalproject.Model.Env.PlpMainModel;
import java.util.ArrayList;
import java.util.List;

public class AMModel {
    public static final String PLP_TYPE = "Glue";
    private PlpMainModel PlpMain;
    private String GlueFramework;
    private ModuleResponseModel ModuleResponse;
    private ModuleActivationModel ModuleActivation;
    private List<LocalVariablesInitializationModel> LocalVariablesInitialization;


    public AMModel(){
        ModuleResponse = new ModuleResponseModel();
        ModuleActivation = new ModuleActivationModel();
        LocalVariablesInitialization = new ArrayList<>();
    }

    public AMModel(AM am) {
        PlpMain = new PlpMainModel(am.getPlpMain());
        GlueFramework = am.getGlueFramework();
        ModuleResponse = new ModuleResponseModel(am.getModuleResponse());
        ModuleActivation = new ModuleActivationModel(am.getModuleActivation());
        LocalVariablesInitialization = CopyLocalVariablesInits(am.getLocalVariablesInitialization());
    }

    private List<LocalVariablesInitializationModel> CopyLocalVariablesInits(List<LocalVariablesInitialization> localVariablesInitialization) {
        List<LocalVariablesInitializationModel> localVariablesInitializationModels = new ArrayList<>();
        for (LocalVariablesInitialization initialization : localVariablesInitialization){
            if (initialization instanceof DataPublishedRobotFramework){
                DataPublishedRobotFramework dataPublishedRobotFramework = (DataPublishedRobotFramework) initialization;
                localVariablesInitializationModels.add(new frontend.finalproject.Model.AM.DataPublishedRobotFramework(dataPublishedRobotFramework));
            }
            else if (initialization instanceof SDParameters){
                SDParameters sdParameters = (SDParameters) initialization;
                localVariablesInitializationModels.add(new SDParametersModel(sdParameters));
            }
            else if (initialization instanceof SkillCodeReturnValue){
                SkillCodeReturnValue skillCodeReturnValue = (SkillCodeReturnValue) initialization;
                localVariablesInitializationModels.add(new SkillCodeReturnValueModel(skillCodeReturnValue));
            }
        }

        return localVariablesInitializationModels;
    }

    public void addLocalVariableInitialization(LocalVariablesInitializationModel model){
        LocalVariablesInitialization.add(model);
    }

    public void addResponseRule(String Response, String ConditionCodeWithLocalVariables){
        ModuleResponse.addResponseRule(Response,ConditionCodeWithLocalVariables);
    }

    public void buildPlpMain(String projectName, String skillName){
        PlpMain = new PlpMainModel(projectName,skillName,PLP_TYPE);
    }

    public String getGlueFramework() {
        return GlueFramework;
    }

    public void setGlueFramework(String glueFramework) {
        GlueFramework = glueFramework;
    }

    public PlpMainModel getPlpMain() {
        return PlpMain;
    }

    public ModuleResponseModel getModuleResponse() {
        return ModuleResponse;
    }

    public ModuleActivationModel getModuleActivation() {
        return ModuleActivation;
    }

    public List<LocalVariablesInitializationModel> getLocalVariablesInitialization() {
        return LocalVariablesInitialization;
    }

    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }

    public void setLocalVariablesInitialization(List<LocalVariablesInitializationModel> localVariablesInitialization) {
        this.LocalVariablesInitialization = localVariablesInitialization;
    }
}

