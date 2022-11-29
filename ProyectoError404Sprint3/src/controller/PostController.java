/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Modelo.Alerta;
import Modelo.Conexion;
import Modelo.Foro;
import Modelo.Post;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author tique
 */
public class PostController implements Initializable {

    private Connection bd;
    private static Foro foros[];
    private Alerta alarma = new Alerta();
    private static int foroActual = 0; // Ojo NO es el Id_FORO , sino el foro en el arreglo foros
    private static int ID_foroActual;
    private static int pagActual = 1;
    private static int postActual = 0;
    private static int ID_postActual;
    static SignInFormController info = new SignInFormController();
    private static int noCredenciales = info.getNoCredenciales();

    private int ordenamiento = 0;
    @FXML
    private Button btnAmigosPost;

    public int getID_postActual() {
        return ID_postActual;
    }

    public static void setID_postActual(int ID_postActual) {
        PostController.ID_postActual = ID_postActual;
    }

    public int getID_foroActual() {
        return ID_foroActual;
    }

    public static void setID_foroActual(int ID_foroActual) {
        PostController.ID_foroActual = ID_foroActual;
    }

    public int getPostActual() {
        return postActual;
    }

    public static void setPostActual(int postActual) {
        PostController.postActual = postActual;
    }

    public int getPagActual() {
        return pagActual;
    }

    public static void setPagActual(int pagActual) {
        PostController.pagActual = pagActual;
    }

    public Foro[] getForos() {
        return foros;
    }

    public static void setForos(Foro[] foros) {
        PostController.foros = foros;
    }

    public int getNoCredenciales() {
        return noCredenciales;
    }

    public void setNoCredenciales(int noCredenciales) {
        this.noCredenciales = noCredenciales;
    }

    public int getForoActual() {
        return foroActual;
    }

    public void setForoActual(int foroActual) {
        this.foroActual = foroActual;
    }

    @FXML
    private Button btnInicioPost;
    @FXML
    private Button btnPerfilPost;
    @FXML
    private Button btnCrearPost;
    @FXML
    private Button btnSalirPost;
    @FXML
    private Button btnPref2;
    @FXML
    private Button btnPrevPost;
    @FXML
    private Button btnNextPost;
    private Button btnBitacora;
    @FXML
    private Label lblTittlePost1;
    @FXML
    private Label lblTextPost1;
    @FXML
    private Label lblTittlePost2;
    @FXML
    private Label lblTextPost2;
    @FXML
    private VBox vBox1;
    @FXML
    private GridPane GridPost;
    @FXML
    private Pane PanePost;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        bd = Conexion.getBd();
        /* //probando unas cosas nada mas
        Stage stage = new Stage();
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(PanePost.getScene());
        stage.show*/

