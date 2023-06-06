package interfaces;
import java.rmi.*;
import commInfra.Room;

public interface MuseumInterface extends Remote {
    Room[] getRooms() throws RemoteException;

    ReturnBool rollACanvas(int ot_id, int roomAssigned) throws RemoteException;

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

    int getTotal_canvas() throws RemoteException;
}
