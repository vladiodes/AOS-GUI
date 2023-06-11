package backend.finalproject.IntegrationRequests;

import DTO.HttpRequests.StopRobotRequestDTO;

public class StopRobotRequest extends HttpRequest{

    public final static String ENDPOINT = "/Solver";
    public final static String REQUEST_TYPE = "DELETE";
    public final static String body = "";

    public StopRobotRequest(StopRobotRequestDTO stopRobotRequestDTO){
        super(ENDPOINT);
    }

    public String getBody() {
        return body;
    }
}
