package frontend.finalproject.Controllers;

import backend.finalproject.AOSFacade;
import backend.finalproject.IAOSFacade;
import frontend.finalproject.Controllers.SubControllers.AddSubController;
import frontend.finalproject.Controllers.SubControllers.AddVarTypeEnumController;
import frontend.finalproject.Model.AM.*;
import frontend.finalproject.Model.Common.AssignmentBlock;
import frontend.finalproject.Model.Common.ImportCodeModel;
import frontend.finalproject.Model.SD.GlobalVariableModuleParametersModel;
import frontend.finalproject.Model.SD.SDModel;
import frontend.finalproject.Utils.AddModelCallback;
import frontend.finalproject.Utils.NotificationUtils;
import frontend.finalproject.Utils.UtilsFXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import utils.Response;

import java.io.IOException;
import java.util.List;

import static frontend.finalproject.Utils.UtilsFXML.*;


public class CreateSkillController {
    public static final String GLOBAL_VARIABLE_PRECONDITION = "Global variable precondition";
    public static final String PLANNER_ASSISTANCE_PRECONDITION = "Planner assistance precondition";
    public static final String DYNAMIC_MODEL = "Dynamic model";
    public static final String POSSIBLE_VALUES = "Possible value";
    @FXML private TextArea FromStringLocalVariableTXT;
    @FXML private TreeView<String> PossibleParamsValueAssTreeView;
    @FXML private TextArea AssignmentCodePossibleParamsValueTXT;
    @FXML
    private TreeView<String> LocalVarInitTreeView;
    @FXML
    private TreeView<String> GlobalVarModuleParamsTreeView;
    @FXML
    private TreeView<String> GlobalVarPreconditionAssTreeView;
    @FXML
    private TreeView<String> PlannerAssistancePreconditionAssTreeView;
    @FXML
    private TreeView<String> DynamicModelTreeView;
    @FXML
    private TreeView<String> ResponseRulesTreeView;
    @FXML
    private TreeView<String> ImportCodeMainTreeView;
    @FXML
    private TreeView<String> ServicesParamsTreeView;
    @FXML
    private Button addSkillBTN;
    @FXML
    private Label titleLBL;
    @FXML
    private TextArea AssignServiceFieldCodeTXT;
    @FXML
    private TextField ServiceFieldNameTXT;
    @FXML
    private TextField ServiceNameTXT;
    @FXML
    private TextField ServicePathTXT;
    @FXML
    private TextArea ImportTXT;
    @FXML
    private TextField FromTXT;
    @FXML
    private TextArea ConditionCodeTXT;
    @FXML
    private TextField ResponseTXT;
    @FXML
    private TextField GlueFrameWorkTXT;
    @FXML
    private TextArea AssignmentCodeDynamicModelTXT;
    @FXML
    private TextField AssignmentNameDynamicModelTXT;
    @FXML
    private TextField ViolatingPreconditionPenaltyTXT;
    @FXML
    private TextArea AssignmentCodePlannerAssistancePreCondTXT;
    @FXML
    private TextField AssignmentNamePlannerAssistancePreCondTXT;
    @FXML
    private TextArea AssignmentCodeGlobVarPreCondTXT;
    @FXML
    private TextField AssignmentNameGlobVarPreCondTXT;
    @FXML
    private TextField GlobalVarModuleParamTypeTXT;
    @FXML
    private TextField GlobalVarModuleParamNameTXT;
    @FXML
    private TextField SkillNameTXT;
    private String projectName;
    private SDModel SDmodel = new SDModel();
    private AMModel AMmodel = new AMModel();
    private SkillCodeReturnValueModel curSkillCodeReturnValue = null;
    private ImportCodeModel curSkillCodeRetValueImportCode = null;
    private ImportCodeModel curRobotFrameworkImportCode = null;
    private DataPublishedRobotFramework curRobotFramework = null;
    private IAOSFacade facade = AOSFacade.getInstance();
    private UtilsFXML.Source source;
    private String originalSkillName;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void handleInsertGlobalVarModuleParamSD(ActionEvent event) {
        SDmodel.addGlobalVariableModuleParameter(GlobalVarModuleParamNameTXT.getText(), GlobalVarModuleParamTypeTXT.getText());
        addGlobalVarModuleParamToTree(new GlobalVariableModuleParametersModel(GlobalVarModuleParamNameTXT.getText(), GlobalVarModuleParamTypeTXT.getText()));
        GlobalVarModuleParamNameTXT.setText("");
        GlobalVarModuleParamTypeTXT.setText("");
    }

    @FXML
    public void initialize() {
        LocalVarInitTreeView.setRoot(new TreeItem<>("Local Variables Initialization"));
        GlobalVarModuleParamsTreeView.setRoot(new TreeItem<>("Global Variables Module Parameters"));
        GlobalVarPreconditionAssTreeView.setRoot(new TreeItem<>("Global Variables Precondition Assignments"));
        PlannerAssistancePreconditionAssTreeView.setRoot(new TreeItem<>("Planner Assistance Precondition Assignments"));
        DynamicModelTreeView.setRoot(new TreeItem<>("Dynamic Models"));
        ResponseRulesTreeView.setRoot(new TreeItem<>("Response Rules"));
        ImportCodeMainTreeView.setRoot(new TreeItem<>("Import Code"));
        ServicesParamsTreeView.setRoot(new TreeItem<>("Services Parameters"));
        PossibleParamsValueAssTreeView.setRoot(new TreeItem<>("Possible Parameters Values"));
    }


    public void handleInsertGlobalVarPreconditionAssignment(ActionEvent event) {
        AssignmentBlock assignmentBlock = new AssignmentBlock(
                AssignmentNameGlobVarPreCondTXT.getText(),
                AssignmentCodeGlobVarPreCondTXT.getText());
        SDmodel.addGlobalVariablePreconditionAssignment(assignmentBlock);
        addAssBlockToTree(GlobalVarPreconditionAssTreeView,GLOBAL_VARIABLE_PRECONDITION,assignmentBlock);
        AssignmentNameGlobVarPreCondTXT.setText("");
        AssignmentCodeGlobVarPreCondTXT.setText("");
        UtilsFXML.showNotification(NotificationUtils.ADDED_GLOBAL_VAR_PRECOND_ASS_TITLE,NotificationUtils.ADDED_GLOBAL_VAR_PRECOND_ASS_TXT,null);
    }

