package DTO.HttpRequests;

import backend.finalproject.IntegrationRequests.IntegrationRequestsHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ManualActionPutRequestDTO implements HttpRequestDTO{
    private int ActionID;

    public ManualActionPutRequestDTO(int ActionID) {
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

    public static class ManualActionGetRequestDTO implements HttpRequestDTO{

        @Override
        public String visit(IntegrationRequestsHandler handler) {
            return handler.handle(this);
        }
    }
}
