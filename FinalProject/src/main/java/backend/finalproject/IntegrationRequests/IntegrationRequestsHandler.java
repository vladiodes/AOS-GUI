package backend.finalproject.IntegrationRequests;

import DTO.HttpRequests.HttpRequestDTO;
import DTO.HttpRequests.InitProjectRequestDTO;
import utils.IntegrationRequestResponse;

import java.io.IOException;

public class IntegrationRequestsHandler {

    public IntegrationRequestResponse handle(InitProjectRequestDTO request) {
        return new InitProjectRequest(request).send();
    }

    public IntegrationRequestResponse handle(HttpRequestDTO request) {
        return request.visit(this);
    }
}
