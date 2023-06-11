package frontend.finalproject.Model.SD;

import backend.finalproject.ProjectFiles.SD.Precondition;
import frontend.finalproject.Model.Common.AssignmentBlock;

import java.util.ArrayList;
import java.util.List;

public class PreconditionsModel {
    private double ViolatingPreconditionPenalty;
    private List<AssignmentBlock> GlobalVariablePreconditionAssignments;
    private List<AssignmentBlock> PlannerAssistancePreconditionsAssignments;

    public PreconditionsModel(){
        GlobalVariablePreconditionAssignments = new ArrayList<>();
        PlannerAssistancePreconditionsAssignments = new ArrayList<>();
    }

    public PreconditionsModel(Precondition preconditions) {
        this.ViolatingPreconditionPenalty = preconditions.getViolatingPreconditionPenalty();
        this.GlobalVariablePreconditionAssignments = AssignmentBlock.CopyAssignmentBlocks(preconditions.getGlobalVariablePreconditionAssignments());
        this.PlannerAssistancePreconditionsAssignments = AssignmentBlock.CopyAssignmentBlocks(preconditions.getPlannerAssistancePreconditionsAssignments());

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

    public void setGlobalVariablePreconditionAssignments(List<AssignmentBlock> lst){
        this.GlobalVariablePreconditionAssignments = lst;
    }

    public void setPlannerAssistancePreconditionsAssignments(List<AssignmentBlock> lst){
        this.PlannerAssistancePreconditionsAssignments = lst;
    }
}
