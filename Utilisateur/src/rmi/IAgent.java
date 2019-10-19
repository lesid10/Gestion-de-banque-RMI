/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 *
 * @author DIAKITE SOUMAILA
 */
public interface IAgent extends Remote{
    public boolean connectAgent(String nom,String mdp)throws RemoteException;
    public void Ajouter(String nom, String prenom, String adresse, String telephone,String typeCompte,String numCpte, String mdpCpte, double soldeInit) throws RemoteException;
    public String GenererMDP()throws RemoteException;
    public void ModifierUnCompte(String numcpte,String nom, String prenom, String adresse, String telephone,String typeCompte, String mdpCpte) throws RemoteException;
    
}
