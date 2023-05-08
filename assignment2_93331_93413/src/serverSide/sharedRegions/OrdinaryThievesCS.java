package serverSide.sharedRegions;

import clientSide.entities.*;
import clientSide.stubs.GeneralRepoStub;
import serverSide.entities.AssaultPartyProxy;
import serverSide.entities.OrdinaryThievesCSProxy;
import serverSide.main.*;
import genclass.GenericIO;
import serverSide.main.Simul_Par;

/**
 * Represents the concentration site shared region of the ordinary thieves
 */
public class OrdinaryThievesCS {

    private final int[] thieves;

    private int n_available_thieves;

    private boolean heistOver;

    private GeneralRepoStub gp;

    private OrdinaryThievesCSProxy MT_Proxy;

    private OrdinaryThievesCSProxy[] OT_Proxy;

    /**
     *   Number of entity groups requesting the shutdown.
     */

    private int nEntities;
    /**
     * Creates the monitor and initializes an array of thief signals
     * @param gp General Repository
     */
    public OrdinaryThievesCS(GeneralRepoStub gp){
        MT_Proxy = null;

        OT_Proxy = new OrdinaryThievesCSProxy[Simul_Par.M - 1];
        for (int i = 0; i < Simul_Par.M - 1; i++) {
            OT_Proxy[i] = null;
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
    public synchronized int amINeeded(){
        int thief_id = ((OrdinaryThievesCSProxy) Thread.currentThread()).getOTId();
        OT_Proxy[thief_id] = (OrdinaryThievesCSProxy) Thread.currentThread();

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

    public synchronized void endOperation ()
    {
        while (nEntities == 0)
        { /* the master thief waits for the termination of the ordinary thieves */
            try
            { wait ();
            }
            catch (InterruptedException ignored) {}
        }
        if (MT_Proxy != null)
            MT_Proxy.interrupt ();
    }


    /**
     *   Operation server shutdown.
     *
     *   New operation.
     */

    public synchronized void shutdown ()
    {
        nEntities += 1;
        if (nEntities >= Simul_Par.E)
            ServerOrdinaryThievesCS.waitConnection = false;
        notifyAll ();                                        // the master thief may now terminate
    }
}
