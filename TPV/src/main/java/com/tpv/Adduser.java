package com.tpv;

import com.tpv.clases.Conn;
import com.tpv.clases.Gestiontpv;
import com.tpv.clases.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
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
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class Adduser implements Initializable{
    static ObservableList<Usuario> users = FXCollections.observableArrayList();
    @FXML
    private TableView <Usuario> tview;
    @FXML
    private TableColumn <Usuario,String> privilegios;
    @FXML
    private TableColumn <Usuario,String> usuario;
    @FXML
    private TableColumn <Usuario,String> DNI;
    static int numusuario;
    @FXML
    private Button cargaTabla;
    @FXML
    private Button Modificar;
    @FXML
    private Button agregarUsuario;
    @FXML
    private Button eliminar;


    @FXML
    public void buscar(){
        //System.out.println("Entra");


        try {

            Statement st = Conn.con().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM usuarios WHERE privilegios <> 'Super Administrador'");
            while (rs.next()){
                users.add(new Usuario(rs.getString("dni"),rs.getString("nombre"),rs.getString("privilegios"),rs.getString("url_imagen"),rs.getString("contrasenya")));
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

static Stage stage;
    static FXMLLoader loader5;
    @FXML
    public void agregarUsuario(ActionEvent actionEvent) {

        try {
            moduser=null;
            stage = new Stage();
            loader5 = new FXMLLoader(getClass().getResource("formulario.fxml"));

            Scene scene = new Scene(loader5.load());
            stage.setScene(scene);
            stage.setResizable(false);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();






        }catch (IOException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    @FXML
    void cargarTabla(ActionEvent event) {
        users.clear();
        buscar();
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tview.getSelectionModel().getSelectedItems().addListener(selectorTablaUsuarios);
        users.clear();
        buscar();
        Modificar.setDisable(true);
        eliminar.setDisable(true);

        // "Admin","Usuario"

    }
    
    private final ListChangeListener<Usuario> selectorTablaUsuarios = new ListChangeListener<Usuario>(){
        @Override
        public void onChanged (ListChangeListener.Change<? extends Usuario> c){
            ponerUsuarioSeleccionado();
        } };

    //Método que devuelve el objeto de la fila seleccionada
    public Usuario getTablaUsuariosSeleccionado(){
        if (tview!=null){ List<Usuario> tabla =
                tview.getSelectionModel().getSelectedItems();
            if (tabla.size()==1) {
                final Usuario usuarioSeleccionado = tabla.get(0);
                return usuarioSeleccionado;
            } }
        return null;
    }
    //Método que a partir del objeto seleccionado lo muestra en el formulario
//También puede habilitar/deshabilitar botones en el formualrio

    static Usuario moduser;



    public void ponerUsuarioSeleccionado(){
        final Usuario usuario = getTablaUsuariosSeleccionado();
        numusuario = users.indexOf(usuario);
        if (usuario!=null){
                moduser = usuario;
                agregarUsuario.setDisable(true);
                Modificar.setDisable(false);
                eliminar.setDisable(false);
        }

    }

    @FXML
    public void Eliminar(ActionEvent actionEvent) {

        try {
            String sql = "DELETE FROM usuarios WHERE (dni = ? );";
            PreparedStatement ps = Conn.con().prepareStatement(sql);
            ps.setString(1,moduser.getDNI());
            ps.executeUpdate();
            users.clear();
            buscar();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void Modificar(ActionEvent actionEvent) throws IOException {
        stage = new Stage();
        Parent root = FXMLLoader.load(getClass().getResource("formulario.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.initStyle(StageStyle.UTILITY);
        stage.show();
        agregarUsuario.setDisable(false);
        Modificar.setDisable(true);
    }

    @FXML
    public void Regresar(ActionEvent actionEvent) {
        Principal.escenaManejoTpv();
    }


    //
    //


}
