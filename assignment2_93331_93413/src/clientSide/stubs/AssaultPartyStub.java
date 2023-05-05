package clientSide.stubs;

import clientSide.entities.OrdinaryThief;

import java.util.ArrayList;

public class AssaultPartyStub {
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

    public AssaultPartyStub(String serverHostName, int serverPortNumb)
    {
        this.serverHostName = serverHostName;
        this.serverPortNumb = serverPortNumb;
    }

    public void setRoom_assigned(int room_assigned) {
    }

    public void setDistance(int distance){

    }

    public void setAP(ArrayList<OrdinaryThief> AP){

    }

    public void setNext_inLine(int next_inLine){

    }

    public int getRoom_assigned() {
        return 0;
    }

    public String getId() {
        return null;
    }

    public void join() {
    }

    public void crawlIn() {
    }

    public void next() {
    }

    public int getDistance() {
        return 0;
    }

    public void reverseDirection() {
    }

    public void crawlOut() {
    }

    public void shutdown(){

    }
}
