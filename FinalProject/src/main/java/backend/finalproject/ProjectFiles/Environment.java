package backend.finalproject.ProjectFiles;


import frontend.finalproject.Model.Env.EnvModel;
import frontend.finalproject.Model.Env.GlobalVariableTypeCompoundModel;
import frontend.finalproject.Model.Env.GlobalVariableTypeEnumModel;
import frontend.finalproject.Model.Env.GlobalVariableTypeModel;

import java.util.ArrayList;
import java.util.List;

// doc: https://github.com/orhaimwerthaim/AOS-WebAPI/blob/master/docs/version2/AOS_documentation_manual.md#environment-file
public class Environment {
    PlpMain plpMain;
    EnvironmentGeneral environmentGeneral;
    List<GlobalVariableType> globalVariableTypes;

    List<GlobalVariablesDeclartion> globalVariablesDeclartions;

    List<InitialBeliefStateAssignment> initialBeliefStateAssignments;

    List<SpecialStates> specialStates;

    public Environment(EnvModel envModel) {
        plpMain = new PlpMain(envModel.getPlpMain());
        environmentGeneral = new EnvironmentGeneral(envModel.getEnvironmentGeneral());
        globalVariableTypes = CopyGlobalVariableTypes(envModel.getGlobalVariableTypes());

    }

    private List<GlobalVariableType> CopyGlobalVariableTypes(List<GlobalVariableTypeModel> globalVariableTypesToCopy) {
        globalVariableTypes = new ArrayList<>();
        for (GlobalVariableTypeModel globalVariableType : globalVariableTypesToCopy){
            if (globalVariableType instanceof GlobalVariableTypeEnumModel){
                globalVariableTypes.add(new GlobalVariableTypeEnum((GlobalVariableTypeEnumModel) globalVariableType));
            }
            else if (globalVariableType instanceof GlobalVariableTypeCompoundModel){
                globalVariableTypes.add(new GlobalVariableTypeCompound((GlobalVariableTypeCompoundModel) globalVariableType));
            }
        }
        return globalVariableTypes;
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