package backend.finalproject.ProjectFiles.Env;

import frontend.finalproject.Model.Env.GlobalVariableTypeEnumModel;

import java.util.HashSet;
import java.util.Set;

public class GlobalVariableTypeEnum extends GlobalVariableType {

    private Set<String> EnumValues;

    public GlobalVariableTypeEnum(String typeName, String type) {
        super(typeName, type);
        EnumValues = new HashSet<>();
    }

    public GlobalVariableTypeEnum(GlobalVariableTypeEnumModel globalVariableType) {
        super(globalVariableType.getTypeName(), globalVariableType.getType());
        EnumValues = new HashSet<>(globalVariableType.getEnumValues());
    }

    public void addEnumValue(String value){
        EnumValues.add(value);
    }

    public Set<String> getEnumValues() {
        return EnumValues;
    }
}
