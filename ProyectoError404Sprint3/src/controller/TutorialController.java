/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author tique
 */
public class TutorialController implements Initializable {

    @FXML
    private ImageView imgImagenCentral;
    @FXML
    private Label lblDescripcion;
    @FXML
    private ImageView btnPage1;
    @FXML
    private ImageView btnPage2;
    @FXML
    private ImageView btnPage3;
    @FXML
    private ImageView btnPage4;
    @FXML
    private ImageView btnPage5;
    @FXML
    private ImageView btnPage6;

    private ImageView[] botones = new ImageView[6];
    private Image[] imagenes = new Image[6];
    private String[] descripciones = new String[6];
    private int diapositivaActual = 0;
    private static int cualTutorial;

    public int getCualTutorial() {
        return cualTutorial;
    }

    public void setCualTutorial(int cualTutorial) {
        this.cualTutorial = cualTutorial;
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        botones[0] = btnPage1;
        botones[1] = btnPage2;
        botones[2] = btnPage3;
        botones[3] = btnPage4;
        botones[4] = btnPage5;
        botones[5] = btnPage6;
        
        int i = 0;
        System.out.println(cualTutorial);
        switch(cualTutorial){
            case 0:
                imagenes[0] = new Image("/img/fotos_tutorial/mainview1.jpeg");
                imagenes[1] = new Image("/img/fotos_tutorial/mainview2.jpeg");
                imagenes[2] = new Image("/img/fotos_tutorial/mainview3.jpeg");
                imagenes[3] = new Image("/img/fotos_tutorial/mainview4.jpeg");
                break;
            case 1:
                imagenes[0] = new Image("/img/fotos_tutorial/perfil1.jpg");
                imagenes[1] = new Image("/img/fotos_tutorial/perfil2.jpg");
                imagenes[2] = new Image("/img/fotos_tutorial/perfil3.jpg");
                imagenes[3] = new Image("/img/fotos_tutorial/perfil4.jpg");
                break;
            case 2:
                imagenes[0] = new Image("/img/fotos_tutorial/postform1.jpg");
                imagenes[1] = new Image("/img/fotos_tutorial/postform2.jpg");
                imagenes[2] = new Image("/img/fotos_tutorial/postform3.jpg");
                imagenes[3] = new Image("/img/fotos_tutorial/postform4.jpg");
                break;
            case 3:
                imagenes[0] = new Image("/img/fotos_tutorial/createpost1.jpeg");
                imagenes[1] = new Image("/img/fotos_tutorial/createpost2.jpeg");
                imagenes[2] = new Image("/img/fotos_tutorial/createpost3.jpeg");
                imagenes[3] = new Image("/img/fotos_tutorial/createpost4.jpeg");
                break;
        }
        imgImagenCentral.setImage(imagenes[diapositivaActual]);
        lblDescripcion.setText(descripciones[diapositivaActual]);
    }

    private void Pasar() {
        Image image = new Image("/img/icons8-360-degrees-51.png"); // se coloca el boton morado
        botones[diapositivaActual].setImage(image);
        imgImagenCentral.setImage(imagenes[diapositivaActual]);
        lblDescripcion.setText(descripciones[diapositivaActual]);
    }

    @FXML
    private void Prev(MouseEvent event) {
        if (diapositivaActual != 0) {
            Image image = new Image("/img/icons8-360-degrees-50.png"); // volvemos a poner el boton de verde
            botones[diapositivaActual].setImage(image);

            diapositivaActual -= 1;
            Pasar();

        }
    }

    @FXML
    private void Next(MouseEvent event) {
        if (diapositivaActual != botones.length - 1) {
            diapositivaActual += 1;
            Pasar();
        } else {
            FinTutorial(event); // legar al final de los botones es terminar el tutorial
        }
    }

    private void FinTutorial(Event event) {
        /*INSERTAR CODIGO DE USUARIO ACTIVO update ESAVTIVO TRUe*/
        Stage stage = new Stage();
        Node source = (Node) event.getSource();
        stage = (Stage) source.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void Skip(MouseEvent event) {
        FinTutorial(event);
    }

}
