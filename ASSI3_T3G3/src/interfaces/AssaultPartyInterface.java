package interfaces;

import clientSide.entities.OrdinaryThief;
import commInfra.AuxOT;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface AssaultPartyInterface extends Remote {
    int getId() throws RemoteException;

    void setRoom_assigned(int roomToSteal) throws RemoteException;

    void setDistance(int distance) throws RemoteException;

    int getRoom_assigned() throws RemoteException;

    void setAP(ArrayList<AuxOT> ap) throws RemoteException;

    void setNext_inLine(int i) throws RemoteException;

    ReturnBool[] join(int ot_id, int mdj) throws RemoteException;

    ReturnInt crawlIn(int ot_id) throws RemoteException;

    void next() throws RemoteException;

    int getDistance() throws RemoteException;

    ReturnBool reverseDirection(int ot_id) throws RemoteException;

    ReturnInt crawlOut(int ot_id) throws RemoteException;

    public void endOperation () throws RemoteException;

    /**
     *   Operation server shutdown.
     *
     *   New operation.
     *
     *     @throws RemoteException if either the invocation of the remote method, or the communication with the registry
     *                             service fails
     */

    public void shutdown () throws RemoteException;
}
