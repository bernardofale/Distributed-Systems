package serverSide.sharedRegions;

import comm_infra.Message;
import comm_infra.MessageException;
import comm_infra.MessageType;
import serverSide.entities.OrdinaryThievesCSProxy;

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
        switch (inMessage.getMsgType()){
            case MessageType.REQEOH:
                CS.endOfHeist();
                outMessage = new Message(MessageType.EOHDONE);

                break;
            case MessageType.REQPREPAP:
                CS.prepareAssaultParty();
                outMessage = new Message(MessageType.PREPAPDONE);

                break;
            case MessageType.REQAIN:
                ((OrdinaryThievesCSProxy)Thread.currentThread()).setOTId(inMessage.getOTId());
                int ot_signal = CS.amINeeded();
                outMessage = new Message(MessageType.AINDONE, inMessage.getOTId(), ot_signal);

                break;
            case MessageType.SHUT:
                CS.shutdown();

                outMessage = new Message(MessageType.SHUTDONE);
                break;
            case MessageType.ENDOP:
                CS.endOperation();
                outMessage = new Message(MessageType.ENDOPDONE);

                break;
        }

        return (outMessage);
    }
}
