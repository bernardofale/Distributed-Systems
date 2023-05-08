package clientSide.stubs;

import clientSide.entities.MasterThief;
import clientSide.entities.OrdinaryThief;
import comm_infra.ClientCom;
import comm_infra.Message;
import comm_infra.MessageType;
import genclass.GenericIO;

public class OrdinaryThiefCSStub {

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
    public OrdinaryThiefCSStub(String serverHostName, int serverPortNumb) {
        this.serverHostName = serverHostName;
        this.serverPortNumb = serverPortNumb;
    }
    public void endOfHeist() {
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

        outMessage = new Message(MessageType.REQEOH);
        com.writeObject (outMessage);
        inMessage = (Message) com.readObject ();

        if (inMessage.getMsgType () != MessageType.EOHDONE)
        { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }

        com.close ();
    }

    public void prepareAssaultParty() {
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

        outMessage = new Message(MessageType.REQPREPAP);
        com.writeObject (outMessage);
        inMessage = (Message) com.readObject ();

        if (inMessage.getMsgType () != MessageType.PREPAPDONE)
        { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }

        com.close ();
    }

    public int amINeeded() {

        ClientCom com;                                                 // communication channel
        Message outMessage,                                            // outgoing message
                inMessage;                                             // incoming message

        com = new ClientCom (serverHostName, serverPortNumb);
        while (!com.open ())
        { try
        { Thread.sleep ((long) (1000));
        }
        catch (InterruptedException ignored) {}
        }
        outMessage = new Message (MessageType.REQAIN, ((OrdinaryThief) Thread.currentThread()).getOT_id());
        com.writeObject (outMessage);
        inMessage = (Message) com.readObject ();
        if (inMessage.getMsgType() != MessageType.AINDONE)
        { GenericIO.writelnString ("Thread " + Thread.currentThread ().getName () + ": Invalid message type!");
            GenericIO.writelnString (inMessage.toString ());
            System.exit (1);
        }
        com.close ();
        return inMessage.getAmINeeded();
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
