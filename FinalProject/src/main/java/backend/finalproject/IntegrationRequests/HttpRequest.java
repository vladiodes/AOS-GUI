package backend.finalproject.IntegrationRequests;

import java.util.*;

public abstract class HttpRequest {
    public final static String BASE_URL ="http://localhost:5000";
    protected String endpoint;
    protected Map<String, String> headers;

    public HttpRequest(String endpoint) {
        this.endpoint = BASE_URL + endpoint;
        headers = new HashMap<String, String>();
        headers.put("Content-Type", "application/json");
        headers.put("Accept","*/*");
    }

    public abstract String send();
}
