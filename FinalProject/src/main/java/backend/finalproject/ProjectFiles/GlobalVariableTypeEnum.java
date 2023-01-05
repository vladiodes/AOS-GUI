package backend.finalproject.ProjectFiles;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class GlobalVariableTypeEnum extends GlobalVariableType {

    private Set<String> enumValues;

    public GlobalVariableTypeEnum(String typeName, String type) {
        super(typeName, type);
        enumValues = new HashSet<>();
    }

    public void addEnumValue(String value){
        enumValues.add(value);
    }
}
