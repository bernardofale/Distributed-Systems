package clientSide.entities;

public interface MasterThiefCloning {
    /**
     *   Set Master Thief id.
     *
     *     @param id master thief id
     */

    public void setMTId (int id);

    /**
     *   Get master thief id.
     *
     *     @return master thief id
     */

    public int getMTId ();

    /**
     *   Set master thief state.
     *
     *     @param state new master thief state
     */

    public void setMTState (MasterThiefStates state);

    /**
     *   Get master thief state.
     *
     *     @return master thief state
     */

    public int getMTState ();

}
