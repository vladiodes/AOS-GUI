package frontend.finalproject.Model.AM;

public class ServiceParameter {
    private String ServiceFieldName;
    private String AssignServiceFieldCode;

    public ServiceParameter(String ServiceFieldName, String AssignServiceFieldCode) {
        this.ServiceFieldName = ServiceFieldName;
        this.AssignServiceFieldCode = AssignServiceFieldCode;
    }

    public String getServiceFieldName() {
        return ServiceFieldName;
    }

    public String getAssignServiceFieldCode() {
        return AssignServiceFieldCode;
    }
}
