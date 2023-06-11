package backend.finalproject.JsonVisualizerTests;

import backend.finalproject.IAOSFacade;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import de.saxsys.javafx.test.JfxRunner;
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

import java.util.LinkedList;
import java.util.List;

@RunWith(JfxRunner.class)
class SimulatedStatesTests {
    private final static String GET_EXEC_OUTCOME_RESP = """
                        {
                             "ExecutionOutcome": [
                                 {
                                     "InitialBeliefeState": [
                                         {
                                             "grid": [
                                                 0,
                                                 0,
                                                 0,
                                                 0,
                                                 0,
                                                 0,
                                                 0,
                                                 0,
                                                 0
                                             ],
                                             "isRobotTurn": true
                                         }
                                     ]
                                 },
                                 {
                                     "ActionSequenceId": 1,
                                     "ActionDetails": {
                                         "ActionID": 0,
                                         "ActionDescription": "ID:0,draw_in_cellAction,oCellP:0",
                                         "ActionName": "draw_in_cell",
                                         "ActionConstantParameters": [
                                             {
                                                 "ParameterName": "oCellP",
                                                 "Value": "0"
                                             }
                                         ]
                                     },
                                     "SolverSentActionTime": "05/03/2023 23:41",
                                     "ModuleExecutionStartTime": "05/03/2023 23:41",
                                     "ModuleExecutionEndTime": "05/03/2023 23:41",
                                     "ModuleResponseText": "draw_in_cell_res_success",
                                     "BeliefStatesAfterExecution": [
                                         {
                                             "grid": [
                                                 1,
                                                 0,
                                                 0,
                                                 0,
                                                 0,
                                                 0,
                                                 0,
                                                 0,
                                                 0
                                             ],
                                             "isRobotTurn": false
                                         }
                                     ]
                                 },
                                 {
                                     "ActionSequenceId": 2,
                                     "ActionDetails": {
                                         "ActionID": 1,
                                         "ActionDescription": "ID:1,draw_in_cellAction,oCellP:1",
                                         "ActionName": "draw_in_cell",
                                         "ActionConstantParameters": [
                                             {
                                                 "ParameterName": "oCellP",
                                                 "Value": "1"
                                             }
                                         ]
                                     },
                                     "SolverSentActionTime": "05/03/2023 23:41",
                                     "ModuleExecutionStartTime": "05/03/2023 23:41",
                                     "ModuleExecutionEndTime": "05/03/2023 23:41",
                                     "ModuleResponseText": "draw_in_cell_res_success",
                                     "BeliefStatesAfterExecution": [
                                         {
                                             "grid": [
                                                 1,
                                                 0,
                                                 0,
                                                 0,
                                                 0,
                                                 0,
                                                 0,
                                                 2,
                                                 0
                                             ],
                                             "isRobotTurn": true
                                         }
                                     ]
                                 },
                                 {
                                     "ActionSequenceId": 3,
                                     "ActionDetails": {
                                         "ActionID": 8,
                                         "ActionDescription": "ID:8,draw_in_cellAction,oCellP:8",
                                         "ActionName": "draw_in_cell",
                                         "ActionConstantParameters": [
                                             {
                                                 "ParameterName": "oCellP",
                                                 "Value": "8"
                                             }
                                         ]
                                     },
                                     "SolverSentActionTime": "05/03/2023 23:41",
                                     "ModuleExecutionStartTime": "05/03/2023 23:41",
                                     "ModuleExecutionEndTime": "05/03/2023 23:41",
                                     "ModuleResponseText": "draw_in_cell_res_success",
                                     "BeliefStatesAfterExecution": [
                                         {
                                             "grid": [
                                                 1,
                                                 0,
                                                 0,
                                                 0,
                                                 0,
                                                 0,
                                                 0,
                                                 2,
                                                 1
                                             ],
                                             "isRobotTurn": false
                                         }
                                     ]
                                 },
                                 {
                                     "ActionSequenceId": 4,
                                     "ActionDetails": {
                                         "ActionID": 8,
                                         "ActionDescription": "ID:8,draw_in_cellAction,oCellP:8",
                                         "ActionName": "draw_in_cell",
                                         "ActionConstantParameters": [
                                             {
                                                 "ParameterName": "oCellP",
                                                 "Value": "8"
                                             }
                                         ]
                                     },
                                     "SolverSentActionTime": "05/03/2023 23:43",
                                     "ModuleExecutionStartTime": "05/03/2023 23:43",
                                     "ModuleExecutionEndTime": "05/03/2023 23:43",
                                     "ModuleResponseText": "draw_in_cell_res_success",
                                     "BeliefStatesAfterExecution": [
                                         {
                                             "grid": [
                                                 1,
                                                 0,
                                                 0,
                                                 0,
                                                 2,
                                                 0,
                                                 0,
                                                 2,
                                                 1
                                             ],
                                             "isRobotTurn": true
                                         }
                                     ]
                                 },
                                 {
                                     "ActionSequenceId": 5,
                                     "ActionDetails": {
                                         "ActionID": 5,
                                         "ActionDescription": "ID:5,draw_in_cellAction,oCellP:5",
                                         "ActionName": "draw_in_cell",
                                         "ActionConstantParameters": [
                                             {
                                                 "ParameterName": "oCellP",
                                                 "Value": "5"
                                             }
                                         ]
                                     },
                                     "SolverSentActionTime": "05/03/2023 23:43",
                                     "ModuleExecutionStartTime": "05/03/2023 23:43",
                                     "ModuleExecutionEndTime": "05/03/2023 23:43",
                                     "ModuleResponseText": "draw_in_cell_res_success",
                                     "BeliefStatesAfterExecution": [
                                         {
                                             "grid": [
                                                 1,
                                                 0,
                                                 2,
                                                 0,
                                                 0,
                                                 1,
                                                 0,
                                                 2,
                                                 1
                                             ],
                                             "isRobotTurn": false
                                         }
                                     ]
                                 },
                                 {
                                     "ActionSequenceId": 6,
                                     "ActionDetails": {
                                         "ActionID": 5,
                                         "ActionDescription": "ID:5,draw_in_cellAction,oCellP:5",
                                         "ActionName": "draw_in_cell",
                                         "ActionConstantParameters": [
                                             {
                                                 "ParameterName": "oCellP",
                                                 "Value": "5"
                                             }
                                         ]
                                     },
                                     "SolverSentActionTime": "05/03/2023 23:43",
                                     "ModuleExecutionStartTime": "05/03/2023 23:43",
                                     "ModuleExecutionEndTime": "05/03/2023 23:43",
                                     "ModuleResponseText": "draw_in_cell_res_success",
                                     "BeliefStatesAfterExecution": [
                                         {
                                             "grid": [
                                                 1,
                                                 0,
                                                 0,
                                                 0,
                                                 2,
                                                 2,
                                                 2,
                                                 0,
                                                 1
                                             ],
                                             "isRobotTurn": true
                                         }
                                     ]
                                 },
                                 {
                                     "ActionSequenceId": 7,
                                     "ActionDetails": {
                                         "ActionID": 5,
                                         "ActionDescription": "ID:5,draw_in_cellAction,oCellP:5",
                                         "ActionName": "draw_in_cell",
                                         "ActionConstantParameters": [
                                             {
                                                 "ParameterName": "oCellP",
                                                 "Value": "5"
                                             }
                                         ]
                                     },
                                     "SolverSentActionTime": "05/03/2023 23:43",
                                     "ModuleExecutionStartTime": "05/03/2023 23:43",
                                     "ModuleExecutionEndTime": "05/03/2023 23:43",
                                     "ModuleResponseText": "draw_in_cell_res_success",
                                     "BeliefStatesAfterExecution": [
                                         {
                                             "grid": [
                                                 1,
                                                 2,
                                                 0,
                                                 2,
                                                 0,
                                                 2,
                                                 0,
                                                 0,
                                                 1
                                             ],
                                             "isRobotTurn": false
                                         }
                                     ]
                                 }
                             ]
                         }
            """;
    private final static String GET_SIMULATED_STATES_RESPONSE = """
                    [
                        {
                            "_id": ObjectId("6452c6e41332957482002b13"),
                            "ActionSequnceId": 0,
                            "SimulatedState": {
                                "grid": [
                                    0,
                                    0,
                                    0,
                                    0,
                                    0,
                                    0,
                                    0,
                                    0,
                                    0
                                ],
                                "isRobotTurn": true
                            }
                        },
                        {
                            "_id": ObjectId("6452c6e41332957482002b18"),
                            "ActionSequnceId": 1,
                            "SimulatedState": {
                                "grid": [
                                    1,
                                    0,
                                    0,
                                    0,
                                    0,
                                    0,
                                    0,
                                    0,
                                    0
                                ],
                                "isRobotTurn": false
                            }
                        },
                        {
                            "_id": ObjectId("6452c6e91332957482002b1e"),
                            "ActionSequnceId": 2,
                            "SimulatedState": {
                                "grid": [
                                    1,
                                    0,
                                    0,
                                    0,
                                    2,
                                    0,
                                    0,
                                    0,
                                    0
                                ],
                                "isRobotTurn": true
                            }
                        },
                        {
                            "_id": ObjectId("6452c7011332957482002b24"),
                            "ActionSequnceId": 3,
                            "SimulatedState": {
                                "grid": [
                                    1,
                                    0,
                                    0,
                                    0,
                                    2,
                                    0,
                                    0,
                                    0,
                                    1
                                ],
                                "isRobotTurn": false
                            }
                        },
                        {
                            "_id": ObjectId("6452c75b1332957482002b2a"),
                            "ActionSequnceId": 4,
                            "SimulatedState": {
                                "grid": [
                                    1,
                                    0,
                                    0,
                                    0,
                                    2,
                                    2,
                                    0,
                                    0,
                                    1
                                ],
                                "isRobotTurn": true
                            }
                        },
                        {
                            "_id": ObjectId("6452c7601332957482002b30"),
                            "ActionSequnceId": 5,
                            "SimulatedState": {
                                "grid": [
                                    1,
                                    0,
                                    0,
                                    0,
                                    2,
                                    2,
                                    0,
                                    0,
                                    1
                                ],
                                "isRobotTurn": false
                            }
                        },
                        {
                            "_id": ObjectId("6452c7651332957482002b36"),
                            "ActionSequnceId": 6,
                            "SimulatedState": {
                                "grid": [
                                    1,
                                    0,
                                    0,
                                    0,
                                    2,
                                    2,
                                    2,
                                    0,
                                    1
                                ],
                                "isRobotTurn": true
                            }
                        },
                        {
                            "_id": ObjectId("6452c76a1332957482002b3c"),
                            "ActionSequnceId": 7,
                            "SimulatedState": {
                                "grid": [
                                    1,
                                    0,
                                    0,
                                    0,
                                    2,
                                    2,
                                    2,
                                    0,
                                    1
                                ],
                                "isRobotTurn": false
                            }
                        }
                    ]
            """;
    private final static String[] ACTION_DESC = new String[]{
            "Initial State",
            "ID:0,draw_in_cellAction,oCellP:0",
            "ID:1,draw_in_cellAction,oCellP:1",
            "ID:8,draw_in_cellAction,oCellP:8",
            "ID:8,draw_in_cellAction,oCellP:8",
            "ID:5,draw_in_cellAction,oCellP:5",
            "ID:5,draw_in_cellAction,oCellP:5",
            "ID:5,draw_in_cellAction,oCellP:5",
    };
    private SimulatedStateVisualizer visualizer;
    @Mock
    private IAOSFacade mockFacade = Mockito.mock(IAOSFacade.class);

