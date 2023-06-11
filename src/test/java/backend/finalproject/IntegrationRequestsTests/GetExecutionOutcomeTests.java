package backend.finalproject.IntegrationRequestsTests;

import DTO.HttpRequests.GetExecutionOutcomeRequestDTO;
import backend.finalproject.IntegrationRequests.IntegrationRequestsHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GetExecutionOutcomeTests {
    private GetExecutionOutcomeRequestDTO requestDTO;
    private final static String jsonResponse = """
            {
                "ExecutionOutcome": [
                    {
                        "InitialBeliefeState": []
                    }
                ]
            }""";
    private final static int belief_size = 1;
    @Mock private IntegrationRequestsHandler mockHandler;
    @BeforeEach
    void setUp(){
        requestDTO = new GetExecutionOutcomeRequestDTO(belief_size,"");
        mockHandler = mock(IntegrationRequestsHandler.class);
        when(mockHandler.handle(requestDTO)).thenReturn(jsonResponse);
    }

    @Test
    void testEmptyBody(){
        Assertions.assertEquals(requestDTO.visit(mockHandler),jsonResponse);
    }
}
