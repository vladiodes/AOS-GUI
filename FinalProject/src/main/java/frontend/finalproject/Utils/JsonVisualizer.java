package frontend.finalproject.Utils;

import com.google.gson.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;


import java.util.ArrayList;
import java.util.List;

public class JsonVisualizer{

    public static final String EXPAND = "<expand>";
    public static final String KEY_COL_STYLE = "-fx-font-size: 16px; -fx-font-weight: bold; -fx-text-fill: #333333;";
    public static final String VAL_COL_STYLE = "-fx-font-size: 16px; -fx-text-fill: #333333;";
    public static final String TABLE_STYLE = "-fx-alignment: CENTER;";
    private final List<TableView<JsonTableEntry>> tables;


    public JsonVisualizer(String jsonString) {
        tables = new ArrayList<>();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonElement jsonElement = gson.fromJson(jsonString, JsonElement.class);
        createTables(jsonElement);
    }

    private void createTables(JsonElement jsonElement){
        if (!jsonElement.isJsonArray()) {
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            ObservableList<JsonTableEntry> data = FXCollections.observableArrayList();
            parseJsonObject(jsonObject, "", data);
            tables.add(initializeTable(data));
        }
        else{
            for (JsonElement element : jsonElement.getAsJsonArray()) {
                createTables(element);
            }
        }
    }

    public Node displayJSON() {
        //@TODO: Change later to display all tables
        return tables.get(0);
    }

    private TableView<JsonTableEntry> initializeTable(ObservableList<JsonTableEntry> data) {
        TableView<JsonTableEntry> table = new TableView<>();

        TableColumn<JsonTableEntry, String> keyColumn = new TableColumn<>("Key");
        keyColumn.setCellValueFactory(new PropertyValueFactory<>("key"));
        keyColumn.setPrefWidth(350);

        TableColumn<JsonTableEntry, String> valueColumn = new TableColumn<>("Value");
        valueColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        valueColumn.setPrefWidth(450);
        table.getColumns().clear();

        table.getColumns().add(keyColumn);
        table.getColumns().add(valueColumn);
        table.setOnMouseClicked(event -> {
                    if (event.getClickCount() == 2) {
                        JsonTableEntry entry = table.getSelectionModel().getSelectedItem();
                        if (entry.getValue().equals(EXPAND)) {
                            UtilsFXML.loadResponseStage(entry.getNestedValue());
                        }
                    }
                }
        );

        table.setItems(data);

        table.setPlaceholder(new Label("No data to display."));
        keyColumn.setStyle(KEY_COL_STYLE);
        valueColumn.setStyle(VAL_COL_STYLE);
        table.setStyle(TABLE_STYLE);

        return table;
    }

    private void parseJsonObject(JsonObject jsonObject, String parentKey, ObservableList<JsonTableEntry> data) {
        for (String key : jsonObject.keySet()) {
            JsonElement jsonElement = jsonObject.get(key);
            if (jsonElement.isJsonObject()) {
                data.add(new JsonTableEntry(parentKey + key, EXPAND, jsonElement.toString()));
            }
            else if(jsonElement.isJsonArray()){
                parseJsonArray(jsonElement.getAsJsonArray(), parentKey + key, data);
            }
            else {
                data.add(new JsonTableEntry(parentKey + key, jsonElement.getAsString()));
            }
        }
    }

    private void parseJsonArray(JsonArray jsonArray, String parentKey, ObservableList<JsonTableEntry> data) {
        for (JsonElement jsonElement : jsonArray) {
            if (jsonElement.isJsonObject()) {
                parseJsonObject(jsonElement.getAsJsonObject(), parentKey, data);
            }
            else if(jsonElement.isJsonArray()){
                parseJsonArray(jsonElement.getAsJsonArray(), parentKey, data);
            }
            else {
                data.add(new JsonTableEntry(parentKey, jsonElement.getAsString()));
            }
        }
    }

    public static class JsonTableEntry {
        private final SimpleStringProperty key;
        private final SimpleStringProperty value;
        private final String nestedValue;

        public JsonTableEntry(String key, String value) {
            this.key = new SimpleStringProperty(key);
            this.value = new SimpleStringProperty(value);
            this.nestedValue = "";
        }

        public JsonTableEntry(String key, String value, String nestedValue) {
            this.key = new SimpleStringProperty(key);
            this.value = new SimpleStringProperty(value);
            this.nestedValue = nestedValue;
        }

        public String getKey() {
            return key.get();
        }

        public void setKey(String key) {
            this.key.set(key);
        }

        public String getValue() {
            return value.get();
        }

        public void setValue(String value) {
            this.value.set(value);
        }

        public String getNestedValue() {
            return nestedValue;
        }
    }
}