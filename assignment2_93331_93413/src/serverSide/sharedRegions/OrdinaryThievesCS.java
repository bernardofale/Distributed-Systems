package serverSide.sharedRegions;

import clientSide.entities.*;
import serverSide.entities.*;
import serverSide.main.*;
import clientSide.stubs.*;
import genclass.GenericIO;
import main.Simul_Par;
import java.util.*;
import java.util.stream.IntStream;

/**
 * Represents the concentration site shared region of the ordinary thieves
 */
public class OrdinaryThievesCS {

    private final int[] thieves;

    private int n_available_thieves;

    private boolean heistOver;

    private GeneralRepo gp;
    /**
     * Creates the monitor and initializes an array of thief signals
     * @param gp General Repository
     */
    public OrdinaryThievesCS(GeneralRepo gp){
        thieves = new int[Simul_Par.M - 1];
        for (int i = 0; i < Simul_Par.M - 1; i++)
            thieves[i] = 0;
        heistOver = false;
        this.gp = gp;
    }

    /**
     * Checks if the heist is over or not
     * @return A boolean representing the state of the heist (Over or not over)
     */
    public boolean isHeistOver() {
        return heistOver;
    }
    /**
     * Sets the state of the heist given the parameter
     * @param heistOver True or false
     */
    public void setHeistOver(boolean heistOver) {
        this.heistOver = heistOver;
    }

    /**
     * Checks if current ordinary thread should be added to available thieves and blocks when available. It is awake
     * when the master thief calls prepareAssaultParty(); If the heist is over returns the lock and terminates.
     * @return Ordinary thief signal
     */
    public synchronized int amINeeded(){
        OrdinaryThief ot = ((OrdinaryThief) Thread.currentThread());
        int thief_id = ot.getOT_id();

        if(thieves[thief_id] == 0){
            n_available_thieves++;
        }
        notifyAll();
        GenericIO.writeString("Notifying master thief! Thief "+thief_id+ " is available.\n");
        while (thieves[thief_id] == 0) {
            try {
                wait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(isHeistOver()){
            return 1;
        }
        GenericIO.writeString("Thief "+thief_id+" is awake.\n");
        if(thieves[thief_id] == 2){
            n_available_thieves--;
            thieves[thief_id] = 0;
        }

        return 0;
    }

    /**
     * Blocks while the available thieves is not M - 1 and starts assembling teams by setting each thief signal
     * as needed for party
     */
    public synchronized void prepareAssaultParty(){
        while (n_available_thieves != (Simul_Par.M - 1)) {
            try {
                wait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        GenericIO.writeString("Assembling!\n");
        for(int i =0; i < Simul_Par.M - 1 ;i++)
            thieves[i] = 2;
        notifyAll();
        GenericIO.writeString("Waking up thieves to prepare assault party....\n");
    }
    /**
     * Signals the ordinary thieves waiting on amINeeded() that the heist is over and can
     * now return the lock and terminate its operation
     */
    public synchronized void endOfHeist(){
        setHeistOver(true);
        for(int i =0; i < Simul_Par.M - 1 ;i++)
            thieves[i] = 1;
        notifyAll();
    }
}
