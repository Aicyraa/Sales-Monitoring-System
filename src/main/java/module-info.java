module SMS {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    exports runner;
    exports models;
    exports controllers;

    opens runner to javafx.fxml;
    opens models to javafx.fxml;
    opens controllers to javafx.fxml;
}