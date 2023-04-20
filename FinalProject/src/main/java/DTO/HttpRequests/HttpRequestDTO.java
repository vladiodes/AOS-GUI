package DTO.HttpRequests;

import backend.finalproject.IntegrationRequests.IntegrationRequestsHandler;
import utils.RequestsResponse.InitProjectRequestResponse;
import utils.RequestsResponse.RequestResponse;

public interface HttpRequestDTO {
    RequestResponse visit(IntegrationRequestsHandler handler);
}
