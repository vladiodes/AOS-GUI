package backend.finalproject.IntegrationRequests;

import DTO.HttpRequests.UpdateLocalVariableRequestDTO;
import com.google.gson.GsonBuilder;
import okhttp3.*;
import utils.IntegrationRequestResponse;

import java.io.IOException;

public class UpdateLocalVariableRequest extends HttpRequest {

    public final static String ENDPOINT = "/localvariable";
    public final static String REQUEST_TYPE = "POST";
    private final String body;

    public UpdateLocalVariableRequest(UpdateLocalVariableRequestDTO requestDTO) {
        super(ENDPOINT);
        body = requestDTO.toJson();
    }

    @Override
    public IntegrationRequestResponse send() {
        try{
            OkHttpClient client = new OkHttpClient().newBuilder().build();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody reqBody = RequestBody.create(mediaType, body);
            Request.Builder builder = new Request.Builder()
                    .url(endpoint)
                    .method(REQUEST_TYPE, reqBody);
            for(String key: headers.keySet())
                builder.addHeader(key, headers.get(key));

            Request request = builder.build();
            Response response = client.newCall(request).execute();

            String jsonResponse = new GsonBuilder().create().toJson("");
            if (response.body() != null)
                 jsonResponse = response.body().string();

            return IntegrationRequestResponse.fromJSON(jsonResponse);
        }catch (IOException e){
            throw new RuntimeException(e);
        }
    }
}
