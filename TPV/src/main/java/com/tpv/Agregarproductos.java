package com.tpv;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageConfig;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.tpv.clases.Conn;
import com.tpv.clases.Productos;
import com.tpv.excepciones.NegativeNumberException;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class Agregarproductos implements Initializable {

    @javafx.fxml.FXML
    private TableView <Productos> tview;
    @javafx.fxml.FXML
    private TableColumn <Productos,Integer> iva;
    @javafx.fxml.FXML
    private TableColumn <Productos,Integer> descuento;
    @javafx.fxml.FXML
    private TableColumn <Productos,String> categoria;
    @javafx.fxml.FXML
    private Button Modificar;
    @javafx.fxml.FXML
    private Button cargaTabla;
    @javafx.fxml.FXML
    private TableColumn <Productos,String>nombre;
    @javafx.fxml.FXML
    private TextField tfnombres;
    @javafx.fxml.FXML
    private TextField tfbarras;
    @javafx.fxml.FXML
    private TextField tfdcto;
    @javafx.fxml.FXML
    private ComboBox <String> cbiva;
    @javafx.fxml.FXML
    private ComboBox <String> cbcategoria;
    @javafx.fxml.FXML
    private ImageView image;
    @javafx.fxml.FXML
    private Button agregar;
    @javafx.fxml.FXML
    private Button eliminar;
    @javafx.fxml.FXML
    private TableColumn QR;
    @javafx.fxml.FXML
    private TableColumn identicadorqr;
    @javafx.fxml.FXML
    private AnchorPane pane;
    @javafx.fxml.FXML
    private Button Modificarabajo;
    @javafx.fxml.FXML
    private Button agregarinicio;
    @javafx.fxml.FXML
    private TextField tfprecio;
    @javafx.fxml.FXML
    private TableColumn cprecio;

    @javafx.fxml.FXML
    public void Eliminar(ActionEvent actionEvent) {
        Alert alertdel = new Alert(Alert.AlertType.CONFIRMATION);
        alertdel.setTitle("Eliminar registro");
        alertdel.setHeaderText("Estas seguro de eliminar este registro");
        Optional<ButtonType> result = alertdel.showAndWait();
        if (result.isPresent()){
            if (result.get() == ButtonType.OK){
                try {
                    String sql = "DELETE FROM `productos` WHERE (`codigo_barras` = ?);";
                    PreparedStatement ps = Conn.con().prepareStatement(sql);
                    ps.setString(1,productoseleccionado.getCodigosbarra());
                    ps.executeUpdate();
                    File f = new File("src/main/resources/com/tpv/"+productoseleccionado.getUrlbarras());
                    boolean borrado = f.delete();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Eliminar registro");
                    alert.setHeaderText("Registro eliminado correctamente");
                    alert.show();
                    list.clear();
                    cargar();
                }catch (SQLException e){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("SQLException");
                    alert.setHeaderText(e.getMessage());
                    alert.show();
                }
            }
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
            File f = new File("src/main/resources/com/tpv/"+productoseleccionado.getUrlbarras());
            Image imagen = new Image(f.toURI().toString());
            image.setImage(imagen);
            agregar.setVisible(false);
            Modificarabajo.setVisible(true);
            agregarinicio.setDisable(true);
            cargaTabla.setDisable(true);
            tview.setDisable(true);
            eliminar.setDisable(true);
            modificar = true;
            Modificar.setText("Salir");
            deshabilitar(false);
            tfbarras.setText(productoseleccionado.getCodigosbarra());
            tfprecio.setText(productoseleccionado.getPrecio()+"");
            tfdcto.setText(productoseleccionado.getDcto()+"");
            int iva;
            if (productoseleccionado.getIva() == 21){
                iva = 0;
            }else if(productoseleccionado.getIva() == 10){
                iva = 1;
            } else {
                iva = 2;
            }
            cbiva.getSelectionModel().select(iva);
            tfnombres.setText(productoseleccionado.getNombre());
        }else{
            Modificar.setText("Modificar");
            deshabilitar(true);
            agregar.setVisible(true);
            Modificarabajo.setVisible(false);
            agregarinicio.setDisable(false);
            cargaTabla.setDisable(false);
            tview.setDisable(false);
            eliminar.setDisable(false);
            image.setImage(null);
            tfnombres.clear();
            tfdcto.clear();
            tfprecio.clear();
            tfbarras.clear();
            cbiva.getSelectionModel().clearSelection();
            cbcategoria.getSelectionModel().clearSelection();



            modificar = false;
        }
    }

    private boolean saliragregar =false;
    @javafx.fxml.FXML
    public void agregarUsuario(ActionEvent actionEvent) {
        deshabilitar(false);
        if(!saliragregar) {
            tview.setDisable(true);
            agregarinicio.setText("Salir");
            Modificar.setDisable(true);
            cargaTabla.setDisable(true);

            eliminar.setDisable(true);
            saliragregar = true;
        }else {
            agregarinicio.setText("Agregar");
            Modificar.setDisable(false);

            cargaTabla.setDisable(false);
            tview.setDisable(false);
            image.setImage(null);
            tfnombres.setText("");
            tfdcto.setText("");
            tfbarras.setText("");
            cbcategoria.getSelectionModel().clearSelection();
            cbiva.getSelectionModel().clearSelection();
            deshabilitar(true);

            saliragregar = false;
            cargaTabla.setDisable(false);
            if (productoseleccionado == null){
                eliminar.setDisable(true);
                Modificar.setDisable(true);
            }else {
                eliminar.setDisable(false);
                Modificar.setDisable(false);
            }

            saliragregar = false;
        }
    }
    @javafx.fxml.FXML
    public void cargarTabla(ActionEvent actionEvent) {
        list.clear();
        cargar();
    }

    private File file;


    @javafx.fxml.FXML
    public void Agregar(ActionEvent actionEvent) {
        if (tfbarras.getText() != "" && tfnombres.getText() != "" && tfdcto.getText() != "" && cbiva.getValue() != null && tfprecio.getText() != "") {
            try {

                String sql = "INSERT INTO `productos` (`codigo_barras`, `nombre`, `id_categoria`, `url_codigobarras`, `iva`, `descuento` , `precio`) VALUES (?, ?, ?, ?, ?, ?,?);";
                String sqlselect = "SElECT * FROM categoria WHERE nombre = ? ;";
                PreparedStatement psselect = Conn.con().prepareStatement(sqlselect);
                psselect.setString(1, cbcategoria.getValue());
                ResultSet rs = psselect.executeQuery();
                String idcategoria = null;
                if (rs.next()) {
                    idcategoria = rs.getString("id_categoria");
                }

                PreparedStatement ps = Conn.con().prepareStatement(sql);


                ps.setString(1, tfbarras.getText());
                ps.setString(2, tfnombres.getText());
                ps.setString(3, idcategoria);

                ps.setString(4, "codeqr/" + tfbarras.getText() + ".png");
                String timpositivo []= cbiva.getValue().split("%");
                int iva = Integer.parseInt(timpositivo[0]);
                ps.setInt(5,  iva);

                int number = Integer.parseInt((String) tfdcto.getText());
                numeronegativo(number);
                ps.setInt(6,number);
                double num = Double.parseDouble(tfprecio.getText());
                numeronegativo(num);
                ps.setDouble(7,num);

                ps.executeUpdate();

                crearQR();

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Registro");
                alert.setHeaderText("Producto "+tfnombres.getText()+" registrado");
                ImageView imageview = new ImageView();
                File f = new File("src/main/resources/com/tpv/codeqr/"+tfbarras.getText()+".png");
                Image imagen = new Image(f.toURI().toString());
                image.setImage(imagen);
                imageview.setImage(imagen);
                ButtonType descargarqr = new ButtonType("Guardar QR");
                ButtonType salir = new ButtonType("salir");
                alert.getButtonTypes().setAll(descargarqr,salir);
                alert.setGraphic(imageview);
                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent()){
                    if (result.get() == descargarqr){
                        DirectoryChooser dchooser = new DirectoryChooser();
                        dchooser.setTitle("Selecciona una carpeta");
                        Stage stage = (Stage) agregar.getScene().getWindow();
                        File file = dchooser.showDialog(stage);
                        if (file != null){
                            try {
                                String barras = tfbarras.getText();
                                String url =  file.getAbsolutePath()+"/"+tfbarras.getText()+".png" ;

                                int ancho = 600;
                                int alto = 600;
                                QRCodeWriter cw = new QRCodeWriter();
                                BitMatrix bitmatrix = cw.encode(barras, BarcodeFormat.QR_CODE, ancho, alto);
                                MatrixToImageConfig config = new MatrixToImageConfig(Color.white.getRGB(),Color.decode("#1f1f1f").getRGB());
                                MatrixToImageWriter.writeToPath(bitmatrix, "png", Paths.get(url),config);
                                Alert alertok = new Alert(Alert.AlertType.INFORMATION);
                                alertok.setTitle("QR no guardado");
                                alertok.setHeaderText("QR guardado correctamente");
                                alertok.setGraphic(imageview);
                                alertok.show();
                                tfnombres.setText("");
                                tfdcto.setText("");
                                tfbarras.setText("");
                                cbcategoria.getSelectionModel().clearSelection();
                                cbiva.getSelectionModel().clearSelection();




                            }catch (IOException | WriterException e){

                            }
                        }else{
                            Alert alertno = new Alert(Alert.AlertType.INFORMATION);
                            alertno.setTitle("QR guardado");
                            alertno.setHeaderText("QR no se ha guardado correctamente");
                            alertno.show();
                        }
                    }else{
                        Alert alertno = new Alert(Alert.AlertType.INFORMATION);
                        alertno.setTitle("QR no guardado");
                        alertno.setHeaderText("QR no se ha guardado correctamente");
                        alertno.show();
                    }


                }
                list.clear();
                cargar();


            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("SQLException");
                alert.setHeaderText(e.getMessage());
                alert.show();
            }catch (NumberFormatException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error en la entrada de datos");
                alert.setHeaderText("No has introducido un numero en el campo descuento o precio");
                alert.show();
            }catch (NegativeNumberException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("NegativeNumberException");
                alert.setHeaderText("En descuento o precio has colocado un numero negativo");
                alert.show();
            }

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Hay campos vacios");
            alert.show();
        }
    }
    public void crearQR(){
        try {
            String barras = tfbarras.getText();
            String url = "src/main/resources/com/tpv/codeqr/"+tfbarras.getText()+".png";
            int ancho = 150;
            int alto = 150;
            QRCodeWriter cw = new QRCodeWriter();
            BitMatrix bitmatrix = cw.encode(barras, BarcodeFormat.QR_CODE, ancho, alto);
            MatrixToImageConfig config = new MatrixToImageConfig(Color.white.getRGB(),Color.decode("#1f1f1f").getRGB());
            MatrixToImageWriter.writeToPath(bitmatrix, "png", Paths.get(url),config);

        }catch (IOException e){
            System.out.println(e.getMessage());
        } catch (WriterException e) {
            throw new RuntimeException(e);
        }
    }

    public void numeronegativo(int num) throws NegativeNumberException {
        if( num < 0){
            throw new NegativeNumberException("El numero es negativo");
        }
    }
    public void numeronegativo(double num) throws NegativeNumberException {
        if( num < 0){
            throw new NegativeNumberException("El numero es negativo");
        }
    }
    public void deshabilitar(boolean n){
        agregar.setDisable(n);
        tfbarras.setDisable(n);
        tfdcto.setDisable(n);
        tfnombres.setDisable(n);
        cbiva.setDisable(n);
        cbcategoria.setDisable(n);
        tfprecio.setDisable(n);

    }
    ObservableList list = FXCollections.observableArrayList();
    public void cargar(){
        try {
            Statement st = Conn.con().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM productos left join categoria on productos.id_categoria = categoria.id_categoria;");
            while (rs.next()){

                String categoria = null;
                if(rs.getString("categoria.nombre") == null){
                    categoria = "Sin categoria";
                }else{
                    categoria = rs.getString("categoria.nombre");
                }

                list.add(new Productos(rs.getString("codigo_barras"),rs.getString("productos.nombre"),categoria,rs.getString("url_codigobarras"),rs.getInt("iva"),rs.getInt("descuento"),rs.getDouble("precio")));
            }
            identicadorqr.setCellValueFactory(new PropertyValueFactory<>("codigosbarra"));
            nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            categoria.setCellValueFactory(new PropertyValueFactory<>("id_categoria"));
            iva.setCellValueFactory(new PropertyValueFactory<>("iva"));
            descuento.setCellValueFactory(new PropertyValueFactory<>("dcto"));
            QR.setCellValueFactory(new PropertyValueFactory<>("urlbarras"));
            QR.setCellFactory(column -> {
                TableCell<Productos, String> cell = new TableCell<>() {
                    private final ImageView imageView = new ImageView();

                    {
                        imageView.setFitWidth(60);
                        imageView.setFitHeight(60);
                    }

                    @Override
                    protected void updateItem(String imagePath, boolean empty) {
                        super.updateItem(imagePath, empty);

                        if (empty || imagePath == null) {
                            setGraphic(null);
                        } else {
                            File fimage = new File("src/main/resources/com/tpv/"+imagePath);

                            Image image = new Image(fimage.toURI().toString());
                            imageView.setImage(image);
                            setGraphic(imageView);
                        }
                    }
                };
                return cell;
            });
            cprecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
            tview.setItems(list);

        }catch (SQLException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("SQLException");
            alert.setHeaderText(e.getMessage());
            alert.show();
        }

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pane.getStylesheets().add(getClass().getResource("css/escenaproductos.css").toExternalForm());
        cargar();
        eliminar.setDisable(true);
        Modificarabajo.setVisible(false);
        Modificar.setDisable(true);
        tview.getSelectionModel().getSelectedItems().addListener(selectorTablaProductos);
        deshabilitar(true);
        ObservableList<String> lista = FXCollections.observableArrayList();
        try {
            Statement st = Conn.con().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM categoria;");

            while (rs.next()){
                lista.add(rs.getString("nombre"));
            }

        }catch (SQLException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("SQLException");
            alert.setHeaderText(e.getMessage());
            alert.show();
        }
        cbcategoria.getItems().setAll(lista);
        cbiva.getItems().addAll("21% - General","10% - Reducido","4% - Superreducido");

    }


    // Selección registros.
    private final ListChangeListener<Productos> selectorTablaProductos = new ListChangeListener<Productos>(){
        @Override
        public void onChanged (ListChangeListener.Change<? extends Productos> c){
            ponerUsuarioSeleccionado();
        } };

    //Método que devuelve el objeto de la fila seleccionada
    public Productos getTablaProductoSeleccionado(){
        if (tview!=null){ List<Productos> tabla =
                tview.getSelectionModel().getSelectedItems();
            if (tabla.size()==1) {
                final Productos productoseleccionado = tabla.get(0);
                return productoseleccionado;
            } }
        return null;
    }


    private Productos productoseleccionado;
    private int numproducto;
    public void ponerUsuarioSeleccionado(){
        final Productos producto = getTablaProductoSeleccionado();
        numproducto = list.indexOf(producto);
        if (producto !=null){
            productoseleccionado = producto;

            Modificar.setDisable(false);
            eliminar.setDisable(false);
        }
    }
    @javafx.fxml.FXML
    public void okmodi(ActionEvent actionEvent) {
        if (tfbarras.getText() != "" && tfnombres.getText() != "" && tfdcto.getText() != "" && cbiva.getValue() != null && tfprecio.getText() != "") {
            try {
                String sql = "UPDATE `productos` SET `nombre` = ?, `id_categoria` = ?, `url_codigobarras` = ?, `iva` = ?, `descuento` = ?, `precio` = ?, codigo_barras = ? WHERE (`codigo_barras` = ?);";
                PreparedStatement ps = Conn.con().prepareStatement(sql);
                String sql1 = "SELECT * FROM categoria where nombre = ? ;";

                PreparedStatement pscat = Conn.con().prepareStatement(sql1);
                pscat.setString(1,cbcategoria.getValue());

                ResultSet rs = pscat.executeQuery();
                Integer categoria= 0;
                if (rs.next()){
                    categoria = rs.getInt("id_categoria");
                }else{
                    categoria = null;
                }



                ps.setString(1, tfnombres.getText());
                if(categoria == null){
                    ps.setString(2,null);
                }else {
                    ps.setInt(2, categoria);
                }




                String barras = tfbarras.getText();
                String url = "src/main/resources/com/tpv/codeqr/"+ tfbarras.getText() + ".png";

                int ancho = 600;
                int alto = 600;
                QRCodeWriter cw = new QRCodeWriter();
                BitMatrix bitmatrix = cw.encode(barras, BarcodeFormat.QR_CODE, ancho, alto);
                MatrixToImageConfig config = new MatrixToImageConfig(Color.white.getRGB(), Color.decode("#1f1f1f").getRGB());
                MatrixToImageWriter.writeToPath(bitmatrix, "png", Paths.get(url), config);


                ps.setString(3, "codeqr/" + tfbarras.getText() + ".png");
                String iva[] = cbiva.getValue().split("%");
                ps.setInt(4,Integer.parseInt(iva[0]));
                numeronegativo(Double.parseDouble(tfprecio.getText()));
                numeronegativo(Integer.parseInt(tfdcto.getText()));
                ps.setInt(5,Integer.parseInt(tfdcto.getText()));
                ps.setDouble(6,Double.parseDouble(tfprecio.getText()));

                ps.setString(7,tfbarras.getText());
                ps.setString(8,productoseleccionado.getCodigosbarra());




                int n = ps.executeUpdate();
                productoseleccionado.setCodigosbarra(tfbarras.getText());



                File f = new File("src/main/resources/com/tpv/codeqr/"+ tfbarras.getText() + ".png");
                Image imageqr = new Image(f.toURI().toString());
                image.setImage(imageqr);
                File fdel = new File("src/main/resources/com/tpv/"+productoseleccionado.getUrlbarras());
                if (!f.getAbsolutePath().equals(fdel.getAbsolutePath())){
                    fdel.delete();
                }
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Modificar registro");
                alert.setHeaderText("Registro modificado correctamente");
                alert.show();
                list.clear();
                cargar();
            } catch (SQLException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("SQLException");
                alert.setHeaderText(e.getMessage());
                alert.show();
            } catch (IOException | WriterException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("IOException , WriterException");
                alert.setContentText(e.getMessage());
                alert.show();
            }catch (NegativeNumberException e){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Numero negativo");
                alert.setHeaderText("Haz introducido un numero negativo en alguno de los campos.");
                alert.show();
            }
        }
    }
}