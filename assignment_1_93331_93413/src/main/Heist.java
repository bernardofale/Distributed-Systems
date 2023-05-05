/*
    Bernardo Falé 93331
    Tiago Rodrigues 93413
    Class 3 Group 3
 */

package main;

import entities.MasterThief;
import entities.OrdinaryThief;
import genclass.GenericIO;
import shared_regions.*;

public class Heist {
    /**
     * Our main method. Initialize monitors and threads needed for the proper operation of the program; It also
     * terminates the threads when the heist is over.
     * @param args The command line arguments.
     **/
    public static void main(String[] args){
        GeneralRepo gp = new GeneralRepo();
        AssaultParty[] aps = new AssaultParty[Simul_Par.N_Parties];
        for (int i = 0; i < Simul_Par.N_Parties; i++) {
            aps[i] = new AssaultParty(i, gp);
        }
        MasterThiefCCS cc_site = new MasterThiefCCS(gp);
        Museum museum = new Museum(gp);
        OrdinaryThievesCS c_site = new OrdinaryThievesCS(gp);
        OrdinaryThief[] thieves = new OrdinaryThief[Simul_Par.M - 1];

        MasterThief mt = new MasterThief("MasterThief", 1, cc_site, c_site, aps, museum, gp);
        mt.start();
        for (int i = 0; i < Simul_Par.M - 1; i++) {
            thieves[i] = new OrdinaryThief("OrdinaryThief_"+i, i, c_site, cc_site, aps, museum, gp);
        }
        for (int i = 0; i < Simul_Par.M - 1; i++){
            thieves[i].start();

        }
        try {
            mt.join();
            GenericIO.writelnString("Master thief terminated!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < Simul_Par.M - 1; i++) {
            try {
                thieves[i].join();
                GenericIO.writelnString("Thief#"+i+" terminated.");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}