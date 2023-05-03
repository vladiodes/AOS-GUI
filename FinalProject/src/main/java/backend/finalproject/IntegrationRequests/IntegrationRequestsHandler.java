package backend.finalproject.IntegrationRequests;

import DTO.HttpRequests.*;
import com.google.gson.GsonBuilder;
import okhttp3.*;

import java.io.IOException;


public class IntegrationRequestsHandler {

    public String handle(InitProjectRequestDTO request) {
        InitProjectRequest initProjectRequest = new InitProjectRequest(request);
        return send(initProjectRequest, initProjectRequest.getBody(), InitProjectRequest.REQUEST_TYPE);
    }

    public String handle(UpdateLocalVariableRequestDTO request){
        UpdateLocalVariableRequest updateLocalVariableRequest = new UpdateLocalVariableRequest(request);
        return send(updateLocalVariableRequest, updateLocalVariableRequest.getBody(), UpdateLocalVariableRequest.REQUEST_TYPE);
    }

    public String handle(StopRobotRequestDTO request){
        StopRobotRequest stopRobotRequest = new StopRobotRequest(request);
        String resp = send(stopRobotRequest, stopRobotRequest.getBody(), StopRobotRequest.REQUEST_TYPE);
        return null; // nothing to return
    }

    public String handle(GetSolverActionsRequestDTO request){
        GetSolverActionsRequest getSolverReq = new GetSolverActionsRequest(request);
        return send(getSolverReq,null,GetSolverActionsRequest.REQUEST_TYPE);
    }

    public String handle(GetExecutionOutcomeRequestDTO request){
        GetExecutionOutcomeRequest getSolverReq = new GetExecutionOutcomeRequest(request.getBeliefSize());
        return send(getSolverReq,getSolverReq.getBody(),GetSolverActionsRequest.REQUEST_TYPE);
    }

    public String handle(HttpRequestDTO request) {
        return request.visit(this);
    }

    public String handle(ManualActionPutRequestDTO.ManualActionDeleteRequestDTO request) {
        ManualActionDeleteRequest delReq = new ManualActionDeleteRequest();
        return send(delReq, delReq.getBody(), ManualActionDeleteRequest.REQUEST_TYPE);
    }

    public String handle(ManualActionPutRequestDTO request) {
        ManualActionRequest manualActionRequest = new ManualActionRequest(request);
        return send(manualActionRequest, manualActionRequest.getBody(), ManualActionRequest.REQUEST_TYPE);
    }

    public String handle(ManualActionPutRequestDTO.ManualActionGetRequestDTO request){
        ManualActionGetRequest manualActionRequest = new ManualActionGetRequest();
        return send(manualActionRequest, manualActionRequest.getBody(), ManualActionGetRequest.REQUEST_TYPE);
    }



    public String send(HttpRequest httpRequest, String body, String REQUEST_TYPE) {
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .readTimeout(0, java.util.concurrent.TimeUnit.SECONDS)
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
            return response.body() != null ? response.body().string() : "";
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
