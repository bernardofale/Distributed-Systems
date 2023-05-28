package commInfra;

import java.io.Serializable;

public class AuxOT implements Serializable {

    private int Id;
    private boolean readyToLeave;

    private int position;

    private int MDj;

    private boolean inParty;
    public AuxOT(int id, int mdJ){
        Id = id;
        readyToLeave = false;
        position = 0;
        MDj = mdJ;
    }

    public int getId(){
        return Id;
    }
    public boolean isReadyToLeave() {
        return readyToLeave;
    }

    public int getPosition() {
        return position;
    }

    public int getMDj() {
        return MDj;
    }

    public void setPosition(int validPo) {
        position = validPo;
    }

    public void setReadyToLeave(boolean b) {
        readyToLeave = b;
    }

    public void setInParty(boolean b) {
        inParty = b;
    }

    public boolean isInParty() {
        return inParty;
    }
}
