package backend.finalproject.ProjectFiles;

public class ExtrinsicChangesDynamicModel {
    // TODO: validate assignments only to state_ ?
    String AssignmentCode;

    public ExtrinsicChangesDynamicModel(frontend.finalproject.Model.Env.ExtrinsicChangesDynamicModel i) {
        this.AssignmentCode = i.getAssignmentCode();
    }
}