    public void handleInsertGlobalPlannerPrecondition(ActionEvent event) {
        AssignmentBlock block = new AssignmentBlock(
                AssignmentNamePlannerAssistancePreCondTXT.getText(),
                AssignmentCodePlannerAssistancePreCondTXT.getText()
        );
        SDmodel.addPlannerAssistancePreconditionsAssignment(block);
        addAssBlockToTree(PlannerAssistancePreconditionAssTreeView,PLANNER_ASSISTANCE_PRECONDITION,block);
        AssignmentNamePlannerAssistancePreCondTXT.setText("");
        AssignmentCodePlannerAssistancePreCondTXT.setText("");
        UtilsFXML.showNotification(NotificationUtils.ADDED_GLOBAL_PLANNER_PRECOND_TITLE,NotificationUtils.ADDED_GLOBAL_PLANNER_PRECOND_TXT,null);
    }

    public void handleInsertDynamicModel(ActionEvent event) {
        AssignmentBlock block = new AssignmentBlock(
                AssignmentNameDynamicModelTXT.getText(),
                AssignmentCodeDynamicModelTXT.getText()
        );
        SDmodel.addDynamicModelAssignment(block);
        addAssBlockToTree(DynamicModelTreeView,DYNAMIC_MODEL,block);
        AssignmentNameDynamicModelTXT.setText("");
        AssignmentCodeDynamicModelTXT.setText("");
        UtilsFXML.showNotification(NotificationUtils.ADDED_DYNAMIC_MODEL_TITLE,NotificationUtils.ADDED_DYNAMIC_MODEL_TXT,null);
    }

    public void handleSDPreviewBTNClick(ActionEvent event) {
        UtilsFXML.handlePreviewBTNClick(event, generateSDJSON());
    }

    private String generateSDJSON() {
        buildSingleFieldsSD();
        Response<String> response = facade.previewSDJSON(SDmodel);
        if (response.hasErrorOccurred())
            UtilsFXML.showNotification(null, null, response);
        else
            return response.getValue();
        return "";
    }

    private void buildSingleFieldsSD() {
        SDmodel.buildPlpMain(projectName, SkillNameTXT.getText());
        SDmodel.setViolatingPreconditionPenalty(Integer.parseInt(ViolatingPreconditionPenaltyTXT.getText()));
    }

    public void handleAMPreviewBTNClick(ActionEvent event) {
        UtilsFXML.handlePreviewBTNClick(event, generateAMJSON());
    }

    private String generateAMJSON() {
        buildSingleFieldsAM();
        Response<String> response = facade.previewAMJSON(AMmodel);
        if (response.hasErrorOccurred()) {
            UtilsFXML.showNotification(null, null, response);
            return "";
        } else
            return response.getValue();
    }

    public void handleAddSkillBTNClick(ActionEvent event) {
        buildSingleFieldsSD();
        buildSingleFieldsAM();
        if (source == UtilsFXML.Source.EDIT_SKILL) {
            saveSkillChanges();
        }
        else
            addNewSkill();
    }

    private void saveSkillChanges() {
        Response<Boolean> response = facade.saveChangesToSkill(this.originalSkillName, SDmodel, AMmodel);
        UtilsFXML.showNotification(NotificationUtils.SAVED_CHANGES_TO_PROJECT_TITLE, NotificationUtils.SAVED_CHANGES_TO_PROJECT_TEXT, response);
        if (!response.hasErrorOccurred() && response.getValue()) {
            this.originalSkillName = SkillNameTXT.getText();
        }
    }

    private void addNewSkill() {
        Response<Boolean> response = facade.addSkillToProject(SDmodel, AMmodel);
        UtilsFXML.showNotification(NotificationUtils.ADD_SKILL_TITLE,
                response.getMessage() == null ? NotificationUtils.ADD_SKILL_SUCCESS_TEXT : response.getMessage(),
                response);
    }

    private void buildSingleFieldsAM() {
        AMmodel.buildPlpMain(getProjectName(), SkillNameTXT.getText());
        AMmodel.setGlueFramework(GlueFrameWorkTXT.getText());
        AMmodel.getModuleActivation().setServicePath(ServicePathTXT.getText());
        AMmodel.getModuleActivation().setServiceName(ServiceNameTXT.getText());
    }

    public void handleInsertResponseRuleBTNClick(ActionEvent event) {
        if (!FromStringLocalVariableTXT.getText().isEmpty()){
            AMmodel.getModuleResponse().setFromStringLocalVariable(FromStringLocalVariableTXT.getText());
        }
        else{
            AMmodel.addResponseRule(
                    ResponseTXT.getText(),
                    ConditionCodeTXT.getText());
            addResponseRuleToTree(new ResponseRule(ResponseTXT.getText(), ConditionCodeTXT.getText()));
        }

        ResponseTXT.setText("");
        ConditionCodeTXT.setText("");
        FromStringLocalVariableTXT.setText("");
        UtilsFXML.showNotification(NotificationUtils.ADDED_RESPONSE_RULE_TITLE, NotificationUtils.ADDED_RESPONSE_RULE_TEXT, null);
    }

    public void handleInsertImportCodeSectionBTNClick(ActionEvent event) {
        String[] imports = ImportTXT.getText().split("\n");
        ImportCodeModel importCodeModel = new ImportCodeModel();
        importCodeModel.setFrom(FromTXT.getText());
        for (String importCode : imports)
            importCodeModel.addImportValue(importCode);

        FromTXT.setText("");
        ImportTXT.setText("");
        AMmodel.getModuleActivation().addImportCode(importCodeModel);

        UtilsFXML.addImportCodeModelToTree(ImportCodeMainTreeView.getRoot(),importCodeModel);
        UtilsFXML.showNotification(NotificationUtils.ADDED_IMPORT_CODE_TITLE, NotificationUtils.ADDED_IMPORT_CODE_TEXT, null);
    }

    public void handleInsertServiceParamBTNClick(ActionEvent event) {
        AMmodel.getModuleActivation().addServiceParam(
                ServiceFieldNameTXT.getText(),
                AssignServiceFieldCodeTXT.getText()
        );
        addServiceParamToTree(new ServiceParameter(ServiceFieldNameTXT.getText(), AssignServiceFieldCodeTXT.getText()));
        ServiceFieldNameTXT.setText("");
        AssignServiceFieldCodeTXT.setText("");
        UtilsFXML.showNotification(NotificationUtils.ADDED_SERVICE_PARAM_TITLE, NotificationUtils.ADDED_SERVICE_PARAM_TEXT, null);
    }

    public void handleInsertLocalVarInitSDbtnClick(ActionEvent event) {
        loadAddStage(UtilsFXML.ADD_SD_FILE_SKILL_PARAMS_PATH,
                (model) -> {
                    SDParametersModel sdParametersModel = (SDParametersModel) model;
                    AMmodel.addLocalVariableInitialization(sdParametersModel);
                    addLocalVarInitToTree(sdParametersModel);
                });
    }

