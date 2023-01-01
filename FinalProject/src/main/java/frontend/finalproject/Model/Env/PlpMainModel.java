package frontend.finalproject.Model.Env;

import com.google.gson.Gson;

public class PlpMainModel {
    private String Project;
    private final String Name = "environment";
    private final String Type = "Environment";
    private final double Version = 1.0;

    public PlpMainModel(String Project){
        this.Project = Project;
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
