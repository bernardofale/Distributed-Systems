package comm_infra;

import serverSide.main.*;
import java.util.ArrayList;

/**
 * Represents a party of thieves; Useful for keeping data outside the assault party monitor.
 */
public class Party {
    private final ArrayList<Integer> thieves;
    private boolean on;

    private final int id;

    /**
     * Creates a party with the given id
     * @param id of the party
     */
    public Party(int id) {
        this.id = id;
        on = false;
        thieves = new ArrayList<>(Simul_Par.K);
    }

    public int getId() {
        return id;
    }

    /**
     * Get the current thieves id's in this party
     * @return an array list of
     */
    public ArrayList<Integer> getThieves() {
        return thieves;
    }

    /**
     * Checks if a party is full of K thieves
     * @return a boolean representing the fullness of the thieves array list
     */
    public boolean isFull(){
        return getThieves().size() == Simul_Par.K;
    }

    /**
     * Checks if a party is currently inside the museum or ongoing in a mission
     * @return a boolean that represents the state of the party (Ongoing or not)
     */
    public boolean isOn() {
        return on;
    }

    /**
     * Sets the state of the party with the given on value
     * @param on Ongoing or not
     */
    public void setOn(boolean on) {
        this.on = on;
    }
}
