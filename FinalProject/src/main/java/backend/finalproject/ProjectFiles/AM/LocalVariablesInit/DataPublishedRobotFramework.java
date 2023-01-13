package backend.finalproject.ProjectFiles.AM.LocalVariablesInit;

import backend.finalproject.ProjectFiles.Common.ImportCode;

import java.util.List;
import java.util.stream.Collectors;

public class DataPublishedRobotFramework extends LocalVariablesInitialization{
    private String LocalVariableName;
    private String RosTopicPath;
    private String VariableType;
    private String InitialValue;

    private String TopicMessageType;

    private List<ImportCode> ImportCode;

    private List<String> AssignmentCode; // TODO: this can be single line also

    public DataPublishedRobotFramework(String localVariableName, String rosTopicPath, String variableType, String initialValue, String topicMessageType, List<ImportCode> importCode, List<String> assignmentCode) {
        LocalVariableName = localVariableName;
        RosTopicPath = rosTopicPath;
        VariableType = variableType;
        InitialValue = initialValue;
        TopicMessageType = topicMessageType;
        ImportCode = importCode;
        AssignmentCode = assignmentCode;
    }

    public DataPublishedRobotFramework(frontend.finalproject.Model.AM.DataPublishedRobotFramework initializationModel) {
        LocalVariableName = initializationModel.getLocalVariableName();
        RosTopicPath = initializationModel.getRosTopicPath();
        VariableType = initializationModel.getInitialValue();
        TopicMessageType = initializationModel.getTopicMessageType();
        ImportCode = initializationModel.getImportCode().stream()
                .map(ImportCode::new).collect(Collectors.toList());

    }

    public String getLocalVariableName() {
        return LocalVariableName;
    }

    public String getRosTopicPath() {
        return RosTopicPath;
    }

    public String getVariableType() {
        return VariableType;
    }

    public String getInitialValue() {
        return InitialValue;
    }

    public String getTopicMessageType() {
        return TopicMessageType;
    }

    public List<backend.finalproject.ProjectFiles.Common.ImportCode> getImportCode() {
        return ImportCode;
    }

    public List<String> getAssignmentCode() {
        return AssignmentCode;
    }
}

