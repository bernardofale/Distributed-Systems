// Bernardo Falé
// Tiago Rodrigues
// Class 3 group 3

package main;

import comm_infra.MemException;
import entities.MasterThief;
import entities.OrdinaryThief;
import shared_regions.*;

public class Heist {
    public static void main(String[] args) throws MemException {

        AssaultParty ap0 = new AssaultParty(0);
        AssaultParty ap1 = new AssaultParty(1);
        MasterThiefCCS cc_site = new MasterThiefCCS();
        Museum museum = new Museum();
        OrdinaryThievesCS c_site = new OrdinaryThievesCS();
        OrdinaryThief[] thieves = new OrdinaryThief[Simul_Par.M - 1];
        GeneralRepo gp = new GeneralRepo(ap0, ap1, cc_site, c_site, museum);
        MasterThief mt = new MasterThief("Master", 1, cc_site, c_site, ap0, ap1, museum, gp);
        mt.start();
        for (int i = 0; i < Simul_Par.M - 1; i++) {
            thieves[i] = new OrdinaryThief("Thief", i, c_site, cc_site, ap0, ap1, museum, gp);
            thieves[i].start();
        }

    }
}