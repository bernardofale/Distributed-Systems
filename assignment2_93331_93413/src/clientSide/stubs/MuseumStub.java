package clientSide.stubs;

import comm_infra.Room;

public class MuseumStub {

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
    public MuseumStub(String serverHostName, int serverPortNumb) {
        this.serverHostName = serverHostName;
        this.serverPortNumb = serverPortNumb;
    }
    public Room[] getRooms() {
        return null;
    }

    public boolean rollACanvas(int roomAssigned) {
        return true;
    }

    public void shutdown(){

    }
}
