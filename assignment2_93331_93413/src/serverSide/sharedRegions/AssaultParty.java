package serverSide.sharedRegions;

import clientSide.entities.*;
import clientSide.stubs.GeneralRepoStub;
import serverSide.entities.AssaultPartyProxy;
import serverSide.entities.OrdinaryThievesCSProxy;
import serverSide.main.*;
import genclass.GenericIO;
import java.util.*;
import java.util.stream.IntStream;

/**
 * Represents the AssaultParty shared region
 */
public class AssaultParty {
    private ArrayList<Integer> AP;

    private final int id;

    private int next_inLine;

    private int distance; //distance to assigned room

    private int room_assigned;

    private GeneralRepoStub gp;

    private AssaultPartyProxy MT_Proxy;

    private AssaultPartyProxy[] OT_Proxy;

    /**
     *   Number of entity groups requesting the shutdown.
     */

    private int nEntities;

    /**
     * Creates an assault party object and initialize variables and flags
     * @param id of the Assault party (0 .. N_Parties)
     * @param gp General Repository
     */
    public AssaultParty(int id, GeneralRepoStub gp){
        MT_Proxy = null;

        OT_Proxy = new AssaultPartyProxy[Simul_Par.M - 1];
        for (int i = 0; i < Simul_Par.M - 1; i++) {
            OT_Proxy[i] = null;
        }
        this.id = id;
        AP = new ArrayList<>(Simul_Par.K);
        next_inLine = 0;
        room_assigned = -1;
        distance = -1;
        this.gp = gp;
        this.nEntities = 0;
    }

    /**
     * Gets the thieves in the Assault party
     * @return an array list of ordinary thieves
     */
    public ArrayList<Integer> getAP() {
        return AP;
    }

    /**
     * Gets the index of the next thief in line
     * @return an integer representing the index of the thief
     */
    public int getNext_inLine() {
        return next_inLine;
    }

    /**
     * Sets the index of the next thief in line
     * @param next_inLine index
     */
    public void setNext_inLine(int next_inLine) {
        this.next_inLine = next_inLine;
    }

    /**
     * Gets the distance to the room/outside
     * @return an integer representing the distance of the assigned room
     */
    public int getDistance() {
        return distance;
    }

    /**
     * Sets the distance assigned to this party
     * @param distance to the room
     */
    public void setDistance(int distance) {
        this.distance = distance;
    }

    /**
     * Get the assigned room of the assault party
     * @return an integer representing the index of the assigned room
     */
    public int getRoom_assigned() {
        return room_assigned;
    }

    /**
     * Assign a room to the assault party
     * @param room_assigned represents the index of the room
     */
    public void setRoom_assigned(int room_assigned) {
        this.room_assigned = room_assigned;
    }

    public int getId() {
        return id;
    }

    /**
     * Sets the Assault party array list. Useful to reset the party data structure
     * @param AP new Array list object
     */
    public void setAP(ArrayList<Integer> AP) {
        this.AP = AP;
    }

    /**
     * Checks if the array list of the party is at full capacity
     * @return a boolean representing the fullness of the party
     */
    public boolean isFull() {
        return getAP().size() == Simul_Par.K;
    }

    /**
     * Checks if all the ordinary thieves in the assault party are ready to leave the concentration site. If not
     * returns false
     * @return a boolean that reflects the ordinary thieves' situation (Ready to leave or not)
     */
    private boolean allReady() {
        for (int i = 0; i < getAP().size(); i++) {
            if (!OT_Proxy[i].getReadyToLeave()) {
                return false;
            }
        }

        return true;
    }

    /**
     * Checks if all the ordinary thieves have reached the desired destination
     * @return a boolean
     */
    private boolean inRoom() {
        for (int i = 0; i < getAP().size(); i++) {
            if (OT_Proxy[i].getPosition() != getDistance()) {
                return false;
            }
        }

        return true;
    }
    /**
     * Checks if the given array of positions is valid
     * @param validPos is an array of integers containing the positions of the thieves currently in the party
     * @return a boolean representing the validity of the array
     */
    private boolean isValidPosition(int[] validPos) {
        int[] sortedArr = Arrays.copyOf(validPos, validPos.length);
        Arrays.sort(sortedArr);
        for (int j = Simul_Par.K - 1; j >= 1; j--) {
            int diff = Math.abs(sortedArr[j] - sortedArr[j - 1]);
            if (diff > Simul_Par.S || (diff == 0 && sortedArr[j] != 0 && sortedArr[j] != getDistance())) {
                return false;
            }
        }
        return true;
    }
    /**
     * Calculates an array of positions based on the given parameters
     * @param i represents the maximum displacement of a thief that should be added to the current position
     *          of the thief
     * @return an array of integers that contain the positions ready for validity check
     */
    private int[] getValidPositions(int i) {
        int[] validPos = IntStream.range(0, Simul_Par.K)
                .map(k -> OT_Proxy[k].getPosition())
                .toArray();
        validPos[getNext_inLine()] += i;
        if (validPos[getNext_inLine()] >= getDistance()) validPos[getNext_inLine()] = getDistance();
        return validPos;
    }

