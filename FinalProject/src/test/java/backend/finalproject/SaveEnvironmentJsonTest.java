package backend.finalproject;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import frontend.finalproject.Model.Env.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import utils.Json.PolymorphDeserializer.PolymorphDeserializer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;


public class SaveEnvironmentJsonTest {
    private final IAOSFacade facade = AOSFacade.getInstance();


    private EnvModel getEnvModelFromJson(String json){
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(GlobalVariableTypeModel.class, new PolymorphDeserializer<GlobalVariableTypeModel>())
                .create();
        return gson.fromJson(json, EnvModel.class);
    }

    @Test
    void saveEnvFileSuccess(){
        File file = new File("../Projects/aosPaper/aosPaperOrig.environment.json");
        try {
            String content = new String(Files.readAllBytes(file.toPath()));
            EnvModel envModel = getEnvModelFromJson(content);
            EnvModel env = facade.createNewProject(envModel).getValue();
            Assertions.assertEquals(env, envModel);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}