package frontend.finalproject.Controllers.SubControllers;

import frontend.finalproject.Model.Common.AssignmentBlock;
import frontend.finalproject.Model.Env.SpecialStateModel;
import frontend.finalproject.Model.Model;
import frontend.finalproject.Utils.NotificationUtils;
import frontend.finalproject.Utils.UtilsFXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.List;

public class EditSpecialStateSubController implements EditSubController {

    private @FXML ChoiceBox<String> patternCustomChoiceBox;
    private @FXML TextArea StateConditionCodeTXT;
    private @FXML TextField RewardTXT;
    private @FXML ChoiceBox<String> IsGoalStateBOX;
    private @FXML ChoiceBox<String> IsOneTimeRewardBOX;
    private @FXML TextArea SpecialStateAssCodeTXT;
    private @FXML TextField SpecialStateAssNameTXT;
    private @FXML Button editBTN;

    private SpecialStateModel model;
    private Runnable callback;

    public void handleEditBTNClick(ActionEvent actionEvent) {
        String selected = patternCustomChoiceBox.selectionModelProperty().getValue().getSelectedItem();
        if (selected.equals("pattern")){
            model.setStateConditionCode(StateConditionCodeTXT.getText());
            model.setReward(Double.parseDouble(RewardTXT.getText()));
            model.setGoalState(IsGoalStateBOX.getValue().equals("true"));
            model.setOneTimeReward(IsOneTimeRewardBOX.getValue().equals("true"));
            model.setStateFunctionCode(null);
        }
        else {
            model.setStateConditionCode(null);
            model.setReward(null);
            model.setGoalState(null);
            model.setOneTimeReward(null);
            String AssName = SpecialStateAssNameTXT.getText().isEmpty() ? null: SpecialStateAssNameTXT.getText();
            AssignmentBlock block = new AssignmentBlock(AssName,SpecialStateAssCodeTXT.getText());
            model.setStateFunctionCode(List.of(block));
        }


        Stage stage = (Stage) editBTN.getScene().getWindow();
        stage.close();
        callback.run();
        UtilsFXML.showNotification(NotificationUtils.EDIT_SPECIAL_STATE_SUCCESS, NotificationUtils.EDIT_SPECIAL_STATE_SUCCESS_MSG,null);
    }

    @Override
    public void setModel(Model model) {
        setModel((SpecialStateModel) model);
    }

    public void setCallback(Runnable callback) {
        this.callback = callback;
    }

    private void setModel(SpecialStateModel model) {
        this.model = model;
        StateConditionCodeTXT.setText(model.getStateConditionCode());
        RewardTXT.setText(String.valueOf(model.getReward()));
        IsGoalStateBOX.selectionModelProperty().get().select(model.isGoalState() ? "true" : "false");
        IsOneTimeRewardBOX.selectionModelProperty().get().select(model.isOneTimeReward() ? "true" : "false");
    }
}
