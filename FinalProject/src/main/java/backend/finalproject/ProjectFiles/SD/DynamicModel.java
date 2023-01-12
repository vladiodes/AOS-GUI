package backend.finalproject.ProjectFiles.SD;

import backend.finalproject.ProjectFiles.Common.AssignmentBlock;
import frontend.finalproject.Model.SD.DynamicModelModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// doc: https://github.com/orhaimwerthaim/AOS-WebAPI/blob/master/docs/version2/AOS_documentation_manual.md#dynamicmodel
public class DynamicModel {
    List<AssignmentBlock> NextStateAssignments;

    public DynamicModel(List<AssignmentBlock> nextStateAssignments) {
        NextStateAssignments = nextStateAssignments;
    }

    public DynamicModel(){
        NextStateAssignments = new ArrayList<>();
    }

    public DynamicModel(DynamicModelModel dynamicModel) {
        NextStateAssignments = dynamicModel.getNextStateAssignments().stream().map(AssignmentBlock::new).collect(Collectors.toList());
    }

    public List<AssignmentBlock> getNextStateAssignments() {
        return NextStateAssignments;
    }
}
