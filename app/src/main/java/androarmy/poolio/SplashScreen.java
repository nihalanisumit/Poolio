package androarmy.poolio;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.onesignal.OneSignal;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;

import javax.net.ssl.HttpsURLConnection;


public class SplashScreen extends Activity {
    MyCountdownTimer myCountdownTimer;
    Animation fadeIn;
    private ImageView logo;
    public String NO_VAL="empty";
     //public final String SIGNIN_URL="http://192.168.1.6/poolio/signin.php";//Siddharth's pc
     public final String SIGNIN_URL="http://192.168.1.14:8080/poolio/signin.php";//Sumit's pc
    public final String CONDITION_URL="http://192.168.1.14:8080/poolio/conditions.php";//Sumit's pc
    private String password = null;
    int flag;
    String heading, description;
    String userid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        OneSignal.startInit(this).init();
        OneSignal.idsAvailable(new OneSignal.IdsAvailableHandler() {
            @Override
            public void idsAvailable( String userId, String registrationId) {
                Log.d("debug", "User:" + userId);
                userid = userId ;
                if (registrationId != null)
                    Log.d("debug", "registrationId:" + registrationId);
            }
        });
        try {
            OneSignal.postNotification(new JSONObject("{'contents': {'en':'Test Message'}, 'include_player_ids': ['" + userid + "']}"),
                    new OneSignal.PostNotificationResponseHandler() {
                        @Override
                        public void onSuccess(JSONObject response) {
                            Log.i("OneSignalExample", "postNotification Success: " + response.toString());
                        }

                        @Override
                        public void onFailure(JSONObject response) {
                            Log.e("OneSignalExample", "postNotification Failure: " + response.toString());
                        }
                    });
        } catch (JSONException e) {
            e.printStackTrace();
        }

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);
        StartAnimations();
        getCondition(CONDITION_URL);
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

        public void onFinish() {

            myCountdownTimer.cancel();
            if (flag == 0) {

                goFurther();
            }
            else if(flag==1) {

                 Dialog dialog = new Dialog(SplashScreen.this);
                dialog.setTitle("UPDATE");
                dialog.setContentView(R.layout.customdialog);
                TextView headingtv = (TextView)dialog.findViewById(R.id.heading_tv);
                TextView descriptiontv = (TextView)dialog.findViewById(R.id.description_tv);
                headingtv.setText(heading);
                descriptiontv.setText(description);
                Button button_continue =(Button)dialog.findViewById(R.id.button_continue);
                Button button_download =(Button)dialog.findViewById(R.id.button_download);
                button_continue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        goFurther();
                    }
                });
                button_download.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String url = "https://play.google.com/store/apps/details?id=androarmy.torque&hl=en";

                        Intent intent = new Intent();
                        intent.setAction(Intent.ACTION_VIEW);
                        intent.addCategory(Intent.CATEGORY_BROWSABLE);

                        //pass the url to intent data
                        intent.setData(Uri.parse(url));

                        startActivity(intent);
                    }
                });
                dialog.show();

            }
            else if(flag==2){
                Dialog dialog = new Dialog(SplashScreen.this);
                dialog.setTitle("UPDATE");
                dialog.setContentView(R.layout.customdialog2);
                TextView headingtv = (TextView)dialog.findViewById(R.id.heading_tv);
                TextView descriptiontv = (TextView)dialog.findViewById(R.id.description_tv);
                headingtv.setText(heading);
                descriptiontv.setText(description);
                Button button_close = (Button)dialog.findViewById(R.id.button_exit);
                button_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        finish();
                    }
                });
                dialog.show();


            }
            else
                goFurther();
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
                    Intent intent = new Intent(getApplicationContext(),SignIn.class);
                    startActivity(intent);
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


    public void getCondition(String url) {

        class GetJSON extends AsyncTask<String, Void, String> {
            ProgressDialog loading;


            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //loading = ProgressDialog.show(getContext(),"Please Wait",null,true,true);

            }

            @Override
            protected String doInBackground(String... params) {

                String uri = params[0];

                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    con.setReadTimeout(15000);
                    con.setConnectTimeout(15000);
                    con.setDoInput(true);
                    con.setDoOutput(true);

                    String response;

                    int responseCode = con.getResponseCode();

                    if (responseCode == HttpsURLConnection.HTTP_OK) {

                        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                        response = br.readLine();
                        return response;
                    } else {

                        response = "Error";

                        return response;

                    }
                }
                catch(Exception e){
                    e.printStackTrace();
                    return "Exception";
                }


            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                //loading.dismiss();



                try {
                    JSONObject jsonObject = new JSONObject(s);
                    JSONArray result = jsonObject.getJSONArray("result");
                    JSONObject c = result.getJSONObject(0);
                    flag = c.getInt("id");
                    if(flag!=0)
                    {
                        heading=c.getString("heading");
                        description=c.getString("description");
                    }



                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }
        GetJSON gj = new GetJSON();
        gj.execute(url);
    }

    void goFurther(){
        SharedPreferences session = getSharedPreferences("session", MODE_PRIVATE);
        String mob = session.getString("mobile", NO_VAL);
        String pass = session.getString("password", NO_VAL);

        if (mob.equalsIgnoreCase(NO_VAL) || pass.equalsIgnoreCase(NO_VAL) || mob.equalsIgnoreCase("") || pass.equalsIgnoreCase("")) {
            Intent myIntent = new Intent(SplashScreen.this, WelcomeSlider.class);
            SplashScreen.this.finish();
            startActivity(myIntent);
            overridePendingTransition(R.anim.next_slide_in, R.anim.next_slide_out);
        } else {
            userLogin(mob, pass);
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

