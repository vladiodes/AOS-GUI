package backend.finalproject.IntegrationRequests;

import DTO.HttpRequests.HttpRequestDTO;
import DTO.HttpRequests.InitProjectRequestDTO;

public class IntegrationRequestsHandler {

    public String handle(InitProjectRequestDTO request) {
        return new InitProjectRequest(request).send();
    }

    public String handle(HttpRequestDTO request) {
        return request.visit(this);
    }
}
