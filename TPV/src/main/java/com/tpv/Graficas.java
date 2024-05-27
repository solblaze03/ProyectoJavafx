package com.tpv;

import com.tpv.clases.Conn;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.awt.event.MouseEvent;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.TextStyle;
import java.util.*;

public class Graficas implements Initializable {
    @FXML
    private LineChart<?, ?> mes;

    @FXML
    private CategoryAxis xmes;

    @FXML
    private NumberAxis ymes;
    @FXML
    private Label gananciasMes;
    @FXML
    private Label diaMes;
    @FXML
    private AnchorPane pane;
    private int nummes;
    private String fechas[] = {"Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto","Septiembre","Octubre","Noviembre","Diciembre"};
    @FXML
    private Pane gananciasaño;
    @FXML
    private Pane escena2;
    @FXML
    private Button arriba;
    @FXML
    private Label gananciasanyo;
    @FXML
    private LineChart graficameses;
    @FXML
    private Pane gananciasmeses;
    @FXML
    private Pane pangananciasaño;
    @FXML
    private Label meses;
    @FXML
    private Label dias;
    @FXML
    private Label comprasaño;
    private int año;
    @FXML
    private Label dias1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pane.getStylesheets().add(getClass().getResource("css/grafica.css").toExternalForm());
        try {
            // ganancias mes
            String sql = "select  sum(total) as suma from factura join detallefactura using(idfactura) where EXTRACT(MONTH FROM hora_compra) = ? AND EXTRACT(Year FROM hora_compra) = ?;";
            nummes  = LocalDate.now().getMonth().getValue();
            PreparedStatement ps = Conn.con().prepareStatement(sql);
            ps.setInt(1,nummes);
            ps.setInt(2,LocalDate.now().getYear());
            ResultSet rs = ps.executeQuery();
            DecimalFormat formatter = new DecimalFormat("#,###.00");
            if(rs.next()) {

                gananciasMes.setText(formatter.format(rs.getDouble("suma"))+"€");
            }
            diaMes.setText("Total ganancias "+fechas[nummes-1] +" de "+LocalDate.now().getYear());
            cargarMes();
            //ganancias mes

            //ganancias año
            año = LocalDate.now().getYear();
            comprasaño.setText("Total compras año "+año);
            String sqlaño = "select sum(total) as suma from detallefactura join factura using (idfactura) where EXTRACT(YEAR FROM hora_compra)= ?";
            PreparedStatement anyo = Conn.con().prepareStatement(sqlaño);
            anyo.setInt(1,año);
            ResultSet rs1 = anyo.executeQuery();
            if (rs1.next()){
                gananciasanyo.setText(formatter.format(rs1.getDouble("suma"))+"€");
            }
            cargarMesesYear();

            //ganancias año
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        double posiciony = gananciasaño.getLayoutY();





    }
    private Timeline timeline;
    private String linea = "";

