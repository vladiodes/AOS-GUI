package frontend.finalproject.Model.Env;

import com.google.gson.Gson;

public class EnvironmentGeneralModel {
    private int Horizon;
    private double Discount;

    public EnvironmentGeneralModel(int Horizon, double Discount){
        this.Horizon = Horizon;
        this.Discount = Discount;
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
