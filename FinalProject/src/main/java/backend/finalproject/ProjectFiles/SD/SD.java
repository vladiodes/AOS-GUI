package backend.finalproject.ProjectFiles.SD;

import backend.finalproject.ProjectFiles.Common.PlpMain;
import frontend.finalproject.Model.SD.SDModel;

import java.util.List;
import java.util.stream.Collectors;

// doc: https://github.com/orhaimwerthaim/AOS-WebAPI/blob/master/docs/version2/AOS_documentation_manual.md#skill-documentation-sd-file
public class SD {
    private PlpMain PlpMain;
    private List<GlobalVariableModuleParameter> GlobalVariableModuleParameters;
    private Precondition Preconditions;

    private DynamicModel DynamicModel;

    public SD(SDModel sdModel) {
        PlpMain = new PlpMain(sdModel.getPlpMain());
        GlobalVariableModuleParameters = sdModel.getGlobalVariableModuleParameters().stream()
                .map(GlobalVariableModuleParameter::new).collect(Collectors.toList());
        Preconditions = new Precondition(sdModel.getPreconditions());
        DynamicModel = new DynamicModel(sdModel.getDynamicModel());
    }
}