    /**
     * The ordinary thief crawls according to the rules
     */
    public synchronized void crawlIn(){
        int thief_id = ((AssaultPartyProxy) Thread.currentThread()).getOTId();

        while (thief_id != getAP().get(getNext_inLine()) || !isFull()) {
            try {
                wait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        int[] validPos;
        int n = getAP().get(getNext_inLine());
        for (int i = OT_Proxy[n].getMDj(); i > 0; i--) {
            validPos = getValidPositions(i);
            if (isValidPosition(validPos)) {
                OT_Proxy[n].setPosition(validPos[getNext_inLine()]);
                gp.setOtPosition(thief_id, validPos[getNext_inLine()]);
                break;
            }
        }
        next_inLine = (getNext_inLine() + Simul_Par.K - 1) % Simul_Par.K;

        notifyAll();
    }

    /**
     * Function called by the ordinary thief when it reaches the desired destination; Blocks until
     * all the other thieves are done crawling and are in AT_ROOM or in COLLECTION_SITE
     */
    public synchronized void next() {
        while (!inRoom()) {
            try {
                int n = getAP().get(getNext_inLine());
                if (OT_Proxy[n].getPosition() == getDistance()) {
                    next_inLine = (getNext_inLine() + Simul_Par.K - 1) % Simul_Par.K;
                    notifyAll();
                }
                wait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    /**
     * Last ordinary thief to roll a canvas signals the assault party that they are reversing direction. Reset
     * some flags and notify
     */
    public synchronized void reverseDirection(){
        int thief_id = ((AssaultPartyProxy) Thread.currentThread()).getOTId();
        OT_Proxy[thief_id] = (AssaultPartyProxy) Thread.currentThread();

        OT_Proxy[thief_id].setReadyToLeave(true);
        while(!allReady()){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        GenericIO.writeString("Reversing direction!\n");
        OT_Proxy[thief_id].setOTState(OrdinaryThievesStates.CRAWLING_OUTWARDS);
        gp.setOT_states(thief_id, OrdinaryThievesStates.CRAWLING_OUTWARDS);
        OT_Proxy[thief_id].setPosition(0);
        notifyAll();
    }

    /**
     * Same movement as in crawlIn() but reverted
     */
    public synchronized void crawlOut(){
        int thief_id = ((AssaultPartyProxy) Thread.currentThread()).getOTId();

        while (thief_id != getAP().get(getNext_inLine()) || !isFull()) {
            try {
                wait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        int[] validPos;
        int n = getAP().get(getNext_inLine());
        for (int i = OT_Proxy[n].getMDj(); i > 0; i--) {
            validPos = getValidPositions(i);
            if (isValidPosition(validPos)) {
                OT_Proxy[n].setPosition(validPos[getNext_inLine()]);
                gp.setOtPosition(thief_id, validPos[getNext_inLine()]);
                break;
            }
        }
        next_inLine = (getNext_inLine() + Simul_Par.K - 1) % Simul_Par.K;

        notifyAll();
    }
    /**
     * Ordinary thief joins party
     */
    public synchronized void join() {
        int thief_id = ((AssaultPartyProxy) Thread.currentThread()).getOTId();
        OT_Proxy[thief_id] = (AssaultPartyProxy) Thread.currentThread();

        GenericIO.writelnString("Thief#"+ thief_id +" placed in AP#"+getId());
        getAP().add(thief_id);
        OT_Proxy[thief_id].setReadyToLeave(false);
        OT_Proxy[thief_id].setInParty(true);
        gp.setIsInParty(thief_id, OT_Proxy[thief_id].isInParty());
        gp.setOtInParty(getAP().indexOf(thief_id), thief_id, getId());
        if(isFull()) notifyAll();

    }

    public synchronized void endOperation (int MTId)
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
            if(getId() == 0) ServerAssaultParty0.waitConnection = false;
            else ServerAssaultParty1.waitConnection = false;
        notifyAll ();                                        // the master thief may now terminate
    }
}
