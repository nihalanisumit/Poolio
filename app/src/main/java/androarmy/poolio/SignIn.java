package androarmy.poolio;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

public class SignIn extends AppCompatActivity {
    TextView goToSignUp;
    Button btn_signin, skipbtn;
    //public final String SIGNIN_URL="http://192.168.1.6/poolio/signin.php";//Siddharth's pc
  // public final String SIGNIN_URL="http://192.168.1.14:8080/poolio/signin.php";//Sumit's pc
   // public final String SIGNIN_URL="http://192.168.1.101/poolio/signin.php";//Siddharth's pc
    public final String SIGNIN_URL="http://192.168.1.14:8080/poolio/signin.php";//Sumit's pc
    EditText input_mob,input_pass;
    String mob="12345",pass;
    SharedPreferences mSharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        goToSignUp = (TextView)findViewById(R.id.link_signup);

        skipbtn=(Button)findViewById(R.id.skip_but);
        input_mob=(EditText)findViewById(R.id.input_number);
        input_pass=(EditText)findViewById(R.id.input_password);
        skipbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignIn.this,Home.class);
                startActivity(intent);
                overridePendingTransition(R.anim.next_slide_in, R.anim.next_slide_out);
            }
        });
        goToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignIn.this, OTP.class);
                startActivity(intent);
                overridePendingTransition(R.anim.next_slide_in,R.anim.next_slide_out);
            }
        });

        btn_signin = (Button)findViewById(R.id.btn_login);
        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSignInButtonClick();

            }
        });
    }

    void onSignInButtonClick()
    {
        mSharedPreferences = getSharedPreferences("UserDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSharedPreferences.edit();

        mob= input_mob.getText().toString().trim();
        editor.putString("mobile",mob);
        editor.commit();
        pass=input_pass.getText().toString().trim();
        if("".equalsIgnoreCase(mob) || "".equalsIgnoreCase(pass)){
            Toast.makeText(getApplicationContext(),"One or more fields are empty!",Toast.LENGTH_SHORT).show();
            return;
        }
        userLogin(mob, pass);
    }
    private void userLogin(final String mobile, final String password){
        class UserLoginClass extends AsyncTask<String,Void,String> {
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(SignIn.this,"Signing in","Please wait while we connect to server",true,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                if("success".equalsIgnoreCase(s)){

                    Intent intent = new Intent(SignIn.this,Home.class);
                    intent.putExtra("mobile",mobile);
                    intent.putExtra("pass",password);
                    startActivity(intent);
                    overridePendingTransition(R.anim.next_slide_in, R.anim.next_slide_out);



                }else{
                    Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                }

            }

            @Override
            protected String doInBackground(String... params) {
                HashMap<String,String> data = new HashMap<>();
                data.put("mobile",params[0]);
                data.put("password",params[1]);

                RegisterUserClass ruc = new RegisterUserClass();

                String result = ruc.sendPostRequest(SIGNIN_URL,data);

                return result;
            }
        }
        UserLoginClass ulc = new UserLoginClass();
        ulc.execute(mobile,password);
    }
}
