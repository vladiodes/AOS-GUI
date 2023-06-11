package backend.finalproject.JsonVisualizerTests;

import backend.finalproject.IAOSFacade;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.sun.marlin.stats.Histogram;
import de.saxsys.javafx.test.JfxRunner;
import frontend.finalproject.ServerResponseDisplayers.ExecutionOutcomeVisualizer;
import frontend.finalproject.ServerResponseDisplayers.JsonTreeViewVisualizer;
import frontend.finalproject.ServerResponseDisplayers.SimulatedStateVisualizer;
import javafx.collections.ObservableSet;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import utils.Response;

import java.util.List;
import java.util.Map;

@RunWith(JfxRunner.class)
class ExecutionOutcomeTests {
    private final static String GET_EXEC_OUTCOME_RESP = """
            
            {
                "ExecutionOutcome": [
                    {
                        "InitialBeliefeState": [
                            {
                                "child": 4,
                                "location0": 0,
                                "location1": 1,
                                "location2": 2,
                                "location3": 3,
                                "pickActionsLeft": 6,
                                "robotArm": 5,
                                "robotLocation": 4,
                                "toy1": {
                                    "location": 0,
                                    "reward": 40.0,
                                    "type": "green"
                                },
                                "toy2": {
                                    "location": 1,
                                    "reward": 20.0,
                                    "type": "blue"
                                },
                                "toy3": {
                                    "location": 2,
                                    "reward": 10.0,
                                    "type": "black"
                                },
                                "toy4": {
                                    "location": 3,
                                    "reward": 10.0,
                                    "type": "red"
                                },
                                "toy_typeBlack": "black",
                                "toy_typeBlue": "blue",
                                "toy_typeGreen": "green",
                                "toy_typeRed": "red"
                            },
                            {
                                "child": 4,
                                "location0": 0,
                                "location1": 1,
                                "location2": 2,
                                "location3": 3,
                                "pickActionsLeft": 6,
                                "robotArm": 5,
                                "robotLocation": 4,
                                "toy1": {
                                    "location": 2,
                                    "reward": 20.0,
                                    "type": "green"
                                },
                                "toy2": {
                                    "location": 1,
                                    "reward": 10.0,
                                    "type": "blue"
                                },
                                "toy3": {
                                    "location": 3,
                                    "reward": 10.0,
                                    "type": "black"
                                },
                                "toy4": {
                                    "location": 0,
                                    "reward": 40.0,
                                    "type": "red"
                                },
                                "toy_typeBlack": "black",
                                "toy_typeBlue": "blue",
                                "toy_typeGreen": "green",
                                "toy_typeRed": "red"
                            },
                            {
                                "child": 4,
                                "location0": 0,
                                "location1": 1,
                                "location2": 2,
                                "location3": 3,
                                "pickActionsLeft": 6,
                                "robotArm": 5,
                                "robotLocation": 4,
                                "toy1": {
                                    "location": 2,
                                    "reward": 40.0,
                                    "type": "green"
                                },
                                "toy2": {
                                    "location": 0,
                                    "reward": 20.0,
                                    "type": "blue"
                                },
                                "toy3": {
                                    "location": 1,
                                    "reward": 10.0,
                                    "type": "black"
                                },
                                "toy4": {
                                    "location": 3,
                                    "reward": 10.0,
                                    "type": "red"
                                },
                                "toy_typeBlack": "black",
                                "toy_typeBlue": "blue",
                                "toy_typeGreen": "green",
                                "toy_typeRed": "red"
                            },
                            {
                                "child": 4,
                                "location0": 0,
                                "location1": 1,
                                "location2": 2,
                                "location3": 3,
                                "pickActionsLeft": 6,
                                "robotArm": 5,
                                "robotLocation": 4,
                                "toy1": {
                                    "location": 2,
                                    "reward": 40.0,
                                    "type": "green"
                                },
                                "toy2": {
                                    "location": 0,
                                    "reward": 20.0,
                                    "type": "blue"
                                },
                                "toy3": {
                                    "location": 3,
                                    "reward": 10.0,
                                    "type": "black"
                                },
                                "toy4": {
                                    "location": 1,
                                    "reward": 10.0,
                                    "type": "red"
                                },
                                "toy_typeBlack": "black",
                                "toy_typeBlue": "blue",
                                "toy_typeGreen": "green",
                                "toy_typeRed": "red"
                            }
                        ]
                    }
                    ]
                    }
                    """;
    private ExecutionOutcomeVisualizer visualizer;


    @BeforeAll
    static void initJfxRuntime() {
        PlatformInitializer.initPlatform();
    }

    Map<String, ExecutionOutcomeVisualizer.Histogram> getHistograms(int stateIndex) {
        visualizer = new ExecutionOutcomeVisualizer(GET_EXEC_OUTCOME_RESP,4);
        Gson gson = new Gson();
        JsonElement execOutcome = gson.fromJson(GET_EXEC_OUTCOME_RESP, JsonElement.class);
        JsonElement state = execOutcome.getAsJsonObject().get("ExecutionOutcome").getAsJsonArray().get(stateIndex);
        return visualizer.buildHistograms(state);
    }


    @Test
    void testBuildHistogramsInitialStateAllVarsIncluded(){
        Map<String, ExecutionOutcomeVisualizer.Histogram> histograms = getHistograms(0);
        Assertions.assertEquals(histograms.size(),24);
    }

    @Test
    void testBuildHistogramsChildFreqIsEqual(){
        Map<String, ExecutionOutcomeVisualizer.Histogram> histogramMap = getHistograms(0);
        ExecutionOutcomeVisualizer.Histogram childHist = histogramMap.get("child");
        Map<String,Integer> childHistMap = childHist.getHistogram();
        Assertions.assertEquals(4,childHistMap.get("4"));
    }

