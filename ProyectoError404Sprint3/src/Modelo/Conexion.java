/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author tique
 */
public class Conexion {
    private Alerta anuncio = new Alerta();
    private static Connection bd;
    
    public Conexion() throws SQLException, ClassNotFoundException {
        getConexion();
    }
    ;
    
    public static Connection getConexion() throws SQLException, ClassNotFoundException {
        //String url = "jdbc:mysql://proyecto.cdpsawbnswbf.us-east-1.rds.amazonaws.com:3306/Proyecto";
        
        String url= "jdbc:mysql://LocalHost:3306/test";        
        String user = "root";
        String clave = "";
        
        /*
        String url= "jdbc:mysql://sql10.freesqldatabase.com:3306/sql10579893";
        String user = "sql10579893";
        String clave = "D64xDBsxeD";*/
        
        /*try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Conexion.class.getName()).log(Level.SEVERE, null, ex);
        }*/
        bd = DriverManager.getConnection(url, user, clave);
        return bd;
    }

    public static Connection getBd() {
        return bd;
    }

    public static void setBd(Connection bd) {
        Conexion.bd = bd;
    }

    

    
}
