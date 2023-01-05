package backend.finalproject.ProjectFiles.Env;

import frontend.finalproject.Model.Env.InitialBeliefStateAssignmentModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

// doc: https://github.com/orhaimwerthaim/AOS-WebAPI/blob/master/docs/version2/AOS_documentation_manual.md#initialbeliefstateassignments
public class InitialBeliefStateAssignment {
    private String AssignmentName;

    // TODO: split by new line if received single line string, and validate with Or that he can handle list of size 1 same as single string
    private List<String> AssignmentCode;

    //TODO: validate split is working
    public InitialBeliefStateAssignment(InitialBeliefStateAssignmentModel initialBeliefStateAssignmentModel) {
        this.AssignmentName = initialBeliefStateAssignmentModel.getAssignmentName();
        this.AssignmentCode = new ArrayList<>();
        this.AssignmentCode.addAll(initialBeliefStateAssignmentModel.getAssignmentCode());
    }
}

