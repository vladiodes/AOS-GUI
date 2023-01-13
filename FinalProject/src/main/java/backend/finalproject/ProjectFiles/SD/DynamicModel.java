package backend.finalproject.ProjectFiles.SD;

import backend.finalproject.ProjectFiles.Common.AssignmentBlockMultipleLines;
import backend.finalproject.ProjectFiles.Common.IAssignmentBlock;
import frontend.finalproject.Model.SD.DynamicModelModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// doc: https://github.com/orhaimwerthaim/AOS-WebAPI/blob/master/docs/version2/AOS_documentation_manual.md#dynamicmodel
public class DynamicModel {
    List<IAssignmentBlock> NextStateAssignments;

    public DynamicModel(List<IAssignmentBlock> nextStateAssignments) {
        NextStateAssignments = nextStateAssignments;
    }

    public DynamicModel(){
        NextStateAssignments = new ArrayList<>();
    }

    // TODO: generate single/multiple line assignment block
    public DynamicModel(DynamicModelModel dynamicModel) {
        NextStateAssignments = dynamicModel.getNextStateAssignments().stream().map(AssignmentBlockMultipleLines::new).collect(Collectors.toList());
    }

    public List<IAssignmentBlock> getNextStateAssignments() {
        return NextStateAssignments;
    }
}
