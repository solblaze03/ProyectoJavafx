package com.tpv;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class Principal extends Application {

    private static Stage stage;
    private static Scene scene,scene1,escenaaddusers,scenacategorias;

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(Principal.class.getResource("Appadmin.fxml"));
        scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();


        //scene1 = new Scene(loader1.load());

        FXMLLoader loaderusers = new FXMLLoader(Principal.class.getResource("anyadirusuarios.fxml"));
        escenaaddusers = new Scene(loaderusers.load());
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

        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
    public static void addusuario(){
            stage.setScene(escenaaddusers);
    }
    public static void escenainicio(){
        stage.setScene(scene);
    }

    public static void escenaCategorias() {
        try {
            FXMLLoader loadercategorias = new FXMLLoader(Principal.class.getResource("Anyadircategorias.fxml"));
            scenacategorias = new Scene(loadercategorias.load());
            stage.setScene(scenacategorias);
        }catch (IOException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText(e.getMessage());
            alert.show();
        }
    }
    public static void main(String[] args) {
        launch();
    }
}