module com.tpv {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens com.tpv to javafx.fxml;
    exports com.tpv;
    exports com.tpv.clases;
    opens com.tpv.clases to javafx.fxml;
}