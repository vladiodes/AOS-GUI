package backend.finalproject.ProjectFiles.Env;

import frontend.finalproject.Model.Env.GlobalVariablesDeclarationModel;

// doc: https://github.com/orhaimwerthaim/AOS-WebAPI/blob/master/docs/version2/AOS_documentation_manual.md#globalvariablesdeclaration
public class GlobalVariablesDeclaration {
    private String Name;

    // TODO: validate that is a valid c++ type, or type defined in GlobalVariableTypes
    private String Type;
    private String DefaultCode;
    private boolean IsActionParameterValue;
    public boolean IsArray;

    public GlobalVariablesDeclaration(GlobalVariablesDeclarationModel v) {
        this.Name = v.getName();
        this.Type = v.getType();
        this.DefaultCode = v.getDefaultCode();
        this.IsActionParameterValue = v.isActionParameterValue();
        this.IsArray = v.isArray();
    }

    public String getName() {
        return Name;
    }

    public String getType() {
        return Type;
    }

    public String getDefaultCode() {
        return DefaultCode;
    }

    public boolean isActionParameterValue() {
        return IsActionParameterValue;
    }

    public boolean isArray() {
        return IsArray;
    }
}
