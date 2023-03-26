package shared_regions;

import entities.OrdinaryThief;
import entities.OrdinaryThievesStates;
import genclass.GenericIO;
import main.Simul_Par;

import java.util.*;

public class AssaultParty {

    private ArrayList<OrdinaryThief> AP;

    private boolean inMuseum;

    private final int id;

    private int next_inLine;

    private int distance; //distance to assigned room

    private int room_assigned;

    public AssaultParty(int id){
        this.id = id;
        AP = new ArrayList<>(Simul_Par.K);
        next_inLine = 0;
        room_assigned = -1;
        inMuseum = false;
        distance = -1;
    }

    public ArrayList<OrdinaryThief> getAP() {
        return AP;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    public boolean isInMuseum() {
        return inMuseum;
    }

    public void setInMuseum(boolean inMuseum) {
        this.inMuseum = inMuseum;
    }

    public int getRoom_assigned() {
        return room_assigned;
    }

    public void setRoom_assigned(int room_assigned) {
        this.room_assigned = room_assigned;
    }

    public int getId() {
        return id;
    }

    public void setAP(ArrayList<OrdinaryThief> AP) {
        this.AP = AP;
    }

    public synchronized void crawlIn(){
        OrdinaryThief ot = (OrdinaryThief) Thread.currentThread();
        int ot_id = ot.getOT_id();

        while (ot_id != getAP().get(next_inLine).getOT_id() || !isFull() || room_assigned == -1) {
            try {
                wait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        int[] predictedPositions = getAP().stream()
                .mapToInt(OrdinaryThief::getPosition)
                .toArray();
        boolean allPositionsValid;
        int predictedPosition = -1;

        // Iterate over possible movements
        for (int i = getAP().get(next_inLine).getMDj(); i > 0; i--) {
            // Calculate the predicted position for this thief
            predictedPosition = predictedPositions[next_inLine] + i;
            if (predictedPosition >= distance) {
                predictedPosition = distance;
            }

            // Check if all positions are valid
            allPositionsValid = true;
            for (int j = 0; j < Simul_Par.K; j++) {
                if (j == next_inLine) {
                    continue;
                }
                int distance = Math.abs(predictedPosition - predictedPositions[j]);
                if (distance > Simul_Par.S || (distance == 0 && predictedPosition != 0 && predictedPosition != getDistance())) {
                    allPositionsValid = false;
                    break;
                }
            }

            // If all positions are valid, update the thief's position and exit the loop
            if (allPositionsValid) {
                predictedPositions[next_inLine] = predictedPosition;
                getAP().get(next_inLine).setPosition(predictedPosition);
                break;
            }
        }

        // If the predicted position is the last position, move the thief directly to that position
        if (predictedPosition == distance) {
            ot.setOT_state(OrdinaryThievesStates.AT_A_ROOM);
        }

        next_inLine = (next_inLine - 1 + Simul_Par.K) % Simul_Par.K;

        notifyAll();
    }

    public boolean isFull() {
        return getAP().size() == Simul_Par.K;
    }

    public synchronized void next() {
        while (!inRoom()) {
            try {
                if (getAP().get(next_inLine).getPosition() == getDistance()) {
                    next_inLine = (next_inLine - 1 + Simul_Par.K) % Simul_Par.K;
                    notifyAll();
                }
                wait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public synchronized boolean inRoom() {
        for (OrdinaryThief ot : getAP()) {
            if (ot.getPosition() != getDistance()) {
                return false;
            }
        }
        return true;
    }
    public synchronized void reverseDirection(){
        OrdinaryThief ot = (OrdinaryThief) Thread.currentThread();
        ot.setReadyToLeave(true);
        while(!allReady()){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        GenericIO.writeString("Reversing direction!\n");
        ot.setOT_state(OrdinaryThievesStates.CRAWLING_OUTWARDS);
        ot.setPosition(0);
        next_inLine = Simul_Par.K - 1;

        notifyAll();
    }

    private boolean allReady() {
        for (OrdinaryThief ot : getAP()) {
            if(!ot.isReadyToLeave()){
                return false;
            }
        }
        return true;
    }

    public synchronized void crawlOut(){
        OrdinaryThief ot = (OrdinaryThief) Thread.currentThread();
        int ot_id = ot.getOT_id();

        while (ot_id != getAP().get(next_inLine).getOT_id() || !isFull() || room_assigned == -1) {
            try {
                wait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        int[] predictedPositions = getAP().stream()
                .mapToInt(OrdinaryThief::getPosition)
                .toArray();
        boolean allPositionsValid;
        int predictedPosition = -1;

        // Iterate over possible movements
        for (int i = getAP().get(next_inLine).getMDj(); i > 0; i--) {
            // Calculate the predicted position for this thief
            predictedPosition = predictedPositions[next_inLine] + i;
            if (predictedPosition >= distance) {
                predictedPosition = distance;
            }

            // Check if all positions are valid
            allPositionsValid = true;
            for (int j = 0; j < Simul_Par.K; j++) {
                if (j == next_inLine) {
                    continue;
                }
                int distance = Math.abs(predictedPosition - predictedPositions[j]);
                if (distance > Simul_Par.S || (distance == 0 && predictedPosition != 0 && predictedPosition != getDistance())) {
                    allPositionsValid = false;
                    break;
                }
            }

            // If all positions are valid, update the thief's position and exit the loop
            if (allPositionsValid) {
                predictedPositions[next_inLine] = predictedPosition;
                getAP().get(next_inLine).setPosition(predictedPosition);
                break;
            }
        }

        // If the predicted position is the last position, move the thief directly to that position
        if (predictedPosition == distance) {
            ot.setOT_state(OrdinaryThievesStates.COLLECTION_SITE);
        }

        next_inLine = (next_inLine - 1 + Simul_Par.K) % Simul_Par.K;

        notifyAll();

    }
}
