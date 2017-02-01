package com.cool.vicky.wallpic.activity;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.cool.vicky.wallpic.R;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        String fontPath = "fonts/Pacifico.ttf";
        TextView appName = (TextView) findViewById(R.id.app_name);
        Typeface tf = Typeface.createFromAsset(getAssets(), fontPath);
        appName.setTypeface(tf);
    }
}
