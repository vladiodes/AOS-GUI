package frontend.finalproject.Model.SD;

import frontend.finalproject.Model.Common.AssignmentBlock;

import java.util.ArrayList;
import java.util.List;

public class DynamicModelModel {
    List<AssignmentBlock> NextStateAssignments;

    public DynamicModelModel() {
        NextStateAssignments = new ArrayList<>();
    }

    public void addAssignment(AssignmentBlock block) {
        NextStateAssignments.add(block);
    }

    public List<AssignmentBlock> getNextStateAssignments() {
        return NextStateAssignments;
    }
}
