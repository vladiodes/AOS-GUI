package backend.finalproject.IntegrationRequests;

import DTO.HttpRequests.HttpRequestDTO;
import DTO.HttpRequests.InitProjectRequestDTO;
import DTO.HttpRequests.StopRobotRequestDTO;
import DTO.HttpRequests.UpdateLocalVariableRequestDTO;
import utils.IntegrationRequestResponse;


public class IntegrationRequestsHandler {

    public IntegrationRequestResponse handle(InitProjectRequestDTO request) {
        return new InitProjectRequest(request).send();
    }

    public IntegrationRequestResponse handle(UpdateLocalVariableRequestDTO request){
        return new UpdateLocalVariableRequest(request).send();
    }

    public IntegrationRequestResponse handle(StopRobotRequestDTO request){
        return new StopRobotRequest().send();
    }

    public IntegrationRequestResponse handle(HttpRequestDTO request) {
        return request.visit(this);
    }
}
