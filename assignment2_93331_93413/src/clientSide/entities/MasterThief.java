package entities;

import comm_infra.Room;
import genclass.GenericIO;
import main.Simul_Par;
import shared_regions.*;

import java.util.ArrayList;

/**
 * Represents the Master thief thread
 */
public class MasterThief extends Thread{

    private MasterThiefStates MT_state;

    private final MasterThiefCCS cc_site;

    private final OrdinaryThievesCS c_site;

    private final Museum museum;

    private boolean isOver;

    private final AssaultParty[] parties;

    private final int id;

    private GeneralRepo gp;

    /**
     * Creates a master thief object and initializes variables and flags needed for the operation of the thread
     */
    public MasterThief(String name, int id, MasterThiefCCS cc_site, OrdinaryThievesCS c_site, AssaultParty[] parties, Museum museum, GeneralRepo gp){
        super(name);
        this.cc_site = cc_site;
        this.c_site = c_site;
        this.MT_state = MasterThiefStates.PLANNING_THE_HEIST;
        this.museum = museum;
        isOver = false;
        this.parties = new AssaultParty[Simul_Par.N_Parties];
        for (int i = 0; i < Simul_Par.N_Parties; i++) {
            this.parties[i] = parties[i];
        }
        this.id = id;
        this.gp = gp;
    }

    /**
     * Checks if the heist is over
     * @return a boolean that represents the state of the heist
     */
    public boolean isOver() {
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
    public int getRoomToBeStolen(){
        for (Room r : museum.getRooms()) {
            if(!r.isAssigned() && r.isEmpty()) return r.getId();
        }
        return Simul_Par.N - 1;
    }

    /**
     * Function that complements isOver() that checks if all the rooms in the museum are empty
     * @return a boolean that represents the emptiness of the museum
     */
    public boolean isHeistOver(){
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
        while(!isOver()){
            switch(getMT_state()){
                case PLANNING_THE_HEIST :
                    cc_site.startOperations();
                    break;
                case DECIDING_WHAT_TO_DO :
                    gp.setMT_state(MasterThiefStates.DECIDING_WHAT_TO_DO);
                    cc_site.appraiseSit(isHeistOver());
                    break;
                case ASSEMBLING_A_GROUP :
                    gp.setMT_state(MasterThiefStates.ASSEMBLING_A_GROUP);
                    c_site.prepareAssaultParty();
                    for(int i = 0; i < Simul_Par.N_Parties; i++){
                        int roomToSteal= getRoomToBeStolen();
                        GenericIO.writeString("Assigning room "+roomToSteal+" to party "+parties[i].getId()+"\n");
                        int distance = museum.getRooms()[roomToSteal].getDistance();
                        museum.getRooms()[roomToSteal].setAssigned(true);
                        parties[i].setRoom_assigned(roomToSteal);
                        parties[i].setDistance(distance);
                        gp.setAPRid(i, roomToSteal);
                    }
                    cc_site.sendAssaultParty();
                    break;
                case WAITING_FOR_GROUP_ARRIVAL:
                    gp.setMT_state(MasterThiefStates.WAITING_FOR_GROUP_ARRIVAL);
                    cc_site.takeARest();
                    cc_site.collectACanvas();
                    for (int i = 0; i < Simul_Par.N_Parties; i++) {
                        museum.getRooms()[parties[i].getRoom_assigned()].setAssigned(false);
                        if(museum.getRooms()[parties[i].getRoom_assigned()].getN_canvas() == 0){
                            museum.getRooms()[parties[i].getRoom_assigned()].setEmpty(true);
                        }
                        parties[i].setAP(new ArrayList<>(Simul_Par.K));
                        parties[i].setNext_inLine(0);
                    }
                    break;
                case PRESENTING_THE_REPORT:
                    gp.setMT_state(MasterThiefStates.PRESENTING_THE_REPORT);
                    int results = cc_site.sumUpResults();
                    isOver = true;
                    c_site.endOfHeist();
                    GenericIO.writeString("Heist over, collected "+results+" canvas!\n");
                    gp.setCanvas(results);
                    gp.printSumUp();
            }
        }
    }
}

