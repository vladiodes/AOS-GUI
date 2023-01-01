package frontend.finalproject.Model.Env;

import java.util.ArrayList;
import java.util.List;

public class GlobalVariableTypeEnumModel extends GlobalVariableTypeModel {

    private List<String> EnumValues;

    public GlobalVariableTypeEnumModel(String TypeName, String Type) {
        super(TypeName, Type);
        EnumValues = new ArrayList<>();
    }

    public void addEnumValue(String value){
        EnumValues.add(value);
    }
}
