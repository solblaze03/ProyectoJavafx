package com.tpv;

import com.tpv.clases.Usuario;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class Manejotpv implements Initializable {


    @javafx.fxml.FXML
    private Label privileges;
    @javafx.fxml.FXML
    private Button Agregar;
    @javafx.fxml.FXML
    private Button Ventas;
    @javafx.fxml.FXML
    private ImageView imagenUser;
    @javafx.fxml.FXML
    private Button Salir;
    @javafx.fxml.FXML
    private Label nombre;
    @FXML
    private Button agragarProductos;
    @FXML
    private AnchorPane pane;
    @FXML
    private Label welcome;
    @FXML
    private Button Salir1;


    @javafx.fxml.FXML
    public void agregarProductos(ActionEvent actionEvent) {
        Principal.escenaproductos();
    }

    @javafx.fxml.FXML
    public void AgregarUsers(ActionEvent actionEvent) {
        Principal.addusuario();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Usuario user = Controller.user();
        nombre.setText(user.getNombre());
        privileges.setText(user.getPrivilegios());
        if(user.getUrlImagen() != null) {

            Image image = new Image(getClass().getResource(user.getUrlImagen()).toExternalForm());
            imagenUser.setImage(image);
        }
        welcome.setText("Bienvenido de nuevo "+user.getNombre());
        if("Usuario".equals(user.getPrivilegios()) ){
            Agregar.setDisable(true);
            agragarProductos.setDisable(true);
        }else{
            agragarProductos.setDisable(false);
        }

        pane.getStylesheets().add(getClass().getResource("css/manejotpv.css").toExternalForm());

    }

    @javafx.fxml.FXML
    public void Salir(ActionEvent actionEvent) {
        Principal.escenainicio();

    }


    @FXML
    public void agregarCategorias(ActionEvent actionEvent) {
        Principal.escenaCategorias();
    }

    @FXML
    public void Ventas(ActionEvent actionEvent) {
        Principal.graficas();
    }

    @FXML
    public void factura(ActionEvent actionEvent) {
        Principal.buscarFactura();
    }
}
