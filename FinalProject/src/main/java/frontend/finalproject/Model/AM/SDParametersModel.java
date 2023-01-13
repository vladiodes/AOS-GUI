package frontend.finalproject.Model.AM;

import backend.finalproject.ProjectFiles.AM.LocalVariablesInit.SDParameters;

public class SDParametersModel extends LocalVariablesInitializationModel {
    private String InputLocalVariable;
    private String FromGlobalVariable;

    public SDParametersModel(String InputLocalVariable, String FromGlobalVariable) {
        this.InputLocalVariable = InputLocalVariable;
        this.FromGlobalVariable = FromGlobalVariable;
    }

    public SDParametersModel(SDParameters sdParameters) {
        this.InputLocalVariable = sdParameters.getInputLocalVariable();
        this.FromGlobalVariable = sdParameters.getFromGlobalVariable();
    }

    public String getInputLocalVariable() {
        return InputLocalVariable;
    }

    public String getFromGlobalVariable() {
        return FromGlobalVariable;
    }
}
