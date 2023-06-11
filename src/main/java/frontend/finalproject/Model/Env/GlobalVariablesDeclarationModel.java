package frontend.finalproject.Model.Env;

import backend.finalproject.ProjectFiles.Env.GlobalVariablesDeclaration;
import frontend.finalproject.Model.Model;

public class GlobalVariablesDeclarationModel implements Model {
    private String Name;
    private String Type;
    private String DefaultCode;
    private boolean IsActionParameterValue = false;

    private boolean IsArray = false;

    public GlobalVariablesDeclarationModel(String Name, String Type, String DefaultCode, boolean IsActionParamValue){
        this.Name = Name;
        this.Type = Type;
        this.DefaultCode = DefaultCode;
        this.IsActionParameterValue = IsActionParamValue;
    }

    public GlobalVariablesDeclarationModel(String Name, String Type, String DefaultCode, boolean IsActionParamValue, boolean IsArray){
        this.Name = Name;
        this.Type = Type;
        this.DefaultCode = DefaultCode;
        this.IsActionParameterValue = IsActionParamValue;
        this.IsArray = IsArray;
    }

    public GlobalVariablesDeclarationModel(GlobalVariablesDeclaration i) {
        this.Name = i.getName();
        this.Type = i.getType();
        this.DefaultCode = i.getDefaultCode();
        this.IsActionParameterValue = i.isActionParameterValue();
        this.IsArray = i.isArray();
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
    public String toString(){

        return String.format("Type: %s\n" +
                "Default code: %s\n" +
                "isArray: %b\n" +
                "isActionParameterValue: %b",getType(),getDefaultCode(),isArray(), isActionParameterValue());
    }

    public void setDefaultCode(String defaultCode) {
        DefaultCode = defaultCode;
    }

    public void setActionParameterValue(boolean actionParameterValue) {
        IsActionParameterValue = actionParameterValue;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setType(String type) {
        Type = type;
    }

    public void setIsArray(boolean isArray) {
        IsArray = isArray;
    }
}
