package DTO.HttpRequests;

import backend.finalproject.IntegrationRequests.IntegrationRequestsHandler;
import utils.IntegrationRequestResponse;

public class StopRobotRequestDTO implements HttpRequestDTO{

    public StopRobotRequestDTO(){

    }
    @Override
    public IntegrationRequestResponse visit(IntegrationRequestsHandler handler) {
        return handler.handle(this);
    }

    //TODO: ask Vladi maybe we should remove this and use the ctr from the front, since there is nothing to build
    public static class StopRobotRequestBuilder{
        public final StopRobotRequestDTO instance = new StopRobotRequestDTO();
        public StopRobotRequestDTO build(){
            return instance;
        }
    }
}
