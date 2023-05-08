package serverSide.entities;

import clientSide.entities.MasterThiefCloning;
import clientSide.entities.MasterThiefStates;
import clientSide.entities.OrdinaryThiefCloning;
import clientSide.entities.OrdinaryThievesStates;
import comm_infra.Message;
import comm_infra.MessageException;
import comm_infra.ServerCom;
import genclass.GenericIO;
import serverSide.sharedRegions.MuseumInterface;

public class MuseumProxy extends Thread implements MasterThiefCloning, OrdinaryThiefCloning {
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

    private MuseumInterface MInter;

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

    private OrdinaryThievesStates OTState;

    /**
     *  Instantiation of a client proxy.
     *
     *     @param sconi communication channel
     *     @param MInter interface to the Assault Party
     */
    public MuseumProxy(ServerCom sconi, MuseumInterface MInter)
    {
        super ("MuseumProxy_" + MuseumProxy.getProxyId ());
        this.sconi = sconi;
        this.MInter = MInter;
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
        { cl = Class.forName ("serverSide.entities.MuseumProxy");
        }
        catch (ClassNotFoundException e)
        { GenericIO.writelnString ("Data type MuseumProxy was not found!");
            e.printStackTrace ();
            System.exit (1);
        }
        synchronized (cl)
        { proxyId = nProxy;
            nProxy += 1;
        }
        return proxyId;
    }

    @Override
    public void setMTId(int id) {
        MTId = id;
    }

    @Override
    public int getMTId() {
        return MTId;
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
        { outMessage = MInter.processAndReply (inMessage);         // process it
        }
        catch (MessageException e)
        { GenericIO.writelnString ("Thread " + getName () + ": " + e.getMessage () + "!");
            GenericIO.writelnString (e.getMessageVal ().toString ());
            System.exit (1);
        }
        sconi.writeObject (outMessage);                                // send service reply
        sconi.close ();                                                // close the communication channel
    }
}
