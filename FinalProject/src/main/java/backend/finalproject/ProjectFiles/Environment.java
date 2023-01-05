package backend.finalproject.ProjectFiles;


import java.util.List;

// doc: https://github.com/orhaimwerthaim/AOS-WebAPI/blob/master/docs/version2/AOS_documentation_manual.md#environment-file
public class Environment {
    PlpMain plpMain;
    EnvironmentGeneral environmentGeneral;
    List<GlobalVariableType> globalVariableTypes;

    List<GlobalVariablesDeclartion> globalVariablesDeclartions;

    List<InitialBeliefStateAssignment> initialBeliefStateAssignments;

    List<SpecialStates> specialStates;
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