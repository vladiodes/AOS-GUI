package frontend.finalproject.ServerResponseDisplayers;

import com.google.gson.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;


public class LogsDisplayer implements IJsonVisualizer {

    public static final String ID_JSON_KEY = "id";
    public static final String LOG_LEVEL_JSON_KEY = "logLevel";
    public static final String LOG_LEVEL_DESC_JSON_KEY = "logLevelDesc";
    public static final String EVENT_JSON_KEY = "event";
    public static final String ADVANCED_JSON_KEY = "advanced";
    public static final String COMPONENT_JSON_KEY = "component";
    public static final String TIME_JSON_KEY = "time";
    public static final String ID_COL_TITLE = "Id";
    public static final String LOG_LEVEL_COL = "Log Level";
    public static final String LOG_LEVEL_DESCRIPTION_COL_TITLE = "Log Level Description";
    public static final String EVENT_COL_TITLE = "Event";
    public static final String ADVANCED_COL_TITLE = "Advanced";
    public static final String COMPONENT_COL_TITLE = "Component";
    public static final String TIME_COL_TITLE = "Time";
    private TableView<LogEntry> tableView;

    public LogsDisplayer(String logsJson) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonElement jsonElement = gson.fromJson(logsJson, JsonElement.class);
        createTable(jsonElement.getAsJsonArray());
    }

    private void createTable(JsonArray jsonArray) {

        ObservableList<LogEntry> data = FXCollections.observableArrayList();
        for(int i = jsonArray.size() - 1; i>=0;i--){
            JsonObject log = jsonArray.get(i).getAsJsonObject();
            String id = log.get(ID_JSON_KEY).getAsString();
            String logLevel = log.get(LOG_LEVEL_JSON_KEY).getAsString();
            String logLevelDesc = log.get(LOG_LEVEL_DESC_JSON_KEY).getAsString();
            String event = log.get(EVENT_JSON_KEY).getAsString();
            String advanced = log.get(ADVANCED_JSON_KEY).getAsString();
            String component = log.get(COMPONENT_JSON_KEY).getAsString();
            String time = log.get(TIME_JSON_KEY).getAsString();

            data.add(new LogEntry(id, logLevel, logLevelDesc, event, advanced, component, time));
        }

        tableView = new TableView<>();
        TableColumn<LogEntry, String> idCol = new TableColumn<>(ID_COL_TITLE);
        idCol.setCellValueFactory(new PropertyValueFactory<>(ID_JSON_KEY));

        TableColumn<LogEntry, String> logLevelCol = new TableColumn<>(LOG_LEVEL_COL);
        logLevelCol.setCellValueFactory(new PropertyValueFactory<>(LOG_LEVEL_JSON_KEY));

        TableColumn<LogEntry,String> logLevelDescCol = new TableColumn<>(LOG_LEVEL_DESCRIPTION_COL_TITLE);
        logLevelDescCol.setCellValueFactory(new PropertyValueFactory<>(LOG_LEVEL_DESC_JSON_KEY));

        TableColumn<LogEntry, String> eventCol = new TableColumn<>(EVENT_COL_TITLE);
        eventCol.setCellValueFactory(new PropertyValueFactory<>(EVENT_JSON_KEY));

        TableColumn<LogEntry, String> advancedCol = new TableColumn<>(ADVANCED_COL_TITLE);
        advancedCol.setCellValueFactory(new PropertyValueFactory<>(ADVANCED_JSON_KEY));

        TableColumn<LogEntry, String> componentCol = new TableColumn<>(COMPONENT_COL_TITLE);
        componentCol.setCellValueFactory(new PropertyValueFactory<>(COMPONENT_JSON_KEY));

        TableColumn<LogEntry, String> timeCol = new TableColumn<>(TIME_COL_TITLE);
        timeCol.setCellValueFactory(new PropertyValueFactory<>(TIME_JSON_KEY));

        tableView.getColumns().add(idCol);
        tableView.getColumns().add(logLevelCol);
        tableView.getColumns().add(logLevelDescCol);
        tableView.getColumns().add(eventCol);
        tableView.getColumns().add(advancedCol);
        tableView.getColumns().add(componentCol);
        tableView.getColumns().add(timeCol);

        tableView.setItems(data);
    }

    @Override
    public Node displayJSON() {
        return this.tableView;
    }

    public static class LogEntry {
        private SimpleStringProperty id;
        private SimpleStringProperty logLevel;
        private SimpleStringProperty logLevelDesc;
        private SimpleStringProperty event;
        private SimpleStringProperty advanced;
        private SimpleStringProperty component;
        private SimpleStringProperty time;

        public LogEntry(String id, String logLevel, String logLevelDesc, String event, String advanced,
                        String component, String time) {
            this.id = new SimpleStringProperty(id);
            this.logLevel = new SimpleStringProperty(logLevel);
            this.logLevelDesc = new SimpleStringProperty(logLevelDesc);
            this.event = new SimpleStringProperty(event);
            this.advanced = new SimpleStringProperty(advanced);
            this.component = new SimpleStringProperty(component);
            this.time = new SimpleStringProperty(time);
        }

        public void setId(String id) {
            this.id.set(id);
        }

        public String getId() {
            return id.get();
        }

        public void setLogLevel(String logLevel) {
            this.logLevel.set(logLevel);
        }

        public String getLogLevel() {
            return logLevel.get();
        }

        public void setLogLevelDesc(String logLevelDesc) {
            this.logLevelDesc.set(logLevelDesc);
        }

        public String getLogLevelDesc() {
            return logLevelDesc.get();
        }

        public void setEvent(String event) {
            this.event.set(event);
        }

        public String getEvent() {
            return event.get();
        }

        public void setAdvanced(String advanced) {
            this.advanced.set(advanced);
        }

        public String getAdvanced() {
            return advanced.get();
        }

        public void setComponent(String component) {
            this.component.set(component);
        }

        public String getComponent() {
            return component.get();
        }

        public void setTime(String time) {
            this.time.set(time);
        }

        public String getTime() {
            return time.get();
        }
    }
}
