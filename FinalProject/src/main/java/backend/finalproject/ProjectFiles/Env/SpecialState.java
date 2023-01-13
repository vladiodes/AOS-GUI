package backend.finalproject.ProjectFiles.Env;

import frontend.finalproject.Model.Env.SpecialStateModel;

// doc: https://github.com/orhaimwerthaim/AOS-WebAPI/blob/master/docs/version2/AOS_documentation_manual.md#specialstates
public class SpecialState {
    private String StateConditionCode;
    private double Reward;
    private boolean IsGoalState = false;
    private boolean IsOneTimeReward = false;

    public SpecialState(SpecialStateModel specialStateModel) {
        StateConditionCode = specialStateModel.getStateConditionCode();
        Reward = specialStateModel.getReward();
        IsGoalState = specialStateModel.isGoalState();
        IsOneTimeReward = specialStateModel.isOneTimeReward();
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
}
