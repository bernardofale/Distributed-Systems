package serverSide.sharedRegions;

import comm_infra.*;
import serverSide.entities.MuseumProxy;

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
        switch (inMessage.getMsgType()) {
            case MessageType.REQMGR:
                Room[] rooms = M.getRooms();
                outMessage = new Message(MessageType.MGRDONE, rooms);

                break;
            case MessageType.REQCANV:
                ((MuseumProxy) Thread.currentThread()).setOTId(inMessage.getOTId());
                boolean rolled = M.rollACanvas(inMessage.getRoomAssigned());
                outMessage = new Message(MessageType.CANVDONE, rolled);

                break;
            case MessageType.SHUT:
                M.shutdown();

                outMessage = new Message(MessageType.SHUTDONE);
                break;
            case MessageType.ENDOP:
                M.endOperation();
                outMessage = new Message(MessageType.ENDOPDONE);

                break;
        }
        return (outMessage);
    }
}
