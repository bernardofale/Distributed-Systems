package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface CollectionSiteInterface extends Remote {
    ReturnInt startOperations() throws RemoteException;

    ReturnBool appraiseSit(boolean heistOver) throws RemoteException;

    ReturnInt sendAssaultParty() throws RemoteException;

    ReturnInt takeARest() throws RemoteException;

    ReturnInt collectACanvas() throws RemoteException;

    int sumUpResults() throws RemoteException;

    ReturnInt prepareExcursion(int thief_id) throws RemoteException;

    ReturnInt handACanvas(int thief_id, boolean isHoldingCanvas) throws RemoteException;

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
