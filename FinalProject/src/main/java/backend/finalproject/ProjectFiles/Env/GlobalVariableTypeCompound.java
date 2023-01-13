package backend.finalproject.ProjectFiles.Env;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import frontend.finalproject.Model.Env.GlobalVariableTypeCompoundModel;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GlobalVariableTypeCompound extends GlobalVariableType {
    private List<CompoundVariable> Variables;

    public GlobalVariableTypeCompound(String typeName, String type) {
        super(typeName, type);
        Variables = new ArrayList<>();
    }

    public GlobalVariableTypeCompound(GlobalVariableTypeCompoundModel globalVariableType) {
        super(globalVariableType.getTypeName(), globalVariableType.getType());
        Variables = new ArrayList<>();
        for (frontend.finalproject.Model.Env.CompoundVariable cv : globalVariableType.getVariables()){
            Variables.add(new CompoundVariable(cv));
        }
    }

    // TODO: validate variable name not exists already
    public void addVariable(String name, String type, String Default){
        CompoundVariable compoundVariable = new CompoundVariable(name, type, Default);
        Variables.add(compoundVariable);
    }

    // TODO: validate variable name not exists already
    public void addVariable(String name, String type){
        CompoundVariable compoundVariable = new CompoundVariable(name, type);
        Variables.add(compoundVariable);
    }

    public List<CompoundVariable> getVariables() {
        return Variables;
    }

    public class CompoundVariable {
        private String Name;
        private String Type;
        private String Default;

        public CompoundVariable(String name, String type, String Default) {
            this.Name = name;
            this.Type = type;
            this.Default = Default;
        }

        public CompoundVariable(String name, String type) {
            this.Name = name;
            this.Type = type;
        }

        public CompoundVariable(frontend.finalproject.Model.Env.CompoundVariable cv) {
            this.Name = cv.getName();
            this.Type = cv.getType();
            this.Default = cv.getDefault();
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
}
