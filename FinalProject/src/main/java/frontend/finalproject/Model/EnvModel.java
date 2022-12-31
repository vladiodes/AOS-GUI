package frontend.finalproject.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EnvModel {
    private Map<String,String> PlpMain;
    private Map<String,String> EnvironmentGeneral;
    private List<Map<String,Object>> GlobalVariableTypes;
    private List<Map<String,String>> GlobalVariablesDeclaration;
    private List<Map<String,String>> InitialBeliefStateAssignments;
    private List<Map<String,String>> SpecialStates;
    private List<Map<String,String>> ExtrinsicChangesDynamicModel;

    public EnvModel(){
        PlpMain = new HashMap<>();
        EnvironmentGeneral = new HashMap<>();
        GlobalVariablesDeclaration = new ArrayList<>();
        GlobalVariableTypes = new ArrayList<>();
        InitialBeliefStateAssignments = new ArrayList<>();
        SpecialStates = new ArrayList<>();
        ExtrinsicChangesDynamicModel = new ArrayList<>();
    }
    public void addEntryPlpMain(String entry, String value){
        PlpMain.put(entry,value);
    }

    public void addEnvGeneral(String entry,String value){
        EnvironmentGeneral.put(entry,value);
    }

    public void addGlobalVariableTypes(Map<String,Object> map){
        GlobalVariableTypes.add(map);
    }

    public void addGlobalVarDec(Map<String,String> map){
        GlobalVariablesDeclaration.add(map);
    }

    public void addInitBeliefStateAss(Map<String,String> map){
        InitialBeliefStateAssignments.add(map);
    }

    public void addSpecialState(Map<String,String> map){
        SpecialStates.add(map);
    }

    public void addExtrinsicChangesDynamicModel(Map<String,String> map){
        ExtrinsicChangesDynamicModel.add(map);
    }
}
