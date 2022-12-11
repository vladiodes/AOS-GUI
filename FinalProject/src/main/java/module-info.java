module frontend.finalproject {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens frontend.finalproject to javafx.fxml;
    exports frontend.finalproject;
}