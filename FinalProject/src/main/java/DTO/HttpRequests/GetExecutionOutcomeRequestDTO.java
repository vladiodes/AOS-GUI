package DTO.HttpRequests;

import backend.finalproject.IntegrationRequests.IntegrationRequestsHandler;
import utils.IntegrationRequestResponse;

public class GetExecutionOutcomeRequestDTO implements HttpRequestDTO{

    private final int belief_size;
    public GetExecutionOutcomeRequestDTO(int belief_size){
        this.belief_size = belief_size;
    }
    @Override
    public IntegrationRequestResponse visit(IntegrationRequestsHandler handler) {
        return handler.handle(this);
    }

    public int getBeliefSize() {
        return belief_size;
    }

}
