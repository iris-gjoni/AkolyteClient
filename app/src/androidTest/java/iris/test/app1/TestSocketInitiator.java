package iris.test.app1;

import org.junit.Test;

import java.io.IOException;

import iris.test.app1.server.connection.SocketInitator;

public class TestSocketInitiator {

    final private SocketInitator socketInitator = new SocketInitator();

    @Test
    public void test() throws IOException {
        socketInitator.connect();
        socketInitator.writeToEndpoint("RQ1.iris.hello");

    }

}
