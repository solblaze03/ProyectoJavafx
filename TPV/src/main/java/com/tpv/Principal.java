package com.tpv;

import com.google.zxing.common.BitMatrix;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class Principal extends Application {

    static Stage stage;
    static Scene scene,scene1,escenaaddusers,scenacategorias,escenaproductos,graficas,factura;

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(Principal.class.getResource("Appadmin.fxml"));
        scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(getClass().getResource("css/inicio.css").toExternalForm());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.setTitle("TPV IES la Senia");
        stage.getIcons().add(new Image(getClass().getResourceAsStream("tpv.png")));
        stage.setResizable(false);

        stage.show();


        //scene1 = new Scene(loader1.load());


    }



    /*
    *  Aqui
    *  Para
    *  adelante
    *  solo
    *  Cambio
    *  de
    *  escenas
     */
    public static void escenaManejoTpv(){
        try {
            FXMLLoader loader1 = new FXMLLoader(Principal.class.getResource("manejotpv.fxml"));
            scene1 = new Scene(loader1.load());
            stage.setScene(scene1);
            stage.setResizable(false);

        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
    public static void addusuario(){
        try {
            FXMLLoader loaderusers = new FXMLLoader(Principal.class.getResource("anyadirusuarios.fxml"));
            escenaaddusers = new Scene(loaderusers.load());
            stage.setScene(escenaaddusers);
            stage.setResizable(false);
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
    public static void escenainicio(){
        stage.setScene(scene);
        stage.setResizable(false);
    }

    public static void escenaproductos(){
        try {
            FXMLLoader loaderproductos = new FXMLLoader(Principal.class.getResource("Anyadirproductos.fxml"));

            escenaproductos = new Scene(loaderproductos.load());

            stage.setScene(escenaproductos);
            stage.setResizable(false);
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public static void escenaCategorias() {
        try {


            FXMLLoader loadercategorias = new FXMLLoader(Principal.class.getResource("Anyadircategorias.fxml"));
            scenacategorias = new Scene(loadercategorias.load());
            stage.setScene(scenacategorias);
            stage.setResizable(false);
        }catch (IOException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }
    public static void graficas() {
        try {
            FXMLLoader loadergraficas = new FXMLLoader(Principal.class.getResource("graficas.fxml"));
            graficas = new Scene(loadergraficas.load());
            stage.setScene(graficas);
            stage.setResizable(true);
            stage.setFullScreen(true);
        }catch (IOException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("error graficas");
            alert.setContentText(e.getMessage());
            alert.show();
            e.printStackTrace();
        }

    }
    public static void buscarFactura() {
        try {
            FXMLLoader fxfactura = new FXMLLoader(Principal.class.getResource("Buscarfactura.fxml"));
            factura = new Scene(fxfactura.load());
            stage.setScene(factura);
            stage.setResizable(false);

        }catch (IOException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("error graficas");
            alert.setContentText(e.getMessage());
            alert.show();
            e.printStackTrace();
        }

    }
    public static void main(String[] args) {
        launch();
    }
}