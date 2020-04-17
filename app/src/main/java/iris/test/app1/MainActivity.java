package iris.test.app1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import iris.test.app1.payment.PaymentScreen;
import iris.test.app1.ui.login.LoginActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void loginScreen(View view){
        Intent i = new Intent(this, NewLoginActivity.class);
        startActivity(i);
    }

    public void createAccountScreen(View v){
        Intent i = new Intent(this, CreateAccountActivity.class);
        startActivity(i);
    }


    public void paymentScreen(View v){
        Intent i = new Intent(this, PaymentScreen.class);
        startActivity(i);
    }

    public void RegisterTrainerInterest(){

    }

}
