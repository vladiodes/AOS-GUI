package frontend.finalproject.Model.Env;

import backend.finalproject.ProjectFiles.Common.PlpMain;
import com.google.gson.Gson;

public class PlpMainModel {
    private String Project;
    private String Name;
    private String Type;
    private double Version = 1.0;

    public PlpMainModel(String Project, String Name, String Type){
        this.Project = Project;
        this.Name = Name;
        this.Type = Type;
    }

    public PlpMainModel(PlpMain plpMain) {
        this.Project = plpMain.getProject();
        this.Name = plpMain.getName();
        this.Type = plpMain.getType();
        this.Version = plpMain.getVersion();
    }

    public String toString(){
        return new Gson().toJson(this);
    }

    public String getProject() {
        return Project;
    }

    public String getName() {
        return Name;
    }

    public String getType() {
        return Type;
    }

    public double getVersion() {
        return Version;
    }

    public void setProject(String project) {
        Project = project;
    }
}
