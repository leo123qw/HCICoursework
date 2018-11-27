package com.example.macbookpro.hcic;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;



import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;


public class stroke extends MainActivity {
    private GifImageView catImageView = null;
    private GifDrawable catDrawable = null;
    private MediaPlayer mp = null;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stroke);

        catImageView = findViewById(R.id.catGif);
        catDrawable = (GifDrawable) catImageView.getDrawable();
        mp = MediaPlayer.create(stroke.this,R.raw.purr);

        catDrawable.stop();



        catImageView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getActionMasked() == MotionEvent.ACTION_DOWN) {
                    catDrawable.start();
                    
                    return true;
                }
                if (event.getActionMasked() == MotionEvent.ACTION_MOVE){
                    catDrawable.start();
                    mp.start();
                    return true;
                }
                if(event.getActionMasked() == MotionEvent.ACTION_UP) {
                    catDrawable.stop();
                    mp.pause();
                    return true;
                }

                return true;

            }
        });




    }


}
