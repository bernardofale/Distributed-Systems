package clientSide.entities;

import genclass.GenericIO;
import serverSide.main.Simul_Par;
import interfaces.*;

import java.rmi.RemoteException;

/**
 * Represents an Ordinary thief thread
 */
public class OrdinaryThief extends Thread{

    private boolean readyToLeave;
    private final int OT_id;

    private OrdinaryThievesStates OT_state;

    private AssaultPartyInterface my_ap;

    private int ap_id;
    private final ConcentrationSiteInterface c_site;

    private final CollectionSiteInterface cc_site;

    private boolean isHoldingCanvas;

    private final int MDj;

    private final MuseumInterface museum;

    private boolean isOver;

    private int position;

    private final AssaultPartyInterface[] parties;

    private boolean inParty;

    private GeneralReposInterface gp;
    /**
     * Creates an Ordinary thief object and initializes variables/flags
     */
    public OrdinaryThief(String name, int id, ConcentrationSiteInterface c_site, CollectionSiteInterface cc_site, AssaultPartyInterface[] parties, MuseumInterface museum, GeneralReposInterface gp) throws RemoteException {
        super(name);
        this.OT_id = id;
        this.c_site = c_site;
        this.OT_state = OrdinaryThievesStates.CONCENTRATION_SITE;
        this.parties = new AssaultPartyInterface[Simul_Par.N_Parties];
        System.arraycopy(parties, 0, this.parties, 0, Simul_Par.N_Parties);
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
        //gp.setOt_mdj(getOT_id(), getMDj());

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
    public AssaultPartyInterface getMy_ap() {
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
                    int ret = 0;
                    ret = amINeeded();
                    GenericIO.writelnInt(ret);
                    if(ret == 1){
                        GenericIO.writelnString("it's over");
                        isOver = true;
                        break;
                    }
                    ap_id = prepareExcursion();
                    my_ap = parties[ap_id];
                    join_ap();

                case CRAWLING_INWARDS:

                    crawlIn();
                    try {
                        if(getPosition() == my_ap.getDistance()){
                            next();
                            setOT_state(OrdinaryThievesStates.AT_A_ROOM);
                            //gp.setOT_states(getOT_id(), OrdinaryThievesStates.AT_A_ROOM);
                            //GenericIO.writeString("Position -> "+getPosition()+" Thief "+getOT_id()+" in a room!\n");
                        }
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case AT_A_ROOM:
                    rollACanvas();
                    //gp.setOtHasCanvas(getOT_id(), isHoldingCanvas);
                    reverseDirection();
                    break;

                case CRAWLING_OUTWARDS:
                    crawlOut();
                    try {
                        if(getPosition() == my_ap.getDistance()){
                            next();
                            setOT_state(OrdinaryThievesStates.COLLECTION_SITE);
                            //gp.setOT_states(getOT_id(), OrdinaryThievesStates.COLLECTION_SITE);
                            //GenericIO.writeString("Position -> "+getPosition()+" Thief "+getOT_id()+" in concentration site!\n");
                        }
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                    break;

                case COLLECTION_SITE:
                    handACanvas();
                    isHoldingCanvas = false;
                    //gp.setOtHasCanvas(getOT_id(), isHoldingCanvas);
                    setPosition(0);
                    setInParty(false);
                    //gp.setIsInParty(getOT_id(), isInParty());
                    //gp.setOT_states(getOT_id(), OrdinaryThievesStates.CONCENTRATION_SITE);
                    break;
            }
        }
        GenericIO.writelnString("ACABEI");
    }

    private int amINeeded() {
        ReturnInt r = null;

        try{
            r = c_site.amINeeded(getOT_id());
        }catch (RemoteException e)
        { GenericIO.writelnString ("Ordinary thief " + getOT_id() + " remote exception on amINeeded: " + e.getMessage ());
            System.exit (1);
        }


        OT_state = r.getOrdinaryThievesStatesVal();

        return r.getIntVal();
    }

    private int prepareExcursion() {
        ReturnInt r = null;

        try {
            r = cc_site.prepareExcursion(getOT_id());
        } catch (RemoteException e)
        { GenericIO.writelnString ("Ordinary thief  " + getOT_id() + " remote exception on prepareExcursion: " + e.getMessage ());
            System.exit (1);
        }


        OT_state = r.getOrdinaryThievesStatesVal();

        return r.getIntVal();
    }

    private void join_ap() {
        ReturnBool[] r = null;

        try {
            r = my_ap.join(getOT_id(), getMDj());
        } catch (RemoteException e)
        { GenericIO.writelnString ("Ordinary thief  " + getOT_id() + " remote exception on join: " + e.getMessage ());
            System.exit (1);
        }

        readyToLeave = r[0].getBooleanVal();

        inParty = r[1].getBooleanVal();
    }

    private void crawlIn(){
        ReturnInt r = null;
        try {
            r = my_ap.crawlIn(getOT_id());
        } catch (RemoteException e)
        { GenericIO.writelnString ("Ordinary thief  " + getOT_id() + " remote exception on crawl in: " + e.getMessage ());
            System.exit (1);
        }
        position = r.getIntVal();
    }

    private void next(){
        try {
            my_ap.next();
        } catch (RemoteException e)
        { GenericIO.writelnString ("Ordinary thief  " + getOT_id() + " remote exception on next: " + e.getMessage ());
            System.exit (1);
        }
    }
    private void rollACanvas(){

        ReturnBool r = null;
        try {
            r = museum.rollACanvas(getOT_id(), my_ap.getRoom_assigned());
        } catch (RemoteException e) {
            GenericIO.writelnString ("Ordinary thief  " + getOT_id() + " remote exception on rollACanvas: " + e.getMessage ());
            System.exit (1);
        }

        isHoldingCanvas = r.getBooleanVal();
        setOT_state(r.getOrdinaryThievesStatesVal());
    }

    private void reverseDirection(){

        ReturnBool r = null;
        try {
            r = my_ap.reverseDirection(getOT_id());
        } catch (RemoteException e) {
            GenericIO.writelnString ("Ordinary thief  " + getOT_id() + " remote exception on reverseDirection: " + e.getMessage ());
            System.exit (1);
        }

        OT_state = r.getOrdinaryThievesStatesVal();
        readyToLeave = r.getBooleanVal();
        position = 0;

    }

    private void crawlOut(){
        ReturnInt r = null;

        try {
            r = my_ap.crawlOut(getOT_id());
        } catch (RemoteException e) {
            GenericIO.writelnString ("Ordinary thief  " + getOT_id() + " remote exception on crawlOut: " + e.getMessage ());
            System.exit (1);
        }

        position = r.getIntVal();
    }

    private void handACanvas(){
        ReturnInt r = null;
        try {
            r = cc_site.handACanvas(getOT_id(), isHoldingCanvas());
        } catch (RemoteException e) {
            GenericIO.writelnString ("Ordinary thief  " + getOT_id() + " remote exception on handACanvas: " + e.getMessage ());
            System.exit (1);
        }
        OT_state = r.getOrdinaryThievesStatesVal();
    }
}
