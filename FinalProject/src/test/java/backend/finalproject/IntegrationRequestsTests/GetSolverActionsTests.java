package backend.finalproject.IntegrationRequestsTests;

import DTO.HttpRequests.GetSolverActionsRequestDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GetSolverActionsTests {
    private GetSolverActionsRequestDTO requestDTO;

    @BeforeEach
    void setUp(){
        requestDTO = new GetSolverActionsRequestDTO();
    }

    @Test
    void testEmptyBody(){
        Assertions.assertEquals(requestDTO.toJson(), "{}");
    }
}
