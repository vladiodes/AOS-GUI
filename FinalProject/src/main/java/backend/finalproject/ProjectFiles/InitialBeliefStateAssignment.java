package backend.finalproject.ProjectFiles;

import frontend.finalproject.Model.Env.InitialBeliefStateAssignmentModel;

import java.util.Arrays;
import java.util.List;
// doc: https://github.com/orhaimwerthaim/AOS-WebAPI/blob/master/docs/version2/AOS_documentation_manual.md#initialbeliefstateassignments
public class InitialBeliefStateAssignment {
    private String assignmentName;

    // TODO: split by new line if received single line string, and validate with Or that he can handle list of size 1 same as single string
    private List<String> assignmentCode;

    public InitialBeliefStateAssignment(InitialBeliefStateAssignmentModel initialBeliefStateAssignmentModel) {
        this.assignmentName = initialBeliefStateAssignmentModel.getAssignmentName();
        this.assignmentCode = Arrays.stream(initialBeliefStateAssignmentModel.getAssignmentName().split("\n")).toList();
    }
}

