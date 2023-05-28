package interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ConcentrationSiteInterface extends Remote {
    void prepareAssaultParty() throws RemoteException;

    void endOfHeist() throws RemoteException;

    ReturnInt amINeeded(int ot_id) throws RemoteException;

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
