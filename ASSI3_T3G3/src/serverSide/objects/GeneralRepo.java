package serverSide.objects;

import clientSide.entities.*;
import commInfra.AssaultPartyLog;
import commInfra.MuseumLog;
import commInfra.ThiefLog;
import interfaces.GeneralReposInterface;
import serverSide.main.*;
import genclass.*;
import java.util.Objects;


public class GeneralRepo implements GeneralReposInterface {
    private MasterThiefStates MT_state;
    private ThiefLog[]  otLog;
    private int collected_canvas;
    private AssaultPartyLog[] apLog;
    private MuseumLog mLog;
    private static String FILENAME;

    private int nEntities;
    public GeneralRepo(){
        this.nEntities = 0;
        if ((FILENAME == null) || Objects.equals (FILENAME, ""))
            this.FILENAME = "logger";
        MT_state = MasterThiefStates.PLANNING_THE_HEIST;
        otLog = new ThiefLog[Simul_Par.M - 1];
        for (int i = 0; i < Simul_Par.M - 1; i++) {
            otLog[i] = new ThiefLog(i);
        }
        apLog = new AssaultPartyLog[Simul_Par.N_Parties];
        for (int i = 0; i < Simul_Par.N_Parties; i++) {
            apLog[i] = new AssaultPartyLog(i);
        }
        mLog = new MuseumLog();
        collected_canvas = 0;
    }

    public synchronized void setOtInParty(int idx, int ot_id, int ap_id) {
        apLog[ap_id].getThieves_ids().add(idx, ot_id);
        reportStatus();
    }

    public synchronized void setMT_state(MasterThiefStates MT_state) {
        this.MT_state = MT_state;
        reportStatus();
    }

    public synchronized void setOT_states(int id, OrdinaryThievesStates state) {
        this.otLog[id].setState(state);
        reportStatus();
    }

    public synchronized void setOt_mdj(int ot_id, int mdj){
        otLog[ot_id].setMdj(mdj);
        reportStatus();
    }

    public synchronized void setAPRid(int ap_id, int Rid){
        apLog[ap_id].setRId(Rid);
        reportStatus();
    }

    public synchronized void setOtPosition(int ot_id, int position){
        otLog[ot_id].setPos(position);
        reportStatus();
    }

    public synchronized void setOtHasCanvas(int ot_id, boolean hasCanvas){
        int c = (hasCanvas) ? 1 : 0;
        otLog[ot_id].setHoldingCanvas(c);
        reportStatus();
    }

    public synchronized void setCanvas(int n_canvas){
        collected_canvas = n_canvas;
        reportStatus();
    }

    public synchronized void setDistance(int RId, int distance){
        int[] arr = mLog.getDistances();
        arr[RId] = distance;
        mLog.setDistances(arr);
        reportStatus();
    }

    public synchronized void setNP(int RId, int qi){
        int[] arr = mLog.getN_Canvas();
        arr[RId] = qi;
        mLog.setN_Canvas(arr);
        reportStatus();
    }

    public synchronized void setIsInParty(int ot_id, boolean isInParty){
        char c = (isInParty) ? 'P' : 'W';
        otLog[ot_id].setInParty(c);
        reportStatus();
    }

    /**
     *   Operation initialization of simulation.
     *
     *   New operation.
     *
     *     @param logFileName name of the logging file
     */

    public synchronized void initSimul (String logFileName)
    {
        if (!Objects.equals (logFileName, ""))
            this.FILENAME = logFileName;
        reportInitialStatus ();
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
            ServerGeneralRepo.shutdown();
    }

