package frontend.finalproject.ServerResponseDisplayers;

import com.google.gson.*;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;


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
    List<JsonElement> executionOutcome;
    List<Map<String,Histogram>> histogramsOfBeliefStates; // for each state in the execution process, we need a histogram for every variable of the state.
    List<List<BarChart<String, Number>>> charts;
    int maxLen = 1;
    int currentIdx = 0;

    public ExecutionOutcomeVisualizer(String jsonString) {
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
                insertJsonObjectToHistograms(entry.getKey() + ".",entry.getValue().getAsJsonObject(), histograms);
            }
            else if(entry.getValue().isJsonArray()){
                int i = 0;
                for(JsonElement arrayElement : entry.getValue().getAsJsonArray()){
                    insertJsonObjectToHistograms(entry.getKey() + "[" + i + "]",arrayElement.getAsJsonObject(), histograms);
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

    private void insertJsonObjectToHistograms(String prefixOfKey, JsonObject jsonObj, Map<String, Histogram> histograms) {
        for (Map.Entry<String, JsonElement> entry : jsonObj.asMap().entrySet()) {
            if (entry.getValue().isJsonObject()) {
                insertJsonObjectToHistograms(prefixOfKey + entry.getKey() + ".", entry.getValue().getAsJsonObject(), histograms);
            } else if (entry.getValue().isJsonArray()) {
                int i = 0;
                for (JsonElement arrayElement : entry.getValue().getAsJsonArray()) {
                    insertJsonObjectToHistograms(entry.getKey() + "[" + i + "].", arrayElement.getAsJsonObject(), histograms);
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

    @Override
    public Node displayJSON() {
        VBox rootContainer = new VBox();

        AtomicReference<List<BarChart<String, Number>>> curHistograms = new AtomicReference<>(charts.get(0));

        HBox histogramsContainer = new HBox();

        histogramsContainer.getChildren().addAll(curHistograms.get());
        ScrollPane scrollPane = new ScrollPane(histogramsContainer);



        HBox filteredTextFieldContainer = new HBox();
        Label filterLabel = new Label("Filter by variable:");
        TextField filterTextField = new TextField();
        filteredTextFieldContainer.getStyleClass().add(CENTER_STYLE);
        filteredTextFieldContainer.getChildren().addAll(filterLabel,filterTextField);


        HBox buttonsContainer = new HBox();
        Button prev = new Button("Previous State");
        Button next = new Button("Next State");
        Button showRawData = new Button("Show Raw Data");
        buttonsContainer.getChildren().addAll(prev,showRawData,next);

        VBox rawDataContainer = new VBox();
        rawDataContainer.getChildren().add(new JsonTreeViewVisualizer(executionOutcome.get(0).toString()).displayJSON());
        rawDataContainer.getStyleClass().add(CENTER_STYLE);
        rawDataContainer.setPrefHeight(0);
        rawDataContainer.setVisible(false);

        prev.setOnAction(e -> {
            if(currentIdx > 0){
                currentIdx--;
                updateContainers(curHistograms, histogramsContainer, rawDataContainer);
            }
        });

        next.setOnAction(e -> {
            if(currentIdx < charts.size() - 1){
                currentIdx++;
                updateContainers(curHistograms, histogramsContainer, rawDataContainer);
            }
        });

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
        });




        stylizeComponents(rootContainer, buttonsContainer, prev, next, showRawData, scrollPane,histogramsContainer,filterTextField,filterLabel);
        rootContainer.getChildren().addAll(scrollPane,filteredTextFieldContainer,buttonsContainer,rawDataContainer);
        return rootContainer;

    }

    private void updateContainers(AtomicReference<List<BarChart<String, Number>>> curHistograms, HBox histogramsContainer, VBox rawDataContainer) {
        rawDataContainer.getChildren().clear();
        rawDataContainer.getChildren().add(new JsonTreeViewVisualizer(executionOutcome.get(currentIdx).toString()).displayJSON());
        curHistograms.set(charts.get(currentIdx));
        histogramsContainer.getChildren().clear();
        histogramsContainer.getChildren().addAll(curHistograms.get());
    }

    private void stylizeComponents(VBox rootContainer, HBox buttonsContainer, Button prev, Button next, Button showRawData, ScrollPane scrollPane,HBox histogramsContainer,TextField filterTextField
    ,Label filterLabel) {
        rootContainer.getStyleClass().add(ROOT_STYLE_CLASS);
        buttonsContainer.getStyleClass().add(CENTER_STYLE);
        next.getStyleClass().add(BTN_STYLE_CLASS);
        prev.getStyleClass().add(BTN_STYLE_CLASS);
        showRawData.getStyleClass().add(BTN_STYLE_CLASS);
        rootContainer.setSpacing(20);
        buttonsContainer.setSpacing(10);

        histogramsContainer.setSpacing(10);

        scrollPane.setPrefWidth(600);
        scrollPane.setPrefHeight(250);

        filterTextField.getStyleClass().add(TEXT_FIELD_STYLE_CLASS);
        filterLabel.getStyleClass().add(LABEL_STYLE_CLASS);
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
