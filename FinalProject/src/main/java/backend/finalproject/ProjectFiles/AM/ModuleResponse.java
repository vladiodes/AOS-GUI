package backend.finalproject.ProjectFiles.AM;

import frontend.finalproject.Model.AM.ModuleResponseModel;
import frontend.finalproject.Model.AM.ResponseRule;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// doc: https://github.com/orhaimwerthaim/AOS-WebAPI/blob/master/docs/version2/AOS_documentation_manual.md#moduleresponse
public class ModuleResponse {
    List<ResponseRule> ResponseRules = null;

    private String FromStringLocalVariable;

    public ModuleResponse(List<ResponseRule> responseRules) {
        ResponseRules = responseRules;
    }

    public ModuleResponse(String fromStringLocalVariable) {
        ResponseRules = new ArrayList<>();
        FromStringLocalVariable = fromStringLocalVariable;
    }

    public ModuleResponse() {
        ResponseRules = new ArrayList<>();
    }

    public ModuleResponse(ModuleResponseModel moduleResponse) {
        FromStringLocalVariable = moduleResponse.getFromStringLocalVariable();
        if (moduleResponse.getResponseRules() != null)
        {
            ResponseRules = moduleResponse.getResponseRules().stream().map(ResponseRule::new).collect(Collectors.toList());
        }
    }

    public String getFromStringLocalVariable() {
        return FromStringLocalVariable;
    }

    public void setFromStringLocalVariable(String fromStringLocalVariable) {
        FromStringLocalVariable = fromStringLocalVariable;
    }

    public List<ResponseRule> getResponseRules() {
        return ResponseRules;
    }

    public class ResponseRule {
        private String Response;
        private String ConditionCodeWithLocalVariables;

        public ResponseRule(String response, String conditionCodeWithLocalVariables) {
            Response = response;
            ConditionCodeWithLocalVariables = conditionCodeWithLocalVariables;
        }

        public ResponseRule(frontend.finalproject.Model.AM.ResponseRule i) {
            Response = i.getResponse();
            ConditionCodeWithLocalVariables = i.getConditionCodeWithLocalVariables();
        }

        public String getResponse() {
            return Response;
        }

        public String getConditionCodeWithLocalVariables() {
            return ConditionCodeWithLocalVariables;
        }

        public void setResponse(String response) {
            Response = response;
        }

        public void setConditionCodeWithLocalVariables(String conditionCodeWithLocalVariables) {
            ConditionCodeWithLocalVariables = conditionCodeWithLocalVariables;
        }
    }
}
