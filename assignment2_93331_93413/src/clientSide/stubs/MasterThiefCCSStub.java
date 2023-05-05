package clientSide.stubs;

public class MasterThiefCCSStub {

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
    public MasterThiefCCSStub(String serverHostName, int serverPortNumb) {
        this.serverHostName = serverHostName;
        this.serverPortNumb = serverPortNumb;
    }
    public void startOperations() {
    }

    public void appraiseSit(boolean heistOver) {
    }

    public void sendAssaultParty() {
    }

    public void takeARest() {
    }

    public void collectACanvas() {
    }

    public int sumUpResults() {
        return 0;
    }

    public int prepareExcursion() {
        return 0;
    }

    public void handACanvas() {
    }

    public void shutdown(){

    }
}