    public void cargarMesesYear(){
        try {
            XYChart.Series series1 = new XYChart.Series();
            int año = LocalDate.now().getYear();
            String sqlgananciasmeses = "select EXTRACT(month FROM hora_compra) as mes ,EXTRACT(YEAR FROM hora_compra) as anyo,sum(total) as suma\n" +
                    "from factura join detallefactura using(idfactura) \n" +
                    "group by mes , anyo having EXTRACT(YEAR FROM hora_compra)= ?;\n";
            PreparedStatement ps2 = Conn.con().prepareStatement(sqlgananciasmeses);
            ps2.setInt(1,año);
            ResultSet rs = ps2.executeQuery();
            DecimalFormat formatter = new DecimalFormat("#,###.00");
            while (rs.next()){
                series1.getData().add(new XYChart.Data<>(fechas[rs.getInt("mes")-1] + "", rs.getDouble("suma") ));
                //Tooltip tooltip = new Tooltip("Día: " + rs.getInt("dia") + "\nGanancia: " + rs.getDouble("suma"));
                //Tooltip.install(dataPoint.getNode(), tooltip);
                linea += fechas[rs.getInt("mes")-1]  +" "+ formatter.format(rs.getDouble("suma"))+"€\n";
            }
            meses.setText(linea);
            tooltip = new Tooltip(linea);
            tooltip.setStyle("-fx-font-size:20;");
            Tooltip.install(pangananciasaño, tooltip);
            graficameses.getData().add(series1);

        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    private String lineameses = "";
    private Tooltip tooltip;
    public void cargarMes(){
        try {

            XYChart.Series series = new XYChart.Series();
            String sql = "select  EXTRACT(DAY FROM hora_compra) as dia ,EXTRACT(MONTH FROM hora_compra) as mes,sum(total) as suma\n" +
                    "from factura join detallefactura using(idfactura) \n" +
                    "group by dia , mes having EXTRACT(MONTH FROM hora_compra)= ?  ;";
            PreparedStatement ps = Conn.con().prepareStatement(sql);

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_MONTH,1);
            calendar.add(Calendar.MONTH,1);
            calendar.add(Calendar.DAY_OF_MONTH,-1);
            int meses = LocalDate.now().getMonth().getValue();
            int ultimodia = calendar.get(Calendar.DAY_OF_MONTH);
            ps.setInt(1,meses);
            //ps.setInt(2,LocalDate.now().getYear());
            ResultSet rs = ps.executeQuery();
            DecimalFormat formatter = new DecimalFormat("#,###.00");
            int cont = 0;
            String lineameses1 ="";
            String lineameses2 = "";
            boolean cambio = false;
            while (rs.next()){
                series.getData().add(new XYChart.Data<>(rs.getInt("dia") + "", rs.getDouble("suma") ));
                if(!cambio) {
                    lineameses += "Dia " + rs.getInt("dia") + " " + formatter.format(rs.getDouble("suma")) + "€\n";
                    lineameses1 += "Dia " + rs.getInt("dia") + " " + formatter.format(rs.getDouble("suma")) + "€\n";
                    cambio = true;
                }else{
                    lineameses += "Dia " + rs.getInt("dia") + " " + formatter.format(rs.getDouble("suma")) + "€\n";
                    lineameses2 += "Dia " + rs.getInt("dia") + " " + formatter.format(rs.getDouble("suma")) + "€\n";
                    cambio = false;
                }
            }

                dias.setText(lineameses1);
            System.out.println("1: "+lineameses1);
            System.out.println("2: "+lineameses2);
                dias1.setText(lineameses2);

            tooltip = new Tooltip(lineameses);
            tooltip.setStyle("-fx-font-size:20;");
            Tooltip.install(gananciasmeses, tooltip);

            mes.getData().add(series);
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void regresar(ActionEvent actionEvent) {
        Principal.escenaManejoTpv();
    }

    @FXML
    public void despues(ActionEvent actionEvent) {
        nummes++;
        cambiografico(nummes);
    }

    @FXML
    public void antes(ActionEvent actionEvent) {
        nummes--;

        cambiografico(nummes);


    }
    public void cambiografico(int i){

        try {

            if(i >= 1 && i <= 12) {
                diaMes.setText("Total compras "+fechas[nummes-1]+" de "+LocalDate.now().getYear());
                XYChart.Series series = new XYChart.Series();

                String sql = "select  EXTRACT(DAY FROM hora_compra) as dia ,EXTRACT(MONTH FROM hora_compra) as mes,sum(total) as suma\n" +
                        "from factura join detallefactura using(idfactura) \n" +
                        "group by dia , mes having EXTRACT(MONTH FROM hora_compra)= ?;";
                PreparedStatement ps = Conn.con().prepareStatement(sql);
                ps.setInt(1,i);
                System.out.println(nummes);

                ResultSet rs = ps.executeQuery();
                boolean registros = false;
                DecimalFormat formatter = new DecimalFormat("#,###.00");
                lineameses = "";
                boolean cambio = false;
                String lineameses1 = "";
                String lineameses2 = "";
                /*
                while (rs.next()){
                    series.getData().add(new XYChart.Data<>(rs.getInt("dia") + "", rs.getDouble("suma") ));
                    registros = true;
                    lineameses += "Dia "+rs.getInt("dia") + " "+ formatter.format(rs.getDouble("suma"))+"€\n";
                }

                 */

                while (rs.next()){
                    series.getData().add(new XYChart.Data<>(rs.getInt("dia") + "", rs.getDouble("suma") ));
                    registros = true;
                    if(!cambio) {
                        lineameses += "Dia " + rs.getInt("dia") + " " + formatter.format(rs.getDouble("suma")) + "€\n";
                        lineameses1 += "Dia " + rs.getInt("dia") + " " + formatter.format(rs.getDouble("suma")) + "€\n";

                        cambio = true;
                    }else{
                        lineameses += "Dia " + rs.getInt("dia") + " " + formatter.format(rs.getDouble("suma")) + "€\n";

                        lineameses2 += "Dia " + rs.getInt("dia") + " " + formatter.format(rs.getDouble("suma")) + "€\n";
                        cambio = false;
                    }
                }
                dias.setText(lineameses1);
                dias1.setText(lineameses2);


                Tooltip tooltip = new Tooltip(lineameses);
                System.out.println(tooltip.getText());
                tooltip.setStyle("-fx-font-size:20;");
                Tooltip.install(gananciasmeses, tooltip);

                String sql1 = "select  sum(total) as suma from factura join detallefactura using(idfactura) where EXTRACT(MONTH FROM hora_compra) = ?  AND EXTRACT(Year FROM hora_compra) = ? ;";


                PreparedStatement ps1 = Conn.con().prepareStatement(sql1);
                System.out.println("mes "+nummes);
                ps1.setInt(1,nummes);
                ps1.setInt(2,LocalDate.now().getYear());
                ResultSet rs1 = ps1.executeQuery();
                if(rs1.next()) {


                    gananciasMes.setText(formatter.format(rs1.getDouble("suma"))+"€");
                }

                mes.getData().clear();

                if (registros) {


                    mes.getData().add(series);
                }
            } else if (i < 1) {
                nummes++;
            }else if (i > 12){
                nummes--;
            }


        }catch (SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

    }

    @FXML
    public void abajo(ActionEvent actionEvent) {
        TranslateTransition translate = new TranslateTransition();
        translate.setNode(gananciasaño);
        translate.setDuration(Duration.millis(1000));
        translate.setByY(-1500);
        translate.play();
        TranslateTransition translate1 = new TranslateTransition();
        translate1.setNode(escena2);
        translate1.setDuration(Duration.millis(1000));
        translate1.setByY(-1090);
        translate1.play();
    }

    @FXML
    public void arriba(ActionEvent actionEvent) {
        TranslateTransition translate = new TranslateTransition();
        translate.setNode(gananciasaño);
        translate.setDuration(Duration.millis(1000));
        translate.setByY(1500);
        translate.play();
        TranslateTransition translate1 = new TranslateTransition();
        translate1.setNode(escena2);
        translate1.setDuration(Duration.millis(1000));
        translate1.setByY(1090);
        translate1.play();
    }

    @FXML
    public void tgmeses(Event event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Ganancias meses");
        TextArea ta = new TextArea(lineameses);
        ta.setEditable(false);
        ta.setWrapText(true);
        alert.getDialogPane().setContent(ta);
        alert.showAndWait();


    }

    @FXML
    void tgaño(Event event) {

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Ganancias meses");
        TextArea ta = new TextArea(linea);
        ta.setEditable(false);
        ta.setWrapText(true);
        alert.getDialogPane().setContent(ta);
        alert.showAndWait();

    }

    @FXML
    public void despuesaño(ActionEvent actionEvent) {
        try {
            año++;
            System.out.println(año);
            XYChart.Series series1 = new XYChart.Series();

            String sqlgananciasmeses = "select EXTRACT(month FROM hora_compra) as mes ,EXTRACT(YEAR FROM hora_compra) as anyo,sum(total) as suma\n" +
                    "from factura join detallefactura using(idfactura) \n" +
                    "group by mes , anyo having EXTRACT(YEAR FROM hora_compra)= ?;\n";
            PreparedStatement ps2 = Conn.con().prepareStatement(sqlgananciasmeses);
            ps2.setInt(1,año);
            ResultSet rs = ps2.executeQuery();
            DecimalFormat formatter = new DecimalFormat("#,###.00");
            boolean registros = false;
            double suma = 0;
            while (rs.next()){
                registros = true;
                series1.getData().add(new XYChart.Data<>(fechas[rs.getInt("mes")-1] + "", rs.getDouble("suma") ));

                //Tooltip tooltip = new Tooltip("Día: " + rs.getInt("dia") + "\nGanancia: " + rs.getDouble("suma"));
                //Tooltip.install(dataPoint.getNode(), tooltip);
                suma +=rs.getDouble("suma");
                linea += fechas[rs.getInt("mes")-1]  +" "+ formatter.format(rs.getDouble("suma"))+"€\n";
            }
            if (!registros){
                comprasaño.setText("Total compras año "+año);
                gananciasanyo.setText("0.0€");
                graficameses.getData().clear();
                meses.setText("");
                tooltip.setText("");
                linea = "";
            }else {
                comprasaño.setText("Total compras año "+año);
                gananciasanyo.setText(formatter.format(suma)+"€");
                meses.setText(linea);
                tooltip = new Tooltip(linea);
                tooltip.setStyle("-fx-font-size:20;");
                Tooltip.install(pangananciasaño, tooltip);
                graficameses.getData().add(series1);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void antesaño(ActionEvent actionEvent) {
        try {
            año--;
            System.out.println(año);
            XYChart.Series series1 = new XYChart.Series();

            String sqlgananciasmeses = "select EXTRACT(month FROM hora_compra) as mes ,EXTRACT(YEAR FROM hora_compra) as anyo,sum(total) as suma\n" +
                    "from factura join detallefactura using(idfactura) \n" +
                    "group by mes , anyo having EXTRACT(YEAR FROM hora_compra)= ?;\n";
            PreparedStatement ps2 = Conn.con().prepareStatement(sqlgananciasmeses);
            ps2.setInt(1,año);
            ResultSet rs = ps2.executeQuery();
            DecimalFormat formatter = new DecimalFormat("#,###.00");
            boolean registros = false;
            while (rs.next()){
                registros = true;
                series1.getData().add(new XYChart.Data<>(fechas[rs.getInt("mes")-1] + "", rs.getDouble("suma") ));
                //Tooltip tooltip = new Tooltip("Día: " + rs.getInt("dia") + "\nGanancia: " + rs.getDouble("suma"));
                //Tooltip.install(dataPoint.getNode(), tooltip);
                linea += fechas[rs.getInt("mes")-1]  +" "+ formatter.format(rs.getDouble("suma"))+"€\n";
            }
            if (!registros){
                comprasaño.setText("Total compras año "+año);
                gananciasanyo.setText("0.0€");
                graficameses.getData().clear();
                meses.setText("");
                tooltip.setText("");
                linea = "";
            }else {
                comprasaño.setText("Total compras año "+año);
                meses.setText(linea);
                tooltip = new Tooltip(linea);
                tooltip.setStyle("-fx-font-size:20;");
                Tooltip.install(pangananciasaño, tooltip);
                graficameses.getData().add(series1);
            }
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
    }
}
