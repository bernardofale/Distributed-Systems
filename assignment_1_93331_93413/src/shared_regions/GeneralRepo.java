package shared_regions;

import comm_infra.Room;
import entities.MasterThiefStates;
import entities.OrdinaryThief;
import entities.OrdinaryThievesStates;
import genclass.GenericIO;
import genclass.TextFile;
import main.Simul_Par;

import java.util.Objects;

public class GeneralRepo {
    private MasterThiefStates MT_state;
    private OrdinaryThievesStates[] OT_states;
    private static String FILENAME;
    private String legend;

    private AssaultParty ap1;
    private AssaultParty ap0;
    private MasterThiefCCS ccs;
    private OrdinaryThievesCS cs;
    private Museum m;


    public GeneralRepo(AssaultParty ap0, AssaultParty ap1, MasterThiefCCS ccs,OrdinaryThievesCS cs, Museum m){
        if ((FILENAME == null) || Objects.equals (FILENAME, ""))
            this.FILENAME = "logger";
        MT_state = MasterThiefStates.PLANNING_THE_HEIST;
        OT_states = new OrdinaryThievesStates[Simul_Par.M - 1];
        for (int i = 0; i < Simul_Par.M - 1; i++) {
            OT_states[i] = OrdinaryThievesStates.CONCENTRATION_SITE;
        }
        this.ap0 = ap0;
        this.ap1 = ap1;
        this.ccs = ccs;
        this.cs = cs;
        this.m = m;

        reportInitialStatus();
    }

    public synchronized void setMT_state(MasterThiefStates MT_state) {
        this.MT_state = MT_state;
        reportStatus();
    }

    public synchronized void setOT_states(int id, OrdinaryThievesStates state) {
        this.OT_states[id] = state;
        reportStatus();
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
        if (cs.getUniqueOTs().size() == Simul_Par.M - 1) {
            TextFile log = new TextFile();                      // instantiation of a text file handler
            String lineStatus = "";                              // state line to be printed
            String second_line = "";
            if (!log.openForAppending(".", FILENAME)) {
                GenericIO.writelnString("The operation of opening for appending the file " + FILENAME + " failed!");
                System.exit(1);
            }

            switch (MT_state) {
                case PLANNING_THE_HEIST:
                    lineStatus += " PLAN ";
                    break;
                case DECIDING_WHAT_TO_DO:
                    lineStatus += " DEC ";
                    break;
                case ASSEMBLING_A_GROUP:
                    lineStatus += " ASS ";
                    break;
                case WAITING_FOR_GROUP_ARRIVAL:
                    lineStatus += " WAIT ";
                    break;
                case PRESENTING_THE_REPORT:
                    lineStatus += " REPO ";
                    break;
            }

            for (int i = 0; i < Simul_Par.M - 1; i++) {
                switch (OT_states[i]) {
                    case CONCENTRATION_SITE:
                        lineStatus += " C_SITE ";
                        break;
                    case CRAWLING_INWARDS:
                        lineStatus += " CRAWL_IN ";
                        break;
                    case AT_A_ROOM:
                        lineStatus += " AT_ROOM ";
                        break;
                    case CRAWLING_OUTWARDS:
                        lineStatus += " CRAWL_OUT ";
                        break;
                    case COLLECTION_SITE:
                        lineStatus += " CC_SITE ";
                        break;
                }


                for (OrdinaryThief ot : cs.getUniqueOTs()) {
                    if (ot.getOT_id() == i) {
                        if (ot.isInParty()) {
                            lineStatus += " 'P' ";
                        } else {
                            lineStatus += " 'W' ";
                        }
                        lineStatus += " " + ot.getMDj() + " ";
                    }
                }
            }
            second_line += " " + ap0.getRoom_assigned() + " ";
            for (OrdinaryThief ot : ap0.getAP()) {
                second_line += " " + ot.getOT_id() + "  " + ot.getPosition() + " ";
                if (ot.isHoldingCanvas()) {
                    second_line += " 1 ";
                } else {
                    second_line += " 0 ";
                }
            }
            second_line += " " + ap1.getRoom_assigned() + " ";
            for (OrdinaryThief ot : ap1.getAP()) {
                second_line += " " + ot.getOT_id() + "  " + ot.getPosition() + " ";
                if (ot.isHoldingCanvas()) {
                    second_line += " 1 ";
                } else {
                    second_line += " 0 ";
                }
            }
            for (Room r : m.getRooms()) {
                second_line += " " + r.getN_canvas() + "  " + r.getDistance() + " ";
            }

            log.writelnString(lineStatus);
            log.writelnString(second_line);

            if (!log.close()) {
                GenericIO.writelnString("The operation of closing the file " + FILENAME + " failed!");
                System.exit(1);
            }
        }
    }
    public void printSumUp()
    {
        TextFile log = new TextFile ();

        if (!log.openForAppending (".", FILENAME))
        { GenericIO.writelnString ("The operation of opening for appending the file " + FILENAME + " failed!");
            System.exit (1);
        }

        log.writelnString("My friends, tonight's effort produced "+ccs.getCollected_canvas()+" priceless paintings!");

        if (!log.close ())
        { GenericIO.writelnString ("The operation of closing the file " + FILENAME + " failed!");
            System.exit (1);
        }
    }
    private boolean allNotNull(OrdinaryThief[] arr) {
        boolean allNotNull = true;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == null) {
                allNotNull = false;
                break;
            }
        }
        return allNotNull;
    }


}
