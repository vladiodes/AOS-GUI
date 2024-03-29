package backend.finalproject.ProjectFiles.SD;

import backend.finalproject.ProjectFiles.Common.AssignmentBlockMultipleLines;
import backend.finalproject.ProjectFiles.Common.IAssignmentBlock;
import backend.finalproject.ProjectFiles.Common.PlpMain;
import backend.finalproject.ProjectFiles.Env.GlobalVariableTypeCompound;
import backend.finalproject.ProjectFiles.Env.GlobalVariableTypeEnum;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import frontend.finalproject.Model.SD.SDModel;
import utils.Json.CustomGson;
import utils.Json.CustomSerializers.GlobalVariableTypeCompoundJsonSerializer;
import utils.Json.CustomSerializers.GlobalVariableTypeEnumJsonSerializer;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// doc: https://github.com/orhaimwerthaim/AOS-WebAPI/blob/master/docs/version2/AOS_documentation_manual.md#skill-documentation-sd-file
public class SD {
    private PlpMain PlpMain;
    private List<GlobalVariableModuleParameter> GlobalVariableModuleParameters;

    private List<IAssignmentBlock> PossibleParametersValue;
    private Precondition Preconditions;

    private DynamicModel DynamicModel;

    public SD(SDModel sdModel) {
        PlpMain = new PlpMain(sdModel.getPlpMain());
        GlobalVariableModuleParameters = sdModel.getGlobalVariableModuleParameters().stream()
                .map(GlobalVariableModuleParameter::new).collect(Collectors.toList());
        Preconditions = new Precondition(sdModel.getPreconditions());
        DynamicModel = new DynamicModel(sdModel.getDynamicModel());
        PossibleParametersValue = sdModel.getPossibleParametersValue().stream().map(AssignmentBlockMultipleLines::new).collect(Collectors.toList());
    }

    public String toJson() {
        Gson gson = CustomGson.getCustomGson();

        return gson.toJson(this).replace("\\t", "\t");
    }

    public String getProjectName() {
        return PlpMain.getProject();
    }

    public PlpMain getPlpMain() {
        return PlpMain;
    }

    public List<GlobalVariableModuleParameter> getGlobalVariableModuleParameters() {
        return GlobalVariableModuleParameters != null ? GlobalVariableModuleParameters : new ArrayList<>();
    }

    public Precondition getPreconditions() {
        return Preconditions;
    }

    public backend.finalproject.ProjectFiles.SD.DynamicModel getDynamicModel() {
        return DynamicModel;
    }

    public List<IAssignmentBlock> getPossibleParametersValue() {
        return PossibleParametersValue != null ? PossibleParametersValue : new ArrayList<>();
    }
}
