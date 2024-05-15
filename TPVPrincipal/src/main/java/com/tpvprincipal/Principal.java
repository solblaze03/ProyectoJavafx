package com.tpvprincipal;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class Principal extends Application {

    private static Stage stage;
    private static Scene scene,sceneprincipal;

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(Principal.class.getResource("Appadmin.fxml"));
        scene = new Scene(fxmlLoader.load());
        scene.getStylesheets().add(getClass().getResource("css/inicio.css").toExternalForm());
        stage.setTitle("Hello!");
        stage.setScene(scene);
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


    public static void pantallaPrincipal(){
    try {
        FXMLLoader loader = new FXMLLoader(Principal.class.getResource("pantallainicio.fxml"));
        sceneprincipal = new Scene(loader.load());
        stage.setScene(sceneprincipal);
        stage.setFullScreen(true);

    }catch (IOException e){
        System.out.println(e.getMessage());
    }

    }

    public static void main(String[] args) {
        launch();
    }
}