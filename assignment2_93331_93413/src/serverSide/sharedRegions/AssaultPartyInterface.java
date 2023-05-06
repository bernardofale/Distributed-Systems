package serverSide.sharedRegions;

import comm_infra.*;

public class AssaultPartyInterface {
    /**
     *  Reference to the Assault Party.
     */

    private final AssaultParty AP;

    /**
     *  Instantiation of an interface to the Assault Party.
     *
     *    @param AP reference to the Assault Party
     */

    public AssaultPartyInterface (AssaultParty AP)
    {
        this.AP = AP;
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
