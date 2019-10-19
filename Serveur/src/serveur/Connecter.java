/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serveur;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 *
 * @author DIAKITES_SOUMAÏLA
 */
public class Connecter {
     Connection con;
    public Connecter(){
        try {
          Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            System.err.println(e);
        }
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/banque","root","");
        } catch (Exception e) {
            System.err.println(e);
        }
        }
    public Connection obtenirConnexion(){
            return con;
    }
}
