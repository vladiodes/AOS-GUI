package frontend.finalproject.Model.Env;

import backend.finalproject.ProjectFiles.Env.GlobalVariableTypeEnum;

import java.util.ArrayList;
import java.util.List;

public class GlobalVariableTypeEnumModel extends GlobalVariableTypeModel {

    private List<String> EnumValues;

    public GlobalVariableTypeEnumModel(String TypeName, String Type) {
        super(TypeName, Type);
        EnumValues = new ArrayList<>();
    }

    public GlobalVariableTypeEnumModel(String TypeName, String Type, List<String> EnumValues) {
        super(TypeName, Type);

    }

    public GlobalVariableTypeEnumModel(GlobalVariableTypeEnum globalVariableType) {
        super(globalVariableType.getTypeName(), globalVariableType.getType());
        this.EnumValues = new ArrayList<>();
        this.EnumValues.addAll(globalVariableType.getEnumValues());
    }

    public void addEnumValue(String value){
        EnumValues.add(value);
    }

    public List<String> getEnumValues() {
        return EnumValues;
    }
}
