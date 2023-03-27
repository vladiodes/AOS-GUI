package frontend.finalproject.Model.Env;

import backend.finalproject.ProjectFiles.Env.SpecialState;
import frontend.finalproject.Model.Model;

public class SpecialStateModel implements Model {
    private String StateConditionCode;
    private double Reward;
    private boolean IsGoalState;
    private boolean IsOneTimeReward;

    public SpecialStateModel(String StateConditionCode, double Reward, boolean IsGoalState, boolean IsOneTimeReward){
        this.StateConditionCode = StateConditionCode;
        this.Reward = Reward;
        this.IsGoalState = IsGoalState;
        this.IsOneTimeReward = IsOneTimeReward;
    }

    public SpecialStateModel(SpecialState i) {
        this.StateConditionCode = i.getStateConditionCode();
        this.Reward = i.getReward();
        this.IsGoalState = i.isGoalState();
        this.IsOneTimeReward = i.isOneTimeReward();
    }

    public String getStateConditionCode() {
        return StateConditionCode;
    }

    public double getReward() {
        return Reward;
    }

    public boolean isGoalState() {
        return IsGoalState;
    }

    public boolean isOneTimeReward() {
        return IsOneTimeReward;
    }

    public String toString(){
        return String.format("State condition code: %s\n" +
                "Reward: %f\n" +
                "IsGoalState: %b\n" +
                "IsOneTimeReward: %b",getStateConditionCode(),getReward(),isGoalState(),isOneTimeReward());
    }

    public void setGoalState(boolean goalState) {
        IsGoalState = goalState;
    }

    public void setOneTimeReward(boolean oneTimeReward) {
        IsOneTimeReward = oneTimeReward;
    }

    public void setReward(double reward) {
        Reward = reward;
    }

    public void setStateConditionCode(String stateConditionCode) {
        StateConditionCode = stateConditionCode;
    }
}
