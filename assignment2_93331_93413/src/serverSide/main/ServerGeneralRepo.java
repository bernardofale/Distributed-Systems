package serverSide.main;

import comm_infra.ServerCom;
import genclass.GenericIO;
import serverSide.entities.GeneralRepoProxy;
import serverSide.sharedRegions.GeneralRepo;
import serverSide.sharedRegions.GeneralRepoInterface;

import java.net.SocketTimeoutException;

public class ServerGeneralRepo {
    /**
     *  Flag signaling the service is active.
     */

    public static boolean waitConnection;

    /**
     *  Main method.
     *
     *    @param args runtime arguments
     *        args[0] - port number for listening to service requests
     */

    public static void main (String [] args)
    {
        GeneralRepo repos;                                            // general repository of information (service to be rendered)
        GeneralRepoInterface reposInter;                              // interface to the general repository of information
        ServerCom scon, sconi;                                         // communication channels
        int portNumb = -1;                                             // port number for listening to service requests

        if (args.length != 1)
        { GenericIO.writelnString ("Wrong number of parameters!");
            System.exit (1);
        }
        try
        { portNumb = Integer.parseInt (args[0]);
        }
        catch (NumberFormatException e)
        { GenericIO.writelnString ("args[0] is not a number!");
            System.exit (1);
        }
        if ((portNumb < 4000) || (portNumb >= 65536))
        { GenericIO.writelnString ("args[0] is not a valid port number!");
            System.exit (1);
        }

        /* service is established */

        repos = new GeneralRepo ();                                   // service is instantiated
        reposInter = new GeneralRepoInterface (repos);                // interface to the service is instantiated
        scon = new ServerCom (portNumb);                               // listening channel at the public port is established
        scon.start ();
        GenericIO.writelnString ("Service is established!");
        GenericIO.writelnString ("Server is listening for service requests.");

        /* service request processing */

        GeneralRepoProxy cliProxy;                                  // service provider agent

        waitConnection = true;
        while (waitConnection)
        { try
        { sconi = scon.accept ();                                              // enter listening procedure
            cliProxy = new GeneralRepoProxy (sconi, reposInter);          // start a service provider agent to address
            cliProxy.start ();                                                   //   the request of service
        }
        catch (SocketTimeoutException ignored) {}
        }
        scon.end ();                                                   // operations termination
        GenericIO.writelnString ("Server was shutdown.");
    }
}
