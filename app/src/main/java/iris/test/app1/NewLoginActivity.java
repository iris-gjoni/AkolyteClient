package iris.test.app1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import iris.test.app1.server.connection.SocketInitator;

public class NewLoginActivity extends AppCompatActivity {

    public static final String logonMsgType = "RQ1";
    private SocketInitator socketInitator = new SocketInitator(); ;
    private ExecutorService executor = Executors.newSingleThreadExecutor();

    private EditText user;
    private EditText pass;

    private Button login;
    private boolean loggedIn = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_login);

        pass = findViewById(R.id.txtLoginPassword);
        user = findViewById(R.id.txtLoginUsername);
        login = findViewById(R.id.btnLogin);

        final Intent i = new Intent(this, MainActivity.class);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ((user.getText() != null) && (pass.getText() != null)) {
                    final boolean login = VerifyLogin(user.getText().toString(), pass.getText().toString());
                    if (login){
                        loggedIn = true;
                        startActivity(i);
                    }
                }
            }
        });






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

}
