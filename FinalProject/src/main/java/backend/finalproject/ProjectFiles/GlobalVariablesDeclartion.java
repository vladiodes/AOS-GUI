package backend.finalproject.ProjectFiles;

import frontend.finalproject.Model.Env.GlobalVariablesDeclarationModel;

// doc: https://github.com/orhaimwerthaim/AOS-WebAPI/blob/master/docs/version2/AOS_documentation_manual.md#globalvariablesdeclaration
public class GlobalVariablesDeclartion {
    private String name;

    // TODO: validate that is a valid c++ type, or type defined in GlobalVariableTypes
    private String type;
    private String defaultCode;
    private boolean isActionParameterValue;

    public GlobalVariablesDeclartion(GlobalVariablesDeclarationModel v) {
        this.name = v.getName();
        this.type = v.getType();
        this.defaultCode = v.getDefaultCode();
        this.isActionParameterValue = v.isActionParameterValue();
    }
}
