package frontend.finalproject.Model.AM;

import java.util.ArrayList;
import java.util.List;

public class ModuleActivationModel {

    private RosServiceModel RosService;

}

class RosServiceModel{
    private String ServicePath;
    private String ServiceName;
    private List<ServiceParameter> ServiceParameters;

    public RosServiceModel(){
        ServiceParameters = new ArrayList<>();
    }

    public void addServiceParameter(String ServiceFieldName, String AssignServiceFieldCode ){
        ServiceParameters.add(new ServiceParameter(ServiceFieldName,AssignServiceFieldCode));
    }
    public String getServicePath() {
        return ServicePath;
    }

    public void setServicePath(String servicePath) {
        ServicePath = servicePath;
    }

    public String getServiceName() {
        return ServiceName;
    }

    public void setServiceName(String serviceName) {
        ServiceName = serviceName;
    }
}

class ServiceParameter {
    private String ServiceFieldName;
    private String AssignServiceFieldCode;

    public ServiceParameter(String ServiceFieldName, String AssignServiceFieldCode){
        this.ServiceFieldName = ServiceFieldName;
        this.AssignServiceFieldCode = AssignServiceFieldCode;
    }
}
