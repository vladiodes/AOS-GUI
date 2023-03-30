package frontend.finalproject.Model.Common;

import backend.finalproject.ProjectFiles.Common.AssignmentBlockMultipleLines;
import backend.finalproject.ProjectFiles.Common.AssignmentBlockSingleLine;
import backend.finalproject.ProjectFiles.Common.IAssignmentBlock;
import frontend.finalproject.Model.Model;

import java.util.ArrayList;
import java.util.List;

public class AssignmentBlock implements Model {
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

    public static List<AssignmentBlock> CopyAssignmentBlocks(List<IAssignmentBlock> iAssignmentBlocks) {
        ArrayList<AssignmentBlock> assignmentBlocks = new ArrayList<>();
        for (IAssignmentBlock assignmentBlock : iAssignmentBlocks){
            if (assignmentBlock instanceof AssignmentBlockMultipleLines){
                AssignmentBlockMultipleLines assignmentBlockMultipleLines = (AssignmentBlockMultipleLines)  assignmentBlock;
                assignmentBlocks.add(new AssignmentBlock(assignmentBlockMultipleLines.getAssignmentName(), assignmentBlockMultipleLines.getAssignmentCode()));
            }
            else if (assignmentBlock instanceof AssignmentBlockSingleLine){
                AssignmentBlockSingleLine assignmentBlockSingleLine = (AssignmentBlockSingleLine) assignmentBlock;
                assignmentBlocks.add(new AssignmentBlock(assignmentBlockSingleLine.getAssignmentName(), assignmentBlockSingleLine.getAssignmentCode()));
            }
        }
        return assignmentBlocks;
    }

    public void setAssignmentName(String assignmentName) {
        AssignmentName = assignmentName;
    }

    public void setAssignmentCode(List<String> assignmentCode) {
        AssignmentCode = assignmentCode;
    }
}

