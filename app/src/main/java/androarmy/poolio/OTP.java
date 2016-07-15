package androarmy.poolio;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class OTP extends AppCompatActivity {
    Button button_sendOTP;
    EditText phonenumber;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp);
        button_sendOTP = (Button) findViewById(R.id.btn_sendotp);
        phonenumber = (EditText) findViewById(R.id.input_number);

//hello
        button_sendOTP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendOTP();
            }
        });


    }

    void sendOTP()
    {   String number = phonenumber.getText().toString().trim();
        Toast.makeText(getApplicationContext(),"Sending OTP...", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(OTP.this, EnterOTP.class);
        intent.putExtra("mobile",number);
        startActivity(intent);
    }
}
