package backend.finalproject.ProjectFiles.AM.LocalVariablesInit;

import backend.finalproject.ProjectFiles.Common.ImportCode;
import frontend.finalproject.Model.AM.SkillCodeReturnValueModel;
import frontend.finalproject.Model.Common.AssignmentBlock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

// doc:
public class SkillCodeReturnValue extends LocalVariablesInitialization{
    private String LocalVariableName;
    private String VariableType;
    private boolean FromROSServiceResponse;
    private List<String> AssignmentCode;
    private List<ImportCode> ImportCode;

    public SkillCodeReturnValue(String localVariableName, String variableType, boolean fromROSServiceResponse, List<String> assignmentCode, List<ImportCode> importCode) {
        LocalVariableName = localVariableName;
        VariableType = variableType;
        FromROSServiceResponse = fromROSServiceResponse;
        AssignmentCode = assignmentCode;
        ImportCode = importCode;
    }

    public SkillCodeReturnValue(SkillCodeReturnValueModel initializationModel) {
        LocalVariableName = initializationModel.getLocalVariableName();
        VariableType = initializationModel.getVariableType();
        FromROSServiceResponse = initializationModel.isFromROSServiceResponse();
        AssignmentCode = new ArrayList<>();
        AssignmentCode.add(initializationModel.getAssignmentCode()); // TODO: notice that in the frontend it is string and not list of string
        ImportCode = initializationModel.getImportCode().stream().map(ImportCode::new).collect(Collectors.toList());
    }

    public String getLocalVariableName() {
        return LocalVariableName;
    }

    public String getVariableType() {
        return VariableType;
    }

    public boolean isFromROSServiceResponse() {
        return FromROSServiceResponse;
    }

    public List<String> getAssignmentCode() {
        return AssignmentCode != null ? AssignmentCode : Collections.emptyList();
    }

    public List<backend.finalproject.ProjectFiles.Common.ImportCode> getImportCode() {
        return ImportCode != null ? ImportCode : Collections.emptyList();
    }

}
