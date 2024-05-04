package com.tpv;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Principal extends Application {

    private static Stage stage;
    private static Scene scene,scene1;

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


    }
    public static void escenaManejoTpv(){
        try {
            FXMLLoader loader1 = new FXMLLoader(Principal.class.getResource("manejotpv.fxml"));
            scene1 = new Scene(loader1.load());
            stage.setScene(scene1);
        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }
    public static void escenainicio(){
        stage.setScene(scene);
    }



    public static void main(String[] args) {
        launch();
    }
}