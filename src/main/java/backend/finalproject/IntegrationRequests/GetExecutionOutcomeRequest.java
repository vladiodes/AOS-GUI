package backend.finalproject.IntegrationRequests;

import DTO.HttpRequests.GetExecutionOutcomeRequestDTO;
import DTO.HttpRequests.InitProjectRequestDTO;

public class GetExecutionOutcomeRequest extends HttpRequest{

    public final static String ENDPOINT = "/ExecutionOutcome";
    public final static String REQUEST_TYPE = "GET";
    public final static String body = null;

    public GetExecutionOutcomeRequest(int beliefSize,String filter) {
        super(String.format("%s?belief_size=%d&filter=%s",ENDPOINT,beliefSize,filter));
    }

    public String getBody() {
        return body;
    }
}
