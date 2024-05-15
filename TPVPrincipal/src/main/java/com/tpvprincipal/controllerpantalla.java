package com.tpvprincipal;

import com.tpvprincipal.clases.Conn;
import com.tpvprincipal.clases.Productos;
import com.tpvprincipal.clases.Usuario;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;

public class controllerpantalla implements Initializable {


    @FXML
    private TableView <Productos>tviewgrande;
    @FXML
    private TableColumn <Productos,String> codigosbarra;
    @FXML
    private TableColumn <Productos,String> producto;
    @FXML
    private TableColumn <Productos,Double> precio;
    @FXML
    private TableColumn <Productos,Integer> iva;
    @FXML
    private TableColumn <Productos,Integer> descuento;
    @FXML
    private TableColumn <Productos,String> categoria;
    private ObservableList <Productos> list = FXCollections.observableArrayList();
    @FXML
    private Button clear;
    @FXML
    private Label nombre;
    @FXML
    private AnchorPane panel;
    @FXML
    private TableColumn tunidad;
    @FXML
    private TableColumn ttotal;
    @FXML
    private TableColumn tprecio;
    @FXML
    private TableColumn tproducto;
    @FXML
    private TableView tviewpequeño;
    @FXML
    private Label totalcajero;


    public void cargartabla(String codigo){
        try {
            String sql = "SELECT * FROM productos left join categoria on productos.id_categoria = categoria.id_categoria where codigo_barras = ?";
            PreparedStatement ps = Conn.con().prepareStatement(sql);
            ps.setString(1,codigo);
            ResultSet rs = ps.executeQuery();
            while (rs.next()){

                String categoria = null;
                if(rs.getString("categoria.nombre") == null){
                    categoria = "Sin categoria";
                }else{
                    categoria = rs.getString("categoria.nombre");
                }

                list.add(new Productos(rs.getString("codigo_barras"),rs.getString("productos.nombre"),categoria,rs.getString("url_codigobarras"),rs.getInt("iva"),rs.getInt("descuento"),rs.getDouble("precio"),rs.getDouble("precio"),1));
            }
            codigosbarra.setCellValueFactory(new PropertyValueFactory<>("codigosbarra"));
            producto.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            categoria.setCellValueFactory(new PropertyValueFactory<>("id_categoria"));
            iva.setCellValueFactory(new PropertyValueFactory<>("iva"));
            descuento.setCellValueFactory(new PropertyValueFactory<>("dcto"));


            precio.setCellValueFactory(new PropertyValueFactory<>("precio"));
            tviewgrande.setItems(list);

        }catch (SQLException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("SQLException");
            alert.setHeaderText(e.getMessage());
            alert.show();
        }
    }

    private ObservableList<Productos> list2 = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Usuario usuario = Controller.user();
        nombre.setText(usuario.getNombre());
        panel.getStylesheets().add(getClass().getResource("css/tablas.css").toExternalForm());


        // para obtener el registro con doble click
        tviewgrande.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Productos productoss = tviewgrande.getSelectionModel().getSelectedItem();