    private void reportInitialStatus ()
    {
        TextFile log = new TextFile ();                      // instantiation of a text file handler

        if (!log.openForWriting (".", FILENAME))
        { GenericIO.writelnString ("The operation of creating the file " + FILENAME + " failed!");
            System.exit (1);
        }
        log.writelnString ("                                          Heist to the Museum - Description of the internal state");
        log.writelnString ("MstT    Thief 1     Thief 2     Thief 3         Thief 4     Thief 5     Thief 6");
        log.writelnString ("Stat   Stat S MD   Stat S MD   Stat S MD        Stat S MD   Stat S MD   Stat S MD");
        log.writelnString("                    Assault party 1                        Assault party 2                                Museum");
        log.writelnString("             Elem 1     Elem 2     Elem 3             Elem 1     Elem 2     Elem 3             Room 1  Room 2 Room 3 Room 4 Room 5");
        log.writelnString("    RId    Id Pos Cv  Id Pos Cv  Id Pos Cv    RId    Id Pos Cv  Id Pos Cv  Id Pos Cv           NP DT   NP DT  NP DT  NP DT  NP DT");
        if (!log.close ())
        { GenericIO.writelnString ("The operation of closing the file " + FILENAME + " failed!");
            System.exit (1);
        }
        reportStatus();
    }
    private void reportStatus () {

        TextFile log = new TextFile();                      // instantiation of a text file handler
        String lineStatus = "";                              // state line to be printed
        String second_line = "";
        if (!log.openForAppending(".", FILENAME)) {
            GenericIO.writelnString("The operation of opening for appending the file " + FILENAME + " failed!");
            System.exit(1);
        }

        switch (MT_state) {
            case PLANNING_THE_HEIST:
                lineStatus += "PLAN   ";
                break;
            case DECIDING_WHAT_TO_DO:
                lineStatus += "DEC   ";
                break;
            case ASSEMBLING_A_GROUP:
                lineStatus += "ASS   ";
                break;
            case WAITING_FOR_GROUP_ARRIVAL:
                lineStatus += "WAIT   ";
                break;
            case PRESENTING_THE_REPORT:
                lineStatus += "REPO   ";
                break;
        }

        for (int i = 0; i < Simul_Par.M - 1; i++) {
            switch (otLog[i].getState()) {
                case CONCENTRATION_SITE:
                    lineStatus += "C_ST ";
                    break;
                case CRAWLING_INWARDS:
                    lineStatus += "CWL_IN ";
                    break;
                case AT_A_ROOM:
                    lineStatus += "AT_RM ";
                    break;
                case CRAWLING_OUTWARDS:
                    lineStatus += "CWL_OUT ";
                    break;
                case COLLECTION_SITE:
                    lineStatus += "CC_ST ";
                    break;
            }
            lineStatus += String.format("%c ", otLog[i].isInParty());
            lineStatus += "" + otLog[i].getMdj() + "    ";
        }

        for (int i = 0; i < Simul_Par.N_Parties; i++) {
            second_line += "     "+ apLog[i].getRId()+ "   ";
            for (int j = 0; j < Simul_Par.K; j++) {
                int ot_id = apLog[i].getThieves_ids().get(j);

                second_line += String.format(" %d  %d  %d   ", apLog[i].getThieves_ids().get(j), otLog[ot_id].getPos(), otLog[ot_id].getHoldingCanvas());
            }
        }
        second_line += "        ";
        for (int i = 0; i < Simul_Par.N; i++) {
            second_line += String.format(" %d %d ", mLog.getN_Canvas()[i], mLog.getDistances()[i]);
        }

        log.writelnString(lineStatus);
        log.writelnString(second_line);

        if (!log.close()) {
            GenericIO.writelnString("The operation of closing the file " + FILENAME + " failed!");
            System.exit(1);
        }
    }
    public void printSumUp()
    {
        TextFile log = new TextFile ();

        if (!log.openForAppending (".", FILENAME))
        { GenericIO.writelnString ("The operation of opening for appending the file " + FILENAME + " failed!");
            System.exit (1);
        }

        log.writelnString("My friends, tonight's effort produced "+collected_canvas+" priceless paintings!");

        if (!log.close ())
        { GenericIO.writelnString ("The operation of closing the file " + FILENAME + " failed!");
            System.exit (1);
        }
    }



}
