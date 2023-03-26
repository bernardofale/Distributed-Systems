package shared_regions;

import entities.MasterThief;
import entities.MasterThiefStates;
import entities.OrdinaryThief;
import genclass.GenericIO;
import main.Simul_Par;

public class OrdinaryThievesCS {

    private OrdinaryThief[] thieves;

    private int n_available_thieves;

    public OrdinaryThievesCS(){
        thieves = new OrdinaryThief[Simul_Par.M - 1];
        for (int i = 0; i < Simul_Par.M - 1; i++)
            thieves[i] = null;

    }

    public OrdinaryThief[] getThieves() {
        return thieves;
    }


    public synchronized boolean amINeeded(){
        OrdinaryThief ot = ((OrdinaryThief) Thread.currentThread());
        int thief_id = ot.getOT_id();
        if(ot.isInAction()){
            n_available_thieves++;
            thieves[thief_id] = ot;
        }
        notifyAll();
        GenericIO.writeString("Notifying master thief and blocking! Thief "+thief_id+ " is available.\n");
        while (ot.isInAction()) {
            try {
                wait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        GenericIO.writeString("Thief "+thief_id+" is awake.\n");
        if(ot.isNeeded()){
            n_available_thieves--;
            thieves[ot.getOT_id()] = null;
            ot.setInAction(true);
        }

        return ot.isNeeded();
    }

    public synchronized void appraiseSit(boolean isOver){
        MasterThief mt= ((MasterThief) Thread.currentThread());
        if(isOver){
            GenericIO.writeString("Heist is over, presenting the report soon.\n");
            ((MasterThief) Thread.currentThread()).setMT_state(MasterThiefStates.PRESENTING_THE_REPORT);
        }else if(!mt.getAp0().isInMuseum() || !mt.getAp1().isInMuseum()){
            GenericIO.writeString("Party not in museum! \n");
            ((MasterThief) Thread.currentThread()).setMT_state(MasterThiefStates.ASSEMBLING_A_GROUP);
        }else if(mt.getAp0().isInMuseum() || mt.getAp1().isInMuseum()){
            GenericIO.writeString("Party in museum! \n");
            ((MasterThief) Thread.currentThread()).setMT_state(MasterThiefStates.WAITING_FOR_GROUP_ARRIVAL);
        }
    }

    public synchronized void prepareAssaultParty(){
        while (n_available_thieves != (Simul_Par.M - 1)) {
            try {
                wait();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        GenericIO.writeString("Assembling!\n");
        for(int i = 0; i < Simul_Par.M - 1; i++) {
            if(getThieves()[i] != null) {
                getThieves()[i].setInAction(false);
                getThieves()[i].setNeeded(true);
            }
        }
        notifyAll();

        GenericIO.writeString("Waking up thieves to prepare assault party....\n");
    }
}
