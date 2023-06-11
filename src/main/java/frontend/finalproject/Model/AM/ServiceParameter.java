package frontend.finalproject.Model.AM;

import backend.finalproject.ProjectFiles.AM.ModuleActivation;
import frontend.finalproject.Model.Model;

public class ServiceParameter implements Model {
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

    public void setAssignServiceFieldCode(String assignServiceFieldCode) {
        AssignServiceFieldCode = assignServiceFieldCode;
    }

    public void setServiceFieldName(String serviceFieldName) {
        ServiceFieldName = serviceFieldName;
    }
}
