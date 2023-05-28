# Maintenance guide:
The following document is a guide for developers who wish to maintain the system.
We go through the main components of the system and dive into the code.

In addition to that, we also provide a detailed walk through of how to add some new functionality to the system in some specific use-cases.
## Table of contents:
- [Facade](#facade)
- [Integration requests](#integration_requests)
- [Application screens](#application_screens)
  - [Execution screen](#execution_screen)
  - [Create/Edit project screen](#create_project_screen)
  - [Create/Edit skill screen](#create_skill_screen)
  - [Logs screen](#logs_screen)
  - [Init project screen](#init_project_screen)

<a name="facade"></a>
# Facade:
* The facade module is implemented using the Facade DP - [Reference](https://refactoring.guru/design-patterns/facade).
Basically, this is the gateway to the backend of our system.
All of its methods are organized and well documented in a java doc format here: [Link](https://github.com/vladiodes/Autonomous-Operating-System/blob/main/FinalProject/src/main/java/backend/finalproject/IAOSFacade.java).

* Each function returns a value wrapped by a [Response](https://github.com/vladiodes/Autonomous-Operating-System/blob/main/FinalProject/src/main/java/utils/Response.java) object.
The Response object would hold the outcome of the function if no error has occurred, by using the following method:
```java
response.getValue();
```
Otherwise, if an error has occured, the Response object would hold the error message, by using the following method:
```java
response.getMessage();
```
* Best practice is to use the following pattern when calling a function from the facade:
```java
// someFunction is a function that returns a Response with a generic type T wrapped inside it
Response<T> response = facade.someFunction();
if(response.hasErrorOccured()) {
    // handle error
    // ...
}
else{
    T value = response.getValue();
    // handle whatever you want to do with the response value
        }
```

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

<a name="application_screens"></a>
# Application screens
The GUI of the application is designed using the MVC design pattern (Ref: [Link](https://en.wikipedia.org/wiki/Model%E2%80%93view%E2%80%93controller)).
Basically, each screen/window is divided into the 3 following parts:
- Model - The data that is displayed/manipulated in the screen. E.g, the env model when creating a new project or the SD/AM of each skill (Ref: [EnvModel](https://github.com/vladiodes/Autonomous-Operating-System/blob/main/FinalProject/src/main/java/frontend/finalproject/Model/Env/EnvModel.java))
- View - The visual representation of the screen. I.e. the .fxml files in the resource folder. The layout as well as the styling is defined in those files (Ref: [create-env-view](https://github.com/vladiodes/Autonomous-Operating-System/blob/main/FinalProject/src/main/resources/frontend/finalproject/Controllers/create-env-view.fxml))
- Controller - Used to manipulate the model, bind it to the relevant nodes in the view and/or render it whenever it's necessary. Basically, the logic of the screen (Ref: [CreateEnvController](https://github.com/vladiodes/Autonomous-Operating-System/blob/main/FinalProject/src/main/java/frontend/finalproject/Controllers/CreateEnvController.java))

In the following section we'll describe some of the main screens of the application.

**_Note: There are some screens that are built dynamically as they depend on the json outcome of the server.
All of those screens must implement the [IJsonVisualizer](https://github.com/vladiodes/Autonomous-Operating-System/blob/main/FinalProject/src/main/java/frontend/finalproject/ServerResponseDisplayers/IJsonVisualizer.java) class.
This class basically receives a plain json string in its constructor and generates a JavaFX Node component which will be placed in the center of the screen.
Each screen that implements this interface can be displayed using the [loadResponseStage](https://github.com/vladiodes/Autonomous-Operating-System/blob/main/FinalProject/src/main/java/frontend/finalproject/Utils/UtilsFXML.java#L172) function._**
<a name="execution_screen"></a>
## Execution screen
This screen is split into 3 tabs:

### Tab #1 : Simulated State
This tab displays the simulated state of the robot. It is implemented by the [SimulatedStateVisualizer](https://github.com/vladiodes/Autonomous-Operating-System/blob/main/FinalProject/src/main/java/frontend/finalproject/ServerResponseDisplayers/SimulatedStateVisualizer.java) which implements the IJsonVisualizer interface.
The constructor of the class receives the json response of the GetSimulatedStates request.
The displayJson() function returns a node that contains the following:
- A tree view of the simulated state (we can also navigate to next/previous states). 
  - The variables of the state that have changed as a result of the action that took place, are highlighted.
  - References: [TreeVisualizer](https://github.com/vladiodes/Autonomous-Operating-System/blob/main/FinalProject/src/main/java/frontend/finalproject/ServerResponseDisplayers/JsonTreeViewVisualizer.java), [Highlighting the tree](https://github.com/vladiodes/Autonomous-Operating-System/blob/main/FinalProject/src/main/java/frontend/finalproject/ServerResponseDisplayers/SimulatedStateVisualizer.java#L214)
- A display button, that displays the current state in a graphical manner (more on that later).

### Tab #2 : Execution Outcome
This tab displays the execution outcome of the robot. It is implemented by the [ExecutionOutcomeVisualizer.DisplayContainer](https://github.com/vladiodes/Autonomous-Operating-System/blob/main/FinalProject/src/main/java/frontend/finalproject/ServerResponseDisplayers/ExecutionOutcomeVisualizer.java#L383) class.
The constructor of the class receives the json response of the GetExecutionOutcome request.

```executionOutcome``` is an array. In each element of the array, there is a list of the possible belief states of the robot.

The component that is generated contains the following:
- A raw view of the json response which is displayed in a tree view.
We use a VBox named ```rawDataContainer``` to store the tree view of the json response. An example of how this content is updated:
```java
rawDataContainer = new VBox();
rawDataContainer.getChildren().add(new JsonTreeViewVisualizer(executionOutcome.get(0).toString()).displayJSON());
rawDataContainer.setVisible(false);
```
- For each element in the ```executionOutcome``` array, a histogram (JavaFX's BarChart) is built.
  - There is a histogram for every variable in the state structure. Moreover, histograms can be filtered by the user by variable's name.
  - Reference: [buildHistograms](https://github.com/vladiodes/Autonomous-Operating-System/blob/main/FinalProject/src/main/java/frontend/finalproject/ServerResponseDisplayers/ExecutionOutcomeVisualizer.java#L121)

### Tab #3 (optional) : Manual Control
This tab is only displayed if the user initialized the last project with Manual Control flag to be true.
We keep track of that flag [here](https://github.com/vladiodes/Autonomous-Operating-System/blob/main/FinalProject/src/main/java/frontend/finalproject/Utils/UtilsFXML.java#L60)

This tab is implemented by the [manual-action-request-view.fxml](https://github.com/vladiodes/Autonomous-Operating-System/blob/main/FinalProject/src/main/resources/frontend/finalproject/Controllers/manual-action-request-view.fxml) view and the [ManualActionRequestController](https://github.com/vladiodes/Autonomous-Operating-System/blob/main/FinalProject/src/main/java/frontend/finalproject/Controllers/ManualActionRequestController.java) controller.

If this tab is displayed, a polling thread runs in the background that does the following:
- Polls the server in intervals of 1 second each.
- It checks whether there are any new states that the robot has reached.
  - If there are, the robot updates the view in all of the tabs accordingly.
  - Otherwise, it goes to sleep and wakes up in the next iteration (after ~1 second).
- Reference: 
  - [polling thread](https://github.com/vladiodes/Autonomous-Operating-System/blob/main/FinalProject/src/main/java/frontend/finalproject/ServerResponseDisplayers/ExecutionOutcomeVisualizer.java#L232) - running the polling thread. 
  - [onActionSentCallback](https://github.com/vladiodes/Autonomous-Operating-System/blob/main/FinalProject/src/main/java/frontend/finalproject/ServerResponseDisplayers/ExecutionOutcomeVisualizer.java#L245) - the function that is run on each iteration.

This tab contains the following:
- A list of all the possible actions that the robot can perform (double-clicking on an actions triggers a send-action request)
  - When sending an action for the robot to execute
- A list of all the actions the robot has performed.