package frontend.finalproject.ServerResponseDisplayers;

import DTO.HttpRequests.GetSolverActionsRequestDTO;
import backend.finalproject.AOSFacade;
import backend.finalproject.IAOSFacade;
import com.google.gson.*;
import javafx.scene.Node;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import utils.Response;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ManualActionsSentVisualizer implements IJsonVisualizer {
    public static final String ACTION_ID = "actionID";
    public static final String ACTION_DESCRIPTION = "actionDescription";
    Map<Integer,String> actionIDToActionName;
    private String json;
    private TreeView<String> treeView;

    public ManualActionsSentVisualizer(String json, IAOSFacade facade) {
        this.json = json;
        buildMap(facade);
    }

    private void buildMap(IAOSFacade facade) {
        actionIDToActionName = new HashMap<>();
        Response<String> availableActions = facade.sendRequest(new GetSolverActionsRequestDTO());
        if(!availableActions.hasErrorOccurred()){
            /*
            response json of the following struct:
            [
                {
                    "actionID": <int>,
                    "actionDescription": <string>
                },
                ...
            ]
             */
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonElement rootElement = gson.fromJson(availableActions.getValue(), JsonElement.class);
            if (rootElement.isJsonArray()) {
                JsonArray array = rootElement.getAsJsonArray();
                for (int i = 0; i < array.size(); i++) {
                    JsonElement childElement = array.get(i);
                    JsonObject childObject = childElement.getAsJsonObject();
                    int actionID = childObject.get(ACTION_ID).getAsInt();
                    String actionDescription = childObject.get(ACTION_DESCRIPTION).getAsString();
                    actionIDToActionName.put(actionID,actionDescription);
                }
            }
        }
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
                "actionID": <int>
            }
            ...
        ]
         */

        if (jsonElement.isJsonArray()) {
            JsonArray array = jsonElement.getAsJsonArray();
            for (int i = 0; i < array.size(); i++) {
                JsonElement childElement = array.get(i);
                JsonObject childObject = childElement.getAsJsonObject();
                int actionID = childObject.get(ACTION_ID).getAsInt();
                TreeItem<String> child;
                child = getActionDesc(actionID);

                parent.getChildren().add(child);
            }
        }
    }

    public TreeItem<String> getActionDesc(int actionID) {
        TreeItem<String> child;
        if(actionIDToActionName.containsKey(actionID))
            child = new TreeItem<>(actionIDToActionName.get(actionID));
        else if(actionID == -1)
            child = new TreeItem<>("Terminated");
        else
            child = new TreeItem<>("Not recognized Action ID" + " : " + actionID);
        return child;
    }

}
