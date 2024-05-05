package com.tpv;

import com.tpv.clases.Gestiontpv;
import com.tpv.clases.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class Segundaventana implements Initializable {

    @FXML
    private Label Contraseña;

    @FXML
    private Label Contraseña1;

    @FXML
    private Label Contraseña11;

    @FXML
    private Button buscarUrl;

    @FXML
    private ChoiceBox<String> cbprivi;

    @FXML
    private PasswordField tdpassword;

    @FXML
    private TextField tfdni;

    @FXML
    private TextField tfnombre;


    @FXML
    @Deprecated
    void buscarUrl(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Elige una imagen");
        Stage stage = (Stage) buscarUrl.getScene().getWindow();
        file = fc.showOpenDialog(stage);
        //copyimage(file.getAbsolutePath());
    }
    private Usuario usuario;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usuario =  Adduser.moduser;
        tfdni.setText(usuario.getDNI());
        tfnombre.setText(usuario.getNombre());

        System.out.println(tfnombre.getText());
        ObservableList<String> listaa = FXCollections.observableArrayList();
        listaa.add("Admin");
        listaa.add("Usuario");



        cbprivi.getItems().setAll(listaa);
    }
    private File file;
    @FXML
    @Deprecated
    void Registrar(ActionEvent event) {
        try {
            Gestiontpv gtpv = new Gestiontpv();
            String sql = "INSERT INTO `tpv`.`usuarios` (`dni`, `nombre`, `contrasenya`, `sesion_abierta`, `fecha_creacion`, `url_imagen`, `privilegios`) VALUES (?, ?, ?, 0, NOW(), ?, ?);";
            PreparedStatement ps = gtpv.Con().prepareStatement(sql);
            ps.setString(1,tfdni.getText());
            ps.setString(2,tfnombre.getText());
            ps.setString(3,tdpassword.getText());
            if(file == null){
                ps.setString(4,null);
            }else{
                String imagen = copyimage(file.getAbsolutePath());
                ps.setString(4, "imguser/" + imagen);
            }

            ps.setString(5,(String )cbprivi.getValue());
            ps.executeUpdate();
        }catch (SQLException e ){
            System.out.println(e.getMessage());
        }


    }
    public String copyimage(String ruta) {
        String rutaimagen[] = ruta.split("\\\\");
        String imagen = rutaimagen[rutaimagen.length-1];
        try {
            Path origen = Paths.get(ruta);
            Path destino = Paths.get("./src/main/resources/com/tpv/imguser/"+imagen);
            Files.copy(origen, destino, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("copiado");
            return imagen;
        }catch (IOException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return null;
    }


}
