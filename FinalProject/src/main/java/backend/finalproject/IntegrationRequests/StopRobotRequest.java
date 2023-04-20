package backend.finalproject.IntegrationRequests;

import DTO.HttpRequests.StopRobotRequestDTO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.*;
import utils.IntegrationRequestResponse;

import java.io.IOException;

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
