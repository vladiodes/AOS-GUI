package backend.finalproject.IntegrationRequests;

import DTO.HttpRequests.GetExecutionOutcomeRequestDTO;
import DTO.HttpRequests.InitProjectRequestDTO;

public class GetExecutionOutcomeRequest extends HttpRequest{

    public final static String ENDPOINT = "/ExecutionOutcome?belief_size=";
    public final static String REQUEST_TYPE = "GET";
    public final static String body = null;

    public GetExecutionOutcomeRequest(int beliefSize) {
        super(ENDPOINT.concat(String.valueOf(beliefSize)));

    }

    public String getBody() {
        return body;
    }
}
