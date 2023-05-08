package serverSide.sharedRegions;

import comm_infra.*;
import serverSide.entities.AssaultPartyProxy;

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
        switch (inMessage.getMsgType ()){
            case MessageType.REQRASS:
                AP.setRoom_assigned(inMessage.getRoomToSteal());
                outMessage = new Message(MessageType.RASSDONE);
                break;
            case MessageType.REQJOIN:
                ((AssaultPartyProxy) Thread.currentThread()).setOTId(inMessage.getOTId());
                AP.join();
                outMessage = new Message(MessageType.JOINDONE, ((AssaultPartyProxy) Thread.currentThread()).getReadyToLeave(), ((AssaultPartyProxy) Thread.currentThread()).isInParty());
                break;
            case MessageType.REQCIN:
                ((AssaultPartyProxy) Thread.currentThread()).setOTId(inMessage.getOTId());
                ((AssaultPartyProxy) Thread.currentThread()).setPosition(inMessage.getPos());
                ((AssaultPartyProxy) Thread.currentThread()).setMDj(inMessage.getMDj());
                AP.crawlIn();
                outMessage = new Message(MessageType.CINDONE, ((AssaultPartyProxy) Thread.currentThread()).getPosition());

                break;
            case MessageType.REQNEXT:
                ((AssaultPartyProxy) Thread.currentThread()).setPosition(inMessage.getPos());
                AP.next();
                outMessage = new Message(MessageType.NEXTDONE);

                break;
            case MessageType.REQDIST:
                int d = AP.getDistance();
                outMessage = new Message(MessageType.DISTDONE, d);
                break;
            case MessageType.REQREV:
                ((AssaultPartyProxy) Thread.currentThread()).setOTId(inMessage.getOTId());
                AP.reverseDirection();
                outMessage = new Message(MessageType.REVDONE, ((AssaultPartyProxy) Thread.currentThread()).getReadyToLeave(), ((AssaultPartyProxy) Thread.currentThread()).getPosition(), ((AssaultPartyProxy) Thread.currentThread()).getOTState());

                break;
            case MessageType.REQCOUT:
                ((AssaultPartyProxy) Thread.currentThread()).setOTId(inMessage.getOTId());
                ((AssaultPartyProxy) Thread.currentThread()).setPosition(inMessage.getPos());
                ((AssaultPartyProxy) Thread.currentThread()).setMDj(inMessage.getMDj());
                AP.crawlIn();
                outMessage = new Message(MessageType.COUTDONE, ((AssaultPartyProxy) Thread.currentThread()).getPosition());

                break;
            case MessageType.ENDOP:
                AP.endOperation(inMessage.getMTId());
                outMessage = new Message (MessageType.ENDOPDONE);

                break;
            case MessageType.SHUT:
                AP.shutdown ();
                outMessage = new Message (MessageType.SHUTDONE);

                break;
            case MessageType.REQAPGRASS:
                int RId = AP.getRoom_assigned();
                outMessage = new Message (MessageType.APGRASSDONE, RId);

                break;
            case MessageType.REQAPSD:
                AP.setDistance(inMessage.getDistance());
                outMessage = new Message(MessageType.SACK);

                break;
            case MessageType.REQSAP:
                AP.setAP(inMessage.getAP());
                outMessage = new Message(MessageType.SACK);

                break;
            case MessageType.REQSNIL:
                AP.setNext_inLine(inMessage.getNextInLine());
                outMessage = new Message(MessageType.SACK);

                break;
        }
        return (outMessage);
    }
}
