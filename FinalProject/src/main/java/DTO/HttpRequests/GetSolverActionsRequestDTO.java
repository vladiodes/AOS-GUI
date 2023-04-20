package DTO.HttpRequests;

import backend.finalproject.IntegrationRequests.IntegrationRequestsHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import utils.IntegrationRequestResponse;

public class GetSolverActionsRequestDTO implements HttpRequestDTO{

    private String PLPsDirectoryPath;
    private final ROSTarget RosTarget;

    public GetSolverActionsRequestDTO(){
        RosTarget = new ROSTarget();
    }

    @Override
    public IntegrationRequestResponse visit(IntegrationRequestsHandler handler) {
        return handler.handle(this);
    }

    public String toJson(){

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .disableHtmlEscaping()
                .create();

        return gson.toJson(this);

    }

    public static class GetSolverActionsRequestDTOBuilder{
        private final GetSolverActionsRequestDTO instance = new GetSolverActionsRequestDTO();

        public GetSolverActionsRequestDTOBuilder setPLPsDirectoryPath(String path){
            instance.PLPsDirectoryPath = path;
            return this;
        }

        public GetSolverActionsRequestDTOBuilder setRosDistribution(String val){
            instance.RosTarget.RosDistribution = val;
            return this;
        }

        public GetSolverActionsRequestDTOBuilder setWorkspaceDirectoryPath(String val){
            instance.RosTarget.WorkspaceDirectoryPath = val;
            return this;
        }

        public GetSolverActionsRequestDTOBuilder setTargetProjectLaunchFile(String val){
            instance.RosTarget.TargetProjectLaunchFile = val;
            return this;
        }

        public GetSolverActionsRequestDTOBuilder setRosTargetProjectPackages(String[] val){
            instance.RosTarget.RosTargetProjectPackages = val;
            return this;
        }

        public GetSolverActionsRequestDTOBuilder setTargetProjectInitializationTimeInSeconds(int val){
            instance.RosTarget.TargetProjectInitializationTimeInSeconds = val;
            return this;
        }

        public GetSolverActionsRequestDTO build(){
            return instance;
        }

    }
}
