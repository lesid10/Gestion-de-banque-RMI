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
public interface IClient extends Remote{
   public boolean connectClient(String  numCpte,String mdp) throws RemoteException; 
    public DefaultTableModel ConsultationOperation(String type,String chaine,String numCpte) throws RemoteException;
}
