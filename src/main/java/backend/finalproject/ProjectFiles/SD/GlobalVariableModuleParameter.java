package backend.finalproject.ProjectFiles.SD;

import frontend.finalproject.Model.SD.GlobalVariableModuleParametersModel;

// doc: https://github.com/orhaimwerthaim/AOS-WebAPI/blob/master/docs/version2/AOS_documentation_manual.md#globalvariablemoduleparameters
public class GlobalVariableModuleParameter {
    private String Name;
    private String Type;

    public GlobalVariableModuleParameter(String name, String type) {
        Name = name;
        Type = type;
    }

    public GlobalVariableModuleParameter(GlobalVariableModuleParametersModel globalVariableModuleParametersModel){
        this.Name = globalVariableModuleParametersModel.getName();
        this.Type = globalVariableModuleParametersModel.getType();
    }

    public String getName() {
        return Name;
    }

    public String getType() {
        return Type;
    }
}
