package frontend.finalproject.Model.SD;

import backend.finalproject.ProjectFiles.SD.GlobalVariableModuleParameter;

public class GlobalVariableModuleParametersModel {
    private String Name;
    private String Type;

    public GlobalVariableModuleParametersModel(String Name, String Type) {
        this.Name = Name;
        this.Type = Type;
    }

    public GlobalVariableModuleParametersModel(GlobalVariableModuleParameter i) {
        this.Name = i.getName();
        this.Type = i.getType();
    }

    public String getName() {
        return Name;
    }

    public String getType() {
        return Type;
    }
}
