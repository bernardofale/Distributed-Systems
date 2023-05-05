package clientSide.entities;

public interface OrdinaryThiefCloning {
    /**
     *   Set Ordinary Thief id.
     *
     *     @param id thief id
     */

    public void setOTId (int id);

    /**
     *   Get thief id.
     *
     *     @return thief id
     */

    public int getOTId ();

    /**
     *   Set thief state.
     *
     *     @param state new thief state
     */

    public void setOTState (OrdinaryThievesStates state);

    /**
     *   Get thief state.
     *
     *     @return thief state
     */

    public int getOTState ();
}
