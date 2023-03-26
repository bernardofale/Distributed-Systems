package entities;

import comm_infra.MemException;
import genclass.GenericIO;
import main.Simul_Par;
import shared_regions.AssaultParty;
import shared_regions.MasterThiefCCS;
import shared_regions.Museum;
import shared_regions.OrdinaryThievesCS;

public class OrdinaryThief extends Thread{

    private boolean readyToLeave;
    private int OT_id;

    private OrdinaryThievesStates OT_state;

    private final AssaultParty ap0;

    private final AssaultParty ap1;

    private AssaultParty my_ap;

    private OrdinaryThievesCS c_site;

    private final MasterThiefCCS cc_site;

    private boolean isHoldingCanvas;

    private final int MDj;

    private final Museum museum;

    private boolean isOver;

    private int position;

    private boolean inAction;

    private boolean isNeeded;

    public OrdinaryThief(String name, int id, OrdinaryThievesCS c_site, MasterThiefCCS cc_site, AssaultParty ap0, AssaultParty ap1, Museum museum) {
        super(name);
        this.OT_id = id;
        this.c_site = c_site;
        this.OT_state = OrdinaryThievesStates.CONCENTRATION_SITE;
        this.ap0 = ap0;
        this.ap1 = ap1;
        this.cc_site = cc_site;
        this.museum = museum;
        isHoldingCanvas = false;
        MDj = Simul_Par.getRandomNumber(Simul_Par.MDmin, Simul_Par.MDmax);
        position = 0;
        isOver = false;
        inAction = true;
        isNeeded = false;
        readyToLeave = false;
    }

    public void setReadyToLeave(boolean readyToLeave) {
        this.readyToLeave = readyToLeave;
    }

    public boolean isReadyToLeave() {
        return readyToLeave;
    }

    public int getMDj() {
        return MDj;
    }

    public int getPosition() {
        return position;
    }

    public boolean isOver() {
        return isOver;
    }

    public void setOver(boolean over) {
        isOver = over;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public AssaultParty getAp0() {
        return ap0;
    }

    public AssaultParty getAp1() {
        return ap1;
    }

    public int getOT_id() {
        return OT_id;
    }

    public OrdinaryThievesStates getOT_state() {
        return OT_state;
    }

    public void setOT_state(OrdinaryThievesStates OT_state) {
        this.OT_state = OT_state;
    }

    public boolean isInAction() {
        return inAction;
    }

    public void setInAction(boolean inAction) {
        this.inAction = inAction;
    }

    public boolean isNeeded() {
        return isNeeded;
    }

    public void setNeeded(boolean needed) {
        isNeeded = needed;
    }

    public AssaultParty getMy_ap() {
        return my_ap;
    }

    public boolean isHoldingCanvas() {
        return isHoldingCanvas;
    }

    @Override
    public void run () {
        while (!isOver()){
            switch(getOT_state()){
                case CONCENTRATION_SITE :
                        readyToLeave = false;
                        boolean needed = c_site.amINeeded();
                        if(!needed){
                            GenericIO.writeString("Heist is over. \n");
                            setOver(true);
                            break;
                        }
                        my_ap = cc_site.prepareExcursion();

                case CRAWLING_INWARDS:
                    my_ap.crawlIn();
                    if(getPosition() == my_ap.getDistance()){
                        my_ap.next();
                        GenericIO.writeString("Position -> "+getPosition()+" Thief "+getOT_id()+" in a room!\n");
                    }
                    break;
                case AT_A_ROOM:
                    isHoldingCanvas = museum.rollACanvas();
                    my_ap.reverseDirection();
                    break;
                case CRAWLING_OUTWARDS:
                    my_ap.crawlOut();
                    if(getPosition() == my_ap.getDistance()){
                        my_ap.next();
                        GenericIO.writeString("Position -> "+getPosition()+" Thief "+getOT_id()+" in concentration site!\n");
                    }

                    break;
                case COLLECTION_SITE:
                    cc_site.handACanvas();
                    isHoldingCanvas = false;
                    setOT_state(OrdinaryThievesStates.CONCENTRATION_SITE);
                    break;
            }
        }
    }
}