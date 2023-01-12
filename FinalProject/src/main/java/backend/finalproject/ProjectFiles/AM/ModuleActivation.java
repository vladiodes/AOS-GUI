package backend.finalproject.ProjectFiles.AM;

import backend.finalproject.ProjectFiles.Common.ImportCode;
import frontend.finalproject.Model.AM.ModuleActivationModel;
import frontend.finalproject.Model.AM.RosServiceModel;

import java.util.List;
import java.util.stream.Collectors;

// doc: https://github.com/orhaimwerthaim/AOS-WebAPI/blob/master/docs/version2/AOS_documentation_manual.md#moduleactivation
public class ModuleActivation {
    RosService RosService;

    public ModuleActivation(RosService rosService) {
        RosService = rosService;
    }

    public ModuleActivation(ModuleActivationModel moduleActivation) {
        RosService = new RosService(moduleActivation.getRosService());
    }

    public RosService getRosService() {
        return RosService;
    }

    // doc: https://github.com/orhaimwerthaim/AOS-WebAPI/blob/master/docs/version2/AOS_documentation_manual.md#rosservice
    private class RosService {
        private List<ImportCode> ImportCode;
        private String ServicePath;
        private String ServiceName;

        private List<ServiceParameter> ServiceParameters;

        public RosService(List<ImportCode> importCode, String servicePath, String serviceName, List<ServiceParameter> serviceParameters) {
            ImportCode = importCode;
            ServicePath = servicePath;
            ServiceName = serviceName;
            ServiceParameters = serviceParameters;
        }

        public RosService(RosServiceModel rosService) {
            ServicePath = rosService.getServicePath();
            ServiceName = rosService.getServiceName();
            ImportCode = rosService.getImportCode().stream().map(ImportCode::new).collect(Collectors.toList());
            ServiceParameters = rosService.getServiceParameters().stream().map(ServiceParameter::new).collect(Collectors.toList());
        }

        public List<ImportCode> getImportCode() {
            return ImportCode;
        }

        public String getServicePath() {
            return ServicePath;
        }

        public String getServiceName() {
            return ServiceName;
        }

        public List<ServiceParameter> getServiceParameters() {
            return ServiceParameters;
        }


        private class ServiceParameter {
            private String ServiceFieldName;
            private String AssignServiceFieldCode;

            public ServiceParameter(frontend.finalproject.Model.AM.ServiceParameter serviceFieldName) {
                ServiceFieldName = serviceFieldName.getServiceFieldName();
                AssignServiceFieldCode = serviceFieldName.getAssignServiceFieldCode();
            }

            public String getServiceFieldName() {
                return ServiceFieldName;
            }

            public String getAssignServiceFieldCode() {
                return AssignServiceFieldCode;
            }
        }
    }
}

/**
 * {

 *             "ServiceParameters": [
 *                 {
 *                     "ServiceFieldName": "goal",
 *                     "AssignServiceFieldCode": "Point(x= nav_to_x, y= nav_to_y, z= nav_to_z)"
 *                 }
 *             ]
 *         }
 */