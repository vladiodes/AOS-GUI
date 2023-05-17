package frontend.finalproject.ServerResponseDisplayers;

import com.google.gson.*;
import javafx.scene.Node;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;


import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ExecutionOutcomeVisualizer implements IJsonVisualizer {
    public static final String EXECUTION_OUTCOME_JSON_KEY = "ExecutionOutcome";
    public static final String INITIAL_BELIEFE_STATE_JSON_KEY = "InitialBeliefeState";
    public static final String BELIEF_STATES_AFTER_EXECUTION_JSON_KEY = "BeliefStatesAfterExecution";
    List<JsonElement> executionOutcome;
    List<Map<String,Integer>> histograms;
    List<BarChart<Number, String>> charts;
    int maxLen = 1;
    int currentIdx = 0;

    public ExecutionOutcomeVisualizer(String jsonString) {
        charts = new LinkedList<>();
        histograms = new LinkedList<>();
        executionOutcome = new LinkedList<>();
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonElement jsonElement = gson.fromJson(jsonString, JsonElement.class);
        jsonElement = jsonElement.getAsJsonObject().get(EXECUTION_OUTCOME_JSON_KEY);
        if(jsonElement.isJsonArray()){
            for (JsonElement element : jsonElement.getAsJsonArray()) {
                executionOutcome.add(element);
                Map<String,Integer> histogram = buildHistogram(element);
                histograms.add(histogram);
                charts.add(createChart(histogram));
            }
        }
    }

    private BarChart<Number, String> createChart(Map<String, Integer> histogramData) {
        // Create axes
        NumberAxis xAxis = new NumberAxis();
        CategoryAxis yAxis = new CategoryAxis();
        xAxis.setLabel("Percentage");
        yAxis.setLabel("Category");

        // Create bar chart
        BarChart<Number, String> barChart = new BarChart<>(xAxis, yAxis);
        barChart.setTitle("State distribution");

        // Convert histogram data to series and add data to the chart
        XYChart.Series<Number, String> series = new XYChart.Series<>();
        histogramData.forEach((category, value) -> {
            Double doubleValue = Double.valueOf(value);
            Double maxLenDouble = (double) maxLen;
            double percentage = doubleValue/maxLenDouble;
            percentage *= 100;
            series.getData().add(new XYChart.Data<>(percentage, category));
        });
        barChart.getData().add(series);

        // Adjust y-axis label font size
        yAxis.setTickLabelFont(Font.font(14));  // Set desired font size

        // Increase bar width
        for (XYChart.Data<Number, String> data : series.getData()) {
            data.getNode().setStyle("-fx-bar-width: 30px;");  // Set desired bar width
        }

        // Set chart height
        barChart.setPrefHeight(500);  // Set desired height

        return barChart;
    }

    private Map<String, Integer> buildHistogram(JsonElement element) {
        Map<String, Integer> histogram = new HashMap<>();
        boolean isInitial = element.getAsJsonObject().has(INITIAL_BELIEFE_STATE_JSON_KEY);
        JsonArray stateArray = element.getAsJsonObject().
                get(isInitial ? INITIAL_BELIEFE_STATE_JSON_KEY : BELIEF_STATES_AFTER_EXECUTION_JSON_KEY).getAsJsonArray();
        maxLen = Math.max(maxLen,stateArray.size());
        for (JsonElement state : stateArray) {
            for(Map.Entry<String, JsonElement> entry: state.getAsJsonObject().asMap().entrySet()){
                if(entry.getValue().isJsonObject()){
                    insertJsonObjectToHistogram(entry.getKey() + ".",entry.getValue().getAsJsonObject(),histogram);
                }
                else if(entry.getValue().isJsonArray()){
                    int i = 0;
                    for(JsonElement arrayElement : entry.getValue().getAsJsonArray()){
                        insertJsonObjectToHistogram(entry.getKey() + "[" + i + "]",arrayElement.getAsJsonObject(),histogram);
                        i++;
                    }
                }
                else {
                    String key = String.format("%s = %s", entry.getKey(), entry.getValue().getAsString());
                    if (histogram.containsKey(key))
                        histogram.put(key, histogram.get(key) + 1);
                    else {
                        histogram.put(key, 1);
                    }
                }
            }
        }
        return histogram;
    }

    private void insertJsonObjectToHistogram(String prefixOfKey, JsonObject jsonObj, Map<String, Integer> histogram) {
        for(Map.Entry<String,JsonElement> entry : jsonObj.asMap().entrySet()){
            if(entry.getValue().isJsonObject()){
                insertJsonObjectToHistogram(prefixOfKey + entry.getKey() + ".",entry.getValue().getAsJsonObject(),histogram);
            }
            else if(entry.getValue().isJsonArray()){
                int i = 0;
                for(JsonElement arrayElement : entry.getValue().getAsJsonArray()){
                    insertJsonObjectToHistogram(entry.getKey() + "[" + i + "]",arrayElement.getAsJsonObject(),histogram);
                    i++;
                }
            }
            else{
                String key = String.format("%s = %s", prefixOfKey + entry.getKey(), entry.getValue().getAsString());
                if (histogram.containsKey(key))
                    histogram.put(key, histogram.get(key) + 1);
                else {
                    histogram.put(key, 1);
                }
            }
        }

    }

    @Override
    public Node displayJSON() {
        VBox vBox = new VBox();
        Node[] curState = new Node[] {new JsonTreeViewVisualizer(executionOutcome.get(0).toString()).displayJSON()};
        HBox hBox = new HBox();
        Button prev = new Button();
        prev.setText("Previous State");
        Button next = new Button();
        next.setText("Next State");
        Button showChart = new Button("Show Distribution");
        hBox.getChildren().addAll(prev,showChart,next);

        VBox chartBox = new VBox();
        chartBox.getChildren().add(charts.get(0));
        chartBox.setVisible(false);



        prev.setOnAction(e->{
            if(currentIdx > 0){
                currentIdx--;
                curState[0] = new JsonTreeViewVisualizer(executionOutcome.get(currentIdx).toString()).displayJSON();
                vBox.getChildren().remove(0);
                vBox.getChildren().add(0,curState[0]);
                chartBox.getChildren().clear();
                chartBox.getChildren().add(charts.get(currentIdx));
            }
        });

        next.setOnAction(e->{
            if(currentIdx < executionOutcome.size() - 1){
                currentIdx++;
                curState[0] = new JsonTreeViewVisualizer(executionOutcome.get(currentIdx).toString()).displayJSON();
                vBox.getChildren().remove(0);
                vBox.getChildren().add(0,curState[0]);
                chartBox.getChildren().clear();
                chartBox.getChildren().add(charts.get(currentIdx));
            }
        });
        ScrollPane scrollPane = new ScrollPane(chartBox);
        scrollPane.setPrefHeight(0);
        scrollPane.getStyleClass().add("TreeBoxWrapper");

        showChart.setOnAction(e->{
            if(chartBox.isVisible()){
                showChart.setText("Show Distribution");
                chartBox.setVisible(false);
                scrollPane.setPrefHeight(0);
            }
            else{
                showChart.setText("Hide Distribution");
                chartBox.setVisible(true);
                scrollPane.setPrefHeight(400);
            }
        });

        vBox.getChildren().addAll(curState[0],hBox,scrollPane);
        vBox.getStyleClass().add("VBoxStateWrapper");
        hBox.getStyleClass().add("TreeBoxWrapper");
        chartBox.getStyleClass().add("TreeBoxWrapper");
        next.getStyleClass().add("formBtn");
        prev.getStyleClass().add("formBtn");
        showChart.getStyleClass().add("formBtn");
        vBox.setSpacing(20);
        hBox.setSpacing(10);
        return vBox;

    }
}
