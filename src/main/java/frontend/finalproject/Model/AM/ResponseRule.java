package frontend.finalproject.Model.AM;

import backend.finalproject.ProjectFiles.AM.ModuleResponse;
import frontend.finalproject.Model.Model;

public class ResponseRule implements Model {
    private String Response;
    private String ConditionCodeWithLocalVariables;

    public ResponseRule(String Response, String ConditionCodeWithLocalVariables) {
        this.Response = Response;
        this.ConditionCodeWithLocalVariables = ConditionCodeWithLocalVariables;
    }

    public void setResponse(String response) {
        Response = response;
    }

    public void setConditionCodeWithLocalVariables(String conditionCodeWithLocalVariables) {
        ConditionCodeWithLocalVariables = conditionCodeWithLocalVariables;
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
