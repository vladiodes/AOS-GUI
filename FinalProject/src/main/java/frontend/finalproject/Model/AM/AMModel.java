package frontend.finalproject.Model.AM;

import frontend.finalproject.Model.Env.PlpMainModel;
import utils.Response;

import java.util.ArrayList;
import java.util.List;

public class AMModel {
    public static final String PLP_TYPE = "Glue";
    private PlpMainModel PlpMain;
    private String GlueFramework;
    private ModuleResponseModel ModuleResponse;
    private ModuleActivationModel ModuleActivation;


    public AMModel(){
        ModuleResponse = new ModuleResponseModel();
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
}

class ModuleResponseModel{
    private List<ResponseRule> ResponseRules;

    public ModuleResponseModel(){
        ResponseRules = new ArrayList<>();
    }

    public void addResponseRule(String Response, String ConditionCodeWithLocalVariables){
        ResponseRules.add(new ResponseRule(Response,ConditionCodeWithLocalVariables));
    }
}

class ResponseRule{
    private String Response;
    private String ConditionCodeWithLocalVariables;

    public ResponseRule(String Response, String ConditionCodeWithLocalVariables){
        this.Response = Response;
        this.ConditionCodeWithLocalVariables = ConditionCodeWithLocalVariables;
    }
}

/**
 * {
 *     "PlpMain": { },
 *     "GlueFramework": "",
 *     "ModuleResponse": { },
 *     "ModuleActivation": { },
 *     "LocalVariablesInitialization": []
 * }
 */
