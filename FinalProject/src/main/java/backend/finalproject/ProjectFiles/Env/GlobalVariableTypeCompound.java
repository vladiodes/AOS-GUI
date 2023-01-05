package backend.finalproject.ProjectFiles.Env;


import frontend.finalproject.Model.Env.GlobalVariableTypeCompoundModel;

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

    private class CompoundVariable {
        private String name;
        private String type;
        private String Default;

        public CompoundVariable(String name, String type, String Default) {
            this.name = name;
            this.type = type;
            this.Default = Default;
        }

        public CompoundVariable(String name, String type) {
            this.name = name;
            this.type = type;
        }

        public CompoundVariable(frontend.finalproject.Model.Env.CompoundVariable cv) {
            this.name = cv.getName();
            this.type = cv.getType();
            this.Default = cv.getDefault();
        }
    }
}
