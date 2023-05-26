module com.da330a.jfx {
    requires javafx.controls;
    requires javafx.fxml;

    exports com.da330a.jfx;
    opens com.da330a.jfx to javafx.fxml;}