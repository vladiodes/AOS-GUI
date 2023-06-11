package backend.finalproject.ProjectFiles.Common;

import frontend.finalproject.Model.Env.ExtrinsicChangesDynamicModel;

import java.util.ArrayList;
import java.util.List;

public class AssignmentBlockMultipleLines implements IAssignmentBlock {
    private String AssignmentName;
    private List<String> AssignmentCode;

    public AssignmentBlockMultipleLines(String AssignmentName, List<String> AssignmentCode) {
        this.AssignmentName = AssignmentName;
        this.AssignmentCode = new ArrayList<>();
        this.AssignmentCode.addAll(AssignmentCode);
    }

    public AssignmentBlockMultipleLines(frontend.finalproject.Model.Common.AssignmentBlock assignmentBlock){
        this.AssignmentName = assignmentBlock.getAssignmentName();
        this.AssignmentCode = new ArrayList<>();
        this.AssignmentCode.addAll(assignmentBlock.getAssignmentCode());
    }

    public AssignmentBlockMultipleLines(List<String> assignmentCode) {
        this.AssignmentCode = new ArrayList<>();
        this.AssignmentCode.addAll(assignmentCode);
    }

    public AssignmentBlockMultipleLines(ExtrinsicChangesDynamicModel extrinsicChangesDynamicModel) {
        this.AssignmentName = extrinsicChangesDynamicModel.getAssignmentName();
        this.AssignmentCode = new ArrayList<>();
        this.AssignmentCode.addAll(extrinsicChangesDynamicModel.getAssignmentCode());
    }

    public String getAssignmentName() {
        return AssignmentName;
    }

    public List<String> getAssignmentCode() {
        return AssignmentCode;
    }
}
