package backend.finalproject.IntegrationRequests;

import com.google.gson.Gson;
import okhttp3.*;


import DTO.HttpRequests.InitProjectRequestDTO;
import utils.IntegrationRequestResponse;

import java.io.IOException;

public class InitProjectRequest extends HttpRequest {
    public final static String ENDPOINT = "/InitializeProject";
    public final static String REQUEST_TYPE = "POST";
    private final String body;

    public InitProjectRequest(InitProjectRequestDTO requestDTO) {
        super(ENDPOINT);
        body = requestDTO.toJson();
    }

    public IntegrationRequestResponse send() {
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody reqBody = RequestBody.create(mediaType, body);
            Request.Builder builder = new Request.Builder()
                    .url(endpoint)
                    .method(REQUEST_TYPE, reqBody);
            for (String key : headers.keySet())
                builder.addHeader(key, headers.get(key));
            Request request = builder.build();
            Response response = client.newCall(request).execute();
            String jsonResponse = response.body().string();

            return IntegrationRequestResponse.fromJSON(jsonResponse);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
