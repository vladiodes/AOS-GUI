package backend.finalproject.ProjectFiles.AM;

import backend.finalproject.ProjectFiles.AM.LocalVariablesInit.DataPublishedRobotFramework;
import backend.finalproject.ProjectFiles.AM.LocalVariablesInit.SDParameters;
import backend.finalproject.ProjectFiles.AM.LocalVariablesInit.SkillCodeReturnValue;
import backend.finalproject.ProjectFiles.Common.PlpMain;
import backend.finalproject.ProjectFiles.AM.LocalVariablesInit.LocalVariablesInitialization;
import frontend.finalproject.Model.AM.AMModel;
import frontend.finalproject.Model.AM.LocalVariablesInitializationModel;
import frontend.finalproject.Model.AM.SDParametersModel;
import frontend.finalproject.Model.AM.SkillCodeReturnValueModel;


import java.util.ArrayList;
import java.util.List;

// doc: https://github.com/orhaimwerthaim/AOS-WebAPI/blob/master/docs/version2/AOS_documentation_manual.md#abstraction-mapping-am-file
public class AM {
    private PlpMain PlpMain;
    private String GlueFramework; // type of robot framework (e.g., "ROS" for ROS1)
    private ModuleResponse ModuleResponse;
    private ModuleActivation ModuleActivation;
    private List<LocalVariablesInitialization> LocalVariablesInitialization;

    public AM(PlpMain plpMain, String glueFramework, ModuleResponse moduleResponse, ModuleActivation moduleActivation, List<LocalVariablesInitialization> localVariablesInitialization) {
        PlpMain = plpMain;
        GlueFramework = glueFramework;
        ModuleResponse = moduleResponse;
        ModuleActivation = moduleActivation;
        LocalVariablesInitialization = localVariablesInitialization;
    }

    public AM(AMModel amModel){
        PlpMain = new PlpMain(amModel.getPlpMain());
        GlueFramework = amModel.getGlueFramework();
        ModuleResponse = new ModuleResponse(amModel.getModuleResponse());
        ModuleActivation = new ModuleActivation(amModel.getModuleActivation());
        LocalVariablesInitialization = CopyLocalVariablesInitialization(amModel.getLocalVariablesInitialization());
    }

    private List<LocalVariablesInitialization> CopyLocalVariablesInitialization(List<LocalVariablesInitializationModel> localVariablesInitialization) {
        LocalVariablesInitialization = new ArrayList<>();
        for (LocalVariablesInitializationModel initializationModel : localVariablesInitialization){
            if (initializationModel instanceof frontend.finalproject.Model.AM.DataPublishedRobotFramework){
                LocalVariablesInitialization.add(new DataPublishedRobotFramework((frontend.finalproject.Model.AM.DataPublishedRobotFramework) initializationModel));
            }
            else if (initializationModel instanceof SkillCodeReturnValueModel){
                LocalVariablesInitialization.add(new SkillCodeReturnValue((SkillCodeReturnValueModel) initializationModel));
            }
            else if (initializationModel instanceof SDParametersModel){
                LocalVariablesInitialization.add(new SDParameters((SDParametersModel) initializationModel));
            }
        }
        return LocalVariablesInitialization;
    }

    public backend.finalproject.ProjectFiles.Common.PlpMain getPlpMain() {
        return PlpMain;
    }

    public String getGlueFramework() {
        return GlueFramework;
    }

    public backend.finalproject.ProjectFiles.AM.ModuleResponse getModuleResponse() {
        return ModuleResponse;
    }

    public backend.finalproject.ProjectFiles.AM.ModuleActivation getModuleActivation() {
        return ModuleActivation;
    }

    public List<backend.finalproject.ProjectFiles.AM.LocalVariablesInit.LocalVariablesInitialization> getLocalVariablesInitialization() {
        return LocalVariablesInitialization;
    }
}