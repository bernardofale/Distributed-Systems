package entities;

import comm_infra.Room;
import genclass.GenericIO;
import shared_regions.*;

public class MasterThief extends Thread{

    private MasterThiefStates MT_state;

    private MasterThiefCCS cc_site;

    private OrdinaryThievesCS c_site;

    private AssaultParty ap0;

    private AssaultParty ap1;

    private Museum museum;

    private boolean isOver;

    private AssaultParty[] parties;
    private int[] assignedRooms;

    private GeneralRepo GP;

    public MasterThief(String name, int id, MasterThiefCCS cc_site, OrdinaryThievesCS c_site, AssaultParty ap0, AssaultParty ap1, Museum museum, GeneralRepo GP){
        super(name);
        this.cc_site = cc_site;
        this.c_site = c_site;
        this.MT_state = MasterThiefStates.PLANNING_THE_HEIST;
        this.ap0 = ap0;
        this.ap1 = ap1;
        this.museum = museum;
        isOver = false;
        parties = new AssaultParty[2];
        assignedRooms = new int[2];
        parties[0] = ap0;
        parties[1] = ap1;
        this.GP = GP;
    }

    public boolean isOver() {
        return isOver;
    }

    public void setMT_state(MasterThiefStates MT_state) {
        this.MT_state = MT_state;
    }

    public MasterThiefStates getMT_state() {
        return MT_state;
    }

    public AssaultParty getAp0() {
        return ap0;
    }

    public AssaultParty getAp1() {
        return ap1;
    }

    public Museum getMuseum() {
        return museum;
    }

    public int[] getAssignedRooms() {
        return assignedRooms;
    }

    public int getRoomToBeStolen(){
        for(int i=0; i<museum.getRooms().length;i++){
            if(!museum.getRooms()[i].isAssigned() && museum.getRooms()[i].isEmpty())
            {
                return i;
            }
        }
        return museum.getRooms().length-1;
    }

    public boolean isHeistOver(){
        for (Room r : museum.getRooms()) {
            if(r.isEmpty()) {
                return false;
            }
        }
        return true;
    }
    @Override
    public void run () {
        while(!isOver()){
            switch(getMT_state()){
                case PLANNING_THE_HEIST :
                    cc_site.startOperations();
                    break;
                case DECIDING_WHAT_TO_DO :
                    GP.setMT_state(MasterThiefStates.DECIDING_WHAT_TO_DO);
                    c_site.appraiseSit(isHeistOver());
                    break;
                case ASSEMBLING_A_GROUP :
                    GP.setMT_state(MasterThiefStates.ASSEMBLING_A_GROUP);
                    c_site.prepareAssaultParty();
                    for(int i = 0; i < 2; i++){
                        int roomToSteal= getRoomToBeStolen();
                        GenericIO.writeString("Assigning room "+roomToSteal+" to party "+parties[i].getId()+"\n");
                        int distance = museum.getRooms()[roomToSteal].getDistance();
                        parties[i].setRoom_assigned(roomToSteal);
                        parties[i].setDistance(distance);
                        assignedRooms[i] = roomToSteal;
                        museum.getRooms()[roomToSteal].setAssigned(true);
                    }
                    cc_site.sendAssaultParty();
                    break;
                case WAITING_FOR_GROUP_ARRIVAL:
                    GP.setMT_state(MasterThiefStates.WAITING_FOR_GROUP_ARRIVAL);
                    cc_site.takeARest();
                    cc_site.collectACanvas();
                    break;
                case PRESENTING_THE_REPORT:
                    GP.setMT_state(MasterThiefStates.PRESENTING_THE_REPORT);
                    GP.printSumUp();
                    int results = cc_site.sumUpResults();
                    isOver = true;
                    GenericIO.writeString("Heist over, collected "+results+" canvas!\n");
            }


        }
    }
}

