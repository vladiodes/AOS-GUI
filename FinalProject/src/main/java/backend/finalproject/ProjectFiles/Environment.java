package backend.finalproject.ProjectFiles;


import frontend.finalproject.Model.Env.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// doc: https://github.com/orhaimwerthaim/AOS-WebAPI/blob/master/docs/version2/AOS_documentation_manual.md#environment-file
public class Environment {
    private PlpMain PlpMain;
    private EnvironmentGeneral EnvironmentGeneral;
    private List<GlobalVariableType> GlobalVariableTypes;

    private List<GlobalVariablesDeclartion> GlobalVariablesDeclartions;

    private List<InitialBeliefStateAssignment> InitialBeliefStateAssignments;

    private List<SpecialState> SpecialStates;

    private List<ExtrinsicChangesDynamicModel> ExtrinsicChangesDynamicModels;


    public Environment(EnvModel envModel) {
        PlpMain = new PlpMain(envModel.getPlpMain());
        EnvironmentGeneral = new EnvironmentGeneral(envModel.getEnvironmentGeneral());
        GlobalVariableTypes = CopyGlobalVariableTypes(envModel.getGlobalVariableTypes());
        GlobalVariablesDeclartions = envModel.getGlobalVariablesDeclaration().stream()
                .map(GlobalVariablesDeclartion::new).collect(Collectors.toList());
        InitialBeliefStateAssignments = envModel.getInitialBeliefStateAssignments().stream()
                .map(InitialBeliefStateAssignment::new).collect(Collectors.toList());
        SpecialStates = envModel.getSpecialStates().stream()
                .map(SpecialState::new).collect(Collectors.toList());
        ExtrinsicChangesDynamicModels = envModel.getExtrinsicChangesDynamicModel().stream()
                .map(ExtrinsicChangesDynamicModel::new).collect(Collectors.toList());
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

    public List<GlobalVariablesDeclartion> getGlobalVariablesDeclartions() {
        return GlobalVariablesDeclartions;
    }

    public List<InitialBeliefStateAssignment> getInitialBeliefStateAssignments() {
        return InitialBeliefStateAssignments;
    }

    public List<SpecialState> getSpecialStates() {
        return SpecialStates;
    }

    public List<ExtrinsicChangesDynamicModel> getExtrinsicChangesDynamicModels() {
        return ExtrinsicChangesDynamicModels;
    }
}

/**
 {
 "PlpMain": { },
 "EnvironmentGeneral": { },
 "GlobalVariableTypes": [ ],
 "GlobalVariablesDeclaration": [ ],
 "InitialBeliefStateAssignments": [ ],
 "SpecialStates": [ ],
 "ExtrinsicChangesDynamicModel": [ ]
 }
 **/