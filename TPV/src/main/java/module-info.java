module com.tpv {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires com.google.zxing;
    requires com.google.zxing.javase;
    requires java.desktop;

    opens com.tpv to javafx.fxml;
    exports com.tpv;
    exports com.tpv.clases;
    opens com.tpv.clases to javafx.fxml;
}