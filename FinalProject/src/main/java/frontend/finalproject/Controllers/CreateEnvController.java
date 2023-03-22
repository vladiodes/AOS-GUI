package frontend.finalproject.Controllers;

import backend.finalproject.AOSFacade;
import backend.finalproject.IAOSFacade;
import frontend.finalproject.Model.Common.AssignmentBlock;
import frontend.finalproject.Model.Env.*;
import frontend.finalproject.NotificationUtils;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import utils.Response;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class CreateEnvController {

    public TreeView<String> SpecialStatesTreeView;
    public TreeView<String> ExChangesDynModelTreeView;
    @FXML private TreeView<String> InitialBeliefStateAssTreeView;
    @FXML private TreeView<String> GlobalVarDecTreeView;
    @FXML private TreeView<String> GlobalVarTypesTreeView;
    @FXML private Label titleLBL;
    @FXML private Button createProjectBTN;
    @FXML private TextArea AssignmentCodeChangeTXT;
    @FXML private TextArea StateConditionCodeTXT;
    @FXML private TextField RewardTXT;
    @FXML private ChoiceBox<String> IsGoalStateBOX;
    @FXML private ChoiceBox<String> IsOneTimeRewardBOX;
    @FXML
    private TextArea InitBeliefAssCodeTXT;
    @FXML private TextField InitBeliefAssNameTXT;
    @FXML private TextField GlobalVarTypeNameTXT;
    private EnvModel envModel = new EnvModel();

    @FXML
    private Button previewBTN;
    @FXML
    private TextField ProjectNameTXT;

    @FXML
    private TextField HorizonTXT;

    @FXML
    private TextField DiscountTXT;

    @FXML
    private TextField NameGlobalVarDecTXT;

    @FXML
    private TextField TypeGlobalVarDecTXT;

    @FXML
    private TextField IsActionParameterValueTXT;

    @FXML
    private TextArea DefaultCodeGlobVarDecTXT;

    @FXML
    private TextField CompoundNameTXT;
    @FXML
    private Label NameLBL;

    @FXML
    private Label TypeLBL;

    @FXML
    private TextField CompoundTypeTXT;

    @FXML
    private Label DefaultLBL;

    @FXML
    private TextField CompoundDefaultTXT;

    @FXML
    private ChoiceBox<String> CompoundEnumChoiceBox;
    @FXML
    private Button nextValBTN;

    @FXML
    private Button nextVarBTN;

    @FXML
    private Label enumValueLBL;

    @FXML
    private TextField enumValueTXT;

    private UtilsFXML.Source source;
    private GlobalVariableTypeModel currentGlobVarType = null;

    private final IAOSFacade facade = AOSFacade.getInstance();
    private Object SpecialStatesCBX;


    @FXML
    public void initialize(){
        CompoundEnumChoiceBox.valueProperty().addListener(
                getListenerForCompoundOrEnumCBX());
        GlobalVarTypesTreeView.setRoot(new TreeItem<>("Global Variable Types"));
        GlobalVarDecTreeView.setRoot(new TreeItem<>("Global Variable Declarations"));
        InitialBeliefStateAssTreeView.setRoot(new TreeItem<>("Initial Belief State Assignments"));
        SpecialStatesTreeView.setRoot(new TreeItem<>("Special states"));
        ExChangesDynModelTreeView.setRoot(new TreeItem<>("Extrinsic Changes Dynamic Model"));
    }

    private ChangeListener<String> getListenerForCompoundOrEnumCBX() {
        return (observable, oldValue, newValue) -> {
            switch (newValue) {
                case "compound":
                    makeEnumOptVisible(false);
                    break;
                case "enum":
                    makeEnumOptVisible(true);
                    break;
            }
        };
    }

    private void makeEnumOptVisible(boolean val) {
        enumValueLBL.setVisible(val);
        nextValBTN.setVisible(val);
        enumValueTXT.setVisible(val);

        nextVarBTN.setVisible(!val);
        NameLBL.setVisible(!val);
        CompoundNameTXT.setVisible(!val);
        TypeLBL.setVisible(!val);
        CompoundTypeTXT.setVisible(!val);
        DefaultLBL.setVisible(!val);
        CompoundDefaultTXT.setVisible(!val);
    }

    private void makeAllVarInvisible(boolean val) {
        enumValueLBL.setVisible(val);
        nextValBTN.setVisible(val);
        enumValueTXT.setVisible(val);

        nextVarBTN.setVisible(val);
        NameLBL.setVisible(val);
        CompoundNameTXT.setVisible(val);
        TypeLBL.setVisible(val);
        CompoundTypeTXT.setVisible(val);
        DefaultLBL.setVisible(val);
        CompoundDefaultTXT.setVisible(val);
    }


    public void handlePreviewBTNClick(ActionEvent event) {
        UtilsFXML.handlePreviewBTNClick(event,generateJSON());
    }

    private String generateJSON(){
        addPlpAndEnvGeneralToModel();
        return facade.previewEnvJSON(envModel).getValue();
    }

    public void handleInsertAnotherGlobalVarTypeClick(ActionEvent event) {
        if(currentGlobVarType!=null) {
            envModel.addGlobalVarType(currentGlobVarType);
            addTypeToTree();
            currentGlobVarType = null;
            GlobalVarTypeNameTXT.setText("");
            makeAllVarInvisible(false);

            UtilsFXML.showNotification(NotificationUtils.ADDED_GLOBAL_VAR_NEW_TYPE_TITLE, NotificationUtils.ADDED_GLOBAL_VAR_NEW_TYPE_TEXT,null);
        }
        else{
            UtilsFXML.showErrorNotification(NotificationUtils.ADDED_GLOBAL_VAR_NEW_TYPE_FAILED_TITLE,NotificationUtils.ADDED_GLOBAL_VAR_TYPE_FAILED_TEXT);
        }



    }

    private void addTypeToTree() {
        if(currentGlobVarType.getType().equals("enum")) {
            GlobalVariableTypeEnumModel enumType = (GlobalVariableTypeEnumModel)currentGlobVarType;
            TreeItem<String> newType = new TreeItem<>(enumType.getTypeName() + " - enum");
            for(String enumVal : enumType.getEnumValues())
                newType.getChildren().add(new TreeItem<>(enumVal));

            GlobalVarTypesTreeView.getRoot().getChildren().add(newType);
        }
        else{ // compound type
            GlobalVariableTypeCompoundModel compoundModel = (GlobalVariableTypeCompoundModel)currentGlobVarType;
            TreeItem<String> newType = new TreeItem<>(compoundModel.getTypeName() + " - compound");
            for(CompoundVariable cv : compoundModel.getVariables())
                newType.getChildren().add(new TreeItem<>(cv.toString()));

            GlobalVarTypesTreeView.getRoot().getChildren().add(newType);
        }

    }

    public void handleInsertNextEnumValClick(ActionEvent event) {
        if(currentGlobVarType==null){
            currentGlobVarType = new GlobalVariableTypeEnumModel(GlobalVarTypeNameTXT.getText(),"enum");
        }
        GlobalVariableTypeEnumModel enumModel = (GlobalVariableTypeEnumModel)currentGlobVarType;
        enumModel.addEnumValue(enumValueTXT.getText());
        enumValueTXT.setText("");
        UtilsFXML.showNotification(NotificationUtils.ADDED_ENUM_VALUE_TITLE,NotificationUtils.ADDED_ENUM_VALUE_TEXT,null);

    }

    public void handleInsertNextCompoundVarClick(ActionEvent event) {
        if(currentGlobVarType==null){
            currentGlobVarType = new GlobalVariableTypeCompoundModel(GlobalVarTypeNameTXT.getText(),"compound");
        }
        GlobalVariableTypeCompoundModel compoundModel = (GlobalVariableTypeCompoundModel) currentGlobVarType;
        CompoundVariable variable = new CompoundVariable(CompoundNameTXT.getText(),CompoundTypeTXT.getText(),CompoundDefaultTXT.getText());
        compoundModel.insertVariable(variable);
        CompoundNameTXT.setText("");
        CompoundTypeTXT.setText("");
        CompoundDefaultTXT.setText("");
        UtilsFXML.showNotification(NotificationUtils.ADDED_COMPOUND_VARIABLE_TITLE,NotificationUtils.ADDED_COMPOUND_VARIABLE_TEXT,null);
    }

    public void handleInsertAnotherVarDecClick(ActionEvent event) {
        GlobalVariablesDeclarationModel globalVarDec = new GlobalVariablesDeclarationModel(NameGlobalVarDecTXT.getText(),
                TypeGlobalVarDecTXT.getText(),
                DefaultCodeGlobVarDecTXT.getText(),
                Boolean.parseBoolean(IsActionParameterValueTXT.getText()));

        envModel.addGlobalVarDec(globalVarDec);
        addGlobalVarDecToTree(globalVarDec);

        IsActionParameterValueTXT.setText("");
        NameGlobalVarDecTXT.setText("");
        TypeGlobalVarDecTXT.setText("");
        DefaultCodeGlobVarDecTXT.setText("");
        UtilsFXML.showNotification(NotificationUtils.ADDED_VARIABLE_DECLARATION_TITLE,NotificationUtils.ADDED_VARIABLE_DECLARATION_TEXT,null);
    }

    private void addGlobalVarDecToTree(GlobalVariablesDeclarationModel globalVarDec) {
        TreeItem<String> newItem = new TreeItem<>(globalVarDec.getName());
        newItem.getChildren().add(new TreeItem<>(globalVarDec.toString()));
        GlobalVarDecTreeView.getRoot().getChildren().add(newItem);
    }

    public void handleInsertAnotherAssClick(ActionEvent event){
        AssignmentBlock initBeliefModel = new AssignmentBlock(InitBeliefAssNameTXT.getText(),InitBeliefAssCodeTXT.getText());
        envModel.addInitBeliefAss(initBeliefModel);
        InitBeliefAssCodeTXT.setText("");
        InitBeliefAssNameTXT.setText("");
        addInitBeliefAssToTree(initBeliefModel);
        UtilsFXML.showNotification(NotificationUtils.ADDED_ASSIGNMENT_TITLE,NotificationUtils.ADDED_ASSIGMENT_TEXT,null);
    }

    private void addInitBeliefAssToTree(AssignmentBlock initBeliefModel) {
        TreeItem<String> newItem = new TreeItem<>(initBeliefModel.getAssignmentName());
        for(String code : initBeliefModel.getAssignmentCode())
            newItem.getChildren().add(new TreeItem<>(code));
        InitialBeliefStateAssTreeView.getRoot().getChildren().add(newItem);
    }

    public void handleInsertAnotherStateClick(ActionEvent event) {
        SpecialStateModel model = new SpecialStateModel(StateConditionCodeTXT.getText(),
                Double.parseDouble(RewardTXT.getText()),
                Boolean.parseBoolean(IsGoalStateBOX.getValue()),
                Boolean.parseBoolean(IsOneTimeRewardBOX.getValue()));
        envModel.addSpecialState(model);

        StateConditionCodeTXT.setText("");
        RewardTXT.setText("");
        IsGoalStateBOX.setValue("");
        IsOneTimeRewardBOX.setValue("");
        addSpecialStateToTree(model);
        UtilsFXML.showNotification(NotificationUtils.ADDED_STATE_TITLE,NotificationUtils.ADDED_STATE_TEXT,null);
    }

    private void addSpecialStateToTree(SpecialStateModel model) {
        TreeItem<String> newItem = new TreeItem<>("Special state");
        newItem.getChildren().add(new TreeItem<>(model.toString()));
        SpecialStatesTreeView.getRoot().getChildren().add(newItem);
    }

    public void handleInsertAnotherChangeClick(ActionEvent event) {
        AssignmentBlock model = new AssignmentBlock(AssignmentCodeChangeTXT.getText());
        envModel.addDynamicChange(model);
        AssignmentCodeChangeTXT.setText("");
        addExChangeDynModelToTree(model);
        UtilsFXML.showNotification(NotificationUtils.ADDED_CHANGE_TITLE,NotificationUtils.ADDED_CHANGE_TEXT,null);
    }

    private void addExChangeDynModelToTree(AssignmentBlock model) {
        TreeItem<String> newItem = new TreeItem<>("Ex change dynamic model");
        for(String code : model.getAssignmentCode())
            newItem.getChildren().add(new TreeItem<>(code));
        ExChangesDynModelTreeView.getRoot().getChildren().add(newItem);
    }

    public void handleCreateProjBTNClick(ActionEvent event) {
        addPlpAndEnvGeneralToModel();
        if(source== UtilsFXML.Source.EDIT_ENV){
            saveChangesToEnv();
        }
        else {
            createNewProject();
        }
    }

    private void createNewProject() {
        Response<EnvModel> response = facade.createNewProject(envModel);
        UtilsFXML.showNotification(NotificationUtils.CREATED_NEW_PROJECT_SUCCESS_TITLE,NotificationUtils.CREATED_NEW_PROJECT_SUCCESS_TEXT,response);
    }

    private void saveChangesToEnv() {
        Response<Boolean> response = facade.saveChangesToEnv(envModel);
        UtilsFXML.showNotification(NotificationUtils.SAVED_CHANGES_TO_ENV_TITLE,NotificationUtils.SAVED_CHANGES_TO_ENV_TEXT,response);
    }

    private void addPlpAndEnvGeneralToModel() {
        envModel.buildPlpMain(new PlpMainModel(ProjectNameTXT.getText(),EnvModel.PLP_NAME, EnvModel.PLP_TYPE));
        envModel.buildEnvGeneral(new EnvironmentGeneralModel(Integer.parseInt(HorizonTXT.getText()),Double.parseDouble(DiscountTXT.getText())));
    }

    public void handleBackBTNClick(ActionEvent event) {
        UtilsFXML.navToHome(event);
    }

    public void handleGlobalVarDecDeleteBTNClick(ActionEvent event) {
        TreeItem<String> val = GlobalVarDecTreeView.selectionModelProperty().getValue().getSelectedItem();
        if(GlobalVarDecTreeView.getRoot().getChildren().contains(val)) {
            String selected = val.getValue();
            envModel.setGlobalVariablesDeclaration(envModel.getGlobalVariablesDeclaration().
                    stream().filter(
                            (dec) -> !dec.getName().equals(selected)
                    ).toList());
            GlobalVarDecTreeView.getRoot().getChildren().remove(val);
            UtilsFXML.showNotification(NotificationUtils.DELETED_VAR_DECLARATION_TITLE, NotificationUtils.DELETED_VAR_DECLARATION_TEXT, null);
        }
        else{
            UtilsFXML.showErrorNotification(NotificationUtils.DELETE_VAR_DEC_TITLE_FAIL,NotificationUtils.DELETED_VAR_DEC_FAIL_TEXT);
        }
    }

    public void handleDeleteExChangeDynModelBTNClick(ActionEvent event) {
        TreeItem<String> val = ExChangesDynModelTreeView.selectionModelProperty().getValue().getSelectedItem();
        if(ExChangesDynModelTreeView.getRoot().getChildren().contains(val)) {
            int selected = ExChangesDynModelTreeView.getRoot().getChildren().indexOf(val);
            envModel.getExtrinsicChangesDynamicModel().remove(selected);
            ExChangesDynModelTreeView.getRoot().getChildren().remove(val);
            UtilsFXML.showNotification(NotificationUtils.DELETED_EX_CHANGE_DYN_MODEL_TITLE, NotificationUtils.DELETED_EX_CHANGE_DYN_MODEL_TEXT, null);
        }
        else
            UtilsFXML.showErrorNotification(NotificationUtils.DELETED_EX_CHANGE_DYN_MODEL_FAIL_TITLE, NotificationUtils.DELETED_EX_CHANGE_DYN_MODEL_FAIL_TEXT);
    }

    public void handleDeleteInitBeliefBTNClick(ActionEvent event) {
        TreeItem<String> val = InitialBeliefStateAssTreeView.selectionModelProperty().getValue().getSelectedItem();
        if(InitialBeliefStateAssTreeView.getRoot().getChildren().contains(val)) {
            String selected = val.getValue();
            envModel.setInitialBeliefStateAssignments(
                    envModel.getInitialBeliefStateAssignments().stream().filter(
                            (state) -> !state.getAssignmentName().equals(selected)
                    ).toList());
            InitialBeliefStateAssTreeView.getRoot().getChildren().remove(val);
            UtilsFXML.showNotification(NotificationUtils.DELETED_INIT_BELIEF_STMT_TITLE, NotificationUtils.DELETED_INIT_BELIEF_STMT_TEXT, null);
        }
        else{
            UtilsFXML.showErrorNotification(NotificationUtils.DELETED_INIT_BELIEF_STMT_FAIL_TITLE,NotificationUtils.DELETED_INIT_BELIEF_STMT_FAIL_TEXT);
        }
    }

    public void handleDeleteSpecialStatesBTNClick(ActionEvent event) {
        TreeItem<String> val = SpecialStatesTreeView.selectionModelProperty().getValue().getSelectedItem();
        if(SpecialStatesTreeView.getRoot().getChildren().contains(val)) {
            int selected = SpecialStatesTreeView.getRoot().getChildren().indexOf(val);
            envModel.getSpecialStates().remove(selected);
            SpecialStatesTreeView.getRoot().getChildren().remove(val);

            UtilsFXML.showNotification(NotificationUtils.DELETED_SPECIAL_STATE_TITLE, NotificationUtils.DELETED_SPECIAL_STATE_TEXT, null);
        }
        else{
            UtilsFXML.showErrorNotification(NotificationUtils.DELETED_SPECIAL_STATE_FAIL_TITLE,NotificationUtils.DELETED_SPECIAL_STATE_FAIL_TEXT);
        }
    }

    public void setSource(UtilsFXML.Source source) {
        this.source = source;
        createProjectBTN.setText("Save changes");
        titleLBL.setText("Edit Project");
    }

    public void setEnv(EnvModel value) {
        envModel = value;
        ProjectNameTXT.setText(envModel.getPlpMain().getProject());
        HorizonTXT.setText(String.valueOf(envModel.getEnvironmentGeneral().getHorizon()));
        DiscountTXT.setText(String.valueOf(envModel.getEnvironmentGeneral().getDiscount()));
        populateAllChoiceBoxesWithEnvValues();
    }

    private void populateAllChoiceBoxesWithEnvValues() {
        addAllGlobalVarTypesToTreeView();
        addAllGlobalVarDecToTreeView();
        addAllInitBeliefStmtToTreeView();
        addAllSpecialStatesToTreeView();
        addAllExChangesDynModelToTreeView();
    }

    private void addAllExChangesDynModelToTreeView() {
        for(AssignmentBlock model : envModel.getExtrinsicChangesDynamicModel())
            addExChangeDynModelToTree(model);
    }

    private void addAllSpecialStatesToTreeView() {
        for(SpecialStateModel model : envModel.getSpecialStates())
            addSpecialStateToTree(model);
    }

    private void addAllInitBeliefStmtToTreeView() {
        for(AssignmentBlock block : envModel.getInitialBeliefStateAssignments())
            addInitBeliefAssToTree(block);
    }

    private void addAllGlobalVarDecToTreeView() {
        for (GlobalVariablesDeclarationModel var : envModel.getGlobalVariablesDeclaration())
            addGlobalVarDecToTree(var);
    }

    private void addAllGlobalVarTypesToTreeView() {
        for(GlobalVariableTypeModel variableTypeModel : envModel.getGlobalVariableTypes()){
            this.currentGlobVarType = variableTypeModel;
            addTypeToTree();
        }
        this.currentGlobVarType = null;

    }

    public void handleDeleteSelectedGlobalVarTypeBTNClick(ActionEvent event) {
        TreeItem<String> val = GlobalVarTypesTreeView.selectionModelProperty().getValue().getSelectedItem();
        if(GlobalVarTypesTreeView.getRoot().getChildren().contains(val)) {
            String selected = val.getValue().endsWith("enum") ? val.getValue().substring(0,val.getValue().length()-7) : val.getValue().substring(0,val.getValue().length()-11);
            System.out.println(selected);
            envModel.setGlobalVariableTypes(
                    envModel.getGlobalVariableTypes().stream().filter(
                                    (type) -> !type.getTypeName().equals(selected))
                            .toList());
            GlobalVarTypesTreeView.getRoot().getChildren().remove(val);
            UtilsFXML.showNotification(NotificationUtils.DELETED_GLOBAL_VAR_TYPE_TITLE, NotificationUtils.DELETED_GLOBAL_VAR_TYPE_TEXT, null);
        }
        else{
            UtilsFXML.showErrorNotification(NotificationUtils.DELETED_GLOBAL_VAR_TYPE_FAIL_TITLE,NotificationUtils.DELETED_GLOBAL_VAR_TYPE_CHOOSE_GLOBAL_VAR_TEXT);
        }
    }
}
