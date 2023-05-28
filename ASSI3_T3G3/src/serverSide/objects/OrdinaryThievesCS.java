package serverSide.objects;

import clientSide.entities.*;
import interfaces.ConcentrationSiteInterface;
import interfaces.GeneralReposInterface;
import interfaces.ReturnInt;
import serverSide.main.*;
import genclass.GenericIO;
import serverSide.main.Simul_Par;

/**
 * Represents the concentration site shared region of the ordinary thieves
 */
public class OrdinaryThievesCS implements ConcentrationSiteInterface {

    private final int[] thieves;

    private int n_available_thieves;

    private boolean heistOver;

    private GeneralReposInterface gp;

    private Thread MT;

    private Thread[] OTs;

    private OrdinaryThievesStates[] thievesStates;

    private MasterThiefStates master_state;


    /**
     *   Number of entity groups requesting the shutdown.
     */

    private int nEntities;
    /**
     * Creates the monitor and initializes an array of thief signals
     * @param gp General Repository
     */
    public OrdinaryThievesCS(GeneralReposInterface gp){
        MT = null;
        master_state = null;
        thievesStates = new OrdinaryThievesStates[Simul_Par.M - 1];
        OTs = new Thread[Simul_Par.M - 1];
        for (int i = 0; i < Simul_Par.M - 1; i++) {
            OTs[i] = null;
            thievesStates[i] = null;
        }
        this.nEntities = 0;
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
    public synchronized ReturnInt amINeeded(int ot_id){
        OTs[ot_id] = Thread.currentThread();


        if(thieves[ot_id] == 0){
            n_available_thieves++;
        }
        notifyAll();
        GenericIO.writeString("Notifying master thief! Thief "+ ot_id + " is available.\n");
        while (thieves[ot_id] == 0) {
            try {
                wait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if(isHeistOver()){
            return new ReturnInt(1, OrdinaryThievesStates.CONCENTRATION_SITE);
        }
        GenericIO.writeString("Thief "+ ot_id +" is awake.\n");
        if(thieves[ot_id] == 2){
            n_available_thieves--;
            thieves[ot_id] = 0;
        }

        return new ReturnInt(0, OrdinaryThievesStates.CONCENTRATION_SITE);
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

    public synchronized void endOperation ()
    {
        while (nEntities == 0)
        { /* the master thief waits for the termination of the ordinary thieves */
            try
            { wait ();
            }
            catch (InterruptedException ignored) {}
        }
        if (MT != null)
            MT.interrupt ();
    }


    /**
     *   Operation server shutdown.
     *
     *   New operation.
     */

    public synchronized void shutdown ()
    {
        nEntities += 1;
        if (nEntities >= Simul_Par.M)
            ServerOrdinaryThievesCS.shutdown();
        notifyAll ();                                        // the master thief may now terminate
    }
}
