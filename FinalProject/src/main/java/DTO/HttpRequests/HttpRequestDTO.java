package DTO.HttpRequests;

import backend.finalproject.IntegrationRequests.IntegrationRequestsHandler;
import utils.IntegrationRequestResponse;

public interface HttpRequestDTO {
    IntegrationRequestResponse visit(IntegrationRequestsHandler handler);
}
