package backend.finalproject.IntegrationRequestsTests;

import DTO.HttpRequests.InitProjectRequestDTO;
import backend.finalproject.IntegrationRequests.IntegrationRequestsHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.Map;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class InitProjectRequestTests {
    private final static String originalJsonBody = """
            {\s
                "PLPsDirectoryPath":"/home/vladiodes/Downloads/collectValuableToys-20230420T140316Z-001/collectValuableToys",
                "RunWithoutRebuild":false,
                "OnlyGenerateCode":true,
                "RosTarget":
                {
                    "WorkspaceDirectoryPath":"/home/vladiodes/catkin_ws/",
                    "TargetProjectLaunchFile":"/home/or/catkin_ws/src/aos_ros_target_project/launch/servers.launch",
                    "RosTargetProjectPackages":["aos_ros_target_project"],
                    "TargetProjectInitializationTimeInSeconds":10
                },
                "SolverConfiguration":{\s
                    "NumOfParticles":5234,
                    "ActionsToSimulate":[],
                    "IsInternalSimulation":true,
                    "PlanningTimePerMoveInSeconds":2,
                    "NumOfBeliefStateParticlesToSaveInDB":1,\s
                    "DebugOn":true
                    },
                "MiddlewareConfiguration":{
                    "DebugOn":true
                    \s
                }
            }""";
    private final static String jsonResponse = """
            {
                "ExecutionOutcome": [
                    {
                        "InitialBeliefeState": []
                    }
                ]
            }""";
    private final static String PLPsDirectoryPath_Key = "PLPsDirectoryPath";
    private final static String RunWithoutRebuild_Key = "RunWithoutRebuild";
    private final static String OnlyGenerateCode_Key = "OnlyGenerateCode";
    private final static String RosTarget_Key = "RosTarget";
    private final static String SolverConfiguration_Key = "SolverConfiguration";
    private final static String MiddlewareConfiguration_Key = "MiddlewareConfiguration";
    private final static String PLPsDirectoryPath = "/home/vladiodes/Downloads/collectValuableToys-20230420T140316Z-001/collectValuableToys";
    private final static boolean RunWithoutRebuild = false;
    private final static boolean OnlyGenerateCode = true;
    private final static String WorkspaceDirectoryPath = "/home/vladiodes/catkin_ws/";
    private final static String TargetProjectLaunchFile = "/home/or/catkin_ws/src/aos_ros_target_project/launch/servers.launch";
    private final static String[] RosTargetProjectPackages = {"aos_ros_target_project"};
    private final static int TargetProjectInitializationTimeInSeconds = 10;
    private final static int NumOfParticles = 5234;
    private final static String[] ActionsToSimulate = {};
    private final static boolean IsInternalSimulation = true;
    private final static int PlanningTimePerMoveInSeconds = 2;
    private final static int NumOfBeliefStateParticlesToSaveInDB = 1;
    private final static boolean SolverDebugOn = true;
    private final static boolean MiddlewareDebugOn = true;
    private static InitProjectRequestDTO initProjectRequestDTO;
    private static String generatedBody;
    private static Map<String, Object> originalMap;
    private static Map<String, Object> generatedMap;
    @Mock private static IntegrationRequestsHandler mockHandler;
    @BeforeAll
    static void setUp() {
        initProjectRequestDTO = new InitProjectRequestDTO.InitProjectRequestDTOBuilder()
                .setPLPsDirectoryPath(PLPsDirectoryPath)
                .setRunWithoutRebuild(RunWithoutRebuild)
                .setOnlyGenerateCode(OnlyGenerateCode)
                .setWorkspaceDirectoryPath(WorkspaceDirectoryPath)
                .setTargetProjectLaunchFile(TargetProjectLaunchFile)
                .setRosTargetProjectPackages(RosTargetProjectPackages)
                .setTargetProjectInitializationTimeInSeconds(TargetProjectInitializationTimeInSeconds)
                .setNumOfParticles(NumOfParticles)
                .setActionsToSimulate(ActionsToSimulate)
                .setIsInternalSimulation(IsInternalSimulation)
                .setPlanningTimePerMoveInSeconds(PlanningTimePerMoveInSeconds)
                .setBeliefStateParticlesToSave(NumOfBeliefStateParticlesToSaveInDB)
                .setDebugOn(SolverDebugOn)
                .setDebugOnMiddleware(MiddlewareDebugOn)
                .build();

        generatedBody = initProjectRequestDTO.toJson();
        originalMap = CompareJsonUtils.jsonToMap(originalJsonBody);
        generatedMap = CompareJsonUtils.jsonToMap(generatedBody);
        mockHandler = mock(IntegrationRequestsHandler.class);
        when(mockHandler.handle(initProjectRequestDTO)).thenReturn(jsonResponse);
    }

    @Test
    void testPlpsDirPath(){
        Assertions.assertEquals(originalMap.get(PLPsDirectoryPath_Key), generatedMap.get(PLPsDirectoryPath_Key));
    }

    @Test
    void testRunWithoutRebuild(){
        Assertions.assertEquals(originalMap.get(RunWithoutRebuild_Key), generatedMap.get(RunWithoutRebuild_Key));
    }

    @Test
    void testOnlyGenerateCode(){
        Assertions.assertEquals(originalMap.get(OnlyGenerateCode_Key), generatedMap.get(OnlyGenerateCode_Key));
    }

    @Test
    void testRosTarget(){
        try {
            Assertions.assertTrue(CompareJsonUtils.compareMaps((Map<String, Object>) originalMap.get(RosTarget_Key), (Map<String, Object>) generatedMap.get(RosTarget_Key)));
        }
        catch (Exception e){
            Assertions.fail();
        }
    }

    @Test
    void testSolverConfiguration(){
        try {
            Assertions.assertTrue(CompareJsonUtils.compareMaps((Map<String, Object>) originalMap.get(SolverConfiguration_Key), (Map<String, Object>) generatedMap.get(SolverConfiguration_Key)));
        }
        catch (Exception e){
            Assertions.fail();
        }
    }

    @Test
    void testMiddlewareConfiguration(){
        try {
            Assertions.assertTrue(CompareJsonUtils.compareMaps((Map<String, Object>) originalMap.get(MiddlewareConfiguration_Key), (Map<String, Object>) generatedMap.get(MiddlewareConfiguration_Key)));
        }
        catch (Exception e){
            Assertions.fail();
        }
    }

    @Test
    void testResponseFromServer(){
        Assertions.assertEquals(jsonResponse, initProjectRequestDTO.visit(mockHandler));
    }

}
