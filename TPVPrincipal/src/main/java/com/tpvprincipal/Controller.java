package com.tpvprincipal;

import com.tpvprincipal.clases.Conn;
import com.tpvprincipal.clases.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class Controller implements Initializable {


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
                PreparedStatement psupdate = Conn.con().prepareStatement("UPDATE usuarios SET sesion_abierta = 1 where dni = ?");
                psupdate.setString(1,numdni);
                psupdate.executeUpdate();
                Principal.pantalladinero();
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


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Statement st = Conn.con().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM usuarios where sesion_abierta = 1");
            if (rs.next()){
                user.añadirUsuario(rs.getString("dni"),rs.getString("nombre"),rs.getString("privilegios"),rs.getString("url_imagen"),rs.getString("contrasenya"));
            }

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }
}