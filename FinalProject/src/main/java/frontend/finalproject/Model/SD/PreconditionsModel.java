package frontend.finalproject.Model.SD;

import frontend.finalproject.Model.Common.AssignmentBlock;

import java.util.ArrayList;
import java.util.List;

public class PreconditionsModel {
    private double ViolatingPreconditionPenalty;
    private final List<AssignmentBlock> GlobalVariablePreconditionAssignments;
    private final List<AssignmentBlock> PlannerAssistancePreconditionsAssignments;

    public PreconditionsModel(){
        GlobalVariablePreconditionAssignments = new ArrayList<>();
        PlannerAssistancePreconditionsAssignments = new ArrayList<>();
    }

    public void addGlobalVariablePreconditionAssignment(AssignmentBlock block){
        GlobalVariablePreconditionAssignments.add(block);
    }

    public void addPlannerAssistancePreconditionsAssignment(AssignmentBlock block){
        PlannerAssistancePreconditionsAssignments.add(block);
    }

    public double getViolatingPreconditionPenalty() {
        return ViolatingPreconditionPenalty;
    }

    public void setViolatingPreconditionPenalty(double violatingPreconditionPenalty) {
        ViolatingPreconditionPenalty = violatingPreconditionPenalty;
    }

    public List<AssignmentBlock> getGlobalVariablePreconditionAssignments() {
        return GlobalVariablePreconditionAssignments;
    }

    public List<AssignmentBlock> getPlannerAssistancePreconditionsAssignments() {
        return PlannerAssistancePreconditionsAssignments;
    }
}
