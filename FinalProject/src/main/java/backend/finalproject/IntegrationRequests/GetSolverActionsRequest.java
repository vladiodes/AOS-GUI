package backend.finalproject.IntegrationRequests;

import DTO.HttpRequests.GetSolverActionsRequestDTO;
import DTO.HttpRequests.InitProjectRequestDTO;

public class GetSolverActionsRequest extends HttpRequest{

    public final static String ENDPOINT = "/SolverAction";
    public final static String REQUEST_TYPE = "GET";
    public String body;

    public GetSolverActionsRequest(GetSolverActionsRequestDTO requestDTO) {
        super(ENDPOINT);
        body = requestDTO.toJson();
    }

    public String getBody() {
        return body;
    }
}
