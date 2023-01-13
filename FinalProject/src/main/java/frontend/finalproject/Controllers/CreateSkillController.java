package frontend.finalproject.Controllers;

import backend.finalproject.AOSFacade;
import backend.finalproject.IAOSFacade;
import frontend.finalproject.Model.AM.AMModel;
import frontend.finalproject.Model.AM.DataPublishedRobotFramework;
import frontend.finalproject.Model.AM.SDParametersModel;
import frontend.finalproject.Model.AM.SkillCodeReturnValueModel;
import frontend.finalproject.Model.Common.AssignmentBlock;
import frontend.finalproject.Model.Common.ImportCodeModel;
import frontend.finalproject.Model.SD.GlobalVariableModuleParametersModel;
import frontend.finalproject.Model.SD.SDModel;
import frontend.finalproject.NotificationUtils;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import utils.Response;


public class CreateSkillController {
    @FXML private ChoiceBox<String> GlobalVarModuleParamsCBX;
    @FXML private ChoiceBox<String> GlobalVarPrecondAssCBX;
    @FXML private ChoiceBox<String> PlannerAssPrecondAssCBX;
    @FXML private ChoiceBox<String> DynamicModelCBX;
    @FXML private TextArea AssCodeSkillCodeRetValueTXT;
    @FXML private TextField SkillCodeImportTXT;
    @FXML private TextField SkillCodeFromTXT;
    @FXML private TextField LocalVarNameRobotFrameWorkTXT;
    @FXML private TextField ROSTopicPathTXT;
    @FXML private TextField RobotFrameworkFromTXT;
    @FXML private TextField RobotFrameworkImportTXT;
    @FXML private TextArea AssCodeRobotFrameworkTXT;
    @FXML private TextField VarTypeRobotFrameworkTXT;
    @FXML private TextField InitialValueTXT;
    @FXML private TextField TopicMessageTypeTXT;
    @FXML private ChoiceBox<String> FromROSCBX;
    @FXML private TextField VarTypeSkillCodeTXT;
    @FXML private TextField LocalVarNameSkillCodeTXT;
    @FXML private TextField FromGlobalVarTXT;
    @FXML private TextField InputLocalVarTXT;
    @FXML private TextField AssignServiceFieldCodeTXT;
    @FXML private TextField ServiceFieldNameTXT;
    @FXML private TextField ServiceNameTXT;
    @FXML private TextField ServicePathTXT;
    @FXML private TextField ImportTXT;
    @FXML private TextField FromTXT;
    @FXML private TextField ConditionCodeTXT;
    @FXML private TextField ResponseTXT;
    @FXML private TextField GlueFrameWorkTXT;
    @FXML private TextField AssignmentCodeDynamicModelTXT;
    @FXML private TextField AssignmentNameDynamicModelTXT;
    @FXML private TextField ViolatingPreconditionPenaltyTXT;
    @FXML private TextField AssignmentCodePlannerAssistancePreCondTXT;
    @FXML private TextField AssignmentNamePlannerAssistancePreCondTXT;
    @FXML private TextField AssignmentCodeGlobVarPreCondTXT;
    @FXML private TextField AssignmentNameGlobVarPreCondTXT;
    @FXML private TextField GlobalVarModuleParamTypeTXT;
    @FXML private TextField GlobalVarModuleParamNameTXT;
    @FXML private TextField SkillNameTXT;
    private String projectName;
    private SDModel SDmodel = new SDModel();
    private AMModel AMmodel = new AMModel();
    private ImportCodeModel curImportCodeModelModuleActivationSection = null;
    private SkillCodeReturnValueModel curSkillCodeReturnValue = null;
    private ImportCodeModel curSkillCodeRetValueImportCode = null;
    private ImportCodeModel curRobotFrameworkImportCode = null;
    private DataPublishedRobotFramework curRobotFramework = null;
    private IAOSFacade facade = AOSFacade.getInstance();

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void handleInsertGlobalVarModuleParamSD(ActionEvent event) {
        SDmodel.addGlobalVariableModuleParameter(GlobalVarModuleParamNameTXT.getText(),GlobalVarModuleParamTypeTXT.getText());
        GlobalVarModuleParamsCBX.getItems().add(GlobalVarModuleParamNameTXT.getText());
        GlobalVarModuleParamNameTXT.setText("");
        GlobalVarModuleParamTypeTXT.setText("");
    }


