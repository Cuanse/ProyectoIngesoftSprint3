/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Modelo.Alerta;
import Modelo.Conexion;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.TextArea;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author tique
 */
public class CommentPostController implements Initializable {

    private Connection bd;
    PostController info = new PostController();
    @FXML
    private TextArea txtComentario;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bd = Conexion.getBd();
    }

    @FXML
    private void BorrarComentario(MouseEvent event) {
        txtComentario.setText("");
    }

    @FXML
    private void EnviarComentario(MouseEvent event) throws SQLException {
        mandarComentario(event, txtComentario.getText());
    }

    @FXML
    private void presionaEnter(KeyEvent event) throws SQLException {
        if (event.getCode() == KeyCode.ENTER) {
            mandarComentario(event, txtComentario.getText());
        }
    }

    private void mandarComentario(Event event, String contenido) throws SQLException {
        if (!txtComentario.getText().equals("")) {
            PreparedStatement ps = bd.prepareStatement("INSERT INTO COMENTARIO (ID_USUARIO,ID_POST,CONTENIDO) values (?,?,?)");
            ps.setInt(1, info.getNoCredenciales()); // enviamos ID_USUARIO
            ps.setInt(2, info.getID_postActual()); // enviamos ID_POST
            ps.setString(3, contenido);
            ps.executeUpdate();

            Node source = (Node) event.getSource();
            Stage stage = (Stage) source.getScene().getWindow();
            new Alerta().Information("Comentario publicado con exito");
            
            ps.close();
            stage.close();           
            
        } else {
            new Alerta().Information("Porfavor escribe algo antes de enviar");
        }
    }
}
