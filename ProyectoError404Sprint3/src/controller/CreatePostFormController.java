/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Modelo.Conexion;
import static controller.PerfilController.info;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author tique
 */
public class CreatePostFormController implements Initializable {
    private Connection bd;
    private PostController info = new PostController();
    @FXML
    private Button btnInicioCrear;
    @FXML
    private Button btnPerfilCrear;
    @FXML
    private Button btnCrearCrear;
    @FXML
    private Button btnSalirCrear;
    @FXML
    private TextField txtNombreCrear;
    @FXML
    private TextArea txtTemaCrear;
    @FXML
    private Button btnLimpiarCrear;
    @FXML
    private Button btnEnviarCrear;
    @FXML
    private Button btnAmigosCrear;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bd = Conexion.getBd();
        try {
            ResultSet rs = bd.createStatement().executeQuery("SELECT ESACTIVO FROM USUARIO WHERE ID_USUARIO = "+info.getNoCredenciales());
            rs.next();
            if(!rs.getBoolean("ESACTIVO")){
                new TutorialController().setCualTutorial(3);
                TabNavegacion("Tutorial");
            }
           rs.close();
        } catch (IOException ex) {
            Logger.getLogger(PostController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PostController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    
    private void TabNavegacion(String direccion) throws IOException{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/"+direccion+".fxml"));

        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
    }
    
    @FXML
    private void EnviarPost(MouseEvent event) throws SQLException {
        PreparedStatement ps = bd.prepareStatement("INSERT INTO POST (ID_PERFIL,ID_FORO,CONTENIDO,TITULO,ENABLED) VALUES(?,?,?,?,?)");
        ps.setInt(1, info.getNoCredenciales());
        ps.setInt(2,info.getID_foroActual() );        
        ps.setString(3, txtTemaCrear.getText());
        ps.setString(4, txtNombreCrear.getText());
        ps.setBoolean(5, true);
        ps.executeUpdate();
        System.out.println("Agregado correctamente");
        
        Stage stage = new Stage();
        Node source = (Node) event.getSource();
        stage = (Stage) source.getScene().getWindow();
        
        ps.close();
        stage.close();
        
    }

    @FXML
    private void Inicio(MouseEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void Perfil(MouseEvent event) throws IOException {
        TabNavegacion("Perfil");
    }

    @FXML
    private void Amigos(MouseEvent event) throws IOException {
        TabNavegacion("Amigos");
    }

    @FXML
    private void Borrarcampos(MouseEvent event) {
        txtTemaCrear.setText("");
       txtNombreCrear.setText("");
    }
    
}
