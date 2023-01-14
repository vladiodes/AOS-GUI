package backend.finalproject.ProjectFiles;

import backend.finalproject.ProjectFiles.AM.AM;
import backend.finalproject.ProjectFiles.Common.PlpMain;
import backend.finalproject.ProjectFiles.SD.SD;

public class Skill {
    private SD sd;
    private AM am;
    private String name;

    public Skill(SD sd, AM am) throws Exception {
        PlpMain sdPlp = sd.getPlpMain();
        PlpMain amPlp = am.getPlpMain();
        if (!sdPlp.getName().equals(amPlp.getName())){
            throw new Exception("SD and AM files has to have matching skill name");
        }
        if (!amPlp.getType().equals("Glue")){
            throw new Exception("AM Plp has to be type Glue");
        }
        if (!sdPlp.getType().equals("PLP")){
            throw new Exception("SD Plp has to be type PLP");
        }
        this.sd = sd;
        this.am = am;
        this.name = sdPlp.getName();
    }

    public SD getSd() {
        return sd;
    }

    public AM getAm() {
        return am;
    }


    public String getSkillName() {
        return name;
    }
}
