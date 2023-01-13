package utils.Json.CustomDeserializers;

import backend.finalproject.ProjectFiles.AM.LocalVariablesInit.DataPublishedRobotFramework;
import backend.finalproject.ProjectFiles.AM.LocalVariablesInit.SDParameters;
import backend.finalproject.ProjectFiles.AM.LocalVariablesInit.SkillCodeReturnValue;
import backend.finalproject.ProjectFiles.Common.AssignmentBlockMultipleLines;
import backend.finalproject.ProjectFiles.Common.AssignmentBlockSingleLine;
import backend.finalproject.ProjectFiles.Common.IAssignmentBlock;
import com.google.gson.*;

import java.lang.reflect.Type;

public class AssignmentBlockDeserializer implements JsonDeserializer<IAssignmentBlock> {

    @Override
    public IAssignmentBlock deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        IAssignmentBlock assignmentBlock;
        try{
            assignmentBlock = new Gson().fromJson(jsonElement.getAsJsonObject(), AssignmentBlockMultipleLines.class);
        }
        catch (Exception e){
            assignmentBlock = new Gson().fromJson(jsonElement.getAsJsonObject(), AssignmentBlockSingleLine.class);
        }

        return assignmentBlock;
    }
}
