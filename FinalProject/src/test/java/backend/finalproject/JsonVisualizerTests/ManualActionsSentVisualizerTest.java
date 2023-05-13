package backend.finalproject.JsonVisualizerTests;

import backend.finalproject.IAOSFacade;
import de.saxsys.javafx.test.JfxRunner;
import frontend.finalproject.ServerResponseDisplayers.ManualActionsSentVisualizer;
import javafx.application.Platform;
import javafx.scene.control.TreeView;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import utils.Response;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@RunWith(JfxRunner.class)
class ManualActionsSentVisualizerTest {

    private ManualActionsSentVisualizer manualActionsSentVisualizer;
    @Mock
    IAOSFacade mockFacade;
    private final static String JSON_RESPONSE_TWO_ACTIONS_SENT = """
            [
                {
                    "actionID": 9
                },
                {
                    "actionID": 2
                }
            ]
            """;

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

    @BeforeAll
    static void initJfxRuntime() {
        PlatformInitializer.initPlatform();
    }

    @BeforeEach
    public void setUp() {
        mockFacade = mock(IAOSFacade.class);
        when(mockFacade.sendRequest(any())).thenReturn(Response.OK(GET_SOLVER_ACTIONS_RESPONSE));
    }

    @Test
    void test2Actions_Display_JSON() {
        manualActionsSentVisualizer = new ManualActionsSentVisualizer(JSON_RESPONSE_TWO_ACTIONS_SENT, mockFacade);
        TreeView<String> treeView = (TreeView<String>) manualActionsSentVisualizer.displayJSON();
        assertEquals(2, treeView.getRoot().getChildren().size());
    }

    @Test
    void testMapOfActions_0() {
        testActionIsMapped(0, "ID:0,draw_in_cellAction,oCellP:0");
    }

    @Test
    void testMapOfActions_1() {
        testActionIsMapped(1, "ID:1,draw_in_cellAction,oCellP:1");
    }

    @Test
    void testMapOfActions_2() {
        testActionIsMapped(2, "ID:2,draw_in_cellAction,oCellP:2");
    }

    @Test
    void testMapOfActions_3() {
        testActionIsMapped(3, "ID:3,draw_in_cellAction,oCellP:3");
    }

    @Test
    void testMapOfActions_4() {
        testActionIsMapped(4, "ID:4,draw_in_cellAction,oCellP:4");
    }

    @Test
    void testMapOfActions_5() {
        testActionIsMapped(5, "ID:5,draw_in_cellAction,oCellP:5");
    }

    @Test
    void testMapOfActions_6() {
        testActionIsMapped(6, "ID:6,draw_in_cellAction,oCellP:6");
    }

    @Test
    void testMapOfActions_7() {
        testActionIsMapped(7, "ID:7,draw_in_cellAction,oCellP:7");
    }

    @Test
    void testMapOfActions_8() {
        testActionIsMapped(8, "ID:8,draw_in_cellAction,oCellP:8");
    }

    @Test
    void testMapOfActions_9() {
        testActionIsMapped(9, "ID:9,detect_board_stateAction");
    }

    @Test
    void testMapOfActions_Terminated() {
        testActionIsMapped(-1, "Terminated");
    }

    @Test
    void testMapOfActions_Unrecognized() {
        testActionIsMapped(10, "Not recognized Action ID : 10");
    }

    private void testActionIsMapped(int actionID, String expected) {
        manualActionsSentVisualizer = new ManualActionsSentVisualizer(JSON_RESPONSE_TWO_ACTIONS_SENT, mockFacade);
        String actual = manualActionsSentVisualizer.getActionDesc(actionID).getValue();
        assertEquals(expected, actual);
    }
}