package shared_regions;

import comm_infra.Room;
import entities.OrdinaryThief;
import genclass.GenericIO;
import main.Simul_Par;

public class Museum {

    private Room[] rooms;

    public Museum(){
        rooms = new Room[Simul_Par.N];
        for (int i = 0; i < Simul_Par.N; i++) {
            rooms[i] = new Room();
        }
    }

    public Room[] getRooms() {
        return rooms;
    }

    public synchronized boolean rollACanvas(){
        OrdinaryThief ot = (OrdinaryThief) Thread.currentThread();
        GenericIO.writeString("Thief #"+ot.getOT_id()+" is stealing a canvas!\n");
        return getRooms()[ot.getMy_ap().getRoom_assigned()].roll();
    }
}
