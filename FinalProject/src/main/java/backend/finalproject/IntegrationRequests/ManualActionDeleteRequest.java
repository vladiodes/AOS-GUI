package backend.finalproject.IntegrationRequests;

public class ManualActionDeleteRequest extends  HttpRequest{
    public static final String ENDPOINT = "/ManualAction";
    public static final String REQUEST_TYPE = "DELETE";

    public ManualActionDeleteRequest() {
        super(ENDPOINT);
    }

    public String getBody(){
        return null;
    }
}
