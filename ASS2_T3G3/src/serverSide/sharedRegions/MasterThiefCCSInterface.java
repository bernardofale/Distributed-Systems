package serverSide.sharedRegions;

import comm_infra.*;
import serverSide.entities.MasterThiefCCSProxy;

public class MasterThiefCCSInterface {
    /**
     *  Reference to the Assault Party.
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
        switch (inMessage.getMsgType()){
            case MessageType.REQSOPS:
                CCS.startOperations();
                outMessage = new Message(MessageType.SOPSDONE, ((MasterThiefCCSProxy)Thread.currentThread()).getMTState());

                break;
            case MessageType.REQAPPS:
                CCS.appraiseSit(inMessage.isOver());
                outMessage = new Message(MessageType.APPSDONE, ((MasterThiefCCSProxy)Thread.currentThread()).getMTState());

                break;
            case MessageType.REQCCSSAP:
                CCS.sendAssaultParty();
                outMessage = new Message(MessageType.CCSSAPDONE, ((MasterThiefCCSProxy)Thread.currentThread()).getMTState());

                break;
            case MessageType.REQCCSTAR:
                CCS.takeARest();
                outMessage = new Message(MessageType.CCSTARDONE, ((MasterThiefCCSProxy)Thread.currentThread()).getMTState());

                break;
            case MessageType.REQCACANV:
                CCS.collectACanvas();
                outMessage = new Message(MessageType.CACANVDONE, ((MasterThiefCCSProxy)Thread.currentThread()).getMTState());

                break;
            case MessageType.REQSUM:
                int canvas = CCS.sumUpResults();
                outMessage = new Message(MessageType.SUMDONE, canvas);

                break;
            case MessageType.REQPREP:
                ((MasterThiefCCSProxy)Thread.currentThread()).setOTId(inMessage.getOTId());
                int ap_id = CCS.prepareExcursion();
                outMessage = new Message(MessageType.PREPDONE, ap_id);

                break;
            case MessageType.REQCANVH:
                ((MasterThiefCCSProxy)Thread.currentThread()).setOTId(inMessage.getOTId());
                ((MasterThiefCCSProxy)Thread.currentThread()).setHoldingCanvas(inMessage.isHoldingCanvas());
                CCS.handACanvas();
                outMessage = new Message(MessageType.CANVHDONE, ((MasterThiefCCSProxy)Thread.currentThread()).getMTState());

                break;
            case MessageType.SHUT:
                CCS.shutdown();

                outMessage = new Message(MessageType.SHUTDONE);
                break;
            case MessageType.ENDOP:
                CCS.endOperation();

                outMessage = new Message(MessageType.ENDOPDONE);
                break;
        }

        return (outMessage);
    }
}
