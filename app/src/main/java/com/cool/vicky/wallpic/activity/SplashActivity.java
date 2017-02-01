package com.cool.vicky.wallpic.activity;


import android.content.Intent;
import android.graphics.Typeface;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.cool.vicky.wallpic.R;


public class SplashActivity extends AppCompatActivity {

    private int SPLASH_TIME_OUT = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        String fontPath = "fonts/Pacifico.ttf";
        TextView appName = (TextView) findViewById(R.id.app_name);
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
        appName.setTypeface(tf);
        TextView poweredBy = (TextView) findViewById(R.id.powered_by);
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
        return;
    }
}

