package commInfra;

import serverSide.main.Simul_Par;

import java.util.ArrayList;

/**
 * Represents an auxiliary data structure to store useful assault party information
 */
public class AssaultPartyLog {

    private int RId;

    private ArrayList<Integer> thieves_ids;
    private int id;

    /**
     * Create assault party log
     */
    public AssaultPartyLog(int id){
        this.id = id;
        RId = -1;
        thieves_ids = new ArrayList<>(Simul_Par.K);
        for (int i = 0; i < Simul_Par.K; i++) {
            thieves_ids.add(i);
        }
    }

    public int getRId() {
        return RId;
    }

    public void setRId(int RId) {
        this.RId = RId;
    }

    public ArrayList<Integer> getThieves_ids() {
        return thieves_ids;
    }

    public void setThieves_ids(ArrayList<Integer> thieves_ids) {
        this.thieves_ids = thieves_ids;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