    public void handleInsertGlobalVarPreconditionAssignment(ActionEvent event) {
        SDmodel.addGlobalVariablePreconditionAssignment(new AssignmentBlock(
                AssignmentNameGlobVarPreCondTXT.getText(),
                AssignmentCodeGlobVarPreCondTXT.getText()));
        GlobalVarPrecondAssCBX.getItems().add(AssignmentNameGlobVarPreCondTXT.getText());
        AssignmentNameGlobVarPreCondTXT.setText("");
        AssignmentCodeGlobVarPreCondTXT.setText("");
    }

    public void handleInsertGlobalPlannerPrecondition(ActionEvent event) {
        SDmodel.addPlannerAssistancePreconditionsAssignment(new AssignmentBlock(
                AssignmentNamePlannerAssistancePreCondTXT.getText(),
                AssignmentCodePlannerAssistancePreCondTXT.getText()
        ));
        PlannerAssPrecondAssCBX.getItems().add(AssignmentNamePlannerAssistancePreCondTXT.getText());
        AssignmentNamePlannerAssistancePreCondTXT.setText("");
        AssignmentCodePlannerAssistancePreCondTXT.setText("");
    }

    public void handleInsertDynamicModel(ActionEvent event) {
        SDmodel.addDynamicModelAssignment(new AssignmentBlock(
                AssignmentNameDynamicModelTXT.getText(),
                AssignmentCodeDynamicModelTXT.getText()
        ));
        DynamicModelCBX.getItems().add(AssignmentNameDynamicModelTXT.getText());
        AssignmentNameDynamicModelTXT.setText("");
        AssignmentCodeDynamicModelTXT.setText("");
    }

    public void handleSDPreviewBTNClick(ActionEvent event) {
        UtilsFXML.handlePreviewBTNClick(event,generateSDJSON());
    }

    private String generateSDJSON() {
        System.out.println(projectName);
        buildSingleFieldsSD();
        return facade.previewSDJSON(SDmodel).getValue();
    }

    private void buildSingleFieldsSD() {
        SDmodel.buildPlpMain(projectName,SkillNameTXT.getText());
        SDmodel.setViolatingPreconditionPenalty(Double.parseDouble(ViolatingPreconditionPenaltyTXT.getText()));
    }

    public void handleAMPreviewBTNClick(ActionEvent event) {
        UtilsFXML.handlePreviewBTNClick(event,generateAMJSON());
    }

    private String generateAMJSON() {
        buildSingleFieldsAM();
        return facade.previewAMJSON(AMmodel).getValue();
    }

    public void handleAddSkillBTNClick(ActionEvent event) {
        buildSingleFieldsSD();
        buildSingleFieldsAM();
        Response<Boolean> response = facade.addSkillToProject(SDmodel,AMmodel);
        System.out.println(response.getValue());
    }

    private void buildSingleFieldsAM() {
        AMmodel.buildPlpMain(getProjectName(),SkillNameTXT.getText());
        AMmodel.setGlueFramework(GlueFrameWorkTXT.getText());
        AMmodel.getModuleActivation().setServicePath(ServicePathTXT.getText());
        AMmodel.getModuleActivation().setServiceName(ServiceNameTXT.getText());
    }

    public void handleInsertResponseRuleBTNClick(ActionEvent event) {
        AMmodel.addResponseRule(
                ResponseTXT.getText(),
                ConditionCodeTXT.getText());
        ResponseTXT.setText("");
        ConditionCodeTXT.setText("");
    }

    public void handleInsertImportValueImportCodeSectionBTNClick(ActionEvent event) {
        if(curImportCodeModelModuleActivationSection == null){
            curImportCodeModelModuleActivationSection = new ImportCodeModel();
        }
        curImportCodeModelModuleActivationSection.addImportValue(ImportTXT.getText());
        ImportTXT.setText("");
    }

    public void handleInsertImportCodeSectionBTNClick(ActionEvent event) {
        curImportCodeModelModuleActivationSection.setFrom(FromTXT.getText());
        FromTXT.setText("");
        ImportTXT.setText("");
        AMmodel.getModuleActivation().addImportCode(curImportCodeModelModuleActivationSection);
        curImportCodeModelModuleActivationSection = null;
    }