    public void handleInsertLocalVarInitSkillCodeBTNClick(ActionEvent event) {

        loadAddStage(UtilsFXML.ADD_SKILL_CODE_RET_VALUE_PATH,
                (model) -> {
                    SkillCodeReturnValueModel skillCodeReturnValueModel = (SkillCodeReturnValueModel) model;
                    AMmodel.addLocalVariableInitialization(skillCodeReturnValueModel);
                    addLocalVarInitToTree(skillCodeReturnValueModel);
                    UtilsFXML.showNotification(NotificationUtils.ADDED_LOCAL_VAR_INIT_SKILL_CODE_RET_VALUE_TITLE, NotificationUtils.ADDED_LOCAL_VAR_INIT_SKILL_CODE_RET_VALUE_TEXT, null);
                });
    }

    public void handleInsertLocalVarINITRobotFrameworkBTNClick(ActionEvent event) {

        loadAddStage(UtilsFXML.ADD_ROBOT_FRAMEWORK_LOCAL_VAR_INIT_PATH,
                (model) -> {
                    DataPublishedRobotFramework dataPublishedRobotFramework = (DataPublishedRobotFramework) model;
                    AMmodel.addLocalVariableInitialization(dataPublishedRobotFramework);
                    addLocalVarInitToTree(dataPublishedRobotFramework);
                    UtilsFXML.showNotification(NotificationUtils.ADDED_LOCAL_VAR_INIT_ROBOT_FRAMEWORK_TITLE, NotificationUtils.ADDED_LOCAL_VAR_INIT_ROBOT_FRAMEWORK_TEXT, null);
                });
    }


    public void handleBackBTNClick(ActionEvent event) {
        UtilsFXML.navToHome(event);
    }

    public void handleDeleteGlobalVarModuleBTNClick(ActionEvent event) {
        TreeItem<String> val = GlobalVarModuleParamsTreeView.getSelectionModel().getSelectedItem();
        if(GlobalVarModuleParamsTreeView.getRoot().getChildren().contains(val)){
            String selected = val.getValue();

            SDmodel.setGlobalVariableModuleParameters(SDmodel.getGlobalVariableModuleParameters().
                    stream().filter(
                            (dec) -> !dec.getName().equals(selected)
                    ).toList());
            GlobalVarModuleParamsTreeView.getRoot().getChildren().remove(val);
            UtilsFXML.showNotification(NotificationUtils.DELETED_GLOBAL_VAR_MODULE_TITLE,NotificationUtils.DELETED_GLOBAL_VAR_MODULE_TEXT,null);
        }
        else{
            UtilsFXML.showErrorNotification(NotificationUtils.DELETE_GLOBAL_VAR_MODULE_FAIL_TITLE,NotificationUtils.DELETE_GLOBAL_VAR_MODULE_FAIL_TEXT);
        }

    }

    public void handleDeleteGlobalVarPreconditionAssBTNClick(ActionEvent event) {
        TreeItem<String> val = GlobalVarPreconditionAssTreeView.getSelectionModel().getSelectedItem();
        if(GlobalVarPreconditionAssTreeView.getRoot().getChildren().contains(val)){
            int selected = GlobalVarPreconditionAssTreeView.getRoot().getChildren().indexOf(val);
            SDmodel.getPreconditions().getGlobalVariablePreconditionAssignments().remove(selected);
            GlobalVarPreconditionAssTreeView.getRoot().getChildren().remove(val);
            UtilsFXML.showNotification(NotificationUtils.DELETED_GLOBAL_VAR_PRECONDITION_ASS_TITLE,NotificationUtils.DELETED_GLOBAL_VAR_PRECONDITION_ASS_TEXT,null);
        }
        else{
            UtilsFXML.showErrorNotification(NotificationUtils.DELETED_GLOBAL_VAR_PRECONDITION_ASS_FAIL_TITLE,NotificationUtils.DELETED_GLOBAL_VAL_PRECONDITION_ASS_FAIL_TEXT);
        }

    }

    public void handleDeletePlannerAssPrecondAssBTNClick(ActionEvent event) {
        TreeItem<String> val = PlannerAssistancePreconditionAssTreeView.getSelectionModel().getSelectedItem();
        if(PlannerAssistancePreconditionAssTreeView.getRoot().getChildren().contains(val)){
            int selected = PlannerAssistancePreconditionAssTreeView.getRoot().getChildren().indexOf(val);
            SDmodel.getPreconditions().getPlannerAssistancePreconditionsAssignments().remove(selected);
            PlannerAssistancePreconditionAssTreeView.getRoot().getChildren().remove(val);
            UtilsFXML.showNotification(NotificationUtils.DELETED_PLANNER_ASS_PRECONDITION_TITLE,NotificationUtils.DELETED_PLANNER_ASS_PRECONDITION_TEXT,null);
        }
        else{
            UtilsFXML.showErrorNotification(NotificationUtils.DELETED_PLANNER_ASS_PRECONDITION_FAIL_TITLE,NotificationUtils.DELETED_PLANNER_ASS_PRECONDITION_FAIL_TEXT);
        }
    }

    public void handleDeleteDynamicModelBTNClick(ActionEvent event) {
        TreeItem<String> val = DynamicModelTreeView.getSelectionModel().getSelectedItem();
        if(DynamicModelTreeView.getRoot().getChildren().contains(val)){
            int selected = DynamicModelTreeView.getRoot().getChildren().indexOf(val);
            SDmodel.getDynamicModel().getNextStateAssignments().remove(selected);
            DynamicModelTreeView.getRoot().getChildren().remove(val);
            UtilsFXML.showNotification(NotificationUtils.DELETED_DYNAMIC_MODEL_TITLE,NotificationUtils.DELETED_DYNAMIC_MODEL_TEXT,null);
        }
        else {
            UtilsFXML.showErrorNotification(NotificationUtils.DELETE_DYNAMIC_MODEL_FAIL_TITLE,NotificationUtils.DELETE_DYNAMIC_MODEL_FAIL_TEXT);
        }
    }

