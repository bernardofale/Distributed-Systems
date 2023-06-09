package clientSide.main;

import clientSide.entities.MasterThief;
import clientSide.stubs.*;
import genclass.GenericIO;
import serverSide.main.Simul_Par;

public class ClientMasterThief {

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
        MasterThief MT;
        AssaultPartyStub[] APStub = new AssaultPartyStub[Simul_Par.N_Parties];
        MasterThiefCCSStub CCSStub;
        MuseumStub MuseumStub;
        OrdinaryThiefCSStub CSStub;
        GeneralRepoStub genReposStub;


        /* getting problem runtime parameters */

        if (args.length != 12) {
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


        /* problem initialization */

        for (int i = 0; i < Simul_Par.N_Parties; i++) {
            APStub[i] = new AssaultPartyStub(APServerHostName[i], APServerPortNumb[i]);
        }

        CCSStub = new MasterThiefCCSStub(CCSServerHostName, CCSServerPortNumb);
        MuseumStub = new MuseumStub(MuseumServerHostName, MuseumServerPortNumb);
        CSStub = new OrdinaryThiefCSStub(CSServerHostName, CSServerPortNumb);

        genReposStub = new GeneralRepoStub(genReposServerHostName, genReposServerPortNumb);
        MT = new MasterThief("Master thief", 0, CCSStub, CSStub, APStub, MuseumStub, genReposStub);


        /* start of the simulation */
        MT.start();

        /* waiting for the end of the simulation */

        GenericIO.writelnString();
        while (MT.isAlive()) {
            for (int i = 0; i < Simul_Par.N_Parties; i++) {
                APStub[i].endOperation(0);
            }
            CCSStub.endOperation();
            CSStub.endOperation();
            MuseumStub.endOperation();
            Thread.yield();
        }
        try {
            MT.join();
        } catch (InterruptedException ignored) {
        }
        GenericIO.writelnString("The Master thief has terminated.");

        for (int i = 0; i < Simul_Par.N_Parties; i++) {
            APStub[i].shutdown();
        }
        MuseumStub.shutdown();
        CCSStub.shutdown();
        CSStub.shutdown();
        genReposStub.shutdown();
    }
}
