package frontend.finalproject.ServerResponseDisplayers;

import DTO.HttpRequests.GetExecutionOutcomeRequestDTO;
import DTO.HttpRequests.GetSimulatedStatesRequestDTO;
import backend.finalproject.AOSFacade;
import com.google.gson.*;
import frontend.finalproject.Controllers.CreateEnvController;
import frontend.finalproject.Controllers.HomeController;
import frontend.finalproject.Controllers.ManualActionRequestController;
import frontend.finalproject.Controllers.ResponseRequestController;
import frontend.finalproject.Controllers.SubControllers.EditSubController;
import frontend.finalproject.Utils.NotificationUtils;
import frontend.finalproject.Utils.UtilsFXML;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import utils.Response;


import java.io.IOException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class ExecutionOutcomeVisualizer implements IJsonVisualizer {
    public static final String EXECUTION_OUTCOME_JSON_KEY = "ExecutionOutcome";
    public static final String INITIAL_BELIEFE_STATE_JSON_KEY = "InitialBeliefeState";
    public static final String BELIEF_STATES_AFTER_EXECUTION_JSON_KEY = "BeliefStatesAfterExecution";
    public static final String ROOT_STYLE_CLASS = "VBoxStateWrapper";
    public static final String CENTER_STYLE = "TreeBoxWrapper";
    public static final String BTN_STYLE_CLASS = "formBtn";
    private static final String TEXT_FIELD_STYLE_CLASS = "TextFieldForm";
    private static final String LABEL_STYLE_CLASS = "TextFieldLabel";
    public static final String ACTION_TEXT_STYLE_CLASS = "Separator_Text";
    public static final int MAN_CONTROL_TAB_IDX = 2;
    List<JsonElement> executionOutcome;
    List<Map<String,Histogram>> histogramsOfBeliefStates; // for each state in the execution process, we need a histogram for every variable of the state.
    List<List<BarChart<String, Number>>> charts;
    int maxLen = 1;
    private List<String> actionDescriptions;
    private SimulatedStateVisualizer.SimulatedStateNode simulatedStateNode = null;
    private int currentSimulatedStateIndex = 0;
    private TabPane tabPane;
    private boolean shouldTerminate = false;
    private int beliefSize;
    private DisplayContainer execOutcomeDisplay;
    private Label prevExecAction;
    private Label nextActionToExec;
    private Button nextButton;
    private Button prevButton;


    public ExecutionOutcomeVisualizer(String jsonString, int beliefSize) {
        this.beliefSize = beliefSize;
        charts = new LinkedList<>();
        histogramsOfBeliefStates = new LinkedList<>();
        executionOutcome = new LinkedList<>();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonElement jsonElement = gson.fromJson(jsonString, JsonElement.class);
        jsonElement = jsonElement.getAsJsonObject().get(EXECUTION_OUTCOME_JSON_KEY);
        if(jsonElement.isJsonArray()){
            for (JsonElement element : jsonElement.getAsJsonArray()) {
                executionOutcome.add(element);
                Map<String,Histogram> histograms = buildHistograms(element);
                histogramsOfBeliefStates.add(histograms);
                charts.add(createCharts(histograms));
            }
        }
    }

    private List<BarChart<String, Number>> createCharts(Map<String,Histogram> histogramsData) {
        List<BarChart<String,Number>> charts = new LinkedList<>();
        for(Map.Entry<String,Histogram> histogramEntry : histogramsData.entrySet()){
            charts.add(createChart(histogramEntry.getValue().getHistogram(),histogramEntry.getKey()));
        }
        return charts;
    }

    private BarChart<String, Number> createChart(Map<String, Integer> histogramData, String title){
        // Create axes
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();

        // Set tick label rotation for x-axis
        xAxis.setTickLabelRotation(0);

        // Create bar chart
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle(title);
        xAxis.setLabel("Value");
        yAxis.setLabel("Frequency");

        // Convert histogram data to series and add data to the chart
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        histogramData.forEach((category, value) -> {
            Double doubleValue = Double.valueOf(value);
            Double maxLenDouble = (double) maxLen;
            double probability = doubleValue/maxLenDouble;
            series.getData().add(new XYChart.Data<>(category, probability));
        });
        barChart.getData().add(series);

        barChart.setPrefHeight(200);
        barChart.setPrefWidth(100);

        return barChart;
    }

    private Map<String, Histogram> buildHistograms(JsonElement execOutcome) {
        Map<String, Histogram> histograms = new HashMap<>();
        boolean isInitial = execOutcome.getAsJsonObject().has(INITIAL_BELIEFE_STATE_JSON_KEY);
        JsonArray stateArray = execOutcome.getAsJsonObject().
                get(isInitial ? INITIAL_BELIEFE_STATE_JSON_KEY : BELIEF_STATES_AFTER_EXECUTION_JSON_KEY).getAsJsonArray();
        maxLen = Math.max(maxLen,stateArray.size());
        for (JsonElement state : stateArray) {
            buildHistogramsOfState(histograms, state);
        }
        return histograms;
    }

    private void buildHistogramsOfState(Map<String, Histogram> histograms, JsonElement state) {
        for(Map.Entry<String, JsonElement> entry: state.getAsJsonObject().asMap().entrySet()){
            if(entry.getValue().isJsonObject()){
                insertJsonObjectToHistograms(entry.getKey() + ".",entry.getValue(), histograms);
            }
            else if(entry.getValue().isJsonArray()){
                int i = 0;
                for(JsonElement arrayElement : entry.getValue().getAsJsonArray()){
                    insertJsonObjectToHistograms(entry.getKey() + "[" + i + "]",arrayElement, histograms);
                    i++;
                }
            }
            else {
                if (histograms.containsKey(entry.getKey()))
                    histograms.get(entry.getKey()).add(entry.getValue().getAsString());
                else {
                    Histogram histogram = new Histogram();
                    histogram.add(entry.getValue().getAsString());
                    histograms.put(entry.getKey(), histogram);
                }
            }
        }
    }

    private void insertJsonObjectToHistograms(String prefixOfKey, JsonElement jsonElement, Map<String, Histogram> histograms) {
        if(jsonElement.isJsonPrimitive()){
            String key = prefixOfKey;
            if (histograms.containsKey(key))
                histograms.get(key).add(jsonElement.getAsString());
            else {
                Histogram histogram = new Histogram();
                histogram.add(jsonElement.getAsString());
                histograms.put(key, histogram);
            }
        }

        else if(jsonElement.isJsonArray()){
            int i = 0;
            for (JsonElement arrayElement : jsonElement.getAsJsonArray()) {
                insertJsonObjectToHistograms(jsonElement.getAsString() + "[" + i + "].", arrayElement, histograms);
                i++;
            }
        }

        else {
            for (Map.Entry<String, JsonElement> entry : jsonElement.getAsJsonObject().asMap().entrySet()) {
                if (entry.getValue().isJsonObject()) {
                    insertJsonObjectToHistograms(prefixOfKey + entry.getKey() + ".", entry.getValue(), histograms);
                } else if (entry.getValue().isJsonArray()) {
                    int i = 0;
                    for (JsonElement arrayElement : entry.getValue().getAsJsonArray()) {
                        insertJsonObjectToHistograms(entry.getKey() + "[" + i + "].", arrayElement, histograms);
                        i++;
                    }
                } else {
                    String key = prefixOfKey + entry.getKey();
                    if (histograms.containsKey(key))
                        histograms.get(key).add(entry.getValue().getAsString());
                    else {
                        Histogram histogram = new Histogram();
                        histogram.add(entry.getValue().getAsString());
                        histograms.put(key, histogram);
                    }
                }
            }
        }
    }

    @Override
    public Node displayJSON() {
        VBox root = new VBox();
        tabPane = new TabPane();
        root.getChildren().add(tabPane);
        Response<String> simStatesResp = AOSFacade.getInstance().sendRequest(new GetSimulatedStatesRequestDTO());
        if(!simStatesResp.hasErrorOccurred()){
            SimulatedStateVisualizer simulatedStateVisualizer = new SimulatedStateVisualizer(simStatesResp.getValue());
            this.simulatedStateNode = (SimulatedStateVisualizer.SimulatedStateNode) simulatedStateVisualizer.displayJSON();
            this.actionDescriptions = simulatedStateVisualizer.getActionDescriptions();
            tabPane.getTabs().add(new Tab("Simulated States",simulatedStateNode.getRoot()));
        }
        execOutcomeDisplay = new DisplayContainer(executionOutcome, charts, actionDescriptions);
        tabPane.getTabs().add(new Tab("Execution Outcome", execOutcomeDisplay.getComponent()));

        if(UtilsFXML.IS_MANUAL_CONTROL) {
            FXMLLoader loader = new FXMLLoader(CreateEnvController.class.getResource(UtilsFXML.SEND_MANUAL_ACTION_REQUEST_PATH));
            try {
                Node manControl = loader.load();
                ManualActionRequestController controller = loader.getController();
                controller.setOnActionSentCallback(null);
                tabPane.getTabs().add(new Tab("Manual Control", manControl));
                runRefreshingThread();
            } catch (IOException ignored) {
            }
        }
        createCommonComponents(root, execOutcomeDisplay);

        return root;
    }

    private void runRefreshingThread() {
        Thread thread = new Thread(() -> {
            while(!shouldTerminate){
                try {
                    Thread.sleep(1000);
                    onActionSentCallback();
                } catch (InterruptedException ignored) {
                }
            }
        });
        thread.start();
    }

    private void onActionSentCallback() {


        Response<String> execOutcomeJson = AOSFacade.getInstance().sendRequest(new GetExecutionOutcomeRequestDTO(beliefSize));
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        JsonElement jsonElement = gson.fromJson(execOutcomeJson.getValue(), JsonElement.class);
        if(jsonElement == null)
            return;

        jsonElement = jsonElement.getAsJsonObject().get(EXECUTION_OUTCOME_JSON_KEY);
        if (jsonElement.isJsonArray() && jsonElement.getAsJsonArray().size() == executionOutcome.size()) {
            return;
        }

        // if we run the following lines, this means that there has been a refresh in the states = man control was sent
        JsonElement finalJsonElement = jsonElement;
        Platform.runLater(() -> {
            Response<String> simStatesResp = AOSFacade.getInstance().sendRequest(new GetSimulatedStatesRequestDTO());
            if (!simStatesResp.hasErrorOccurred()) {
                SimulatedStateVisualizer simulatedStateVisualizer = new SimulatedStateVisualizer(simStatesResp.getValue());
                this.simulatedStateNode = (SimulatedStateVisualizer.SimulatedStateNode) simulatedStateVisualizer.displayJSON();
                this.actionDescriptions = simulatedStateVisualizer.getActionDescriptions();
                tabPane.getTabs().get(0).setContent(simulatedStateNode.getRoot());
            }

            // taking care of the initial state - for some reason it's empty at first.
            if(executionOutcome.size() == 1){
                executionOutcome.clear();
                charts.clear();
            }

            for (int i = executionOutcome.size(); i < finalJsonElement.getAsJsonArray().size(); i++) {
                JsonElement element = finalJsonElement.getAsJsonArray().get(i);
                executionOutcome.add(element);
                Map<String, Histogram> histograms = buildHistograms(element);
                histogramsOfBeliefStates.add(histograms);
                charts.add(createCharts(histograms));
            }

            this.execOutcomeDisplay = new DisplayContainer(executionOutcome, charts, actionDescriptions);
            tabPane.getTabs().get(1).setContent(execOutcomeDisplay.getComponent());
            updateNextPrevExecActions(prevExecAction, nextActionToExec);

            for (int i = 1; i <= currentSimulatedStateIndex; i++) {
                simulatedStateNode.handleNextBtn(null);
                execOutcomeDisplay.handleNextBtn();
            }

            bindNextPrevStateButtonActions();
        });
    }

    private void bindNextPrevStateButtonActions() {
        nextButton.setOnAction(event -> {
            if(currentSimulatedStateIndex < actionDescriptions.size() - 1) {
                currentSimulatedStateIndex++;
                execOutcomeDisplay.handleNextBtn();
                simulatedStateNode.handleNextBtn(event);
                updateNextPrevExecActions(prevExecAction, nextActionToExec);
            }
            if(UtilsFXML.IS_MANUAL_CONTROL && currentSimulatedStateIndex == actionDescriptions.size() - 1){
                UtilsFXML.showNotification(NotificationUtils.MAN_CONTROL_NOTIFICATION,NotificationUtils.MAN_CONTROL_NOTIFICATION_MSG,null);

            }
        });

        prevButton.setOnAction(event -> {
            if(currentSimulatedStateIndex > 0) {
                currentSimulatedStateIndex--;
                execOutcomeDisplay.handlePrevBtn();
                simulatedStateNode.handlePrevBtn(event);
                updateNextPrevExecActions(prevExecAction, nextActionToExec);
            }
        });
    }

    private void createCommonComponents(VBox root, DisplayContainer execOutcomeDisplay) {
        prevExecAction = new Label();
        nextActionToExec = new Label();
        updateNextPrevExecActions(prevExecAction, nextActionToExec);

        HBox nextPrevButtonsContainer = new HBox();
        nextButton = new Button("Next State");
        nextButton.setId("nextStateButton");
        prevButton = new Button("Previous State");
//        prevButton.setDisable(true);
//        prevButton.setId("prevStateButton");
        nextPrevButtonsContainer.getChildren().addAll(prevButton, nextButton);

        bindNextPrevStateButtonActions();

        nextPrevButtonsContainer.getStyleClass().add(CENTER_STYLE);
        root.getStyleClass().add(CENTER_STYLE);
        root.setSpacing(20);
        nextPrevButtonsContainer.setSpacing(10);
        nextButton.getStyleClass().add(BTN_STYLE_CLASS);
        prevButton.getStyleClass().add(BTN_STYLE_CLASS);
        prevExecAction.getStyleClass().add(ACTION_TEXT_STYLE_CLASS);
        nextActionToExec.getStyleClass().add(ACTION_TEXT_STYLE_CLASS);

        root.getChildren().addAll(prevExecAction, nextActionToExec,nextPrevButtonsContainer);
    }

    private void updateNextPrevExecActions(Label prevExecAction, Label nextActionToExec) {
        prevExecAction.setText("Previously executed action: " + actionDescriptions.get(currentSimulatedStateIndex));
        String nAction = currentSimulatedStateIndex + 1 < actionDescriptions.size() ?
                actionDescriptions.get(currentSimulatedStateIndex + 1) :
                "No more actions";
        nextActionToExec.setText("Next action to execute: " + nAction);
    }

    public void terminateRefresh() {
        shouldTerminate = true;
    }

    private static class DisplayContainer{
        private int currentIdx = 0;
        List<JsonElement> executionOutcome;
        List<List<BarChart<String, Number>>> charts;

        VBox rootContainer;
        HBox histogramsContainer;
        ScrollPane scrollPane;
        HBox filteredTextFieldContainer;
        Label filterLabel;
        TextField filterTextField;
        HBox buttonsContainer;
        Button showRawData;
        VBox rawDataContainer;
        List<String> actionDescriptions;
        AtomicReference<List<BarChart<String, Number>>> curHistograms;


        private DisplayContainer(List<JsonElement> executionOutcome, List<List<BarChart<String, Number>>> charts, List<String> actionDescriptions){
            this.executionOutcome = executionOutcome;
            this.charts = charts;
            this.actionDescriptions = actionDescriptions;
        }

        public void handleNextBtn(){
            if(currentIdx < charts.size() - 1){
                currentIdx++;
                updateContainers(curHistograms, histogramsContainer, rawDataContainer);
            }
        }

        public void handlePrevBtn(){
            if(currentIdx > 0){
                currentIdx--;
                updateContainers(curHistograms, histogramsContainer, rawDataContainer);
            }
        }
        private Node getComponent(){
            curHistograms = new AtomicReference<>(charts.get(0));

            buildComponents(curHistograms);
            bindActionsToButtons(curHistograms);
            stylizeComponents();

            rootContainer.getChildren().addAll(scrollPane,filteredTextFieldContainer,buttonsContainer,rawDataContainer);
            return rootContainer;
        }

        private void bindActionsToButtons(AtomicReference<List<BarChart<String, Number>>> curHistograms) {
            showRawData.setOnAction(e -> {
                if(rawDataContainer.isVisible()){
                    rawDataContainer.setVisible(false);
                    rawDataContainer.setPrefHeight(0);
                    showRawData.setText("Show Raw Data");
                }
                else{
                    rawDataContainer.setVisible(true);
                    rawDataContainer.setPrefHeight(400);
                    showRawData.setText("Hide Raw Data");
                }
            });

            filterTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                populateHistograms(curHistograms, newValue);
            });
        }

        private void populateHistograms(AtomicReference<List<BarChart<String, Number>>> curHistograms, String newValue) {
            if(newValue.isEmpty()){
                histogramsContainer.getChildren().clear();
                histogramsContainer.getChildren().addAll(curHistograms.get());
            }
            else{
                List<BarChart<String, Number>> filteredCharts = new ArrayList<>();
                for(BarChart<String, Number> chart : curHistograms.get()){
                    if(chart.getTitle().contains(newValue)){
                        filteredCharts.add(chart);
                    }
                }
                histogramsContainer.getChildren().clear();
                histogramsContainer.getChildren().addAll(filteredCharts);
            }
        }

        private void buildComponents(AtomicReference<List<BarChart<String, Number>>> curHistograms) {
            rootContainer = new VBox();
            histogramsContainer = new HBox();

            histogramsContainer.getChildren().addAll(curHistograms.get());
            scrollPane = new ScrollPane(histogramsContainer);


            filteredTextFieldContainer = new HBox();
            filterLabel = new Label("Filter by variable:");
            filterTextField = new TextField();
            filteredTextFieldContainer.getStyleClass().add(CENTER_STYLE);
            filteredTextFieldContainer.getChildren().addAll(filterLabel,filterTextField);


            buttonsContainer = new HBox();
            showRawData = new Button("Show Raw Data");
            buttonsContainer.getChildren().addAll(showRawData);

            rawDataContainer = new VBox();
            rawDataContainer.getChildren().add(new JsonTreeViewVisualizer(executionOutcome.get(0).toString()).displayJSON());
            rawDataContainer.setVisible(false);

        }

        private void updateContainers(AtomicReference<List<BarChart<String, Number>>> curHistograms, HBox histogramsContainer, VBox rawDataContainer) {
            rawDataContainer.getChildren().clear();
            rawDataContainer.getChildren().add(new JsonTreeViewVisualizer(executionOutcome.get(currentIdx).toString()).displayJSON());
            curHistograms.set(charts.get(currentIdx));
            populateHistograms(curHistograms, filterTextField.getText());
        }

        private void stylizeComponents() {
            rootContainer.getStyleClass().add(ROOT_STYLE_CLASS);
            buttonsContainer.getStyleClass().add(CENTER_STYLE);
            showRawData.getStyleClass().add(BTN_STYLE_CLASS);
            rootContainer.setSpacing(20);
            buttonsContainer.setSpacing(10);

            histogramsContainer.setSpacing(10);

            scrollPane.setPrefWidth(600);
            scrollPane.setPrefHeight(250);

            filterTextField.getStyleClass().add(TEXT_FIELD_STYLE_CLASS);
            filterLabel.getStyleClass().add(LABEL_STYLE_CLASS);

            rawDataContainer.getStyleClass().add(CENTER_STYLE);
            rawDataContainer.setPrefHeight(0);

        }

    }

    private static class Histogram {
        private final Map<String,Integer> histogram;

        private Histogram() {
            histogram = new HashMap<>();
        }

        private void add(String key) {
            if (histogram.containsKey(key))
                histogram.put(key, histogram.get(key) + 1);
            else {
                histogram.put(key, 1);
            }
        }

        private Map<String, Integer> getHistogram() {
            return histogram;
        }
    }
}
