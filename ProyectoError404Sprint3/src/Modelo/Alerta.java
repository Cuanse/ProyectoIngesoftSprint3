/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import javafx.scene.control.Alert;

/**
 *
 * @author tique
 */
public class Alerta {
    public Alerta(){
        
    }
    public Alert Error(String contenido){
           Alert alerta = new Alert(Alert.AlertType.ERROR);
           alerta.setTitle("Oops");
           alerta.setContentText(contenido);
           alerta.setHeaderText("Algo salió mal");
           alerta.showAndWait();
           return alerta;
    }
    
    public Alert Information(String contenido){
           Alert alerta = new Alert(Alert.AlertType.INFORMATION);
           alerta.setTitle("");
           alerta.setContentText(contenido);
           alerta.setHeaderText("Verificacion");
           alerta.showAndWait();
           return alerta;
    }
}
