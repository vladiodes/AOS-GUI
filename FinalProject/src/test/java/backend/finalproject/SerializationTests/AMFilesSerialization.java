package backend.finalproject.SerializationTests;


import backend.finalproject.AOSFacade;
import backend.finalproject.IAOSFacade;
import backend.finalproject.IntegrationRequestsTests.CompareJsonUtils;
import backend.finalproject.ProjectFiles.AM.AM;
import backend.finalproject.ProjectFiles.Env.Environment;
import backend.finalproject.ProjectFiles.SD.SD;
import com.google.gson.Gson;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import utils.Json.CustomGson;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class AMFilesSerialization {
    private final IAOSFacade facade = AOSFacade.getInstance();
    private final String testJsonsPath = Paths.get("").toAbsolutePath().toString() + File.separator + "src" + File.separator + "test" + File.separator + "java" + File.separator + "backend" + File.separator + "finalproject" + File.separator + "SerializationTests" + File.separator;
    private final Gson gson = CustomGson.getCustomGson();


    // test serialization and deserialization of valid AM files
    @Test
    void testValidAmFiles(){ // FAILS, TODO: fix
        String validAmFilesPath  = testJsonsPath + "ValidAMFiles";
        File[] files = new File(validAmFilesPath).listFiles();
        assert files != null;
        for (File file : files) {
            try {
                String content = new String(Files.readAllBytes(file.toPath()));
                // deserialize the string to AM
                AM am = gson.fromJson(content, AM.class);
                // serialize the AM to string
                String amJson = gson.toJson(am);
                // deserialize the string to AM
                AM am2 = gson.fromJson(amJson, AM.class);
                // serialize the AM to string
                String amJson2 = gson.toJson(am2);
                // compare the two strings
                Assertions.assertTrue(CompareJsonUtils.compareMaps(CompareJsonUtils.jsonToMap(amJson), CompareJsonUtils.jsonToMap(amJson2)));
                // compare also with original json
                Assertions.assertTrue(CompareJsonUtils.compareMaps(CompareJsonUtils.jsonToMap(content), CompareJsonUtils.jsonToMap(amJson2)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // test serialization and deserialization of valid Env files
    @Test
    void testValidEnvFiles(){ // FAILS, TODO: fix
        String validEnvFilesPath  = testJsonsPath + "ValidEnvFiles";
        File[] files = new File(validEnvFilesPath).listFiles();
        assert files != null;
        for (File file : files) {
            try {
                String content = new String(Files.readAllBytes(file.toPath()));
                // deserialize the string to Environment
                Environment environment = gson.fromJson(content, Environment.class);
                // serialize the Environment to string
                String environmentJson = gson.toJson(environment);
                // deserialize the string to Environment
                Environment environment2 = gson.fromJson(environmentJson, Environment.class);
                // serialize the Environment to string
                String environmentJson2 = gson.toJson(environment2);
                // compare the two strings
                Assertions.assertTrue(CompareJsonUtils.compareMaps(CompareJsonUtils.jsonToMap(environmentJson), CompareJsonUtils.jsonToMap(environmentJson2)));
                // compare also with original json
                Assertions.assertTrue(CompareJsonUtils.compareMaps(CompareJsonUtils.jsonToMap(content), CompareJsonUtils.jsonToMap(environmentJson2)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // test serialization and deserialization of valid SD files
    @Test
    void testValidSDFiles(){ // PASS
        String validSDFilesPath  = testJsonsPath + "ValidSDFiles";
        File[] files = new File(validSDFilesPath).listFiles();
        assert files != null;
        for (File file : files) {
            try {
                String content = new String(Files.readAllBytes(file.toPath()));
                // deserialize the string to SD
                SD sd = gson.fromJson(content, SD.class);
                // serialize the SD to string
                String sdJson = gson.toJson(sd);
                // deserialize the string to SD
                SD sd2 = gson.fromJson(sdJson, SD.class);
                // serialize the SD to string
                String sdJson2 = gson.toJson(sd2);
                // compare the two strings
                Assertions.assertTrue(CompareJsonUtils.compareMaps(CompareJsonUtils.jsonToMap(sdJson), CompareJsonUtils.jsonToMap(sdJson2)));
                // compare also with original json
                Assertions.assertTrue(CompareJsonUtils.compareMaps(CompareJsonUtils.jsonToMap(content), CompareJsonUtils.jsonToMap(sdJson2)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}