package utils.RequestsResponse;

import com.google.gson.Gson;

import java.util.List;

public class InitProjectRequestResponse implements RequestResponse {
    private List<String> errors;
    private List<String> remarks;

    public List<String> getErrors() {
        return errors;
    }

    public List<String> getRemarks() {
        return remarks;
    }

    public static InitProjectRequestResponse fromJSON(String json){
        Gson gson = new Gson();
        return gson.fromJson(json, InitProjectRequestResponse.class);
    }
}
