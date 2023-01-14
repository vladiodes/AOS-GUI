package frontend.finalproject.Model.AM;

import backend.finalproject.ProjectFiles.AM.ModuleResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ModuleResponseModel {
    private List<ResponseRule> ResponseRules;

    public ModuleResponseModel() {
        ResponseRules = new ArrayList<>();
    }

    public ModuleResponseModel(ModuleResponse moduleResponse) {
        ResponseRules = moduleResponse.getResponseRules().stream().map(ResponseRule::new).collect(Collectors.toList());
    }

    public void addResponseRule(String Response, String ConditionCodeWithLocalVariables) {
        ResponseRules.add(new ResponseRule(Response, ConditionCodeWithLocalVariables));
    }

    public List<ResponseRule> getResponseRules() {
        return ResponseRules;
    }

    public void setResponseRules(List<ResponseRule> lst){
        this.ResponseRules = lst;
    }
}
