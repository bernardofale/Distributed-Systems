package serverSide.sharedRegions;

import clientSide.entities.*;
import clientSide.stubs.GeneralRepoStub;
import genclass.GenericIO;
import serverSide.main.Simul_Par;
import comm_infra.*;

/**
 * Represents the master thief control and collection site shared region.
 */
public class MasterThiefCCS {

    private int collected_canvas;
    private boolean partiesReady;
    private int back;
    private boolean collect;
    private Party[] parties;

    private GeneralRepoStub gp;

    /**
     * Creates the master thief control and collection site object. Initialize variables and N_Parties of Party
     * to keep useful data linked to the thieves and respective parties
     * @param gp General Repository
     */
    public MasterThiefCCS(GeneralRepoStub gp){
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
    public int getCollected_canvas() {
        return collected_canvas;
    }

    /**
     * Checks if all parties are on museum. If at least one is not it returns true
     * @return a boolean representing the state of the parties
     */
    public boolean isPartiesInMuseum(){
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
    public boolean allPartiesReady(){
        boolean full = true;
        for (Party p : parties) {
            full = full && p.isFull();
        }
        return full;
    }
    /**
     *  First function call by the master thief. Forwards her to the state DECIDING_WHAT_TO_DO
     */
    public synchronized void startOperations(){
        System.out.println("Starting operations...");
        ((MasterThief)Thread.currentThread()).setMT_state(MasterThiefStates.DECIDING_WHAT_TO_DO);
    }

    /**
     * Appraises the situation and checks:
     * -> if the heist is over it should go to PRESENTING_THE_REPORT
     * -> if at least one party is not ongoing it changes the state to ASSEMBLING_A_GROUP
     * -> if all parties are in museum take a rest and the state should be WAITING_FOR_GROUP_ARRIVAL
     * @param isOver boolean representing the state of the heist
     */
    public synchronized void appraiseSit(boolean isOver) {
        MasterThief mt = (MasterThief) Thread.currentThread();

        if (isOver) {
            GenericIO.writeString("Heist is over, presenting the report soon.\n");
            mt.setMT_state(MasterThiefStates.PRESENTING_THE_REPORT);
        } else if (!isPartiesInMuseum()) {
            GenericIO.writeString("Party not in museum! \n");
            mt.setMT_state(MasterThiefStates.ASSEMBLING_A_GROUP);
        } else {
            GenericIO.writeString("Party in museum! \n");
            mt.setMT_state(MasterThiefStates.WAITING_FOR_GROUP_ARRIVAL);
        }
    }

    /**
     * Take a rest and wait while the thieves are stealing canvas from the museum
     */
    public synchronized void takeARest(){
        ((MasterThief) Thread.currentThread()).setMT_state(MasterThiefStates.WAITING_FOR_GROUP_ARRIVAL);
        GenericIO.writeString("Taking a rest!\n");
        while(back < (Simul_Par.M - 1)){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        GenericIO.writeString("Thieves are back! Master thief waking up! \n");
    }

    /**
     * Adds the ordinary thief to a party and waits while the master thief doesn't signal that all parties should
     * be going; The last of the ordinary thieves joining a party notifies the master. When awake the state
     * should change to CRAWLING_INWARDS
     * @return an integer representing the index of the Assault Party assigned to the current ordinary thief
     */
    public synchronized int prepareExcursion(){
        OrdinaryThief ot = (OrdinaryThief) Thread.currentThread();

        int idx = getPartyIdx();
        parties[idx].getThieves().add(ot.getOT_id());

        if(allPartiesReady()){
            partiesReady = true;
            GenericIO.writeString("Last thief "+ ot.getOT_id()+" waking up master thief, parties are assembled!\n");
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
        ot.setOT_state(OrdinaryThievesStates.CRAWLING_INWARDS);
        gp.setOT_states(ot.getOT_id(), OrdinaryThievesStates.CRAWLING_INWARDS);
        return idx;
    }
    /**
     * Blocks while all the parties are not ready and sets them as ongoing when they are ready.
     */
    public synchronized void sendAssaultParty(){
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
        ((MasterThief) Thread.currentThread()).setMT_state(MasterThiefStates.DECIDING_WHAT_TO_DO);

    }
    /**
     * Master thief "collects" the canvas and re-initializes variables and flags, such as the parties,
     * for the next iteration of the heist. The state should change to DECIDING_WHAT_TO_DO
     */
    public synchronized void collectACanvas(){
        MasterThief mt = (MasterThief) Thread.currentThread();
        GenericIO.writeString("Collecting...");

        partiesReady = false;
        for (int i = 0; i < Simul_Par.N_Parties; i++) {
            parties[i] = new Party(i);
        }

        mt.setMT_state(MasterThiefStates.DECIDING_WHAT_TO_DO);
        GenericIO.writeString("Collected "+collected_canvas+ " canvas!\n");
        collect = true;
        notifyAll();
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
    public synchronized void handACanvas(){
        OrdinaryThief ot = (OrdinaryThief) Thread.currentThread();
        GenericIO.writeString("Thief #"+ot.getOT_id()+" handing canvas!\n");
        back++;
        if(ot.isHoldingCanvas()) collected_canvas++;
        if(back == 6) notifyAll();

        while(!collect){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        ot.setOT_state(OrdinaryThievesStates.CONCENTRATION_SITE);
    }
}
