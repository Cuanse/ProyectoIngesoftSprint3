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
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author tique
 */
public class AjustesPerfilController implements Initializable {

    private Connection bd;
    private Alerta alarma = new Alerta();
    PostController info = new PostController();
    @FXML
    private Button btnAjustesPerfil;
    @FXML
    private Label lblBioPerfil;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextArea txtBiografia;
    @FXML
    private TextField txtTelefono;
    @FXML
    private TextField txtDireccion;
    @FXML
    private TextField txtGenero;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bd = Conexion.getBd();
    }

    @FXML
    private void guardarPerfil(MouseEvent event) throws SQLException {

        //PreparedStatement ps = bd.prepareStatement("INSERT INTO PERFIL (EMAILPUBLICO,BIOGRAFIA,TELEFONO,DIRECCION,GENERO) VALUES(?,?,?,?,?)");
        ResultSet rs = bd.createStatement().executeQuery("SELECT ID_PERFIL from USUARIO WHERE ID_USUARIO = " + info.getNoCredenciales());
        rs.next();
        if (rs.getInt("ID_PERFIL") == 0) {
            PreparedStatement ps = bd.prepareStatement("INSERT INTO PERFIL (EMAILPUBLICO,BIOGRAFIA,TELEFONO,DIRECCION,GENERO) VALUES(?,?,?,?,?)");
            if (!txtEmail.getText().equals("")) {
                ps.setString(1, txtEmail.getText());
            } else {
                ps.setString(1, "--");
            }

            if (!txtBiografia.getText().equals("")) {
                ps.setString(2, txtBiografia.getText());
            } else {
                ps.setString(2, "--");
            }

            if (!txtTelefono.getText().equals("")) {
                ps.setString(3, txtTelefono.getText());
            } else {
                ps.setString(3, "--");
            }

            if (!txtDireccion.getText().equals("")) {
                ps.setString(4, txtDireccion.getText());
            } else {
                ps.setString(4, "--");
            }

            if (!txtGenero.getText().equals("")) {
                ps.setString(5, txtGenero.getText());
            } else {
                ps.setString(5, "-");
            }
            ps.executeUpdate();
            ps.close();

            rs = bd.createStatement().executeQuery("SELECT ID_PERFIL from PERFIL ORDER BY ID_PERFIL DESC");
            rs.next(); //por defecto diremos que es el último ingresado, igual no es como que varios usuarios vayan a llenar datos al mismo tiempo
            PreparedStatement ps1 = bd.prepareStatement("UPDATE USUARIO SET ID_PERFIL = ? , ESACTIVO = true WHERE ID_USUARIO = ?");
            ps1.setString(1, rs.getString("ID_PERFIL"));
            ps1.setInt(2, info.getNoCredenciales());
            ps1.executeUpdate();

            ps1.close();
        } else {
            int ID = rs.getInt("ID_PERFIL");
            PreparedStatement ps = bd.prepareStatement("UPDATE PERFIL SET EMAILPUBLICO = ?,BIOGRAFIA = ?,TELEFONO = ?,DIRECCION = ?,GENERO = ? WHERE ID_PERFIL =" + rs.getInt("ID_PERFIL"));
            rs = bd.createStatement().executeQuery("SELECT * from PERFIL WHERE ID_PERFIL =" + ID);
            rs.next();
            if (!txtEmail.getText().equals("")) {
                ps.setString(1, txtEmail.getText());
            } else {
                ps.setString(1, rs.getString("EMAILPUBLICO"));
            }

            if (!txtBiografia.getText().equals("")) {
                ps.setString(2, txtBiografia.getText());
            } else {
                ps.setString(2, rs.getString("BIOGRAFIA"));
            }

            if (!txtTelefono.getText().equals("")) {
                ps.setString(3, txtTelefono.getText());
            } else {
                ps.setString(3, rs.getString("TELEFONO"));
            }

            if (!txtDireccion.getText().equals("")) {
                ps.setString(4, txtDireccion.getText());
            } else {
                ps.setString(4, rs.getString("DIRECCION"));
            }

            if (!txtGenero.getText().equals("")) {
                ps.setString(5, txtGenero.getText());
            } else {
                ps.setString(5, rs.getString("GENERO"));
            }
            ps.executeUpdate();

            ps.close();
        }

        alarma.Information("Datos actualizados con exito");
        Stage stage = new Stage();
        Node source = (Node) event.getSource();
        stage = (Stage) source.getScene().getWindow();

        rs.close();
        stage.close();

    }

}
