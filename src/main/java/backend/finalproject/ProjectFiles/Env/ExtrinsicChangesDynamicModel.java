package backend.finalproject.ProjectFiles.Env;

import java.util.ArrayList;
import java.util.List;

public class ExtrinsicChangesDynamicModel {
    // TODO: validate assignments only to state_ ?
    List<String> AssignmentCode;

    public ExtrinsicChangesDynamicModel(frontend.finalproject.Model.Env.ExtrinsicChangesDynamicModel i) {
        this.AssignmentCode = new ArrayList<>();
        this.AssignmentCode.addAll(i.getAssignmentCode());
    }
}
