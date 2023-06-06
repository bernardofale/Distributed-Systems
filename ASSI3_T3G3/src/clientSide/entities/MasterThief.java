package clientSide.entities;

import commInfra.*;
import genclass.GenericIO;
import interfaces.*;
import serverSide.main.*;

import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Represents the Master thief thread
 */
public class MasterThief extends Thread{

    private MasterThiefStates MT_state;

    private final CollectionSiteInterface cc_site;

    private final ConcentrationSiteInterface c_site;

    private final MuseumInterface museum;

    private boolean isOver;

    private final AssaultPartyInterface[] parties;

    private final int id;

    private GeneralReposInterface gp;

    private int total_canvas;

    private boolean finish;

    /**
     * Creates a master thief object and initializes variables and flags needed for the operation of the thread
     */
    public MasterThief(String name, int id, CollectionSiteInterface cc_site, ConcentrationSiteInterface c_site, AssaultPartyInterface[] parties, MuseumInterface museum, GeneralReposInterface gp){
        super(name);
        this.cc_site = cc_site;
        this.c_site = c_site;
        this.MT_state = MasterThiefStates.PLANNING_THE_HEIST;
        this.museum = museum;
        isOver = false;
        this.parties = new AssaultPartyInterface[Simul_Par.N_Parties];
        System.arraycopy(parties, 0, this.parties, 0, Simul_Par.N_Parties);
        this.id = id;
        this.gp = gp;
        try {
            total_canvas = museum.getTotal_canvas();
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
        finish = false;
    }

    /**
     * Checks if the heist is over
     * @return a boolean that represents the state of the heist
     */
    public synchronized boolean isOver() {
        return isOver;
    }

    /**
     * Sets the master thief state given the parameter
     * @param MT_state is an enumerate that represents a master thief state
     */
    public void setMT_state(MasterThiefStates MT_state) {
        this.MT_state = MT_state;
    }

    /**
     * Gets the current master thief state
     * @return an enumerate that represents the master thief state
     */
    public MasterThiefStates getMT_state() {
        return MT_state;
    }

    /**
     * Get the first room in the available rooms of the museum that could be stolen
     * @return an integer that represents the index of the room
     */
    public int getRoomToBeStolen() throws RemoteException {
        for (Room r : museum.getRooms()) {
            if(!r.isAssigned() && !r.isEmpty()) return r.getId();
        }
        return Simul_Par.N - 1;
    }

    /**
     * Function that complements isOver() that checks if all the rooms in the museum are empty
     * @return a boolean that represents the emptiness of the museum
     */
    public synchronized boolean isHeistOver() throws RemoteException {
        for (Room r : museum.getRooms()) {
            if(r.isEmpty()) {
                return false;
            }
        }
        return true;
    }
    /**
     * The main run method; Starts executing when thread.start() is called
     */
    @Override
    public void run () {
        while(!finish){
            switch(getMT_state()){
                case PLANNING_THE_HEIST :
                    startOps();
                    break;

                case DECIDING_WHAT_TO_DO :
                    //gp.setMT_state(MasterThiefStates.DECIDING_WHAT_TO_DO);
                    appraiseSit();
                    break;

                case ASSEMBLING_A_GROUP :
                    //gp.setMT_state(MasterThiefStates.ASSEMBLING_A_GROUP);
                    prepareAssaultParty();
                    for(int i = 0; i < Simul_Par.N_Parties; i++){
                        int roomToSteal;
                        try {
                            roomToSteal = getRoomToBeStolen();

                        } catch (RemoteException e) {
                            throw new RuntimeException(e);
                        }


                        int distance;
                        try {
                            distance = museum.getRooms()[roomToSteal].getDistance();
                            museum.getRooms()[roomToSteal].setAssigned(true);
                            parties[i].setRoom_assigned(roomToSteal);
                            parties[i].setDistance(distance);
                        } catch (RemoteException e) {
                            throw new RuntimeException(e);
                        }
                        //gp.setAPRid(i, roomToSteal);
                    }
                    sendAssaultParty();
                    break;

                case WAITING_FOR_GROUP_ARRIVAL:
                    //gp.setMT_state(MasterThiefStates.WAITING_FOR_GROUP_ARRIVAL);
                    takeARest();
                    collectACanvas();

                    for (int i = 0; i < Simul_Par.N_Parties; i++) {
                        try {
                            museum.getRooms()[parties[i].getRoom_assigned()].setAssigned(false);
                            parties[i].setFull(0);
                            parties[i].setAP(new ArrayList<>(Simul_Par.K));
                            parties[i].setNext_inLine(0);
                        } catch (RemoteException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                case PRESENTING_THE_REPORT:
                    //gp.setMT_state(MasterThiefStates.PRESENTING_THE_REPORT);
                    int results = 0;
                    results = sumUpResults();
                    finish = true;
                    endOfHeist();
                    GenericIO.writeString("Heist over, collected " + results + " canvas!\n");
                    //gp.setCanvas(results);
                    //gp.printSumUp();
                    break;
            }
        }
    }

    private void startOps(){
        ReturnInt r = null;
        try {
            r = cc_site.startOperations();
        } catch (RemoteException e)
        { GenericIO.writelnString ("Master thief remote exception on startOperations: " + e.getMessage ());
            System.exit (1);
        }
        MT_state = r.getState_master();
    }

    private void appraiseSit(){
        ReturnBool r = null;
        try {
            r = cc_site.appraiseSit(isOver);
        } catch (RemoteException e)
        { GenericIO.writelnString ("Master thief remote exception on appraiseSit: " + e.getMessage ());
            System.exit (1);
        }
        MT_state = r.getMaster_state();

    }

    private void prepareAssaultParty(){
        try {
            c_site.prepareAssaultParty();
        } catch (RemoteException e) {
            GenericIO.writelnString ("Master thief remote exception on prepareAssaultParty: " + e.getMessage ());
            System.exit (1);
        }
    }

    private void sendAssaultParty(){
        ReturnInt r = null;
        try {
            r = cc_site.sendAssaultParty();
        } catch (RemoteException e) {
            GenericIO.writelnString ("Master thief remote exception on sendAssaultParty: " + e.getMessage ());
            System.exit (1);
        }

        MT_state = r.getState_master();
    }

    private void takeARest(){
        ReturnInt r = null;
        try {
            r = cc_site.takeARest();
        } catch (RemoteException e) {
            GenericIO.writelnString ("Master thief remote exception on takeARest: " + e.getMessage ());
            System.exit (1);
        }
        MT_state = r.getState_master();
    }

    private void collectACanvas() {
        ReturnBool r = null;
        try {
            r = cc_site.collectACanvas(total_canvas);
        } catch (RemoteException e) {
            GenericIO.writelnString ("Master thief remote exception on collectACanvas: " + e.getMessage ());
            System.exit (1);
        }
        MT_state = r.getMaster_state();
        isOver = r.getBooleanVal();
    }

    private int sumUpResults(){
        int r = -1;
        try {
            r = cc_site.sumUpResults();
        } catch (RemoteException e) {
            GenericIO.writelnString ("Master thief remote exception on sumUpResults: " + e.getMessage ());
            System.exit (1);
        }
        return r;
    }

    private void endOfHeist(){
        try {
            c_site.endOfHeist();
        } catch (RemoteException e) {
            GenericIO.writelnString ("Master thief remote exception on endOfHeist: " + e.getMessage ());
            System.exit (1);
        }
    }
}

