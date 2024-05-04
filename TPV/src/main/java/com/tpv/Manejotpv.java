package com.tpv;

import com.tpv.clases.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
    private Label nombre;
    @javafx.fxml.FXML
    private ImageView imagenUser;
    @javafx.fxml.FXML
    private Button Salir;
    @javafx.fxml.FXML
    private Button adduser;

    @javafx.fxml.FXML
    public void agregarProductos(ActionEvent actionEvent) {
    }

    @javafx.fxml.FXML
    public void AgregarUsers(ActionEvent actionEvent) {
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Usuario user = Controller.user();
        nombre.setText(user.getNombre());
        privileges.setText(user.getPrivilegios());

        Image image = new Image(getClass().getResource(user.getUrlImagen()).toExternalForm());
        imagenUser.setImage(image);

        if("usuario".equals(user.getPrivilegios()) ){
            adduser.setDisable(true);
        }else{
            adduser.setDisable(false);
        }

    }

    @javafx.fxml.FXML
    public void Salir(ActionEvent actionEvent) {
        Principal.escenainicio();

    }
}
