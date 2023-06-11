package backend.finalproject.IntegrationRequestsTests;

import DTO.HttpRequests.GetSolverActionsRequestDTO;
import backend.finalproject.IntegrationRequests.IntegrationRequestsHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GetSolverActionsTests {
    private GetSolverActionsRequestDTO requestDTO;
    private final static String jsonResponse = "[]";
    @Mock private IntegrationRequestsHandler mockHandler;

    @BeforeEach
    void setUp(){
        requestDTO = new GetSolverActionsRequestDTO();
    }

    @Test
    void testEmptyBody(){
        Assertions.assertEquals(requestDTO.toJson(), "{}");
    }

    @Test
    void testResponseFromServer(){
        mockHandler = mock(IntegrationRequestsHandler.class);
        when(mockHandler.handle(requestDTO)).thenReturn(jsonResponse);
        Assertions.assertEquals(mockHandler.handle(requestDTO), jsonResponse);
    }
}
