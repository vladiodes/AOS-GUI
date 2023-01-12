package frontend.finalproject.Model.AM;

import frontend.finalproject.Model.Common.ImportCodeModel;

import java.util.ArrayList;
import java.util.List;

public class ModuleActivationModel {

    private RosServiceModel RosService;

    public ModuleActivationModel(){
        RosService = new RosServiceModel();
    }

    public void addImportCode(ImportCodeModel model){
        RosService.addImportCode(model);
    }

    public void setServicePath(String val){
        RosService.setServicePath(val);
    }

    public void setServiceName(String val){
        RosService.setServiceName(val);
    }

    public void addServiceParam(String ServiceFieldName, String AssignServiceFieldCode){
        RosService.addServiceParameter(ServiceFieldName,AssignServiceFieldCode);
    }

    public RosServiceModel getRosService() {
        return RosService;
    }
}

