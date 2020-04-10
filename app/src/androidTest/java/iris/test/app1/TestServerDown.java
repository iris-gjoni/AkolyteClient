package iris.test.app1;

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import iris.test.app1.server.connection.SocketInitator;

public class TestServerDown {

    final private SocketInitator socketInitator = new SocketInitator();
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    @Test
    public void testLoginNoServer()  {
        socketInitator.setMessage("RQ1.jeff.password");
        Future<String> responseFuture = executor.submit(socketInitator);
        String response = "";

        try {
            response = responseFuture.get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Assert.assertEquals("failed logon", response);
    }

}
