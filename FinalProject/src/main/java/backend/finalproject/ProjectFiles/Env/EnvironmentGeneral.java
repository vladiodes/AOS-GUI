package backend.finalproject.ProjectFiles.Env;


import frontend.finalproject.Model.Env.EnvironmentGeneralModel;

// doc: https://github.com/orhaimwerthaim/AOS-WebAPI/blob/master/docs/version2/AOS_documentation_manual.md#environmentgeneral
public class EnvironmentGeneral {
    int Horizon;
    double Discount; // // TODO: Legal values are in the range of (1,0)


    public EnvironmentGeneral(EnvironmentGeneralModel environmentGeneral) {
        this.Horizon = environmentGeneral.getHorizon();
        this.Discount = environmentGeneral.getDiscount();
    }

    public int getHorizon() {
        return Horizon;
    }

    public double getDiscount() {
        return Discount;
    }
}
