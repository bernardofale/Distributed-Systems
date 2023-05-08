package comm_infra;

import clientSide.entities.MasterThiefStates;
import clientSide.entities.OrdinaryThievesStates;
import genclass.GenericIO;
import serverSide.main.Simul_Par;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;

public class Message implements Serializable {
    private int log_canvas;
    private int Log_Rid;
    private boolean Log_holding_canvas;
    private boolean isInParty = false;
    private int Log_mdj;
    private boolean rolled;
    private int nextInLine;
    private ArrayList<Integer> AP;
    private int roomAssigned;
    private int distance;
    private boolean isOver;
    private int pos = -1;

    private int collectedCanvas = -1;
    private boolean inParty = false;
    /**
     *  Name of the logging file.
     */
    private String fName = null;

    /**
     *  Serialization key.
     */

    private static final long serialVersionUID = 2021L;

    /**
     *  Message type.
     */

    private int msgType = -1;

    /**
     *  Master thief identification.
     */

    private int MTId = -1;

    /**
     *  Master thief state.
     */

    private MasterThiefStates MTState;

    /**
     *  Ordinary thief identification.
     */

    private int OTId = -1;

    /**
     *  Ordinary thief state.
     */

    private OrdinaryThievesStates OTState;

    /**
     *  End of operations.
     */

    private boolean endOp = false;

    private int MDj = -1;

    private int RId = -1;

    private int N_canvas = -1;

    private int Rdistance = -1;

    private int amINeeded = -1;
    private Room[] rooms = new Room[Simul_Par.N];

    private boolean readyToLeave;

    private int roomToSteal;

    private boolean holdingCanvas = false;

    private int ap_id = -1;

    private int idx = -1;

    private int ot_id = -1;

    private int Log_ap_id = -1;

    private int Log_pos = -1;



    /**
     *  Message instantiation (form 1).
     *
     *     @param type message type
     */
    public Message (int type)
    {
        msgType = type;
    }

    /**
     *  Message instantiation (form 2).
     *
     *     @param type message type
     *     @param state master thief state
     */

    public Message (int type, MasterThiefStates state)
    {
        msgType = type;
        if ((msgType == MessageType.GPSMTS) || (msgType == MessageType.REQMTS) || (msgType == MessageType.REQSOPS) || (msgType == MessageType.REQAPPS) || (msgType == MessageType.REQCCSSAP) || (msgType == MessageType.REQCCSTAR) || (msgType == MessageType.REQCACANV))
        {
            MTState = state;
        }
        else { GenericIO.writelnString ("Message type = " + msgType + ": non-implemented instantiation!");
            System.exit (1);
        }
    }

    /**
     *  Message instantiation (form 8).
     *
     *     @param type message type
     *     @param id ordinary thief identification
     *     @param state ordinary thief state
     */

    public Message (int type, int id, OrdinaryThievesStates state)
    {
        msgType = type;
        if ((msgType == MessageType.REQOTS) || (msgType == MessageType.REQPREP) || (msgType == MessageType.REQCANV) ||
                (msgType == MessageType.REQREV) || (msgType == MessageType.REQCANVH) || (msgType == MessageType.GPSOTST))
        { OTId= id;
            OTState = state;
        }
        else { GenericIO.writelnString ("Message type = " + msgType + ": non-implemented instantiation!");
            System.exit (1);
        }
    }

    /**
     *  Message instantiation (form 3).
     *
     *     @param type message type
     *     @param id MT identification
     */

    public Message (int type, int id)
    {
        msgType = type;
        if(type == MessageType.REQRASS){
            roomToSteal = id;
        } else if (type == MessageType.REQAPSD) {
            distance = id;
        }else if(type == MessageType.APGRASSDONE) {
            roomAssigned = id;
        }else if(type == MessageType.REQSNIL) {
            nextInLine = id;
        }else if(type == MessageType.SUMDONE || type == MessageType.GPSUMDONE) {
            collectedCanvas = id;
        }else if(type == MessageType.REQAIN || type == MessageType.REQPREP || type == MessageType.REQJOIN) {
            OTId = id;
        }else if(type == MessageType.PREPDONE) {
            ap_id = id;
        }else if(type == MessageType.DISTDONE) {
            distance = id;
        }else if(type == MessageType.CINDONE || type == MessageType.COUTDONE) {
            pos = id;
        }else if(type == MessageType.GPSCANV){
            log_canvas = id;
        } else {
            MTId = id;
        }
    }

