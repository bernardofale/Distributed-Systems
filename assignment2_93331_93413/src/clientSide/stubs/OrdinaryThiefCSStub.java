package clientSide.stubs;

public class OrdinaryThiefCSStub {

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
    public OrdinaryThiefCSStub(String serverHostName, int serverPortNumb) {
        this.serverHostName = serverHostName;
        this.serverPortNumb = serverPortNumb;
    }
    public void endOfHeist() {
    }

    public void prepareAssaultParty() {
    }

    public int amINeeded() {
        return 0;
    }

    public void shutdown(){

    }
}
