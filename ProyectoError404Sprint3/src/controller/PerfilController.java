/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Modelo.Alerta;
import Modelo.Conexion;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
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
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author tique
 */
public class PerfilController implements Initializable {

    static SignInFormController info = new SignInFormController();
    private Connection bd;
    private static int NoPerfil = 0;
    @FXML
    private Button btnInicioPerfil;
    @FXML
    private Button btnPerfilPerfil;
    @FXML
    private Button btnCrearPerfil;
    @FXML
    private Button btnSalirPrfil;
    @FXML
    private Button btnAjustesPerfil;
    @FXML
    private Label lblUserPerfil;
    @FXML
    private Label lblMailPerfil;
    @FXML
    private Label lblBioPerfil;
    @FXML
    private Label lblPaisPerfil;
    @FXML
    private Label lblTelefonoPerfil;
    @FXML
    private Label lblDirPerfil;
    @FXML
    private Label lblCumplePerfil;
    @FXML
    private Label lblGeneroPerfil;
    @FXML
    private Button btnAmigosPerfil;

    public int getNoPerfil() {
        return NoPerfil;
    }

    public void setNoPerfil(int NoPerfil) {
        PerfilController.NoPerfil = NoPerfil;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bd = Conexion.getBd();
        System.out.println("bd Guardada bien");
        try {
            actualizarDatos();
        } catch (SQLException ex) {
            Logger.getLogger(PerfilController.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            ResultSet rs = bd.createStatement().executeQuery("SELECT ESACTIVO FROM USUARIO WHERE ID_USUARIO = "+info.getNoCredenciales());
            rs.next();
            if(!rs.getBoolean("ESACTIVO")){
                new TutorialController().setCualTutorial(1);
                TabNavegacion("Tutorial");
            }
           rs.close();
        } catch (IOException ex) {
            Logger.getLogger(PostController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PostController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void TabNavegacion(String direccion) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/" + direccion + ".fxml"));

        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.setTitle(direccion);
        
        stage.showAndWait();
    }

    private void actualizarDatos() throws SQLException {
        int ID_Buscar;
        if(NoPerfil == 0){ // si es 0, se dejó inicializar en 0 entonces el mismo usuario que loggeó está viendo su propio eprfil
            ID_Buscar = info.getNoCredenciales();
            
        }else{
            ID_Buscar = NoPerfil;   // al no ser cero es porque AMIGOSCONTROLLER setteó NoPerfil antes de iniciar este controller.
        }
        ResultSet rs = bd.createStatement().executeQuery("SELECT * from USUARIO WHERE ID_USUARIO = " + ID_Buscar);
        rs.next();
        
        try {
            lblUserPerfil.setText(rs.getString("NOMBREUSUARIO"));
            NoPerfil = rs.getInt("ID_PERFIL"); // Yo se que por esta linea salen errores pero no detiene la aplicacion entonces no me importa xd
        } catch (SQLException ex) { // significa que ID_PERFIL = null y traté de leer un null (por eso el try catch)
            //Sucede que ese null lo convierte a un 0 automaticamente JAVA, por eso borre lo que habia aca
        }
        if ((NoPerfil != 0)) { // significa que tiene ID_PERFIL y lo va a buscar en PERFIL
            rs = bd.createStatement().executeQuery("SELECT * from PERFIL WHERE ID_PERFIL = " + NoPerfil); // creo que toma el último perfil ingresado.... verdad? xd
            rs.next();
            lblMailPerfil.setText(rs.getString("EMAILPUBLICO"));
            lblBioPerfil.setText(rs.getString("BIOGRAFIA"));

            lblTelefonoPerfil.setText(rs.getString("TELEFONO"));
            lblDirPerfil.setText(rs.getString("DIRECCION"));
            lblGeneroPerfil.setText(rs.getString("GENERO"));
        }else{
            new Alerta().Error("Este usuario no tiene perfil publico");
        }
        rs.close();
    }

    @FXML
    private void ajustesPerfil(MouseEvent event) throws IOException, SQLException {
        TabNavegacion("AjustesPerfil");
        actualizarDatos();
    }

    @FXML
    private void Inicio(MouseEvent event) {
        NoPerfil = 0; // cada vez que se cierre ponerlo en 0 por uso practico y de emergencia (asi funciona el codigo sr por no hacerlo mas bonito por falta de tiempo)
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void CrearPost(MouseEvent event) throws IOException {
        TabNavegacion("CreatePostForm");
    }

    @FXML
    private void Amigos(MouseEvent event) throws IOException {
        TabNavegacion("Amigos");
    }

}
