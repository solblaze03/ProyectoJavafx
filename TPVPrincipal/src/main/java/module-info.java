module com.tpvprincipal {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.postgresql.jdbc;
    requires java.sql;
    requires java.desktop;
    requires commons.math3;


    opens com.tpvprincipal to javafx.fxml;
    exports com.tpvprincipal;
    exports com.tpvprincipal.clases;
    opens com.tpvprincipal.clases to javafx.fxml;
}