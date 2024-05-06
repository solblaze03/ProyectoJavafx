package com.tpv;

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
    private TableColumn <Usuario,String> DNI;

    private int numusuario;
    @FXML
    private Button cargaTabla;


    @FXML
    public void buscar(){
        //System.out.println("Entra");
        tview.getSelectionModel().getSelectedItems().addListener(selectorTablaUsuarios);
        Gestiontpv gtpv = new Gestiontpv();
        try {
            Statement st = gtpv.Con().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM usuarios WHERE privilegios <> 'Superoot'");
            while (rs.next()){
                users.add(new Usuario(rs.getString("dni"),rs.getString("nombre"),rs.getString("privilegios"),rs.getString("url_imagen")));
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
            moduser=null;
            Stage stage = new Stage();
            Parent root = FXMLLoader.load(getClass().getResource("formulario.fxml"));
            Scene scene = new Scene(root);
            stage.setScene(scene);
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




        // "Admin","Usuario"
    }





    //

    //

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

    public static Usuario moduser;
    public void ponerUsuarioSeleccionado(){
        final Usuario usuario = getTablaUsuariosSeleccionado();
        numusuario = users.indexOf(usuario);
        if (usuario!=null){
            //tfnombre.setText(usuario.getNombre());

            moduser = usuario;
            try {
                Stage stage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("formulario.fxml"));
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.initStyle(StageStyle.UTILITY);
                stage.show();


            }catch (IOException e){
                System.out.println(e.getMessage());
                e.printStackTrace();
            }

        } }
    //
    //


}
