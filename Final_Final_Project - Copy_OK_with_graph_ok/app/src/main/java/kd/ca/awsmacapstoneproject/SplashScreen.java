package kd.ca.awsmacapstoneproject;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Window;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen extends AppCompatActivity {


        private final AppCompatActivity activity = SplashScreen.this;
        long Delay = 3000;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            setContentView(R.layout.activity_splash_screen);
            ActionBar actionBar = getSupportActionBar();
            actionBar.hide();

            Timer RunSplash = new Timer();

            TimerTask ShowSplash = new TimerTask() {
                @Override
                public void run() {
                    finish();
                    Intent accountsIntent = new Intent(activity,Home.class);
                    startActivity(accountsIntent);
                    overridePendingTransition(R.anim.fadein, R.anim.fadeout);

                }
            };
            RunSplash.schedule(ShowSplash, Delay);
        }

        @Override
        public boolean onKeyDown(int keyCode, KeyEvent event) {
            switch(keyCode) {
                case android.view.KeyEvent.KEYCODE_BACK:
                    //minimize application
                    return true;
            }
            return super.onKeyDown(keyCode, event);
        }

    @Override
    public void onBackPressed() {

    }
}
