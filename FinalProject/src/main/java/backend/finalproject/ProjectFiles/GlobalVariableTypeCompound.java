package backend.finalproject.ProjectFiles;



import java.util.ArrayList;
import java.util.List;

public class GlobalVariableTypeCompound extends GlobalVariableType {
    private List<CompoundVariable> variables;

    public GlobalVariableTypeCompound(String typeName, String type) {
        super(typeName, type);
        variables = new ArrayList<>();
    }

    // TODO: validate variable name not exists already
    public void addVariable(String name, String type, String Default){
        CompoundVariable compoundVariable = new CompoundVariable(name, type, Default);
        variables.add(compoundVariable);
    }

    // TODO: validate variable name not exists already
    public void addVariable(String name, String type){
        CompoundVariable compoundVariable = new CompoundVariable(name, type);
        variables.add(compoundVariable);
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
    }
}
