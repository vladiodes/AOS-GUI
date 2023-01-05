package frontend.finalproject.Model.Env;

import java.util.ArrayList;
import java.util.List;

public class GlobalVariableTypeCompoundModel extends GlobalVariableTypeModel {
    private List<CompoundVariable> Variables;

    public GlobalVariableTypeCompoundModel(String TypeName, String Type) {
        super(TypeName, Type);
        Variables = new ArrayList<>();
    }

    public GlobalVariableTypeCompoundModel(String TypeName, String Type, List<CompoundVariable> Variables) {
        super(TypeName, Type);
        this.Variables = new ArrayList<>();
        this.Variables.addAll(Variables);
    }

    public void insertVariable(CompoundVariable variable){
        Variables.add(variable);
    }
    public List<CompoundVariable> getVariables() {
        return Variables;
    }
}
