package backend.finalproject.JsonVisualizerTests;

import de.saxsys.javafx.test.JfxRunner;
import frontend.finalproject.ServerResponseDisplayers.SolverActionsVisualizer;
import javafx.application.Platform;
import javafx.scene.control.TreeView;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(JfxRunner.class)
class SolverActionsVisualizerTest {

    private final static String GET_SOLVER_ACTIONS_RESPONSE = """
                        [
                            {
                                "actionID": 0,
                                "actionDescription": "ID:0,draw_in_cellAction,oCellP:0",
                                "actionName": "draw_in_cell",
                                "actionConstantParameters": [
                                    {
                                        "parameterName": "oCellP",
                                        "value": "0"
                                    }
                                ]
                            },
                            {
                                "actionID": 1,
                                "actionDescription": "ID:1,draw_in_cellAction,oCellP:1",
                                "actionName": "draw_in_cell",
                                "actionConstantParameters": [
                                    {
                                        "parameterName": "oCellP",
                                        "value": "1"
                                    }
                                ]
                            },
                            {
                                "actionID": 2,
                                "actionDescription": "ID:2,draw_in_cellAction,oCellP:2",
                                "actionName": "draw_in_cell",
                                "actionConstantParameters": [
                                    {
                                        "parameterName": "oCellP",
                                        "value": "2"
                                    }
                                ]
                            },
                            {
                                "actionID": 3,
                                "actionDescription": "ID:3,draw_in_cellAction,oCellP:3",
                                "actionName": "draw_in_cell",
                                "actionConstantParameters": [
                                    {
                                        "parameterName": "oCellP",
                                        "value": "3"
                                    }
                                ]
                            },
                            {
                                "actionID": 4,
                                "actionDescription": "ID:4,draw_in_cellAction,oCellP:4",
                                "actionName": "draw_in_cell",
                                "actionConstantParameters": [
                                    {
                                        "parameterName": "oCellP",
                                        "value": "4"
                                    }
                                ]
                            },
                            {
                                "actionID": 5,
                                "actionDescription": "ID:5,draw_in_cellAction,oCellP:5",
                                "actionName": "draw_in_cell",
                                "actionConstantParameters": [
                                    {
                                        "parameterName": "oCellP",
                                        "value": "5"
                                    }
                                ]
                            },
                            {
                                "actionID": 6,
                                "actionDescription": "ID:6,draw_in_cellAction,oCellP:6",
                                "actionName": "draw_in_cell",
                                "actionConstantParameters": [
                                    {
                                        "parameterName": "oCellP",
                                        "value": "6"
                                    }
                                ]
                            },
                            {
                                "actionID": 7,
                                "actionDescription": "ID:7,draw_in_cellAction,oCellP:7",
                                "actionName": "draw_in_cell",
                                "actionConstantParameters": [
                                    {
                                        "parameterName": "oCellP",
                                        "value": "7"
                                    }
                                ]
                            },
                            {
                                "actionID": 8,
                                "actionDescription": "ID:8,draw_in_cellAction,oCellP:8",
                                "actionName": "draw_in_cell",
                                "actionConstantParameters": [
                                    {
                                        "parameterName": "oCellP",
                                        "value": "8"
                                    }
                                ]
                            },
                            {
                                "actionID": 9,
                                "actionDescription": "ID:9,detect_board_stateAction",
                                "actionName": "detect_board_state",
                                "actionConstantParameters": []
                            }
                        ]
            """;
    private SolverActionsVisualizer solverActionsVisualizer;

    @BeforeAll
    static void initJfxRuntime() {
        PlatformInitializer.initPlatform();
    }

    @Test
    void testDisplaySolverActions() {
        solverActionsVisualizer = new SolverActionsVisualizer(GET_SOLVER_ACTIONS_RESPONSE);
        TreeView<String> treeView = (TreeView<String>) solverActionsVisualizer.displayJSON();
        assertEquals(treeView.getRoot().getChildren().size(), 10);
    }
}