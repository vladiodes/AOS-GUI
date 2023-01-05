package frontend.finalproject.Model.AM;

import java.util.ArrayList;
import java.util.List;

public class LocalVariablesInitializationModel {

}

class SDParametersModel extends LocalVariablesInitializationModel{
    private String InputLocalVariable;
    private String FromGlobalVariable;

    public SDParametersModel(String InputLocalVariable, String FromGlobalVariable){
        this.InputLocalVariable = InputLocalVariable;
        this.FromGlobalVariable = FromGlobalVariable;
    }

}

class SkillCodeReturnValueModel extends LocalVariablesInitializationModel{
    private String LocalVariableName;
    private String VariableType;
    private boolean FromROSServiceResponse;
    private String AssignmentCode;
    private List<ImportCodeModel> ImportCode;

    public SkillCodeReturnValueModel(){
        ImportCode = new ArrayList<>();
    }

    public void addImportCodeModel(ImportCodeModel model){
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
}

class DataPublishedRobotFramework extends LocalVariablesInitializationModel{
    private String LocalVariableName;
    private String RosTopicPath;
    private String VariableType;
    private String InitialValue;
    private String TopicMessageType;
    private List<ImportCodeModel> ImportCode;
    private String AssignmentCode;

    public DataPublishedRobotFramework(){
        ImportCode = new ArrayList<>();
    }

    public void addImportCodeModel(ImportCodeModel model){
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
}
