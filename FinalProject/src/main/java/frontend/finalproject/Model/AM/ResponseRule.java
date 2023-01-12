package frontend.finalproject.Model.AM;

public class ResponseRule {
    private String Response;
    private String ConditionCodeWithLocalVariables;

    public ResponseRule(String Response, String ConditionCodeWithLocalVariables) {
        this.Response = Response;
        this.ConditionCodeWithLocalVariables = ConditionCodeWithLocalVariables;
    }

    public String getResponse() {
        return Response;
    }

    public String getConditionCodeWithLocalVariables() {
        return ConditionCodeWithLocalVariables;
    }
}
