package backend.finalproject.IntegrationRequests;

import DTO.HttpRequests.HttpRequestDTO;
import DTO.HttpRequests.InitProjectRequestDTO;
import DTO.HttpRequests.StopRobotRequestDTO;
import DTO.HttpRequests.UpdateLocalVariableRequestDTO;
import com.google.gson.GsonBuilder;
import okhttp3.*;
import utils.IntegrationRequestResponse;

import java.io.IOException;


public class IntegrationRequestsHandler {

    public IntegrationRequestResponse handle(InitProjectRequestDTO request) {
        InitProjectRequest initProjectRequest = new InitProjectRequest(request);
        return send(initProjectRequest, initProjectRequest.getBody(), InitProjectRequest.REQUEST_TYPE);
    }

    public IntegrationRequestResponse handle(UpdateLocalVariableRequestDTO request){
        UpdateLocalVariableRequest updateLocalVariableRequest = new UpdateLocalVariableRequest(request);
        return send(updateLocalVariableRequest, updateLocalVariableRequest.getBody(), UpdateLocalVariableRequest.REQUEST_TYPE);
    }

    public IntegrationRequestResponse handle(StopRobotRequestDTO request){
        StopRobotRequest stopRobotRequest = new StopRobotRequest(request);
        return send(stopRobotRequest, stopRobotRequest.getBody(), StopRobotRequest.REQUEST_TYPE);
    }

    public IntegrationRequestResponse handle(HttpRequestDTO request) {
        return request.visit(this);
    }

    public IntegrationRequestResponse send(HttpRequest httpRequest, String body, String REQUEST_TYPE) {
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody reqBody = RequestBody.create(mediaType, body);
            Request.Builder builder = new Request.Builder()
                    .url(httpRequest.endpoint)
                    .method(REQUEST_TYPE, reqBody);
            for (String key : httpRequest.headers.keySet())
                builder.addHeader(key, httpRequest.headers.get(key));
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
