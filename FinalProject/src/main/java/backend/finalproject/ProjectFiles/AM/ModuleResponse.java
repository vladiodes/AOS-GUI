package backend.finalproject.ProjectFiles.AM;

import frontend.finalproject.Model.AM.ModuleResponseModel;
import frontend.finalproject.Model.AM.ResponseRule;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// doc: https://github.com/orhaimwerthaim/AOS-WebAPI/blob/master/docs/version2/AOS_documentation_manual.md#moduleresponse
public class ModuleResponse {
    List<ResponseRule> ResponseRules;

    public ModuleResponse(List<ResponseRule> responseRules) {
        ResponseRules = responseRules;
    }

    public ModuleResponse() {
        ResponseRules = new ArrayList<>();
    }

    public ModuleResponse(ModuleResponseModel moduleResponse) {
        ResponseRules = moduleResponse.getResponseRules().stream().map(ResponseRule::new).collect(Collectors.toList());
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
    }
}
