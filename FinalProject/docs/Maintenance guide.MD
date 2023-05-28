# Maintenance guide:
## Table of contents:
- [Integration requests](#integration_requests)

<a name="integration_requests"></a>
# Integration requests:
The integration requests module is implemented using the Visitor DP - [Reference](https://refactoring.guru/design-patterns/visitor).
The main classes taking part in this module are:
- [**HttpRequest**](https://github.com/vladiodes/Autonomous-Operating-System/blob/main/FinalProject/src/main/java/backend/finalproject/IntegrationRequests/HttpRequest.java) - An abstract class that contains the server info, some common headers, etc. It's recommended to extend this function for each of your new integration requests to avoid code duplication.
- [**HttpRequestDTO**](https://github.com/vladiodes/Autonomous-Operating-System/blob/main/FinalProject/src/main/java/DTO/HttpRequests/HttpRequestDTO.java) - The DTO interface representing the HTTP request. Acts as the visited class in the visitor DP. Any integration request must implement that class (more on that later). The interface contains one and only function:
```java
String visit(IntegrationRequestsHandler handler);
```
The function is used to visit the visitor class (IntegrationRequestsHandler) and return the json response as a plain string.
The implementation in each of the specific DTO classes should be identical and look like this:
```java
@Override
    public String visit(IntegrationRequestsHandler handler) {
        return handler.handle(this);
    }
```
What it does it simply calls the visitor class' handle function and passes itself as an argument. Utilizing the double dispatch mechanism, the visitor class will then call the appropriate function to handle the request.
 
- [**IntegrationRequestsHandler**](https://github.com/vladiodes/Autonomous-Operating-System/blob/main/FinalProject/src/main/java/backend/finalproject/IntegrationRequests/IntegrationRequestsHandler.java) - This class acts as the visitor class in the visitor DP. It contains a handle function for each of the DTO classes. The main function that is used to handle **any** integration request is the following:
```java
public String handle(HttpRequestDTO request) {
        return request.visit(this);
    }
```
In addition to that, the handler class also contains a function for each of the DTO classes. The function is used to handle the specific request and return the json response as a plain string.
Usage example:
```java
IntegrationRequestsHandler handler = new IntegrationRequestsHandler();
HttpRequestDTO request = new GetSimulatedStatesRequestDTO();
String response = request.visit(handler);
// do something with the response
// ...
```

- Currently, the supported integration requests in the system are:
  - [GetExecutionOutcome](https://github.com/vladiodes/Autonomous-Operating-System/blob/main/FinalProject/src/main/java/DTO/HttpRequests/GetExecutionOutcomeRequestDTO.java)
  - [GetLogs](https://github.com/vladiodes/Autonomous-Operating-System/blob/main/FinalProject/src/main/java/DTO/HttpRequests/GetLogsRequestDTO.java)
  - [GetSimulatedStates](https://github.com/vladiodes/Autonomous-Operating-System/blob/main/FinalProject/src/main/java/DTO/HttpRequests/GetSimulatedStatesRequestDTO.java)
  - [GetSolverActions](https://github.com/vladiodes/Autonomous-Operating-System/blob/main/FinalProject/src/main/java/DTO/HttpRequests/GetSolverActionsRequestDTO.java)
  - [InitProject](https://github.com/vladiodes/Autonomous-Operating-System/blob/main/FinalProject/src/main/java/DTO/HttpRequests/InitProjectRequestDTO.java)
  - [ManualAction](https://github.com/vladiodes/Autonomous-Operating-System/blob/main/FinalProject/src/main/java/DTO/HttpRequests/ManualActionPutRequestDTO.java)
  - [StopRobot](https://github.com/vladiodes/Autonomous-Operating-System/blob/main/FinalProject/src/main/java/DTO/HttpRequests/StopRobotRequestDTO.java)
  - [UpdateLocalVariable](https://github.com/vladiodes/Autonomous-Operating-System/blob/main/FinalProject/src/main/java/DTO/HttpRequests/UpdateLocalVariableRequestDTO.java)

## Adding a new integration request:
The following section is a detailed walk through of how to add a new integration request to the system.
We'd demonstrate that on an already existing http request.
### Step 1 - Create a new DTO class:
Create a new DTO class for your new integration request. The class should implement the HttpRequestDTO interface.
Add any fields you'd like to your class - body fields, params of the request, etc. In our case, this is a simple GET request without body/params.
The visit function should look like this:
```java
public class GetSimulatedStatesRequestDTO implements HttpRequestDTO{
    @Override
    public String visit(IntegrationRequestsHandler handler) {
        return handler.handle(this);
    }
}
```

### Step 2 - Create a new class extending the HttpRequest class (optional):
This should be straight forward to implement. see the following example - [GetSimulatedStatesRequest](https://github.com/vladiodes/Autonomous-Operating-System/blob/main/FinalProject/src/main/java/backend/finalproject/IntegrationRequests/GetExecutionOutcomeRequest.java).
### Step 3 - Add a new function to the IntegrationRequestsHandler class:
Add a new function to the IntegrationRequestsHandler class. The function should take your new DTO class as an argument.
```java
public String handle(GetSimulatedStatesRequestDTO request) {
        // send the request
        // ...
        return response;
    }
```
Or for a more specific implementation:
```java
    public String handle(GetExecutionOutcomeRequestDTO request){
        GetExecutionOutcomeRequest getSolverReq = new GetExecutionOutcomeRequest(request.getBeliefSize());
        return send(getSolverReq,getSolverReq.getBody(),GetSolverActionsRequest.REQUEST_TYPE);
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
```

Basically, the send function is general and can be used for any request. You can utilize it and implement your method similarly.

### Step 4: Call the new request:
Say you have an instance of the AOSFacade named facade somewhere in your code.
Then you can call the new request like this:
```java
// ...
// Create your request DTO class from user input, etc
HttpRequestDTO request = new GetSimulatedStatesRequestDTO();
Response<String> response = facade.sendRequest(request);
if(!response.hasErrorOccured()){
    // if no errors occured, do something with the response
        }
else{
    // handle the error
        }
// ...
```