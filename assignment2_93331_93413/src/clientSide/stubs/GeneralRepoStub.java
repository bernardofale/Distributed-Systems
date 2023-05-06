package clientSide.stubs;

import clientSide.entities.MasterThiefStates;
import clientSide.entities.OrdinaryThievesStates;

public class GeneralRepoStub {
    /**
     *  Name of the platform where is located the barber shop server.
     */

    private String serverHostName;

    /**
     *  Port number for listening to service requests.
     */

    private int serverPortNumb;

    /**
     *   Instantiation of a stub to the barber shop.
     *
     *     @param serverHostName name of the platform where is located the barber shop server
     *     @param serverPortNumb port number for listening to service requests
     */
    public GeneralRepoStub(String serverHostName, int serverPortNumb) {
        this.serverHostName = serverHostName;
        this.serverPortNumb = serverPortNumb;
    }

    public void setMT_state(MasterThiefStates masterThiefStates) {
    }

    public void setAPRid(int i, int roomToSteal) {
    }

    public void setCanvas(int results) {
    }

    public void printSumUp() {
    }

    public void setOT_states(int otId, OrdinaryThievesStates ordinaryThievesStates) {
    }

    public void setOtHasCanvas(int otId, boolean isHoldingCanvas) {
    }

    public void setIsInParty(int otId, boolean inParty) {
    }

    public void setOt_mdj(int otId, int mDj) {
    }

    public void shutdown(){

    }

    public void setOtPosition(int otId, int validPo) {
    }

    public void setOtInParty(int i, int otId, int id) {
    }

    public void setDistance(int i, int distance) {
    }

    public void setNP(int i, int nCanvas) {
    }
}
