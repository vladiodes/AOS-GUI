package frontend.finalproject.Utils;

import com.google.gson.Gson;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.List;
import java.util.Map;

/**
 * This class is responsible for displaying a JSON response in a readable and user-friendly way.
 */
public class JsonVisualizer {
    private String json;

    public JsonVisualizer(String json) {
        this.json = json;
    }

    public Node displayJSON() {
        return displayJSON(this.json);
    }

    private Node displayJSON(String json) {
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(10, 10, 10, 10));

        Map<String, Object> response = jsonToMap(json);

        VBox vbox = new VBox();
        vbox.setSpacing(20);
        vbox.setPadding(new Insets(10, 10, 10, 10));

        for (Map.Entry<String, Object> entry : response.entrySet()) {
            extractJsonEntry(vbox, entry);
        }
        borderPane.setCenter(vbox);
        return borderPane;
    }

    private void extractJsonEntry(VBox vbox, Map.Entry<String, Object> entry) {
        VBox keyValuePair = new VBox();
        keyValuePair.setAlignment(Pos.CENTER_LEFT);
        keyValuePair.setSpacing(5);

        Label titleLabel = new Label(entry.getKey());
        titleLabel.setFont(Font.font("Segoe UI", 20));
        titleLabel.setTextFill(Color.web("#0066cc"));
        keyValuePair.getChildren().add(titleLabel);

        Object value = entry.getValue();
        if (value instanceof Map) {
            keyValuePair.getChildren().add(displayJSON(new Gson().toJson(entry.getValue())));
        } else if (value instanceof List<?>) {
            extractJsonList(keyValuePair, (List<?>) value);
        }

        vbox.getChildren().add(keyValuePair);
    }

    private void extractJsonList(VBox keyValuePair, List<?> value) {
        for (Object str : value) {
            if (str instanceof Map || str instanceof List<?>) {
                keyValuePair.getChildren().add(displayJSON(new Gson().toJson(str)));
            } else {
                Label valueLabel = new Label(str.toString());
                valueLabel.setFont(Font.font("Segoe UI", 12));
                valueLabel.setTextFill(Color.web("#333333"));
                keyValuePair.getChildren().add(valueLabel);
            }
        }
    }

    private Map<String, Object> jsonToMap(String json) {
        Gson gson = new Gson();
        Map<String, Object> map = gson.fromJson(json, Map.class);
        return map;
    }
}
