package frontend.finalproject.Model.AM;

public class SDParametersModel extends LocalVariablesInitializationModel {
    private String InputLocalVariable;
    private String FromGlobalVariable;

    public SDParametersModel(String InputLocalVariable, String FromGlobalVariable) {
        this.InputLocalVariable = InputLocalVariable;
        this.FromGlobalVariable = FromGlobalVariable;
    }

    public String getInputLocalVariable() {
        return InputLocalVariable;
    }

    public String getFromGlobalVariable() {
        return FromGlobalVariable;
    }
}