    public void handleResponseRuleDeleteBTNClick(ActionEvent event) {
        TreeItem<String> val = ResponseRulesTreeView.getSelectionModel().getSelectedItem();
        if(ResponseRulesTreeView.getRoot().getChildren().contains(val)){
            String selected = val.getValue();
            AMmodel.getModuleResponse().setResponseRules(AMmodel.getModuleResponse().getResponseRules().stream().filter(
                    (x) -> !x.getResponse().equals(selected)
            ).toList());
            ResponseRulesTreeView.getRoot().getChildren().remove(val);
            UtilsFXML.showNotification(NotificationUtils.DELETED_RESPONSE_RULE_TITLE,NotificationUtils.DELETED_RESPONSE_RULE_TEXT,null);
        }
        else{
            UtilsFXML.showErrorNotification(NotificationUtils.DELETE_RESPONSE_RULE_FAIL_TITLE,NotificationUtils.DELETE_RESPONSE_RULE_FAIL_TEXT);
        }

    }

    public void handleImportCodeResponseRuleSectionDeleteBTNClick(ActionEvent event) {
        TreeItem<String> val = ImportCodeMainTreeView.getSelectionModel().getSelectedItem();
        if(ImportCodeMainTreeView.getRoot().getChildren().contains(val)){
            int selected = ImportCodeMainTreeView.getRoot().getChildren().indexOf(val);
            AMmodel.getModuleActivation().getRosService().getImportCode().remove(selected);
            ImportCodeMainTreeView.getRoot().getChildren().remove(val);

            UtilsFXML.showNotification(NotificationUtils.DELETED_IMPORT_CODE_TITLE,NotificationUtils.DELETED_IMPORT_CODE_TEXT,null);
        }
        else {
            UtilsFXML.showErrorNotification(NotificationUtils.DELETE_IMPORT_CODE_RESPONSE_RULE_SECTION_FAIL_TITLE,NotificationUtils.DELETE_IMPORT_CODE_RESPONSE_RULE_SECTION_FAIL_TEXT);
        }
    }

    public void handleDeleteServiceParamsBTNClick(ActionEvent event) {
        TreeItem<String> val = ServicesParamsTreeView.getSelectionModel().getSelectedItem();
        if(ServicesParamsTreeView.getRoot().getChildren().contains(val)){
            String selected = val.getValue();
            AMmodel.getModuleActivation().getRosService().setServiceParameters(AMmodel.getModuleActivation().getRosService().getServiceParameters().stream().filter(
                    (x) -> !x.getServiceFieldName().equals(selected)
            ).toList());
            ServicesParamsTreeView.getRoot().getChildren().remove(val);

            UtilsFXML.showNotification(NotificationUtils.DELETED_SERVICE_PARAM_TITLE,NotificationUtils.DELETED_SERVICE_PARAM_TEXT,null);
        }
        else{
            UtilsFXML.showErrorNotification(NotificationUtils.DELETE_SERVICE_PARAMS_FAIL_TITLE,NotificationUtils.DELETE_SERVICE_PARAMS_FAIL_TEXT);
        }

    }

    public void setSource(UtilsFXML.Source source) {
        this.source = source;
        titleLBL.setText("Edit Skill");
        addSkillBTN.setText("Save changes");

    }

    public void setSD(SDModel value) {
        this.originalSkillName = value.getPlpMain().getName();
        SDmodel = value;
        SkillNameTXT.setText(SDmodel.getPlpMain().getName());
        ViolatingPreconditionPenaltyTXT.setText(String.valueOf(SDmodel.getPreconditions().getViolatingPreconditionPenalty()));
        populateSDTree();
    }

    private void populateSDTree() {
        populateGlobalVarModuleParamsTree();
        populateGlobalVarPreconditionAssTree();
        populatePlannerAssistancePreconditionAssTree();
        populateDynamicModelTree();
        populatePossibleParamsValueAssTree();
    }

    private void populatePossibleParamsValueAssTree() {
        for (AssignmentBlock block : SDmodel.getPossibleParametersValue()) {
            addAssBlockToTree(PossibleParamsValueAssTreeView, POSSIBLE_VALUES, block);
        }
    }

    private void populateDynamicModelTree() {
        populateAssignmentBlocksInTree(DynamicModelTreeView, SDmodel.getDynamicModel().getNextStateAssignments(), DYNAMIC_MODEL);
    }

    private void populateAssignmentBlocksInTree(TreeView<String> treeView, List<AssignmentBlock> assignmentBlocks, String defaultName) {
        for (AssignmentBlock block : assignmentBlocks) {
            addAssBlockToTree(treeView, defaultName, block);
        }
    }

    private void addAssBlockToTree(TreeView<String> treeView, String defaultName, AssignmentBlock block) {
        TreeItem<String> newItem = new TreeItem<>(block.getAssignmentName() == null || block.getAssignmentName().isEmpty() ? defaultName : block.getAssignmentName());
        for (String code : block.getAssignmentCode())
            newItem.getChildren().add(new TreeItem<>(code));
        treeView.getRoot().getChildren().add(newItem);
    }

    private void populatePlannerAssistancePreconditionAssTree() {
        populateAssignmentBlocksInTree(PlannerAssistancePreconditionAssTreeView, SDmodel.getPreconditions().getPlannerAssistancePreconditionsAssignments(), PLANNER_ASSISTANCE_PRECONDITION);
    }

    private void populateGlobalVarPreconditionAssTree() {
        populateAssignmentBlocksInTree(GlobalVarPreconditionAssTreeView, SDmodel.getPreconditions().getGlobalVariablePreconditionAssignments(), GLOBAL_VARIABLE_PRECONDITION);
    }

    private void populateGlobalVarModuleParamsTree() {
        for (GlobalVariableModuleParametersModel model : SDmodel.getGlobalVariableModuleParameters()) {
            addGlobalVarModuleParamToTree(model);
        }
    }

    private void addGlobalVarModuleParamToTree(GlobalVariableModuleParametersModel model) {
        TreeItem<String> item = new TreeItem<>(model.getName());
        item.getChildren().add(new TreeItem<>("Type: " + model.getType()));
        GlobalVarModuleParamsTreeView.getRoot().getChildren().add(item);
    }

    public void setAM(AMModel value) {
        AMmodel = value;
        GlueFrameWorkTXT.setText(AMmodel.getGlueFramework());
        ServicePathTXT.setText(AMmodel.getModuleActivation().getRosService().getServicePath());
        ServiceNameTXT.setText(AMmodel.getModuleActivation().getRosService().getServiceName());
        populateAMTree();
    }

    private void populateAMTree() {
        populateLocalVarInitTree();
        populateResponseRulesTree();
        populateImportCodeMainTree();
        populateServicesParamsTree();

    }

    private void populateServicesParamsTree() {
        for (ServiceParameter param : AMmodel.getModuleActivation().getRosService().getServiceParameters()) {
            addServiceParamToTree(param);
        }
    }

