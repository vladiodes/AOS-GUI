package utils.RequestsResponse;

import java.util.List;

public class GetExecutionOutcomeRequestResponse implements RequestResponse {
    private List<ExecutionOutcome> ExecutionOutcome;

}

class ExecutionOutcome {
    private List<String> initialBeliefeState;
}
