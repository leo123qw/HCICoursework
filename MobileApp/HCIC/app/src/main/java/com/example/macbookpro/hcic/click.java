package com.example.macbookpro.hcic;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class click extends MainActivity {
    TextView times,clicks;
    Button clickbutton, startbutton;

    CountDownTimer timer;
    int time = 30;

    int click =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click);

        times = (TextView) findViewById(R.id.times);
        clicks = (TextView) findViewById(R.id.clicks);
        clickbutton = (Button) findViewById(R.id.clickbutton);
        startbutton = (Button) findViewById(R.id.startsbutton);

        startbutton.setEnabled(true);
        clickbutton.setEnabled(false);

        timer = new CountDownTimer(30000,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                time--;
                times.setText("Time: "+ time);
            }

            @Override
            public void onFinish() {
                startbutton.setEnabled(true);
                clickbutton.setEnabled(false);
                times.setText("Time: 0");
            }
        };

        clickbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click++;
                clicks.setText("Clicks: "+ click);
            }
        });

        startbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                timer.start();
                startbutton.setEnabled(false);
                clickbutton.setEnabled(true);
                click = 0;
                time = 30;
                times.setText("Time: "+ time);
                clicks.setText("Clicks: "+ click);
            }
        });
    }

}
