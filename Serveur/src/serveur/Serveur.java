/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package serveur;

import java.rmi.Naming;
import java.rmi.registry.LocateRegistry;
import rmi.ImplementationServices;

/**
 *
 * @author User
 */
public class Serveur {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       try {
            //On démarre l'annuaire
            LocateRegistry.createRegistry(1099);
            //On crée l'objet distant
            ImplementationServices serv = new ImplementationServices();
            //Afficher le références
            System.out.println(serv.toString());
            
            //Publier les références dans l'annuaire
            Naming.rebind("rmi://localhost:1099/BANQUE", serv);        
            
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
