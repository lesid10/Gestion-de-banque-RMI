/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author DIAKITE SOUMAILA
 */
public interface IOperation extends Remote  {
    public void Versement(String numCompte,int montant) throws RemoteException;
    public void Retrait(String numCompte,int montant) throws RemoteException;
    public void Virement(String NumCompteExp,String NumCompteDest, int solde) throws RemoteException;
    public DefaultTableModel Consultation(String NumCompteExp) throws RemoteException;
    public DefaultTableModel ListeOperation(String NumCompteExp) throws RemoteException;
    public DefaultTableModel InitialisationListeCompte() throws RemoteException;
}
