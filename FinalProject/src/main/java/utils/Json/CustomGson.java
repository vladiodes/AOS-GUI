package utils.Json;

import backend.finalproject.ProjectFiles.AM.LocalVariablesInit.LocalVariablesInitialization;
import backend.finalproject.ProjectFiles.Common.IAssignmentBlock;
import backend.finalproject.ProjectFiles.Env.GlobalVariableType;
import backend.finalproject.ProjectFiles.Env.GlobalVariableTypeCompound;
import backend.finalproject.ProjectFiles.Env.GlobalVariableTypeEnum;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import frontend.finalproject.Model.Env.GlobalVariableTypeModel;
import utils.Json.CustomDeserializers.AssignmentBlockDeserializer;
import utils.Json.CustomDeserializers.LocalVariablesInitializationDeserializer;
import utils.Json.CustomSerializers.GlobalVariableTypeCompoundJsonSerializer;
import utils.Json.CustomSerializers.GlobalVariableTypeEnumJsonSerializer;
import utils.Json.PolymorphDeserializer.PolymorphDeserializer;

public class CustomGson {
    public static Gson getCustomGson(){
        return new GsonBuilder()
                .setPrettyPrinting()
                .disableHtmlEscaping()
                .registerTypeAdapter(GlobalVariableTypeCompound.class, new GlobalVariableTypeCompoundJsonSerializer())
                .registerTypeAdapter(GlobalVariableTypeEnum.class, new GlobalVariableTypeEnumJsonSerializer())
                .registerTypeAdapter(IAssignmentBlock.class, new AssignmentBlockDeserializer())
                .registerTypeAdapter(LocalVariablesInitialization.class, new LocalVariablesInitializationDeserializer())
                .registerTypeAdapter(GlobalVariableType.class, new PolymorphDeserializer<GlobalVariableType>())
                .registerTypeAdapter(GlobalVariableTypeModel.class, new PolymorphDeserializer<GlobalVariableTypeModel>())
                .create();
    }
}
