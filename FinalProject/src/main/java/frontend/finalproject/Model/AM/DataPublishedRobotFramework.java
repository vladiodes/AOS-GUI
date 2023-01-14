package frontend.finalproject.Model.AM;

import frontend.finalproject.Model.Common.ImportCodeModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DataPublishedRobotFramework extends LocalVariablesInitializationModel {
    private String LocalVariableName;
    private String RosTopicPath;
    private String VariableType;
    private String InitialValue;
    private String TopicMessageType;
    private List<ImportCodeModel> ImportCode;
    private String AssignmentCode; // TODO: change to List?

    public DataPublishedRobotFramework() {
        ImportCode = new ArrayList<>();
    }

    public DataPublishedRobotFramework(backend.finalproject.ProjectFiles.AM.LocalVariablesInit.DataPublishedRobotFramework dataPublishedRobotFramework) {
        this.LocalVariableName = dataPublishedRobotFramework.getLocalVariableName();
        this.RosTopicPath = dataPublishedRobotFramework.getRosTopicPath();
        this.VariableType = dataPublishedRobotFramework.getVariableType();
        this.InitialValue = dataPublishedRobotFramework.getInitialValue();
        this.TopicMessageType = dataPublishedRobotFramework.getTopicMessageType();
        this.ImportCode = dataPublishedRobotFramework.getImportCode().stream().map(ImportCodeModel::new).collect(Collectors.toList());
        this.AssignmentCode = String.join("\n", dataPublishedRobotFramework.getAssignmentCode());
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

    public String getRosTopicPath() {
        return RosTopicPath;
    }

    public void setRosTopicPath(String rosTopicPath) {
        RosTopicPath = rosTopicPath;
    }

    public String getVariableType() {
        return VariableType;
    }

    public void setVariableType(String variableType) {
        VariableType = variableType;
    }

    public String getInitialValue() {
        return InitialValue;
    }

    public void setInitialValue(String initialValue) {
        InitialValue = initialValue;
    }

    public String getTopicMessageType() {
        return TopicMessageType;
    }

    public void setTopicMessageType(String topicMessageType) {
        TopicMessageType = topicMessageType;
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
