package frontend.finalproject.Model.AM;

import backend.finalproject.ProjectFiles.AM.ModuleActivation;

public class ServiceParameter {
    private String ServiceFieldName;
    private String AssignServiceFieldCode;

    public ServiceParameter(String ServiceFieldName, String AssignServiceFieldCode) {
        this.ServiceFieldName = ServiceFieldName;
        this.AssignServiceFieldCode = AssignServiceFieldCode;
    }

    public ServiceParameter(ModuleActivation.RosService.ServiceParameter i) {
        this.ServiceFieldName = i.getServiceFieldName();
        this.AssignServiceFieldCode = i.getAssignServiceFieldCode();
    }

    public String getServiceFieldName() {
        return ServiceFieldName;
    }

    public String getAssignServiceFieldCode() {
        return AssignServiceFieldCode;
    }
}
