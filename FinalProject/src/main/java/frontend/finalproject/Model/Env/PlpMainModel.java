package frontend.finalproject.Model.Env;

import com.google.gson.Gson;

public class PlpMainModel {
    private String Project;
    private String Name;
    private String Type;
    private final double Version = 1.0;

    public PlpMainModel(String Project, String Name, String Type){
        this.Project = Project;
        this.Name = Name;
        this.Type = Type;
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
}
