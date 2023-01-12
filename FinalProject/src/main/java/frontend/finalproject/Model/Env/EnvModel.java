package frontend.finalproject.Model.Env;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import frontend.finalproject.Model.Common.AssignmentBlock;

import java.util.ArrayList;

import java.util.List;

public class EnvModel {
    public static final String PLP_NAME = "environment";
    public static final String PLP_TYPE = "Environment";
    private PlpMainModel PlpMain;
    private EnvironmentGeneralModel EnvironmentGeneral;
    private List<GlobalVariableTypeModel> GlobalVariableTypes = new ArrayList<>();
    private   List<GlobalVariablesDeclarationModel> GlobalVariablesDeclaration = new ArrayList<>();
    private List<AssignmentBlock> InitialBeliefStateAssignments = new ArrayList<>();
    private List<SpecialStateModel> SpecialStates = new ArrayList<>();
    private List<ExtrinsicChangesDynamicModel> ExtrinsicChangesDynamicModel = new ArrayList<>();

    public void buildPlpMain(PlpMainModel model){
        this.PlpMain = model;
    }

    public void buildEnvGeneral(EnvironmentGeneralModel model){
        this.EnvironmentGeneral = model;
    }

    public PlpMainModel getPlpMain() {
        return PlpMain;
    }

    public EnvironmentGeneralModel getEnvironmentGeneral() {
        return EnvironmentGeneral;
    }

    public List<GlobalVariableTypeModel> getGlobalVariableTypes() {
        return GlobalVariableTypes;
    }

    public List<GlobalVariablesDeclarationModel> getGlobalVariablesDeclaration() {
        return GlobalVariablesDeclaration;
    }

    public List<AssignmentBlock> getInitialBeliefStateAssignments() {
        return InitialBeliefStateAssignments;
    }

    public List<SpecialStateModel> getSpecialStates() {
        return SpecialStates;
    }

    public List<frontend.finalproject.Model.Env.ExtrinsicChangesDynamicModel> getExtrinsicChangesDynamicModel() {
        return ExtrinsicChangesDynamicModel;
    }

    public void addGlobalVarType(GlobalVariableTypeModel model){
        this.GlobalVariableTypes.add(model);
    }

    public void addGlobalVarDec(GlobalVariablesDeclarationModel model){
        this.GlobalVariablesDeclaration.add(model);
    }

    public void addInitBeliefAss(AssignmentBlock initBeliefModel) {
        InitialBeliefStateAssignments.add(initBeliefModel);
    }

    public void addSpecialState(SpecialStateModel model) {
        SpecialStates.add(model);
    }

    public void addDynamicChange(frontend.finalproject.Model.Env.ExtrinsicChangesDynamicModel model) {
        ExtrinsicChangesDynamicModel.add(model);
    }

    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }

}
