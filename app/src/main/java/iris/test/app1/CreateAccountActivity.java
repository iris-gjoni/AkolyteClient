package iris.test.app1;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import iris.test.app1.server.connection.SocketInitator;

public class CreateAccountActivity extends AppCompatActivity {

    private SocketInitator socketInitator = new SocketInitator();
    private ExecutorService service = Executors.newSingleThreadExecutor();

    private ArrayAdapter<String> games;
    private Spinner game;
    private ArrayAdapter<String> platforms;
    private Spinner platform;

    private EditText pass;
    private EditText confPass;
    private EditText user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        game = findViewById(R.id.gameDropDown);
        games = new ArrayAdapter<String>(CreateAccountActivity.this,
                R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.gameGroup2));
        game.setAdapter(games);

        platform = findViewById(R.id.platformDropDown);
        platforms = new ArrayAdapter<String>(CreateAccountActivity.this,
                R.layout.support_simple_spinner_dropdown_item, getResources().getStringArray(R.array.platformGroup));
        platform.setAdapter(platforms);

        pass = findViewById(R.id.txtPassword);
        confPass = findViewById(R.id.txtConfirmPassword);
        user = findViewById(R.id.txtUsername);

        pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
        confPass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

        pass.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                MatchingPasswords();
            }
        });

    }

    private void MatchingPasswords() {
        if ( (pass.getText() != null) && (confPass.getText()!= null) ){
            if ( pass.getText().toString().equals(confPass.getText().toString())){
                pass.setHighlightColor(getResources().getColor(R.color.forrestGreen));
            } else {
                pass.setHighlightColor(getResources().getColor(R.color.red));
            }
        }
    }

    public CreateAccountActivity() {

    }

    public void populateXlistGame(){
        game = findViewById(R.id.gameDropDown);
    }

    public boolean CheckIfUserNameValid(final String User){
        socketInitator.setMessage(User);

        return true;
    }

    public String sendDataAndGetResponse(final String dataToSend){
        socketInitator.setMessage(dataToSend);
        Future<String> response =  service.submit(socketInitator);
        try {
            return response.get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "failed to send message";
    }


}
