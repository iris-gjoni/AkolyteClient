package iris.test.app1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import iris.test.app1.server.connection.SocketInitator;

public class TestRecieveUserData {


    final private SocketInitator socketInitator = new SocketInitator();
    private ExecutorService executor = Executors.newSingleThreadExecutor();



}