    public void handleInsertServiceParamBTNClick(ActionEvent event) {
        AMmodel.getModuleActivation().addServiceParam(
                ServiceFieldNameTXT.getText(),
                AssignServiceFieldCodeTXT.getText()
        );
        ServiceFieldNameTXT.setText("");
        AssignServiceFieldCodeTXT.setText("");
    }

    public void handleInsertLocalVarInitSDbtnClick(ActionEvent event) {
        AMmodel.addLocalVariableInitialization(new SDParametersModel(
                InputLocalVarTXT.getText(),
                FromGlobalVarTXT.getText()
        ));
        InputLocalVarTXT.setText("");
        FromGlobalVarTXT.setText("");
    }

    public void handleInsertLocalVarInitSkillCodeBTNClick(ActionEvent event) {
        if(curSkillCodeReturnValue == null)
            curSkillCodeReturnValue = new SkillCodeReturnValueModel();

        curSkillCodeReturnValue.setLocalVariableName(LocalVarNameSkillCodeTXT.getText());
        curSkillCodeReturnValue.setVariableType(VarTypeSkillCodeTXT.getText());
        curSkillCodeReturnValue.setFromROSServiceResponse(Boolean.parseBoolean(FromROSCBX.getValue()));
        curSkillCodeReturnValue.setAssignmentCode(AssCodeSkillCodeRetValueTXT.getText());
        AMmodel.addLocalVariableInitialization(curSkillCodeReturnValue);
        LocalVarNameSkillCodeTXT.setText("");
        VarTypeSkillCodeTXT.setText("");
        FromROSCBX.setValue("");
        AssCodeSkillCodeRetValueTXT.setText("");
        curSkillCodeReturnValue = null;
    }

    public void handleInsertImportValueSkillCodeBTNClick(ActionEvent event) {
        if(curSkillCodeRetValueImportCode == null)
            curSkillCodeRetValueImportCode = new ImportCodeModel();
        curSkillCodeRetValueImportCode.addImportValue(SkillCodeImportTXT.getText());
        SkillCodeImportTXT.setText("");
    }

    public void handleInsertImportCodeSkillCodeBTNClick(ActionEvent event) {
        if(curSkillCodeRetValueImportCode == null)
            curSkillCodeRetValueImportCode = new ImportCodeModel();
        if(curSkillCodeReturnValue == null)
            curSkillCodeReturnValue = new SkillCodeReturnValueModel();

        curSkillCodeRetValueImportCode.setFrom(SkillCodeFromTXT.getText());
        curSkillCodeReturnValue.addImportCodeModel(curSkillCodeRetValueImportCode);
        curSkillCodeRetValueImportCode = null;
        SkillCodeImportTXT.setText("");
        SkillCodeFromTXT.setText("");
    }

    public void handleInsertLocalVarINITRobotFrameworkBTNClick(ActionEvent event) {
        if (curRobotFramework == null)
            curRobotFramework = new DataPublishedRobotFramework();
        curRobotFramework.setLocalVariableName(LocalVarNameRobotFrameWorkTXT.getText());
        curRobotFramework.setRosTopicPath(ROSTopicPathTXT.getText());
        curRobotFramework.setVariableType(VarTypeRobotFrameworkTXT.getText());
        curRobotFramework.setInitialValue(InitialValueTXT.getText());
        curRobotFramework.setTopicMessageType(TopicMessageTypeTXT.getText());
        curRobotFramework.setAssignmentCode(AssCodeRobotFrameworkTXT.getText());
        AMmodel.addLocalVariableInitialization(curRobotFramework);
        LocalVarNameRobotFrameWorkTXT.setText("");
        ROSTopicPathTXT.setText("");
        VarTypeRobotFrameworkTXT.setText("");
        InitialValueTXT.setText("");
        TopicMessageTypeTXT.setText("");
        AssCodeRobotFrameworkTXT.setText("");
        curRobotFramework = null;
    }

    public void handleInsertImportValueRobotFrameworkBTNClick(ActionEvent event) {
        if(curRobotFrameworkImportCode == null)
            curRobotFrameworkImportCode = new ImportCodeModel();
        curRobotFrameworkImportCode.addImportValue(RobotFrameworkImportTXT.getText());
        RobotFrameworkImportTXT.setText("");
    }

