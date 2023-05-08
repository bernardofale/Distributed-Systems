package clientSide.main;

import clientSide.entities.MasterThief;
import clientSide.entities.OrdinaryThief;
import clientSide.stubs.*;
import genclass.GenericIO;
import serverSide.main.Simul_Par;

public class ClientOrdinaryThief {

    /**
     *
     * @param args args[0] -> Assault party 0 hostname
     * @param args args[1] -> Assault party 1 hostname
     * @param args args[2] -> Control and collection site hostname
     *
     * @param args args[3] -> Concentration site hostname
     * @param args args[4] -> Museum host name
     * @param args args[5] -> AP0 port number
     *
     * @param args args[6] -> AP1 port number
     * @param args args[7] -> CCS port number
     * @param args args[8] -> CS port number
     *
     * @param args args[9] -> Museum port number
     * @param args args[10] -> General repos hostname
     * @param args args[11] -> general repos port number
     * @param args args[12] -> logfile name
     */
    public static void main(String[] args) {
        String[] APServerHostName = new String[Simul_Par.N_Parties];                               // name of the platform where is located the barber shop server
        String CCSServerHostName;
        String CSServerHostName;
        String MuseumServerHostName;
        int[] APServerPortNumb = new int[Simul_Par.N_Parties];
        int CCSServerPortNumb = -1;
        int CSServerPortNumb = -1;
        int MuseumServerPortNumb = -1;
        String genReposServerHostName;
        int genReposServerPortNumb = -1;
        OrdinaryThief[] OT = new OrdinaryThief[Simul_Par.M - 1];
        AssaultPartyStub[] APStub = new AssaultPartyStub[Simul_Par.N_Parties];
        MasterThiefCCSStub CCSStub;
        MuseumStub MuseumStub;
        OrdinaryThiefCSStub CSStub;
        GeneralRepoStub genReposStub;
        String FILENAME;


        /* getting problem runtime parameters */

        if (args.length != 13) {
            GenericIO.writelnString("Wrong number of parameters!");
            System.exit(1);
        }
        APServerHostName[0] = args[0];
        APServerHostName[1] = args[1];
        CCSServerHostName = args[2];
        CSServerHostName = args[3];
        MuseumServerHostName = args[4];
        try {
            for (int i = 0; i < Simul_Par.N_Parties; i++) {
                APServerPortNumb[i] = Integer.parseInt(args[5 + i]);
            }
            CCSServerPortNumb = Integer.parseInt(args[7]);
            CSServerPortNumb = Integer.parseInt(args[8]);
            MuseumServerPortNumb = Integer.parseInt(args[9]);
        } catch (NumberFormatException e) {
            GenericIO.writelnString("args[1] is not a number!");
            System.exit(1);
        }

        genReposServerHostName = args[10];
        try {
            genReposServerPortNumb = Integer.parseInt(args[11]);
        } catch (NumberFormatException e) {
            GenericIO.writelnString("args[11] is not a number!");
            System.exit(1);
        }

        FILENAME = args[12];
        /* problem initialization */

        for (int i = 0; i < Simul_Par.N_Parties; i++) {
            APStub[i] = new AssaultPartyStub(APServerHostName[i], APServerPortNumb[i]);
        }

        CCSStub = new MasterThiefCCSStub(CCSServerHostName, CCSServerPortNumb);
        MuseumStub = new MuseumStub(MuseumServerHostName, MuseumServerPortNumb);
        CSStub = new OrdinaryThiefCSStub(CSServerHostName, CSServerPortNumb);

        genReposStub = new GeneralRepoStub(genReposServerHostName, genReposServerPortNumb);
        genReposStub.initSimul(FILENAME);

        for (int i = 0; i < Simul_Par.M - 1; i++) {
            OT[i] = new OrdinaryThief("Master thief", 0, CSStub, CCSStub, APStub, MuseumStub, genReposStub);
        }


        /* start of the simulation */
        for (int i = 0; i < Simul_Par.M - 1; i++) {
            OT[i].start();
        }

        /* waiting for the end of the simulation */

        GenericIO.writelnString();
        for (int i = 0; i < Simul_Par.M - 1; i++) {
            try {
                OT[i].join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            GenericIO.writelnString("The Master thief has terminated.");
        }

        for (int i = 0; i < Simul_Par.N_Parties; i++) {
            APStub[i].shutdown();
        }
        MuseumStub.shutdown();
        CCSStub.shutdown();
        CSStub.shutdown();
        genReposStub.shutdown();
    }
}
