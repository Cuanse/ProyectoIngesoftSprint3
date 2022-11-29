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
import java.sql.Timestamp;
import java.util.Date;
public class Post {
    private int ID_POST = 0;
    private String autor = "Nadie";
    private String titulo = "Blank";
    private String contenido = "Orem Implus es la cosa de relleno para imprentas en los documentos...";
    private Timestamp creacion = Timestamp.valueOf("2020-12-12 01:24:23");

    public int getID_POST() {
        return ID_POST;
    }

    public void setID_POST(int ID_POST) {
        this.ID_POST = ID_POST;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }
    
    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public Timestamp getCreacion() {
        return creacion;
    }

    public void setCreacion(Timestamp creacion) {
        this.creacion = (Timestamp) creacion;
    }

}
