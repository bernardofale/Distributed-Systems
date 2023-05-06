package serverSide.sharedRegions;

import comm_infra.Message;
import comm_infra.MessageException;

public class OrdinaryThievesCSInterface {
    /**
     *  Reference to the Concentration Site.
     */

    private final OrdinaryThievesCS CS;

    /**
     *  Instantiation of an interface to the Concentration Site.
     *
     *    @param CS reference to the Concentration Site
     */

    public OrdinaryThievesCSInterface (OrdinaryThievesCS CS)
    {
        this.CS = CS;
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
