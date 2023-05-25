package interfaces;

import clientSide.entities.MasterThiefStates;
import clientSide.entities.OrdinaryThievesStates;

public interface GeneralReposInterface {
    void setMT_state(MasterThiefStates masterThiefStates);

    void setAPRid(int i, int roomToSteal);

    void setCanvas(int results);

    void printSumUp();

    void setOt_mdj(int otId, int mDj);

    void setOT_states(int otId, OrdinaryThievesStates ordinaryThievesStates);

    void setOtHasCanvas(int otId, boolean isHoldingCanvas);

    void setIsInParty(int otId, boolean inParty);
}
