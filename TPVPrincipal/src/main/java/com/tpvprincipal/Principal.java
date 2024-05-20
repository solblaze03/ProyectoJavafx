package com.tpvprincipal;

import com.tpvprincipal.clases.Conn;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLRecoverableException;
import java.sql.Statement;

public class Principal extends Application {

    static Stage stage;
    static Scene scene,sceneprincipal,scenedinero;

    @Override
    public void start(Stage stage) throws IOException {
        this.stage = stage;


        try {
            Statement st = Conn.con().createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM usuarios where sesion_abierta = 1");
            if (rs.next()){
                Controller.user().añadirUsuario(rs.getString("dni"),rs.getString("nombre"),rs.getString("privilegios"),rs.getString("url_imagen"),rs.getString("contrasenya"));
                FXMLLoader loadercpantalla = new FXMLLoader(Principal.class.getResource("pantallainicio.fxml"));
                sceneprincipal = new Scene(loadercpantalla.load());
                stage.setScene(sceneprincipal);
                stage.setResizable(true);
                stage.show();

            }else{
                FXMLLoader fxmlLoader = new FXMLLoader(Principal.class.getResource("Appadmin.fxml"));
                scene = new Scene(fxmlLoader.load());
                scene.getStylesheets().add(getClass().getResource("css/inicio.css").toExternalForm());
                stage.setTitle("Hello!");
                stage.setScene(scene);
                stage.setResizable(false);

                stage.show();
            }

        }catch (SQLException e){
            System.out.println(e.getMessage());
        }


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
            stage.setResizable(true);


        }catch (IOException e){
            System.out.println(e.getMessage());
        }
    }

    public static void pantalladinero(){
        try {
            FXMLLoader loader = new FXMLLoader(Principal.class.getResource("cantidaddinero.fxml"));
            scenedinero = new Scene(loader.load());
            stage.setScene(scenedinero);
        }catch (IOException e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("IOException");
            alert.setHeaderText(e.getMessage());
            alert.show();
        }

    }

    public static void fullScreen(){
        stage.setScene(sceneprincipal);
        stage.setFullScreen(true);
    }

    public static void main(String[] args) {
        launch();
    }
}