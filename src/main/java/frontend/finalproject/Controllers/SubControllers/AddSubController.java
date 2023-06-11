package frontend.finalproject.Controllers.SubControllers;

import frontend.finalproject.Model.Model;

public interface AddSubController {
    void setCallback(Runnable callback);

    Model getModel();
}