    private void addServiceParamToTree(ServiceParameter param) {
        TreeItem<String> newItem = new TreeItem<>(param.getServiceFieldName());
        newItem.getChildren().add(new TreeItem<>(param.getAssignServiceFieldCode()));
        ServicesParamsTreeView.getRoot().getChildren().add(newItem);
    }

    private void populateImportCodeMainTree() {
        TreeItem<String> imports = createImportCodeTree(AMmodel.getModuleActivation().getRosService().getImportCode());
        for (TreeItem<String> item : imports.getChildren())
            ImportCodeMainTreeView.getRoot().getChildren().add(item);
    }

    private void populateResponseRulesTree() {
        for (ResponseRule rule : AMmodel.getModuleResponse().getResponseRules()) {
            addResponseRuleToTree(rule);
        }
    }

    private void addResponseRuleToTree(ResponseRule rule) {
        TreeItem<String> newItem = new TreeItem<>(rule.getResponse());
        newItem.getChildren().add(new TreeItem<>(rule.getConditionCodeWithLocalVariables()));
        ResponseRulesTreeView.getRoot().getChildren().add(newItem);
    }

    private void addLocalVarInitToTree(LocalVariablesInitializationModel model) {
        if (model instanceof SDParametersModel) {
            addSDParamModelToTree((SDParametersModel) model);
        } else if (model instanceof SkillCodeReturnValueModel) {
            addSkillCodeRetValueToTree((SkillCodeReturnValueModel) model);
        } else if (model instanceof DataPublishedRobotFramework) {
            addDataPublishedRobotFWToTree((DataPublishedRobotFramework) model);
        }
    }

    private void addDataPublishedRobotFWToTree(DataPublishedRobotFramework model) {
        TreeItem<String> newItem = new TreeItem<>("Data published: " + model.getLocalVariableName());
        newItem.getChildren().add(new TreeItem<>("ROS topic path: " + model.getRosTopicPath()));
        newItem.getChildren().add(new TreeItem<>("Variable type: " + model.getVariableType()));
        newItem.getChildren().add(new TreeItem<>("Initial value: " + model.getInitialValue()));
        newItem.getChildren().add(new TreeItem<>("Topic message type" + model.getTopicMessageType()));
        newItem.getChildren().add(new TreeItem<>("Assignment code: " + model.getAssignmentCode()));
        newItem.getChildren().add(createImportCodeTree(model.getImportCode()));

        LocalVarInitTreeView.getRoot().getChildren().add(newItem);
    }

    private void addSkillCodeRetValueToTree(SkillCodeReturnValueModel model) {
        TreeItem<String> newItem = new TreeItem<>("Skill code return value: " + model.getLocalVariableName());
        newItem.getChildren().add(new TreeItem<>("Variable type: " + model.getVariableType()));
        newItem.getChildren().add(new TreeItem<>("From ROS Service Response: " + model.isFromROSServiceResponse()));
        newItem.getChildren().add(new TreeItem<>("Assignment code: " + model.getAssignmentCode()));
        newItem.getChildren().add(createImportCodeTree(model.getImportCode()));

        LocalVarInitTreeView.getRoot().getChildren().add(newItem);
    }

    private void addSDParamModelToTree(SDParametersModel model) {
        TreeItem<String> newItem = new TreeItem<>("SD parameter: " + model.getInputLocalVariable());
        newItem.getChildren().add(new TreeItem<>("From Global Variable: " + model.getFromGlobalVariable()));
        LocalVarInitTreeView.getRoot().getChildren().add(newItem);
    }

    private void populateLocalVarInitTree() {
        for (LocalVariablesInitializationModel model : AMmodel.getLocalVariablesInitialization())
            addLocalVarInitToTree(model);
    }

    public void handleEditResponseRuleBTNClick(ActionEvent actionEvent) {
        TreeItem<String> selected = ResponseRulesTreeView.getSelectionModel().getSelectedItem();
        if(ResponseRulesTreeView.getRoot().getChildren().contains(selected)) {
            ResponseRule rule = AMmodel.getModuleResponse().getResponseRules().stream().
                    filter(r -> r.getResponse().equals(selected.getValue())).findFirst().orElse(null);

            loadEditStage(UtilsFXML.EDIT_RESPONSE_RULE_PATH, rule, selected,
                    () -> {
                        assert rule != null;
                        selected.setValue(rule.getResponse());
                        selected.getChildren().get(0).setValue(rule.getConditionCodeWithLocalVariables());
                    });

        }
        else {
            UtilsFXML.showErrorNotification(NotificationUtils.EDIT_RESPONSE_RULE_FAIL_TITLE, NotificationUtils.EDIT_RESPONSE_RULE_FAIL_TEXT);
        }
    }

