package frontend.finalproject.Controllers;

import backend.finalproject.IAOSFacade;
import frontend.finalproject.Model.Env.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CreateEnvController {

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

    private GlobalVariableTypeModel currentGlobVarType = null;

    private IAOSFacade facade;


    @FXML
    public void initialize(){
        CompoundEnumChoiceBox.valueProperty().addListener(
                (observable, oldValue, newValue) -> {
            switch (newValue){
                case "compound":
                    makeEnumOptVisible(false);
                    break;
                case "enum":
                    makeEnumOptVisible(true);
                    break;
            }
        });
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
        Stage stage = new Stage();
        try{
            FXMLLoader loader = new FXMLLoader(CreateEnvController.class.getResource(UtilsFXML.PREVIEW_CODE_PATH));
            Parent root = loader.load();
            CodePreviewController controller = loader.getController();
            controller.setCode(generateJSON());
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }

    private String generateJSON(){
        envModel.buildPlpMain(new PlpMainModel(ProjectNameTXT.getText()));
        envModel.buildEnvGeneral(new EnvironmentGeneralModel(Integer.parseInt(HorizonTXT.getText()),Double.parseDouble(DiscountTXT.getText())));
        return envModel.toString();
    }

    public void handleInsertAnotherGlobalVarTypeClick(ActionEvent event) {
        if(currentGlobVarType!=null) {
            envModel.addGlobalVarType(currentGlobVarType);
            currentGlobVarType = null;
        }
        GlobalVarTypeNameTXT.setText("");
        makeAllVarInvisible(false);

    }

    public void handleInsertNextEnumValClick(ActionEvent event) {
        if(currentGlobVarType==null){
            currentGlobVarType = new GlobalVariableTypeEnumModel(GlobalVarTypeNameTXT.getText(),"enum");
        }
        GlobalVariableTypeEnumModel enumModel = (GlobalVariableTypeEnumModel)currentGlobVarType;
        enumModel.addEnumValue(enumValueTXT.getText());
        enumValueTXT.setText("");
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
    }

    public void handleInsertAnotherVarDecClick(ActionEvent event) {
        GlobalVariablesDeclarationModel globalVarDec = new GlobalVariablesDeclarationModel(NameGlobalVarDecTXT.getText(),
                TypeGlobalVarDecTXT.getText(),
                DefaultCodeGlobVarDecTXT.getText(),
                Boolean.parseBoolean(IsActionParameterValueTXT.getText()));

        envModel.addGlobalVarDec(globalVarDec);

        IsActionParameterValueTXT.setText("");
        NameGlobalVarDecTXT.setText("");
        TypeGlobalVarDecTXT.setText("");
        DefaultCodeGlobVarDecTXT.setText("");
    }

    public void handleInsertAnotherAssClick(ActionEvent event){
        InitialBeliefStateAssignmentModel initBeliefModel = new InitialBeliefStateAssignmentModel(InitBeliefAssNameTXT.getText(),InitBeliefAssCodeTXT.getText());
        envModel.addInitBeliefAss(initBeliefModel);
        InitBeliefAssCodeTXT.setText("");
        InitBeliefAssNameTXT.setText("");
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
    }

    public void handleInsertAnotherChangeClick(ActionEvent event) {
        ExtrinsicChangesDynamicModel model = new ExtrinsicChangesDynamicModel(AssignmentCodeChangeTXT.getText());
        envModel.addDynamicChange(model);
        AssignmentCodeChangeTXT.setText("");
    }
}
