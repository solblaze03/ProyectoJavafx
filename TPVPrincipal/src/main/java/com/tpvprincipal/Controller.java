package com.tpvprincipal;

import com.tpvprincipal.clases.Conn;
import com.tpvprincipal.clases.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Controller {


    @FXML
    private Button Inicio;
    @FXML
    private PasswordField password;
    @FXML
    private TextField dni;
    static Usuario user = new Usuario();
    @FXML
    private Pane panel;

    public static Usuario user(){
        return user;
    }

    @FXML
    public void inicioSesion(ActionEvent actionEvent) {

        String pass = password.getText();
        String numdni = dni.getText();

        try {
            PreparedStatement ps = Conn.con().prepareStatement("SELECT * FROM usuarios where dni = ? and contrasenya = ? ;");
            ps.setString(1,numdni);
            ps.setString(2,pass);
            ResultSet rs  = ps.executeQuery();
            if(rs.next()){
                user.añadirUsuario(numdni,rs.getString("nombre"),rs.getString("privilegios"),rs.getString("url_imagen"),rs.getString("contrasenya"));
                System.out.println(user);
                user();

                dni.setText("");
                password.setText("");
                System.out.println("Hola");
                Principal.pantallaPrincipal();
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error no se ha podido iniciar sesión");
                alert.setHeaderText("Error");
                alert.show();
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }


    }


}