package backend.finalproject.ProjectFiles;

import backend.finalproject.ProjectFiles.AM.AM;
import backend.finalproject.ProjectFiles.SD.SD;

public class Skill {
    private SD sd;
    private AM am;

    public Skill(SD sd, AM am) {
        this.sd = sd;
        this.am = am;
    }

    public SD getSd() {
        return sd;
    }

    public AM getAm() {
        return am;
    }
}
