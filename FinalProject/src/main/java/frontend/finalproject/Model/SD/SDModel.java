package frontend.finalproject.Model.SD;

import backend.finalproject.ProjectFiles.SD.SD;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import frontend.finalproject.Model.Common.AssignmentBlock;
import frontend.finalproject.Model.Env.GlobalVariablesDeclarationModel;
import frontend.finalproject.Model.Env.PlpMainModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SDModel {
    public static final String PLP_NAME = "PLP";
    private PlpMainModel PlpMain;
    private List<GlobalVariableModuleParametersModel> GlobalVariableModuleParameters;
    private PreconditionsModel Preconditions;
    private DynamicModelModel DynamicModel;

    public SDModel(SD sd) {
        PlpMain = new PlpMainModel(sd.getPlpMain());
        GlobalVariableModuleParameters = sd.getGlobalVariableModuleParameters().stream()
                .map(GlobalVariableModuleParametersModel::new).collect(Collectors.toList());
        Preconditions = new PreconditionsModel(sd.getPreconditions());
        DynamicModel = new DynamicModelModel(sd.getDynamicModel());
    }

    public PlpMainModel getPlpMain() {
        return PlpMain;
    }

    public List<GlobalVariableModuleParametersModel> getGlobalVariableModuleParameters() {
        return GlobalVariableModuleParameters;
    }

    public PreconditionsModel getPreconditions() {
        return Preconditions;
    }

    public DynamicModelModel getDynamicModel() {
        return DynamicModel;
    }

    public SDModel(){
        Preconditions = new PreconditionsModel();
        DynamicModel = new DynamicModelModel();
        GlobalVariableModuleParameters = new ArrayList<>();
    }

    public void addDynamicModelAssignment(AssignmentBlock block){
        DynamicModel.addAssignment(block);
    }

    public void addGlobalVariableModuleParameter(String Name, String Type){
        GlobalVariableModuleParameters.add(new GlobalVariableModuleParametersModel(Name,Type));
    }

    public void addNextStateAssignment(AssignmentBlock block){
        DynamicModel.addAssignment(block);
    }

    public void addGlobalVariablePreconditionAssignment(AssignmentBlock block){
        Preconditions.addGlobalVariablePreconditionAssignment(block);
    }

    public void addPlannerAssistancePreconditionsAssignment(AssignmentBlock block){
        Preconditions.addPlannerAssistancePreconditionsAssignment(block);
    }

    public void setViolatingPreconditionPenalty(double value){
        Preconditions.setViolatingPreconditionPenalty(value);
    }


    public void buildPlpMain(String projectName, String skillName){
        PlpMain = new PlpMainModel(projectName,skillName,PLP_NAME);
    }

    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
}