    @Test
    void testBuildHistogramsRobotLocationFreqIsEqual(){
        Map<String, ExecutionOutcomeVisualizer.Histogram> histogramMap = getHistograms(0);
        ExecutionOutcomeVisualizer.Histogram robotLocationHist = histogramMap.get("robotLocation");
        Map<String,Integer> robotLocationHistMap = robotLocationHist.getHistogram();
        Assertions.assertEquals(4,robotLocationHistMap.get("4"));
    }

    @Test
    void testBuildHistogramsRobotArmFreqIsEqual(){
        Map<String, ExecutionOutcomeVisualizer.Histogram> histogramMap = getHistograms(0);
        ExecutionOutcomeVisualizer.Histogram robotArmHist = histogramMap.get("robotArm");
        Map<String,Integer> robotArmHistMap = robotArmHist.getHistogram();
        Assertions.assertEquals(4,robotArmHistMap.get("5"));
    }

    @Test
    void testBuildHistogramsPickActionsLeftFreqIsEqual(){
        Map<String, ExecutionOutcomeVisualizer.Histogram> histogramMap = getHistograms(0);
        ExecutionOutcomeVisualizer.Histogram pickActionsLeftHist = histogramMap.get("pickActionsLeft");
        Map<String,Integer> pickActionsLeftHistMap = pickActionsLeftHist.getHistogram();
        Assertions.assertEquals(4,pickActionsLeftHistMap.get("6"));
    }

    @Test
    void testBuildHistogramsToy1LocationFreqIsEqual(){
        Map<String, ExecutionOutcomeVisualizer.Histogram> histogramMap = getHistograms(0);
        ExecutionOutcomeVisualizer.Histogram toy1LocationHist = histogramMap.get("toy1.location");
        Map<String,Integer> toy1LocationHistMap = toy1LocationHist.getHistogram();
        Assertions.assertEquals(3,toy1LocationHistMap.get("2"));
        Assertions.assertEquals(1,toy1LocationHistMap.get("0"));

    }

    @Test
    void testBuildHistogramsToy2LocationFreqIsEqual(){
        Map<String, ExecutionOutcomeVisualizer.Histogram> histogramMap = getHistograms(0);
        ExecutionOutcomeVisualizer.Histogram toy2LocationHist = histogramMap.get("toy2.location");
        Map<String,Integer> toy2LocationHistMap = toy2LocationHist.getHistogram();
        Assertions.assertEquals(2,toy2LocationHistMap.get("1"));
        Assertions.assertEquals(2,toy2LocationHistMap.get("0"));
    }

    @Test
    void testBuildHistogramsToy3LocationFreqIsEqual(){
        Map<String, ExecutionOutcomeVisualizer.Histogram> histogramMap = getHistograms(0);
        ExecutionOutcomeVisualizer.Histogram toy3LocationHist = histogramMap.get("toy3.location");
        Map<String,Integer> toy3LocationHistMap = toy3LocationHist.getHistogram();
        Assertions.assertEquals(2,toy3LocationHistMap.get("3"));
        Assertions.assertEquals(1,toy3LocationHistMap.get("2"));
        Assertions.assertEquals(1,toy3LocationHistMap.get("1"));
    }

    @Test
    void testBuildHistogramsToy4LocationFreqIsEqual(){
        Map<String, ExecutionOutcomeVisualizer.Histogram> histogramMap = getHistograms(0);
        ExecutionOutcomeVisualizer.Histogram toy4LocationHist = histogramMap.get("toy4.location");
        Map<String,Integer> toy4LocationHistMap = toy4LocationHist.getHistogram();
        Assertions.assertEquals(1,toy4LocationHistMap.get("0"));
        Assertions.assertEquals(1,toy4LocationHistMap.get("1"));
        Assertions.assertEquals(2,toy4LocationHistMap.get("3"));
    }

    @Test
    void testBuildHistogramsToy1RewardFreqIsEqual(){
        Map<String, ExecutionOutcomeVisualizer.Histogram> histogramMap = getHistograms(0);
        ExecutionOutcomeVisualizer.Histogram toy1RewardHist = histogramMap.get("toy1.reward");
        Map<String,Integer> toy1RewardHistMap = toy1RewardHist.getHistogram();
        Assertions.assertEquals(3,toy1RewardHistMap.get("40.0"));
        Assertions.assertEquals(1,toy1RewardHistMap.get("20.0"));
    }

    @Test
    void testBuildHistogramsToy2RewardFreqIsEqual(){
        Map<String, ExecutionOutcomeVisualizer.Histogram> histogramMap = getHistograms(0);
        ExecutionOutcomeVisualizer.Histogram toy2RewardHist = histogramMap.get("toy2.reward");
        Map<String,Integer> toy2RewardHistMap = toy2RewardHist.getHistogram();
        Assertions.assertEquals(1,toy2RewardHistMap.get("10.0"));
        Assertions.assertEquals(3,toy2RewardHistMap.get("20.0"));
    }

    @Test
    void testBuildHistogramsToy3RewardFreqIsEqual(){
        Map<String, ExecutionOutcomeVisualizer.Histogram> histogramMap = getHistograms(0);
        ExecutionOutcomeVisualizer.Histogram toy3RewardHist = histogramMap.get("toy3.reward");
        Map<String,Integer> toy3RewardHistMap = toy3RewardHist.getHistogram();
        Assertions.assertEquals(4,toy3RewardHistMap.get("10.0"));
    }


}