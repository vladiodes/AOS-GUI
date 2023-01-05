package frontend.finalproject.Model.Env;

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
        this.EnumValues = new ArrayList<>();
        this.EnumValues.addAll(EnumValues);
    }

    public void addEnumValue(String value){
        EnumValues.add(value);
    }

    public List<String> getEnumValues() {
        return EnumValues;
    }
}
