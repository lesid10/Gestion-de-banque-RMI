/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import serveur.Connecter;

/**
 *
 * @author DIAKITE SOUMAILA
 */
public class ImplementationServices extends UnicastRemoteObject implements IAgent,IClient,IOperation{
    Connecter conn = new Connecter();
    Statement stm, stm2,stm3;
    ResultSet Rs;
    
    public ImplementationServices() throws RemoteException {
        super();   
    }
    public String AdaptToSqlString(String str)
    {
        str=str.replace("'", "''");
        return "'"+str+"'";
    }
    
    public boolean connectAgent(String nom,String mdp)throws RemoteException{
        Boolean trouve = false;
        try {
            stm = conn.obtenirConnexion().createStatement();
            ResultSet res = stm.executeQuery("SELECT login,mdp FROM agentbanque "
                        + " WHERE login = "+AdaptToSqlString(nom)+" AND mdp="+AdaptToSqlString(mdp));
            while(res.next()){
                trouve = true;
            }
        }
        catch (Exception e) {
             JOptionPane.showMessageDialog(null,"::[ Erreur de connexion ,vérifier votre requête ou\n informer votre administrateur de données..=>"+e+" ]","A propos",JOptionPane.INFORMATION_MESSAGE);
        }
        return trouve;
    }
    
    public void Ajouter(String nom, String prenom, String adresse, String telephone,String typeCompte,String numCpte, String mdpCpte, double soldeInit) throws RemoteException{
        try {
            //On vérifie si le compte existe déjà
            //On recupère IdClient
            ResultSet res = stm.executeQuery("SELECT idClient FROM client "
                        + " WHERE nomClient = "+AdaptToSqlString(nom)+" AND prenomClient="+AdaptToSqlString(prenom)
                        + "AND adresseClient="+AdaptToSqlString(adresse)
                        + " AND telephoneClient="+AdaptToSqlString(telephone));
            int taille = 0;
            while(res.next()){taille++;}
            if(taille !=0){
                JOptionPane.showMessageDialog(null,"Ce compte existe déjà ","AJOUT COMPTE",JOptionPane.INFORMATION_MESSAGE);
            }
            else{
                //On l'insère dans la BD
                 String requete1="INSERT INTO client(idClient, nomClient, prenomClient,adresseClient,telephoneClient)"
                + "VALUES(NULL,"+AdaptToSqlString(nom)+","+AdaptToSqlString(prenom)+","
                +AdaptToSqlString(adresse)+","+AdaptToSqlString(telephone)+")"; 
                
                stm = conn.obtenirConnexion().createStatement();
                stm.executeUpdate(requete1);
                //On recupère IdClient
                ResultSet res2 = stm.executeQuery("SELECT idClient FROM client "
                        + " WHERE nomClient = "+AdaptToSqlString(nom)+" AND prenomClient="+AdaptToSqlString(prenom)
                        + "AND adresseClient="+AdaptToSqlString(adresse)
                        + " AND telephoneClient="+AdaptToSqlString(telephone));
                
                //int id = Integer.valueOf(res2.getString("idClient")).intValue();
                res2.next();
                 //Créer compte
                 String requete2="INSERT INTO compte(idCompte, numCompte, idClient,typeCompte,soldeCompte,dateCreationCompte,mdpCompte)"
                + "VALUES(NULL,"+AdaptToSqlString(numCpte)+","+res2.getString("idClient")+","+AdaptToSqlString(typeCompte)+","
                +soldeInit+",NOW(),"+AdaptToSqlString(mdpCpte)+")"; 

                stm.executeUpdate(requete2);
                
            }

        } catch (SQLException e) {trateur"
                        + " de données..=>"+e+" ]","Problème de connexion à la base de données",JOptionPane.WARNING_MESSAGE);
        }
                JOptionPane.showMessageDialog(null,"::[ Erreur d'insertion dans la base de données ,vérifier votre requête ou\n informer votre adminis
    }
    
    public boolean connectClient(String  numCpte,String mdp)throws RemoteException{
         Boolean trouve = false;
        try {
            stm = conn.obtenirConnexion().createStatement();
   
            ResultSet res = stm.executeQuery("SELECT numCompte,mdpCompte FROM compte "
                        + " WHERE numCompte = "+AdaptToSqlString(numCpte)+" AND mdpCompte="+AdaptToSqlString(mdp));
            
            while(res.next()){
                trouve = true;
            }
        }
        catch (Exception e) {
             JOptionPane.showMessageDialog(null,"::[ Erreur de connexion ,vérifier votre requête ou\n informer votre administrateur de données..=>"+e+" ]","A propos",JOptionPane.INFORMATION_MESSAGE);
        }
        return trouve;
    }
   
