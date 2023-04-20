package backend.finalproject.IntegrationRequests;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.*;
import utils.IntegrationRequestResponse;

import java.io.IOException;

public class StopRobotRequest extends HttpRequest{

    public final static String ENDPOINT = "/Solver";
    public final static String REQUEST_TYPE = "DELETE";
    public final static RequestBody body = null;

    public StopRobotRequest(){
        super(ENDPOINT);
    }
    @Override
    public IntegrationRequestResponse send() {
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            Request.Builder builder = new Request.Builder()
                    .url(endpoint)
                    .method(REQUEST_TYPE, body);
            for (String key : headers.keySet())
                builder.addHeader(key, headers.get(key));
            Request request = builder.build();
            Response response = client.newCall(request).execute();

            String jsonResponse = new GsonBuilder().create().toJson("");
            if (response.body() != null)
                jsonResponse = response.body().string();

            return IntegrationRequestResponse.fromJSON(jsonResponse);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
