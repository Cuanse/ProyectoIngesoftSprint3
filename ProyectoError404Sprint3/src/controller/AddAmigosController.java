/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Modelo.Alerta;
import Modelo.Conexion;
import static controller.AmigosController.info;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author tique
 */
public class AddAmigosController implements Initializable {
private Connection bd;
    @FXML
    private TextField txtfieldNombreAdd;
    @FXML
    private ImageView btnCheckAdd;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bd = Conexion.getBd();
    }

    @FXML
    private void AñadirAmigo(MouseEvent event) throws SQLException {
        if (!txtfieldNombreAdd.getText().equals("")) {
            ResultSet rs = bd.createStatement().executeQuery( "SELECT ID_USUARIO FROM USUARIO where NOMBREUSUARIO = \"" + txtfieldNombreAdd.getText()+"\"");
            
            if (rs.next()) {
                PreparedStatement ps = bd.prepareStatement("INSERT INTO AMIGO (ID_USUARIOM,ID_USUARIOR) values (?,?)");
                ps.setInt(1, info.getNoCredenciales());
                ps.setInt(2, rs.getInt("ID_USUARIO"));
                ps.executeUpdate();
                new Alerta().Information("Agregado tu amigo correctamente");
            } else {
                new Alerta().Error("Aquella persona no existe");
            }     
            //cerramos
            Stage stage = new Stage();
            Node source = (Node) event.getSource();
            stage = (Stage) source.getScene().getWindow();
            stage.close();
        }else{
            new Alerta().Error("Escribe el nombre de tu amigo");
        }
    }

}