    public void Versement(String numCompte,int montant) throws RemoteException{
        try {
            stm = conn.obtenirConnexion().createStatement();
            stm2 = conn.obtenirConnexion().createStatement();

            ResultSet res1 = stm.executeQuery("SELECT soldeCompte FROM compte "
                        + " WHERE numCompte = "+AdaptToSqlString(numCompte));
            //ResultSet res = stm.executeQuery("SELECT soldeCompte FROM compte WHERE numCompte = '"+numCompte+"'");
            res1.next();
            //On recupère le montant
            int argent = Integer.valueOf(res1.getString("soldeCompte")).intValue();
          
            //On met à jour notre BD
             String requete1="UPDATE compte "
                + "SET soldeCompte="+(argent+montant)
                + " WHERE numCompte="+AdaptToSqlString(numCompte);
            //double s = argent+montant;
            //String requete1="UPDATE compte SET soldeCompte="+(argent+montant)+ "WHERE numCompte= "+numCompte;
            
            String requete2 = "INSERT INTO operation(idOperation,numCompte,typeOperation,montantOperation,dateOperation) VALUES "
                    + "(NULL,"+AdaptToSqlString(numCompte)+",'VERSEMENT',"+montant+",NOW())";

                stm.executeUpdate(requete1);
                stm2.executeUpdate(requete2);
         
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"::[ Erreur d'insertion dans la base de données ,vérifier votre requête ou\n informer votre administrateur"
                        + " de données..=>"+e+" ]","Problème de connexion à la base de données",JOptionPane.WARNING_MESSAGE);
        }
    }
    public void Retrait(String numCompte,int montant) throws RemoteException{
        try {
           stm = conn.obtenirConnexion().createStatement();
            stm2 = conn.obtenirConnexion().createStatement();

            ResultSet res1 = stm.executeQuery("SELECT soldeCompte FROM compte "
                        + " WHERE numCompte = "+AdaptToSqlString(numCompte));
            //ResultSet res = stm.executeQuery("SELECT soldeCompte FROM compte WHERE numCompte = '"+numCompte+"'");
            res1.next();
            //On recupère le montant
            int argent = Integer.valueOf(res1.getString("soldeCompte")).intValue();
          
            //On met à jour notre BD
             String requete1="UPDATE compte "
                + "SET soldeCompte="+(argent-montant)
                + " WHERE numCompte="+AdaptToSqlString(numCompte);
           
            
            String requete2 = "INSERT INTO operation(idOperation,numCompte,typeOperation,montantOperation,dateOperation) VALUES "
                    + "(NULL,"+AdaptToSqlString(numCompte)+",'RETRAIT',"+montant+",NOW())";

                stm.executeUpdate(requete1);
                stm2.executeUpdate(requete2);
                
     
        } catch (SQLException e) {
                JOptionPane.showMessageDialog(null,"::[ Erreur d'insertion dans la base de données ,vérifier votre requête ou\n informer votre administrateur"
                        + " de données..=>"+e+" ]","Problème de connexion à la base de données",JOptionPane.WARNING_MESSAGE);
        } 
    }
    public void Virement(String NumCompteExp,String NumCompteDest, int solde) throws RemoteException{
        try {
            stm = conn.obtenirConnexion().createStatement();
            stm2 = conn.obtenirConnexion().createStatement();
            stm3 = conn.obtenirConnexion().createStatement();
            ResultSet res1 = stm.executeQuery("SELECT soldeCompte FROM compte "
                        + " WHERE numCompte = "+AdaptToSqlString(NumCompteDest));
            ResultSet res2 = stm2.executeQuery("SELECT soldeCompte FROM compte "
                        + " WHERE numCompte = "+AdaptToSqlString(NumCompteExp));
            //On recupère le montant
            res1.next();
            res2.next();
            int argent1 = Integer.valueOf(res1.getString("soldeCompte")).intValue();
            int argent2 = Integer.valueOf(res2.getString("soldeCompte")).intValue();
            //On met à jour notre BD
            String requete1="UPDATE compte "
                + "SET soldeCompte="+(argent1+solde)
                + " WHERE numCompte="+AdaptToSqlString(NumCompteDest);
            String requete2="UPDATE compte "
                + "SET soldeCompte="+(argent2-solde)
                + " WHERE numCompte="+AdaptToSqlString(NumCompteExp);
            
            String requete3 = "INSERT INTO operation(idOperation,numCompte,typeOperation,montantOperation,dateOperation) VALUES "
                    + "(NULL,"+AdaptToSqlString(NumCompteExp)+",'VIREMENT',"+solde+",NOW())";
               
                stm3.executeUpdate(requete1);
                stm3.executeUpdate(requete2);
                stm3.executeUpdate(requete3);
                
        } catch (SQLException e) {
                JOptionPane.showMessageDialog(null,"::[ Erreur d'insertion dans la base de données ,vérifier votre requête ou\n informer votre administrateur"
                        + " de données..=>"+e+" ]","Problème de connexion à la base de données",JOptionPane.WARNING_MESSAGE);
        }
    }
    public static String attribuer_pwd() {
      String res = "";
      String[] t = {
          "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n",
          "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
          "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N",
          "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
          , "1", "2", "3", "4", "5", "6",
          "7", "8", "9", "0"};
      //La taille du mot de passe est un nombre compris entre 6 et 10 gйnйrйe alйatoirement
      int n = (int) (Math.random() * 5 + 6);
      //Génération du mot de passe
      int nbAlea = 0;
      for (int i = 0; i < n; i++)
          //nbAlea = (int) (Math.random() * 73);
        res += t[(int) (Math.random() * 73)];
      return(res);
    }
    public String GenererMDP()throws RemoteException{
        String numCpte = "";
        try {
            ResultSet res = stm.executeQuery("SELECT numCompte FROM compte ");
            numCpte = attribuer_pwd();
            while (res.next()) {
                if(res.getString("numCompte") == numCpte){
                    res.beforeFirst();
                    numCpte = attribuer_pwd();
                }
            }
        } catch (SQLException e) {
                JOptionPane.showMessageDialog(null,"::[ Erreur d'insertion dans la base de données ,vérifier votre requête ou\n informer votre administrateur"
                        + " de données..=>"+e+" ]","Problème de connexion à la base de données=>GENERATION DE NUMERO DE COMPTE",JOptionPane.WARNING_MESSAGE);
        }
        return numCpte;
    }
    @Override
    public DefaultTableModel InitialisationListeCompte() throws RemoteException{
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Numéro compte");
        model.addColumn("Type compte");
        model.addColumn("Date ouverture");
        model.addColumn("Solde");
        model.addColumn("Proprietaire");
        try {
            stm = conn.obtenirConnexion().createStatement();
            stm2 = conn.obtenirConnexion().createStatement();
            ResultSet res = stm.executeQuery(" SELECT idClient,nomClient,prenomClient,telephoneClient FROM client "); 
            //res.beforeFirst();
            while(res.next()){
                ResultSet resClient = stm2.executeQuery("SELECT numCompte,typeCompte,dateCreationCompte,soldeCompte FROM compte WHERE idClient ="+Integer.valueOf(res.getString("idClient")).intValue()); 
                while(resClient.next()){
                    model.addRow(new Object[]{resClient.getString("numCompte"),resClient.getString("typeCompte"),
                        resClient.getString("dateCreationCompte"),resClient.getString("soldeCompte"),
                        res.getString("nomClient")+"  "+res.getString("prenomClient")+" ::tel::"+res.getString("telephoneClient")});
                    }
            }
   
        } catch (Exception e) {
             JOptionPane.showMessageDialog(null,"::[ Erreur de connexion ,vérifier votre requête ou\n informer votre administrateur "
                     + "de données..=>"+e+" ]","Problème de connexion à la base de données",JOptionPane.WARNING_MESSAGE);
        }
        
        return model;
    }

    @Override
    public DefaultTableModel Consultation(String NumCompteExp) throws RemoteException {
        //String[] resultat = {};
        DefaultTableModel model = new DefaultTableModel();
        try {
            stm = conn.obtenirConnexion().createStatement();
            stm2 = conn.obtenirConnexion().createStatement();
            ResultSet res = stm.executeQuery("SELECT numCompte,idClient,typeCompte,dateCreationCompte,soldeCompte,mdpCompte FROM compte WHERE numCompte="+AdaptToSqlString(NumCompteExp)+"");   
            res.next();
            //resultat[0] = res.getString("numCompte");
            int id = Integer.valueOf(res.getString("idClient")).intValue();
            ResultSet resClient = stm2.executeQuery("SELECT nomClient,prenomClient,adresseClient,telephoneClient FROM client WHERE idClient ="+id+""); 
            resClient.next();
                   
            model.addColumn("numCompte");
            model.addColumn("nomClient");
            model.addColumn("prenomClient");
            model.addColumn("typeCompte");
            model.addColumn("telephoneClient");
            model.addColumn("dateCreationCompte");
            model.addColumn("soldeCompte");
            model.addColumn("adresseClient");
            model.addColumn("mdpCompte");
            
            model.addRow(new Object[]{res.getString("numCompte"),resClient.getString("nomClient"),
                resClient.getString("prenomClient"),res.getString("typeCompte"),resClient.getString("telephoneClient"),
                res.getString("dateCreationCompte"),res.getString("soldeCompte"),
                resClient.getString("adresseClient"),res.getString("mdpCompte")});
            
            
            
        } catch (Exception e) {
             JOptionPane.showMessageDialog(null,"::[ Erreur de connexion ,vérifier votre requête ou\n informer votre administrateur "
                     + "de données..=>"+e+" ]","Problème de connexion à la base de données",JOptionPane.WARNING_MESSAGE);
        }
        
        //return resultat;
        return model;
    }

    @Override
    public DefaultTableModel ListeOperation(String NumCompteExp) throws RemoteException {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Type Opération");
        model.addColumn("Date Opération");
        model.addColumn("Montant");
        try {
            stm = conn.obtenirConnexion().createStatement();
            ResultSet res = stm.executeQuery("SELECT typeOperation,dateOperation,montantOperation FROM operation WHERE numCompte ="+AdaptToSqlString(NumCompteExp));   
            while(res.next()){
                model.addRow(new Object[]{res.getString("typeOperation"),res.getString("dateOperation"),res.getString("montantOperation")});
            }
        } catch (Exception e) {
             JOptionPane.showMessageDialog(null,"::[ Erreur de connexion ,vérifier votre requête ou\n informer votre administrateur "
                     + "de données..=>"+e+" ]","Problème de connexion à la base de données",JOptionPane.WARNING_MESSAGE);
        }
        
        return model;
    }

    @Override
    public void ModifierUnCompte(String numcpte,String nom, String prenom, String adresse, String telephone, String typeCompte, String mdpCpte) throws RemoteException {
        try {
            stm = conn.obtenirConnexion().createStatement();
            stm2 = conn.obtenirConnexion().createStatement();
            stm3 = conn.obtenirConnexion().createStatement();
            ResultSet res = stm.executeQuery("SELECT idClient FROM compte "
                        + " WHERE numCompte = "+AdaptToSqlString(numcpte));
            res.next();
            //On recupère le montant
            int id = Integer.valueOf(res.getString("idClient")).intValue();
          
            //On met à jour notre BD
            String requete1="UPDATE client "
                + "SET nomClient="+AdaptToSqlString(nom)+","
                + " prenomClient =" + AdaptToSqlString(prenom)+","
                + " adresseClient =" + AdaptToSqlString(adresse)+","
                + " telephoneClient =" + AdaptToSqlString(telephone)
                + "WHERE idClient="+id;
            
            String requete2="UPDATE compte "
                + "SET typeCompte="+AdaptToSqlString(typeCompte)+","
                + "mdpCompte="+AdaptToSqlString(mdpCpte)
                + "WHERE numCompte="+AdaptToSqlString(numcpte);
            
                
                stm2.executeUpdate(requete1);
                stm3.executeUpdate(requete2);
                
        } catch (SQLException e) {
                JOptionPane.showMessageDialog(null,"::[ Erreur d'insertion dans la base de données ,vérifier votre requête ou\n informer votre administrateur"
                        + " de données..=>"+e+" ]","Problème de connexion à la base de données",JOptionPane.WARNING_MESSAGE);
        }
    }

    @Override
    public DefaultTableModel ConsultationOperation(String type, String chaine,String numCpte) throws RemoteException {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Type Opération");
        model.addColumn("Date Opération");
        model.addColumn("Montant");
        try {
            stm = conn.obtenirConnexion().createStatement();
            ResultSet res;
            if(type.equals("Par date")){
                 res = stm.executeQuery("SELECT typeOperation, dateOperation, montantOperation FROM operation WHERE numCompte ="
                         + AdaptToSqlString(numCpte) + " AND dateOperation = "+AdaptToSqlString(chaine));
            }else if(type.equals("Par type")){
                res = stm.executeQuery("SELECT typeOperation, dateOperation, montantOperation FROM operation WHERE numCompte ="
                         + AdaptToSqlString(numCpte) + " AND typeOperation = "+AdaptToSqlString(chaine));
            }
            else{
                res = stm.executeQuery("SELECT typeOperation, dateOperation, montantOperation FROM operation WHERE numCompte ="
                         + AdaptToSqlString(numCpte) + " AND montantOperation = "+AdaptToSqlString(chaine));
            }
            
            while(res.next()){
                model.addRow(new Object[]{res.getString("typeOperation"),res.getString("dateOperation"),res.getString("montantOperation")});
            }
        } catch (Exception e) {
             JOptionPane.showMessageDialog(null,"::[ Erreur de connexion ,vérifier votre requête ou\n informer votre administrateur "
                     + "de données..=>"+e+" ]","Problème de connexion à la base de données",JOptionPane.WARNING_MESSAGE);
        }
        
        return model;
    }
}
