package frontend.finalproject.Model.Env;

import backend.finalproject.ProjectFiles.Env.GlobalVariablesDeclaration;

public class GlobalVariablesDeclarationModel {
    private String Name;
    private String Type;
    private String DefaultCode;
    private boolean IsActionParameterValue = false;

    public GlobalVariablesDeclarationModel(String Name, String Type, String DefaultCode, boolean IsActionParamValue){
        this.Name = Name;
        this.Type = Type;
        this.DefaultCode = DefaultCode;
        this.IsActionParameterValue = IsActionParamValue;
    }

    public GlobalVariablesDeclarationModel(GlobalVariablesDeclaration i) {
        this.Name = i.getName();
        this.Type = i.getType();
        this.DefaultCode = i.getDefaultCode();
        this.IsActionParameterValue = i.isActionParameterValue();
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
}
