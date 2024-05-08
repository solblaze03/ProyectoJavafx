package com.tpv;

import com.tpv.clases.Categoria;
import com.tpv.clases.Gestiontpv;
import com.tpv.clases.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

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
import java.util.ResourceBundle;

public class Agregarcategorias implements Initializable {

    @javafx.fxml.FXML
    private TableView <Categoria> tview;
    @javafx.fxml.FXML
    private TableColumn <Categoria,String> Categoria;
    @javafx.fxml.FXML
    private Button Modificar;
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
    public void Eliminar(ActionEvent actionEvent) {

    }

    @javafx.fxml.FXML
    public void Regresar(ActionEvent actionEvent) {
        Principal.escenaManejoTpv();
    }

    @javafx.fxml.FXML
    public void Modificar(ActionEvent actionEvent) {
        Agregar.setVisible(false);
        modificarregistro.setVisible(true);
        agregarCategoria.setDisable(true);
        cargaTabla.setDisable(true);
        del.setDisable(true);
        regresar.setDisable(true);
        tfnombre.setText(categoriaseleccionada.getNombre());
        tfidcategoria.setText(categoriaseleccionada.getCategoria());
        Image imagenmodificar = new Image(getClass().getResource(categoriaseleccionada.getUrl_imagen()).toExternalForm());
        Imagen.setImage(imagenmodificar);
        Habilitar(false);

    }

    public void Habilitar(boolean n){
        addimagen.setDisable(n);
        tfidcategoria.setDisable(n);
        tfnombre.setDisable(n);
        Agregar.setDisable(n);

    }
    @javafx.fxml.FXML
    public void cargarTabla(ActionEvent actionEvent) {
        try {
            Gestiontpv tpv = new Gestiontpv();
            Statement st = tpv.Con().createStatement();

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @javafx.fxml.FXML
    public void Agregar(ActionEvent actionEvent) {
        System.out.println("Lista : "+tfnombre.getText() +" "+ tfidcategoria.getText()+" "+file.getAbsolutePath());
        Habilitar(true);
        tview.setDisable(false);
        try{
            Gestiontpv tpv = new Gestiontpv();
            String sql = "INSERT INTO categoria (id_categoria,nombre,urlImagen) values(?,?,?)";
            PreparedStatement ps = tpv.Con().prepareStatement(sql);
            ps.setString(1,tfidcategoria.getText());
            ps.setString(2,tfnombre.getText());
            if (file == null) {
                ps.setString(3,null);
            }else {
                String url = copyimage(file.getAbsolutePath());
                ps.setString(3,"Categorias/"+url);
            }
            ps.executeUpdate();
            file = null;
            imagen1 = null;
            Imagen.setImage(null);
            tfidcategoria.setText("");
            tfnombre.setText("");
        }catch (SQLException e){
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
    }

    private ObservableList<Categoria> lista = FXCollections.observableArrayList();
    public void cargar(){
        try{
            Gestiontpv tpv = new Gestiontpv();
            Statement st  = tpv.Con().createStatement();
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
        Habilitar(false);
        tview.setDisable(true);
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

        }

    }


    @javafx.fxml.FXML
    public void ModificarRegistro(ActionEvent actionEvent) {

    }
}
