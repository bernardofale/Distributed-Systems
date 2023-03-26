package main;

public class Simul_Par {
    /**
     *   Number of thieves
     */

    public static final int M = 7;

    /**
     *   Minimum number of paintings in one room
     */

    public static final int Qmin = 8;

    /**
     *   Maximum number of paintings in one room
     */

    public static final int Qmax = 16;

    /**
     *   Number of thieves in one assault party.
     */

    public static final int K = 3;

    /**
     *   Number of museum rooms
     */

    public static final int N = 5;

    /**
     *   Minimum distance between museum and outside
     */

    public static final int Dmin = 15;

    /**
     *   Maximum distance between museum and outside
     */

    public static final int Dmax = 30;

    /**
     *   Maximum distance between thieves
     */

    public static final int S = 3;

    /**
     *   Minimum displacement
     */

    public static final int MDmin = 2;

    /**
     *   Maximum displacement
     */

    public static final int MDmax = 6;

    /**
     *   It can not be instantiated.
     */

    public static int getRandomNumber(int min, int max){
        return (int) (Math.random() * (max - min + 1) + min);
    }

    private Simul_Par() { }
}
