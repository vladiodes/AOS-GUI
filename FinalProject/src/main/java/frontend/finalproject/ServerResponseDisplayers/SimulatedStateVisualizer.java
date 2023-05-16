package frontend.finalproject.ServerResponseDisplayers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.LinkedList;
import java.util.List;

public class SimulatedStateVisualizer implements IJsonVisualizer {
    private List<JsonElement> simulatedStates;
    private int currentSimulatedStateIndex = 0;

    public SimulatedStateVisualizer(String json) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonElement jsonElement = gson.fromJson(json, JsonElement.class);
        simulatedStates = new LinkedList<>();
        if (jsonElement.isJsonArray()) {
            for (JsonElement element : jsonElement.getAsJsonArray()) {
                simulatedStates.add(element);
            }
        }
    }

    @Override
    public Node displayJSON() {
        VBox vBox = new VBox();
        final Node[] curState = {new JsonTreeViewVisualizer(simulatedStates.get(currentSimulatedStateIndex).toString()).displayJSON()};
        HBox hBox = new HBox();
        Button prev = new Button();
        prev.setText("Previous State");
        Button next = new Button();
        next.setText("Next State");
        Button display = new Button("Display state");
        hBox.getChildren().addAll(prev, next, display);
        vBox.getChildren().addAll(curState[0], hBox);
        prev.setOnAction(e -> {
            if (currentSimulatedStateIndex > 0) {
                currentSimulatedStateIndex--;
                vBox.getChildren().remove(curState[0]);
                curState[0] = new JsonTreeViewVisualizer(simulatedStates.get(currentSimulatedStateIndex).toString()).displayJSON();
                vBox.getChildren().add(0, curState[0]);
            }
        });
        next.setOnAction(e -> {
            if (currentSimulatedStateIndex < simulatedStates.size() - 1) {
                currentSimulatedStateIndex++;
                vBox.getChildren().remove(curState[0]);
                curState[0] = new JsonTreeViewVisualizer(simulatedStates.get(currentSimulatedStateIndex).toString()).displayJSON();
                vBox.getChildren().add(0, curState[0]);
            }
        });

        prev.getStyleClass().add("formBtn");
        next.getStyleClass().add("formBtn");
        display.getStyleClass().add("formBtn");
        hBox.getStyleClass().add("TreeBoxWrapper");

        hBox.setSpacing(10);
        vBox.setSpacing(20);

        return vBox;
    }
}
