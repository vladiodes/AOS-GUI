package frontend.finalproject.Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class CreateEnvController {

    @FXML
    private TextField NameTXT;
    @FXML
    private Label NameLBL;

    @FXML
    private Label TypeLBL;

    @FXML
    private TextField TypeTXT;

    @FXML
    private Label DefaultLBL;

    @FXML
    private TextField DefaultTXT;

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
        NameTXT.setVisible(!val);
        TypeLBL.setVisible(!val);
        TypeTXT.setVisible(!val);
        DefaultLBL.setVisible(!val);
        DefaultTXT.setVisible(!val);

    }

}
