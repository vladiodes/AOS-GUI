package backend.finalproject.IntegrationRequests;

public class ManualActionGetRequest extends HttpRequest {
    public static final String REQUEST_TYPE = "GET";
    public static final String ENDPOINT = "/ManualAction";

    public ManualActionGetRequest(){
        super(ENDPOINT);
    }

    public String getBody() {
        return null;
    }
}
