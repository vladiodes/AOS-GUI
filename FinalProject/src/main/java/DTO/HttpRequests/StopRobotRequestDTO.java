package DTO.HttpRequests;

import backend.finalproject.IntegrationRequests.IntegrationRequestsHandler;
import utils.RequestsResponse.InitProjectRequestResponse;
import utils.RequestsResponse.RequestResponse;

public class StopRobotRequestDTO implements HttpRequestDTO{

    public StopRobotRequestDTO(){

    }
    @Override
    public RequestResponse visit(IntegrationRequestsHandler handler) {
        return handler.handle(this);
    }

}
