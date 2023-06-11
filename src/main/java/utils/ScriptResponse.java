package utils;

public class ScriptResponse {

    private String output = "";
    private boolean saveFig = false;

    public ScriptResponse(){

    }
    public void setOutput(String output) {
        this.output = output;
    }

    public void setSaveFig(boolean saveFig) {
        this.saveFig = saveFig;
    }

    public String getOutput() {
        return output;
    }

    public boolean getSaveFig(){
        return saveFig;
    }
}