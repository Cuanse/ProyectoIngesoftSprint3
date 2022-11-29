/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Modelo.Alerta;
import Modelo.Comentario;
import Modelo.Conexion;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author tique
 */
public class ComentariosPostController implements Initializable {

    private Connection bd;
    PostController info = new PostController();
    //ArrayList<Comentario> coments = new ArrayList<>();
    ArrayList<String> coments = new ArrayList<>();
    ObservableList<Comentario> comentarios;
    
    //Me rendí con el codigo de tableView, es muy extraño y no funciona, pero no lo borro por si un dia quiero seguir sufriendo
    /*private TableView<Comentario> tablaComentarios = new TableView<Comentario>();
    private TableColumn<Comentario, String> TableColumn = new TableColumn<Comentario,String>("Comentarios");*/
    @FXML
    private Label lblComentarios;


        /**
         * Initializes the controller class.
         */
   
        public void initialize(URL url, ResourceBundle rb) {
            ResultSet rs;
            bd = Conexion.getBd();
            try {
                rs = bd.createStatement().executeQuery("SELECT * from COMENTARIO WHERE ID_POST = " + info.getID_postActual());
                if (rs.next()) {
                    ResultSet rs2;
                    //ArrayList Comentarios = new ArrayList();
                    lblComentarios.setText("");
                    lblComentarios.setAlignment(Pos.TOP_LEFT);
                    do {
                        rs2 = bd.createStatement().executeQuery("Select * from USUARIO where ID_USUARIO = " + rs.getInt("ID_USUARIO")); // buscamos los nombres de cada usuario que comentó el post
                        rs2.next();
                        System.out.println(rs.getString("CONTENIDO"));
                        coments.add(rs2.getString("NOMBREUSUARIO") + " dice: " + rs.getString("CONTENIDO"));
                        lblComentarios.setText(lblComentarios.getText()+rs2.getString("NOMBREUSUARIO") + " dice: " + rs.getString("CONTENIDO")+"\n");
                        //coments.add(new Comentario(rs.getString("CONTENIDO")));
                        

                    } while (rs.next());
                    /*tablaComentarios.setItems(FXCollections.observableArrayList(coments));
                    TableColumn.setCellValueFactory(new PropertyValueFactory("contenido"));
                    tablaComentarios.getColumns().setAll(TableColumn);*/
                    
                    rs.close();
                    rs2.close();
                } else {
                    new Alerta().Information("No hay comentarios, sé el primero!!");
                }

            } catch (SQLException ex) {
                Logger.getLogger(ComentariosPostController.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }
