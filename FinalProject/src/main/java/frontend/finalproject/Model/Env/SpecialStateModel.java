package frontend.finalproject.Model.Env;

public class SpecialStateModel {
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
}
