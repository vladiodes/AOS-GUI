package frontend.finalproject.Model.AM;

import java.util.ArrayList;
import java.util.List;

public class ModuleResponseModel {
    private List<ResponseRule> ResponseRules;

    public ModuleResponseModel() {
        ResponseRules = new ArrayList<>();
    }

    public void addResponseRule(String Response, String ConditionCodeWithLocalVariables) {
        ResponseRules.add(new ResponseRule(Response, ConditionCodeWithLocalVariables));
    }

    public List<ResponseRule> getResponseRules() {
        return ResponseRules;
    }
}
