package clientSide.stubs;

import clientSide.entities.MasterThief;
import clientSide.entities.OrdinaryThief;
import comm_infra.ClientCom;
import comm_infra.Message;
import comm_infra.MessageType;
import genclass.GenericIO;

public class MasterThiefCCSStub {

    /**
     *  Name of the platform where is located the barber shop server.
     */

    private String serverHostName;

    /**
     *  Port number for listening to service requests.
     */

    private int serverPortNumb;

    /**
     *   Instantiation of a stub to the barber shop.
     *
     *     @param serverHostName name of the platform where is located the barber shop server
     *     @param serverPortNumb port number for listening to service requests
     */
    public MasterThiefCCSStub(String serverHostName, int serverPortNumb) {
        this.serverHostName = serverHostName;
        this.serverPortNumb = serverPortNumb;
    }
    public void startOperations() {
        ClientCom com;                                                 // communication channel
        Message outMessage,                                            // outgoing message
                inMessage;                                             // incoming message

        com = new ClientCom (serverHostName, serverPortNumb);
        while (!com.open ())                                           // waits for a connection to be established
        { try
        { Thread.currentThread ().sleep ((long) (10));
        }
        catch (InterruptedException ignored) {}
        }

        outMessage = new Message(MessageType.REQSOPS);
        com.writeObject (outMessage);
        inMessage = (Message) com.readObject ();

        if (inMessage.getMsgType () != MessageType.SOPSDONE)
        { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }

        com.close ();
        ((MasterThief) Thread.currentThread ()).setMT_state(inMessage.getMTState());

    }

    public void appraiseSit(boolean heistOver) {
        ClientCom com;                                                 // communication channel
        Message outMessage,                                            // outgoing message
                inMessage;                                             // incoming message

        com = new ClientCom (serverHostName, serverPortNumb);
        while (!com.open ())                                           // waits for a connection to be established
        { try
        { Thread.currentThread ().sleep ((long) (10));
        }
        catch (InterruptedException ignored) {}
        }

        outMessage = new Message(MessageType.REQAPPS, heistOver);
        com.writeObject (outMessage);
        inMessage = (Message) com.readObject ();

        if (inMessage.getMsgType () != MessageType.APPSDONE)
        { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }

        com.close ();
        ((MasterThief) Thread.currentThread ()).setMT_state(inMessage.getMTState());

    }

    public void sendAssaultParty() {
        ClientCom com;                                                 // communication channel
        Message outMessage,                                            // outgoing message
                inMessage;                                             // incoming message

        com = new ClientCom (serverHostName, serverPortNumb);
        while (!com.open ())                                           // waits for a connection to be established
        { try
        { Thread.currentThread ().sleep ((long) (10));
        }
        catch (InterruptedException ignored) {}
        }

        outMessage = new Message(MessageType.REQCCSSAP);
        com.writeObject (outMessage);
        inMessage = (Message) com.readObject ();

        if (inMessage.getMsgType () != MessageType.CCSSAPDONE)
        { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }

        com.close ();
        ((MasterThief) Thread.currentThread ()).setMT_state(inMessage.getMTState());
    }

    public void takeARest() {
        ClientCom com;                                                 // communication channel
        Message outMessage,                                            // outgoing message
                inMessage;                                             // incoming message

        com = new ClientCom (serverHostName, serverPortNumb);
        while (!com.open ())                                           // waits for a connection to be established
        { try
        { Thread.currentThread ().sleep ((long) (10));
        }
        catch (InterruptedException ignored) {}
        }

        outMessage = new Message(MessageType.REQCCSTAR);
        com.writeObject (outMessage);
        inMessage = (Message) com.readObject ();

        if (inMessage.getMsgType () != MessageType.REQCCSTAR)
        { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }

        com.close ();
        ((MasterThief) Thread.currentThread ()).setMT_state(inMessage.getMTState());
    }

