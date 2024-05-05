package com.tpv;

import com.tpv.clases.Usuario;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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
    private Button adduser;
    @javafx.fxml.FXML
    private ImageView imagenUser;
    @javafx.fxml.FXML
    private Button Salir;
    @javafx.fxml.FXML
    private Label nombre;
    @FXML
    private Label Contraseña;
    @FXML
    private Label Contraseña1;
    @FXML
    private Label Contraseña11;
    @FXML
    private ComboBox<String> cbprivi;
    @FXML
    private PasswordField tdpassword;
    @FXML
    private TextField tfdni;
    @FXML
    private TextField tfnombre;


    @FXML
    void buscar(ActionEvent event) {

    }

    @javafx.fxml.FXML
    public void agregarProductos(ActionEvent actionEvent) {
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

        if("Usuario".equals(user.getPrivilegios()) ){
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
