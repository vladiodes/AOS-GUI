package frontend.finalproject.Model.Env;

public class GlobalVariablesDeclarationModel {
    private String Name;
    private String Type;
    private String DefaultCode;
    private boolean IsActionParameterValue;

    public GlobalVariablesDeclarationModel(String Name, String Type, String DefaultCode, boolean IsActionParamValue){
        this.Name = Name;
        this.Type = Type;
        this.DefaultCode = DefaultCode;
        this.IsActionParameterValue = IsActionParamValue;
    }
}
