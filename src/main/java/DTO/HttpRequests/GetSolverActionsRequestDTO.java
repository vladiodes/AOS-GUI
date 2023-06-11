package DTO.HttpRequests;

import backend.finalproject.IntegrationRequests.IntegrationRequestsHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GetSolverActionsRequestDTO implements HttpRequestDTO{

    public GetSolverActionsRequestDTO(){
    }

    @Override
    public String visit(IntegrationRequestsHandler handler) {
        return handler.handle(this);
    }

    public String toJson(){

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .disableHtmlEscaping()
                .create();

        return gson.toJson(this);

    }
}
