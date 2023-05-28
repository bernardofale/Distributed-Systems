package serverSide.objects;

import clientSide.entities.*;

import commInfra.Room;
import interfaces.GeneralReposInterface;
import interfaces.MuseumInterface;
import interfaces.ReturnBool;
import serverSide.main.ServerMuseum;
import serverSide.main.Simul_Par;

import java.rmi.RemoteException;

/**
 * Represents the Museum shared region containing information regarding the rooms inside
 */
public class Museum implements MuseumInterface {

    private Room[] rooms;

    private GeneralReposInterface gp;

    private Thread MT;

    private Thread[] OTs;

    private OrdinaryThievesStates[] thieves_states;

    private MasterThiefStates master_state;

    /**
     *   Number of entity groups requesting the shutdown.
     */

    private int nEntities;

    /**
     * Creates a museum object and initializes the rooms (0..N)
     * @param gp General Repository
     */
    public Museum(GeneralReposInterface gp) throws RemoteException {
        MT = null;

        OTs = new Thread[Simul_Par.M - 1];
        thieves_states = new OrdinaryThievesStates[Simul_Par.M - 1];
        for (int i = 0; i < Simul_Par.M - 1; i++) {
            OTs[i] = null;
            thieves_states[i] = null;
        }
        master_state = null;
        this.nEntities = 0;
        rooms = new Room[Simul_Par.N];
        this.gp = gp;
        for (int i = 0; i < Simul_Par.N; i++) {
            rooms[i] = new Room(i);
            //this.gp.setDistance(i, rooms[i].getDistance());
            //this.gp.setNP(i, rooms[i].getN_canvas());
        }

    }

    /**
     * Gets the all the rooms inside the museum
     * @return an array of type Room
     */
    public Room[] getRooms() {
        return rooms;
    }

    /**
     * Roll a canvas from a room in the museum
     * @return a boolean representing if a canvas has been rolled or not
     */
    public synchronized ReturnBool rollACanvas(int ot_id, int room_assigned) throws RemoteException {
        OTs[ot_id] = Thread.currentThread();
        thieves_states[ot_id] = OrdinaryThievesStates.AT_A_ROOM;
        //gp.setNP(room_assigned, getRooms()[room_assigned].getN_canvas());

        return new ReturnBool(getRooms()[room_assigned].roll(), thieves_states[ot_id]);
    }

    public synchronized void endOperation ()
    {
        while (nEntities == 0)
        { /* the master thief waits for the termination of the ordinary thieves */
            try
            { wait ();
            }
            catch (InterruptedException ignored) {}
        }
        if (MT != null)
            MT.interrupt ();
    }


    /**
     *   Operation server shutdown.
     *
     *   New operation.
     */

    public synchronized void shutdown ()
    {
        nEntities += 1;
        if (nEntities >= Simul_Par.M)
            ServerMuseum.shutdown();
        notifyAll ();                                        // the master thief may now terminate
    }
}
