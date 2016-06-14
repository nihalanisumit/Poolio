package androarmy.poolio;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {
    MyCountdownTimer myCountdownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        myCountdownTimer = new MyCountdownTimer(3000, 1000);
        myCountdownTimer.start();

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

