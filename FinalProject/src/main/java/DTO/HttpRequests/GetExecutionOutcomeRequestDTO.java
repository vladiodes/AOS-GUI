package DTO.HttpRequests;

import backend.finalproject.IntegrationRequests.IntegrationRequestsHandler;

public class GetExecutionOutcomeRequestDTO implements HttpRequestDTO{

    private final int belief_size;
    private final String filter;
    public GetExecutionOutcomeRequestDTO(int belief_size, String filter){
        this.belief_size = belief_size;
        this.filter = filter;
    }
    @Override
    public String visit(IntegrationRequestsHandler handler) {
        return handler.handle(this);
    }

    public int getBeliefSize() {
        return belief_size;
    }

    public String getFilter() {
        return filter;
    }

}
