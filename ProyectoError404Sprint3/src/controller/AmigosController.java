/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

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
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author tique
 */
public class AmigosController implements Initializable {

    private Connection bd;
    static SignInFormController info = new SignInFormController();
    private Pane Panes[] = new Pane[5]; // Los Panes 
    private Label Labels[] = new Label[Panes.length]; // Los Labels de los Panes
    private int Amigos_ID[] = new int[5]; // Guardar el ID de tus 5 amigos
    private PerfilController perfil = new PerfilController();
    @FXML
    private GridPane GridPost;
    @FXML
    private Button btnInicioAmigos;
    @FXML
    private Button btnPerfilAmigos;
    @FXML
    private Button btnCrearAmigos;
    @FXML
    private Button btnAmigosAmigos;
    @FXML
    private Button btnSalirAmigos;
    @FXML
    private ImageView btnAddAmigos;
    @FXML
    private Button btnPrevAmigos;
    @FXML
    private Button btnNextAmigos;
    @FXML
    private Pane PaneAmigos;
    @FXML
    private Label lblNombreAmigos1;
    @FXML
    private Pane PaneAmigos1;
    @FXML
    private Label lblNombreAmigos11;
    @FXML
    private Pane PaneAmigos2;
    @FXML
    private Label lblNombreAmigos12;
    @FXML
    private Pane PaneAmigos3;
    @FXML
    private Label lblNombreAmigos13;
    @FXML
    private Pane PaneAmigos4;
    @FXML
    private Label lblNombreAmigos14;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bd = Conexion.getBd();
        Panes[0] = PaneAmigos;
        Panes[1] = PaneAmigos1;
        Panes[2] = PaneAmigos2;
        Panes[3] = PaneAmigos3;
        Panes[4] = PaneAmigos4;
        // Yo sé que el codigo es una mierda pero no recuerdo como hacerlo más bonito
        Labels[0] = lblNombreAmigos1;
        Labels[1] = lblNombreAmigos11;
        Labels[2] = lblNombreAmigos12;
        Labels[3] = lblNombreAmigos13;
        Labels[4] = lblNombreAmigos14;

        try {
            LeerAmigos();
        } catch (SQLException ex) {
            Logger.getLogger(AmigosController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void verPerfil(MouseEvent event) throws IOException {
        TabNavegacion("Perfil");
    }

    @FXML
    private void CrearPost(MouseEvent event) throws IOException {
        TabNavegacion("CreatePostForm");
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

    private void LeerAmigos() throws SQLException {
        ResultSet rs = bd.createStatement().executeQuery("SELECT COUNT(*) FROM AMIGO where ID_USUARIOM = " + info.getNoCredenciales());
        rs.next();
        int n = rs.getInt("COUNT(*)"); // Saber cuantos amigos tiene.
 /*OLVIDE poner next y prev para ver mas amigos de tu lista de amigos porque medio flojera*/
        if (n > Panes.length) { // pa msotrar solo 5 amigos
            n = Panes.length; 
        }

        rs = bd.createStatement().executeQuery("SELECT ID_USUARIOR FROM AMIGO where ID_USUARIOM = " + info.getNoCredenciales()); // elegir usuarios con solicitud
        rs.next();
        for (int i = 0; i < n; i++) { // tecnicamente solo leemos 5 amigos pero que me importa xd
            Amigos_ID[i] = rs.getInt("ID_USUARIOR");
            ResultSet rs2 = bd.createStatement().executeQuery("SELECT NOMBREUSUARIO FROM USUARIO where ID_USUARIO = " + Amigos_ID[i]); // buscar sus nombres
            Panes[i].setDisable(false);
            Panes[i].setVisible(true);
            rs2.next();
            Labels[i].setText(rs2.getString("NOMBREUSUARIO"));
            rs.next();
            rs2.close();
        }
        rs.close();

    }

    @FXML
    private void AddAmigo(MouseEvent event) throws IOException, SQLException {
        TabNavegacion("AddAmigos");
        LeerAmigos();
    }

    @FXML
    private void VerAmigo1(MouseEvent event) throws IOException {
        perfil.setNoPerfil(Amigos_ID[0]);
        TabNavegacion("Perfil");
    }

    @FXML
    private void VerAmigo2(MouseEvent event) throws IOException {
        perfil.setNoPerfil(Amigos_ID[1]);
        TabNavegacion("Perfil");
    }

    @FXML
    private void VerAmigo3(MouseEvent event) throws IOException {
        perfil.setNoPerfil(Amigos_ID[2]);
        TabNavegacion("Perfil");
    }

    @FXML
    private void VerAmigo4(MouseEvent event) throws IOException {
        perfil.setNoPerfil(Amigos_ID[3]);
        TabNavegacion("Perfil");
    }

    @FXML
    private void VerAmigo5(MouseEvent event) throws IOException {
        perfil.setNoPerfil(Amigos_ID[4]);
        TabNavegacion("Perfil");
    }

    @FXML
    private void Inicio(MouseEvent event) {
        Node source = (Node) event.getSource();
        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
