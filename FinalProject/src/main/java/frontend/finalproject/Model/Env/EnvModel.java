package frontend.finalproject.Model.Env;

import backend.finalproject.ProjectFiles.Common.AssignmentBlockMultipleLines;
import backend.finalproject.ProjectFiles.Common.AssignmentBlockSingleLine;
import backend.finalproject.ProjectFiles.Common.IAssignmentBlock;
import backend.finalproject.ProjectFiles.Env.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import frontend.finalproject.Model.Common.AssignmentBlock;

import java.util.ArrayList;

import java.util.List;
import java.util.stream.Collectors;

public class EnvModel {
    public static final String PLP_NAME = "environment";
    public static final String PLP_TYPE = "Environment";
    private PlpMainModel PlpMain;
    private EnvironmentGeneralModel EnvironmentGeneral;
    private List<GlobalVariableTypeModel> GlobalVariableTypes = new ArrayList<>();
    private   List<GlobalVariablesDeclarationModel> GlobalVariablesDeclaration = new ArrayList<>();
    private List<AssignmentBlock> InitialBeliefStateAssignments = new ArrayList<>();
    private List<SpecialStateModel> SpecialStates = new ArrayList<>();
    private List<AssignmentBlock> ExtrinsicChangesDynamicModel = new ArrayList<>();

    public EnvModel(){

    }

    public EnvModel(Environment environment) {
        PlpMain = new PlpMainModel(environment.getPlpMain());
        EnvironmentGeneral = new EnvironmentGeneralModel(environment.getEnvironmentGeneral());
        GlobalVariableTypes = CopyGlobalVariableTypes(environment.getGlobalVariableTypes());
        GlobalVariablesDeclaration = environment.getGlobalVariablesDeclaration().stream()
                .map(GlobalVariablesDeclarationModel::new).collect(Collectors.toList());
        InitialBeliefStateAssignments = CopyAssignmentBlocks(environment.getInitialBeliefStateAssignments());
        SpecialStates = environment.getSpecialStates().stream()
                .map(SpecialStateModel::new).collect(Collectors.toList());
        ExtrinsicChangesDynamicModel = CopyAssignmentBlocks(environment.getExtrinsicChangesDynamicModel());
    }

    private List<AssignmentBlock> CopyAssignmentBlocks(List<IAssignmentBlock> iAssignmentBlocks) {
        ArrayList<AssignmentBlock> assignmentBlocks = new ArrayList<>();
        for (IAssignmentBlock assignmentBlock : iAssignmentBlocks){
            if (assignmentBlock instanceof AssignmentBlockMultipleLines assignmentBlockMultipleLines){
                assignmentBlocks.add(new AssignmentBlock(assignmentBlockMultipleLines.getAssignmentName(), assignmentBlockMultipleLines.getAssignmentCode()));
            }
            else if (assignmentBlock instanceof AssignmentBlockSingleLine assignmentBlockSingleLine){
                assignmentBlocks.add(new AssignmentBlock(assignmentBlockSingleLine.getAssignmentName(), assignmentBlockSingleLine.getAssignmentCode()));
            }
        }
        return assignmentBlocks;
    }

    private List<GlobalVariableTypeModel> CopyGlobalVariableTypes(List<GlobalVariableType> globalVariableTypes) {
        List<GlobalVariableTypeModel> globalVariableTypesModels = new ArrayList<>();
        for (GlobalVariableType globalVariableType : globalVariableTypes){
            if (globalVariableType instanceof GlobalVariableTypeEnum){
                globalVariableTypesModels.add(new GlobalVariableTypeEnumModel((GlobalVariableTypeEnum) globalVariableType));
            }
            else if (globalVariableType instanceof GlobalVariableTypeCompound){
                globalVariableTypesModels.add(new GlobalVariableTypeCompoundModel((GlobalVariableTypeCompound) globalVariableType));
            }
        }
        return globalVariableTypesModels;
    }

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

    public List<AssignmentBlock> getExtrinsicChangesDynamicModel() {
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

    public void addDynamicChange(AssignmentBlock model) {
        ExtrinsicChangesDynamicModel.add(model);
    }

    public String toString() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }

}
