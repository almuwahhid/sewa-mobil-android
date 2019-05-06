package com.sewamobil.sewamobil.menu.splashscreen;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sewamobil.sewamobil.R;
import com.sewamobil.sewamobil.menu.main.MainActivity;

import lib.almuwahhid.Activity.ActivityGeneral;

public class SplashActivity extends ActivityGeneral {

    Thread timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        initTimer();
        timer.start();
    }

    private void initTimer(){
        timer = new Thread() {
            public void run() {
                try {
                    //Create the database
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    startActivity(new Intent(getContext(), MainActivity.class));
                    finish();
                }
            }
        };
    }
}
