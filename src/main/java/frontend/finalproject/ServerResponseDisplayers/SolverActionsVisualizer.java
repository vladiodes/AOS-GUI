package frontend.finalproject.ServerResponseDisplayers;

import com.google.gson.*;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.HashMap;


public class SolverActionsVisualizer implements IJsonVisualizer{
    public static final String ACTION_DESCRIPTION = "actionDescription";
    public static final String ACTION_INFO_DESC = "Double click on an action to send to the robot";
    public static final String INFO_LABEL = "Info_Label";
    public static final String FILTER_ACTIONS = "Filter actions";
    public static final String TEXT_FIELD_FORM = "TextFieldForm";
    public static final String TEXT_FIELD_LABEL = "TextFieldLabel";
    public static final String CENTER_ALIGN_CLASS = "TreeBoxWrapper";
    private final String json;
    private TreeView<String> treeView;
    private final HashMap<TreeItem<String>,Integer> allActions = new HashMap<>();
    private final OnDoubleClickCallback callback;

    public SolverActionsVisualizer(String json, OnDoubleClickCallback callback) {
        this.json = json;
        this.callback = callback;
    }

    @Override
    public Node displayJSON() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonElement rootElement = gson.fromJson(json, JsonElement.class);
        populateActionsList(rootElement);

        treeView = new TreeView<>();
        treeView.setRoot(new TreeItem<>());
        treeView.setShowRoot(false);
        treeView.getRoot().getChildren().addAll(allActions.keySet());

        VBox root = new VBox();
        root.getStyleClass().add(CENTER_ALIGN_CLASS);
        root.setSpacing(10);

        Label infoLabel = new Label(ACTION_INFO_DESC);
        infoLabel.getStyleClass().add(INFO_LABEL);

        HBox filterContainer = new HBox();
        TextField filterTextField = new TextField();
        Label filterLabel = new Label(FILTER_ACTIONS);
        filterTextField.getStyleClass().add(TEXT_FIELD_FORM);
        filterLabel.getStyleClass().add(TEXT_FIELD_LABEL);
        filterContainer.getChildren().addAll(filterLabel, filterTextField);

        filterTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            treeView.getRoot().getChildren().clear();
            for(TreeItem<String> action : allActions.keySet()){
                if(action.getValue().contains(newValue)){
                    treeView.getRoot().getChildren().add(action);
                }
            }
        });

        root.getChildren().addAll(treeView, infoLabel,filterContainer);

        return root;
    }

    private void populateActionsList(JsonElement jsonElement) {
        // json element should be an array of the following struct:
        /*
        [
            {
                "actionID": <int>,
                "actionDescription": <string>
            }
            ...
        ]
         */
        if (jsonElement.isJsonArray()) {
            JsonArray array = jsonElement.getAsJsonArray();
            for (int i = 0; i < array.size(); i++) {
                int actionID = array.get(i).getAsJsonObject().get("actionID").getAsInt();
                JsonElement childElement = array.get(i);
                JsonObject childObject = childElement.getAsJsonObject();
                String actionDesc = childObject.get(ACTION_DESCRIPTION).getAsString();
                TreeItem<String> child = new TreeItem<>("Action Description: " + actionDesc);
                allActions.put(child,actionID);
            }
            allActions.put(new TreeItem<>("Terminate action"),-1);
        }
    }

    public void setOnActionSentCallback(Runnable onActionSentCallback) {
        treeView.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY && event.getClickCount() == 2) {
                TreeItem<String> selectedItem = treeView.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    int actionID = allActions.get(selectedItem);
                    callback.onDoubleClick(actionID);
                    if(onActionSentCallback != null)
                        onActionSentCallback.run();
                }
            }
        });

    }

    public interface OnDoubleClickCallback{
        void onDoubleClick(int actionID);
    }
}
