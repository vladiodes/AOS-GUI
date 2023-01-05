package frontend.finalproject.Model.Env;

import utils.Json.JsonSubtype;
import utils.Json.JsonType;

@JsonType(
        property = "Type",
        subtypes = {
                @JsonSubtype(clazz = GlobalVariableTypeCompoundModel.class, name = "compound"),
                @JsonSubtype(clazz = GlobalVariableTypeEnumModel.class, name = "enum")
        }
)
public abstract class GlobalVariableTypeModel {
    protected String TypeName;
    protected String Type;

    public GlobalVariableTypeModel(String TypeName, String Type){
        this.TypeName = TypeName;
        this.Type = Type;
    }

    public String getTypeName() {
        return TypeName;
    }

    public String getType() {
        return Type;
    }
}
