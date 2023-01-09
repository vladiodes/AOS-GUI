package backend.finalproject.ProjectFiles.SD;

import backend.finalproject.ProjectFiles.Common.AssignmentBlock;

import java.util.ArrayList;
import java.util.List;

// doc: https://github.com/orhaimwerthaim/AOS-WebAPI/blob/master/docs/version2/AOS_documentation_manual.md#dynamicmodel
public class DynamicModel {
    List<AssignmentBlock> NextStateAssignments;

    public DynamicModel(List<AssignmentBlock> nextStateAssignments) {
        NextStateAssignments = nextStateAssignments;
    }

    public DynamicModel(){
        NextStateAssignments = new ArrayList<>();
    }

    public List<AssignmentBlock> getNextStateAssignments() {
        return NextStateAssignments;
    }
}
