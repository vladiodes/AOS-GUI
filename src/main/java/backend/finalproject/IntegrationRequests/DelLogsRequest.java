package backend.finalproject.IntegrationRequests;

public class DelLogsRequest extends HttpRequest{
    public static final String ENDPOINT = "/LogMessage";
    public static final String REQUEST_TYPE = "DELETE";

    public DelLogsRequest() {
        super(ENDPOINT);
    }

    public String getBody(){
        return null;
    }
}