    public void collectACanvas() {
        ClientCom com;                                                 // communication channel
        Message outMessage,                                            // outgoing message
                inMessage;                                             // incoming message

        com = new ClientCom (serverHostName, serverPortNumb);
        while (!com.open ())                                           // waits for a connection to be established
        { try
        { Thread.currentThread ().sleep ((long) (10));
        }
        catch (InterruptedException ignored) {}
        }

        outMessage = new Message(MessageType.REQCACANV);
        com.writeObject (outMessage);
        inMessage = (Message) com.readObject ();

        if (inMessage.getMsgType () != MessageType.CACANVDONE)
        { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }

        com.close ();
        ((MasterThief) Thread.currentThread ()).setMT_state(inMessage.getMTState());
    }

    public int sumUpResults() {
        ClientCom com;                                                 // communication channel
        Message outMessage,                                            // outgoing message
                inMessage;                                             // incoming message

        com = new ClientCom (serverHostName, serverPortNumb);
        while (!com.open ())                                           // waits for a connection to be established
        { try
        { Thread.currentThread ().sleep ((long) (10));
        }
        catch (InterruptedException ignored) {}
        }

        outMessage = new Message(MessageType.REQSUM);
        com.writeObject (outMessage);
        inMessage = (Message) com.readObject ();

        if (inMessage.getMsgType () != MessageType.SUMDONE)
        { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }

        com.close ();
        return inMessage.getCollectedCanvas();
    }

    public int prepareExcursion() {
        ClientCom com;                                                 // communication channel
        Message outMessage,                                            // outgoing message
                inMessage;                                             // incoming message

        com = new ClientCom (serverHostName, serverPortNumb);
        while (!com.open ())                                           // waits for a connection to be established
        { try
        { Thread.currentThread ().sleep ((long) (10));
        }
        catch (InterruptedException ignored) {}
        }

        outMessage = new Message(MessageType.REQPREP, ((OrdinaryThief) Thread.currentThread()).getOT_id());
        com.writeObject (outMessage);
        inMessage = (Message) com.readObject ();

        if (inMessage.getMsgType () != MessageType.PREPDONE)
        { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }

        com.close ();
        return inMessage.getAp_id();
    }

    public void handACanvas() {
        ClientCom com;                                                 // communication channel
        Message outMessage,                                            // outgoing message
                inMessage;                                             // incoming message

        com = new ClientCom (serverHostName, serverPortNumb);
        while (!com.open ())                                           // waits for a connection to be established
        { try
        { Thread.currentThread ().sleep ((long) (10));
        }
        catch (InterruptedException ignored) {}
        }

        outMessage = new Message(MessageType.REQCANVH, ((OrdinaryThief) Thread.currentThread()).getOT_id(), ((OrdinaryThief) Thread.currentThread()).isHoldingCanvas());
        com.writeObject (outMessage);
        inMessage = (Message) com.readObject ();

        if (inMessage.getMsgType () != MessageType.CANVHDONE)
        { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }

        com.close ();
        ((OrdinaryThief) Thread.currentThread ()).setOT_state(inMessage.getOTState());
    }

    public void shutdown(){
        ClientCom com;                                                 // communication channel
        Message outMessage,                                            // outgoing message
                inMessage;                                             // incoming message

        com = new ClientCom (serverHostName, serverPortNumb);
        while (!com.open ())
        { try
        { Thread.sleep ((long) (1000));
        }
        catch (InterruptedException e) {}
        }
        outMessage = new Message (MessageType.SHUT);
        com.writeObject (outMessage);
        inMessage = (Message) com.readObject ();
        if (inMessage.getMsgType() != MessageType.SHUTDONE)
        { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }
        com.close ();
    }

    public void endOperation() {
        ClientCom com;                                                 // communication channel
        Message outMessage,                                            // outgoing message
                inMessage;                                             // incoming message

        com = new ClientCom (serverHostName, serverPortNumb);
        while (!com.open ())                                           // waits for a connection to be established
        { try
        { Thread.currentThread ().sleep ((long) (10));
        }
        catch (InterruptedException ignored) {}
        }

        outMessage = new Message(MessageType.ENDOP);
        com.writeObject (outMessage);
        inMessage = (Message) com.readObject ();

        if (inMessage.getMsgType () != MessageType.ENDOPDONE)
        { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }

        com.close ();
    }
}
