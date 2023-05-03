package backend.finalproject.IntegrationRequests;

public class GetSimulatedStatesRequest extends HttpRequest {
    public final static String ENDPOINT = "/SimulatedState";
    public final static String REQUEST_TYPE = "GET";
    public GetSimulatedStatesRequest() {
        super(ENDPOINT);
    }

    public String getBody(){
        return null;
    }
}
