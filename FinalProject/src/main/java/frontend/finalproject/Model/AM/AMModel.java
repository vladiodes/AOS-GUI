package frontend.finalproject.Model.AM;

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
}

