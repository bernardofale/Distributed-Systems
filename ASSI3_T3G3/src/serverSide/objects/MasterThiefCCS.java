package serverSide.objects;

import clientSide.entities.*;
import genclass.GenericIO;
import interfaces.CollectionSiteInterface;
import interfaces.GeneralReposInterface;
import interfaces.ReturnBool;
import interfaces.ReturnInt;
import serverSide.main.ServerMasterThiefCCS;
import serverSide.main.Simul_Par;
import commInfra.*;

import java.rmi.RemoteException;

/**
 * Represents the master thief control and collection site shared region.
 */
public class MasterThiefCCS implements CollectionSiteInterface {

    private int collected_canvas;
    private boolean partiesReady;
    private int back;
    private boolean collect;
    private Party[] parties;

    private GeneralReposInterface gp;

    private Thread MT;

    private Thread[] thieves;

    private OrdinaryThievesStates[] thievesStates;

    private MasterThiefStates master_state;

    /**
     *   Number of entity groups requesting the shutdown.
     */

    private int nEntities;

    /**
     * Creates the master thief control and collection site object. Initialize variables and N_Parties of Party
     * to keep useful data linked to the thieves and respective parties
     * @param gp General Repository
     */
    public MasterThiefCCS(GeneralReposInterface gp){
        MT = null;
        master_state = null;
        thieves = new Thread[Simul_Par.M - 1];
        thievesStates = new OrdinaryThievesStates[Simul_Par.M - 1];
        for (int i = 0; i < Simul_Par.M - 1; i++) {
            thieves[i] = null;
            thievesStates[i] = null;
        }
        this.nEntities = 0;

        collected_canvas = 0;
        partiesReady = false;
        collect = false;
        parties = new Party[Simul_Par.N_Parties];
        for (int i = 0; i < parties.length; i++) {
            parties[i] = new Party(i);
        }
        this.gp = gp;
    }

    /**
     * Returns the index of the first available party
     * @return integer representing the index of the party (0..N_Parties)
     */
    private int getPartyIdx(){
        for (Party p : parties) {
            if(p.getThieves().size() < Simul_Par.K){
                return p.getId();
            }
        }
        return -1;
    }
    /**
     * Get the overall number of collected canvas
     * @return an integer representing the number of stolen canvas
     */
    private int getCollected_canvas() {
        return collected_canvas;
    }

    /**
     * Checks if all parties are on museum. If at least one is not it returns true
     * @return a boolean representing the state of the parties
     */
    private boolean isPartiesInMuseum(){
        boolean isPartiesInMuseum = false;
        for (Party p : parties) {
            isPartiesInMuseum = isPartiesInMuseum || p.isOn();
        }
        return isPartiesInMuseum;
    }
    /**
     * Checks if all parties are ready to go the museum. If at least one is not it returns false
     * @return a boolean representing if the parties are ready to crawl
     */
    private boolean allPartiesReady(){
        boolean full = true;
        for (Party p : parties) {
            full = full && p.isFull();
        }
        return full;
    }
    /**
     *  First function call by the master thief. Forwards her to the state DECIDING_WHAT_TO_DO
     */
    public synchronized ReturnInt startOperations(){
        System.out.println("Starting operations...");
        MT = Thread.currentThread();
        master_state = MasterThiefStates.DECIDING_WHAT_TO_DO;

        return new ReturnInt(0, master_state);
    }

    /**
     * Appraises the situation and checks:
     * -> if the heist is over it should go to PRESENTING_THE_REPORT
     * -> if at least one party is not ongoing it changes the state to ASSEMBLING_A_GROUP
     * -> if all parties are in museum take a rest and the state should be WAITING_FOR_GROUP_ARRIVAL
     * @param isOver boolean representing the state of the heist
     */
    public synchronized ReturnBool appraiseSit(boolean isOver) {

        if (isOver) {
            GenericIO.writeString("Heist is over, presenting the report soon.\n");
            master_state = MasterThiefStates.PRESENTING_THE_REPORT;
        } else if (!isPartiesInMuseum()) {
            GenericIO.writeString("Party not in museum! \n");
            master_state = MasterThiefStates.ASSEMBLING_A_GROUP;
        } else {
            GenericIO.writeString("Party in museum! \n");
            master_state = MasterThiefStates.WAITING_FOR_GROUP_ARRIVAL;
        }

        return new ReturnBool(true, master_state);
    }

