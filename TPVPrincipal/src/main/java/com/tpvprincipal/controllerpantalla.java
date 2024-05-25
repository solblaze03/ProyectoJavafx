package com.tpvprincipal;

import com.tpvprincipal.clases.Conn;
import com.tpvprincipal.clases.Productos;
import com.tpvprincipal.clases.Usuario;
import com.tpvprincipal.excepciones.NegativeNumberException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.InputMethodEvent;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import org.apache.commons.math3.util.Precision;
import javax.swing.text.NumberFormatter;
import java.io.*;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    private TableView <Productos> tviewpequeño;
    @FXML
    private Label totalcajero;
    @FXML
    private Button tvcoma;
    @FXML
    private TextField tfvuelta;
    @FXML
    private Button tvdel;
    @FXML
    private Button tvclear;
    @FXML
    private Button tv0;
    @FXML
    private Button tv3;
    @FXML
    private Button tv2;
    @FXML
    private Button tv4;
    @FXML
    private Button tv7;
    @FXML
    private Button tv9;
    @FXML
    private Button tv8;
    @FXML
    private Label tdcto;
    @FXML
    private Button cobrarefec;
    @FXML
    private Button cobrarfac;
    @FXML
    private Button cerrarcaja;
    static Double dineroencaja = 0.0;

    public void cargartabla(String codigo){
        try {
            String sql = "SELECT *,categoria.nombre as cnombre,productos.nombre as pnombre FROM productos left join categoria on productos.id_categoria = categoria.id_categoria where codigo_barras like ?";
            PreparedStatement ps = Conn.con().prepareStatement(sql);
            ps.setString(1,codigo+"%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()){

                String categoria = null;
                if(rs.getString("cnombre") == null){
                    categoria = "Sin categoria";
                }else{
                    categoria = rs.getString("cnombre");
                }

                list.add(new Productos(rs.getString("codigo_barras"),rs.getString("pnombre"),categoria,rs.getString("url_codigobarras"),rs.getInt("iva"),rs.getInt("descuento"),rs.getDouble("precio"),rs.getDouble("precio"),1));
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
            System.out.println(e.getMessage());
        }
    }
    private double cant_descuento;
    private ObservableList<Productos> list2 = FXCollections.observableArrayList();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        cargartabla("");
        double totalcaja = 0;
        double suma1 = 0;
        try {
            FileReader fr = new FileReader("src/main/resources/com/tpvprincipal/Facturas/total.txt");
            BufferedReader bf = new BufferedReader(fr);
            String linea  = bf.readLine();

            if (linea != null){
                String partescadena[] = linea.split(":");
                totalfacturas = Double.parseDouble(partescadena[0]);
                Billete.suma = Double.parseDouble(partescadena[1]);
                dineroencaja = Double.parseDouble(partescadena[2]);
            }else{
                dineroencaja = Billete.suma;
            }
            System.out.println("Total suma: "+Billete.suma);
            System.out.println("Total facturas: "+totalfacturas);
            System.out.println("Dinero en caja "+ dineroencaja);

            FileWriter fw = new FileWriter("src/main/resources/com/tpvprincipal/Facturas/total.txt");
            fw.write(totalfacturas+":"+Billete.suma+":"+dineroencaja);
            fw.close();
        }catch (IOException e){
            System.out.println(e.getMessage());
        }




        cobrarefec.setDisable(true);
        cobrarfac.setDisable(true);
        Usuario usuario = Controller.user();
        nombre.setText(usuario.getNombre());
        panel.getStylesheets().add(getClass().getResource("css/tablas.css").toExternalForm());

        System.out.println(Billete.suma);
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
        tviewpequeño.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2){

                Productos productos1 = tviewpequeño.getSelectionModel().getSelectedItem();
                for (int i = 0; i < list2.size(); i++) {
                    if (list2.get(i).getCodigosbarra().equals(productos1.getCodigosbarra())){
                        if(list2.get(i).getCantidad() == 1){
                            //double total = ((list2.get(i).getCantidad() - 1) * list2.get(i).getTotal()) / list2.get(i).getCantidad();
                            //System.out.println(total);
                            double preciounidad = (list2.get(i).getTotal()) / list2.get(i).getCantidad();

                            totalfactura -= preciounidad;
                            totalcajero.setText(totalfactura+"");
                            list2.remove(i);
                        }else {
                            double total = ((list2.get(i).getCantidad() - 1) * list2.get(i).getTotal()) / list2.get(i).getCantidad();
                            list2.get(i).setTotal(total);
                            list2.get(i).setCantidad((int) list2.get(i).getCantidad() - 1);
                            System.out.println(("1 "+ Double.parseDouble(totalcajero.getText())));
                            System.out.println("total "+total);

                            double preciounidad = (1 * list2.get(i).getTotal()) / list2.get(i).getCantidad();
                            System.out.println("Precio unidad: "+preciounidad);
                            totalcajero.setText( (totalfactura - preciounidad + ""));
                            totalfactura -=  preciounidad ;
                            list2.set(i, list2.get(i));
                        }
                    }
                }
            }
        });
    }

    private String cadnumero = "";
    double totalfactura  = 0;
    public void agregarTviewPequeño(Productos producto){
        double totaldcto = 0;
        int numprod = 0;
        boolean esta = false;
        int numero = 0;
        cobrarfac.setDisable(false);
        cobrarefec.setDisable(false);

        for (int i = 0; i < list2.size(); i++) {
            if (list2.get(i).getCodigosbarra().equals(producto.getCodigosbarra())){
                numero = i;
                esta = true;
            }
        }

        if(!esta) {
                if(producto.getDcto() == 0) {

                    list2.add(producto);
                    tunidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
                    tproducto.setCellValueFactory(new PropertyValueFactory<>("nombre"));
                    tprecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
                    ttotal.setCellValueFactory(new PropertyValueFactory<>("total"));
                    totalfactura += producto.getPrecio();
                    tviewpequeño.setItems(list2);
                    numprod = list2.indexOf(producto);
                }else{

                    producto.setTotal(producto.getPrecio() -  (producto.getPrecio() * producto.getDcto() / 100));
                    System.out.println(producto.getTotal());

                    list2.add(producto);
                    tunidad.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
                    tproducto.setCellValueFactory(new PropertyValueFactory<>("nombre"));
                    tprecio.setCellValueFactory(new PropertyValueFactory<>("precio"));
                    ttotal.setCellValueFactory(new PropertyValueFactory<>("total"));
                    tviewpequeño.setItems(list2);
                    totalfactura += producto.getPrecio() - ((producto.getPrecio() * producto.getDcto() / 100));
                }


        }else{



            if(list2.get(numero).getDcto() == 0) {
                list2.get(numero).setCantidad(list2.get(numero).getCantidad() + 1);
                list2.get(numero).setTotal(list2.get(numero).getPrecio());


                list2.set(numero, list2.get(numero));
                totalfactura += list2.get(numero).getPrecio();
                list2.get(numero).setTotal(list2.get(numero).getPrecio() * list2.get(numero).getCantidad());
                list2.set(numero,list2.get(numero));
            }else{
                System.out.println("entra");
                Productos pd = list2.get(numero);


                pd.setCantidad(pd.getCantidad() + 1);
                double totalcolumn =  (((pd.getPrecio() * pd.getCantidad()) - (((pd.getPrecio() * pd.getCantidad()) * pd.getDcto())) / 100));

                pd.setTotal(totalcolumn);



                list2.set(numero, pd);
                totalfactura += producto.getPrecio() - ((producto.getPrecio() * producto.getDcto() / 100));
            }
        }

        totalcajero.setText( Precision.round(totalfactura,2)+"");

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
    static Double totalfacturas = 0.0;

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

    private String cadenapq = "";
    private int cont = 0;
    public void agregarNumerostfVuelta(String num){


            if(punto && cont < 2){
                System.out.println("arriba");
                cadenapq += num;
                tfvuelta.setText(cadenapq);
                cont++;
            }else if(cont <2){
                System.out.println("abajo");

                cadenapq += num;
                tfvuelta.setText(cadenapq);
            }
    }



    public void numnegativo(double num) throws NegativeNumberException{
        if(num >0){
            throw new NegativeNumberException("Numero negativo");
        }
    }

    public void pagar(String tipopago){
        try {
            String total[] =  totalcajero.getText().split("€");
            System.out.println(total[0]);
            System.out.println(tfvuelta.getText());
            if(tipopago.equals("Efectivo")){
                double vueltas = Double.parseDouble(total[0]) - Double.parseDouble(tfvuelta.getText());
                numnegativo(vueltas);
                double cambiototal = Double.parseDouble(tfvuelta.getText()) - Double.parseDouble(total[0]);
                cambiototal = Precision.round(cambiototal, 2);
                Alert alertvueltas = new Alert(Alert.AlertType.INFORMATION);
                alertvueltas.setTitle("Cambio");
                alertvueltas.setHeaderText("El total de cambio es de " + cambiototal + "€");
                alertvueltas.showAndWait();
            }
            String sql = "insert into factura (nombre_usuario,hora_compra,tipo_pago) values ( ?,Current_TIMESTAMP , ? );";
            PreparedStatement ps = Conn.con().prepareStatement(sql,PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1,Controller.user().getNombre());
            ps.setString(2,tipopago);
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            int registro = 0;
            if (rs.next()){
                registro = rs.getInt(1);
            }
            for (int i = 0; i < list2.size(); i++) {

                String sqldetalle = "insert into detallefactura (idfactura,producto,cantidad,precio,descuento,total) values (?,?,?,?,?,?);";
                PreparedStatement psdetalle = Conn.con().prepareStatement(sqldetalle);
                psdetalle.setInt(1,registro);
                psdetalle.setString(2,list2.get(i).getNombre());
                psdetalle.setInt(3,list2.get(i).getCantidad());
                psdetalle.setDouble(4,list2.get(i).getPrecio());
                psdetalle.setDouble(5,list2.get(i).getDcto());
                psdetalle.setDouble(6,list2.get(i).getTotal());
                System.out.println("Entra");
                System.out.println( psdetalle.executeUpdate());


            }





            String linea1 = "";
            double totalsindcto = 0;
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");

            for (int i = 0; i < list2.size(); i++) {
                Productos pro = list2.get(i);

                linea1 += String.format("Cantidad %d - Producto %s - IVA %d %%  - Precio %.2f € - dcto %d %% \n", pro.getCantidad(), pro.getNombre(), pro.getIva(), (pro.getPrecio() * pro.getCantidad()), pro.getDcto());
                totalsindcto += pro.getPrecio() * pro.getCantidad();
            }

            String facturapago = "_____________________________________________________\n" +
                    "Bienvenido\n" +
                    "\n" +
                    "numero de factura: "+registro+
                    "\nHa sido atendido por " + Controller.user().getNombre() + "\n" +
                    "Se puede contactar al siguiente teléfono: +34 747429922\n" +
                    "Compra hecha el " + dtf.format(LocalDateTime.now()) +
                    "\n_____________________________________________________\n" +
                    linea1 +
                    "_______________________________________________________\n" +
                    "Total sin descuento: " + totalsindcto +
                    "\nTotal: " + totalcajero.getText() + "\n" +
                    "Pago hecho con: "+tipopago+


                    "\n________________________________________________________";

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Factura");
            alert.setHeaderText(null);
            TextArea ta = new TextArea(facturapago);
            ta.setEditable(false);
            ta.setWrapText(true);
            alert.getDialogPane().setContent(ta);
            alert.setHeight(430.0);
            alert.showAndWait();


            // imprimir en fichero
            FileWriter fw = new FileWriter("src/main/resources/com/tpvprincipal/Facturas/file1.txt");
            fw.write(facturapago);
            fw.close();

            totalfacturas +=  Precision.round(Double.parseDouble(total[0]),2);
            System.out.println(totalfacturas);

            list2.clear();

            FileWriter fwtotal = new FileWriter("src/main/resources/com/tpvprincipal/Facturas/total.txt");

            if (tipopago.equals("Tarjeta")){
                System.out.println("Tarjeta");
                System.out.println(dineroencaja);
                fwtotal.write( totalfacturas+ ":" + Billete.suma+":"+ dineroencaja);
                fwtotal.close();
            }else {
                System.out.println("Efectivo");
                dineroencaja = Billete.suma + totalfacturas;
                fwtotal.write(totalfacturas + ":" + Billete.suma+":"+ dineroencaja);
                fwtotal.close();
            }

            cobrarefec.setDisable(true);
            cobrarfac.setDisable(true);
            totalcajero.setText("0.0 €");
            tfvuelta.setText("");
            totalfactura = 0;
            cadenapq = "";
            cont = 0;
            punto = false;
            tfbuscar.setText("");
            list.clear();

        }catch (IOException e){
            System.out.println(e.getMessage());
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }catch (NegativeNumberException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No tiene suficiente dinero para hacer esta compra");
            alert.show();
        }catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No has introducido ningun numero");
            alert.show();
        }
    }



    @FXML
    public void tv1(ActionEvent actionEvent) {
        agregarNumerostfVuelta("1");
    }

    @FXML
    public void tv3(ActionEvent actionEvent) {
        agregarNumerostfVuelta("3");
    }

    @FXML
    public void tv2(ActionEvent actionEvent) {
        agregarNumerostfVuelta("2");
    }

    @FXML
    public void tv5(ActionEvent actionEvent) {
        agregarNumerostfVuelta("5");
    }

    @FXML
    public void tv4(ActionEvent actionEvent) {
        agregarNumerostfVuelta("4");
    }

    @FXML
    public void tv7(ActionEvent actionEvent) {
        agregarNumerostfVuelta("7");
    }

    @FXML
    public void tv6(ActionEvent actionEvent) {
        agregarNumerostfVuelta("6");
    }

    @FXML
    public void tv9(ActionEvent actionEvent) {
        agregarNumerostfVuelta("9");
    }

    @FXML
    public void tv8(ActionEvent actionEvent) {
        agregarNumerostfVuelta("8");
    }
    @FXML
    public void tv0(ActionEvent actionEvent) {
        agregarNumerostfVuelta("0");
    }
    boolean punto = false;
    @FXML
    public void tvcoma(ActionEvent actionEvent) {
        if(!cadenapq.contains(".") && cadenapq.length() != 0) {
            agregarNumerostfVuelta(".");
            punto = true;
        }
    }

    @FXML
    public void tvdel(ActionEvent actionEvent) {
        if(tfvuelta.getText().length() != 0){
            String caracter = cadenapq.substring(tfvuelta.getText().length()-1,tfvuelta.getText().length());
            cadenapq = cadenapq.substring(0,tfvuelta.getText().length()-1);
            tfvuelta.setText(cadenapq);

            if (caracter.equals(".")){
                punto = false;
                cont = 0;
            }
            if(punto){
                cont--;
            }
        }
    }

    @FXML
    public void tvclear(ActionEvent actionEvent) {
        cadenapq = "";
        tfvuelta.setText("");
        punto = false;
        cont = 0;
    }

    @FXML
    public void fullscreen(ActionEvent actionEvent) {
        Principal.fullScreen();
    }

    @FXML
    public void cobrarTarjeta(ActionEvent actionEvent) {
        pagar("Tarjeta");
    }

    @FXML
    public void cobrarEfectivo(ActionEvent actionEvent) {
        pagar("Efectivo");
    }
    @FXML
    public void cerrarCaja(ActionEvent actionEvent) {
        try {
            Statement st = Conn.con().createStatement();
            st.executeUpdate("UPDATE usuarios SET sesion_abierta = 0 where sesion_abierta = 1");
            FileWriter fw = new FileWriter("src/main/resources/com/tpvprincipal/Facturas/total.txt");
            fw.close();
            Principal.pantalladinero();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
}
