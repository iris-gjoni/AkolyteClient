package iris.test.app1.server.connection;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class SocketInitator implements Runnable {

    private final String host = "localhost";
    private final int port = 1001;
    private SocketChannel socketChannel;
    private final ByteBuffer byteBuffer = ByteBuffer.allocate(48);
    public String message = "";
    public String response = "";

    public SocketInitator()  {
    }

    @Override
    public void run(){
        try {
            System.out.println("connect:"+connect());
            System.out.println("writeToEndpoint:"+writeToEndpoint());
            this.response = blockingReadFromEndpoint();
            disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public boolean connect() throws IOException {
        socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("192.168.0.8", 1001));
        return socketChannel.isConnected();
    }

    public boolean disconnect() throws IOException {
        if (socketChannel.isConnected()) {
            socketChannel.close();       }
        return socketChannel.isConnected();
    }

    public boolean isConnected(){
        return socketChannel.isConnected();
    }

    public boolean writeToEndpoint() throws IOException {
        byteBuffer.put(message.getBytes());
        byteBuffer.flip();
        socketChannel.write(byteBuffer);
        byteBuffer.clear();
        return true;
    }

    public String blockingReadFromEndpoint() throws IOException {
        boolean waiting = true;
        while (waiting){
            int byteRead = socketChannel.read(byteBuffer);
            if (byteRead > 0){
                waiting = false;
                return "loggedOn"; // for now
            }
            //block until response - what if no response ? dead lock?
        }
            return "failed to logon ";
    }

    public boolean sendAction(){
        return false;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public void StartThread(){
        Thread t = new Thread(this);
        t.start();
    }
}
