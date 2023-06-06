package clientSide.main;

import java.rmi.registry.*;
import java.rmi.*;
import java.rmi.server.*;
import clientSide.entities.*;
import serverSide.main.*;
import interfaces.*;
import genclass.GenericIO;

/**
 *    Client side of the Sleeping Barbers (Ordinary thieves).
 *
 *    Implementation of a client-server model of type 2 (server replication).
 *    Communication is based on Java RMI.
 */

public class ClientOrdinaryThief {
    /**
     *  Main method.
     *
     *    @param args runtime arguments
     *        args[0] - name of the platform where is located the RMI registering service
     *        args[1] - port number where the registering service is listening to service requests
     *        args[2] - name of the logging file
     */

    public static void main (String [] args) throws RemoteException {
        String rmiRegHostName;                                         // name of the platform where is located the RMI registering service
        int rmiRegPortNumb = -1;                                       // port number where the registering service is listening to service requests
        String fileName;                                               // name of the logging file

        /* getting problem runtime parameters */

        if (args.length != 3)
        { GenericIO.writelnString ("Wrong number of parameters!");
            System.exit (1);
        }
        rmiRegHostName = args[0];
        try
        { rmiRegPortNumb = Integer.parseInt (args[1]);
        }
        catch (NumberFormatException e)
        { GenericIO.writelnString ("args[1] is not a number!");
            System.exit (1);
        }
        if ((rmiRegPortNumb < 4000) || (rmiRegPortNumb >= 65536))
        { GenericIO.writelnString ("args[1] is not a valid port number!");
            System.exit (1);
        }
        fileName = args[2];

        /* problem initialization */

        String nameEntryGeneralRepos = "GeneralRepository";            // public name of the general repository object
        GeneralReposInterface reposStub = null;                        // remote reference to the general repository object
        AssaultPartyInterface[] APStub = new AssaultPartyInterface[Simul_Par.N_Parties];                          // remote reference to the Assault Party object
        String nameEntryAP0 = "AssaultParty0";                     // public name of the AP0 object
        String nameEntryAP1 = "AssaultParty1";                     // public name of the AP1 object
        String nameEntryCCS = "CollectionSite";                     // public name of the CCS object
        CollectionSiteInterface CCSStub = null;                          // remote reference to the CCS object
        String nameEntryCS = "ConcentrationSite";                     // public name of the CS object
        ConcentrationSiteInterface CSStub = null;                          // remote reference to the CS object
        String nameEntryMuseum = "Museum";                     // public name of the Museum object
        MuseumInterface MuseumStub = null;                          // remote reference to the Museum object
        Registry registry = null;                                      // remote reference for registration in the RMI registry service
        OrdinaryThief[] thieves = new OrdinaryThief[Simul_Par.M - 1];              // array of customer threads

        try
        { registry = LocateRegistry.getRegistry (rmiRegHostName, rmiRegPortNumb);
        }
        catch (RemoteException e)
        { GenericIO.writelnString ("RMI registry creation exception: " + e.getMessage ());
            e.printStackTrace ();
            System.exit (1);
        }

        try
        { reposStub = (GeneralReposInterface) registry.lookup (nameEntryGeneralRepos);
        }
        catch (RemoteException e)
        { GenericIO.writelnString ("GeneralRepos lookup exception: " + e.getMessage ());
            e.printStackTrace ();
            System.exit (1);
        }
        catch (NotBoundException e)
        { GenericIO.writelnString ("GeneralRepos not bound exception: " + e.getMessage ());
            e.printStackTrace ();
            System.exit (1);
        }

        try
        { APStub[0] = (AssaultPartyInterface) registry.lookup (nameEntryAP0);
        }
        catch (RemoteException e)
        { GenericIO.writelnString ("AP0 lookup exception: " + e.getMessage ());
            e.printStackTrace ();
            System.exit (1);
        }
        catch (NotBoundException e)
        { GenericIO.writelnString ("AP0 not bound exception: " + e.getMessage ());
            e.printStackTrace ();
            System.exit (1);
        }

        try
        { APStub[1] = (AssaultPartyInterface) registry.lookup (nameEntryAP1);
        }
        catch (RemoteException e)
        { GenericIO.writelnString ("AP1 lookup exception: " + e.getMessage ());
            e.printStackTrace ();
            System.exit (1);
        }
        catch (NotBoundException e)
        { GenericIO.writelnString ("AP1 not bound exception: " + e.getMessage ());
            e.printStackTrace ();
            System.exit (1);
        }

        try
        { CSStub = (ConcentrationSiteInterface) registry.lookup (nameEntryCS);
        }
        catch (RemoteException e)
        { GenericIO.writelnString ("CS lookup exception: " + e.getMessage ());
            e.printStackTrace ();
            System.exit (1);
        }
        catch (NotBoundException e)
        { GenericIO.writelnString ("CS not bound exception: " + e.getMessage ());
            e.printStackTrace ();
            System.exit (1);
        }

        try
        { CCSStub = (CollectionSiteInterface) registry.lookup (nameEntryCCS);
        }
        catch (RemoteException e)
        { GenericIO.writelnString ("CCS lookup exception: " + e.getMessage ());
            e.printStackTrace ();
            System.exit (1);
        }
        catch (NotBoundException e)
        { GenericIO.writelnString ("CCS not bound exception: " + e.getMessage ());
            e.printStackTrace ();
            System.exit (1);
        }

        try
        { MuseumStub = (MuseumInterface) registry.lookup (nameEntryMuseum);
        }
        catch (RemoteException e)
        { GenericIO.writelnString ("Museum lookup exception: " + e.getMessage ());
            e.printStackTrace ();
            System.exit (1);
        }
        catch (NotBoundException e)
        { GenericIO.writelnString ("Museum not bound exception: " + e.getMessage ());
            e.printStackTrace ();
            System.exit (1);
        }

        //reposStub.initSimul(fileName);

        for (int i = 0; i < Simul_Par.M - 1; i++)
            thieves[i] = new OrdinaryThief("thief_" + (i), i, CSStub, CCSStub, APStub, MuseumStub, reposStub);

        /* start of the simulation */

        for (int i = 0; i < Simul_Par.M - 1; i++)
            thieves[i].start ();

        /* waiting for the end of the simulation */

        for (int i = 0; i < Simul_Par.M - 1; i++)
        { try
        { thieves[i].join ();
        }
        catch (InterruptedException ignored) {}
            GenericIO.writelnString ("The ordinary thief " + (i) + " has terminated.");
        }
        GenericIO.writelnString ();

        try {
            CCSStub.shutdown ();

        }
        catch (RemoteException e)
        { GenericIO.writelnString ("Master thief generator remote exception on CCS shutdown: " + e.getMessage ());
            System.exit (1);
        }
        try {
            CSStub.shutdown();
        } catch (RemoteException e) {
            GenericIO.writelnString ("Master thief generator remote exception on CS shutdown: " + e.getMessage ());
            System.exit (1);
        }
        try {
            APStub[0].shutdown();
        } catch (RemoteException e) {
            GenericIO.writelnString ("Master thief generator remote exception on AP0 shutdown: " + e.getMessage ());
            System.exit (1);
        }
        try {
            APStub[1].shutdown();
        } catch (RemoteException e) {
            GenericIO.writelnString ("Master thief generator remote exception on AP1 shutdown: " + e.getMessage ());
            System.exit (1);
        }
        try {
            MuseumStub.shutdown();
        } catch (RemoteException e) {
            GenericIO.writelnString ("Master thief generator remote exception on Museum shutdown: " + e.getMessage ());
            System.exit (1);
        }
        try {
            reposStub.shutdown ();
        }
        catch (RemoteException e)
        { GenericIO.writelnString ("Barber generator remote exception on GeneralRepos shutdown: " + e.getMessage ());
            System.exit (1);
        }
    }

}

