package backend.finalproject.IntegrationRequestsTests;

import com.google.gson.Gson;

import java.util.Map;

public class CompareJsonUtils {
    public static boolean compareMaps(Map<String, Object> originalMap, Map<String, Object> generatedMap) {
        try {
            for (Map.Entry<String, Object> entry : originalMap.entrySet()) {
                if (generatedMap.get(entry.getKey()) == null) {
                    return false;
                }
                if (entry.getValue() instanceof Map) {
                    if (!compareMaps((Map<String, Object>) entry.getValue(), (Map<String, Object>) generatedMap.get(entry.getKey()))) {
                        return false;
                    }
                } else if (entry.getValue() instanceof String[]) {
                    if (!compareStringArrays((String[]) entry.getValue(), (String[]) generatedMap.get(entry.getKey()))) {
                        return false;
                    }
                } else if (!entry.getValue().equals(generatedMap.get(entry.getKey()))) {
                    return false;
                }
            }
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public static boolean compareStringArrays(String[] value, String[] strings) {
        if(value.length != strings.length){
            return false;
        }
        for(int i = 0; i < value.length; i++){
            if(!value[i].equals(strings[i])){
                return false;
            }
        }
        return true;
    }

    public static Map<String, Object> jsonToMap(String jsonStr) {
        Gson gson = new Gson();
        Map<String, Object> map = gson.fromJson(jsonStr, Map.class);
        return map;
    }
}
