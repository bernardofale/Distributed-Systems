package comm_infra;

import main.Simul_Par;

/**
 * Represents an auxiliary data structure for keeping logging information of the Museum and its rooms;
 */
public class MuseumLog {

    private int[] n_Canvas;
    private int[] distances;

    public MuseumLog(){
        n_Canvas = new int[Simul_Par.N];
        for (int i = 0; i < Simul_Par.N; i++) {
            n_Canvas[i] = -1;
        }
        distances = new int[Simul_Par.N];
        for (int i = 0; i < Simul_Par.N; i++) {
            distances[i] = -1;
        }
    }

    public int[] getN_Canvas() {
        return n_Canvas;
    }

    public void setN_Canvas(int[] n_Canvas) {
        this.n_Canvas = n_Canvas;
    }

    public int[] getDistances() {
        return distances;
    }

    public void setDistances(int[] distances) {
        this.distances = distances;
    }
}
