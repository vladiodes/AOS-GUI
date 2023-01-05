package backend.finalproject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import frontend.finalproject.Model.Env.*;
import org.junit.jupiter.api.Test;
import utils.Json.PolymorphDeserializer;
import utils.Response;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SaveEnvironmentJsonTest {
    private final IAOSFacade facade = new AOSFacade();

    private EnvModel buildAosPaperEnvModel(){
        EnvModel envModel = new EnvModel();

        PlpMainModel plpMainModel = new PlpMainModel("aosPaper");

        EnvironmentGeneralModel environmentGeneralModel = new EnvironmentGeneralModel(10, 0.97);

        GlobalVariableTypeCompoundModel compoundModel1 = new GlobalVariableTypeCompoundModel("tLocation", "compound");
        CompoundVariable compoundVariableX = new CompoundVariable("x", "float", "0.0");
        CompoundVariable compoundVariableY = new CompoundVariable("y", "float", "0.0");
        CompoundVariable compoundVariableZ = new CompoundVariable("z", "float", "0.0");
        CompoundVariable compoundVariableDiscrete = new CompoundVariable("discrete", "int", "-1");
        compoundModel1.insertVariable(compoundVariableX);
        compoundModel1.insertVariable(compoundVariableY);
        compoundModel1.insertVariable(compoundVariableZ);
        compoundModel1.insertVariable(compoundVariableDiscrete);

        GlobalVariableTypeCompoundModel compoundModel2 = new GlobalVariableTypeCompoundModel("tLocation", "compound");
        CompoundVariable compoundVariableDiscrete2  = new CompoundVariable("discrete", "int", "1");
        CompoundVariable compoundVariableVisited  = new CompoundVariable("visited", "bool", "false");
        compoundModel2.insertVariable(compoundVariableDiscrete2);
        compoundModel2.insertVariable(compoundVariableVisited);

        GlobalVariablesDeclarationModel globalVariablesDeclarationModelRobotLocation = new GlobalVariablesDeclarationModel("robotLocation", "tLocation", "state.robotLocation.discrete = -1;", false);
        GlobalVariablesDeclarationModel globalVariablesDeclarationModelV1 = new GlobalVariablesDeclarationModel("v1", "tVisitedLocation", "state.v1.discrete = 1;", false);
        GlobalVariablesDeclarationModel globalVariablesDeclarationModelV2 = new GlobalVariablesDeclarationModel("v2", "tVisitedLocation", "state.v2.discrete = 1;", false);
        GlobalVariablesDeclarationModel globalVariablesDeclarationModelV3 = new GlobalVariablesDeclarationModel("v3", "tVisitedLocation", "state.v3.discrete = 1;", false);

        GlobalVariablesDeclarationModel globalVariablesDeclarationModelL1 = new GlobalVariablesDeclarationModel("", "tVisitedLocation", "state.v1.discrete = 1;", false);


        return null;
    }

    private EnvModel getEnvModelFromJson(String json){
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(GlobalVariableTypeModel.class, new PolymorphDeserializer<GlobalVariableTypeModel>())
                .create();
        EnvModel envModel = gson.fromJson(json, EnvModel.class);
        return envModel;
    }

    @Test
    void saveEnvFileSuccess(){
        File file = new File("../Examples/aosPaper.environment.json");
        try {
            String content = new String(Files.readAllBytes(file.toPath()));
            EnvModel envModel = getEnvModelFromJson(content);
            facade.createNewProject(envModel);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

/**
 {


 "GlobalVariableTypes": [
 {
 "TypeName": "tLocation",
 "Type": "compound",
 "Variables": [
 {
 "Name": "x",
 "Type": "float",
 "Default": "0.0"
 },
 {
 "Name": "y",
 "Type": "float",
 "Default": "0.0"
 },
 {
 "Name": "z",
 "Type": "float",
 "Default": "0.0"
 },
 {
 "Name": "discrete",
 "Type": "int",
 "Default": "-1"
 }
 ]
 },
 {
 "TypeName": "tVisitedLocation",
 "Type": "compound",
 "Variables": [
 {
 "Name": "discrete",
 "Type": "int",
 "Default": "1"
 },
 {
 "Name": "visited",
 "Type": "bool",
 "Default": "false"
 }
 ]
 }
 ],
 "GlobalVariablesDeclaration": [
 {
 "Name": "robotLocation",
 "Type": "tLocation",
 "DefaultCode": "state.robotLocation.discrete = -1;"
 },
 {
 "Name": "v1",
 "Type": "tVisitedLocation",
 "DefaultCode": "state.v1.discrete = 1;"
 },
 {
 "Name": "v2",
 "Type": "tVisitedLocation",
 "DefaultCode": "state.v2.discrete = 2;"
 },
 {
 "Name": "v3",
 "Type": "tVisitedLocation",
 "DefaultCode": "state.v3.discrete = 3"
 },
 {
 "Name": "l1",
 "Type": "tLocation",
 "DefaultCode": "state.l1.x = -1.01606154442; state.l1.y = 0.660750925541; state.l1.z =-0.00454711914062; state.l1.discrete = 1;",
 "IsActionParameterValue": true
 },
 {
 "Name": "l2",
 "Type": "tLocation",
 "DefaultCode": "state.l2.x = 0.00500533776358; state.l2.y = 0.640727937222; state.l2.z =-0.00143432617188; state.l2.discrete = 2;",
 "IsActionParameterValue": true
 },
 {
 "Name": "l3",
 "Type": "tLocation",
 "DefaultCode": "state.l3.x = 0.986030161381; state.l3.y = 0.610693752766; state.l3.z =-0.00143432617188; state.l3.discrete = 3;",
 "IsActionParameterValue": true
 },
 {
 "Name": "l4",
 "Type": "tLocation",
 "DefaultCode": "state.l4.x = -2.01710748672; state.l4.y = 0.570648550987; state.l4.z =-0.165466308594; state.l4.discrete = 4;"
 }
 ],
 "InitialBeliefStateAssignments": [
 {
 "AssignmentName": "set reachable points",
 "AssignmentCode": "state.robotLocation.discrete = AOS.Bernoulli(0.5) ? 1 : (AOS.Bernoulli(0.2) ? 2 : 3);"
 }
 ],
 "SpecialStates": [
 {
 "StateConditionCode": "!state.v1.visited && state.v2.visited",
 "Reward": -50.0,
 "IsOneTimeReward": true
 },
 {
 "StateConditionCode": "state.v1.visited && state.v2.visited && state.v3.visited",
 "Reward": 7000.0,
 "IsGoalState": true
 }
 ],
 "ExtrinsicChangesDynamicModel": [
 {
 "AssignmentCode": "if (AOS.Bernoulli(0.05)) state_.robotLocation.discrete = -1;"
 }
 ]
 }
 **/
