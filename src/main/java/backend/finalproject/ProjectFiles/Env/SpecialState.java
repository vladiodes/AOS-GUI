package backend.finalproject.ProjectFiles.Env;

import backend.finalproject.ProjectFiles.Common.AssignmentBlockMultipleLines;
import backend.finalproject.ProjectFiles.Common.IAssignmentBlock;
import frontend.finalproject.Model.Env.SpecialStateModel;

import java.util.List;
import java.util.stream.Collectors;

// doc: https://github.com/orhaimwerthaim/AOS-WebAPI/blob/master/docs/version2/AOS_documentation_manual.md#specialstates
public class SpecialState {
    private String StateConditionCode = null;
    private Double Reward = null;
    private Boolean IsGoalState = null;
    private Boolean IsOneTimeReward = null;

    private List<IAssignmentBlock> StateFunctionCode = null;

    public SpecialState(SpecialStateModel specialStateModel) {
        if (specialStateModel.getStateFunctionCode() != null){
            StateFunctionCode = specialStateModel.getStateFunctionCode().stream()
                    .map(AssignmentBlockMultipleLines::new).collect(Collectors.toList());
        }
        else {
            StateConditionCode = specialStateModel.getStateConditionCode();
            Reward = specialStateModel.getReward();
            IsGoalState = specialStateModel.isGoalState();
            IsOneTimeReward = specialStateModel.isOneTimeReward();
            StateFunctionCode = null;
        }
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

    public List<IAssignmentBlock> getStateFunctionCode() {
        return StateFunctionCode;
    }
}
