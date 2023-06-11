package frontend.finalproject.Model.Env;

import backend.finalproject.ProjectFiles.Env.GlobalVariableTypeCompound;

public class CompoundVariable {
    private String Name;
    private String Type;
    private String Default;

    public CompoundVariable(String Name, String Type, String Default){
        this.Name = Name;
        this.Type = Type;
        this.Default = Default;
    }

    public CompoundVariable(GlobalVariableTypeCompound.CompoundVariable c) {
        this.Name = c.getName();
        this.Type = c.getType();
        this.Default = c.getDefault();
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

    public String toString(){
        return String.format("Name: %s, Type: %s, Default: %s",getName(),getType(),getDefault());
    }
}
