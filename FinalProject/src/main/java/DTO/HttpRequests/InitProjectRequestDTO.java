package DTO.HttpRequests;

import backend.finalproject.IntegrationRequests.IntegrationRequestsHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import frontend.finalproject.Controllers.InitializeProjectRequestController;

public class InitProjectRequestDTO implements HttpRequestDTO {
    private String PLPsDirectoryPath;
    private boolean OnlyGenerateCode;
    private boolean RunWithoutRebuild;
    private final ROSTarget RosTarget;
    private final SolverConfiguration SolverConfiguration;
    private final MiddlewareConfiguration MiddlewareConfiguration;

    public InitProjectRequestDTO(){
        RosTarget = new ROSTarget();
        SolverConfiguration = new SolverConfiguration();
        MiddlewareConfiguration = new MiddlewareConfiguration();
    }

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

    public static class InitProjectRequestDTOBuilder{
        private final InitProjectRequestDTO instance = new InitProjectRequestDTO();

        public InitProjectRequestDTOBuilder setPLPsDirectoryPath(String path){
            instance.PLPsDirectoryPath = path;
            return this;
        }

        public InitProjectRequestDTOBuilder setOnlyGenerateCode(boolean val){
            instance.OnlyGenerateCode = val;
            return this;
        }

        public InitProjectRequestDTOBuilder setRunWithoutRebuild(boolean val){
            instance.RunWithoutRebuild = val;
            return this;
        }

        public InitProjectRequestDTOBuilder setRosDistribution(String val){
            instance.RosTarget.RosDistribution = val;
            return this;
        }

        public InitProjectRequestDTOBuilder setWorkspaceDirectoryPath(String val){
            instance.RosTarget.WorkspaceDirectoryPath = val;
            return this;
        }

        public InitProjectRequestDTOBuilder setTargetProjectLaunchFile(String val){
            instance.RosTarget.TargetProjectLaunchFile = val;
            return this;
        }

        public InitProjectRequestDTOBuilder setRosTargetProjectPackages(String[] val){
            instance.RosTarget.RosTargetProjectPackages = val;
            return this;
        }

        public InitProjectRequestDTOBuilder setTargetProjectInitializationTimeInSeconds(int val){
            instance.RosTarget.TargetProjectInitializationTimeInSeconds = val;
            return this;
        }

        public InitProjectRequestDTOBuilder setPolicyGraphDepth(int val){
            instance.SolverConfiguration.PolicyGraphDepth = val;
            return this;
        }

        public InitProjectRequestDTOBuilder setDebugOn(boolean val){
            instance.SolverConfiguration.DebugOn = val;
            return this;
        }

        public InitProjectRequestDTOBuilder setNumOfParticles(int val){
            instance.SolverConfiguration.NumOfParticles = val;
            return this;
        }

        public InitProjectRequestDTOBuilder setVerbosity(int val){
            instance.SolverConfiguration.Verbosity = val;
            return this;
        }

        public InitProjectRequestDTOBuilder setManualControl(boolean val){
            instance.SolverConfiguration.ManualControl = val;
            return this;
        }

        public InitProjectRequestDTOBuilder setActionsToSimulate(String[] val){
            instance.SolverConfiguration.ActionsToSimulate = val;
            return this;
        }

        public InitProjectRequestDTOBuilder setIsInternalSimulation(boolean val){
            instance.SolverConfiguration.IsInternalSimulation = val;
            return this;
        }

        public InitProjectRequestDTOBuilder setPlanningTimePerMoveInSeconds(double val){
            instance.SolverConfiguration.PlanningTimePerMoveInSeconds = val;
            return this;
        }

        public InitProjectRequestDTOBuilder setDebugOnMiddleware(boolean val){
            instance.MiddlewareConfiguration.DebugOn = val;
            return this;
        }

        public InitProjectRequestDTOBuilder setBeliefStateParticlesToSave(int val){
            instance.SolverConfiguration.NumOfBeliefStateParticlesToSaveInDB = val;
            return this;
        }

        public InitProjectRequestDTOBuilder setLoadBeliefFromDB(boolean val){
            instance.SolverConfiguration.LoadBeliefFromDB = val;
            return this;
        }

        public InitProjectRequestDTO build(){
            return instance;
        }

    }
}


class MiddlewareConfiguration{
    boolean DebugOn;
}

class SolverConfiguration {
    int PolicyGraphDepth;
    boolean LoadBeliefFromDB;
    boolean DebugOn;
    int NumOfParticles;
    int NumOfBeliefStateParticlesToSaveInDB;
    int Verbosity;
    String[] ActionsToSimulate;
    boolean IsInternalSimulation;
    double PlanningTimePerMoveInSeconds;
    boolean ManualControl;
}
