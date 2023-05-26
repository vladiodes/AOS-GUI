package frontend.finalproject.Controllers;

import backend.finalproject.AOSFacade;
import backend.finalproject.IAOSFacade;
import frontend.finalproject.Controllers.SubControllers.AddSubController;
import frontend.finalproject.Controllers.SubControllers.AddVarTypeEnumController;
import frontend.finalproject.Model.Common.AssignmentBlock;
import frontend.finalproject.Model.Env.*;
import frontend.finalproject.Utils.NotificationUtils;
import frontend.finalproject.Utils.UtilsFXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Popup;
import javafx.stage.Stage;
import utils.Response;

import java.io.IOException;

import static frontend.finalproject.Utils.UtilsFXML.loadEditStage;

public class CreateEnvController {

    @FXML private TreeView<String> SpecialStatesTreeView;
    @FXML private TreeView<String> ExChangesDynModelTreeView;
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
    private EnvModel envModel = new EnvModel();

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
    private ChoiceBox<String> CompoundEnumChoiceBox;

    private UtilsFXML.Source source;
    private GlobalVariableTypeModel currentGlobVarType = null;

    private final IAOSFacade facade = AOSFacade.getInstance();


    @FXML
    public void initialize(){
        GlobalVarTypesTreeView.setRoot(new TreeItem<>("Global Variable Types"));
        GlobalVarDecTreeView.setRoot(new TreeItem<>("Global Variable Declarations"));
        InitialBeliefStateAssTreeView.setRoot(new TreeItem<>("Initial Belief State Assignments"));
        SpecialStatesTreeView.setRoot(new TreeItem<>("Special states"));
        ExChangesDynModelTreeView.setRoot(new TreeItem<>("Extrinsic Changes Dynamic Model"));
    }

    public void handlePreviewBTNClick(ActionEvent event) {
        UtilsFXML.handlePreviewBTNClick(event,generateJSON());
    }

    private String generateJSON(){
        addPlpAndEnvGeneralToModel();
        return facade.previewEnvJSON(envModel).getValue();
    }

    public void handleInsertAnotherGlobalVarTypeClick(ActionEvent event) {
        String selected = CompoundEnumChoiceBox.selectionModelProperty().getValue().getSelectedItem();
        if(selected == null)
            return;

        if(selected.equals("enum")) {
            loadSubStage(UtilsFXML.ADD_VAR_TYPE_PATH);
        }
        else{ // compound
            loadSubStage(UtilsFXML.ADD_VAR_TYPE_COMPOUND_PATH);
        }
    }

