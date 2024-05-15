module com.tpvprincipal {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.tpvprincipal to javafx.fxml;
    exports com.tpvprincipal;
    exports com.tpvprincipal.clases;
    opens com.tpvprincipal.clases to javafx.fxml;
}