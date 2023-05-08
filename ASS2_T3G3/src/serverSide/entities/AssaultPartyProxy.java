package serverSide.entities;

import clientSide.entities.*;
import comm_infra.*;
import serverSide.main.Simul_Par;
import serverSide.sharedRegions.AssaultPartyInterface;
import genclass.GenericIO;

public class AssaultPartyProxy extends Thread implements MasterThiefCloning, OrdinaryThiefCloning{

    /**
     *  Number of instantiated threads.
     */

    private static int nProxy = 0;

    /**
     *  Communication channel.
     */

    private ServerCom sconi;

    /**
     *  Interface to the Assault Party.
     */

    private AssaultPartyInterface APInter;

    /**
     *  Master thief identification.
     */

    private int MTId;

    /**
     *  Master thief state.
     */

    private MasterThiefStates MTState;

    /**
     *  Ordinary thief identification.
     */

    private int OTId;

    /**
     *  Ordinary thief state.
     */

    private boolean readyToLeave;

    private boolean inParty;

    private int position;

    private int MDj;

    private OrdinaryThievesStates OTState;

    /**
     *  Instantiation of a client proxy.
     *
     *     @param sconi communication channel
     *     @param APInter interface to the Assault Party
     */
    public AssaultPartyProxy(ServerCom sconi, AssaultPartyInterface APInter)
    {
        super ("AssaultPartyProxy_" + AssaultPartyProxy.getProxyId ());
        this.sconi = sconi;
        this.APInter = APInter;
        readyToLeave = false;
    }

    /**
     *  Generation of the instantiation identifier.
     *
     *     @return instantiation identifier
     */

    private static int getProxyId ()
    {
        Class<?> cl = null;                                            // representation of the proxy object in JVM
        int proxyId;                                                   // instantiation identifier

        try
        { cl = Class.forName ("serverSide.entities.AssaultPartyProxy");
        }
        catch (ClassNotFoundException e)
        { GenericIO.writelnString ("Data type AssaultPartyProxy was not found!");
            e.printStackTrace ();
            System.exit (1);
        }
        synchronized (cl)
        { proxyId = nProxy;
            nProxy += 1;
        }
        return proxyId;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public void setMTId(int id) {
        MTId = id;
    }

    @Override
    public int getMTId() {

        return MTId;
    }

    public int getMDj() {
        return MDj;
    }

    @Override
    public void setMTState(MasterThiefStates state) {
        MTState = state;
    }

    @Override
    public MasterThiefStates getMTState() {
        return MTState;
    }

    @Override
    public void setOTId(int id) {
        OTId = id;
    }

    @Override
    public int getOTId() {
        return OTId;
    }

    @Override
    public void setOTState(OrdinaryThievesStates state) {
        OTState = state;
    }

    @Override
    public OrdinaryThievesStates getOTState() {
        return OTState;
    }

    public boolean getReadyToLeave() {
        return readyToLeave;
    }

    public void setReadyToLeave(boolean readyToLeave) {
        this.readyToLeave = readyToLeave;
    }

    public boolean isInParty() {
        return inParty;
    }

    public void setInParty(boolean inParty) {
        this.inParty = inParty;
    }

    public void setRoom_assigned(int roomToSteal) {
    }
    /**
     *  Life cycle of the service provider agent.
     */

    @Override
    public void run ()
    {
        Message inMessage = null,                                      // service request
                outMessage = null;                                     // service reply

        /* service providing */

        inMessage = (Message) sconi.readObject ();                     // get service request
        try
        { outMessage = APInter.processAndReply (inMessage);         // process it
        }
        catch (MessageException e)
        { GenericIO.writelnString ("Thread " + getName () + ": " + e.getMessage () + "!");
            GenericIO.writelnString (e.getMessageVal ().toString ());
            System.exit (1);
        }
        sconi.writeObject (outMessage);                                // send service reply
        sconi.close ();                                                // close the communication channel
    }

    public void setMDj(int mDj) {
        this.MDj = mDj;
    }

}
