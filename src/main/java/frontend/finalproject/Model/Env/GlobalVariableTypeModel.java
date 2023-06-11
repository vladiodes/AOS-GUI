package frontend.finalproject.Model.Env;

import frontend.finalproject.Model.Model;
import utils.Json.PolymorphDeserializer.JsonSubtype;
import utils.Json.PolymorphDeserializer.JsonType;

@JsonType(
        property = "Type",
        subtypes = {
                @JsonSubtype(clazz = GlobalVariableTypeCompoundModel.class, name = "compound"),
                @JsonSubtype(clazz = GlobalVariableTypeEnumModel.class, name = "enum")
        }
)
public abstract class GlobalVariableTypeModel implements Model {
    protected String TypeName;
    protected String Type;

    public GlobalVariableTypeModel(String TypeName, String Type){
        this.TypeName = TypeName;
        this.Type = Type;
    }

    public void setTypeName(String typeName) {
        TypeName = typeName;
    }

    public String getTypeName() {
        return TypeName;
    }

    public String getType() {
        return Type;
    }
}
