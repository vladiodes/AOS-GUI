package utils.Json.CustomDeserializers;

import backend.finalproject.ProjectFiles.AM.LocalVariablesInit.DataPublishedRobotFramework;
import backend.finalproject.ProjectFiles.AM.LocalVariablesInit.LocalVariablesInitialization;

import backend.finalproject.ProjectFiles.AM.LocalVariablesInit.SDParameters;
import backend.finalproject.ProjectFiles.AM.LocalVariablesInit.SkillCodeReturnValue;
import com.google.gson.*;

import java.lang.reflect.Type;

public class LocalVariablesInitializationDeserializer implements JsonDeserializer<LocalVariablesInitialization> {
    @Override
    public LocalVariablesInitialization deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        LocalVariablesInitialization localVariablesInitialization;
        if (jsonElement.getAsJsonObject().get("InputLocalVariable") != null) { // Skill code return value unique field
            localVariablesInitialization = new Gson().fromJson(jsonElement.getAsJsonObject(), SDParameters.class);
        }
        else if (jsonElement.getAsJsonObject().get("RosTopicPath") != null){ // Data published Robot Framework unique field
            localVariablesInitialization = new Gson().fromJson(jsonElement.getAsJsonObject(), DataPublishedRobotFramework.class);
        }
        else {
            localVariablesInitialization = new Gson().fromJson(jsonElement.getAsJsonObject(), SkillCodeReturnValue.class); // SkillCodeReturnValue
        }

        return localVariablesInitialization;
    }
}
