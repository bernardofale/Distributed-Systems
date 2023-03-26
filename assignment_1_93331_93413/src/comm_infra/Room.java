package comm_infra;

import main.Simul_Par;


public class Room {

    private int n_canvas;

    private int distance;

    private Canvas[] canvas;

    private boolean isEmpty;

    private boolean assigned;
    public Room(){

        n_canvas = Simul_Par.getRandomNumber(Simul_Par.Qmin, Simul_Par.Qmax);
        distance = Simul_Par.getRandomNumber(Simul_Par.Dmin, Simul_Par.Dmax);
        canvas = new Canvas[n_canvas];
        for (int i = 0; i < n_canvas; i++) {
            canvas[i] = new Canvas(i);
        }
        assigned = false;
        isEmpty = false;
    }

    public boolean isAssigned() {
        return assigned;
    }

    public void setAssigned(boolean assigned) {
        this.assigned = assigned;
    }

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
    public boolean isEmpty() {
        return !isEmpty;
    }

    public void setEmpty(boolean empty) {
        isEmpty = empty;
    }

    public int getN_canvas() {
        return n_canvas;
    }

    public int getDistance() {
        return distance;
    }

    public Canvas[] getCanvas() {
        return canvas;
    }

}
