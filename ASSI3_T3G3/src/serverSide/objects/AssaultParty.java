package serverSide.objects;

import clientSide.entities.*;
import commInfra.AuxOT;
import interfaces.AssaultPartyInterface;
import interfaces.GeneralReposInterface;
import interfaces.ReturnBool;
import interfaces.ReturnInt;
import serverSide.main.*;
import genclass.GenericIO;

import java.rmi.RemoteException;
import java.util.*;
import java.util.stream.IntStream;

/**
 * Represents the AssaultParty shared region
 */
public class AssaultParty implements AssaultPartyInterface {
    private ArrayList<AuxOT> AP;

    private final int id;

    private int next_inLine;

    private int distance; //distance to assigned room

    private int room_assigned;

    private Thread[] thieves;

    private OrdinaryThievesStates[] thieves_states;

    private MasterThiefStates master_state;

    private Thread MT;

    private GeneralReposInterface gp;

    private int nEntities;

    private int full;

    /**
     * Creates an assault party object and initialize variables and flags
     * @param id of the Assault party (0 .. N_Parties)
     * @param gp General Repository
     */
    public AssaultParty(int id, GeneralReposInterface gp){
        this.id = id;
        AP = new ArrayList<>(Simul_Par.K);
        MT = null;
        thieves = new Thread [Simul_Par.M - 1];
        thieves_states = new OrdinaryThievesStates[Simul_Par.M - 1];
        for (int i = 0; i < Simul_Par.M - 1; i++)
        { thieves[i] = null;
            thieves_states[i] = null;
        }
        master_state = null;
        next_inLine = 0;
        room_assigned = -1;
        distance = -1;
        this.gp = gp;
        nEntities = 0;
        full = 0;
    }

    /**
     * Gets the thieves in the Assault party
     * @return an array list of ordinary thieves
     */
    public ArrayList<AuxOT> getAP() {
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
    public void setAP(ArrayList<AuxOT> AP) {
        this.AP = AP;
    }

    /**
     * Checks if the array list of the party is at full capacity
     * @return a boolean representing the fullness of the party
     */
    public boolean isFull() {
        return full == Simul_Par.K;
    }

    public void setFull(int f) {
        full = f;
    }

    /**
     * Checks if all the ordinary thieves in the assault party are ready to leave the concentration site. If not
     * returns false
     * @return a boolean that reflects the ordinary thieves' situation (Ready to leave or not)
     */
    private boolean allReady() {
        for (AuxOT ot : getAP()) {
            if(!ot.isReadyToLeave()){
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if all the ordinary thieves have reached the desired destination
     * @return a boolean
     */
    public boolean inRoom() {
        for (AuxOT ot : getAP()) {
            if (ot.getPosition() != getDistance()) {
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
                .map(k -> getAP().get(k).getPosition())
                .toArray();
        validPos[getNext_inLine()] += i;
        if (validPos[getNext_inLine()] >= getDistance()) validPos[getNext_inLine()] = getDistance();
        return validPos;
    }

    /**
     * The ordinary thief crawls according to the rules
     */
    public synchronized ReturnInt crawlIn(int ot_id) throws RemoteException {
        thieves[ot_id] = Thread.currentThread();
        int pos = -1;
        while (ot_id != getAP().get(getNext_inLine()).getId() || !isFull()) {
            try {
                wait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        int[] validPos;
        for (int i = getAP().get(getNext_inLine()).getMDj(); i > 0; i--) {
            validPos = getValidPositions(i);
            if (isValidPosition(validPos)) {
                getAP().get(getNext_inLine()).setPosition(validPos[getNext_inLine()]);
                //gp.setOtPosition(ot_id, validPos[getNext_inLine()]);
                pos = validPos[getNext_inLine()];
                break;
            }
        }
        next_inLine = (getNext_inLine() + Simul_Par.K - 1) % Simul_Par.K;

        notifyAll();

        return new ReturnInt(pos, thieves_states[ot_id]);
    }

    /**
     * Function called by the ordinary thief when it reaches the desired destination; Blocks until
     * all the other thieves are done crawling and are in AT_ROOM or in COLLECTION_SITE
     */
    public synchronized void next() {
        while (!inRoom()) {
            try {
                if (getAP().get(getNext_inLine()).getPosition() == getDistance()) {
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
    public synchronized ReturnBool reverseDirection(int ot_id) throws RemoteException {
        thieves[ot_id] = Thread.currentThread();
        AuxOT ot = null;

        for (AuxOT o : getAP()) {
            if(o.getId() == ot_id){
                ot = o;
            }
        }
        assert ot != null;
        ot.setReadyToLeave(true);

        while(!allReady()){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        GenericIO.writeString("Reversing direction!\n");
        thieves_states[ot_id] = OrdinaryThievesStates.CRAWLING_OUTWARDS;
        //gp.setOT_states(ot_id, thieves_states[ot_id]);
        ot.setPosition(0);
        notifyAll();

        return new ReturnBool(true, thieves_states[ot_id]);
    }

    /**
     * Same movement as in crawlIn() but reverted
     */
    public synchronized ReturnInt crawlOut(int ot_id) throws RemoteException {
        thieves[ot_id] = Thread.currentThread();
        int pos = -1;
        while (ot_id != getAP().get(getNext_inLine()).getId() || !isFull()) {
            try {
                wait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        int[] validPos;
        for (int i = getAP().get(getNext_inLine()).getMDj(); i > 0; i--) {
            validPos = getValidPositions(i);
            if (isValidPosition(validPos)) {
                getAP().get(getNext_inLine()).setPosition(validPos[getNext_inLine()]);
                //gp.setOtPosition(ot_id, validPos[getNext_inLine()]);
                pos = validPos[getNext_inLine()];
                break;
            }
        }
        next_inLine = (getNext_inLine() + Simul_Par.K - 1) % Simul_Par.K;

        notifyAll();

        return new ReturnInt(pos, thieves_states[ot_id]);
    }
    /**
     * Ordinary thief joins party
     */
    public synchronized ReturnBool[] join(int ot_id, int mdj) throws RemoteException {
        thieves[ot_id] = Thread.currentThread();
        GenericIO.writelnString("Thief#"+ ot_id +" placed in AP#" + getId());
        AuxOT ot = new AuxOT(ot_id, mdj);
        getAP().add(ot);
        full++;
        ot.setReadyToLeave(false);
        ot.setInParty(true);
        //gp.setIsInParty(ot_id, ot.isInParty());
        //gp.setOtInParty(getAP().indexOf(ot), ot_id, getId());
        if(isFull()) notifyAll();

        ReturnBool[] r = new ReturnBool[2];

        //ready to leave
        r[0] = new ReturnBool(false, thieves_states[ot_id]);

        //in party
        r[1] = new ReturnBool(true, thieves_states[ot_id]);

        return r;
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

    public synchronized void shutdown () throws RemoteException
    {
        nEntities += 1;
        if (nEntities >= 1)
            if(getId() == 0)
                ServerAssaultParty.shutdown();
        notifyAll ();                                        // the master thief may now terminate
    }
}
