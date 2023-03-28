package frontend.finalproject.Controllers.SubControllers;

import frontend.finalproject.Controllers.UtilsFXML;
import frontend.finalproject.Model.Common.ImportCodeModel;
import frontend.finalproject.Model.Model;
import frontend.finalproject.NotificationUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Collections;

public class EditImportCodeController implements EditSubController {
    @FXML private Button editBTN;
    @FXML private TextArea ImportTXT;
    @FXML private TextField FromTXT;

    private Runnable callback;
    private ImportCodeModel model;


    public void handleEditBTNClick(ActionEvent actionEvent) {
        model.setFrom(FromTXT.getText());
        String[] impts = ImportTXT.getText().split("\n");
        ArrayList<String> imptsList = new ArrayList<>();
        Collections.addAll(imptsList, impts);
        model.setImport(imptsList);

        Stage stage = (Stage) editBTN.getScene().getWindow();
        stage.close();
        callback.run();
        UtilsFXML.showNotification(NotificationUtils.EDIT_IMPORT_CODE_SUCCESS_TITLE, NotificationUtils.EDIT_IMPORT_CODE_SUCCESS_TEXT,null);
    }

    @Override
    public void setModel(Model model) {
        setModel((ImportCodeModel) model);
    }

    private void setModel(ImportCodeModel model) {
        this.model = model;
        FromTXT.setText(model.getFrom());
        StringBuilder sb = new StringBuilder();
        for(String imp : model.getImport()) {
            sb.append(imp);
            sb.append("\n");
        }
        ImportTXT.setText(sb.toString());
    }

    @Override
    public void setCallback(Runnable r) {
        this.callback = r;
    }
}
