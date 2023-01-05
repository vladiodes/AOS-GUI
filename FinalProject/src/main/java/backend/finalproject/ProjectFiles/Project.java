package backend.finalproject.ProjectFiles;

import frontend.finalproject.Model.Env.EnvModel;

import java.util.ArrayList;
import java.util.List;

public class Project {
    Environment environment;
    List<Skill> skils;

    public Project(EnvModel envModel) {
        environment = new Environment(envModel);
        skils = new ArrayList<>();
    }
}
