package backend.finalproject.IntegrationRequests;


import DTO.HttpRequests.InitProjectRequestDTO;

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
