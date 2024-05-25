package com.tpv;

import com.tpv.clases.Conn;
import com.tpv.clases.facturas;
import com.tpv.excepciones.NegativeNumberException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.PropertyPermission;
import java.util.ResourceBundle;

public class Factura implements Initializable {

    @javafx.fxml.FXML
    private TableView tview;
    @javafx.fxml.FXML
    private TableColumn precio;
    @javafx.fxml.FXML
    private TableColumn total;
    @javafx.fxml.FXML
    private TableColumn descuento;
    @javafx.fxml.FXML
    private TableColumn atendido;
    @javafx.fxml.FXML
    private TextField tfactura;
    @javafx.fxml.FXML
    private TableColumn producto;
    @javafx.fxml.FXML
    private TableColumn cantidad;
    @javafx.fxml.FXML
    private AnchorPane pane;
    @javafx.fxml.FXML
    private TableColumn Tipopago;
    @javafx.fxml.FXML
    private Label totalfactura;
    private ObservableList <facturas> facturas = FXCollections.observableArrayList();
    @javafx.fxml.FXML
    private TableColumn fecha;

    @javafx.fxml.FXML
    public void buscar(ActionEvent actionEvent) {
        try{
            numeronegativo(Integer.parseInt(tfactura.getText()));
            int num = Integer.parseInt(tfactura.getText());
            facturas.clear();
            String sql = "select *, (select sum(total) from factura join detallefactura using (idfactura) where idfactura = ?) as suma from factura join detallefactura using (idfactura) where idfactura = ?;";
            PreparedStatement ps = Conn.con().prepareStatement(sql);
            ps.setInt(1,num);
            ps.setInt(2,num);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){
                totalfactura.setText(rs.getDouble("suma")+"€");
                facturas.add(new facturas(rs.getString("nombre_usuario"),rs.getString("tipo_pago"),rs.getTimestamp("hora_compra"),rs.getString("producto"),rs.getInt("cantidad"),rs.getDouble("precio"),rs.getInt("descuento"),rs.getDouble("total")));
            }

            atendido.setCellValueFactory(new PropertyValueFactory<>("trabajador"));
            Tipopago.setCellValueFactory(new PropertyValueFactory<>("tipopago"));
            fecha.setCellValueFactory(new PropertyValueFactory<>("horacompra"));
            producto.setCellValueFactory(new PropertyValueFactory<>("producto"));
            cantidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
            precio.setCellValueFactory(new PropertyValueFactory<>("precio"));
            descuento.setCellValueFactory(new PropertyValueFactory<>("descuento"));
            total.setCellValueFactory(new PropertyValueFactory<>("total"));

            tview.setItems(facturas);




        }catch (NegativeNumberException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(e.getMessage());
            alert.show();
        }catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Has introducido un numero o un caracter no valido");
            alert.show();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }

    }
    public void numeronegativo(int num) throws NegativeNumberException {
        if (num < 0){
            throw  new NegativeNumberException("Has introducido un numero negativo");
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pane.getStylesheets().add(getClass().getResource("css/escenaproductos.css").toExternalForm());

        
    }
}
