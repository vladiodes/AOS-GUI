package frontend.finalproject.Model.AM;

import backend.finalproject.ProjectFiles.AM.LocalVariablesInit.SkillCodeReturnValue;
import frontend.finalproject.Model.Common.ImportCodeModel;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SkillCodeReturnValueModel extends LocalVariablesInitializationModel {
    private String LocalVariableName;
    private String VariableType;
    private boolean FromROSServiceResponse;
    private String AssignmentCode; // TODO: is this should be list?
    private List<ImportCodeModel> ImportCode;

    public SkillCodeReturnValueModel() {
        ImportCode = new ArrayList<>();
    }

    public SkillCodeReturnValueModel(SkillCodeReturnValue skillCodeReturnValue) {
        this.LocalVariableName = skillCodeReturnValue.getLocalVariableName();
        this.VariableType = skillCodeReturnValue.getVariableType();
        this.FromROSServiceResponse = skillCodeReturnValue.isFromROSServiceResponse();
        this.AssignmentCode = String.join("\n", skillCodeReturnValue.getAssignmentCode());
        this.ImportCode = skillCodeReturnValue.getImportCode().stream().map(ImportCodeModel::new).collect(Collectors.toList());
    }

    public void addImportCodeModel(ImportCodeModel model) {
        ImportCode.add(model);
    }

    public String getLocalVariableName() {
        return LocalVariableName;
    }

    public void setLocalVariableName(String localVariableName) {
        LocalVariableName = localVariableName;
    }

    public String getVariableType() {
        return VariableType;
    }

    public void setVariableType(String variableType) {
        VariableType = variableType;
    }

    public boolean isFromROSServiceResponse() {
        return FromROSServiceResponse;
    }

    public void setFromROSServiceResponse(boolean fromROSServiceResponse) {
        FromROSServiceResponse = fromROSServiceResponse;
    }

    public String getAssignmentCode() {
        return AssignmentCode;
    }

    public void setAssignmentCode(String assignmentCode) {
        AssignmentCode = assignmentCode;
    }

    public List<ImportCodeModel> getImportCode() {
        return ImportCode;
    }
}
