package frontend.finalproject.ServerResponseDisplayers;

import com.google.gson.*;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;


public class SolverActionsVisualizer implements IJsonVisualizer{
    private static final String ACTION_ID = "actionID";
    public static final String ACTION_DESCRIPTION = "actionDescription";
    private String json;
    private TreeView<String> treeView;

    public SolverActionsVisualizer(String json) {
        this.json = json;
    }

    @Override
    public Node displayJSON() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonElement rootElement = gson.fromJson(json, JsonElement.class);
        treeView = new TreeView<>();
        treeView.setRoot(new TreeItem<>());
        treeView.setShowRoot(false);
        populateTreeView(rootElement, treeView.getRoot());
        return treeView;
    }

    private void populateTreeView(JsonElement jsonElement, TreeItem<String> parent) {
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
                JsonElement childElement = array.get(i);
                JsonObject childObject = childElement.getAsJsonObject();
                String actionDesc = childObject.get(ACTION_DESCRIPTION).getAsString();
                TreeItem<String> child = new TreeItem<>("Action Description: " + actionDesc);
                parent.getChildren().add(child);
            }
        }
    }
}
