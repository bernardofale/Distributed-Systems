package shared_regions;

import comm_infra.Room;
import entities.OrdinaryThief;
import entities.OrdinaryThievesStates;
import main.Simul_Par;

/**
 * Represents the Museum shared region containing information regarding the rooms inside
 */
public class Museum {

    private Room[] rooms;

    private GeneralRepo gp;
    /**
     * Creates a museum object and initializes the rooms (0..N)
     * @param gp General Repository
     */
    public Museum(GeneralRepo gp){
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
        OrdinaryThief ot = (OrdinaryThief) Thread.currentThread();
        ot.setOT_state(OrdinaryThievesStates.AT_A_ROOM);
        gp.setNP(room_assigned, getRooms()[room_assigned].getN_canvas());
        return getRooms()[room_assigned].roll();
    }
}
