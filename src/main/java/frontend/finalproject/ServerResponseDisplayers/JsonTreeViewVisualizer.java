package frontend.finalproject.ServerResponseDisplayers;

import com.google.gson.*;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.*;

public class JsonTreeViewVisualizer implements IJsonVisualizer {

    private String json;
    private TreeView<String> treeView;

    public JsonTreeViewVisualizer(String json) {
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
        if (jsonElement.isJsonArray()) {
            JsonArray array = jsonElement.getAsJsonArray();
            for (int i = 0; i < array.size(); i++) {
                JsonElement childElement = array.get(i);
                String childName = parent.getValue() == null ? "Element[" + i + "]" : parent.getValue().substring(0,parent.getValue().indexOf(" ")) + "[" + i + "]";
                TreeItem<String> child = new TreeItem<>(childName);
                parent.getChildren().add(child);
                populateTreeView(childElement, child);
            }
        } else if (jsonElement.isJsonObject()) {
            JsonObject object = jsonElement.getAsJsonObject();
            Set<Map.Entry<String, JsonElement>> entrySet = object.entrySet();
            for (Map.Entry<String, JsonElement> entry : entrySet) {
                String childName = entry.getKey();
                JsonElement childElement = entry.getValue();
                TreeItem<String> child;
                if (childElement.isJsonPrimitive()) {
                    String childValue = childElement.getAsString();
                    child = new TreeItem<>(childName + " : " + childValue);
                } else if (childElement.isJsonObject()) {
                    child = new TreeItem<>(childName + " : Object {}");
                    populateTreeView(childElement, child);
                }
                else{
                    child = new TreeItem<>(childName + " : List []");
                    populateTreeView(childElement, child);
                }
                parent.getChildren().add(child);
            }
        } else {
            String value = jsonElement.getAsString();
            parent.getChildren().add(new TreeItem<>(value));
        }
    }
}
