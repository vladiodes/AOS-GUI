package frontend.finalproject.Model.Common;

public class AssignmentBlock {
    private String AssignmentName;
    private String AssignmentCode;

    public AssignmentBlock(String AssignmentName, String AssignmentCode) {
        this.AssignmentName = AssignmentName;
        this.AssignmentCode = AssignmentCode;
    }

    public String getAssignmentName() {
        return AssignmentName;
    }

    public String getAssignmentCode() {
        return AssignmentCode;
    }
}