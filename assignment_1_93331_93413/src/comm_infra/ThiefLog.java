package comm_infra;

import entities.OrdinaryThievesStates;

public class ThiefLog {
    private int id;
    private int pos;
    private int holdingCanvas;

    private int mdj;
    private boolean inParty;
    private OrdinaryThievesStates state;

    public ThiefLog(int id){
        this.id = id;
        pos = 0;
        holdingCanvas = 0;
        state = OrdinaryThievesStates.CONCENTRATION_SITE;
        mdj = -1;
    }

    public int getMdj() {
        return mdj;
    }

    public void setMdj(int mdj) {
        this.mdj = mdj;
    }

    public boolean isInParty() {
        return inParty;
    }

    public void setInParty(boolean inParty) {
        this.inParty = inParty;
    }

    public OrdinaryThievesStates getState() {
        return state;
    }

    public void setState(OrdinaryThievesStates state) {
        this.state = state;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public int getHoldingCanvas() {
        return holdingCanvas;
    }

    public void setHoldingCanvas(int holdingCanvas) {
        this.holdingCanvas = holdingCanvas;
    }
}
