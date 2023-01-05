package frontend.finalproject.Model.AM;

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

}

class RosServiceModel{
    private List<ImportCodeModel> ImportCode;
    private String ServicePath;
    private String ServiceName;
    private List<ServiceParameter> ServiceParameters;

    public RosServiceModel(){
        ServiceParameters = new ArrayList<>();
        ImportCode = new ArrayList<>();
    }

    public void addImportCode(ImportCodeModel model){
        ImportCode.add(model);
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

class ImportCodeModel{
    private String From;
    private List<String> Import;

    public ImportCodeModel(){
        Import = new ArrayList<>();
    }

    public String getFrom() {
        return From;
    }

    public void setFrom(String from) {
        From = from;
    }

    public void addImportValue(String val){
        Import.add(val);
    }
}
