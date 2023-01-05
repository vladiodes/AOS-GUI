package frontend.finalproject.Model.Env;

public class InitialBeliefStateAssignmentModel {
    private String AssignmentName;
    private String AssignmentCode;

    public InitialBeliefStateAssignmentModel(String AssignmentName, String AssignmentCode){
        this.AssignmentName = AssignmentName;
        this.AssignmentCode = AssignmentCode;
    }

    public String getAssignmentName() {
        return AssignmentName;
    }

    public String getAssignmentCode() {
        return AssignmentCode;
    }
}
