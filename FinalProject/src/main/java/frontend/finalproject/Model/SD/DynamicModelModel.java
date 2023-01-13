package frontend.finalproject.Model.SD;

import backend.finalproject.ProjectFiles.SD.DynamicModel;
import frontend.finalproject.Model.Common.AssignmentBlock;

import java.util.ArrayList;
import java.util.List;

public class DynamicModelModel {
    List<AssignmentBlock> NextStateAssignments;

    public DynamicModelModel() {
        NextStateAssignments = new ArrayList<>();
    }

    public DynamicModelModel(DynamicModel dynamicModel) {
        NextStateAssignments = AssignmentBlock.CopyAssignmentBlocks(dynamicModel.getNextStateAssignments());
    }

    public void addAssignment(AssignmentBlock block) {
        NextStateAssignments.add(block);
    }

    public List<AssignmentBlock> getNextStateAssignments() {
        return NextStateAssignments;
    }
}
