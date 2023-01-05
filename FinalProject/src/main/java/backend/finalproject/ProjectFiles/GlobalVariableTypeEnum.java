package backend.finalproject.ProjectFiles;

import frontend.finalproject.Model.Env.GlobalVariableTypeEnumModel;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class GlobalVariableTypeEnum extends GlobalVariableType {

    private Set<String> enumValues;

    public GlobalVariableTypeEnum(String typeName, String type) {
        super(typeName, type);
        enumValues = new HashSet<>();
    }

    public GlobalVariableTypeEnum(GlobalVariableTypeEnumModel globalVariableType) {
        super(globalVariableType.getTypeName(), globalVariableType.getType());
        enumValues = new HashSet<>(globalVariableType.getEnumValues());
    }

    public void addEnumValue(String value){
        enumValues.add(value);
    }
}
