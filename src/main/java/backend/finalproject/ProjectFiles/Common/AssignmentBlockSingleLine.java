package backend.finalproject.ProjectFiles.Common;

import java.util.List;

public class AssignmentBlockSingleLine implements IAssignmentBlock{
    private String AssignmentName;
    private String AssignmentCode;

    public AssignmentBlockSingleLine(String assignmentName, String assignmentCode) {
        AssignmentName = assignmentName;
        AssignmentCode = assignmentCode;
    }

    public String getAssignmentName() {
        return AssignmentName;
    }

    public String getAssignmentCode() {
        return AssignmentCode;
    }
}
