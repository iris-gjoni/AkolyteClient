package iris.test.app1.server.connection;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.Callable;


public class SocketInitator implements Callable<String> {

    private String host = "192.168.0.8";
    private int port = 1001;
    private SocketChannel socketChannel;
    private final ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
    private final ByteBuffer readBuffer = ByteBuffer.allocate(1024);
    private String message = "";

    private boolean waiting = true;

    public SocketInitator()  {

    }

    public SocketInitator(final String host, final int port)  {
        this.host = host;
        this.port = port;
    }

    @Override
    public String call(){
        try {
            if (connect()) {
                writeToEndpoint();
                String response = blockingReadFromEndpoint();
                disconnect();
                return response;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "failed logon";
    }

    public boolean connect()  {
        try {
            socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            socketChannel.connect(new InetSocketAddress(host, port));
            Thread.sleep(1000); // allow 1 seconds for connection
            return socketChannel.finishConnect();
        } catch (Exception e ){
            e.printStackTrace();
        }
        return false;
    }

    public boolean disconnect() throws IOException {
        if (socketChannel.isConnected()) {
            socketChannel.close();       }
        return socketChannel.isConnected();
    }

    public boolean isConnected(){
        return socketChannel.isConnected();
    }

    public boolean writeToEndpoint()  {
        writeBuffer.put(message.getBytes());
        writeBuffer.flip();
        try {
            socketChannel.write(writeBuffer);
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        writeBuffer.clear();
        return true;
    }

    public String blockingReadFromEndpoint() throws IOException {
        long requestStartTime = System.currentTimeMillis();
        System.out.println(requestStartTime);
        readBuffer.clear();

        while (waiting){
            int bytesRead = socketChannel.read(readBuffer);
            if (bytesRead > 0){
                waiting = false;
                return decodeMessage(readBuffer);
            } else if ((System.currentTimeMillis() - requestStartTime) > 5000){ // break after 5 seconds
                //breaks out of loop
                waiting = false;
                break;
            }

        }
        System.out.println("failed to read from endpoint");
        return "failed to to read from endpoint ";
    }

    private String decodeMessage(ByteBuffer byteBuffer) {
        byteBuffer.flip();
        return new String(byteBuffer.array()).trim();
    }


    public void setMessage(String message) {
        this.message = message;
    }


}
