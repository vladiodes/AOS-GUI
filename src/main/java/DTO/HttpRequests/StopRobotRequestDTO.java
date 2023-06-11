package DTO.HttpRequests;

import backend.finalproject.IntegrationRequests.IntegrationRequestsHandler;

public class StopRobotRequestDTO implements HttpRequestDTO{

    public StopRobotRequestDTO(){

    }
    @Override
    public String visit(IntegrationRequestsHandler handler) {
        return handler.handle(this);
    }

}
