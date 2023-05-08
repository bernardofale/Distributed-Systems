package serverSide.entities;

import comm_infra.*;
import genclass.GenericIO;
import serverSide.sharedRegions.*;
import clientSide.entities.*;

public class GeneralRepoProxy extends Thread{
    /**
     *  Number of instantiayed threads.
     */

    private static int nProxy = 0;

    /**
     *  Communication channel.
     */

    private ServerCom sconi;

    /**
     *  Interface to the General Repository of Information.
     */

    private GeneralRepoInterface repoInter;

    /**
     *  Instantiation of a client proxy.
     *
     *     @param sconi communication channel
     *     @param reposInter interface to the general repository of information
     */

    public GeneralRepoProxy (ServerCom sconi, GeneralRepoInterface reposInter)
    {
        super ("GeneralRepoProxy_" + GeneralRepoProxy.getProxyId ());
        this.sconi = sconi;
        this.repoInter = reposInter;
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
        { cl = Class.forName ("serverSide.entities.GeneralRepoProxy");
        }
        catch (ClassNotFoundException e)
        { GenericIO.writelnString ("Data type GeneralRepoProxy was not found!");
            e.printStackTrace ();
            System.exit (1);
        }
        synchronized (cl)
        { proxyId = nProxy;
            nProxy += 1;
        }
        return proxyId;
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
        { outMessage = repoInter.processAndReply (inMessage);         // process it
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
