module frontend.finalproject {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires com.google.gson;

    opens frontend.finalproject to javafx.fxml;
    exports frontend.finalproject.Controllers;
    exports backend.finalproject;
    exports utils;
    exports frontend.finalproject.Model.Env;
    opens frontend.finalproject.Model.Env to com.google.gson;
    opens frontend.finalproject.Controllers to javafx.fxml;
    opens frontend.finalproject.Model.Common to com.google.gson;
    opens backend.finalproject.ProjectFiles to com.google.gson;
}