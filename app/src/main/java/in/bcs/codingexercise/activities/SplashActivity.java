package in.bcs.codingexercise.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import in.bcs.codingexercise.R;

public class SplashActivity extends AppCompatActivity {
    //Splash activity display time
    private static final int SPLASH_DELAY = 1000;
    private final Handler mHandler = new Handler();
    private final Launcher mLauncher = new Launcher();

    @Override
    protected void onStart() {
        super.onStart();
        //Splash activity display time
        mHandler.postDelayed(mLauncher, SPLASH_DELAY);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
    }

    @Override
    protected void onStop() {
        mHandler.removeCallbacks(mLauncher);
        super.onStop();
    }
    private class Launcher implements Runnable {
        @Override
        public void run() {
            if (!isFinishing()) {
                //Launch the MainActivity once the delay time is completed
                startActivity(new Intent(SplashActivity.this, FactsRecyclerActivity.class));
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                finish();
            }
        }
    }
}
