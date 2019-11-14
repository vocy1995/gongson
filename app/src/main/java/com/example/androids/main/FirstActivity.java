package com.example.androids.main;


import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;

public class FirstActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.firstactivity);
        Handler hd = new Handler();
        hd.postDelayed(new Runnable() {
            @Override
            public void run() {

                finish();       // 3 초후 이미지를 닫아버림
            }
        }, 3000);
    }
}
