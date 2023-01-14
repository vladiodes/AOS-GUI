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

import java.util.concurrent.atomic.AtomicInteger;

public class CreateEnvController {

    @FXML private Label titleLBL;
    @FXML private Button createProjectBTN;
    @FXML private ChoiceBox<String> compoundVarsCBX;
    @FXML private ChoiceBox<String> enumValueCBX;
    @FXML private ChoiceBox<String> GlobalVarTypeCBX;
    @FXML private ChoiceBox<String> InitBeliefStmtCBX;
    @FXML private ChoiceBox<String> SpecialStatesCBX;
    @FXML private ChoiceBox<String> ExChangesDynModelCBX;
    @FXML private ChoiceBox<String> GlobalVarDecCBX;
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


    @FXML
    public void initialize(){
        CompoundEnumChoiceBox.valueProperty().addListener(
                getListenerForCompoundOrEnumCBX());

//        GlobalVarTypeCBX.valueProperty().addListener(
//                getListenerForGlobalVarTypeCBX()
//        );
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

    private ChangeListener<String> getListenerForGlobalVarTypeCBX() {
        return (observable, oldValue, newValue) -> {

            for (GlobalVariableTypeModel type : envModel.getGlobalVariableTypes()) {
                if (type.getTypeName().equals(newValue)) {
                    GlobalVarTypeNameTXT.setText(type.getTypeName());
                    if (type instanceof GlobalVariableTypeEnumModel enumModel) {
                        makeEnumOptVisible(true);
                        CompoundEnumChoiceBox.setValue("enum");
                        enumValueCBX.setItems(FXCollections.observableArrayList(enumModel.getEnumValues()));
                    } else {
                        makeEnumOptVisible(false);
                        GlobalVariableTypeCompoundModel compoundModel = (GlobalVariableTypeCompoundModel) type;
                        CompoundEnumChoiceBox.setValue("compound");
                        compoundVarsCBX.setItems(FXCollections.observableArrayList(
                                compoundModel.getVariables().stream().map(CompoundVariable::getName).toList()
                        ));
                    }
                }
            }
        };
    }

    private void makeEnumOptVisible(boolean val) {
        enumValueLBL.setVisible(val);
        nextValBTN.setVisible(val);
        enumValueTXT.setVisible(val);
        //enumValueCBX.setVisible(val);

        nextVarBTN.setVisible(!val);
        NameLBL.setVisible(!val);
        CompoundNameTXT.setVisible(!val);
        TypeLBL.setVisible(!val);
        CompoundTypeTXT.setVisible(!val);
        DefaultLBL.setVisible(!val);
        CompoundDefaultTXT.setVisible(!val);
        //compoundVarsCBX.setVisible(!val);
    }

    private void makeAllVarInvisible(boolean val) {
        enumValueLBL.setVisible(val);
        nextValBTN.setVisible(val);
        enumValueTXT.setVisible(val);
        //enumValueCBX.setVisible(val);

        nextVarBTN.setVisible(val);
        NameLBL.setVisible(val);
        CompoundNameTXT.setVisible(val);
        TypeLBL.setVisible(val);
        CompoundTypeTXT.setVisible(val);
        DefaultLBL.setVisible(val);
        CompoundDefaultTXT.setVisible(val);
        //compoundVarsCBX.setVisible(val);
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
            GlobalVarTypeCBX.getItems().add(currentGlobVarType.getTypeName());
            currentGlobVarType = null;
        }
        GlobalVarTypeNameTXT.setText("");
        makeAllVarInvisible(false);

        UtilsFXML.showNotification(NotificationUtils.ADDED_GLOBAL_VAR_NEW_TYPE_TITLE, NotificationUtils.ADDED_GLOBAL_VAR_NEW_TYPE_TEXT,null);

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
        GlobalVarDecCBX.getItems().add(globalVarDec.getName());

        IsActionParameterValueTXT.setText("");
        NameGlobalVarDecTXT.setText("");
        TypeGlobalVarDecTXT.setText("");
        DefaultCodeGlobVarDecTXT.setText("");
        UtilsFXML.showNotification(NotificationUtils.ADDED_VARIABLE_DECLARATION_TITLE,NotificationUtils.ADDED_VARIABLE_DECLARATION_TEXT,null);
    }

