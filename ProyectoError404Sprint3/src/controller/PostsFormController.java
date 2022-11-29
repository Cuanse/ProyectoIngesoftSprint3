/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Modelo.Alerta;
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
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author tique
 */
public class PostsFormController implements Initializable {
    private Connection bd;
    PostController info = new PostController();
    @FXML
    private Text txtTitle;
    @FXML
    private Text txtContent;
    @FXML
    private Label lblDate;
    @FXML
    private Label lblAutor;
    @FXML
    private ImageView likeImg;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bd = Conexion.getBd();
        System.out.println("bd Guardada bien");    
        int n = info.getPostActual();
        txtTitle.setText(info.getForos()[info.getForoActual()].getPosts()[((info.getPagActual()-1)*2)+n].getTitulo());
        txtContent.setText(info.getForos()[info.getForoActual()].getPosts()[((info.getPagActual()-1)*2)+n].getContenido());
        lblDate.setText(info.getForos()[info.getForoActual()].getPosts()[((info.getPagActual()-1)*2)+n].getCreacion().toString());
        lblAutor.setText(info.getForos()[info.getForoActual()].getPosts()[((info.getPagActual()-1)*2)+n].getAutor());
        
        try {
            ResultSet rs = bd.createStatement().executeQuery("SELECT ESACTIVO FROM USUARIO WHERE ID_USUARIO = "+info.getNoCredenciales());
            rs.next();
            if(!rs.getBoolean("ESACTIVO")){
                new TutorialController().setCualTutorial(2);
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
    private void BorrarPost(MouseEvent event) throws SQLException {
        ResultSet rs = bd.createStatement().executeQuery("SELECT * from USUARIO WHERE ID_USUARIO = " + info.getNoCredenciales());
        rs.next();
        if(rs.getBoolean("ESADMIN") | rs.getString("NOMBREUSUARIO").equals(lblAutor.getText()) ){
            PreparedStatement ps = bd.prepareStatement("UPDATE POST SET ENABLED = false WHERE ID_POST = " + info.getForos()[info.getForoActual()].getPosts()[((info.getPagActual()-1)*2)+info.getPostActual()].getID_POST());
            ps.executeUpdate();
            new Alerta().Information("Eliminado Correctamente");
            // Lo siguiente es para salir automaticamente del post
            Stage stage = new Stage();
            Node source = (Node) event.getSource();
            stage = (Stage) source.getScene().getWindow();
            
            ps.close();
            stage.close();
        }else{
            new Alerta().Information("NO eres Admin o el dueño del post");            
        }
        rs.close();
    }

    @FXML
    private void leaveComment(MouseEvent event) throws IOException {
        TabNavegacion("comentariosPost");
    }

    @FXML
    private void likeBtn(MouseEvent event) throws SQLException {        
        PreparedStatement ps = bd.prepareStatement("INSERT INTO LIKES (ID_USUARIO,ID_POST) values (?,?)");
        ps.setInt(1, info.getNoCredenciales()); // enviamos ID_USUARIO
        ps.setInt(2, info.getID_postActual()); // enviamos ID_POST
        ps.executeUpdate();
        Image image=new Image("/img/icons8-facebook-like-skin-type-3-48-iluminated.png");
        likeImg.setImage(image);
        ps.close();
    }

    @FXML
    private void escribirComentario(MouseEvent event) throws IOException {
        TabNavegacion("CommentPost");
    }

    @FXML
    private void Inicio(MouseEvent event) {
        Stage stage = new Stage();
        Node source = (Node) event.getSource();
        stage = (Stage) source.getScene().getWindow();
        stage.close();
        
    }

    @FXML
    private void Perfil(MouseEvent event) throws IOException {
        TabNavegacion("Perfil");
        
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
