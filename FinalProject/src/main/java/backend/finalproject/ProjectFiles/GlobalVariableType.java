package backend.finalproject.ProjectFiles;

// doc: https://github.com/orhaimwerthaim/AOS-WebAPI/blob/master/docs/version2/AOS_documentation_manual.md#globalvariabletypes
public abstract class GlobalVariableType {
    protected String TypeName;
    protected String Type;

    public GlobalVariableType(String typeName, String type) {
        this.TypeName = typeName;
        this.Type = type;
    }
}