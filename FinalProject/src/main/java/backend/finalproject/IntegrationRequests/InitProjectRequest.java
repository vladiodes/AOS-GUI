package backend.finalproject.IntegrationRequests;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.*;


import DTO.HttpRequests.InitProjectRequestDTO;
import utils.IntegrationRequestResponse;

import java.io.IOException;

public class InitProjectRequest extends HttpRequest {
    public final static String ENDPOINT = "/InitializeProject";
    public final static String REQUEST_TYPE = "POST";
    private final String body;

    public InitProjectRequest(InitProjectRequestDTO requestDTO) {
        super(ENDPOINT);
        body = requestDTO.toJson();
    }

    public String getBody() {
        return body;
    }
}
