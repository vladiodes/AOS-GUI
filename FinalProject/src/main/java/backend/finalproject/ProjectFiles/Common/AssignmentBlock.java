package backend.finalproject.ProjectFiles.Common;

import frontend.finalproject.Model.Env.ExtrinsicChangesDynamicModel;

import java.util.ArrayList;
import java.util.List;

public class AssignmentBlock {
    private String AssignmentName;
    private List<String> AssignmentCode;

    public AssignmentBlock(String AssignmentName, List<String> AssignmentCode) {
        this.AssignmentName = AssignmentName;
        this.AssignmentCode = new ArrayList<>();
        this.AssignmentCode.addAll(AssignmentCode);
    }

    public AssignmentBlock(frontend.finalproject.Model.Common.AssignmentBlock assignmentBlock){
        this.AssignmentName = assignmentBlock.getAssignmentName();
        this.AssignmentCode = new ArrayList<>();
        this.AssignmentCode.addAll(assignmentBlock.getAssignmentCode());
    }

    public AssignmentBlock(List<String> assignmentCode) {
        this.AssignmentCode = new ArrayList<>();
        this.AssignmentCode.addAll(assignmentCode);
    }

    public AssignmentBlock(ExtrinsicChangesDynamicModel extrinsicChangesDynamicModel) {
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
