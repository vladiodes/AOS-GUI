package frontend.finalproject.Controllers.SubControllers;

import frontend.finalproject.Controllers.UtilsFXML;
import frontend.finalproject.Model.Env.GlobalVariableTypeModel;
import java.util.*;

public interface VarTypeSubController {
    void setSource(UtilsFXML.Source source);

    void setGlobalVarType(GlobalVariableTypeModel type);

    List<GlobalVariableTypeModel> getAddedVars();
}
