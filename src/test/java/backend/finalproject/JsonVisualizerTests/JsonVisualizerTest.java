package backend.finalproject.JsonVisualizerTests;

import de.saxsys.javafx.test.JfxRunner;
import frontend.finalproject.ServerResponseDisplayers.JsonTableViewVisualizer;
import javafx.application.Platform;
import javafx.scene.Node;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;

@RunWith(JfxRunner.class)
class JsonVisualizerTest {

    private final static String SINGLE_LIST_STRING = """
            [
                "This is a sample response from the server",
                "Another response",
                "And another"
            ]
            """;

    private final static String NESTED_LIST_STRING = """
            [
                "This is a sample response from the server",
                "Another response",
                "And another",
                [
                    "This is a sample response from the server",
                    "Another response",
                    "And another"
                ]
            ]
            """;

    private final static String SIMPLE_JSON_RESPONSE = """
            {
                "Field1": 1,
                "Field2": "Value2"
            }
            """;

    private final static String NESTED_JSON_RESPONSE = """
            {
                "Field1": 1,
                "Field2": "Value2",
                "Field3": {
                    "Field4": 2,
                    "Field5": "Value5"
                }
            }
            """;

    private final static String JSON_RESPONSE_WITH_LIST = """
            {
                "Field1": 1,
                "Field2": "Value2",
                "Field3": [
                    "This is a sample response from the server",
                    "Another response",
                    "And another"
                ]
            }
            """;

    private final static String JSON_RESPONSE_NESTED_OBJECTS_INSIDE_LIST = """
            {
                "Field1": 1,
                "Field2": "Value2",
                "Field3": [
                    {
                        "Field4": 2,
                        "Field5": "Value5"
                    },
                    {
                        "Field6": 3,
                        "Field7": "Value7"
                    }
                ]
            }
            """;


    private JsonTableViewVisualizer visualizer;

    @BeforeAll
    static void initJfxRuntime() {
        PlatformInitializer.initPlatform();
    }


    @Test
    void testSingleListString() {
        testVisualizer(SINGLE_LIST_STRING);
    }

    @Test
    void testNestedListString() {
        testVisualizer(NESTED_LIST_STRING);
    }

    @Test
    void testSimpleJsonResponse() {
        testVisualizer(SIMPLE_JSON_RESPONSE);
    }

    @Test
    void testNestedJsonResponse() {
        testVisualizer(NESTED_JSON_RESPONSE);
    }

    @Test
    void testJsonResponseWithList() {
        testVisualizer(JSON_RESPONSE_WITH_LIST);
    }

    @Test
    void testJsonResponseNestedObjectsInsideList() {
        testVisualizer(JSON_RESPONSE_NESTED_OBJECTS_INSIDE_LIST);
    }

    @Test
    void testEmptyJson() {
        testVisualizer("{}");
    }

    @Test
    void testEmptyJsonArray() {
        testVisualizer("[]");
    }

    @Test
    void testSimpleString(){
        testVisualizer("\"This is a simple string\"");
    }

    @Test
    void testSimpleNum(){
        testVisualizer("123");
    }



    private void testVisualizer(String json) {
        visualizer = new JsonTableViewVisualizer(json);
        Node node = visualizer.displayJSON();
        Assertions.assertNotNull(node);
    }
}