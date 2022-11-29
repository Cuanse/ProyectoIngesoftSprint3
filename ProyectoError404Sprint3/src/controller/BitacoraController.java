/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Modelo.Conexion;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.input.MouseEvent;

/**
 * FXML Controller class
 *
 * @author tique
 */
public class BitacoraController implements Initializable {
 private Connection bd;
    @FXML
    private ComboBox<String> comboboxTabla;
    @FXML
    private Button btnActualizar;
    @FXML
    private TableView<?> tabla;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bd = Conexion.getBd();
        /*Opciones del ComboBox*/
        ArrayList<String> Tablas = new ArrayList<>();
        
        Collections.addAll(Tablas, new String[]{
            "FORO",
            "POST",
            "USUARIO",
            "PERFIL",
            "LIKES",
            "COMENTARIO"
            
        });
        
        comboboxTabla.setItems(FXCollections.observableArrayList(Tablas));
    }    

    @FXML
    private void Actualizar(MouseEvent event) throws SQLException {
        ResultSet rs = bd.createStatement().executeQuery("SELECT * from BITACORA ORDER BY FECHA DESC WHERE TABLA = "+ comboboxTabla.getValue().toString());
        rs.next();
    }
    
}
