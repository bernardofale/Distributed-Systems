package interfaces;

import java.rmi.RemoteException;
import java.util.ArrayList;

public interface AssaultPartyInterface {
    String getId();

    void setRoom_assigned(int roomToSteal) throws RemoteException;

    void setDistance(int distance) throws RemoteException;

    int getRoom_assigned() throws RemoteException;

    void setAP(ArrayList<Object> objects) throws RemoteException;

    void setNext_inLine(int i) throws RemoteException;

    void join() throws RemoteException;

    void crawlIn() throws RemoteException;

    void next() throws RemoteException;

    int getDistance() throws RemoteException;

    void reverseDirection() throws RemoteException;

    void crawlOut() throws RemoteException;
}