    private void loadSubStage(String fxml) {
        Stage stage = new Stage();
        try{
            FXMLLoader loader = new FXMLLoader(AddVarTypeEnumController.class.getResource(fxml));
            Parent root = loader.load();
            AddSubController controller = loader.getController();
            controller.setCallback(() -> {
                currentGlobVarType = (GlobalVariableTypeModel) controller.getModel();
                envModel.addGlobalVarType(currentGlobVarType);
                addTypeToTree();
            });

            stage.setScene(new Scene(root));
            stage.show();
        }
        catch (IOException e){
            e.printStackTrace();
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

    public void handleEditGlobalVarTypeClick(ActionEvent actionEvent) {
        TreeItem<String> selected = GlobalVarTypesTreeView.selectionModelProperty().getValue().getSelectedItem();
        if(GlobalVarTypesTreeView.getRoot().getChildren().contains(selected)) {
            if (selected.getValue().endsWith("enum")) {
                editGlobalVarEnumType(selected);
            }
            else{
                editGlobalVarCompoundType(selected);
            }
        }
        else{
            UtilsFXML.showErrorNotification(NotificationUtils.EDIT_GLOBAL_VAR_TYPE_FAIL_TITLE,NotificationUtils.EDIT_GLOBAL_VAR_TYPE_FAIL_TEXT);
        }
    }

    private void editGlobalVarCompoundType(TreeItem<String> selected) {

        String selectedType = selected.getValue().substring(0, selected.getValue().length() - 11);
        GlobalVariableTypeModel type = envModel.getGlobalVariableTypes().stream().filter((t) -> t.getTypeName().equals(selectedType)).findFirst().orElse(null);
        if (type != null) {
            loadEditStage(UtilsFXML.ADD_VAR_TYPE_COMPOUND_PATH, type, selected,
                    () -> {
                        currentGlobVarType = type;
                        GlobalVariableTypeCompoundModel compoundModel = (GlobalVariableTypeCompoundModel) currentGlobVarType;
                        selected.setValue(compoundModel.getTypeName() + " - compound");
                        selected.getChildren().clear();
                        for (CompoundVariable cv : compoundModel.getVariables())
                            selected.getChildren().add(new TreeItem<>(cv.toString()));
                    });
        }
    }

    private void editGlobalVarEnumType(TreeItem<String> selected) {
        String selectedType = selected.getValue().substring(0, selected.getValue().length() - 7);
        GlobalVariableTypeModel type = envModel.getGlobalVariableTypes().stream().filter((t) -> t.getTypeName().equals(selectedType)).findFirst().orElse(null);
        if (type != null) {
            loadEditStage(UtilsFXML.ADD_VAR_TYPE_PATH,type,selected,
                    () -> {
                        currentGlobVarType = type;
                        GlobalVariableTypeEnumModel enumType = (GlobalVariableTypeEnumModel)currentGlobVarType;
                        selected.setValue(enumType.getTypeName() + " - enum");
                        selected.getChildren().clear();
                        for(String enumVal : enumType.getEnumValues())
                            selected.getChildren().add(new TreeItem<>(enumVal));
                    });
        }
    }

    public void handledEditGlobalVarDecEditBTNClick(ActionEvent actionEvent) {
        TreeItem<String> selectedItem = GlobalVarDecTreeView.selectionModelProperty().getValue().getSelectedItem();
        if (GlobalVarDecTreeView.getRoot().getChildren().contains(selectedItem)) {
            GlobalVariablesDeclarationModel model = envModel.getGlobalVariablesDeclaration()
                    .stream().filter((var) -> var.getName().equals(selectedItem.getValue())).findFirst().orElse(null);
            loadEditStage(UtilsFXML.EDIT_GLOBAL_VAR_DEC_PATH,model,selectedItem,
                    () -> {
                        assert model != null;
                        selectedItem.setValue(model.getName());
                        selectedItem.getChildren().remove(0);
                        selectedItem.getChildren().add(new TreeItem<>(model.toString()));
                    });
        }
        else{
            UtilsFXML.showErrorNotification(NotificationUtils.EDIT_GLOBAL_VAR_DEC_FAIL_TITLE,NotificationUtils.EDIT_GLOBAL_VAR_DEC_FAIL_TEXT);
        }
    }

    public void handleEditInitBeliefBTNClick(ActionEvent actionEvent) {
        TreeItem<String> selectedItem = InitialBeliefStateAssTreeView.selectionModelProperty().getValue().getSelectedItem();
        if (InitialBeliefStateAssTreeView.getRoot().getChildren().contains(selectedItem)) {
            AssignmentBlock model = envModel.getInitialBeliefStateAssignments()
                    .stream().filter((var) -> var.getAssignmentName().equals(selectedItem.getValue())).findFirst().orElse(null);
            loadEditStage(UtilsFXML.EDIT_ASS_CODE_PATH,model,selectedItem,
                    () -> {
                        assert model != null;
                        selectedItem.setValue(model.getAssignmentName());
                        selectedItem.getChildren().setAll(model.getAssignmentCode().stream().map(TreeItem::new).toList());
                    });
        }
        else{
            UtilsFXML.showErrorNotification(NotificationUtils.EDIT_INIT_BELIEF_FAIL_TITLE,NotificationUtils.EDIT_INIT_BELIEF_FAIL_TEXT);
        }
    }


    public void handleEditStateBTNClick(ActionEvent actionEvent) {
        TreeItem<String> selectedItem = SpecialStatesTreeView.selectionModelProperty().getValue().getSelectedItem();
        if (SpecialStatesTreeView.getRoot().getChildren().contains(selectedItem)) {
            SpecialStateModel model = envModel.getSpecialStates().get(SpecialStatesTreeView.getRoot().getChildren().indexOf(selectedItem));
            loadEditStage(UtilsFXML.EDIT_STATE_PATH, model,selectedItem,
                        () -> {
                            selectedItem.getChildren().remove(0);
                            selectedItem.getChildren().add(new TreeItem<>(model.toString()));
                        }
                    );
        }
        else{
            UtilsFXML.showErrorNotification(NotificationUtils.EDIT_STATE_FAIL_TITLE,NotificationUtils.EDIT_STATE_FAIL_TEXT);
        }
    }

    public void handleExChangeEditBTNClick(ActionEvent actionEvent) {
        TreeItem<String> selectedItem = ExChangesDynModelTreeView.selectionModelProperty().getValue().getSelectedItem();
        if (ExChangesDynModelTreeView.getRoot().getChildren().contains(selectedItem)) {
            AssignmentBlock model = envModel.getExtrinsicChangesDynamicModel().get(ExChangesDynModelTreeView.getRoot().getChildren().indexOf(selectedItem));
            loadEditStage(UtilsFXML.EDIT_ASS_CODE_PATH, model,selectedItem,
                    () -> {
                        selectedItem.getChildren().setAll(model.getAssignmentCode().stream().map(TreeItem::new).toList());
                    }
            );
        }
        else{
            UtilsFXML.showErrorNotification(NotificationUtils.EDIT_EXCHANGE_FAIL_TITLE,NotificationUtils.EDIT_EXCHANGE_FAIL_TEXT);
        }
    }

    public void plpMainInfo(ActionEvent actionEvent) {
        popUpWindow("PlpMain", "header section that each documentation file contains.");
    }
    public void projectInfo(ActionEvent actionEvent) {
        popUpWindow("Project", "field of type string. It describes the project name, all documentation files in the project must have the same name (e.g., \"cleaning_robot1\").");
    }
    public void envGeneralInfo(ActionEvent actionEvent) {
        popUpWindow("EnvironmentGeneral", "describes the general parameters of the planning problem.");
    }
    public void horizonInfo(ActionEvent actionEvent) {
        popUpWindow("Horizon", "Field of type integer.\n It describes how many forward steps the AOS considers when it decides on an action. On sampling algorithms, a longer horizon means fewer trajectories sampled on the same planning period, so it is better to specify an exact number; remember that if the horizon is too small, the robot cannot simulate reaching the goal.");
    }
    public void discountInfo(ActionEvent actionEvent) {
        popUpWindow("Discount", "field of type decimal. Legal values are in the range of (1,0). It defines the discount factor on the value of future rewards. For values close to 1, we don't care so much about when we receive a reward; on the other hand, for values close to 0, we only care about rewards received in the next step, ignoring the future. In robotic domains, where each action has a cost, a value close to 1 is recommended (e.g., 0.9999).");
    }
    public void globalVariableTypeInfo(ActionEvent actionEvent) {
        popUpWindow("GlobalVariableTypes", "The supported state variable types are the primitive C++ types (e.g., string, int, float). The \"GlobalVariableTypes\" section enables users to define custom state variable types. More specifically, enums and compound data structures that aggregate multiple data items.\n" +
                "After a type is defined, the user may use it to define a state variable.");
    }
    public void globalVarTypeTypeInfo(ActionEvent actionEvent) {
        popUpWindow("Type", "compund/enum. After a type is defined, the user may use it to define a state variable.");
    }
    public void globalVarDeclInfo(ActionEvent actionEvent) {
        popUpWindow("GlobalVariablesDeclaration", "defines the state variables and possible action parameters.\n" +
                "The field is an array of defined types and action parameters");
    }
    public void globalVarDecNameInfo(ActionEvent actionEvent) {
        popUpWindow("Name", "type string that defines the variables name");
    }
    public void globalVarDecTypeInfo(ActionEvent actionEvent) {
        popUpWindow("Type", "type string that defines the state variable types. Supported types are the C++ primitive types (e.g., int, float, double, string, char, bool) and custom types (defined in the GlobalVariableTypes section).");
    }
    public void isActionParamInfo(ActionEvent actionEvent) {
        popUpWindow("IsActionParameter", "type boolean (can take 'true' or 'false' values). It determines if this item is an action parameter. Action parameters are values the AOS can send as skill parameters. A Skill that takes a parameter of type int and a parameter of type string can receive any combination of action parameter values defined from type int and string. Suppose a user has two skills that take a parameter of the same type but have different possible values. In that case, the user should define a custom type that will wrap the skill parameters, and the skill will receive a parameter of that type.");
    }
    public void defaultCodeInfo(ActionEvent actionEvent) {
        popUpWindow("DefaultCode", "type string can be used to initialize the variable. This field value contains C++ code. the variable is referred to as 'state.' where is replaced by the variable \"Name\".");
    }
    public void initBeliefStateAssignInfo(ActionEvent actionEvent) {
        popUpWindow("InitialBeliefStateAssignments", "In many robotic domains, the initial state is unknown, and the robot should reason about this uncertainty. This section allows the user to define the uncertainty about the initial state using code. He can use the AOS extensions to sample from known distributions (Bernoulli, Discrete or Normal, see).\n" +
                "The state variables can be referred to and changed using 'state.variable_name'.\n" +
                "More technically, the AOS generates a particle set to represent the distribution of the initial belief state; each particle is a fully initialized state, and its values are taken from the default state variable value and sampled from this code section.");
    }
    public void assignmentNameInfo(ActionEvent actionEvent) {
        popUpWindow("Assignments blocks", "There are a few sections of Assignments blocks like \"InitialBeliefStateAssignments\" they all have the same syntax.\n" +
                "An Assignments block is an array of assignments. Each assignment item may have an \"AssignmentName\" for readability, and it must have an \"AssignmentCode\" field which is a string (a single code line) or an array of strings such that each string is a code line.");
    }
    public void assignmentCodeInfo(ActionEvent actionEvent) {
        popUpWindow("Assignments blocks", "There are a few sections of Assignments blocks like \"InitialBeliefStateAssignments\" they all have the same syntax.\n" +
                "An Assignments block is an array of assignments. Each assignment item may have an \"AssignmentName\" for readability, and it must have an \"AssignmentCode\" field which is a string (a single code line) or an array of strings such that each string is a code line.");
    }
    public void specialStatesInfo(ActionEvent actionEvent) {
        popUpWindow("SpecialStates", "The \"SpecialStates\" section uses to define desired or undesired states. The robot's goal is to maximize the expected discounted reward. This section allows the engineer to define desired or undesired states (e.g., \"the drone should be balanced,\" \"hitting a wall is terrible\"). It can also define landmarks in the form of states the given a one-time reward the first time a state is reached (unlike \"hitting a wall is terrible\"). Moreover, the user can define goal rewards; these are termination conditions for the robot that ends its current operation (the robot will stop if its current belief distribution indicates a terminal state with a mean of more than 0.9 and a standard deviation of less than 0.1).");
    }
    public void stateConditionCodeInfo(ActionEvent actionEvent) {
        popUpWindow("StateConditionCode", "string code line that defines the condition indicating that we reached the state. The condition is defined over some or all state variable assignments, and it can only be defined in a single line (use lambda expressions for conditions that require iterating over multiple variables).");
    }

    public void rewardInfo(ActionEvent actionEvent) {
        popUpWindow("Reward", " decimal field indicating the reward given when the condition is met.");
    }
    public void isGoalStateInfo(ActionEvent actionEvent) {
        popUpWindow("IsGoalState", "boolean field to define if it is a terminal state (default value is false).");
    }
    public void isOneTimeRewardInfo(ActionEvent actionEvent) {
        popUpWindow("IsOneTimeReward", "boolean field to define if the reward will be given only one time or multiple times (default value is false). ");
    }
    public void extrinsicChangesDynamicModelInfo(ActionEvent actionEvent) {
        popUpWindow("ExtrinsicChangesDynamicModel", "This section uses to define extrinsic changes. These are changes that the robot did not invoke by its skills. For example, let's say there is a probability that it will start to rain, making the floor wet and making it harder to navigate. The agent did not invoke the rain, but it affected the robot's decisions.");
    }

    private void popUpWindow(String title, String text){
        Stage stage = new Stage();
        Label infoLabel = new Label(text);
        // set the style of infoLabel to present all the text in multiple lines if needed
        infoLabel.setStyle("-fx-text-fill: white; -fx-font-size: 15px; -fx-font-weight: bold; -fx-padding: 10px; -fx-text-alignment: center; -fx-background-color: #2a2a2a; -fx-background-radius: 10px;");
        // set the text to be wrapped if it is too long
        infoLabel.setWrapText(true);
        // set the width of the label to be 400
        infoLabel.setPrefWidth(400);

        // set the scene of the stage to be the infoLabel
        stage.setScene(new Scene(infoLabel));
        // set the title of the stage
        stage.setTitle(title);
        // show the stage
        stage.show();
    }

}
