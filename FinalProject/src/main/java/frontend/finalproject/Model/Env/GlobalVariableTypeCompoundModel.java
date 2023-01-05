package frontend.finalproject.Model.Env;

import java.util.ArrayList;
import java.util.List;

public class GlobalVariableTypeCompoundModel extends GlobalVariableTypeModel {
    private List<CompoundVariable> Variables;

    public GlobalVariableTypeCompoundModel(String TypeName, String Type) {
        super(TypeName, Type);
        Variables = new ArrayList<>();
    }

    public void insertVariable(CompoundVariable variable){
        Variables.add(variable);
    }

    public List<CompoundVariable> getVariables() {
        return Variables;
    }
}
