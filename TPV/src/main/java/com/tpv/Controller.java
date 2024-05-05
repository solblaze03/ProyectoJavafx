package com.tpv;

import com.tpv.clases.Gestiontpv;
import com.tpv.clases.Usuario;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

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

    public static Usuario user(){
        return user;
    }

    @FXML
    public void inicioSesion(ActionEvent actionEvent) {
        Gestiontpv gtpv = new Gestiontpv();
        String pass = password.getText();
        String numdni = dni.getText();

        try {
            PreparedStatement ps = gtpv.Con().prepareStatement("SELECT * FROM usuarios where dni = ? and contrasenya = ? ");
            ps.setString(1,numdni);
            ps.setString(2,pass);
            ResultSet rs  = ps.executeQuery();

            if(rs.next()){
                user.añadirUsuario(numdni,rs.getString("nombre"),rs.getString("privilegios"),rs.getString("url_imagen"));
                System.out.println(user);
                user();
                Principal.escenaManejoTpv();
                System.out.println("Hola");
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error no se ha podido iniciar sesión");
                alert.setHeaderText("Error");
                alert.show();;
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }


    }
}