    @BeforeAll
    static void initJfxRuntime() {
        PlatformInitializer.initPlatform();
    }

    void setUp(String json) {
        Mockito.when(mockFacade.sendRequest(Mockito.any())).thenReturn(Response.OK(GET_EXEC_OUTCOME_RESP));
        visualizer = new SimulatedStateVisualizer(json, mockFacade);
    }

    @Test
    void testActionsDescriptionEqualSize() {
        setUp(GET_SIMULATED_STATES_RESPONSE);
        List<String> actions = visualizer.getActionDescriptions();
        Assertions.assertEquals(actions.size(), 8);
    }

    @Test
    void testActionsDescriptionAllNamesExist() {
        setUp(GET_SIMULATED_STATES_RESPONSE);
        List<String> actions = visualizer.getActionDescriptions();
        for (int i = 0; i < ACTION_DESC.length; i++) {
            Assertions.assertEquals(actions.get(i), ACTION_DESC[i]);
        }
    }

    void testChangesAdded(String prevStateJson, String nextStateJson, int expectedSize) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonElement prevState = gson.fromJson(prevStateJson, JsonElement.class);
        JsonElement nextState = gson.fromJson(nextStateJson, JsonElement.class);
        JsonTreeViewVisualizer treeViewVisualizer = new JsonTreeViewVisualizer(String.format("{\"SimulatedState\":%s}", nextStateJson));
        ObservableSet<TreeItem<String>> set = SimulatedStateVisualizer.SimulatedStateNode.highlightChangedVariablesInState(prevState, nextState, (TreeView<String>) treeViewVisualizer.displayJSON(), false);
        Assertions.assertEquals(set.size(), expectedSize);
    }

    @Test
    void testRobotTurnOnlyChanged() {
        String prev = """
                 {
                             "grid": [
                                 1,
                                 0,
                                 0,
                                 0,
                                 2,
                                 2,
                                 2,
                                 0,
                                 1
                             ],
                             "isRobotTurn": true
                         }
                """;
        String next = """
                {
                            "grid": [
                                1,
                                0,
                                0,
                                0,
                                2,
                                2,
                                2,
                                0,
                                1
                            ],
                            "isRobotTurn": false
                        }
                """;
        testChangesAdded(prev, next, 1);
    }

    @Test
    void testRobotTurnChangedAndTwoCellsChanged() {
        String prev = """
                 {
                             "grid": [
                                 2,
                                 1,
                                 0,
                                 0,
                                 2,
                                 2,
                                 2,
                                 0,
                                 1
                             ],
                             "isRobotTurn": true
                         }
                """;
        String next = """
                {
                            "grid": [
                                1,
                                0,
                                0,
                                0,
                                2,
                                2,
                                2,
                                0,
                                1
                            ],
                            "isRobotTurn": false
                        }
                """;
        testChangesAdded(prev, next, 4);
    }

    @Test
    void testNoChanges() {
        String prev = """
                 {
                             "grid": [
                                 1,
                                 0,
                                 0,
                                 0,
                                 2,
                                 2,
                                 2,
                                 0,
                                 1
                             ],
                             "isRobotTurn": true
                         }
                """;
        testChangesAdded(prev, prev, 0);
    }

    @Test
    void testNestedChanges() {
        String prev = """
                 {
                 "field1": {
                    "field1.field1": 2,
                    "field1.field2": 2
                 }
                         }
                """;
        String next = """
                 {
                 "field1": {
                    "field1.field1": 1,
                    "field1.field2": 2
                 }
                         }
                """;
        testChangesAdded(prev, next, 2);
    }

    @Test
    void testNestedChangesDeep() {
        String prev = """
                 {
                 "field1": {
                    "field1.field1": {
                        "field1.field1.field1": 2,
                        "field1.field1.field2": 2
                    },
                    "field1.field2": 2
                 }
                         }
                """;
        String next = """
                 {
                 "field1": {
                    "field1.field1": {
                        "field1.field1.field1": 1,
                        "field1.field1.field2": 2
                    },
                    "field1.field2": 2
                 }
                         }
                """;
        testChangesAdded(prev, next, 3);
    }

    @Test
    void testNestedChangesDeepSameDepth() {
        String prev = """
                {
                             "field1": {
                                "field1.field1": {
                                    "field1.field1.field1": 2,
                                    "field1.field1.field2": 2
                                },
                                "field1.field2": 2
                             }
                                     }
                            """;
        String next = """
                {
                "field1": {
                    "field1.field1": {
                        "field1.field1.field1": 1,
                        "field1.field1.field2": 1
                    },
                    "field1.field2": 2
                }
                        }
                """;
        testChangesAdded(prev, next, 4);
    }

    @Test
    void testAllChanged(){
        String prev = """
                {
                             "field1": {
                                "field1.field1": {
                                    "field1.field1.field1": 2,
                                    "field1.field1.field2": 2
                                },
                                "field1.field2": 2
                             }
                                     }
                            """;
        String next = """
                {
                "field1": {
                    "field1.field1": {
                        "field1.field1.field1": 1,
                        "field1.field1.field2": 1
                    },
                    "field1.field2": 3
                }
                        }
                """;
        testChangesAdded(prev, next, 5);
    }

    @Test
    void testNestedInsideArray(){
        String prev = """
                {
                             "field1": {
                                "field1.field1": {
                                    "field1.field1.field1": 2,
                                    "field1.field1.field2": 2
                                },
                                "field1.field2": 2
                             },
                             "field2": [
                                1,
                                2,
                                3
                             ]
                                     }
                            """;
        String next = """
                {
                             "field1": {
                                "field1.field1": {
                                    "field1.field1.field1": 2,
                                    "field1.field1.field2": 2
                                },
                                "field1.field2": 2
                             },
                             "field2": [
                                4,
                                5,
                                6
                             ]
                                     }
                            """;
        testChangesAdded(prev, next, 4);
    }
}