package frontend.finalproject.Model.Env;

public class CompoundVariable {
    private String Name;
    private String Type;
    private String Default;

    public CompoundVariable(String Name, String Type, String Default){
        this.Name = Name;
        this.Type = Type;
        this.Default = Default;
    }

    public String getName() {
        return Name;
    }

    public String getType() {
        return Type;
    }

    public String getDefault() {
        return Default;
    }
}