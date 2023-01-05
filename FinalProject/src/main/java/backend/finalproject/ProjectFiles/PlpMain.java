package backend.finalproject.ProjectFiles;

import frontend.finalproject.Model.Env.PlpMainModel;

// doc: https://github.com/orhaimwerthaim/AOS-WebAPI/blob/master/docs/version2/AOS_documentation_manual.md#plpmain
public class PlpMain {
    /*
    describes the project name, all documentation files in the project must have the same name
    (e.g., "cleaning_robot1").
     */
    String project;

    /*
    Stores the file name.
    Environment files should always be named "environment"
    skill's AM and SD files are named by the skill they document, and corresponding AM and SD files must have the same name.
     */
    String name; // TODO: if type Environment validate name is "environment"

    // The file type. "Environment" for environment files, "PLP" for SD files, and "Glue" for AM files.
    String type; // TODO: change to enum?

    // current documentation version
    double version;

    public PlpMain(PlpMainModel plpMain) {
        this.name = plpMain.getName();
        this.project = plpMain.getProject();
        this.type = plpMain.getType();
        this.version = plpMain.getVersion();
    }
}