    /**
     * Take a rest and wait while the thieves are stealing canvas from the museum
     */
    public synchronized ReturnInt takeARest(){
        master_state = MasterThiefStates.WAITING_FOR_GROUP_ARRIVAL;
        GenericIO.writeString("Taking a rest!\n");
        while(back < (Simul_Par.M - 1)){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        GenericIO.writeString("Thieves are back! Master thief waking up! \n");
        return new ReturnInt(0, master_state);
    }

    /**
     * Adds the ordinary thief to a party and waits while the master thief doesn't signal that all parties should
     * be going; The last of the ordinary thieves joining a party notifies the master. When awake the state
     * should change to CRAWLING_INWARDS
     * @return an integer representing the index of the Assault Party assigned to the current ordinary thief
     */
    public synchronized ReturnInt prepareExcursion(int thief_id) throws RemoteException {
        thieves[thief_id] = Thread.currentThread();

        int idx = getPartyIdx();
        parties[idx].getThieves().add(thief_id);

        if(allPartiesReady()){
            partiesReady = true;
            GenericIO.writeString("Last thief "+ thief_id +" waking up master thief, parties are assembled!\n");
            notifyAll();
        }

        while(!parties[idx].isOn()){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        notifyAll();
        thievesStates[thief_id] = OrdinaryThievesStates.CRAWLING_INWARDS;
        //gp.setOT_states(thief_id, thievesStates[thief_id]);
        return new ReturnInt(idx, thievesStates[thief_id]);
    }
    /**
     * Blocks while all the parties are not ready and sets them as ongoing when they are ready.
     */
    public synchronized ReturnInt sendAssaultParty(){
        while(!partiesReady){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        back = 0;
        collect = false;
        for(Party p : parties){
            p.setOn(true);
        }
        GenericIO.writeString("Master thief is awake, sending assault parties!\n");
        notifyAll();
        master_state = MasterThiefStates.DECIDING_WHAT_TO_DO;

        return new ReturnInt(0, master_state);

    }
    /**
     * Master thief "collects" the canvas and re-initializes variables and flags, such as the parties,
     * for the next iteration of the heist. The state should change to DECIDING_WHAT_TO_DO
     */
    public synchronized ReturnBool collectACanvas(int total_canvas){
        GenericIO.writeString("Collecting...");

        partiesReady = false;
        for (int i = 0; i < Simul_Par.N_Parties; i++) {
            parties[i] = new Party(i);
        }
        master_state = MasterThiefStates.DECIDING_WHAT_TO_DO;
        GenericIO.writeString("Collected " + collected_canvas + " canvas!\n");
        if(collected_canvas == total_canvas){
            master_state = MasterThiefStates.PRESENTING_THE_REPORT;
            return new ReturnBool(true, master_state);
        }
        collect = true;
        notifyAll();

        return new ReturnBool(false, master_state);
    }

    /**
     * Function to sum up the results obtained
     * @return an integer representing the collected canvas
     */
    public synchronized int sumUpResults(){
        return getCollected_canvas();
    }

    /**
     * Ordinary thief hands the canvas and blocks while the master thief doesn't notify and triggers
     */
    public synchronized ReturnInt handACanvas(int thief_id, boolean isHoldingCanvas){
        thieves[thief_id] = Thread.currentThread();

        GenericIO.writeString("Thief #"+ thief_id +" handing canvas!\n");
        back++;
        if(isHoldingCanvas) collected_canvas++;
        if(back == 6) notifyAll();

        while(!collect){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        thievesStates[thief_id] = OrdinaryThievesStates.CONCENTRATION_SITE;

        return new ReturnInt(0, thievesStates[thief_id]);
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
        if (nEntities >= 1)
            ServerMasterThiefCCS.shutdown();
        notifyAll ();                                        // the master thief may now terminate
    }
}
