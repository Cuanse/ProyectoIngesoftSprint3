/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

/**
 *
 * @author tique
 */
public class Comentario {
    private int ID_USUARIO;
    private String autor;
    private String contenido;
    public Comentario(String contenido){
        this.contenido = contenido;
    }
}
