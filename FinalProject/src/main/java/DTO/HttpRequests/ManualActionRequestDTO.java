package DTO.HttpRequests;

import backend.finalproject.IntegrationRequests.IntegrationRequestsHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ManualActionRequestDTO implements HttpRequestDTO{
    private int ActionID;

    public ManualActionRequestDTO(int ActionID) {
        this.ActionID = ActionID;
    }

    @Override
    public String visit(IntegrationRequestsHandler handler) {
        return handler.handle(this);
    }

    public String toJSON() {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .disableHtmlEscaping()
                .create();

        return gson.toJson(this);
    }
}
