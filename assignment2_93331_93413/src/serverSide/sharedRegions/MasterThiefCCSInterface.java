package serverSide.sharedRegions;

import comm_infra.*;

public class MasterThiefCCSInterface {
    /**
     *  Reference to the Control and Collection Site.
     */

    private final MasterThiefCCS CCS;

    /**
     *  Instantiation of an interface to the Control and collection site.
     *
     *    @param CCS reference to the Control and Collection Site
     */

    public MasterThiefCCSInterface (MasterThiefCCS CCS)
    {
        this.CCS = CCS;
    }

    /**
     *  Processing of the incoming messages.
     *
     *  Validation, execution of the corresponding method and generation of the outgoing message.
     *
     *    @param inMessage service request
     *    @return service reply
     *    @throws MessageException if the incoming message is not valid
     */

    public Message processAndReply (Message inMessage) throws MessageException
    {
        Message outMessage = null;                                     // outgoing message

        /* validation of the incoming message */


        return (outMessage);
    }
}
