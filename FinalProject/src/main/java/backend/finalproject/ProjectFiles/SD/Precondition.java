package backend.finalproject.ProjectFiles.SD;

import backend.finalproject.ProjectFiles.Common.AssignmentBlock;
import frontend.finalproject.Model.SD.PreconditionsModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// doc: https://github.com/orhaimwerthaim/AOS-WebAPI/blob/master/docs/version2/AOS_documentation_manual.md#assisting-the-planner
public class Precondition {
    private List<AssignmentBlock> GlobalVariablePreconditionAssignments;
    private List<AssignmentBlock> PlannerAssistancePreconditionsAssignments;
    private double ViolatingPreconditionPenalty;

    public Precondition(List<AssignmentBlock> globalVariablePreconditionAssignments, List<AssignmentBlock> plannerAssistancePreconditionsAssignments, double violatingPreconditionPenalty) {
        GlobalVariablePreconditionAssignments = globalVariablePreconditionAssignments;
        PlannerAssistancePreconditionsAssignments = plannerAssistancePreconditionsAssignments;
        ViolatingPreconditionPenalty = violatingPreconditionPenalty;
    }

    public Precondition() {
        GlobalVariablePreconditionAssignments = new ArrayList<>();
        PlannerAssistancePreconditionsAssignments = new ArrayList<>();
    }

    public Precondition(PreconditionsModel preconditions) {
        GlobalVariablePreconditionAssignments = preconditions.getGlobalVariablePreconditionAssignments().stream()
                .map(AssignmentBlock::new).collect(Collectors.toList());
        PlannerAssistancePreconditionsAssignments = preconditions.getPlannerAssistancePreconditionsAssignments().stream()
                .map(AssignmentBlock::new).collect(Collectors.toList());
        ViolatingPreconditionPenalty = preconditions.getViolatingPreconditionPenalty();
    }

    public List<AssignmentBlock> getGlobalVariablePreconditionAssignments() {
        return GlobalVariablePreconditionAssignments;
    }

    public List<AssignmentBlock> getPlannerAssistancePreconditionsAssignments() {
        return PlannerAssistancePreconditionsAssignments;
    }

    public double getViolatingPreconditionPenalty() {
        return ViolatingPreconditionPenalty;
    }
}
