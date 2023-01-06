package frontend.finalproject.Model.Env;

import java.util.ArrayList;
import java.util.List;

public class ExtrinsicChangesDynamicModel {
    private List<String> AssignmentCode;

    public ExtrinsicChangesDynamicModel(List<String> AssignmentCode){
        this.AssignmentCode = new ArrayList<>();
        this.AssignmentCode.addAll(AssignmentCode);
    }

    public List<String> getAssignmentCode() {
        return AssignmentCode;
    }
}
