package shared_regions;

import entities.MasterThief;
import entities.MasterThiefStates;
import entities.OrdinaryThief;
import entities.OrdinaryThievesStates;
import genclass.GenericIO;
import main.Simul_Par;

import java.util.ArrayList;

public class MasterThiefCCS {

    private int collected_canvas;

    private boolean partiesReady;
    private boolean sendAP;
    private int back;

    private boolean collect;

    public MasterThiefCCS(){
        collected_canvas = 0;
        partiesReady = false;
        sendAP = false;
        collect = false;

    }

    public synchronized void startOperations(){
        System.out.println("Starting operations...");
        ((MasterThief)Thread.currentThread()).setMT_state(MasterThiefStates.DECIDING_WHAT_TO_DO);
    }
    public synchronized void takeARest(){
        ((MasterThief) Thread.currentThread()).setMT_state(MasterThiefStates.WAITING_FOR_GROUP_ARRIVAL);
        GenericIO.writeString("Taking a rest!\n");
        while(back != (Simul_Par.M - 1)){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        GenericIO.writeString("Thieves are back! Master thief waking up! \n");
    }

    public synchronized AssaultParty prepareExcursion(){
        OrdinaryThief ot = (OrdinaryThief) Thread.currentThread();
        ot.setOT_state(OrdinaryThievesStates.CRAWLING_INWARDS);
        ot.setReadyToLeave(false);
        AssaultParty my_ap;
        if(ot.getAp0().isFull()){
            ot.getAp1().getAP().add(ot);
            GenericIO.writeString("Thief "+ ot.getOT_id()+" placed in AP#"+ot.getAp1().getId()+"\n");
            my_ap = ot.getAp1();
        }else{
            ot.getAp0().getAP().add(ot);
            GenericIO.writeString("Thief "+ ot.getOT_id()+" placed in AP#"+ot.getAp0().getId()+"\n");
            my_ap = ot.getAp0();
        }
        if(ot.getAp0().isFull() && ot.getAp1().isFull()){
            partiesReady = true;
            GenericIO.writeString("Last thief "+ ot.getOT_id()+" waking up master thief, parties are assembled!\n");
            notifyAll();
        }

        while(!sendAP){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return my_ap;
    }
    public synchronized void sendAssaultParty(){
        MasterThief mt = (MasterThief) Thread.currentThread();
        while(!partiesReady){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        back = 0;
        collect = false;
        mt.getAp0().setInMuseum(true);
        mt.getAp1().setInMuseum(true);
        GenericIO.writeString("Master thief is awake, sending assault parties!\n");
        sendAP = true;
        notifyAll();
        ((MasterThief) Thread.currentThread()).setMT_state(MasterThiefStates.DECIDING_WHAT_TO_DO);

    }
    public synchronized void collectACanvas(){
        MasterThief mt = (MasterThief) Thread.currentThread();
        GenericIO.writeString("Collecting...");
        int ap0_assigned_room_canvas = mt.getMuseum().getRooms()[mt.getAp0().getRoom_assigned()].getN_canvas();
        int ap1_assigned_room_canvas = mt.getMuseum().getRooms()[mt.getAp1().getRoom_assigned()].getN_canvas();
        mt.getAp0().setInMuseum(false);
        mt.getAp1().setInMuseum(false);
        sendAP = false;
        partiesReady = false;

        if(ap0_assigned_room_canvas == 0){
            mt.getMuseum().getRooms()[mt.getAp0().getRoom_assigned()].setEmpty(true);
        }
        if(ap1_assigned_room_canvas == 0){
            mt.getMuseum().getRooms()[mt.getAp1().getRoom_assigned()].setEmpty(true);
        }
        for (int i = 0; i < mt.getAssignedRooms().length; i++) {
            mt.getMuseum().getRooms()[mt.getAssignedRooms()[i]].setAssigned(false);
        }

        mt.getAp0().setAP(new ArrayList<>(Simul_Par.K));
        mt.getAp1().setAP(new ArrayList<>(Simul_Par.K));

        mt.setMT_state(MasterThiefStates.DECIDING_WHAT_TO_DO);
        GenericIO.writeString("Collected "+collected_canvas+ " canvas!\n");
        collect = true;
        notifyAll();
    }

    public synchronized int sumUpResults(){
        return collected_canvas;
    }

    public synchronized void handACanvas(){
        OrdinaryThief ot = (OrdinaryThief) Thread.currentThread();
        GenericIO.writeString("Thief #"+ot.getOT_id()+" handing canvas!\n");
        back++;
        if(ot.isHoldingCanvas()) collected_canvas++;
        if(back == 6) notifyAll();

        while(!collect){
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
