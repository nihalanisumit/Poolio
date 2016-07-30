package androarmy.poolio;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

public class SignUp extends AppCompatActivity {
    TextView goToSignUp;
    String fname,lname,mobile,password,email,gender,password_encrypt;
    Button signup;
    EditText fnameet,lnameet,emailet,passwordet;
    Switch genderSwitch;

    //public final String REGISTER_URL="http://192.168.1.6/poolio/register.php"; //Siddharth's pc
    public final String REGISTER_URL="http://192.168.1.14:8080/poolio/register.php";// Sumit's pc
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        goToSignUp = (TextView)findViewById(R.id.link_login);
        goToSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUp.this, SignIn.class);
                startActivity(intent);
                overridePendingTransition(R.anim.next_slide_in, R.anim.next_slide_out);
            }
        });
        fnameet=(EditText)findViewById(R.id.input_fname);
        genderSwitch=(Switch)findViewById(R.id.gender_switch);
        genderSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.i("isChecked",String.valueOf(isChecked));
                if(buttonView.getText().toString().trim().equalsIgnoreCase("male")){
                    buttonView.setText("female");
                }
                else {
                    buttonView.setText("male");
                }
            }
        });
        lnameet=(EditText)findViewById(R.id.input_lname);
        //addresset=(EditText)findViewById(R.id.address);
        passwordet=(EditText)findViewById(R.id.input_password);
        emailet=(EditText)findViewById(R.id.input_email);

        signup = (Button)findViewById(R.id.btn_signup);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUp();
            }
        });
    }


    public void signUp(){
        Intent in = getIntent();
        password=passwordet.getText().toString().trim();
        gender=genderSwitch.getText().toString().trim();
        if("".equalsIgnoreCase(password)){
            Toast.makeText(getApplicationContext(),"Please enter password!",Toast.LENGTH_SHORT).show();
            return;
        }
        password_encrypt= md5(password.toString());

        EditText pass_verify = (EditText)findViewById(R.id.input_password_verify);
        if(pass_verify.getText().toString().trim().equalsIgnoreCase(password)) {


            mobile = in.getStringExtra("mobile");
            fname = fnameet.getText().toString().trim();
            lname = lnameet.getText().toString().trim();
            //address = addresset.getText().toString().trim();
            email = emailet.getText().toString().trim();
            if("".equalsIgnoreCase(fname)||"".equalsIgnoreCase(lname)||"".equalsIgnoreCase(email)){
                Toast.makeText(getApplicationContext(),"one or more fields are empty.",Toast.LENGTH_SHORT).show();
                return;
            }
            register(fname,lname,password_encrypt,email,mobile,gender);
        }
        else {
            Toast.makeText(getApplicationContext(), "Passwords do not match!", Toast.LENGTH_SHORT).show();
            return;
        }
    }
    public void register(String fname,String lname, final String password,String email, final String mobile,final String gender){
        class RegisterUser extends AsyncTask<String, Void, String>{
            ProgressDialog loading;
            RegisterUserClass ruc=new RegisterUserClass();

            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(SignUp.this, "Signing Up","Please wait while we connect to server", true, true);
            }
            protected void onPostExecute(String s){
                super.onPostExecute(s);
                loading.dismiss();
                if("".equals(s))
                {
                    s="Server error, Please try again after some time!";
                }
                else if("successfully registered".equalsIgnoreCase(s)){
                    Intent myIntent = new Intent(SignUp.this, Home.class);
                    myIntent.putExtra("mobile",mobile);
                    myIntent.putExtra("pass",password);
                    startActivity(myIntent);
                    overridePendingTransition(R.anim.next_slide_in, R.anim.next_slide_out);
                }

                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();


            }


            @Override
            protected String doInBackground(String... params) {
                HashMap<String, String> data = new HashMap<String,String>();
                data.put("fname",params[0]);
                data.put("lname",params[1]);
                data.put("password",params[2]);
                data.put("email",params[3]);
                data.put("mobile",params[4]);
                data.put("gender",params[5]);
                String result = ruc.sendPostRequest(REGISTER_URL,data);
                Log.i("@doinBackground:",result);
                return  result;

            }}
        RegisterUser ru = new RegisterUser();
        ru.execute(fname,lname,password,email,mobile,gender);
    }

    public String md5(String s){
        MessageDigest digest ;
        try {
            digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes(Charset.forName("US-ASCII")),0,s.length());
            byte[] magnitude = digest.digest();
            BigInteger bi  = new BigInteger(1,magnitude);
            String hash = String.format("%0" + (magnitude.length << 1) + "X" , bi);
            return hash;
        }
        catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }

        return "";
    }
}


