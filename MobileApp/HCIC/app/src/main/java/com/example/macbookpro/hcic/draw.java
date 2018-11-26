
package com.example.macbookpro.hcic;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.FileNotFoundException;


public class draw extends MainActivity {
    private ImageView iv_canvas;
    private Paint paint;
    private Canvas canvas;
    private Bitmap baseBitmap;
    private Bitmap alterBitmap;
    private Button buttonLoadImage;
    private Button btn_resume;
    private static int RESULT_LOAD_IMAGE = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);
        paint = new Paint();
        paint.setStrokeWidth(10);
        paint.setColor(Color.RED);

        iv_canvas =  findViewById(R.id.imageView);
        btn_resume =  findViewById(R.id.resume);
        btn_resume.setOnClickListener(click);
        iv_canvas.setOnTouchListener(new MyTouchListener());
        buttonLoadImage = (Button) findViewById(R.id.uploadimage);
        buttonLoadImage.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View arg0) {
                // open photo gallery
                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                //set up the result and return
                startActivityForResult(i,RESULT_LOAD_IMAGE);

            }
        });




    }


    public class MyTouchListener implements View.OnTouchListener {
        float startX;
        float startY;
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    if (baseBitmap == null){
                        baseBitmap = Bitmap.createBitmap(iv_canvas.getWidth(), iv_canvas.getHeight(), Bitmap.Config.ARGB_8888);
                        canvas = new Canvas(baseBitmap);
                        canvas.drawColor(Color.WHITE);
                    }
                    startX = event.getX();
                    startY = event.getY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    float stopX =event.getX();
                    float stopY = event.getY();
                    //draw a line between two points
                    canvas.drawLine(startX, startY, stopX, stopY, paint);
                    iv_canvas.invalidate();
                    startX = event.getX();
                    startY = event.getY();
                    //display the image into imageview
                    break;
                case MotionEvent.ACTION_UP:
                    break;
                default:
                    break;
            }
            return true;
        }
    };
    private View.OnClickListener click = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.resume:
                    resumeCanvas();
                    break;
                default:
                    break;
            }
        }
    };
    //resume
    protected void resumeCanvas(){
        if(baseBitmap != null){
            baseBitmap = Bitmap.createBitmap(iv_canvas.getWidth(), iv_canvas.getHeight(), Bitmap.Config.ARGB_8888);
            canvas = new Canvas(baseBitmap);
            canvas.drawColor(Color.WHITE);
            iv_canvas.setImageBitmap(baseBitmap);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri imageFileUri = data.getData();
            Display display = getWindowManager().getDefaultDisplay();
            float dw = display.getWidth();
            float dh = display.getHeight();

            try {
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                baseBitmap = BitmapFactory.decodeStream(getContentResolver()
                        .openInputStream (imageFileUri), null, options);
                int heightRatio = (int) Math.ceil(options.outHeight / dh);
                int widthRatio = (int) Math.ceil(options.outWidth / dw);
                if (heightRatio > 1 && widthRatio > 1) {
                    if (heightRatio > widthRatio) {
                        options.inSampleSize = heightRatio;
                    } else {
                        options.inSampleSize = widthRatio;
                    }
                }
                options.inJustDecodeBounds = false;
                baseBitmap = BitmapFactory.decodeStream(getContentResolver()
                        .openInputStream(imageFileUri), null, options);
                alterBitmap = Bitmap.createBitmap(baseBitmap.getWidth(),
                        baseBitmap.getHeight(), baseBitmap.getConfig());
                canvas = new Canvas(alterBitmap);
                paint = new Paint();
                paint.setColor(Color.GREEN);
                paint.setStrokeWidth(10);
                Matrix matrix = new Matrix();
                canvas.drawBitmap(baseBitmap, matrix, paint);
                iv_canvas.setImageBitmap(alterBitmap);
                iv_canvas.setOnTouchListener(new MyTouchListener());

            } catch (FileNotFoundException e) {

                e.printStackTrace();
            }

        }
    }

}



