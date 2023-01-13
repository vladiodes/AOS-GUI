package backend.finalproject.ProjectFiles.Env;


import backend.finalproject.ProjectFiles.Common.AssignmentBlockMultipleLines;
import backend.finalproject.ProjectFiles.Common.IAssignmentBlock;
import backend.finalproject.ProjectFiles.Common.PlpMain;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import frontend.finalproject.Model.Common.AssignmentBlock;
import frontend.finalproject.Model.Env.*;
import utils.Json.CustomSerializers.GlobalVariableTypeCompoundJsonSerializer;
import utils.Json.CustomSerializers.GlobalVariableTypeEnumJsonSerializer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// doc: https://github.com/orhaimwerthaim/AOS-WebAPI/blob/master/docs/version2/AOS_documentation_manual.md#environment-file
public class Environment {
    private backend.finalproject.ProjectFiles.Common.PlpMain PlpMain;
    private EnvironmentGeneral EnvironmentGeneral;
    private List<GlobalVariableType> GlobalVariableTypes;
    private List<GlobalVariablesDeclaration> GlobalVariablesDeclaration;

    private List<IAssignmentBlock> InitialBeliefStateAssignments;

    private List<SpecialState> SpecialStates;

    private List<IAssignmentBlock> ExtrinsicChangesDynamicModel;


    public Environment(EnvModel envModel) {
        PlpMain = new PlpMain(envModel.getPlpMain());
        EnvironmentGeneral = new EnvironmentGeneral(envModel.getEnvironmentGeneral());
        GlobalVariableTypes = CopyGlobalVariableTypes(envModel.getGlobalVariableTypes());
        GlobalVariablesDeclaration = envModel.getGlobalVariablesDeclaration().stream()
                .map(GlobalVariablesDeclaration::new).collect(Collectors.toList());
        InitialBeliefStateAssignments = envModel.getInitialBeliefStateAssignments().stream()
                .map(AssignmentBlockMultipleLines::new).collect(Collectors.toList());
        SpecialStates = envModel.getSpecialStates().stream()
                .map(SpecialState::new).collect(Collectors.toList());
        ExtrinsicChangesDynamicModel = envModel.getExtrinsicChangesDynamicModel().stream()
                .map(AssignmentBlockMultipleLines::new).collect(Collectors.toList());
    }

    private List<IAssignmentBlock> CopyAssignmentBlocks(List<AssignmentBlock> initialBeliefStateAssignments) {
        InitialBeliefStateAssignments = new ArrayList<>();
        return InitialBeliefStateAssignments;
    }


    private List<GlobalVariableType> CopyGlobalVariableTypes(List<GlobalVariableTypeModel> globalVariableTypesToCopy) {
        GlobalVariableTypes = new ArrayList<>();
        for (GlobalVariableTypeModel globalVariableType : globalVariableTypesToCopy){
            if (globalVariableType instanceof GlobalVariableTypeEnumModel){
                GlobalVariableTypes.add(new GlobalVariableTypeEnum((GlobalVariableTypeEnumModel) globalVariableType));
            }
            else if (globalVariableType instanceof GlobalVariableTypeCompoundModel){
                GlobalVariableTypes.add(new GlobalVariableTypeCompound((GlobalVariableTypeCompoundModel) globalVariableType));
            }
        }
        return GlobalVariableTypes;
    }

    public String getProjectName() {
        return PlpMain.getProject();
    }

    public PlpMain getPlpMain() {
        return PlpMain;
    }

    public EnvironmentGeneral getEnvironmentGeneral() {
        return EnvironmentGeneral;
    }

    public List<GlobalVariableType> getGlobalVariableTypes() {
        return GlobalVariableTypes;
    }

    public List<GlobalVariablesDeclaration> getGlobalVariablesDeclaration() {
        return GlobalVariablesDeclaration;
    }

    public List<IAssignmentBlock> getInitialBeliefStateAssignments() {
        return InitialBeliefStateAssignments;
    }

    public List<SpecialState> getSpecialStates() {
        return SpecialStates;
    }

    public List<IAssignmentBlock> getExtrinsicChangesDynamicModel() {
        return ExtrinsicChangesDynamicModel;
    }

    public String toJson() {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .disableHtmlEscaping()
                .registerTypeAdapter(GlobalVariableTypeCompound.class, new GlobalVariableTypeCompoundJsonSerializer())
                .registerTypeAdapter(GlobalVariableTypeEnum.class, new GlobalVariableTypeEnumJsonSerializer())
                .create();

        return gson.toJson(this);
    }
}
