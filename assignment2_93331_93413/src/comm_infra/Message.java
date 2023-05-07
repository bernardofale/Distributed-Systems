package comm_infra;

import java.io.*;
import genclass.GenericIO;


/**
 *   Internal structure of the exchanged messages.
 *
 *   Implementation of a client-server model of type 2 (server replication).
 *   Communication is based on a communication channel under the TCP protocol.
 */

public class Message implements Serializable {

    /**
     *  Serialization key.
     */

    private static final long serialVersionUID = 2021L;

    /**
     *  Message type.
     */

    private int msgType = -1;

    /**
     *  Barber identification.
     */

    private int MasterId = -1;

    /**
     *  Master state.
     */

    private int MasterState = -1;

    /**
     *  OrdinaryThief identification.
     */

    private int OTId = -1;

    /**
     *  OrdinaryThief state.
     */

    private int OTState = -1;

    /**
     *  End of operations (MasterThief).
     */

    private boolean endOp = false;

    /**
     *  Name of the logging file.
     */

    private String logName = null;




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
     *     @param id barber / customer identification
     *     @param state barber / customer state
     */

    public Message (int type, int id, int state)
    {
        msgType = type;
        if ((msgType == MessageType.STBST) || (msgType == MessageType.CALLCUST) || (msgType == MessageType.RPAYDONE))
        { MasterId= id;
            MasterState = state;
        }
        else if ((msgType == MessageType.STCST) || (msgType == MessageType.REQCUTH) || (msgType == MessageType.CUTHDONE) ||
                (msgType == MessageType.BSHOPF))
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
     *     @param id barber identification
     */

    public Message (int type, int id)
    {
        msgType = type;
        MasterId= id;
    }

    /**
     *  Message instantiation (form 4).
     *
     *     @param type message type
     *     @param id barber identification
     *     @param endOP end of operations flag
     */

    public Message (int type, int id, boolean endOp)
    {
        msgType = type;
        MasterId= id;
        this.endOp = endOp;
    }

    /**
     *  Message instantiation (form 5).
     *
     *     @param type message type
     *     @param MasterId barber identification
     *     @param MasterState barber state
     *     @param OTId customer identification
     */

    public Message (int type, int MasterId, int MasterState, int OTId)
    {
        msgType = type;
        this.MasterId= MasterId;
        this.MasterState = MasterState;
        this.OTId= OTId;
    }

    /**
     *  Message instantiation (form 6).
     *
     *     @param type message type
     *     @param MasterId barber identification
     *     @param MasterState barber state
     *     @param OTId customer identification
     *     @param OTState customer state
     */

    public Message (int type, int MasterId, int MasterState, int OTId, int OTState)
    {
        msgType = type;
        this.MasterId= MasterId;
        this.MasterState = MasterState;
        this.OTId= OTId;
        this.OTState = OTState;
    }

    public int getMsgType() {
        return msgType;
    }

    public int getMasterId() {
        return MasterId;
    }

    public int getMasterState() {
        return MasterState;
    }

    public int getOTId() {
        return OTId;
    }

    public int getOTState() {
        return OTState;
    }

    public boolean isEndOp() {
        return endOp;
    }

    public String getLogName() {
        return logName;
    }

    @Override
    public String toString ()
    {
        return ("Message type = " + msgType +
                "\nMaster Thief Id = " + MasterId +
                "\nMaster Thief State = " + MasterState +
                "\nOrdinary Thief Id = " + OTId +
                "\nOrdinary Thief State = " + OTState +
                "\nEnd of Operations (Master Thief) = " + endOp +
                "\nName of logging file = " + logName);
    }
}
