package comm_infra;

public class MessageType {

    /**
     *  Initialization of the logging file name (service request).
     */

    public static final int SETNFIC = 1;

    /**
     *  Logging file was initialized (reply).
     */

    public static final int NFICDONE = 2;

    /**
     *  Request crawlIn
     */
    public static final int REQCIN = 3;

    /**
     *  crawlIn done (reply)
     */
    public static final int CINDONE = 4;

    /**
     *  Request next
     */
    public static final int REQNEXT = 5;

    /**
     *   next reply
     */
    public static final int NEXTDONE = 6;

    /**
     *  Request Reverse direction
     */
    public static final int REQREV = 7;

    /**
     *   Reverse direction (replu)
     */
    public static final int REVDONE = 8;

    /**
     *  Request crawl out
     */
    public static final int REQCOUT = 9;

    /**
     *  crawl out (reply)
     */
    public static final int COUTDONE = 10;

    /**
     *  Request join party
     */
    public static final int REQJOIN = 11;

    /**
     *  Join party (reply)
     */
    public static final int JOINDONE = 12;

    /**
     *  Request amINeeded()
     */
    public static final int REQAIN = 13;

    /**
     *  amIneeded (reply)
     */
    public static final int AINDONE = 14;

    /**
     *  Request prepare excursion
     */
    public static final int REQPREP = 15;

    /**
     *  prepare excursion (reply)
     */
    public static final int PREPDONE = 16;

    /**
     *  Request get distance
     */
    public static final int REQDIST = 17;

    /**
     *  get Distance (reply)
     */
    public static final int DISTDONE = 18;

    /**
     *  Request set ordinary thief states for General Repo
     */
    public static final int REQROTS = 19;


    /**
     *  Roll a canvas request
     */
    public static final int REQCANV = 21;

    /**
     *  roll a canvas (reply)
     */
    public static final int CANVDONE = 22;

    /**
     *  Request room assigned
     */
    public static final int REQRASS = 23;

    /**
     *  get room assigned (reply)
     */
    public static final int RASSDONE = 24;

    /**
     *  General repo set if ot has canvas
     */
    public static final int REQHCANV = 25;


    /**
     *  OT hand a canvas request
     */
    public static final int REQCANVH = 27;

    /**
     *  hand a canvas (reply)
     */
    public static final int CANVHDONE = 28;

    /**
     *  gp set in party request
     */
    public static final int REQSIP = 29;


    /**
     *  Request start operations
     */
    public static final int REQSOPS = 31;

    /**
     *  start ops (reply)
     */
    public static final int SOPSDONE = 32;

    /**
     *  gp set mt state request
     */
    public static final int REQSMTS = 33;


    /**
     *  appraise sit request
     */
    public static final int REQAPPS = 35;

    /**
     *  appraise sit (reply)
     */
    public static final int APPSDONE = 36;

    /**
     *  Prepare assault party
     */
    public static final int REQPREPAP = 37;

    /**
     *  prepare assault party (reply)
     */
    public static final int PREPAPDONE = 38;

    /**
     *  Request museum get rooms
     */
    public static final int REQMGR = 39;

    /**
     *  museum get rooms (reply)
     */
    public static final int MGRDONE = 40;

    /**
     *  request museum get distance
     */
    public static final int REQMGD = 41;

    /**
     *  museum get distance (reply)
     */
    public static final int MGDDONE = 42;

    /**
     *  museum set room assigned request
     */
    public static final int REQMSRASS = 43;


    /**
     *  AP set room assigned request
     */
    public static final int REQAPSRASS = 45;


    /**
     *  ap set distance to target request
     */
    public static final int REQAPSD = 47;


    /**
     *  General repo set AP room id request
     */
    public static final int REQSAPRID = 49;


    /**
     *  send Assault party request
     */
    public static final int REQCCSSAP = 51;

    /**
     *  send AP (reply)
     */
    public static final int CCSSAPDONE = 52;

    /**
     *  take a Rest request
     */
    public static final int REQCCSTAR = 53;

    /**
     *  take a rest (reply)
     */
    public static final int CCSTARDONE = 54;

    /**
     *  Collect a canvas request
     */
    public static final int REQCACANV = 55;

    /**
     *  Collect a canvas (reply)
     */
    public static final int CACANVDONE = 56;

    /**
     *  Get museum room number of canvas
     */
    public static final int REQGNCANV = 57;

    /**
     *  Get museum room number of canvas (reply)
     */
    public static final int GNCANVDONE = 58;

    /**
     *  Set if room is empty request
     */
    public static final int REQEMPT = 59;


    /**
     *  Set AP request
     */
    public static final int REQSAP = 61;



    /**
     *  Set next in line request
     */
    public static final int REQSNIL = 62;

    /**
     *   sum up results request
     */
    public static final int REQSUM = 91;

    /**
     *  Sum up results (reply)
     */
    public static final int SUMDONE = 90;

    /**
     *  End of heist request
     */
    public static final int REQEOH = 89;

    /**
     *  End of heist (reply)
     */
    public static final int EOHDONE = 88;

    /**
     *  General repo set canvas request
     */
    public static final int REQSCANV = 87;

    /**
     *  Set acknowledge
     */
    public static final int SACK = 86;

    /**
     *  General repo print sum up
     */
    public static final int REQPSUM = 85;

    /**
     *  General repo print sum up (reply)
     */
    public static final int PSUMDONE = 84;

    /**
     *  Request MT state
     */
    public static final int REQMTS = 63;

    /**
     *  Request OT state
     */
    public static final int REQOTS = 64;

    /**
     * Request end of operations
     */
    public static final int ENDOP = 65;

    /**
     * end of operations done
     */
    public static final int ENDOPDONE = 66;

    /**
     * Request end of operations
     */
    public static final int SHUT = 67;

    /**
     * end of operations done
     */
    public static final int SHUTDONE = 68;

    /**
     *  AP get room assigned request
     */
    public static final int REQAPGRASS = 69;

    /**
     *  AP get room assigned request
     */
    public static final int APGRASSDONE = 70;

    /**
     * GP set number of paintings
     */

    public static final int GPSNP = 71;

    /**
     * GP set room distance
     */

    public static final int GPSDIST = 72;

    /**
     * GP ot waiting or not
     */

    public static final int GPSINP = 73;

    /**
     * GP set OT position
     */

    public static final int GPSOTPOS = 74;

    /**
     * GP set ot mdj
     */

    public static final int GPSOTMDj = 75;

    /**
     * GP set ot in party
     */

    public static final int GPSOTIP = 76;

    /**
     * GP ot has canvas or not
     */

    public static final int GPSOTHCANV = 77;

    /**
     * GP set ot state
     */

    public static final int GPSOTST = 78;

    /**
     * GP set number of canvas
     */

    public static final int GPSCANV = 79;

    /**
     * GP set mt state
     */

    public static final int GPSMTS = 80;

    /**
     * GP set assault party room id to be stolen
     */

    public static final int GPSAPRID = 81;

    /**
     * GP print sum up
     */

    public static final int GPSUM = 82;

    /**
     * GP print sum up (reply)
     */

    public static final int GPSUMDONE = 83;

}
