package frontend.finalproject.Model.Env;

import backend.finalproject.ProjectFiles.Env.EnvironmentGeneral;
import com.google.gson.Gson;

public class EnvironmentGeneralModel {
    private int Horizon;
    private double Discount;

    public EnvironmentGeneralModel(int Horizon, double Discount){
        this.Horizon = Horizon;
        this.Discount = Discount;
    }

    public EnvironmentGeneralModel(EnvironmentGeneral environmentGeneral) {
        this.Horizon = environmentGeneral.getHorizon();
        this.Discount = environmentGeneral.getDiscount();
    }

    public String toString(){
        return new Gson().toJson(this);
    }

    public int getHorizon() {
        return Horizon;
    }

    public double getDiscount() {
        return Discount;
    }
}