    /**
     *  Message instantiation (form 4).
     *
     *     @param type message type
     *     @param id MT identification
     *     @param endOp end of operations flag
     */

    public Message (int type, int id, boolean endOp)
    {
        msgType = type;
        if(type == MessageType.REQCANVH) {
            OTId = id;
            holdingCanvas = endOp;
        } else if (type == MessageType.GPSINP) {
            ot_id = id;
            isInParty = endOp;
        }else if(type == MessageType.GPSOTHCANV){
            ot_id = id;
            Log_holding_canvas = endOp;
        }else{
                MTId = id;
                this.endOp = endOp;
        }

    }

    /**
     *  Message instantiation (form 5).
     *
     *     @param type message type
     *     @param MTId MT identification
     *     @param MTState MT state
     *     @param OTId OT identification
     */

    public Message (int type, int MTId, MasterThiefStates MTState, int OTId)
    {
        msgType = type;
        this.MTId= MTId;
        this.MTState = MTState;
        this.OTId= OTId;
    }

    /**
     *  Message instantiation (form 6).
     *
     *     @param type message type
     *     @param MTId MT identification
     *     @param MTState MT state
     *     @param OTId OT identification
     *     @param OTState OT state
     */

    public Message (int type, int MTId, MasterThiefStates MTState , int OTId, OrdinaryThievesStates OTState)
    {
        msgType = type;
        this.MTId= MTId;
        this.MTState = MTState;
        this.OTId= OTId;
        this.OTState = OTState;
    }

    /**
     *  Message instantiation (form 7).
     *
     *     @param type message type
     *     @param name name of the logging file
     */

    public Message (int type, String name)
    {
        msgType = type;
        fName= name;
    }

    /**
     *  Message instantiation (form 9).
     *
     *     @param type message type
     *     @param id OT id
     *     @param MDj Maximum Displacement
     *     @param pos position of thief
     */

    public Message (int type, int id, int MDj, int pos)
    {
        this.msgType = type;
        if(type == MessageType.GPSOTIP){
            idx = id;
            ot_id = MDj;
            Log_ap_id = pos;
        }else{
            this.OTId = id;
            this.MDj = MDj;
            this.pos = pos;
        }
    }

    /**
     *  Message instantiation (form 10).
     *
     *     @param type message type
     *     @param id OT id
     *     @param pos position of thief
     */

    public Message (int type, int id, int pos)
    {
        this.msgType = type;
        if(type == MessageType.AINDONE) {
            this.OTId = id;
            amINeeded = pos;
        } else if (type == MessageType.GPSNP) {
            RId = id;
            N_canvas = pos;
        } else if (type == MessageType.GPSDIST) {
            RId = id;
            Rdistance = pos;
        }else if(type == MessageType.GPSOTPOS) {
            ot_id = id;
            Log_pos = pos;
        }else if(type == MessageType.GPSOTMDj) {
            ot_id = id;
            Log_mdj = pos;
        }else if(type == MessageType.GPSAPRID) {
            Log_ap_id = id;
            Log_Rid = pos;
        }else if(type == MessageType.REQCANV){
            OTId = id;
            roomAssigned = pos;
        }else{
                this.pos = pos;
            }
        }

    /**
     *  Message instantiation (form 11).
     *
     *     @param type message type
     *     @param isOver heist is over or not
     */

    public Message (int type, boolean isOver)
    {
        this.msgType = type;
        if(type == MessageType.CANVDONE){
            rolled = isOver;
        }else{
            this.isOver = isOver;
        }

    }

    /**
     *  Message instantiation (form 12).
     *
     *     @param type message type
     *     @param rooms Rooms array
     */

    public Message (int type, Room[] rooms)
    {
        this.msgType = type;
        this.rooms = rooms;
    }

    /**
     *  Message instantiation (form 13).
     *
     *     @param type message type
     *     @param AP Assault party
     */

    public Message (int type, ArrayList<Integer> AP)
    {
        this.msgType = type;
        this.AP = AP;
    }