    public void handleInsertAnotherAssClick(ActionEvent event){
        AssignmentBlock initBeliefModel = new AssignmentBlock(InitBeliefAssNameTXT.getText(),InitBeliefAssCodeTXT.getText());
        envModel.addInitBeliefAss(initBeliefModel);
        InitBeliefAssCodeTXT.setText("");
        InitBeliefAssNameTXT.setText("");
        InitBeliefStmtCBX.getItems().add(initBeliefModel.getAssignmentName());
        UtilsFXML.showNotification(NotificationUtils.ADDED_ASSIGNMENT_TITLE,NotificationUtils.ADDED_ASSIGMENT_TEXT,null);
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
        SpecialStatesCBX.getItems().add(String.valueOf(SpecialStatesCBX.getItems().size() + 1));
        UtilsFXML.showNotification(NotificationUtils.ADDED_STATE_TITLE,NotificationUtils.ADDED_STATE_TEXT,null);
    }

    public void handleInsertAnotherChangeClick(ActionEvent event) {
        AssignmentBlock model = new AssignmentBlock(AssignmentCodeChangeTXT.getText());
        envModel.addDynamicChange(model);
        AssignmentCodeChangeTXT.setText("");
        ExChangesDynModelCBX.getItems().add(String.valueOf(ExChangesDynModelCBX.getItems().size() + 1));
        UtilsFXML.showNotification(NotificationUtils.ADDED_CHANGE_TITLE,NotificationUtils.ADDED_CHANGE_TEXT,null);
    }

    public void handleCreateProjBTNClick(ActionEvent event) {
        addPlpAndEnvGeneralToModel();
        if(source== UtilsFXML.Source.EDIT_ENV){
            //@TODO: Change this implementation
            System.out.println("Work in progress...");
        }
        else {
            if (facade.createNewProject(envModel).getValue() != null)
                UtilsFXML.showNotification(NotificationUtils.CREATED_NEW_PROJECT_SUCCESS_TITLE, NotificationUtils.CREATED_NEW_PROJECT_SUCCESS_TEXT, null);
            else {
                UtilsFXML.showNotification(NotificationUtils.CREATED_NEW_PROJECT_FAIL_TITLE, NotificationUtils.CREATED_NEW_PROJECT_FAIL_TEXT, null);
            }
        }
    }

    private void addPlpAndEnvGeneralToModel() {
        envModel.buildPlpMain(new PlpMainModel(ProjectNameTXT.getText(),EnvModel.PLP_NAME, EnvModel.PLP_TYPE));
        envModel.buildEnvGeneral(new EnvironmentGeneralModel(Integer.parseInt(HorizonTXT.getText()),Double.parseDouble(DiscountTXT.getText())));
    }

    public void handleBackBTNClick(ActionEvent event) {
        UtilsFXML.navToHome(event);
    }

    public void handleDeleteGlobalVarTypeBTNClick(ActionEvent event) {
        String selected = GlobalVarTypeCBX.selectionModelProperty().getValue().getSelectedItem();
        envModel.setGlobalVariableTypes(
                envModel.getGlobalVariableTypes().stream().filter(
                                (type) -> !type.getTypeName().equals(selected))
                        .toList());
        GlobalVarTypeCBX.setItems(FXCollections.observableArrayList(
                envModel.getGlobalVariableTypes().stream().map(
                        GlobalVariableTypeModel::getTypeName
                ).toList()
        ));
        GlobalVarTypeCBX.setValue("");
        UtilsFXML.showNotification(NotificationUtils.DELETED_GLOBAL_VAR_TYPE_TITLE,NotificationUtils.DELETED_GLOBAL_VAR_TYPE_TEXT,null);
    }

    public void handleGlobalVarDecDeleteBTNClick(ActionEvent event) {
        String selected = GlobalVarDecCBX.selectionModelProperty().getValue().getSelectedItem();
        envModel.setGlobalVariablesDeclaration(envModel.getGlobalVariablesDeclaration().
                stream().filter(
                        (dec) -> !dec.getName().equals(selected)
                ).toList());
        GlobalVarDecCBX.setItems(FXCollections.observableArrayList(
                envModel.getGlobalVariablesDeclaration().stream().map(
                        GlobalVariablesDeclarationModel::getName
                ).toList()
        ));
        GlobalVarDecCBX.setValue("");
        UtilsFXML.showNotification(NotificationUtils.DELETED_VAR_DECLARATION_TITLE,NotificationUtils.DELETED_VAR_DECLARATION_TEXT,null);
    }

