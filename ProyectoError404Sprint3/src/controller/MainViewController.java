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
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author tique
 */
public class MainViewController implements Initializable {

    private Connection bd;
    @FXML
    private Button btnSignIn;
    @FXML
    private Button btnSignUp;
    @FXML
    private StackPane containerForm;
    @FXML
    private GridPane GridMainView;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        bd = Conexion.getBd();
    }

    @FXML
    private void SignIn(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/SignInForm.fxml"));

        Parent root = loader.load();
        SignInFormController formulario = loader.getController();

        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait(); // Espere a que sign in dé una respuesta
        if (SignInFormController.isAccesoConcedido()) {
            // Iniciamos la vista Post (falta mandar las credenciales de inicio de sesion)
            loader = new FXMLLoader(getClass().getResource("/Vista/Post.fxml"));
            root = loader.load();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
            // Cerramos la window de MainView
            Node source = (Node) event.getSource();
            stage = (Stage) source.getScene().getWindow();
            stage.close();
        }

    }

    @FXML
    private void SignUp(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Vista/SignUpForm.fxml"));

        Parent root = loader.load();
        SignUpFormController formulario = loader.getController();

        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
    }

    public Connection getBd() {
        return bd;
    }

    public void setBd(Connection bd) {
        this.bd = bd;
    }

}
