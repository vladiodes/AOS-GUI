package DTO.HttpRequests;

import backend.finalproject.IntegrationRequests.IntegrationRequestsHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

public class UpdateLocalVariableRequestDTO implements HttpRequestDTO{

    private String VarName;
    private Map<String,Object> value;
    private String module;

    public UpdateLocalVariableRequestDTO(){
        value = new HashMap<>();
    }

    //TODO: make this function globally accessible - duplication of code
    public String toJson(){

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .disableHtmlEscaping()
                .create();

        return gson.toJson(this);

    }


    @Override
    public String visit(IntegrationRequestsHandler handler) {
        return handler.handle(this);
    }

    public static class UpdateLocalVariableRequestDTOBuilder{

        private final UpdateLocalVariableRequestDTO instance = new UpdateLocalVariableRequestDTO();

        public UpdateLocalVariableRequestDTOBuilder setVarName(String varName){
            instance.VarName = varName;
            return this;
        }

        public UpdateLocalVariableRequestDTOBuilder setModule(String module){
            instance.module = module;
            return this;
        }

        public UpdateLocalVariableRequestDTOBuilder setValue(Map<String,Object> values){
            instance.value = values;
            return this;
        }

        public UpdateLocalVariableRequestDTOBuilder addValue(String field, Object fieldValue){
            instance.value.put(field, fieldValue);
            return this;
        }

        public UpdateLocalVariableRequestDTO build(){
            return instance;
        }

    }
}
