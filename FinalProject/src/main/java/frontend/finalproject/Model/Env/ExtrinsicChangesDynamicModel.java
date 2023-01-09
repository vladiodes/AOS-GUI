package frontend.finalproject.Model.Env;

import java.util.ArrayList;
import java.util.List;

public class ExtrinsicChangesDynamicModel {

    private String AssignmentName;
    private List<String> AssignmentCode;

    public ExtrinsicChangesDynamicModel(String assignmentName, List<String> assignmentCode) {
        AssignmentName = assignmentName;
        this.AssignmentCode = new ArrayList<>();
        this.AssignmentCode.addAll(assignmentCode);
    }

    public ExtrinsicChangesDynamicModel(List<String> AssignmentCode){
        this.AssignmentCode = new ArrayList<>();
        this.AssignmentCode.addAll(AssignmentCode);
    }

    public ExtrinsicChangesDynamicModel(String AssignmentCode){
        this.AssignmentCode = new ArrayList<>();
        String[] code = AssignmentCode.split("\n");
        this.AssignmentCode.addAll(List.of(code));
    }


    public List<String> getAssignmentCode() {
        return AssignmentCode;
    }

    public String getAssignmentName() {
        return AssignmentName;
    }
}
