package DTO.HttpRequests;

import backend.finalproject.IntegrationRequests.IntegrationRequestsHandler;

public class GetSimulatedStatesRequestDTO implements HttpRequestDTO{
    @Override
    public String visit(IntegrationRequestsHandler handler) {
        return handler.handle(this);
    }
}
