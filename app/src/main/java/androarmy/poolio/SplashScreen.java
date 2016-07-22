package androarmy.poolio;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.Toast;

import java.math.BigInteger;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;


public class SplashScreen extends Activity {
    MyCountdownTimer myCountdownTimer;
    Animation fadeIn;
    private ImageView logo;
    public String NO_VAL="empty";
     //public final String SIGNIN_URL="http://192.168.1.6/poolio/signin.php";//Siddharth's pc
     public final String SIGNIN_URL="http://192.168.1.14:8080/poolio/signin.php";//Sumit's pc
    private String password = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        StartAnimations();
        myCountdownTimer = new MyCountdownTimer(3000, 1000);
        myCountdownTimer.start();

        logo = (ImageView)findViewById(R.id.logo);

        fadeIn = new AlphaAnimation(0.0f , 1.0f ) ;
        fadeIn.setDuration(2100);
        fadeIn.setFillAfter(true);
        logo.startAnimation(fadeIn);

    }

    private void StartAnimations() {
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.alpha);
        anim.reset();
        anim = AnimationUtils.loadAnimation(this, R.anim.translate);
        anim.reset();
        ImageView iv = (ImageView) findViewById(R.id.logo);
        iv.clearAnimation();
        iv.startAnimation(anim);
    }

    public class MyCountdownTimer extends CountDownTimer
    {

        public MyCountdownTimer(long startTime, long interval) {
            super(startTime, interval);
        }

        @Override
        public void onTick(long millisUntilFinished)
        {

        }
        @Override

        public void onFinish()
        {
            myCountdownTimer.cancel();
            SharedPreferences session = getSharedPreferences("session", MODE_PRIVATE);
            String mob = session.getString("mobile", NO_VAL);
            String pass = session.getString("password", NO_VAL);
            md5(pass);
            if (mob.equalsIgnoreCase(NO_VAL) || pass.equalsIgnoreCase(NO_VAL) || mob.equalsIgnoreCase("") || pass.equalsIgnoreCase("")) {
                Intent myIntent = new Intent(SplashScreen.this, WelcomeSlider.class);
                SplashScreen.this.finish();
                startActivity(myIntent);
                overridePendingTransition(R.anim.next_slide_in, R.anim.next_slide_out);
            }
            else {
                      userLogin(mob,password);
             }
        }
        private void userLogin(final String mobile, final String password){
            class UserLoginClass extends AsyncTask<String,Void,String> {
                ProgressDialog loading;
                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    loading = ProgressDialog.show(SplashScreen.this,"Signing in","Please wait while we connect to server",true,true);
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    loading.dismiss();
                    if("success".equalsIgnoreCase(s)){

                        Intent intent = new Intent(SplashScreen.this,Home.class);
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

    public String md5(String s){
        MessageDigest digest ;
        try {
            digest = MessageDigest.getInstance("MD5");
            digest.update(s.getBytes(Charset.forName("US-ASCII")),0,s.length());
            byte[] magnitude = digest.digest();
            BigInteger bi  = new BigInteger(1,magnitude);
            String hash = String.format("%0" + (magnitude.length << 1) + "X" , bi);
            password = hash;
            return hash;
        }
        catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }

        return "";
    }
}

