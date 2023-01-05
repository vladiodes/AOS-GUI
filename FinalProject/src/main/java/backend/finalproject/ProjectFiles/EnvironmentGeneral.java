package backend.finalproject.ProjectFiles;


import frontend.finalproject.Model.Env.EnvironmentGeneralModel;

// doc: https://github.com/orhaimwerthaim/AOS-WebAPI/blob/master/docs/version2/AOS_documentation_manual.md#environmentgeneral
public class EnvironmentGeneral {
    int horizon;
    double discount; // // TODO: Legal values are in the range of (1,0)

    public EnvironmentGeneral(EnvironmentGeneralModel environmentGeneral) {
        this.horizon = environmentGeneral.getHorizon();
        this.discount = environmentGeneral.getDiscount();
    }
}