    /**
     *  Message instantiation (form 13).
     *
     *     @param type message type
     *     @param readyToLeave ready to leave
     *     @param inParty is in party or not
     */

    public Message (int type, boolean readyToLeave, boolean inParty){
        msgType = type;
        this.readyToLeave = readyToLeave;
        this.inParty = inParty;
    }

    /**
     *  Message instantiation (form 14).
     *
     *     @param type message type
     *     @param readyToLeave ready to leave
     *     @param position is in party or not
     * @param  otState ordinary thief state
     */
    public Message(int type, boolean readyToLeave, int position, OrdinaryThievesStates otState) {
        msgType = type;
        this.readyToLeave = readyToLeave;
        this.pos = position;
        this.OTState = otState;
    }

    /**
     *  Getting message type.
     *
     *     @return message type
     */

    public int getMsgType ()
    {
        return (msgType);
    }

    public String getfName() {
        return fName;
    }

    public int getDistance() {
        return distance;
    }

    public int getRId() {
        return RId;
    }

    public int getN_canvas() {
        return N_canvas;
    }

    public ArrayList<Integer> getAP() {
        return AP;
    }

    public int getNextInLine() {
        return nextInLine;
    }

    public int getRoomToSteal() {
        return roomToSteal;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public int getLog_canvas() {
        return log_canvas;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public int getMTId() {
        return MTId;
    }

    public boolean isLog_holding_canvas() {
        return Log_holding_canvas;
    }

    public int getLog_mdj() {
        return Log_mdj;
    }

    public boolean inP(){
        return isInParty;
    }

    public void setMTId(int MTId) {
        this.MTId = MTId;
    }

    public MasterThiefStates getMTState() {
        return MTState;
    }

    public void setMTState(MasterThiefStates MTState) {
        this.MTState = MTState;
    }

    public int getOTId() {
        return OTId;
    }

    public boolean isReadyToLeave() {
        return readyToLeave;
    }

    public int getRdistance() {
        return Rdistance;
    }

    public int getIdx() {
        return idx;
    }

    public int getOt_id() {
        return ot_id;
    }

    public int getLog_ap_id() {
        return Log_ap_id;
    }

    public boolean isRolled() {
        return rolled;
    }

    public int getMDj() {
        return MDj;
    }

    public int getRoomAssigned() {
        return roomAssigned;
    }

    public int getLog_pos() {
        return Log_pos;
    }

    public int getAmINeeded() {
        return amINeeded;
    }

    public void setOTId(int OTId) {
        this.OTId = OTId;
    }

    public int getCollectedCanvas() {
        return collectedCanvas;
    }

    public Room[] getRooms() {
        return rooms;
    }

    public boolean isHoldingCanvas() {
        return holdingCanvas;
    }

    public void setRooms(Room[] rooms) {
        this.rooms = rooms;
    }

    public int getAp_id() {
        return ap_id;
    }

    public OrdinaryThievesStates getOTState() {
        return OTState;
    }


    public void setOTState(OrdinaryThievesStates OTState) {
        this.OTState = OTState;
    }

    public boolean isEndOp() {
        return endOp;
    }

    public void setEndOp(boolean endOp) {
        this.endOp = endOp;
    }

    public int getLog_Rid() {
        return Log_Rid;
    }

    /**
     *  Getting end of operations flag .
     *
     *     @return end of operations flag
     */

    public boolean getEndOp ()
    {
        return (endOp);
    }

    public boolean isOver() {
        return isOver;
    }

    /**
     *  Getting name of logging file.
     *
     *     @return name of the logging file
     */

    public String getLogFName ()
    {
        return (fName);
    }

    public boolean isInParty() {
        return inParty;
    }

    public int getPos() {
        return pos;
    }

    /**
     *  Printing the values of the internal fields.
     *
     *  It is used for debugging purposes.
     *
     *     @return string containing, in separate lines, the pair field name - field value
     */



    @Override
    public String toString ()
    {
        return ("Message type = " + msgType +
                "\nMT Id = " + MTId +
                "\nMT State = " + MTState +
                "\nOT Id = " + OTId +
                "\nOT State = " + OTState +
                "\nEnd of Operations (MT) = " + endOp +
                "\nName of logging file = " + fName +
                "\nPos = " + pos +
                "\nMDj = " + MDj);
    }
}

