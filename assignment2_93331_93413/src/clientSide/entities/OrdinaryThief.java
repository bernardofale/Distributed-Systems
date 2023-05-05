package clientSide.entities;

import genclass.GenericIO;
import serverSide.main.*;
import clientSide.stubs.*;

/**
 * Represents an Ordinary thief thread
 */
public class OrdinaryThief extends Thread{

    private boolean readyToLeave;
    private final int OT_id;

    private OrdinaryThievesStates OT_state;

    private AssaultPartyStub my_ap;

    private int ap_id;
    private final OrdinaryThiefCSStub c_site;

    private final MasterThiefCCSStub cc_site;

    private boolean isHoldingCanvas;

    private final int MDj;

    private final MuseumStub museum;

    private boolean isOver;

    private int position;

    private final AssaultPartyStub[] parties;

    private boolean inParty;

    private final GeneralRepoStub gp;
    /**
     * Creates an Ordinary thief object and initializes variables/flags
     */
    public OrdinaryThief(String name, int id, OrdinaryThiefCSStub c_site, MasterThiefCCSStub cc_site, AssaultPartyStub[] parties, MuseumStub museum, GeneralRepoStub gp) {
        super(name);
        this.OT_id = id;
        this.c_site = c_site;
        this.OT_state = OrdinaryThievesStates.CONCENTRATION_SITE;
        this.parties = new AssaultPartyStub[Simul_Par.N_Parties];
        for (int i = 0; i < Simul_Par.N_Parties; i++) {
            this.parties[i] = parties[i];
        }
        this.cc_site = cc_site;
        this.museum = museum;
        isHoldingCanvas = false;
        MDj = Simul_Par.getRandomNumber(Simul_Par.MDmin, Simul_Par.MDmax);
        position = 0;
        isOver = false;
        readyToLeave = false;
        ap_id = -1;
        inParty = false;
        this.gp = gp;
        gp.setOt_mdj(getOT_id(), getMDj());
    }

    /**
     * Check if the inParty flag that represents if the thief is in a party
     * @return a boolean that represents the state of thief in a party
     */
    public boolean isInParty() {
        return inParty;
    }

    /**
     * Set the thief in party given the value
     * @param inParty A boolean stating the readiness of the thief
     */
    public void setInParty(boolean inParty) {
        this.inParty = inParty;
    }

    /**
     * Set the readyToLeave flag that represents if the thief is ready to start crawling out
     * @param readyToLeave A boolean stating the readiness of the thief
     */
    public void setReadyToLeave(boolean readyToLeave) {
        this.readyToLeave = readyToLeave;
    }

    /**
     * Checks if the thief is ready to leave the room
     * @return a boolean that represents the readiness of the thief
     */
    public boolean isReadyToLeave() {
        return readyToLeave;
    }

    /**
     * Get the maximum displacement of the ordinary thief
     * @return an integer that represents the maximum displacement
     */
    public int getMDj() {
        return MDj;
    }

    /**
     * Get the current position of the thief
     * @return an integer that represents the current position of the ordinary thief
     */
    public int getPosition() {
        return position;
    }

    /**
     * Checks if the heist is over
     * @return a boolean that returns the state of the heist
     */
    public boolean isOver() {
        return isOver;
    }

    /**
     * Sets the "over" flag as given in the parameters
     * @param over A boolean that represents if the heist is over or not
     */
    public void setOver(boolean over) {
        isOver = over;
    }

    /**
     * Set the ordinary thief position
     * @param position an integer that represents the position of the ordinary thief
     */
    public void setPosition(int position) {
        this.position = position;
    }

    /**
     * Get the ordinary thief id
     * @return an integer that represents the id of the ordinary thief
     */
    public int getOT_id() {
        return OT_id;
    }

    /**
     * Get the current state of the ordinary thief
     * @return an enumerate that represents the ordinary thief's state
     */
    public OrdinaryThievesStates getOT_state() {
        return OT_state;
    }

    /**
     * Set the ordinary thief state accordingly
     * @param OT_state is an enumerate that represents the state of the ordinary thief
     */
    public void setOT_state(OrdinaryThievesStates OT_state) {
        this.OT_state = OT_state;
    }

    /**
     * Get the ordinary thief's assault party
     * @return an Assault party object containing the assault party information
     */
    public AssaultPartyStub getMy_ap() {
        return my_ap;
    }

    /**
     * Checks if the ordinary thief is holding a canvas or not
     * @return a boolean that represents if the hands of thief are busy or not
     */
    public boolean isHoldingCanvas() {
        return isHoldingCanvas;
    }

    /**
     * Main run() method; Starts executing when thread.start() is called
     */
    @Override
    public void run () {
        while (!isOver()){
            switch(getOT_state()){
                case CONCENTRATION_SITE :
                    int ret = c_site.amINeeded();
                    if(ret == 1){
                        setOver(true);
                        break;
                    }
                    ap_id = cc_site.prepareExcursion();
                    my_ap = parties[ap_id];
                    my_ap.join();
                case CRAWLING_INWARDS:
                    my_ap.crawlIn();
                    if(getPosition() == my_ap.getDistance()){
                        my_ap.next();
                        setOT_state(OrdinaryThievesStates.AT_A_ROOM);
                        gp.setOT_states(getOT_id(), OrdinaryThievesStates.AT_A_ROOM);
                        GenericIO.writeString("Position -> "+getPosition()+" Thief "+getOT_id()+" in a room!\n");
                    }
                    break;
                case AT_A_ROOM:
                    isHoldingCanvas = museum.rollACanvas(getMy_ap().getRoom_assigned());
                    gp.setOtHasCanvas(getOT_id(), isHoldingCanvas);
                    my_ap.reverseDirection();
                    break;
                case CRAWLING_OUTWARDS:
                    my_ap.crawlOut();
                    if(getPosition() == my_ap.getDistance()){
                        my_ap.next();
                        setOT_state(OrdinaryThievesStates.COLLECTION_SITE);
                        gp.setOT_states(getOT_id(), OrdinaryThievesStates.COLLECTION_SITE);
                        GenericIO.writeString("Position -> "+getPosition()+" Thief "+getOT_id()+" in concentration site!\n");
                    }
                    break;
                case COLLECTION_SITE:
                    cc_site.handACanvas();
                    isHoldingCanvas = false;
                    gp.setOtHasCanvas(getOT_id(), isHoldingCanvas);
                    setPosition(0);
                    setInParty(false);
                    gp.setIsInParty(getOT_id(), isInParty());
                    gp.setOT_states(getOT_id(), OrdinaryThievesStates.CONCENTRATION_SITE);
                    break;
            }
        }
    }
}
