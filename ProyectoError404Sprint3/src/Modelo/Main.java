/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import controller.MainViewController;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author tique
 */
public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException, SQLException, ClassNotFoundException {
        System.out.println("Iniciando...");
        try {
            Conexion.getConexion();            
            Parent root = FXMLLoader.load(getClass().getResource("/Vista/MainView.fxml"));
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException | ClassNotFoundException | SQLException ex) {
            System.out.println("Servicio no disponible...");
            new Alerta().Error("Falta BD");
        }

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
