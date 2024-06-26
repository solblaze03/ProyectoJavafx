package com.tpv;

import com.tpv.clases.Conn;
import com.tpv.clases.Gestiontpv;
import com.tpv.clases.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
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

public class Segundaventana implements Initializable{
    @FXML
    private Label Contraseña;
    @FXML
    private Label Contraseña1;
    @FXML
    private Label Contraseña11;
    @FXML
    private Button buscarUrl;
    @FXML
    private ComboBox<String> cbprivi;
    @FXML
    private PasswordField tdpassword;
    @FXML
    private TextField tfdni;
    @FXML
    private TextField tfnombre;
    @FXML
    private ImageView imagen;
    @FXML
    private Label registroCorrecto;
    @FXML
    private Button Modificar;
    @FXML
    private Button registro;
    private double suma;
    @FXML
    private AnchorPane pane;


    @FXML
    void buscarUrl(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Elige una imagen");
        Stage stage = (Stage) buscarUrl.getScene().getWindow();
        file = fc.showOpenDialog(stage);


        try {
            if(file != null) {
                Image imagen1 = new Image(new FileInputStream(file.getAbsolutePath()));
                imagen.setImage(imagen1);
            }else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("No has elegido ninguna imagen");
                alert.show();
            }
        }catch (FileNotFoundException e){
            System.out.println(e.getMessage());
        }
        //copyimage(file.getAbsolutePath());

    }
    private Usuario usuario;

    private File file;
    @FXML
    void Registrar(ActionEvent event) {
        try {

            String sql = "INSERT INTO usuarios (dni, nombre, contrasenya, sesion_abierta, fecha_creacion, url_imagen, privilegios) VALUES (?, ?, ?, 0, NOW(), ?, ?);";
            PreparedStatement ps = Conn.con().prepareStatement(sql);
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
            registroCorrecto.setText("Registro agregado");
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmación");
            alert.setHeaderText("Se ha registrado correctamente el usuario "+tfnombre.getText());
            alert.show();


            Adduser.stage.close();
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

        }catch (IOException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("IOException");
            alert.setHeaderText(e.getMessage());
            alert.show();
        }
        return url;
    }


    @FXML
    public void Modificar(ActionEvent actionEvent) {


        ObservableList <Usuario> lista = Adduser.users;
        //cbprivi.setValue(usuario.getPrivilegios());
        String imagen = "";
        if(file  == null){
            imagen = usuario.getUrlImagen();
        }else{
            String  url  = copyimage(file.getAbsolutePath());
            imagen = "imguser/" + url;
        }

        try {
            String sql = "UPDATE usuarios SET dni = ? , nombre = ?, contrasenya = ?, url_imagen = ? , privilegios = ? WHERE (dni = ?);";
            PreparedStatement ps = Conn.con().prepareStatement(sql);
            ps.setString(1,tfdni.getText());
            ps.setString(2,tfnombre.getText());
            ps.setString(3,tdpassword.getText());
            ps.setString(4,imagen);
            ps.setString(5,cbprivi.getValue());
            ps.setString(6,usuario.getDNI());
            ps.executeUpdate();

            lista.set(Adduser.numusuario, new Usuario(tfdni.getText(), tfnombre.getText(), cbprivi.getValue(), imagen, tdpassword.getText()));
            Adduser.stage.close();
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Modificación exitosa");
            alert.setHeaderText("Se ha modificado correctamente el registro en la base de datos.");
            alert.show();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

        // new Usuario(rs.getString("dni"),rs.getString("nombre"),rs.getString("privilegios"),rs.getString("url_imagen"))
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pane.getStylesheets().add(getClass().getResource("css/escenaproductos.css").toExternalForm());
        usuario =  Adduser.moduser;
        if(usuario != null) {
            tfdni.setText(usuario.getDNI());
            tfnombre.setText(usuario.getNombre());
            tdpassword.setText(usuario.getPassword());
            Modificar.setVisible(true);
            registro.setVisible(false);

            if (usuario.getUrlImagen() != null) {

                File file = new File("src/main/resources/com/tpv/"+usuario.getUrlImagen());
                Image image = new Image(file.toURI().toString());
                imagen.setImage(image);


            }


        }else{
            tfdni.setText("");
            tfnombre.setText("");
            Modificar.setVisible(false);
            registro.setVisible(true);
        }

        ObservableList<String> listaa = FXCollections.observableArrayList();
        listaa.add("Admin");
        listaa.add("Usuario");


        cbprivi.getItems().setAll(listaa);

    }
}
