package interfaces;

import clientSide.entities.MasterThiefStates;
import clientSide.entities.OrdinaryThievesStates;

import java.io.Serializable;

/**
 *  Data type to return both an identification value and an integer state value.
 *
 *  Used in calls on remote objects.
 */

public class ReturnInt implements Serializable
{
    /**
     *  Serialization key.
     */

    public static final long serialVersionUID = 2021L;

    /**
     *  Integer identification value.
     */

    private int val;

    /**
     *  Integer state value.
     */

    private OrdinaryThievesStates state;

    private MasterThiefStates state_master;

    /**
     *  ReturnBoolean instantiation.
     *
     *     @param val integer identification value
     *     @param state integer state value
     */

    public ReturnInt (int val, OrdinaryThievesStates state)
    {
        this.val = val;
        this.state = state;
    }

    public ReturnInt(int val, MasterThiefStates state){
        this.val = val;
        this.state_master = state;
    }

    /**
     *  Getting integer identification value.
     *
     *     @return integer identification value
     */

    public int getIntVal ()
    {
        return (val);
    }

    /**
     *  Getting integer state value.
     *
     *     @return integer state value
     */

    public OrdinaryThievesStates getOrdinaryThievesStatesVal ()
    {
        return (state);
    }

    public MasterThiefStates getState_master() {
        return state_master;
    }
}
