package frontend.finalproject.Model.SD;

public class GlobalVariableModuleParametersModel {
    private String Name;
    private String Type;

    public GlobalVariableModuleParametersModel(String Name, String Type) {
        this.Name = Name;
        this.Type = Type;
    }

    public String getName() {
        return Name;
    }

    public String getType() {
        return Type;
    }
}
