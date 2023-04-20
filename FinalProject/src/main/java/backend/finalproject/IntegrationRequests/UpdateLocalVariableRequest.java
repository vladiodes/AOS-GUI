package backend.finalproject.IntegrationRequests;

import DTO.HttpRequests.UpdateLocalVariableRequestDTO;
import com.google.gson.GsonBuilder;
import okhttp3.*;
import utils.IntegrationRequestResponse;

import java.io.IOException;

public class UpdateLocalVariableRequest extends HttpRequest {

    public final static String ENDPOINT = "/localvariable";
    public final static String REQUEST_TYPE = "POST";
    private final String body;

    public UpdateLocalVariableRequest(UpdateLocalVariableRequestDTO requestDTO) {
        super(ENDPOINT);
        body = requestDTO.toJson();
    }

    public String getBody() {
        return body;
    }
}
