package com.tpv;

import com.tpv.clases.Categoria;
import com.tpv.clases.Conn;
import com.tpv.clases.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class Agregarcategorias implements Initializable {

    @javafx.fxml.FXML
    private TableView <Categoria> tview;
    @javafx.fxml.FXML
    private TableColumn <Categoria,String> Categoria;
    @javafx.fxml.FXML
    private TableColumn <Categoria, String>Id_categoria;
    @javafx.fxml.FXML
    private Button cargaTabla;
    @javafx.fxml.FXML
    private Button agregarCategoria;
    @javafx.fxml.FXML
    private Button Agregar;
    @javafx.fxml.FXML
    private TextField tfnombre;
    @javafx.fxml.FXML
    private ImageView Imagen;
    @javafx.fxml.FXML
    private TextField tfidcategoria;
    @javafx.fxml.FXML
    private Button addimagen;
    @javafx.fxml.FXML
    private Button modificarregistro;
    @javafx.fxml.FXML
    private Button del;
    @javafx.fxml.FXML
    private Button regresar;
    @javafx.fxml.FXML
    private Label lbcategorias;
    @javafx.fxml.FXML
    private Button btModificar;

    @javafx.fxml.FXML
    public void Eliminar(ActionEvent actionEvent) {
        try {
            System.out.println(categoriaseleccionada.getNombre());
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Eliminar registro");
            alert.setHeaderText("Eliminar registro "+categoriaseleccionada.getNombre());
            alert.setContentText("¿Estas seguro de eliminar el registro "+categoriaseleccionada.getNombre()+ "?");
            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent()){

                if(result.get() == ButtonType.OK){
                    String sql = "DELETE FROM `categoria` WHERE (`id_categoria` = ? );";
                    PreparedStatement ps = Conn.con().prepareStatement(sql);
                    ps.setString(1,categoriaseleccionada.getCategoria());
                    ps.executeUpdate();
                    lista.clear();
                    cargar();
                    Alert alert3 = new Alert(Alert.AlertType.INFORMATION);
                    alert3.setTitle("Eliminar registro");
                    alert3.setHeaderText("Se ha eliminado el registro correctamente.");
                    alert3.show();
                }else{
                    Alert alert2 = new Alert(Alert.AlertType.INFORMATION);
                    alert2.setTitle("Eliminar registro");
                    alert2.setHeaderText("No se ha eliminado el registro");
                    alert2.show();
                }
            }





        }catch (SQLException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("SQLException");
            alert.setContentText(e.getMessage());
        }
    }

    @javafx.fxml.FXML
    public void Regresar(ActionEvent actionEvent) {
        Principal.escenaManejoTpv();
    }

    private boolean modificar = false;
    @javafx.fxml.FXML
    public void Modificar(ActionEvent actionEvent) {
        if (!modificar) {
            lbcategorias.setText("Estas en modo edición.");
            lbcategorias.setStyle("-fx-text-fill: red;");
            Agregar.setVisible(false);
            modificarregistro.setVisible(true);
            agregarCategoria.setDisable(true);
            cargaTabla.setDisable(true);
            tview.setDisable(true);
            del.setDisable(true);
            modificarregistro.setDisable(false);


            try {
                String sql = "SELECT * FROM categoria WHERE id_categoria = ? ;";
                PreparedStatement ps = Conn.con().prepareStatement(sql);
                ps.setString(1,categoriaseleccionada.getCategoria());
                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    tfnombre.setText(rs.getString("nombre"));
                    tfidcategoria.setText(rs.getString("id_categoria"));
                    try {
                        System.out.println(rs.getString("urlImagen"));
                        File f = new File("src/main/resources/com/tpv/"+rs.getString("urlImagen"));
                        Image imagenmodificar = new Image(f.toURI().toString());
                        Imagen.setImage(imagenmodificar);
                    }
                    catch (NullPointerException e) {
                        Image imagendefault = new Image(getClass().getResource("Categorias/tux.jpg").toExternalForm());
                        Imagen.setImage(imagendefault);
                    }
                }

            }catch (SQLException e){
                System.out.println(e.getMessage());
            }




            Habilitar(false);
            modificar = true;
            btModificar.setText("Salir");
        }else{
            lbcategorias.setText("Presiona un a fila columna de la tabla para modificarla.");
            lbcategorias.setStyle("-fx-text-fill: black;");
            modificarregistro.setDisable(true);
            modificarregistro.setVisible(true);
            agregarCategoria.setDisable(false);
            cargaTabla.setDisable(false);
            tview.setDisable(false);
            del.setDisable(false);
            modificar = false;
            btModificar.setText("Modificar");
            Habilitar(true);
            Imagen.setImage(null);
            tfidcategoria.clear();
            tfnombre.clear();
            tview.setDisable(false);
        }
    }

    public void Habilitar(boolean n){
        addimagen.setDisable(n);
        tfidcategoria.setDisable(n);
        tfnombre.setDisable(n);
        Agregar.setDisable(n);

    }
    @javafx.fxml.FXML
    public void cargarTabla(ActionEvent actionEvent) {

            lista.clear();
            cargar();

    }

    public boolean saliragregar = false;
    @javafx.fxml.FXML
    public void Agregar(ActionEvent actionEvent) {

            System.out.println("Lista : " + tfnombre.getText() + " " + tfidcategoria.getText() + " " + file.getAbsolutePath());
            Habilitar(true);
            tview.setDisable(false);
            try {

                String sql = "INSERT INTO categoria (id_categoria,nombre,urlImagen) values(?,?,?)";
                PreparedStatement ps = Conn.con().prepareStatement(sql);
                ps.setString(1, tfidcategoria.getText());
                ps.setString(2, tfnombre.getText());
                if (file == null) {
                    ps.setString(3, null);
                } else {
                    String url = copyimage(file.getAbsolutePath());
                    ps.setString(3, "Categorias/" + url);
                }
                ps.executeUpdate();
                file = null;
                imagen1 = null;
                Imagen.setImage(null);
                tfidcategoria.setText("");
                tfnombre.setText("");
                agregarCategoria.setText("Salir");

                saliragregar = true;
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

    }


    private File file;
    String urlimagen;
    private Image imagen1;
    @javafx.fxml.FXML
    public void addImagen(ActionEvent actionEvent) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Elige una imagen");
        Stage stage = (Stage) addimagen.getScene().getWindow();
        file = fc.showOpenDialog(stage);

        try {
            if (file != null) {
                urlimagen = file.getAbsolutePath();
                imagen1 = new Image(new FileInputStream(file.getAbsolutePath()));
                Imagen.setImage(imagen1);
            }else{
                file = new File(urlimagen);
                System.out.println(file.getAbsolutePath());
                Imagen.setImage(imagen1);
            }
        }catch (FileNotFoundException e){
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
            Path destino = Paths.get("./src/main/resources/com/tpv/Categorias/"+url);
            Files.copy(origen, destino, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("copiado");
        }catch (IOException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return url;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tview.getSelectionModel().getSelectedItems().addListener(selectorTablaCategorias);
        Habilitar(true);
        modificarregistro.setVisible(false);
        cargar();
        btModificar.setDisable(true);
    }

    private ObservableList<Categoria> lista = FXCollections.observableArrayList();
    public void cargar(){
        try{

            Statement st  = Conn.con().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM categoria");
            while (rs.next()){
                lista.add(new Categoria(rs.getString("id_categoria"),rs.getString("nombre"),rs.getString("urlImagen")));
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

        Id_categoria.setCellValueFactory(new PropertyValueFactory<>("categoria"));
        Categoria.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        tview.setItems(lista);
    }

    @javafx.fxml.FXML
    public void agregarCategoria(ActionEvent actionEvent) {
        if(!saliragregar) {
            Habilitar(false);
            modificarregistro.setVisible(false);
            Agregar.setVisible(true);
            tview.setDisable(true);
            agregarCategoria.setText("Salir");
            btModificar.setDisable(true);
            cargaTabla.setDisable(true);
            lbcategorias.setText("");
            del.setDisable(true);
            saliragregar = true;
        }else {
            btModificar.setDisable(false);
            lbcategorias.setText("Presiona un a fila columna de la tabla para modificarla.");
            cargaTabla.setDisable(false);
            tview.setDisable(false);
            Imagen.setImage(null);
            tfidcategoria.setText("");
            tfnombre.setText("");
            Habilitar(true);
            del.setDisable(false);
            agregarCategoria.setText("Agregar");
            saliragregar = false;
            cargaTabla.setDisable(false);
            btModificar.setDisable(false);

            del.setDisable(false);
            saliragregar = false;
        }
    }

    // acceder registros con tableview

    private final ListChangeListener<Categoria> selectorTablaCategorias = new ListChangeListener<Categoria>(){
        @Override
        public void onChanged (ListChangeListener.Change<? extends Categoria> c){
            ponerCategoriaSeleccionado();;
        } };

    //Método que devuelve el objeto de la fila seleccionada
    public Categoria getTablaCategoriaSeleccionado(){
        if (tview!=null){ List<Categoria> tabla =
                tview.getSelectionModel().getSelectedItems();
            if (tabla.size()==1) {
                final Categoria categoriaSeleccionada = tabla.get(0);
                return categoriaSeleccionada;
            } }
        return null;
    }
    //Método que a partir del objeto seleccionado lo muestra en el formulario
//También puede habilitar/deshabilitar botones en el formualrio

    public static Usuario moduser;

    private int numcategoria;
    private Categoria categoriaseleccionada;
    public void ponerCategoriaSeleccionado(){
        final Categoria categoria = getTablaCategoriaSeleccionado();
        numcategoria = lista.indexOf(categoria);
        if (categoria!=null){
            categoriaseleccionada = categoria;
            System.out.println(categoriaseleccionada.getNombre());
            btModificar.setDisable(false);


        }
    }


    @javafx.fxml.FXML
    public void ModificarRegistro(ActionEvent actionEvent) {
        String imagenantigua = null;

        try {

            String sql1 = "SELECT * FROM categoria WHERE id_categoria = ? ;";
            PreparedStatement ps2 = Conn.con().prepareStatement(sql1);
            ps2.setString(1,categoriaseleccionada.getCategoria());
            ResultSet rs = ps2.executeQuery();
            if (rs.next()){
                imagenantigua = rs.getString("urlImagen");
            }

            String sql = "UPDATE `categoria` SET `id_categoria` = ?, `nombre` = ?, `urlImagen` = ? WHERE (`id_categoria` = ? );";
            PreparedStatement psupdate = Conn.con().prepareStatement(sql);
            psupdate.setString(1,tfidcategoria.getText());
            if(file != null) {
                String imagenseleccionada = copyimage(file.getAbsolutePath());
                psupdate.setString(3, "Categorias/"+imagenseleccionada);

            }else{
                psupdate.setString(3, imagenantigua);

            }
            psupdate.setString(2,tfnombre.getText());

            psupdate.setString(4,categoriaseleccionada.getCategoria());




            psupdate.executeUpdate();
            lista.clear();
            cargar();
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Usuario Modificado");
            alert.setHeaderText("Usuario modificado correctamente.");
            alert.setContentText("Para quitar el modo edición presione en el boton salir.");
            alert.show();
        }catch (SQLException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());

            alert.show();
        }
    }
}
