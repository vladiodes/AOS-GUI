module frontend.finalproject {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires com.google.gson;
    requires okhttp3;

    opens frontend.finalproject to javafx.fxml;
    exports frontend.finalproject.Controllers;
    exports DTO.HttpRequests;
    exports backend.finalproject;
    exports backend.finalproject.IntegrationRequests;
    exports utils;
    exports frontend.finalproject.Model.Env;
    exports frontend.finalproject.Model.AM;
    exports frontend.finalproject.Model.SD;
    opens frontend.finalproject.Model.Env to com.google.gson;
    opens DTO.HttpRequests to com.google.gson;
    opens utils to com.google.gson;
    opens frontend.finalproject.Controllers to javafx.fxml;
    opens frontend.finalproject.Controllers.SubControllers to javafx.fxml;
    opens frontend.finalproject.Model.Common to com.google.gson;
    opens backend.finalproject.ProjectFiles to com.google.gson;
    opens frontend.finalproject.Model.SD to com.google.gson;
    opens frontend.finalproject.Model.AM to com.google.gson;
    opens backend.finalproject.ProjectFiles.Env to com.google.gson;
    opens backend.finalproject.ProjectFiles.Common to com.google.gson;
    opens backend.finalproject.ProjectFiles.SD to com.google.gson;
    opens backend.finalproject.ProjectFiles.AM to com.google.gson;
    opens backend.finalproject.ProjectFiles.AM.LocalVariablesInit to com.google.gson;
    exports frontend.finalproject.Utils;
    opens frontend.finalproject.Utils to javafx.fxml;
    exports frontend.finalproject.ServerResponseDisplayers;
    opens frontend.finalproject.ServerResponseDisplayers to javafx.fxml;
}