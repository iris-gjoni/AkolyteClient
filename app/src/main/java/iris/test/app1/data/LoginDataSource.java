package iris.test.app1.data;

import iris.test.app1.data.model.LoggedInUser;
import iris.test.app1.server.connection.SocketInitator;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public static final String logonMsgType = "RQ1";
    private SocketInitator socketInitator;

    public Result<LoggedInUser> login(String username, String password) {

        try {
            socketInitator = new SocketInitator(); // new one each time?
            final boolean login = VerifyLogin(username, password);
            System.out.println("loged in = " + login);
            // TODO: handle loggedInUser authentication
            //send authentication details to the server - need to do encyrptions with private and public keys.
            LoggedInUser fakeUser =
                    new LoggedInUser(
                            java.util.UUID.randomUUID().toString(),
                            "Jane Doe");
            return new Result.Success<>(fakeUser);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }

    public boolean VerifyLogin(final String user, final String pass) throws IOException {
        socketInitator.setMessage(CreateLogonMessage(user, pass));
        socketInitator.StartThread();

        if (socketInitator.getResponse().equals("loggedOn")){
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

    public boolean GetUserData(){
        return false;
    }
}
