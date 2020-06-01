package com.example.timeismoney;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


public class StatsActivity extends AppCompatActivity {
Button btnStart;
Button btnStop;
ImageView icanchor;
Animation roundingalone;
Chronometer timerHere;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);
    btnStart = findViewById(R.id.btnstart);
    btnStop = findViewById(R.id.btnstop);
    icanchor = findViewById(R.id.icanchor);
    timerHere = findViewById(R.id.timerHere);

    btnStop.setAlpha(0);
    roundingalone = AnimationUtils.loadAnimation(this, R.anim.roundingalone);
    btnStart.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            icanchor.startAnimation(roundingalone);
            btnStop.animate().alpha(1).translationY(-80).start();
            btnStart.animate().alpha(0).setDuration(300).start();
            timerHere.setBase(SystemClock.elapsedRealtime());
            timerHere.start();


        }
    });
    }


    public void stop(View view) {
        timerHere.stop();
        icanchor.clearAnimation();
        btnStart.animate().alpha(1).setDuration(300).start();
        btnStop.animate().alpha(0).setDuration(300).start();


    }
}
