package com.tpvprincipal;

import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.AnchorPane;
import org.apache.commons.math3.util.Precision;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class Billete implements Initializable {
    @javafx.fxml.FXML
    private Spinner <Integer> cincoeuros;
    @javafx.fxml.FXML
    private Spinner <Integer> diezcentimos;
    @javafx.fxml.FXML
    private Spinner <Integer> uneuro;
    @javafx.fxml.FXML
    private Spinner <Integer> doseuros;
    @javafx.fxml.FXML
    private Spinner <Integer> uncentimo;
    @javafx.fxml.FXML
    private Spinner <Integer> doscentimos;
    @javafx.fxml.FXML
    private Spinner <Integer> cincocentimos;
    @javafx.fxml.FXML
    private Spinner <Integer> ventieeuros;
    @javafx.fxml.FXML
    private Spinner <Integer> diezeuros;
    @javafx.fxml.FXML
    private Spinner <Integer> cincuentaeuros;
    @javafx.fxml.FXML
    private Spinner <Integer> cincuentcentimos;
    @javafx.fxml.FXML
    private AnchorPane panel;
    static Double suma;
    @javafx.fxml.FXML
    private Spinner <Integer> veintecent;
    @javafx.fxml.FXML
    private Button cerrarcaja;
    @javafx.fxml.FXML
    private Button ingresar;

    @javafx.fxml.FXML
    public void ingresar(ActionEvent actionEvent) {
        suma = 0.0;
        suma += diezcentimos.getValue() * 0.10;
        suma += uneuro.getValue() * 1.0;
        suma += doseuros.getValue() * 2.0;
        suma += uncentimo.getValue() * 0.01;
        suma += cincoeuros.getValue() * 5.0;
        suma += doscentimos.getValue() * 0.02;
        suma += cincocentimos.getValue() * 0.05;
        suma += ventieeuros.getValue() * 20.0;
        suma += diezeuros.getValue() * 10.0;
        suma += veintecent.getValue() * 0.20;
        suma += cincuentaeuros.getValue() * 50.0 ;
        suma += cincuentcentimos.getValue() * 0.50;

        suma = Precision.round(suma,2);

        if (suma < 250){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Alerta");
            alert.setHeaderText("Se recomienda tener mas dinero en la caja");
            alert.showAndWait();
        }


        Principal.pantallaPrincipal();

    }


    public void confSpinner(){
        SpinnerValueFactory<Integer> valor = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,1000,0);
        SpinnerValueFactory<Integer> valor1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,1000,0);
        SpinnerValueFactory<Integer> valor2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,1000,0);
        SpinnerValueFactory<Integer> valor3 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,1000,0);
        SpinnerValueFactory<Integer> valor4 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,1000,0);
        SpinnerValueFactory<Integer> valor5 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,1000,0);
        SpinnerValueFactory<Integer> valor6 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,1000,0);
        SpinnerValueFactory<Integer> valor7 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,1000,0);
        SpinnerValueFactory<Integer> valor8 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,1000,0);
        SpinnerValueFactory<Integer> valor9 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,1000,0);
        SpinnerValueFactory<Integer> valor10 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,1000,0);
        SpinnerValueFactory<Integer> valor11 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0,1000,0);
        uncentimo.setValueFactory(valor5);
        doscentimos.setValueFactory(valor6);
        cincocentimos.setValueFactory(valor7);
        diezcentimos.setValueFactory(valor2);
        veintecent.setValueFactory(valor10);
        cincuentcentimos.setValueFactory(valor);
        uneuro.setValueFactory(valor3);
        doseuros.setValueFactory(valor4);
        cincoeuros.setValueFactory(valor1);
        diezeuros.setValueFactory(valor9);
        ventieeuros.setValueFactory(valor8);
        cincuentaeuros.setValueFactory(valor11);













    }
    private double cierre;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println(suma);
        if(suma == null) {
            cerrarcaja.setVisible(false);
            boolean b = panel.getStylesheets().add(getClass().getResource("css/dinero.css").toExternalForm());
            System.out.println(b);
            confSpinner();
        }else{

            cerrarcaja.setVisible(true);
            ingresar.setVisible(false);
            confSpinner();


        }
    }

    @javafx.fxml.FXML
    public void cerrarCaja(ActionEvent actionEvent) {
        cincuentcentimos.cancelEdit();
        cierre = 0.0;
        cierre += diezcentimos.getValue() * 0.10;
        cierre += uneuro.getValue() * 1.0;
        cierre += doseuros.getValue() * 2.0;
        cierre += uncentimo.getValue() * 0.01;
        cierre += cincoeuros.getValue() * 5.0;
        cierre += doscentimos.getValue() * 0.02;
        cierre += cincocentimos.getValue() * 0.05;
        cierre += ventieeuros.getValue() * 20.0;
        cierre += diezeuros.getValue() * 10.0;
        cierre += veintecent.getValue() * 0.20;
        cierre += cincuentaeuros.getValue() * 50.0 ;


        cierre = Precision.round(cierre,2);
        System.out.println("Cierre "+cincuentaeuros.getValue());


        double total = controllerpantalla.dineroencaja;
        System.out.println("total "+cierre+ " "+total);

        if (cierre == total){
            System.out.println("Entra");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Descuadre en la caja");
            alert.setHeaderText("No hubo descuadre en la caja");
            alert.showAndWait();
            System.exit(0);
        }else{
            System.out.println("Entra");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Descuadre en la caja");
            alert.setHeaderText("Hubo un descuadre de caja de " + (cierre - total) +" euros" );
            alert.showAndWait();
            System.exit(0);
        }



    }
}