                if (productoss != null) {
                    System.out.println(productoss.getCantidad());
                    agregarTviewPequeño(productoss);
                }
            }
        });


    }


    double totalfactura  = 0;
    public void agregarTviewPequeño(Productos producto){
        int numprod = 0;
        boolean esta = false;
        int numero = 0;
        for (int i = 0; i < list2.size(); i++) {
            if (list2.get(i).getCodigosbarra().equals(producto.getCodigosbarra())){

                numero = i;
                esta = true;
            }
        }

        if(!esta) {
            numprod = list2.indexOf(producto);

            nombre.setText(numero+"");
            list2.add(producto);
            tunidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
            tproducto.setCellValueFactory(new PropertyValueFactory<>("nombre"));
            tprecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
            ttotal.setCellValueFactory(new PropertyValueFactory<>("total"));
            totalfactura += producto.getPrecio();
            tviewpequeño.setItems(list2);
            System.out.println("no Esta el producto");
        }else{


            System.out.println("Esta el producto");
            list2.get(numero).setCantidad(list2.get(numero).getCantidad()+1);
            list2.get(numero).setTotal(producto.getPrecio());


            list2.set(numero,list2.get(numero));
            totalfactura += producto.getPrecio();
        }
        totalcajero.setText(totalfactura+"");

    }








    /*
    *   Botones
    *   del
    *   teclado
     */


    @javafx.fxml.FXML
    private Button t4;
    @javafx.fxml.FXML
    private Button t5;
    @javafx.fxml.FXML
    private Button tDel;
    @javafx.fxml.FXML
    private Button t6;
    @javafx.fxml.FXML
    private Button t7;
    @javafx.fxml.FXML
    private Button t8;
    @javafx.fxml.FXML
    private Button t9;
    @javafx.fxml.FXML
    private Button tA;
    @javafx.fxml.FXML
    private Button tB;
    @javafx.fxml.FXML
    private Button tC;
    @javafx.fxml.FXML
    private Button tD;
    @javafx.fxml.FXML
    private Button tE;
    @javafx.fxml.FXML
    private Button tF;
    @javafx.fxml.FXML
    private Button tG;
    @javafx.fxml.FXML
    private Button tH;
    @javafx.fxml.FXML
    private Button tI;
    @javafx.fxml.FXML
    private Button tand;
    @javafx.fxml.FXML
    private Button tJ;
    @javafx.fxml.FXML
    private Button tK;
    @javafx.fxml.FXML
    private Button tdospuntos;
    @javafx.fxml.FXML
    private Button tL;
    @javafx.fxml.FXML
    private Button tM;
    @javafx.fxml.FXML
    private Button tN;
    @javafx.fxml.FXML
    private Button tO;
    @javafx.fxml.FXML
    private Button tP;
    @javafx.fxml.FXML
    private Button tQ;
    @javafx.fxml.FXML
    private Button tÑ;
    @javafx.fxml.FXML
    private Button tR;
    @javafx.fxml.FXML
    private Button tS;
    @javafx.fxml.FXML
    private Button Tt;
    @javafx.fxml.FXML
    private Button tU;
    @javafx.fxml.FXML
    private Button tV;
    @javafx.fxml.FXML
    private Button tW;
    @javafx.fxml.FXML
    private Button tX;
    @javafx.fxml.FXML
    private Button tmenor;
    @javafx.fxml.FXML
    private Button tY;
    @javafx.fxml.FXML
    private Button tZ;
    @javafx.fxml.FXML
    private Button tmayor;
    @javafx.fxml.FXML
    private Button tpcoma;
    @javafx.fxml.FXML
    private Button t0;
    @javafx.fxml.FXML
    private Button t1;
    @javafx.fxml.FXML
    private Button t2;
    @javafx.fxml.FXML
    private Button t3;

    private String cadena = "";
    @javafx.fxml.FXML
    private TextField tfbuscar;
    private boolean mayuscula = true;
    @javafx.fxml.FXML
    private Button tmayus;

    public void setCaracter(String car){

        cadena=tfbuscar.getText();

        if(mayuscula) {
            car = car.toUpperCase();
            cadena += car;

            tfbuscar.setText(cadena);
        }else{
            car = car.toLowerCase();
            cadena += car;
            tfbuscar.setText(cadena);
        }

    }

    @javafx.fxml.FXML
    public void t4(ActionEvent actionEvent) {
        setCaracter("4");
        list.clear();
        cargartabla(tfbuscar.getText());
    }

    @javafx.fxml.FXML
    public void t5(ActionEvent actionEvent) {
        setCaracter("5");
        list.clear();
        cargartabla(tfbuscar.getText());
    }

    @javafx.fxml.FXML
    public void t6(ActionEvent actionEvent) {
        setCaracter("6");
        list.clear();
        cargartabla(tfbuscar.getText());
    }

    @javafx.fxml.FXML
    public void tDel(ActionEvent actionEvent) {
        cadena=tfbuscar.getText();
        if(cadena.length() != 0){
            cadena = cadena.substring(0,cadena.length()-1);
            tfbuscar.setText(cadena);
        }
        list.clear();
        cargartabla(tfbuscar.getText());
    }

    @javafx.fxml.FXML
    public void t7(ActionEvent actionEvent) {

        setCaracter("7");
        list.clear();
        cargartabla(tfbuscar.getText());
    }

    @javafx.fxml.FXML
    public void t8(ActionEvent actionEvent) {
        setCaracter("8");
        list.clear();
        cargartabla(tfbuscar.getText());
    }

    @javafx.fxml.FXML
    public void t9(ActionEvent actionEvent) {
        setCaracter("9");
        list.clear();
        cargartabla(tfbuscar.getText());
    }

    @javafx.fxml.FXML
    public void tA(ActionEvent actionEvent) {
        setCaracter("A");
        list.clear();
        cargartabla(tfbuscar.getText());
    }

    @javafx.fxml.FXML
    public void tB(ActionEvent actionEvent) {
        setCaracter("B");
        list.clear();
        cargartabla(tfbuscar.getText());
    }

    @javafx.fxml.FXML
    public void tC(ActionEvent actionEvent) {
        setCaracter("C");
        list.clear();
        cargartabla(tfbuscar.getText());
    }

    @javafx.fxml.FXML
    public void tD(ActionEvent actionEvent) {
        setCaracter("D");
        list.clear();
        cargartabla(tfbuscar.getText());
    }

    @javafx.fxml.FXML
    public void tE(ActionEvent actionEvent) {
        setCaracter("E");
        list.clear();
        cargartabla(tfbuscar.getText());
    }

    @javafx.fxml.FXML
    public void tF(ActionEvent actionEvent) {
        setCaracter("F");
        list.clear();
        cargartabla(tfbuscar.getText());
    }

    @javafx.fxml.FXML
    public void tG(ActionEvent actionEvent) {
        setCaracter("G");
        list.clear();
        cargartabla(tfbuscar.getText());
    }

    @javafx.fxml.FXML
    public void tH(ActionEvent actionEvent) {
        setCaracter("H");
        list.clear();
        cargartabla(tfbuscar.getText());
    }

    @javafx.fxml.FXML
    public void tand(ActionEvent actionEvent) {
        setCaracter("&");
        list.clear();
        cargartabla(tfbuscar.getText());
    }

    @javafx.fxml.FXML
    public void tI(ActionEvent actionEvent) {
        setCaracter("I");
        list.clear();
        cargartabla(tfbuscar.getText());
    }

    @javafx.fxml.FXML
    public void tJ(ActionEvent actionEvent) {
        setCaracter("J");
        list.clear();
        cargartabla(tfbuscar.getText());
    }

    @javafx.fxml.FXML
    public void tK(ActionEvent actionEvent) {
        setCaracter("K");
        list.clear();
        cargartabla(tfbuscar.getText());
    }

    @javafx.fxml.FXML
    public void tL(ActionEvent actionEvent) {
        setCaracter("L");
        list.clear();
        cargartabla(tfbuscar.getText());
    }

    @javafx.fxml.FXML
    public void tM(ActionEvent actionEvent) {
        setCaracter("M");
        list.clear();
        cargartabla(tfbuscar.getText());
    }

    @javafx.fxml.FXML
    public void tN(ActionEvent actionEvent) {
        setCaracter("N");
        list.clear();
        cargartabla(tfbuscar.getText());
    }

    @javafx.fxml.FXML
    public void tO(ActionEvent actionEvent) {
        setCaracter("O");
        list.clear();
        cargartabla(tfbuscar.getText());
    }

    @javafx.fxml.FXML
    public void T0(ActionEvent actionEvent) {
        setCaracter("0");
        list.clear();
        cargartabla(tfbuscar.getText());
    }

    @javafx.fxml.FXML
    public void tP(ActionEvent actionEvent) {
        setCaracter("P");
        list.clear();
        cargartabla(tfbuscar.getText());
    }

    @javafx.fxml.FXML
    public void tÑ(ActionEvent actionEvent) {
        setCaracter("Ñ");
        list.clear();
        cargartabla(tfbuscar.getText());
    }

    @javafx.fxml.FXML
    public void tQ(ActionEvent actionEvent) {
        setCaracter("Q");
        list.clear();
        cargartabla(tfbuscar.getText());
    }

    @javafx.fxml.FXML
    public void tR(ActionEvent actionEvent) {
        setCaracter("R");
        list.clear();
        cargartabla(tfbuscar.getText());
    }

    @javafx.fxml.FXML
    public void tS(ActionEvent actionEvent) {
        setCaracter("S");
        list.clear();
        cargartabla(tfbuscar.getText());
    }

    @javafx.fxml.FXML
    public void Tt(ActionEvent actionEvent) {
        setCaracter("T");
        list.clear();
        cargartabla(tfbuscar.getText());
    }

    @javafx.fxml.FXML
    public void tU(ActionEvent actionEvent) {
        setCaracter("U");
        list.clear();
        cargartabla(tfbuscar.getText());
    }

    @javafx.fxml.FXML
    public void tV(ActionEvent actionEvent) {
        setCaracter("V");
        list.clear();
        cargartabla(tfbuscar.getText());
    }

    @javafx.fxml.FXML
    public void tW(ActionEvent actionEvent) {
        setCaracter("W");
        list.clear();
        cargartabla(tfbuscar.getText());
    }

    @javafx.fxml.FXML
    public void tmenor(ActionEvent actionEvent) {
        setCaracter("<");
        list.clear();
        cargartabla(tfbuscar.getText());
    }

    @javafx.fxml.FXML
    public void tX(ActionEvent actionEvent) {
        setCaracter("X");
        list.clear();
        cargartabla(tfbuscar.getText());
    }

    @javafx.fxml.FXML
    public void tY(ActionEvent actionEvent) {
        setCaracter("Y");
        list.clear();
        cargartabla(tfbuscar.getText());
    }

    @javafx.fxml.FXML
    public void tZ(ActionEvent actionEvent) {
        setCaracter("Z");
        list.clear();
        cargartabla(tfbuscar.getText());
    }

    @javafx.fxml.FXML
    public void tmayor(ActionEvent actionEvent) {
        setCaracter(">");
        list.clear();
        cargartabla(tfbuscar.getText());
    }

    @javafx.fxml.FXML
    public void tpcoma(ActionEvent actionEvent) {
        setCaracter(";");
        list.clear();
        cargartabla(tfbuscar.getText());
    }

    @javafx.fxml.FXML
    public void t1(ActionEvent actionEvent) {
        setCaracter("1");
        list.clear();
        cargartabla(tfbuscar.getText());
    }

    @javafx.fxml.FXML
    public void t2(ActionEvent actionEvent) {
        setCaracter("2");
        list.clear();
        cargartabla(tfbuscar.getText());
    }

    @javafx.fxml.FXML
    public void t3(ActionEvent actionEvent) {

        setCaracter("3");
        list.clear();
        cargartabla(tfbuscar.getText());
    }

    @javafx.fxml.FXML
    public void tmayus(ActionEvent actionEvent) {
        if (mayuscula){
            tmayus.setText("MINUS");
            tA.setText("a");
            tB.setText("b");
            tQ.setText("q");
            tW.setText("w");
            tE.setText("e");
            tR.setText("r");
            Tt.setText("t");
            tY.setText("y");
            tU.setText("u");
            tI.setText("i");
            tO.setText("o");
            tP.setText("p");
            tS.setText("s");
            tD.setText("d");
            tF.setText("f");
            tG.setText("g");
            tH.setText("h");
            tJ.setText("j");
            tK.setText("k");
            tL.setText("l");
            tÑ.setText("ñ");
            tZ.setText("z");
            tX.setText("x");
            tC.setText("c");
            tV.setText("v");
            tB.setText("b");
            tN.setText("n");
            tM.setText("m");
            mayuscula = false;
        }else{
            tA.setText("A");
            tB.setText("B");
            tQ.setText("Q");
            tW.setText("W");
            tE.setText("E");
            tR.setText("R");
            Tt.setText("T");
            tY.setText("Y");
            tU.setText("U");
            tI.setText("I");
            tO.setText("O");
            tP.setText("P");
            tS.setText("S");
            tD.setText("D");
            tF.setText("F");
            tG.setText("G");
            tH.setText("H");
            tJ.setText("J");
            tK.setText("K");
            tL.setText("L");
            tÑ.setText("Ñ");
            tZ.setText("Z");
            tX.setText("X");
            tC.setText("C");
            tV.setText("V");
            tB.setText("B");
            tN.setText("N");
            tM.setText("M");
            tmayus.setText("MAYUS");
            mayuscula = true;
        }

    }

    @javafx.fxml.FXML
    public void tdospuntos(ActionEvent actionEvent) {
        setCaracter(":");
    }

    @FXML
    public void prueba(KeyEvent event) {
        System.out.println(tfbuscar.getText());
        list.clear();
        cargartabla(tfbuscar.getText());

    }


    @FXML
    public void clear(ActionEvent actionEvent) {
        tfbuscar.setText("");
        cadena="";
        list.clear();
        cargartabla(tfbuscar.getText());
    }


}
