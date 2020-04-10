package iris.test.app1.data;

import iris.test.app1.data.model.LoggedInUser;
import iris.test.app1.server.connection.SocketInitator;

import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public static final String logonMsgType = "RQ1";
    private SocketInitator socketInitator;
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    public Result<LoggedInUser> login(String username, String password) {

        try {
            socketInitator = new SocketInitator(); // new one each time?
            final boolean login = VerifyLogin(username, password);
            System.out.println("loged in = " + login);
            // TODO: handle loggedInUser authentication
            //send authentication details to the server - need to do encyrptions with private and public keys.
            if (login) {
                LoggedInUser User =
                        new LoggedInUser(
                                java.util.UUID.randomUUID().toString(),
                                username);
                return new Result.Success<>(User);
            } else {
                return new Result.Error(new IOException("Incorrect Username/ Password combination"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }

    public boolean VerifyLogin(final String user, final String pass)  {
        socketInitator.setMessage(CreateLogonMessage(user, pass));
        Future<String> responseFuture = executor.submit(socketInitator);

        String response = "";
        try {
            response = responseFuture.get();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (response.equals("loggedOn")){
            return true;
        }
        else {
            return false;
        }
    }

    private String CreateLogonMessage(final String user, final String pass) {
        StringBuilder s = new StringBuilder();
        s.append(logonMsgType).append(".").append(user).append(".").append(pass);
        return s.toString();
    }

    public boolean ExtractUserData(){
        return false;
    }
}
