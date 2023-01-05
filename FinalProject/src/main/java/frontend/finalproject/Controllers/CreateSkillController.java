package frontend.finalproject.Controllers;

import frontend.finalproject.Model.AM.AMModel;
import frontend.finalproject.Model.Common.AssignmentBlock;
import frontend.finalproject.Model.SD.SDModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class CreateSkillController {
    @FXML private TextArea AssCodeSkillCodeRetValueTXT;
    @FXML private Button InsertAnotherImportCodeSkillCodeBTN;
    @FXML private Button InsertAnotherImportValueSkillCodeBTN;
    @FXML private TextField SkillCodeImportTXT;
    @FXML private TextField SkillCodeFromTXT;
    @FXML private TextField LocalVarNameRobotFrameWorkTXT;
    @FXML private TextField ROSTopicPathTXT;
    @FXML private Button InsertRobotFrameWorkParamBTN;
    @FXML private TextField RobotFrameworkFromTXT;
    @FXML private TextField RobotFrameworkImportTXT;
    @FXML private Button InsertAnotherImportValueRobotFrameworkBTN;
    @FXML private Button InsertAnotherImportCodeRobotFrameworkBTN;
    @FXML private TextArea AssCodeRobotFrameworkTXT;
    @FXML private TextField VarTypeRobotFrameworkTXT;
    @FXML private TextField InitialValueTXT;
    @FXML private TextField TopicMessageTypeTXT;
    @FXML private ChoiceBox<String> FromROSCBX;
    @FXML private Button InsertSkillCodeParamBTN;
    @FXML private TextField VarTypeSkillCodeTXT;
    @FXML private TextField LocalVarNameSkillCodeTXT;
    @FXML private Button InsertSDParamBTN;
    @FXML private TextField FromGlobalVarTXT;
    @FXML private TextField InputLocalVarTXT;
    @FXML private Button InsertAnotherServiceParamsBTN;
    @FXML private TextField AssignServiceFieldCodeTXT;
    @FXML private TextField ServiceFieldNameTXT;
    @FXML private TextField ServiceNameTXT;
    @FXML private TextField ServicePathTXT;
    @FXML private Button InsertAnotherImportCodeBTN;
    @FXML private Button InsertAnotherImportValueBTN;
    @FXML private TextField ImportTXT;
    @FXML private TextField FromTXT;
    @FXML private TextField ConditionCodeTXT;
    @FXML private TextField ResponseTXT;
    @FXML private TextField GlueFrameWorkTXT;
    @FXML private TextField AssignmentCodeDynamicModelTXT;
    @FXML private Button InsertAnotherDynamicModelBTN;
    @FXML private TextField AssignmentNameDynamicModelTXT;
    @FXML private TextField ViolatingPreconditionPenaltyTXT;
    @FXML private TextField AssignmentCodePlannerAssistancePreCondTXT;
    @FXML private Button InsertAnotherPlannerPrecondBTN;
    @FXML private TextField AssignmentNamePlannerAssistancePreCondTXT;
    @FXML private TextField AssignmentCodeGlobVarPreCondTXT;
    @FXML private Button InsertAnotherGlobVarPrecondBTN;
    @FXML private Button InsertAnotherGlobVarBTN;
    @FXML private TextField AssignmentNameGlobVarPreCondTXT;
    @FXML private TextField GlobalVarModuleParamTypeTXT;
    @FXML private TextField GlobalVarModuleParamNameTXT;
    @FXML private TextField SkillNameTXT;
    private String projectName;
    private SDModel SDmodel = new SDModel();
    private AMModel AMmodel = new AMModel();

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void handleInsertGlobalVarModuleParamSD(ActionEvent event) {
        SDmodel.addGlobalVariableModuleParameter(GlobalVarModuleParamNameTXT.getText(),GlobalVarModuleParamTypeTXT.getText());
        GlobalVarModuleParamNameTXT.setText("");
        GlobalVarModuleParamTypeTXT.setText("");
    }

    public void handleInsertGlobalVarPreconditionAssignment(ActionEvent event) {
        SDmodel.addGlobalVariablePreconditionAssignment(new AssignmentBlock(
                AssignmentNameGlobVarPreCondTXT.getText(),
                AssignmentCodeGlobVarPreCondTXT.getText()));
        AssignmentNameGlobVarPreCondTXT.setText("");
        AssignmentCodeGlobVarPreCondTXT.setText("");
    }

    public void handleInsertGlobalPlannerPrecondition(ActionEvent event) {
        SDmodel.addPlannerAssistancePreconditionsAssignment(new AssignmentBlock(
                AssignmentNamePlannerAssistancePreCondTXT.getText(),
                AssignmentCodePlannerAssistancePreCondTXT.getText()
        ));
        AssignmentNamePlannerAssistancePreCondTXT.setText("");
        AssignmentCodePlannerAssistancePreCondTXT.setText("");
    }


    public void handleInsertDynamicModel(ActionEvent event) {
        SDmodel.addDynamicModelAssignment(new AssignmentBlock(
                AssignmentNameDynamicModelTXT.getText(),
                AssignmentCodeDynamicModelTXT.getText()
        ));
        AssignmentNameDynamicModelTXT.setText("");
        AssignmentCodeDynamicModelTXT.setText("");
    }
}
