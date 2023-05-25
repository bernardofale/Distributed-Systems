package interfaces;
import java.rmi.*;
import comm_infra.Room;

public interface MuseumInterface extends Remote {
    Room[] getRooms() throws RemoteException;

    boolean rollACanvas(int roomAssigned);
}
