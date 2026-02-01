module SMS {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;

    exports runner.srs;
    exports utilities.srs;

    opens runner.srs to javafx.fxml;
    opens utilities.srs to javafx.fxml;
}