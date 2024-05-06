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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.nio.file.*;
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
    private ImageView imagen;


    @FXML
    void buscarUrl(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Elige una imagen");
        Stage stage = (Stage) buscarUrl.getScene().getWindow();
        file = fc.showOpenDialog(stage);
        System.out.println(file.getAbsolutePath());
        try {
            Image imagen1 = new Image(new FileInputStream(file.getAbsolutePath()));
            imagen.setImage(imagen1);
        }catch (FileNotFoundException e){
            System.out.println(e.getMessage());
        }
        //copyimage(file.getAbsolutePath());

    }
    private Usuario usuario;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usuario =  Adduser.moduser;
        if(usuario != null) {
            tfdni.setText(usuario.getDNI());
            tfnombre.setText(usuario.getNombre());
            cbprivi.setValue(usuario.getPrivilegios());
            if (usuario.getUrlImagen() != null) {
                Image image = new Image(getClass().getResource(usuario.getUrlImagen()).toExternalForm());
                imagen.setImage(image);
            }

            System.out.println(usuario.getUrlImagen());
        }else{
            tfdni.setText("");
            tfnombre.setText("");
        }

            ObservableList<String> listaa = FXCollections.observableArrayList();
            listaa.add("Admin");
            listaa.add("Usuario");


            cbprivi.getItems().setAll(listaa);

    }
    private File file;
    @FXML
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
                System.out.println("copy : "+imagen);
                ps.setString(4, "imguser/" + imagen);
            }

            ps.setString(5,(String )cbprivi.getValue());
            ps.executeUpdate();
        }catch (SQLException e ){
            System.out.println(e.getMessage());
        }

    }
    public String copyimage(String ruta) {
        //copiar
        String rutaimagen[] = ruta.split("\\\\");
        String url = "";
        String osName = System.getProperty("os.name");
        if (osName.startsWith("Windows")) {
            String imagen[] = file.getAbsolutePath().split("\\\\");
            url = imagen[imagen.length-1];


        } else if (osName.startsWith("Linux")) {
            String imagen[] = file.getAbsolutePath().split("/");
            url = imagen[imagen.length-1];
        }
        System.out.println("Url: "+url);

        try {
            Path origen = Paths.get(ruta);
            //System.out.println(url);
            Path destino = Paths.get("./src/main/resources/com/tpv/imguser/"+url);
            Files.copy(origen, destino, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("copiado");
        }catch (IOException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return url;
    }


}
