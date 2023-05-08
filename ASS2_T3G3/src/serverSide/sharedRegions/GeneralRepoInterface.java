package serverSide.sharedRegions;

import comm_infra.*;

/**
 *  Interface to the General Repository of Information.
 *
 *    It is responsible to validate and process the incoming message, execute the corresponding method on the
 *    General Repository and generate the outgoing message.
 *    Implementation of a client-server model of type 2 (server replication).
 *    Communication is based on a communication channel under the TCP protocol.
 */

public class GeneralRepoInterface {
    /**
     *  Reference to the general repository.
     */

    private final GeneralRepo repos;

    /**
     *  Instantiation of an interface to the general repository.
     *
     *    @param repos reference to the general repository
     */

    public GeneralRepoInterface (GeneralRepo repos)
    {
        this.repos = repos;
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
        Message outMessage = null;                                     // mensagem de resposta

        /* validation of the incoming message */
        switch (inMessage.getMsgType ()) {
            case MessageType.GPSMTS:
                repos.setMT_state(inMessage.getMTState());
                outMessage = new Message(MessageType.SACK);

                break;
            case MessageType.GPSAPRID:
                repos.setAPRid(inMessage.getLog_ap_id(), inMessage.getLog_Rid());
                outMessage = new Message(MessageType.SACK);

                break;
            case MessageType.GPSCANV:
                repos.setCanvas(inMessage.getLog_canvas());
                outMessage = new Message(MessageType.SACK);

                break;
            case MessageType.GPSUM:
                repos.printSumUp();
                outMessage = new Message(MessageType.GPSUMDONE);

                break;
            case MessageType.GPSOTST:
                repos.setOT_states(inMessage.getOt_id(), inMessage.getOTState());
                outMessage = new Message(MessageType.SACK);

                break;
            case MessageType.GPSOTHCANV:
                repos.setOtHasCanvas(inMessage.getOt_id(), inMessage.isLog_holding_canvas());
                outMessage = new Message(MessageType.SACK);

                break;
            case MessageType.GPSINP:
                repos.setIsInParty(inMessage.getOt_id(), inMessage.isInParty());
                outMessage = new Message(MessageType.SACK);

                break;
            case MessageType.GPSOTMDj:
                repos.setOt_mdj(inMessage.getOt_id(), inMessage.getLog_mdj());
                outMessage = new Message(MessageType.SACK);

                break;
            case MessageType.SHUT:
                repos.shutdown();
                outMessage = new Message(MessageType.SHUTDONE);

                break;
            case MessageType.GPSOTPOS:
                repos.setOtPosition(inMessage.getOt_id(), inMessage.getLog_pos());
                outMessage = new Message(MessageType.SACK);

                break;
            case MessageType.GPSOTIP:
                repos.setOtInParty(inMessage.getIdx(), inMessage.getOt_id(), inMessage.getLog_ap_id());
                outMessage = new Message(MessageType.SACK);

                break;
            case MessageType.GPSDIST:
                repos.setDistance(inMessage.getLog_Rid(), inMessage.getRdistance());
                outMessage = new Message(MessageType.SACK);

                break;
            case MessageType.GPSNP:
                repos.setNP(inMessage.getLog_Rid(), inMessage.getN_canvas());
                outMessage = new Message(MessageType.SACK);

                break;
            case MessageType.SETNFIC:
                repos.initSimul(inMessage.getfName());
                outMessage = new Message(MessageType.NFICDONE);

                break;
        }

        return (outMessage);
    }
}
