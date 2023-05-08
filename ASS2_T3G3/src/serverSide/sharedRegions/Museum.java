package serverSide.sharedRegions;

import clientSide.entities.*;
import clientSide.stubs.GeneralRepoStub;
import comm_infra.*;
import serverSide.entities.MuseumProxy;
import serverSide.entities.OrdinaryThievesCSProxy;
import serverSide.main.ServerAssaultParty0;
import serverSide.main.ServerAssaultParty1;
import serverSide.main.ServerMuseum;
import serverSide.main.Simul_Par;

/**
 * Represents the Museum shared region containing information regarding the rooms inside
 */
public class Museum {

    private Room[] rooms;

    private GeneralRepoStub gp;

    private MuseumProxy MT_Proxy;

    private MuseumProxy[] OT_Proxy;

    /**
     *   Number of entity groups requesting the shutdown.
     */

    private int nEntities;

    /**
     * Creates a museum object and initializes the rooms (0..N)
     * @param gp General Repository
     */
    public Museum(GeneralRepoStub gp){
        MT_Proxy = null;

        OT_Proxy = new MuseumProxy[Simul_Par.M - 1];
        for (int i = 0; i < Simul_Par.M - 1; i++) {
            OT_Proxy[i] = null;
        }
        this.nEntities = 0;
        rooms = new Room[Simul_Par.N];
        this.gp = gp;
        for (int i = 0; i < Simul_Par.N; i++) {
            rooms[i] = new Room(i);
            this.gp.setDistance(i, rooms[i].getDistance());
            this.gp.setNP(i, rooms[i].getN_canvas());
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
    public synchronized boolean rollACanvas(int room_assigned){
        int thief_id = ((MuseumProxy) Thread.currentThread()).getOTId();
        OT_Proxy[thief_id] = (MuseumProxy) Thread.currentThread();
        OT_Proxy[thief_id].setOTState(OrdinaryThievesStates.AT_A_ROOM);
        gp.setNP(room_assigned, getRooms()[room_assigned].getN_canvas());
        return getRooms()[room_assigned].roll();
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
        if (MT_Proxy != null)
            MT_Proxy.interrupt ();
    }


    /**
     *   Operation server shutdown.
     *
     *   New operation.
     */

    public synchronized void shutdown ()
    {
        nEntities += 1;
        if (nEntities >= Simul_Par.E)
            ServerMuseum.waitConnection = false;
        notifyAll ();                                        // the master thief may now terminate
    }
}
