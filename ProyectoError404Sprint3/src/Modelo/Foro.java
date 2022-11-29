/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.lang.reflect.Array;

/**
 *
 * @author tique
 */
public class Foro {
    
    private Post posts[] = new Post[0]; // Solo guardamos 10 porque son los Posts más recientes
    private int ID;
    private String nombre;
    private String categoria;
    
    public Foro(int ID,String nombre, String categoria){
        this.ID = ID;
        this.nombre = nombre;
        this.categoria = categoria;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
    
    public Post[] getPosts() {
        return posts;
    }

    public void setPosts(Post[] posts) {
        this.posts = posts;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    
    
}
