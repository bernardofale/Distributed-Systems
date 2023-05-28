package interfaces;

import clientSide.entities.MasterThiefStates;
import clientSide.entities.OrdinaryThievesStates;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface GeneralReposInterface extends Remote {
    void setMT_state(MasterThiefStates masterThiefStates) throws RemoteException;

    void setAPRid(int i, int roomToSteal) throws RemoteException;

    void setCanvas(int results) throws RemoteException;

    void printSumUp() throws RemoteException;

    void setOt_mdj(int otId, int mDj) throws RemoteException;

    void setOT_states(int otId, OrdinaryThievesStates ordinaryThievesStates) throws RemoteException;

    void setOtHasCanvas(int otId, boolean isHoldingCanvas) throws RemoteException;

    void setIsInParty(int otId, boolean inParty) throws RemoteException;

    void setOtPosition(int otId, int validPo) throws RemoteException;

    void setOtInParty(int i, int otId, int id) throws RemoteException;

    void setDistance(int i, int distance) throws RemoteException;

    void setNP(int i, int nCanvas) throws RemoteException;

    public void shutdown () throws RemoteException;

    public void initSimul(String fileName) throws RemoteException;
}
