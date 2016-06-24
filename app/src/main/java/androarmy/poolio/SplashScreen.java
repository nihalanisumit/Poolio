package androarmy.poolio;

import android.app.Activity;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;


public class SplashScreen extends Activity {
    MyCountdownTimer myCountdownTimer;
    Animation fadeIn;
    private ImageView logo;



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
            Intent myIntent = new Intent(SplashScreen.this, WelcomeSlider.class);
            SplashScreen.this.finish();
            startActivity(myIntent);
            overridePendingTransition(R.anim.next_slide_in, R.anim.next_slide_out);
        }
    }
}

