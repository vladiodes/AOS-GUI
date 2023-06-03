package frontend.finalproject.Model.AM;

import backend.finalproject.ProjectFiles.AM.ModuleResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ModuleResponseModel {
    private List<ResponseRule> ResponseRules = null;

    private String FromStringLocalVariable = null;

    public ModuleResponseModel() {
        ResponseRules = new ArrayList<>();
    }

    public ModuleResponseModel(ModuleResponse moduleResponse) {
        FromStringLocalVariable = moduleResponse.getFromStringLocalVariable();
        if (moduleResponse.getResponseRules() != null)
        {
            ResponseRules = moduleResponse.getResponseRules().stream().map(ResponseRule::new).collect(Collectors.toList());
        }
    }

    public void addResponseRule(String Response, String ConditionCodeWithLocalVariables) {
        ResponseRules = ResponseRules == null ? new ArrayList<>() : ResponseRules;
        ResponseRules.add(new ResponseRule(Response, ConditionCodeWithLocalVariables));
    }

    public ModuleResponseModel(String fromStringLocalVariable) {
        ResponseRules = new ArrayList<>();
        FromStringLocalVariable = fromStringLocalVariable;
    }

    public List<ResponseRule> getResponseRules() {
        return ResponseRules;
    }

    public void setResponseRules(List<ResponseRule> lst){
        this.ResponseRules = lst;
    }

    public String getFromStringLocalVariable() {
        return FromStringLocalVariable;
    }

    public void setFromStringLocalVariable(String fromStringLocalVariable) {
        FromStringLocalVariable = fromStringLocalVariable;
    }


}
