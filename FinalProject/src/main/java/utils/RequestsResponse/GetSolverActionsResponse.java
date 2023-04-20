package utils.RequestsResponse;

import com.google.gson.Gson;

public class GetSolverActionsResponse implements RequestResponse {
    private String[] SolverAction;

    public static GetSolverActionsResponse fromJSON(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, GetSolverActionsResponse.class);
    }

    public String[] getActions() {
        return SolverAction;
    }

}
