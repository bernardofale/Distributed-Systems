package serverSide.sharedRegions;

import comm_infra.*;

public class MuseumInterface {
    /**
     *  Reference to the Museum.
     */

    private final Museum M;

    /**
     *  Instantiation of an interface to the Museum.
     *
     *    @param M reference to the Museum
     */

    public MuseumInterface (Museum M)
    {
        this.M = M;
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
