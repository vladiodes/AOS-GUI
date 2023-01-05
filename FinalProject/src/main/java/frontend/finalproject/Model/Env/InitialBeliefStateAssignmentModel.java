package frontend.finalproject.Model.Env;

import java.util.ArrayList;
import java.util.List;

public class InitialBeliefStateAssignmentModel {
    private String AssignmentName;
    private List<String> AssignmentCode;

    public InitialBeliefStateAssignmentModel(String AssignmentName, List<String> AssignmentCode){
        this.AssignmentName = AssignmentName;
        this.AssignmentCode = new ArrayList<>();
        this.AssignmentCode.addAll(AssignmentCode);
    }

    public String getAssignmentName() {
        return AssignmentName;
    }

    public List<String> getAssignmentCode() {
        return AssignmentCode;
    }
}