    private void loadAddStage(String fxml, AddModelCallback addToTreeCallBack){
        Stage stage = new Stage();
        try{
            FXMLLoader loader = new FXMLLoader(AddVarTypeEnumController.class.getResource(fxml));
            Parent root = loader.load();
            AddSubController controller = loader.getController();
            controller.setCallback(() -> {
                addToTreeCallBack.add(controller.getModel());
            });

            stage.setScene(new Scene(root));
            stage.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    public void handleEditGlobalVarModuleParamsBTNClick(ActionEvent actionEvent) {
        TreeItem<String> selected = GlobalVarModuleParamsTreeView.getSelectionModel().getSelectedItem();
        if(GlobalVarModuleParamsTreeView.getRoot().getChildren().contains(selected)) {
            GlobalVariableModuleParametersModel model = SDmodel.getGlobalVariableModuleParameters().stream().
                    filter(p -> p.getName().equals(selected.getValue())).findFirst().orElse(null);

            loadEditStage(UtilsFXML.EDIT_GLOBAL_VAR_MODULE_PARAMS_PATH, model, selected,
                    () -> {
                        assert model != null;
                        selected.setValue(model.getName());
                        selected.getChildren().get(0).setValue("Type: " + model.getType());
                    });
        }
        else {
            UtilsFXML.showErrorNotification(NotificationUtils.EDIT_GLOBAL_VAR_MODULE_PARAMS_FAIL_TITLE, NotificationUtils.EDIT_GLOBAL_VAR_MODULE_PARAMS_FAIL_TEXT);
        }
    }

    public void handleEditGlobalVarPrecondAssBTNClick(ActionEvent actionEvent) {
        TreeItem<String> selectedItem = GlobalVarPreconditionAssTreeView.getSelectionModel().getSelectedItem();
        if(GlobalVarPreconditionAssTreeView.getRoot().getChildren().contains(selectedItem)){
            AssignmentBlock model = SDmodel.getPreconditions().getGlobalVariablePreconditionAssignments().get(GlobalVarPreconditionAssTreeView.getRoot().getChildren().indexOf(selectedItem));


            loadEditStage(UtilsFXML.EDIT_ASS_CODE_PATH, model,selectedItem,
                    () -> {
                        assert model != null;
                        selectedItem.setValue(model.getAssignmentName() == null || model.getAssignmentName().isEmpty() ? GLOBAL_VARIABLE_PRECONDITION : model.getAssignmentName());
                        selectedItem.getChildren().setAll(model.getAssignmentCode().stream().map(TreeItem::new).toList());
                    }
            );

        }
        else {
            UtilsFXML.showErrorNotification(NotificationUtils.EDIT_GLOBAL_VAR_PRECOND_ASS_FAIL_TITLE,NotificationUtils.EDIT_GLOBAL_VAR_PRECOND_ASS_FAIL_TEXT);
        }
    }

    public void handleEditPlannerAssistancePreconditionAssEditBTNClick(ActionEvent actionEvent) {
        TreeItem<String> selectedItem = PlannerAssistancePreconditionAssTreeView.getSelectionModel().getSelectedItem();
        if (PlannerAssistancePreconditionAssTreeView.getRoot().getChildren().contains(selectedItem)) {
            AssignmentBlock model = SDmodel.getPreconditions().getPlannerAssistancePreconditionsAssignments().get(PlannerAssistancePreconditionAssTreeView.getRoot().getChildren().indexOf(selectedItem));

            loadEditStage(UtilsFXML.EDIT_ASS_CODE_PATH, model,selectedItem,
                    () -> {
                        assert model != null;
                        selectedItem.setValue(model.getAssignmentName() == null || model.getAssignmentName().isEmpty() ? PLANNER_ASSISTANCE_PRECONDITION : model.getAssignmentName());
                        selectedItem.getChildren().setAll(model.getAssignmentCode().stream().map(TreeItem::new).toList());
                    }
            );
        }
        else{
            UtilsFXML.showErrorNotification(NotificationUtils.EDIT_PLANNER_ASSISTANCE_PRECOND_ASS_FAIL_TITLE,NotificationUtils.EDIT_PLANNER_ASSISTANCE_PRECOND_ASS_FAIL_TEXT);
        }
    }

    public void handleEditDynamicModelBTNClick(ActionEvent actionEvent) {
        TreeItem<String> selectedItem = DynamicModelTreeView.getSelectionModel().getSelectedItem();
        if(DynamicModelTreeView.getRoot().getChildren().contains(selectedItem)){
            AssignmentBlock model = SDmodel.getDynamicModel().getNextStateAssignments().get(DynamicModelTreeView.getRoot().getChildren().indexOf(selectedItem));

            loadEditStage(UtilsFXML.EDIT_ASS_CODE_PATH, model,selectedItem,
                    () -> {
                        assert model != null;
                        selectedItem.setValue(model.getAssignmentName() == null || model.getAssignmentName().isEmpty() ? DYNAMIC_MODEL : model.getAssignmentName());
                        selectedItem.getChildren().setAll(model.getAssignmentCode().stream().map(TreeItem::new).toList());
                    }
            );
        }
        else{
            UtilsFXML.showErrorNotification(NotificationUtils.EDIT_DYNAMIC_MODEL_FAIL_TITLE,NotificationUtils.EDIT_DYNAMIC_MODEL_FAIL_TEXT);
        }
    }

    public void handleEditImportCodeMainEditBTNClick(ActionEvent actionEvent) {
        TreeItem<String> selectedItem = ImportCodeMainTreeView.getSelectionModel().getSelectedItem();
        if(ImportCodeMainTreeView.getRoot().getChildren().contains(selectedItem)){
            ImportCodeModel model = AMmodel.getModuleActivation().getRosService().getImportCode()
                    .get(ImportCodeMainTreeView.getRoot().getChildren().indexOf(selectedItem));

            loadEditStage(UtilsFXML.EDIT_IMPORT_CODE_PATH, model,selectedItem,
                    () -> {
                        assert model != null;
                        selectedItem.setValue("From: " + model.getFrom());
                        selectedItem.getChildren().setAll(model.getImport().stream().map(TreeItem::new).toList());
                    }
            );
        }
        else{
            UtilsFXML.showErrorNotification(NotificationUtils.EDIT_IMPORT_CODE_FAIL_TITLE,NotificationUtils.EDIT_IMPORT_CODE_FAIL_TEXT);
        }
    }

    public void handleEditServiceParamsEditBTNClick(ActionEvent actionEvent) {
        TreeItem<String> selectedItem = ServicesParamsTreeView.getSelectionModel().getSelectedItem();
        if(ServicesParamsTreeView.getRoot().getChildren().contains(selectedItem)){
            ServiceParameter model = AMmodel.getModuleActivation().getRosService().getServiceParameters()
                    .get(ServicesParamsTreeView.getRoot().getChildren().indexOf(selectedItem));

            loadEditStage(UtilsFXML.EDIT_SERVICE_PARAMS_PATH, model,selectedItem,
                    () -> {
                        assert model != null;
                        selectedItem.setValue(model.getServiceFieldName());
                        selectedItem.getChildren().remove(0);
                        selectedItem.getChildren().add(new TreeItem<>(model.getAssignServiceFieldCode()));
                    }
            );
        }
        else{
            UtilsFXML.showErrorNotification(NotificationUtils.EDIT_SERVICE_PARAMS_FAIL_TITLE,NotificationUtils.EDIT_SERVICE_PARAMS_FAIL_TEXT);
        }

    }

    public void handleEditLocalVarInitBtnClick(ActionEvent actionEvent) {
        TreeItem<String> val = LocalVarInitTreeView.getSelectionModel().getSelectedItem();
        if(LocalVarInitTreeView.getRoot().getChildren().contains(val)){
            LocalVariablesInitializationModel model = AMmodel.getLocalVariablesInitialization()
                    .get(LocalVarInitTreeView.getRoot().getChildren().indexOf(val));
            editLocalVarInit(model,val);
        }
        else {
            UtilsFXML.showErrorNotification(NotificationUtils.EDIT_LOCAL_VAR_INIT_FAIL_TITLE,NotificationUtils.EDIT_LOCAL_VAR_INIT_FAIL_TEXT);
        }

    }

    private void editLocalVarInit(LocalVariablesInitializationModel model, TreeItem<String> val) {
        if(model instanceof SDParametersModel m){
            loadEditStage(UtilsFXML.ADD_SD_FILE_SKILL_PARAMS_PATH, m,val,
                    () -> {
                        val.setValue("SD parameter: " + m.getInputLocalVariable());
                        val.getChildren().remove(0);
                        val.getChildren().add(new TreeItem<>("From Global Variable: " + m.getFromGlobalVariable()));
                    }
            );
        }
        else if(model instanceof DataPublishedRobotFramework m){
            loadEditStage(UtilsFXML.ADD_ROBOT_FRAMEWORK_LOCAL_VAR_INIT_PATH, m,val,
                    () -> {
                        val.setValue("Data published: " + m.getLocalVariableName());
                        val.getChildren().clear();
                        val.getChildren().add(new TreeItem<>("ROS topic path: " + m.getRosTopicPath()));
                        val.getChildren().add(new TreeItem<>("Variable type: " + m.getVariableType()));
                        val.getChildren().add(new TreeItem<>("Initial value: " + m.getInitialValue()));
                        val.getChildren().add(new TreeItem<>("Topic message type" + m.getTopicMessageType()));
                        val.getChildren().add(new TreeItem<>("Assignment code: " + m.getAssignmentCode()));
                        val.getChildren().add(createImportCodeTree(m.getImportCode()));
                    }
            );
        }
        else if(model instanceof SkillCodeReturnValueModel m){
            loadEditStage(UtilsFXML.ADD_SKILL_CODE_RET_VALUE_PATH, m,val,
                    () -> {

                        val.setValue("Skill code return value: " + m.getLocalVariableName());
                        val.getChildren().clear();
                        val.getChildren().add(new TreeItem<>("Variable type: " + m.getVariableType()));
                        val.getChildren().add(new TreeItem<>("From ROS Service Response: " + m.isFromROSServiceResponse()));
                        val.getChildren().add(new TreeItem<>("Assignment code: " + m.getAssignmentCode()));
                        val.getChildren().add(createImportCodeTree(m.getImportCode()));
                    }
            );
        }
    }

    public void handleDeleteLocalVarInitBTNClick(ActionEvent actionEvent) {
        TreeItem<String> val = LocalVarInitTreeView.getSelectionModel().getSelectedItem();
        if(LocalVarInitTreeView.getRoot().getChildren().contains(val)){
            AMmodel.getLocalVariablesInitialization().remove(LocalVarInitTreeView.getRoot().getChildren().indexOf(val));
            LocalVarInitTreeView.getRoot().getChildren().remove(val);
            UtilsFXML.showNotification(NotificationUtils.DELETE_LOCAL_VAR_INIT_SUCCESS_TITLE,NotificationUtils.DELETE_LOCAL_VAR_INIT_SUCCESS_TEXT,null);
        }
        else {
            UtilsFXML.showErrorNotification(NotificationUtils.DELETE_LOCAL_VAR_INIT_FAIL_TITLE,NotificationUtils.DELETE_LOCAL_VAR_INIT_FAIL_TEXT);
        }
    }

    public void handleDeleteParamsValueAssBTNClick(ActionEvent actionEvent) {
        TreeItem<String> val = PossibleParamsValueAssTreeView.getSelectionModel().getSelectedItem();
        if(PossibleParamsValueAssTreeView.getRoot().getChildren().contains(val)){
            int selected = PossibleParamsValueAssTreeView.getRoot().getChildren().indexOf(val);
            SDmodel.getPossibleParametersValue().remove(selected);
            PossibleParamsValueAssTreeView.getRoot().getChildren().remove(val);
            UtilsFXML.showNotification(NotificationUtils.DELETED_POSSIBLE_PARAMS_VALUE_TITLE,NotificationUtils.DELETED_POSSIBLE_PARAMS_VALUE_TEXT,null);
        }
        else{
            UtilsFXML.showErrorNotification(NotificationUtils.DELETE_POSSIBLE_PARAMS_VALUE_FAIL_TITLE,NotificationUtils.DELETE_POSSIBLE_PARAMS_VALUE_FAIL_TEXT);
        }

    }

    public void handleEditPossibleParamsValueAssEditBTNClick(ActionEvent actionEvent) {
        TreeItem<String> selectedItem = PossibleParamsValueAssTreeView.getSelectionModel().getSelectedItem();
        if (PossibleParamsValueAssTreeView.getRoot().getChildren().contains(selectedItem)) {
            AssignmentBlock model = SDmodel.getPossibleParametersValue().get(PossibleParamsValueAssTreeView.getRoot().getChildren().indexOf(selectedItem));
            loadEditStage(UtilsFXML.EDIT_ASS_CODE_PATH, model, selectedItem,
                    () -> {
                        assert model != null;
                        selectedItem.getChildren().setAll(model.getAssignmentCode().stream().map(TreeItem::new).toList());
                        model.setAssignmentName(null); // because we don't want a name for the assignment block.
                    }
            );

        } else {
            UtilsFXML.showErrorNotification(NotificationUtils.INSERT_POSSIBLE_PARAMS_VALUE_FAIL_TITLE,NotificationUtils.INSERT_POSSIBLE_PARAMS_VALUE_FAIL_TXT);
        }
    }

    public void handleInsertPossibleParamsValueBtnClick(ActionEvent actionEvent) {
        AssignmentBlock block = new AssignmentBlock(
                null,
                AssignmentCodePossibleParamsValueTXT.getText()
        );
        SDmodel.addPossibleParamsValueAssBlock(block);
        addAssBlockToTree(PossibleParamsValueAssTreeView,POSSIBLE_VALUES,block);
        AssignmentCodePossibleParamsValueTXT.setText("");
        UtilsFXML.showNotification(NotificationUtils.INSERT_POSSIBLE_PARAMS_VALUE_SUCCESS_TITLE,NotificationUtils.INSERT_POSSIBLE_PARAMS_VALUE_SUCCESS_TEXT,null);
    }

    public void plpMainInfo(ActionEvent actionEvent) {
        popUpWindow("PlpMain", "header section that each documentation file contains.");
    }
    public void globalVarModuleParamInfo(ActionEvent actionEvent) {
        popUpWindow("GlobalVariableModuleParameters", "This section uses to define the parameters sent to a skill. The skill parameters are the context under which the skill operates. Possible parameters are, for example, 1) an enum value for navigation modes like 'fast' navigation vs. 'safe' navigation or 2) the navigation destination. Formally the \"GlobalVariableModuleParameters\" is an array of parameter items");
    }
    public void glovalVarModuleParamNameInfo(ActionEvent actionEvent) {
        popUpWindow("Name", "the string name to refer to the parameter");
    }
    public void glovalVarModuleParamTypeInfo(ActionEvent actionEvent) {
        popUpWindow("Type", "a string indicating the parameter type");
    }
    public void globalVarPreconditionAssignInfo(ActionEvent actionEvent) {
        popUpWindow("GlobalVariablePreconditionAssignments", "This section is used to define when a skill with given parameters met the preconditions (default is true). This field is an Assinment block, the should assign a value to the reserved variable __meetPrecondition and it can use (not change) any state variable from state (see The three sets of state variables).");
    }
    public void assignmentCodeInfo(ActionEvent actionEvent) {
        popUpWindow("Assignments blocks", "There are a few sections of Assignments blocks like \"InitialBeliefStateAssignments\" they all have the same syntax.\n" +
                "An Assignments block is an array of assignments. Each assignment item may have an \"AssignmentName\" for readability, and it must have an \"AssignmentCode\" field which is a string (a single code line) or an array of strings such that each string is a code line.");
    }
    public void plannerAssistPrecondAssignInfo(ActionEvent actionEvent) {
        popUpWindow("PlannerAssistancePreconditionsAssignments", "In this section, the user can define a default (rollout) policy. This field is an Assinment block that should set the value of the reserved variable __heuristicValue. The code assignment can be conditioned on variable from state (but cannot change their value. See The three sets of state variables).\n" +
                "The default policy will draw between all available skills and parameter assignments. The weight of each skill will be its computed __heuristicValue");
    }
    public void dynamicModelInfo(ActionEvent actionEvent) {
        popUpWindow("dynamicModelInfo", "The DynamicModel defines the high-level behavior of a skill. More specifically, the transition reward and observation models, how a skill changes the state, what costs (or rewards) are applied and which observations are returned. The \"NextStateAssignments\" is an assignments block that sets the next state (state__), reward (__reward reserved variable), and observation (__moduleResponse reserved variable) conditioned on the previose state (state), the state after extrinsic changes (state_) and if the preconditions were met (__meetPrecondition).");
    }
    public void glueFrameworkInfo(ActionEvent actionEvent) {
        popUpWindow("GlueFramework", "GlueFramework is a string field to specify the type of robot framework (e.g., \"ROS\" for ROS1).");
    }
    public void responseReulesInfo(ActionEvent actionEvent) {
        popUpWindow("ResponseRules", "ModuleResponse section defines the translation between an actual execution outcome of a skill, to observations the AOS planning engine can reason about. The planning engine uses the SD documentation to simulate what might happen. The AM ModuleResponse section is used to translate what really happened to the language used in the SD documentation (see DynamicModel). The planning engine uses this information to update the robot's belief.\n" +
                "\n" +
                "The ResponseRules is the array of possible observations a skill can return.");
    }
    public void responseInfo(ActionEvent actionEvent) {
        popUpWindow("Response", "a string field that defines the observation name. The SD __moduleResponse variable can only receive values defined as \"Response.\"");
    }
    public void conditionCodeInfo(ActionEvent actionEvent) {
        popUpWindow("ConditionCodeWithLocalVariables", " a string field that uses the user to define when the skill returns the current response (code is in Python for ROS). The condition may depend on \"Local Variable\" values (see Local Variables).");
    }
    public void importCodeInfo(ActionEvent actionEvent) {
        popUpWindow("ImportCode", "defines imported modules used when calling the service.");
    }
    public void servicePathInfo(ActionEvent actionEvent) {
        popUpWindow("ServicePath", "the service path (called in the service ROS Wiki) ");
    }
    public void serviceNameInfo(ActionEvent actionEvent) {
        popUpWindow("ServiceName", "he name of the service \"srv\" file (<ServiceName>.srv).");
    }
    public void serviceParametersInfo(ActionEvent actionEvent) {
        popUpWindow("ServiceParameters", " the array of parameters sent in the service request");
    }
    public void serviceFieldNameInfo(ActionEvent actionEvent) {
        popUpWindow("ServiceFieldName", "the name of the parameter as defined in the <ServiceName>.srv file.");
    }
    public void assignServiceFieldCodeInfo(ActionEvent actionEvent) {
        popUpWindow("AssignServiceFieldCode", "the value of the parameter. The user can define a Python code with local variables (see Local Variables).");
    }
    public void localVarInitInfo(ActionEvent actionEvent) {
        popUpWindow("LocalVariablesInitialization", "The \"LocalVariablesInitialization\" section is used to define local variables.\n" +
                "Local variables can take their value from three possible sources:\n" +
                "\n" +
                "SD file skill parameters (see GlobalVariableModuleParameters section). Only this type of local variable can be used to activate the skill since the other local variables' value is calculated when the skill execution ends.\n" +
                "\"InputLocalVariable\" is the name of the local variable.\n" +
                "\"FromGlobalVariable\" is the name of the skill parameter defined in the SD \"GlobalVariableModuleParameters\" section." +
                "Skill-code returned value. Using the value returned from the skill code. The user can define a Python function to manipulate the returned value to something more meaningful or convenient.\n" +
                "This local variable definition has the following fields:\n" +
                "\"LocalVariableName\" is the local variable name.\n" +
                "\"VariableType\" this optional field is the type of the variable when converted to C++ (used for the \"state given observation\" feature).\n" +
                "\"FromROSServiceResponse\" should be true when the value is taken from the service response.\n" +
                "\"AssignmentCode\" is the Python code for assigning the local variable value from the ROS service response (returned value). The reserved word __input is used to reference the service returned value. This field value is the string code. Nevertheless, users can define it as an array of strings representing complex Python code (the indentations are preserved).\n" +
                "\"ImportCode\" is an array of imports needed when receiving the service response." +
                "Public data published in the robot framework (e.g., ROS topics). This type of local variable is constantly updated when certain public information is published in the robot framework(e.g., when a ROS topic message is published). It can capture events that occur during the skill execution. It's last value will be used when the skill observation is calculated.\n" +
                "This type of local variable is defined using the following fields:\n" +
                "\"LocalVariableName\" is the local variable name.\n" +
                "\"RosTopicPath\" is the topic path.\\\n" +
                "\"InitialValue\" defines the value used to initialize the variable.\n" +
                "\"TopicMessageType\" is the type of the topic message (see).\n" +
                "\"VariableType\" this optional field is the type of the variable when converted to C++ (used for the \"state given observation\" feature).\n" +
                "\"AssignmentCode\" is the Python code for assigning the local variable value from the ROS service response (returned value). The reserved word __input is used to reference the service returned value. This field value is the string code. Nevertheless, users can define it as an array of strings representing complex Python code (the indentations are preserved).\n" +
                "\"ImportCode\" is an array of imports needed when receiving the service response.");
    }
}