        //Cargar los primeros Posts de la primera página
        LoadPosts(foroActual, (pagActual - 1) * 2);
        ID_foroActual = foros[foroActual].getID();
        try {
            ResultSet rs = bd.createStatement().executeQuery("SELECT ESACTIVO FROM USUARIO WHERE ID_USUARIO = " + noCredenciales);
            rs.next();
            if (!rs.getBoolean("ESACTIVO")) {
                new TutorialController().setCualTutorial(0);
                TabNavegacion("Tutorial");
            }
            rs.close();
        } catch (IOException ex) {
            Logger.getLogger(PostController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(PostController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }


    private void LoadPosts(int foro, int page) { // Page es un int medio raro, porque debe ser un numero de ((Nopágina - 1)*2) para saber cual par de posts debe cargar
        int n = 3;
        //Código cuando tengamos bd SQL para contar cuantos foros existen
        ResultSet rs = null; // tocaba inicializarla en algo por los posibles errores
        try {
            rs = bd.createStatement().executeQuery("SELECT COUNT(*) FROM FORO");
            rs.next();
            n = rs.getInt("COUNT(*)"); // Esto es solo convertir el result set a int
            rs = bd.createStatement().executeQuery("SELECT * FROM FORO");
        } catch (SQLException ex) {
            Logger.getLogger(PostController.class.getName()).log(Level.SEVERE, null, ex);
        }
        foros = new Foro[n];

        int i = 0; // contador que recorre los foros.
        try {
            /*Tecnicamente estamos cargando TODOS los foros y TODOS los posts que contienen, lo cual no es optimo pero que importa*/
            while (rs.next()) {
                foros[i] = new Foro(rs.getInt("ID_FORO"), rs.getString("Nombre"), rs.getString("Categoria"));
                // hice este switch case a la carrera, perdona el desastre,m ni siquiera hace lo suyo correctamente, solo debe alternar los psots para que se vea diferente
                ResultSet rs2 = bd.createStatement().executeQuery("SELECT * FROM POST WHERE ENABLED = true and ID_FORO = " + rs.getInt("ID_FORO") + " ORDER BY ID_POST DESC  LIMIT 10"); // quizá es getInt y falta Order by Date en lugar de ID_post;
                switch (ordenamiento) {
                    case 0:
                        rs2 = bd.createStatement().executeQuery("SELECT * FROM POST WHERE ENABLED = true and ID_FORO = " + rs.getInt("ID_FORO") + " ORDER BY ID_POST DESC  LIMIT 10"); // quizá es getInt y falta Order by Date en lugar de ID_post
                        break;
                    case 1:
                        rs2 = bd.createStatement().executeQuery("SELECT * FROM POST WHERE ENABLED = true and ID_FORO = " + foros[foroActual].getID() + " ORDER BY TITULO DESC  LIMIT 10");
                        break;
                    case 2:
                        rs2 = bd.createStatement().executeQuery("SELECT * FROM POST WHERE ENABLED = true and ID_FORO = " + foros[foroActual].getID() + " ORDER BY ID_PERFIL LIMIT 10");
                        break;
                    case 3:
                        rs2 = bd.createStatement().executeQuery("SELECT * FROM POST WHERE ENABLED = true and ID_FORO = " + foros[foroActual].getID() + " ORDER BY ID_POST DESC  LIMIT 10");
                        break;
                }

                ResultSet rs3 = bd.createStatement().executeQuery("SELECT COUNT(*) from (SELECT * FROM POST WHERE ENABLED = true and ID_FORO = " + rs.getInt("ID_FORO") + " ORDER BY ID_POST DESC  LIMIT 10" + ") as SUBQUERY");
                rs3.next();
                n = rs3.getInt("COUNT(*)");
                rs3.close();
                //n = 1;
                //System.out.println("SELECT COUNT(*) from (SELECT * FROM POST WHERE ID_FORO = " + rs.getString("ID_FORO")+") as SUBQUERY");
                //System.out.print(rs3.getInt("COUNT(*)"));
                if (n == 0) {
                    alarma.Information("No hay Posts en este foro: " + rs.getString("Nombre"));
                } else {
                    Post posts[] = new Post[10];
                    // Ingresamos los posts máximo 10
                    for (int j = 0; j < 10; j++) {
                        posts[j] = new Post();
                        if (rs2.next()) {
                            rs3 = bd.createStatement().executeQuery("SELECT * FROM USUARIO WHERE ID_USUARIO = " + rs2.getInt("ID_PERFIL"));
                            rs3.next();
                            posts[j].setID_POST(rs2.getInt("ID_POST"));
                            posts[j].setTitulo(rs2.getString("TITULO"));
                            posts[j].setAutor(/*"Autor-" +*/rs3.getString("NOMBREUSUARIO"));
                            posts[j].setContenido(rs2.getString("CONTENIDO"));
                            posts[j].setCreacion(rs2.getTimestamp("CREADOPOST")); // todavia no sé como Diana lo puso en la bd tan raro pero ok
                        }
                    }
                    foros[i].setPosts(posts);
                }
                rs2.close();
                i++;
            }
            rs.close();
        } catch (SQLException ex) {
            Logger.getLogger(PostController.class.getName()).log(Level.SEVERE, null, ex);
        }

        int cant = foros[foro].getPosts().length;
        if (cant == 0) {
            alarma.Information("Este foro no tiene Posts");
            lblTittlePost1.setText("Blank");
            lblTextPost1.setText("");
        } else if (cant == 1) {
            lblTittlePost1.setText(foros[foro].getPosts()[page].getTitulo());
            lblTextPost1.setText(foros[foro].getPosts()[page].getContenido());
        } else {
            lblTittlePost1.setText(foros[foro].getPosts()[page].getTitulo()); // En cada pagina caben 2 posts, pero si no hay mas de 2 posts toca omitir codigo.
            lblTittlePost2.setText(foros[foro].getPosts()[page + 1].getTitulo());

            lblTextPost1.setText(foros[foro].getPosts()[page].getContenido());
            lblTextPost2.setText(foros[foro].getPosts()[page + 1].getContenido());
        }
    }

    private void ReadPost(int posicion) throws IOException {
        postActual = posicion;
        System.out.print(foros[foroActual].getPosts()[((pagActual - 1) * 2) + posicion].getID_POST() + foros[foroActual].getPosts()[((pagActual - 1) * 2) + posicion].getTitulo());
        ID_postActual = foros[foroActual].getPosts()[((pagActual - 1) * 2) + posicion].getID_POST();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/PostsForm.fxml"));

        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
        LoadPosts(foroActual, (pagActual - 1) * 2);
    }

    private void TabNavegacion(String direccion) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/" + direccion + ".fxml"));

        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();

    }

    @FXML
    private void Vbox1Clicked(MouseEvent event) throws IOException {
        ReadPost(0);
    }

    @FXML
    private void verPerfil(MouseEvent event) throws IOException {
        TabNavegacion("Perfil");
    }

    @FXML
    private void CrearPost(MouseEvent event) throws IOException {
        TabNavegacion("CreatePostForm");
        alarma.Information("Gracias Por la publicación");
        LoadPosts(foroActual, (pagActual - 1) * 2);
    }

    @FXML
    private void Vbox2Clicked(MouseEvent event) throws IOException {
        ReadPost(1);
    }

    @FXML
    private void Prev(MouseEvent event) {
        if (pagActual == 1) {
            pagActual = 5;
        } else {
            pagActual = (pagActual - 1) % 6;
        }
        LoadPosts(foroActual, (pagActual - 1) * 2);
    }

    @FXML
    private void Next(MouseEvent event) {
        if (pagActual == 5) {
            pagActual = 1;
        } else {
            pagActual = (pagActual + 1) % 6;
        }
        LoadPosts(foroActual, (pagActual - 1) * 2);
    }

    @FXML
    private void pag1(MouseEvent event) {
        pagActual = 1;
        LoadPosts(foroActual, (pagActual - 1) * 2);
    }

    @FXML
    private void pag2(MouseEvent event) {
        pagActual = 2;
        LoadPosts(foroActual, (pagActual - 1) * 2);
    }

    @FXML
    private void pag3(MouseEvent event) {
        pagActual = 3;
        LoadPosts(foroActual, (pagActual - 1) * 2);
    }

    @FXML
    private void pag4(MouseEvent event) {
        pagActual = 4;
        LoadPosts(foroActual, (pagActual - 1) * 2);
    }

    @FXML
    private void pag5(MouseEvent event) {
        pagActual = 5;
        LoadPosts(foroActual, (pagActual - 1) * 2);
    }

    @FXML
    private void Amigos(MouseEvent event) throws IOException {
        TabNavegacion("Amigos");
    }

    @FXML
    private void PostsDeportesForo(MouseEvent event) {
    }

    @FXML
    private void PostsGuardados(MouseEvent event) {
    }

    @FXML
    private void OrderMasLikes(MouseEvent event) throws SQLException {
        ordenamiento = 1;
        LoadPosts(foroActual, (pagActual - 1) * 2);
    }

    @FXML
    private void OrderAdminPosts(MouseEvent event) throws SQLException {
        ordenamiento = 2;
        LoadPosts(foroActual, (pagActual - 1) * 2);
    }

    @FXML
    private void OrderRecientes(MouseEvent event) throws SQLException {
        ordenamiento = 0;
        LoadPosts(foroActual, (pagActual - 1) * 2);
    }

    @FXML
    private void CERRARSESION(MouseEvent event) throws IOException {

        alarma.Information("Cerrando Sesion...");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/" + "MainView" + ".fxml"));

        Parent root = loader.load();

        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.show();

        Node source = (Node) event.getSource();
        stage = (Stage) source.getScene().getWindow();
        stage.close();
    }


}
