package backend.finalproject.ProjectFiles.Env;

import frontend.finalproject.Model.Env.GlobalVariableTypeCompoundModel;
import frontend.finalproject.Model.Env.GlobalVariableTypeEnumModel;
import utils.Json.PolymorphDeserializer.JsonSubtype;
import utils.Json.PolymorphDeserializer.JsonType;

// doc: https://github.com/orhaimwerthaim/AOS-WebAPI/blob/master/docs/version2/AOS_documentation_manual.md#globalvariabletypes
@JsonType(
        property = "Type",
        subtypes = {
                @JsonSubtype(clazz = GlobalVariableTypeCompound.class, name = "compound"),
                @JsonSubtype(clazz = GlobalVariableTypeEnum.class, name = "enum")
        }
)
public abstract class GlobalVariableType {
    protected String TypeName;
    protected String Type;

    public GlobalVariableType(String typeName, String type) {
        this.TypeName = typeName;
        this.Type = type;
    }

    public String getTypeName() {
        return TypeName;
    }

    public String getType() {
        return Type;
    }
}