    public void handleInsertImportCodeRobotFrameworkBTNClick(ActionEvent event) {
        if(curRobotFrameworkImportCode == null)
            curRobotFrameworkImportCode = new ImportCodeModel();
        if(curRobotFramework == null)
            curRobotFramework = new DataPublishedRobotFramework();

        curRobotFrameworkImportCode.setFrom(RobotFrameworkFromTXT.getText());
        curRobotFramework.addImportCodeModel(curRobotFrameworkImportCode);
        curRobotFrameworkImportCode = null;
        RobotFrameworkFromTXT.setText("");
        RobotFrameworkImportTXT.setText("");
    }

    public void handleBackBTNClick(ActionEvent event) {
        UtilsFXML.navToHome(event);
    }

    public void handleDeleteGlobalVarModuleBTNClick(ActionEvent event) {
        String selected = GlobalVarModuleParamsCBX.selectionModelProperty().getValue().getSelectedItem();
        SDmodel.setGlobalVariableModuleParameters(
                SDmodel.getGlobalVariableModuleParameters()
                        .stream().filter(
                                (var) -> !var.getName().equals(selected)
                        ).toList());
        GlobalVarModuleParamsCBX.setItems(FXCollections.observableArrayList(
                SDmodel.getGlobalVariableModuleParameters().stream()
                        .map(GlobalVariableModuleParametersModel::getName).toList()
        ));
        GlobalVarModuleParamsCBX.setValue("");
        UtilsFXML.showNotification(NotificationUtils.DELETED_GLOBAL_VAR_MODULE_TITLE,NotificationUtils.DELETED_GLOBAL_VAR_MODULE_TEXT,null);
    }

    public void handleDeleteGlobalVarPreconditionAssBTNClick(ActionEvent event) {
        String selected = GlobalVarPrecondAssCBX.selectionModelProperty().getValue().getSelectedItem();
        SDmodel.getPreconditions().setGlobalVariablePreconditionAssignments(
                SDmodel.getPreconditions().getGlobalVariablePreconditionAssignments().stream().filter(
                        (assignment) -> !assignment.getAssignmentName().equals(selected)
                ).toList()
        );
        GlobalVarPrecondAssCBX.setItems(FXCollections.observableArrayList(
                SDmodel.getPreconditions().getGlobalVariablePreconditionAssignments().
                        stream().map(AssignmentBlock::getAssignmentName).toList()
        ));
        GlobalVarPrecondAssCBX.setValue("");
        UtilsFXML.showNotification(NotificationUtils.DELETED_GLOBAL_VAR_PRECONDITION_ASS_TITLE,NotificationUtils.DELETED_GLOBAL_VAR_PRECONDITION_ASS_TEXT,null);
    }

    public void handleDeletePlannerAssPrecondAssBTNClick(ActionEvent event) {
        String selected = PlannerAssPrecondAssCBX.selectionModelProperty().getValue().getSelectedItem();
        SDmodel.getPreconditions().setPlannerAssistancePreconditionsAssignments(
                SDmodel.getPreconditions().getPlannerAssistancePreconditionsAssignments().stream().filter(
                        (ass) -> !ass.getAssignmentName().equals(selected)
                ).toList()
        );
        PlannerAssPrecondAssCBX.setItems(FXCollections.observableArrayList(
                SDmodel.getPreconditions().getPlannerAssistancePreconditionsAssignments()
                        .stream().map(AssignmentBlock::getAssignmentName).toList()
        ));
        PlannerAssPrecondAssCBX.setValue("");
        UtilsFXML.showNotification(NotificationUtils.DELETED_PLANNER_ASS_PRECONDITION_TITLE,NotificationUtils.DELETED_PLANNER_ASS_PRECONDITION_TEXT,null);
    }

    public void handleDeleteDynamicModelBTNClick(ActionEvent event) {
        String selected = DynamicModelCBX.selectionModelProperty().getValue().getSelectedItem();
        SDmodel.getDynamicModel().setNextStateAssignments(
                SDmodel.getDynamicModel().getNextStateAssignments().stream().filter(
                        (ass) -> !ass.getAssignmentName().equals(selected)
                ).toList()
        );
        DynamicModelCBX.setItems(FXCollections.observableArrayList(
                SDmodel.getDynamicModel().getNextStateAssignments()
                        .stream().map(AssignmentBlock::getAssignmentName).toList()
        ));
        DynamicModelCBX.setValue("");
        UtilsFXML.showNotification(NotificationUtils.DELETED_DYNAMIC_MODEL_TITLE,NotificationUtils.DELETED_DYNAMIC_MODEL_TEXT,null);
    }
}
