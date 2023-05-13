package backend.finalproject.ProjectFiles.Env;

import backend.finalproject.ProjectFiles.Common.AssignmentBlockMultipleLines;
import frontend.finalproject.Model.Env.SpecialStateModel;

import java.util.ArrayList;
import java.util.List;

// doc: https://github.com/orhaimwerthaim/AOS-WebAPI/blob/master/docs/version2/AOS_documentation_manual.md#specialstates
public class SpecialState {
    private String StateConditionCode;
    private double Reward;
    private boolean IsGoalState = false;
    private boolean IsOneTimeReward = false;

    private List<AssignmentBlockMultipleLines> StateFunctionCode = new ArrayList<>();

    public SpecialState(SpecialStateModel specialStateModel) {
        StateConditionCode = specialStateModel.getStateConditionCode();
        Reward = specialStateModel.getReward();
        IsGoalState = specialStateModel.isGoalState();
        IsOneTimeReward = specialStateModel.isOneTimeReward();
        StateFunctionCode = new ArrayList<>(); // TODO: add this to FE model class
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

    public List<AssignmentBlockMultipleLines> getStateFunctionCode() {
        return StateFunctionCode;
    }
}
