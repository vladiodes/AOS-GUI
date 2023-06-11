package backend.finalproject.IntegrationRequests;

import DTO.HttpRequests.ManualActionPutRequestDTO;

public class ManualActionRequest extends HttpRequest{
    public final static String ENDPOINT = "/ManualAction";
    public final static String REQUEST_TYPE = "POST";
    private final String body;

    public ManualActionRequest(ManualActionPutRequestDTO request) {
        super(ENDPOINT);
        body = request.toJSON();
    }

    public String getBody() {
        return body;
    }
}