    public void handleDeleteExChangeDynModelBTNClick(ActionEvent event) {
        Integer selected = Integer.valueOf(ExChangesDynModelCBX.selectionModelProperty().getValue().getSelectedItem());
        envModel.getExtrinsicChangesDynamicModel().remove(selected - 1);
        AtomicInteger i = new AtomicInteger(0);
        ExChangesDynModelCBX.setItems(FXCollections.observableArrayList(
                envModel.getExtrinsicChangesDynamicModel()
                        .stream().map(
                        (model) -> {
                            i.getAndIncrement();
                            return String.valueOf(i.get());
                        }
                ).toList()
        ));

        ExChangesDynModelCBX.setValue("");
        UtilsFXML.showNotification(NotificationUtils.DELETED_EX_CHANGE_DYN_MODEL_TITLE,NotificationUtils.DELETED_EX_CHANGE_DYN_MODEL_TEXT,null);
    }

    public void handleDeleteInitBeliefBTNClick(ActionEvent event) {
        String selected = InitBeliefStmtCBX.selectionModelProperty().getValue().getSelectedItem();
        envModel.setInitialBeliefStateAssignments(
                envModel.getInitialBeliefStateAssignments().stream().filter(
                        (state) -> !state.getAssignmentName().equals(selected)
                ).toList());
        InitBeliefStmtCBX.setItems(FXCollections.observableArrayList(
                envModel.getInitialBeliefStateAssignments().stream().map(
                        AssignmentBlock::getAssignmentName
                ).toList()
        ));
        InitBeliefStmtCBX.setValue("");
        UtilsFXML.showNotification(NotificationUtils.DELETED_INIT_BELIEF_STMT_TITLE,NotificationUtils.DELETED_INIT_BELIEF_STMT_TEXT,null);
    }

    public void handleDeleteSpecialStatesBTNClick(ActionEvent event) {
        Integer selected = Integer.valueOf(SpecialStatesCBX.selectionModelProperty().getValue().getSelectedItem());
        envModel.getSpecialStates().remove(selected-1);
        AtomicInteger i = new AtomicInteger(0);
        SpecialStatesCBX.setItems(FXCollections.observableArrayList(
                envModel.getSpecialStates()
                        .stream().map(
                                (model) -> {
                                    i.getAndIncrement();
                                    return String.valueOf(i.get());
                                }
                        ).toList()
        ));

        SpecialStatesCBX.setValue("");
        UtilsFXML.showNotification(NotificationUtils.DELETED_SPECIAL_STATE_TITLE,NotificationUtils.DELETED_SPECIAL_STATE_TEXT,null);
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
        GlobalVarTypeCBX.setItems(FXCollections.observableArrayList(
                envModel.getGlobalVariableTypes().stream().map(GlobalVariableTypeModel::getTypeName).toList()
        ));
        GlobalVarDecCBX.setItems(FXCollections.observableArrayList(
                envModel.getGlobalVariablesDeclaration().stream().map(GlobalVariablesDeclarationModel::getName).toList()
        ));
        InitBeliefStmtCBX.setItems(FXCollections.observableArrayList(
                envModel.getInitialBeliefStateAssignments().stream().map(AssignmentBlock::getAssignmentName).toList()
        ));
        AtomicInteger i = new AtomicInteger(0);
        SpecialStatesCBX.setItems(FXCollections.observableArrayList(
                envModel.getSpecialStates().stream().map(
                        (x) -> String.valueOf(i.incrementAndGet())
                ).toList()
        ));
        i.set(0);
        ExChangesDynModelCBX.setItems(FXCollections.observableArrayList(
                envModel.getExtrinsicChangesDynamicModel().stream().map(
                        (x) -> String.valueOf(i.incrementAndGet())
                ).toList()
        ));
    }
}
