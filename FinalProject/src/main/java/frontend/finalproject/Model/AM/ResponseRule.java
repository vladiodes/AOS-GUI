package frontend.finalproject.Model.AM;

import backend.finalproject.ProjectFiles.AM.ModuleResponse;

public class ResponseRule {
    private String Response;
    private String ConditionCodeWithLocalVariables;

    public ResponseRule(String Response, String ConditionCodeWithLocalVariables) {
        this.Response = Response;
        this.ConditionCodeWithLocalVariables = ConditionCodeWithLocalVariables;
    }

    public ResponseRule(ModuleResponse.ResponseRule i) {
        this.Response = i.getResponse();
        this.ConditionCodeWithLocalVariables = i.getConditionCodeWithLocalVariables();
    }

    public String getResponse() {
        return Response;
    }

    public String getConditionCodeWithLocalVariables() {
        return ConditionCodeWithLocalVariables;
    }
}
