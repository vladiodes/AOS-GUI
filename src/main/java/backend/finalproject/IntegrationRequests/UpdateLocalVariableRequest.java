package backend.finalproject.IntegrationRequests;

import DTO.HttpRequests.UpdateLocalVariableRequestDTO;

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
