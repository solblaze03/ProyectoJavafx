package com.tpv;

import com.tpv.clases.Gestiontpv;
import com.tpv.clases.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.*;
import java.util.ResourceBundle;

public class Adduser implements Initializable{
    private ObservableList<Usuario> users = FXCollections.observableArrayList();
    @FXML
    private TableView <Usuario> tview;
    @FXML
    private TableColumn <Usuario,String> privilegios;
    @FXML
    private TableColumn <Usuario,String> usuario;
    @FXML
    private Button agregarUsuario;
    @FXML
    private TableColumn <Usuario,String> DNI;
    @FXML
    private Label Contraseña;

    @FXML
    private Label Contraseña1;

    @FXML
    private Label Contraseña11;

    @FXML
    private ChoiceBox cbprivi = new ChoiceBox<>();

    @FXML
    private PasswordField tdpassword;

    @FXML
    private TextField tfdni;

    @FXML
    private TextField tfnombre;
    @FXML
    private Button buscarUrl;



    @FXML
    public void buscar(){
        //System.out.println("Entra");
        Gestiontpv gtpv = new Gestiontpv();
        try {
            Statement st = gtpv.Con().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM usuarios WHERE privilegios <> 'Superoot'");
            while (rs.next()){
                users.add(new Usuario(rs.getString("dni"),rs.getString("nombre"),rs.getString("privilegios")));
            }

            DNI.setCellValueFactory(new PropertyValueFactory<>("DNI"));
            usuario.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            privilegios.setCellValueFactory(new PropertyValueFactory<>("privilegios"));

            tview.setItems(users);

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }catch (NullPointerException e){
            System.out.println(e.getMessage());
        }
    }


    @FXML
    public void agregarUsuario(ActionEvent actionEvent) {

        try {
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("formulario.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();

        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
    @FXML
    void cargarTabla(ActionEvent event) {
        users.clear();
        buscar();
    }
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
                ps.setString(4, "imguser/" + imagen);
            }

            ps.setString(5,(String )cbprivi.getValue());
            ps.executeUpdate();
        }catch (SQLException e ){
            System.out.println(e.getMessage());
        }
    }
    private File file;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    ObservableList<String> listaa = FXCollections.observableArrayList();
    listaa.add("Admin");
    listaa.add("Usuario");
    cbprivi.getItems().setAll(listaa);

        // "Admin","Usuario"
    }

    @FXML
    void buscarUrl(ActionEvent event) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Elige una imagen");
        Stage stage = (Stage) buscarUrl.getScene().getWindow();
        file = fc.showOpenDialog(stage);
        //copyimage(file.getAbsolutePath());
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
