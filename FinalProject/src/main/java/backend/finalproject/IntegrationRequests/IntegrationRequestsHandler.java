package backend.finalproject.IntegrationRequests;

import DTO.HttpRequests.*;
import com.google.gson.GsonBuilder;
import okhttp3.*;
import utils.RequestsResponse.InitProjectRequestResponse;
import utils.RequestsResponse.RequestResponse;

import java.io.IOException;


public class IntegrationRequestsHandler {

    public InitProjectRequestResponse handle(InitProjectRequestDTO request) {
        InitProjectRequest initProjectRequest = new InitProjectRequest(request);
        return send(initProjectRequest, initProjectRequest.getBody(), InitProjectRequest.REQUEST_TYPE);
    }

    public InitProjectRequestResponse handle(UpdateLocalVariableRequestDTO request){
        UpdateLocalVariableRequest updateLocalVariableRequest = new UpdateLocalVariableRequest(request);
        return send(updateLocalVariableRequest, updateLocalVariableRequest.getBody(), UpdateLocalVariableRequest.REQUEST_TYPE);
    }

    public InitProjectRequestResponse handle(StopRobotRequestDTO request){
        StopRobotRequest stopRobotRequest = new StopRobotRequest(request);
        return send(stopRobotRequest, stopRobotRequest.getBody(), StopRobotRequest.REQUEST_TYPE);
    }

    public InitProjectRequestResponse handle(GetSolverActionsRequestDTO request){
        GetSolverActionsRequest getSolverReq = new GetSolverActionsRequest(request);
        return send(getSolverReq,null,GetSolverActionsRequest.REQUEST_TYPE);
    }



    public InitProjectRequestResponse handle(GetExecutionOutcomeRequestDTO request){
        GetExecutionOutcomeRequest getSolverReq = new GetExecutionOutcomeRequest(request.getBeliefSize());
        return send(getSolverReq,getSolverReq.getBody(),GetSolverActionsRequest.REQUEST_TYPE);
    }

    public RequestResponse handle(HttpRequestDTO request) {
        return request.visit(this);
    }



    public InitProjectRequestResponse send(HttpRequest httpRequest, String body, String REQUEST_TYPE) {
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody reqBody = body != null ? RequestBody.create(mediaType, body) : null;
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

            return InitProjectRequestResponse.fromJSON(jsonResponse);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
