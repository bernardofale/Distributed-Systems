package commInfra;

import java.io.Serializable;

/** Represents a canvas and its parameters. Useful for knowing if a canvas is being stolen or not.
 *
 */
public class Canvas implements Serializable {
    private boolean isStolen;
    private final int id;

    /**
     * Creates a canvas with the specified id.
     * @param id The canvas id (0..Qi)
     */
    public Canvas(int id){
        this.id = id;
        isStolen = false;
    }

    /**
     * Checks if a canvas is already stolen from the museum
     * @return a boolean that represents if a canvas is stolen or not
     */
    public boolean isStolen() {
        return isStolen;
    }

    /**
     * Sets the current state of the canvas. (stolen or not stolen)
     */
    public void setStolen(boolean stolen) {
        isStolen = stolen;
    }

}
