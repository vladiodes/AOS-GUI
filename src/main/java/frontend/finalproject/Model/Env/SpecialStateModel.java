package frontend.finalproject.Model.Env;

import backend.finalproject.ProjectFiles.Env.SpecialState;
import frontend.finalproject.Model.Common.AssignmentBlock;
import frontend.finalproject.Model.Model;

import java.util.ArrayList;
import java.util.List;

public class SpecialStateModel implements Model {
    private String StateConditionCode;
    private Double Reward;
    private Boolean IsGoalState;
    private Boolean IsOneTimeReward;

    private List<AssignmentBlock> StateFunctionCode;

    public SpecialStateModel(String StateConditionCode, double Reward, boolean IsGoalState, boolean IsOneTimeReward){
        this.StateConditionCode = StateConditionCode;
        this.Reward = Reward;
        this.IsGoalState = IsGoalState;
        this.IsOneTimeReward = IsOneTimeReward;
        StateFunctionCode = null;
    }

    public SpecialStateModel(List<AssignmentBlock> stateFunctionCode){
        StateFunctionCode = new ArrayList<>();
        StateFunctionCode.addAll(stateFunctionCode);
    }

    public SpecialStateModel(SpecialState i) {
        if (i.getStateFunctionCode() != null){
            StateFunctionCode = AssignmentBlock.CopyAssignmentBlocks(i.getStateFunctionCode());
        }
        else {
            this.StateConditionCode = i.getStateConditionCode();
            this.Reward = i.getReward();
            this.IsGoalState = i.isGoalState();
            this.IsOneTimeReward = i.isOneTimeReward();
            StateFunctionCode = null;
        }

    }

    public String getStateConditionCode() {
        return StateConditionCode;
    }

    public double getReward() {
        return Reward == null ? 0 : Reward;
    }

    public boolean isGoalState() {
        return IsGoalState != null && IsGoalState;
    }

    public boolean isOneTimeReward() {
        return IsOneTimeReward != null && IsOneTimeReward;
    }

    public String toString(){
        if (StateFunctionCode != null){
            return String.format("State function code:\n%s", StateFunctionCode);
        }

        return String.format("State condition code: %s\n" +
                "Reward: %f\n" +
                "IsGoalState: %b\n" +
                "IsOneTimeReward: %b",getStateConditionCode(),getReward(),isGoalState(),isOneTimeReward());
    }

    public void setGoalState(Boolean goalState) {
        IsGoalState = goalState != null && goalState;
    }

    public void setOneTimeReward(Boolean oneTimeReward) {
        IsOneTimeReward = oneTimeReward != null && oneTimeReward;
    }

    public void setReward(Double reward) {
        Reward = reward == null ? 0 : reward;
    }

    public void setStateConditionCode(String stateConditionCode) {
        StateConditionCode = stateConditionCode;
    }

    public List<AssignmentBlock> getStateFunctionCode() {
        return StateFunctionCode;
    }
    public void setStateFunctionCode(List<AssignmentBlock> stateFunctionCode) {
        StateFunctionCode = stateFunctionCode;
    }
}
