package backend.finalproject.ProjectFiles.SD;

import backend.finalproject.ProjectFiles.Common.AssignmentBlockMultipleLines;
import backend.finalproject.ProjectFiles.Common.IAssignmentBlock;
import frontend.finalproject.Model.SD.PreconditionsModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// doc: https://github.com/orhaimwerthaim/AOS-WebAPI/blob/master/docs/version2/AOS_documentation_manual.md#assisting-the-planner
public class Precondition {
    private List<IAssignmentBlock> GlobalVariablePreconditionAssignments;
    private List<IAssignmentBlock> PlannerAssistancePreconditionsAssignments;
    private double ViolatingPreconditionPenalty;

    public Precondition(List<IAssignmentBlock> globalVariablePreconditionAssignments, List<IAssignmentBlock> plannerAssistancePreconditionsAssignments, double violatingPreconditionPenalty) {
        GlobalVariablePreconditionAssignments = globalVariablePreconditionAssignments;
        PlannerAssistancePreconditionsAssignments = plannerAssistancePreconditionsAssignments;
        ViolatingPreconditionPenalty = violatingPreconditionPenalty;
    }

    public Precondition() {
        GlobalVariablePreconditionAssignments = new ArrayList<>();
        PlannerAssistancePreconditionsAssignments = new ArrayList<>();
    }

    // TODO: change to generic IAssignmentBlock
    public Precondition(PreconditionsModel preconditions) {
        GlobalVariablePreconditionAssignments = preconditions.getGlobalVariablePreconditionAssignments().stream()
                .map(AssignmentBlockMultipleLines::new).collect(Collectors.toList());
        PlannerAssistancePreconditionsAssignments = preconditions.getPlannerAssistancePreconditionsAssignments().stream()
                .map(AssignmentBlockMultipleLines::new).collect(Collectors.toList());
        ViolatingPreconditionPenalty = preconditions.getViolatingPreconditionPenalty();
    }

    public List<IAssignmentBlock> getGlobalVariablePreconditionAssignments() {
        return GlobalVariablePreconditionAssignments;
    }

    public List<IAssignmentBlock> getPlannerAssistancePreconditionsAssignments() {
        return PlannerAssistancePreconditionsAssignments;
    }

    public double getViolatingPreconditionPenalty() {
        return ViolatingPreconditionPenalty;
    }
}
