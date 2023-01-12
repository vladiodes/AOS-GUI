package frontend.finalproject.Model.Common;

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

    public AssignmentBlock(String AssignmentName, String AssignmentCode) {
        this.AssignmentName = AssignmentName;
        this.AssignmentCode = new ArrayList<>();
        String[] code = AssignmentCode.split("\n");
        this.AssignmentCode.addAll(List.of(code));
    }

    public AssignmentBlock(List<String> assignmentCode) {
        this.AssignmentCode = new ArrayList<>();
        this.AssignmentCode.addAll(AssignmentCode);
    }

    public AssignmentBlock(String assignmentCode) {
        this.AssignmentCode = new ArrayList<>();
        String[] code = assignmentCode.split("\n");
        this.AssignmentCode.addAll(List.of(code));
    }

    public String getAssignmentName() {
        return AssignmentName;
    }

    public List<String> getAssignmentCode() {
        return AssignmentCode;
    }
}

