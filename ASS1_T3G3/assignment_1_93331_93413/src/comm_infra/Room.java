package comm_infra;

import main.Simul_Par;

/**
 * Represent a room Ni of the museum. Keeps data related to distance, number of canvas, if it is assigned or not;
 */
public class Room {

    private int n_canvas;

    private final int distance;

    private final Canvas[] canvas;

    private boolean isEmpty;

    private boolean assigned;
    private final int id;
    /**
     * Creates an object of Room and instantiates needed variables and a random number of canvas and
     * a random distance to the room;
     * @param id Id of the room (0..N)
     */
    public Room(int id){
        n_canvas = Simul_Par.getRandomNumber(Simul_Par.Qmin, Simul_Par.Qmax);
        distance = Simul_Par.getRandomNumber(Simul_Par.Dmin, Simul_Par.Dmax);
        canvas = new Canvas[n_canvas];
        for (int i = 0; i < n_canvas; i++) {
            canvas[i] = new Canvas(i);
        }
        assigned = false;
        isEmpty = false;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    /**
     * Checks if a room is currently available for stealing or not
     */
    public boolean isAssigned() {
        return assigned;
    }

    /**
     * Sets the state of the room as assigned or not
     * @param assigned true or false
     */
    public void setAssigned(boolean assigned) {
        this.assigned = assigned;
    }

    /**
     * Roll a canvas from the room. Set the canvas in the array of canvas as stolen and decrement the number
     * of available canvas
     */
    public boolean roll(){
        for (Canvas c : getCanvas()) {
            if(!c.isStolen()){
                c.setStolen(true);
                n_canvas--;
                return true;
            }
        }
        return false;
    }
    /**
     * Checks if the room is empty
     * @return a boolean that informs if the current room is empty or not
     */
    public boolean isEmpty() {
        return !isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }

    /**
     * Get the number of available canvas in the room
     * @return an integer representing the number of canvas
     */
    public int getN_canvas() {
        return n_canvas;
    }

    /**
     * Get the distance between the room and the outside
     * @return an integer representing the distance towards the outside/inside
     */
    public int getDistance() {
        return distance;
    }

    /**
     * Get the set of canvas in the room
     * @return an array of Canvas objects
     */
    public Canvas[] getCanvas() {
        return canvas;
    }

}
