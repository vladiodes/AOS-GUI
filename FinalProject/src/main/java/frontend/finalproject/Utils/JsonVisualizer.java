package frontend.finalproject.Utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import java.util.HashMap;
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
        return this.json.isBlank() ? new VBox() : displayJSON(this.json);
    }
    private Node displayJSON(String json){
        JsonElement jsonElement = toJson(json);
        if(jsonElement.isJsonPrimitive()){
            return displaySingleLine(jsonElement.getAsJsonPrimitive());
        }
        else if (jsonElement.isJsonArray()) {
            return displayJsonArray(jsonElement);
        } else {
            return displayJsonObject(jsonElement);
        }
    }

    private Node displayJsonObject(JsonElement jsonElement) {
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(10, 10, 10, 10));

        VBox vbox = new VBox();
        vbox.setSpacing(20);
        vbox.setPadding(new Insets(10, 10, 10, 10));

        for (Map.Entry<String, JsonElement> entry : jsonElement.getAsJsonObject().entrySet()) {
            extractJsonEntry(vbox, entry);
        }
        borderPane.setCenter(vbox);
        return borderPane;
    }

    private Node displayJsonArray(JsonElement jsonElement) {
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(10, 10, 10, 10));

        VBox vbox = new VBox();
        vbox.setSpacing(20);
        vbox.setPadding(new Insets(10, 10, 10, 10));

        for (JsonElement element : jsonElement.getAsJsonArray()) {
            if (element.isJsonPrimitive()) {
                extractSingleLine(vbox, element.getAsJsonPrimitive());
            } else if (element.isJsonObject()) {
                extractJsonObject(vbox, element);
            } else if (element.isJsonArray()) {
                extractJsonArray(vbox, element);
            }
        }
        borderPane.setCenter(vbox);
        return borderPane;
    }

    private void extractJsonArray(VBox vbox, JsonElement element) {
        VBox vbox1 = new VBox();
        vbox1.setSpacing(20);
        vbox1.setPadding(new Insets(10, 10, 10, 10));

        for (JsonElement element1 : element.getAsJsonArray()) {
            if (element1.isJsonPrimitive()) {
                extractSingleLine(vbox1, element1.getAsJsonPrimitive());
            } else if (element1.isJsonObject()) {
                extractJsonObject(vbox1, element1);
            } else if (element1.isJsonArray()) {
                extractJsonArray(vbox1, element1);
            }
        }
        vbox.getChildren().add(vbox1);
    }

    private void extractJsonObject(VBox vbox, JsonElement element) {
        VBox vbox1 = new VBox();
        vbox1.setSpacing(20);
        vbox1.setPadding(new Insets(10, 10, 10, 10));

        for (Map.Entry<String, JsonElement> entry : element.getAsJsonObject().entrySet()) {
            extractJsonEntry(vbox1, entry);
        }
        vbox.getChildren().add(vbox1);
    }

    private void extractJsonEntry(VBox vBox, Map.Entry<String, JsonElement> entry) {
        VBox keyValuePair = new VBox();
        keyValuePair.setAlignment(Pos.CENTER_LEFT);
        keyValuePair.setSpacing(5);

        Label titleLabel = new Label(entry.getKey());
        titleLabel.setFont(Font.font("Segoe UI", 20));
        titleLabel.setTextFill(Color.web("#0066cc"));
        keyValuePair.getChildren().add(titleLabel);

        JsonElement value = entry.getValue();
        if (value.isJsonObject()) {
            keyValuePair.getChildren().add(displayJsonObject(value));
        } else if (value.isJsonArray()) {
            keyValuePair.getChildren().add(displayJsonArray(value));
        } else {
            extractSingleLine(keyValuePair, value.getAsJsonPrimitive());
        }

        vBox.getChildren().add(keyValuePair);
    }

    private Node displaySingleLine(JsonPrimitive primitive) {
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(10, 10, 10, 10));

        VBox vbox = new VBox();
        vbox.setSpacing(20);
        vbox.setPadding(new Insets(10, 10, 10, 10));
        extractSingleLine(vbox, primitive);
        borderPane.setCenter(vbox);
        return borderPane;
    }

    private void extractSingleLine(VBox keyValuePair, JsonPrimitive primitive) {
        Label valueLabel = new Label(primitive.getAsString());
        valueLabel.setFont(Font.font("Segoe UI", 12));
        valueLabel.setTextFill(Color.web("#333333"));
        keyValuePair.getChildren().add(valueLabel);
    }

    private JsonElement toJson(String json) {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        return gson.fromJson(json, JsonElement.class);
    }

}
