package backend.finalproject.ProjectFiles.AM.LocalVariablesInit;

import frontend.finalproject.Model.AM.SDParametersModel;

public class SDParameters extends LocalVariablesInitialization {
    private String InputLocalVariable;
    private String FromGlobalVariable;

    public SDParameters(String inputLocalVariable, String fromGlobalVariable) {
        InputLocalVariable = inputLocalVariable;
        FromGlobalVariable = fromGlobalVariable;
    }

    public SDParameters(SDParametersModel initializationModel) {
        InputLocalVariable = initializationModel.getInputLocalVariable();
        FromGlobalVariable = initializationModel.getFromGlobalVariable();
    }

    public String getInputLocalVariable() {
        return InputLocalVariable;
    }

    public String getFromGlobalVariable() {
        return FromGlobalVariable;
    }
}
