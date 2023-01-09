package backend.finalproject.ProjectFiles.SD;

import backend.finalproject.ProjectFiles.Common.AssignmentBlock;

import java.util.ArrayList;
import java.util.List;

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

/**
 * "Preconditions": {
 *         "GlobalVariablePreconditionAssignments": [],
 *         "PlannerAssistancePreconditionsAssignments": [],
 *         "ViolatingPreconditionPenalty": 0
 *     },
 */