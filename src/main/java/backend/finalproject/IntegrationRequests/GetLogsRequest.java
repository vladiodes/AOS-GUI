package backend.finalproject.IntegrationRequests;

public class GetLogsRequest extends HttpRequest {
    public final static String ENDPOINT = "/LogMessage";
    public final static String REQUEST_TYPE = "GET";
    public GetLogsRequest() {
        super(ENDPOINT);
    }

    public String getBody(){
        return null;
    }


}
