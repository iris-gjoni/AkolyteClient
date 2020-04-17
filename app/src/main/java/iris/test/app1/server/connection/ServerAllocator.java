package iris.test.app1.server.connection;

public class ServerAllocator {

    /* needs to hold details of allocated server */

    private static final String LONDON = "LN";
    private static final String AMERICA = "USA";
    private static final String ASIA = "AIS";



    private static final String LN_PRIMARY_HOST = "192.168.0.8";
    private static final int LN_PRIMARY_PORT = 1001;
    private static final int LN_SECONDARY_PORT = 1002;

    /* config loaded Ip's and port of International Servers */

    ServerAllocator(){

    }

    public void RegionSelected(){

    }

    public static String getLnPrimaryHost() {
        return LN_PRIMARY_HOST;
    }

    public static int getLnPrimaryPort() {
        return LN_PRIMARY_PORT;
    }

    public static int getLnSecondaryPort() {
        return LN_SECONDARY_PORT;
    }
}
