package backend.finalproject.IntegrationRequests;

import DTO.HttpRequests.InitProjectRequestDTO;

public class IntegrationRequestsHandler {

    public String handle(InitProjectRequestDTO request) {
        return new InitProjectRequest(request).send();
    }
}
