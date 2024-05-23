package com.tpv;

import com.tpv.clases.Conn;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        pane.getStylesheets().add(getClass().getResource("css/grafica.css").toExternalForm());
        try {
            String sql = "select  sum(total) as suma from factura join detallefactura using(idfactura) where EXTRACT(MONTH FROM hora_compra) = ?;";

            nummes  = LocalDate.now().getMonth().getValue();
            PreparedStatement ps = Conn.con().prepareStatement(sql);
            ps.setInt(1,nummes);
            ResultSet rs = ps.executeQuery();
            if(rs.next()) {

                DecimalFormat formatter = new DecimalFormat("#,###.00");
                gananciasMes.setText(formatter.format(rs.getDouble("suma"))+"€");
            }
            Month mesanyo = LocalDate.now().getMonth();
            Locale local = new Locale("es","ES");
            String letraMayuscula = mesanyo.getDisplayName(TextStyle.FULL,local).substring(0,1);
            String restominuscula = mesanyo.getDisplayName(TextStyle.FULL,local).substring(1,mesanyo.getDisplayName(TextStyle.FULL,local).length());
            String Mesespanyol = letraMayuscula.toUpperCase() + restominuscula;

            diaMes.setText("Total ganancias "+fechas[nummes-1]);

            cargarMes();
        }catch (SQLException e){
            System.out.println(e.getMessage());
        }
        double posiciony = gananciasaño.getLayoutY();
    /*
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                num--;
                gananciasaño.setLayoutY(num);


            }
        }));

     */

    }
    private Timeline timeline;
    //private double num = gananciasaño.getLayoutY();
    public void cargarMes(){
        try {

            XYChart.Series series = new XYChart.Series();

            ArrayList<Integer> list = new ArrayList<>();
            String sql = "select  EXTRACT(DAY FROM hora_compra) as dia ,EXTRACT(MONTH FROM hora_compra) as mes,sum(total) as suma\n" +
                    "from factura join detallefactura using(idfactura) \n" +
                    "group by dia , mes having EXTRACT(MONTH FROM hora_compra)= ?;";
            PreparedStatement ps = Conn.con().prepareStatement(sql);

            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.DAY_OF_MONTH,1);
            calendar.add(Calendar.MONTH,1);
            calendar.add(Calendar.DAY_OF_MONTH,-1);
            int meses = LocalDate.now().getMonth().getValue();
            int ultimodia = calendar.get(Calendar.DAY_OF_MONTH);
            ps.setInt(1,meses);
            ResultSet rs = ps.executeQuery();

            while (rs.next()){
                series.getData().add(new XYChart.Data<>(rs.getInt("dia") + "", rs.getInt("suma") ));

            }
            series.setName("Mayo");

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
                diaMes.setText("Total ganancias "+fechas[nummes-1]);
                XYChart.Series series = new XYChart.Series();

                String sql = "select  EXTRACT(DAY FROM hora_compra) as dia ,EXTRACT(MONTH FROM hora_compra) as mes,sum(total) as suma\n" +
                        "from factura join detallefactura using(idfactura) \n" +
                        "group by dia , mes having EXTRACT(MONTH FROM hora_compra)= ?;";
                PreparedStatement ps = Conn.con().prepareStatement(sql);
                ps.setInt(1,i);
                System.out.println(nummes);

                ResultSet rs = ps.executeQuery();
                boolean registros = false;
                while (rs.next()){
                    series.getData().add(new XYChart.Data<>(rs.getInt("dia") + "", rs.getInt("suma") ));
                    registros = true;
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
        }

    }

    @FXML
    public void abajo(ActionEvent actionEvent) {
        timeline.play();
    }
}
