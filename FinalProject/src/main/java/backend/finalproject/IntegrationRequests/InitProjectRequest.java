package backend.finalproject.IntegrationRequests;

import okhttp3.*;


import DTO.HttpRequests.InitProjectRequestDTO;

public class InitProjectRequest extends HttpRequest {
    public final static String ENDPOINT = "/InitializeProject";
    public final static String REQUEST_TYPE = "POST";
    private final String body;

    public InitProjectRequest(InitProjectRequestDTO requestDTO) {
        super(ENDPOINT);
        body = requestDTO.toJson();
    }

    public String send() {
        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .build();
            MediaType mediaType = MediaType.parse("application/json");
            RequestBody reqBody = RequestBody.create(mediaType, body);
            Request.Builder builder = new Request.Builder()
                    .url(endpoint)
                    .method(REQUEST_TYPE, reqBody);
            for(String key : headers.keySet())
                builder.addHeader(key, headers.get(key));
            Request request = builder.build();
            Response response = client.newCall(request).execute();
            return response.body().string();
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return "";
    }
}
