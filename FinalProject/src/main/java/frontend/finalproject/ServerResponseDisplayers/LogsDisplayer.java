package frontend.finalproject.ServerResponseDisplayers;

import DTO.HttpRequests.GetLogsRequestDTO;
import backend.finalproject.AOSFacade;
import com.google.gson.*;
import frontend.finalproject.Utils.LogsNotificationsFetcher;
import frontend.finalproject.Utils.NotificationUtils;
import frontend.finalproject.Utils.UtilsFXML;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import utils.Response;


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
    public static final String CENTER_ALIGN_STYLE = "TreeBoxWrapper";
    public static final String BUTTON_STYLE_CLASS = "formBtn";
    public static final String LABEL_STYLE = "TextFieldLabel";
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
        VBox root = new VBox();

        Button clearLogs = new Button("Clear Logs");
        clearLogs.setOnAction((e) -> {
            Response<String> response = AOSFacade.getInstance().sendRequest(new GetLogsRequestDTO.DelLogsRequestDTO());
            if(!response.hasErrorOccurred()){
                UtilsFXML.showNotification(NotificationUtils.LOGS_CLEARED_TITLE,NotificationUtils.LOGS_CLEARED_TXT,null);
                tableView.getColumns().clear();
            }
            else{
                UtilsFXML.showErrorNotification(NotificationUtils.ERROR_SENDING_REQUEST_TITLE,NotificationUtils.ERROR_SENDING_REQUEST_TITLE);
            }
        });


        HBox buttonsContainer = new HBox();
        Label logsNotifsLbl = new Label("Logs notifications");

        SwitchButton btn = new SwitchButton(new SimpleBooleanProperty(!LogsNotificationsFetcher.getInstance().isPaused()));

        buttonsContainer.getChildren().addAll(logsNotifsLbl,btn,clearLogs);

        root.getChildren().addAll(tableView, buttonsContainer);

        stylizeComponent(root, clearLogs,buttonsContainer,logsNotifsLbl);

        return root;
    }

    private static void stylizeComponent(VBox root, Button clearLogs, HBox buttonsContainer, Label logsNotifsLbl) {
        root.getStyleClass().add(CENTER_ALIGN_STYLE);
        root.setSpacing(10);
        clearLogs.getStyleClass().add(BUTTON_STYLE_CLASS);


        buttonsContainer.getStyleClass().add(CENTER_ALIGN_STYLE);
        buttonsContainer.setSpacing(20);

        logsNotifsLbl.getStyleClass().add(LABEL_STYLE);
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

    private static class SwitchButton extends Label
    {
        private SimpleBooleanProperty switchedOn;

        public SwitchButton(SimpleBooleanProperty switchedOn) {
            this.switchedOn = switchedOn;
            Button switchBtn = new Button();
            switchBtn.setPrefWidth(40);
            switchBtn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent t) {
                    switchedOn.set(!switchedOn.get());
                }
            });

            setGraphic(switchBtn);

            switchedOn.addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> ov,
                                    Boolean t, Boolean t1) {
                    if (t1) {
                        setText("ON");
                        setStyle("-fx-background-color: green;-fx-text-fill:white;");
                        setContentDisplay(ContentDisplay.RIGHT);
                        LogsNotificationsFetcher.getInstance().resumeFetcher();
                    } else {
                        setText("OFF");
                        setStyle("-fx-background-color: grey;-fx-text-fill:black;");
                        setContentDisplay(ContentDisplay.LEFT);
                        LogsNotificationsFetcher.getInstance().pauseFetcher();
                    }
                }
            });
            setText(!switchOnProperty().get() ? "OFF" : "ON");
            setStyle(!switchOnProperty().get() ? "-fx-background-color: grey;-fx-text-fill:black;" : "-fx-background-color: green;-fx-text-fill:white;");
            setContentDisplay(!switchOnProperty().get() ? ContentDisplay.LEFT : ContentDisplay.RIGHT);

        }

        public SimpleBooleanProperty switchOnProperty() { return switchedOn; }
    }
}
