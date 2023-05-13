package backend.finalproject.IntegrationRequestsTests;


import DTO.HttpRequests.ManualActionPutRequestDTO;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SendManActionTests {
    private final static String json_req = """
             {
                 "ActionID":1
             }
            """;

    @Test
    void testSendManAction(){
        ManualActionPutRequestDTO requestDTO = new ManualActionPutRequestDTO(1);
        Map<String,Object> actualMap = CompareJsonUtils.jsonToMap(requestDTO.toJSON());
        Map<String,Object> expectedMap = CompareJsonUtils.jsonToMap(json_req);
        assertTrue(CompareJsonUtils.compareMaps(actualMap,expectedMap));
    }

}
