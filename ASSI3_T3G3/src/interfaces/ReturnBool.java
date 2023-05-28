package interfaces;

import clientSide.entities.MasterThiefStates;
import clientSide.entities.OrdinaryThievesStates;

import java.io.Serializable;

/**
 *  Data type to return both a boolean and an integer state value.
 *
 *  Used in calls on remote objects.
 */

public class ReturnBool implements Serializable
{
    /**
     *  Serialization key.
     */

    public static final long serialVersionUID = 2021L;

    /**
     *  Boolean value.
     */

    private boolean val;

    /**
     *  Integer state value.
     */

    private OrdinaryThievesStates state;

    private MasterThiefStates master_state;

    /**
     *  ReturnBoolean instantiation.
     *
     *     @param val boolean value
     *     @param state integer state value
     */

    public ReturnBool (boolean val, OrdinaryThievesStates state)
    {
        this.val = val;
        this.state = state;
    }

    public ReturnBool (boolean val, MasterThiefStates state)
    {
        this.val = val;
        this.master_state = state;
    }

    /**
     *  Getting boolean value.
     *
     *     @return boolean value
     */

    public boolean getBooleanVal ()
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

    public MasterThiefStates getMaster_state() { return master_state; }
}
