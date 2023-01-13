package frontend.finalproject.Model.AM;

import backend.finalproject.ProjectFiles.AM.ModuleActivation;
import frontend.finalproject.Model.Common.ImportCodeModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RosServiceModel {
    private List<ImportCodeModel> ImportCode;
    private String ServicePath;
    private String ServiceName;
    private List<ServiceParameter> ServiceParameters;

    public RosServiceModel() {
        ServiceParameters = new ArrayList<>();
        ImportCode = new ArrayList<>();
    }

    public RosServiceModel(ModuleActivation.RosService rosService) {
        ImportCode = rosService.getImportCode().stream().map(ImportCodeModel::new).collect(Collectors.toList());
        ServicePath = rosService.getServicePath();
        ServiceName = rosService.getServiceName();
        ServiceParameters = rosService.getServiceParameters().stream().map(i -> new ServiceParameter(i)).collect(Collectors.toList());
    }

    public void addImportCode(ImportCodeModel model) {
        ImportCode.add(model);
    }

    public void addServiceParameter(String ServiceFieldName, String AssignServiceFieldCode) {
        ServiceParameters.add(new ServiceParameter(ServiceFieldName, AssignServiceFieldCode));
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

    public List<ImportCodeModel> getImportCode() {
        return ImportCode;
    }

    public List<ServiceParameter> getServiceParameters() {
        return ServiceParameters;
    }
}
