package backend.finalproject.ProjectFiles;

import frontend.finalproject.Model.Env.SpecialStateModel;

// doc: https://github.com/orhaimwerthaim/AOS-WebAPI/blob/master/docs/version2/AOS_documentation_manual.md#specialstates
public class SpecialState {
    private String stateConditionCode;
    private double reward;
    private boolean isGoalState = false;
    private boolean isOneTimeReward = false;

    public SpecialState(SpecialStateModel specialStateModel) {
        stateConditionCode = specialStateModel.getStateConditionCode();
        reward = specialStateModel.getReward();
        isGoalState = specialStateModel.isGoalState();
        isOneTimeReward = specialStateModel.isOneTimeReward();
    }
}
