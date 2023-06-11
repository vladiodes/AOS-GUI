package utils.Json.CustomSerializers;

import backend.finalproject.ProjectFiles.Env.GlobalVariableTypeEnum;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;


/**
 This class is only for json output BEAUTY
 what is beauty you ask? beauty is when the parent class fields (eg: TypeName, Type) are printed before the child class field (EnumValues)
 gson default serializer think different. so we implement serializer for this child class specifically
 */
public class GlobalVariableTypeEnumJsonSerializer implements JsonSerializer<GlobalVariableTypeEnum> {

    @Override
    public JsonElement serialize(GlobalVariableTypeEnum gv, java.lang.reflect.Type type, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        object.add("TypeName", context.serialize(gv.getTypeName()));
        object.add("Type", context.serialize(gv.getType()));
        object.add("EnumValues", context.serialize(gv.getEnumValues()));
        // ...
        return object;
    }
}
